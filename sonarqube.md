# PostgreSQL ja SonarQube

PostgresQL, SonarQube asennuksiin löytyy scriptit. Jos suoritat scriptit, voit jättää välistä ohjeiden asennus vaiheet.

## PostgreSQL

SonarQube tarvitsee toimiakseen PostgreSQL käyttäjän ja taulukon.

### Asennus

Lisätään avaimet ja PostgreSQL arkisto Apt lähteeksi:

```sh
curl -fsSL https://www.postgresql.org/media/keys/ACCC4CF8.asc | \
  sudo gpg --dearmor -o /etc/apt/trusted.gpg.d/postgresql.gpg

echo "deb http://apt.postgresql.org/pub/repos/apt/ `lsb_release -cs`-pgdg main" | \
  sudo tee  /etc/apt/sources.list.d/pgdg.list
```

Asenna PostgreSQL

```sh
sudo apt install postgresql-13 postgresql-client-13 postgresql-contrib -y
```

### PostgreSQL Käyttäjän Ja Tietokannan Luonti SonarQubea Varten

Aloita PostgreSQL

```sh
sudo systemctl enable postgresql
sudo systemctl start postgresql
```

Vaihda `postgres` käyttäjän salasana ja vaihda käyttäjää

```sh
sudo passwd postgres
su postgres
```

Luo PostgreSQL käyttäjä `sonar` ja kirjaudu PostgreSQL

```sh
createuser sonar
psql
```

Aseta `sonar` käyttäjälle salasana, luo tietokanta `sonarqube` ja anna siihen täydet oikeudet.

```sql
ALTER USER sonar WITH ENCRYPTED password 'my_strong_password';
CREATE DATABASE sonarqube OWNER sonar;
GRANT ALL PRIVILEGES ON DATABASE sonarqube to sonar;
\q
```

Vaihda takaisin omaan käyttäjään.

## SonarQube

### Asennus

Ladataan ja puretaan SonarQuben zip tiedosto kansioon, nimetään se uudelleen `sonarqube`, ja siirretään se kansioon `/opt/`.

```sh
sudo wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-9.9.5.90363.zip
sudo unzip sonarqube-9.9.5.90363.zip
sudo mv sonarqube-9.9.5.90363 sonarqube
sudo mv sonarqube /opt/
```

Voit halutessasi käydä tarkistamassa onko uudempi versio julkaistu.

### Ryhmän Ja Käyttäjän Luominen

Seuraavaksi luodaan ryhmä sekä käyttäjä `sonar`. Käyttäjän kotikansioksi asetetaan SonarQuben asennuskansio `/opt/sonarqube`, ja käyttäjälle annetaan opikeudet kysiseen kansioon.

```sh
sudo groupadd sonar
sudo useradd -d /opt/sonarqube -g sonar sonar
sudo chown sonar:sonar /opt/sonarqube -R
```

Seuraavaksi luomme luomme SonarQube palvelun, jonka asetamme alkamaan aina palvelimen käynnistyessä.

```sh
sudo nano /etc/systemd/system/sonar.service
```

Sen sisällöksi tulee

```conf
[Unit]
Description=SonarQube service
After=syslog.target network.target

[Service]
Type=forking
ExecStart=/opt/sonarqube/bin/linux-x86–64/sonar.sh start
ExecStop=/opt/sonarqube/bin/linux-x86–64/sonar.sh stop
User=sonar
Group=sonar
Restart=always
LimitNOFILE=262144
LimitNPROC=4096

[Install]
WantedBy=multi-user.target
```

Sitten muokkaamme tiedostoa

```sh
sudo nano /etc/sysctl.conf
```

johon lisäämme rivit

```txt
vm.max_map_count=524288
fs.file-max=262144
ulimit -n 262144
ulimit -u 4096
```

ja lopuksi uudelleenkäynnistämme palvelimen.

### Konfigurointi

Muokataan SonarQuben configuration tiedostoa.

```sh
sudo nano /opt/sonarqube/conf/sonar.properties
```

Haluamme etsiä sieltä seuraavat rivit

```conf
#sonar.jdbc.username=
#sonar.jdbc.password=
```

poistaa niistä kommentit `#`, ja asettaa niihin aikaisemmin luomamme PostgreSQL `sonar` käyttäjän ja antamamme salasanan, sekä lisätä uuden rivin, jossa määritämme käytettävän PostgreSQL taulukon `sonarqube`. Tiedoston kuuluisi näyttää lopuksi jotakuinkin tältä.

```conf
sonar.jdbc.username=sonar
sonar.jdbc.password=my_strong_password
sonar.jdbc.url=jdbc:postgresql://localhost:5432/sonarqube
```

Samasta tiedostosta voi vaihtaa SonarQube käyttämää porttia riviltä `#sonar.web.port=9000`.

Muista avata portti palomuurista.

```sh
sudo ufw allow 9000
```

Lopuksi asetamme SonarQube palvelun uudelleenkäynnistymään automaattisesti, käynnistämmne palvelun, ja tarkistamme että käynnistys onnistui.

```sh
sudo systemctl enable sonar
sudo systemctl start sonar
sudo systemctl status sonar
```

### Kirjautuminen SonarQube Käyttöliittymään

SonarQuben web käyttöliittymä löytyy portista `9000`. Ensimmäisen kirjautumiskerran tunnus ja salasana ovat admin. SonarQube pyytää vaihtamaan salasanan.

### Token Luominen

Luo `Global Analysis Token` tyyppinen token.

![sonarqube my account security tokens](/images/tokens-1.png)

![Global Analysis Token](/images/tokens-2.png)

Kopioi token talteen, sillä se näytetään vain kerran.

## SonarQuben Käyttöönotto Jenkinsissä

Asennetaan `SonarQube Scanner` laajennus Jenkinsiin. Laajennuksien asennus löytyy **Manage Jenkins > Plugins > Available Plugins**

![manage jenkins plugins](/images/manage-jenkins-plugins.png)

Etsi laajennus saatavilla olevista laajennuksista ja valitse se asennettavaksi.

![jenkins available plugin search](/images/available-plugins-sonar.png)

Laajennukset asentuvat `Install` napista.

![jenkins plugin install button](/images/install.png)

Muokkaa jenkinsin työkaluja **Manage Jenkins > Tools**

![alt text](/images/manage-jenkins-tools.png)

Lisää automaattisesti asentuva SonarQube Scanner

![add sonarqube scanner installation](/images/add-sonar.png)

Seuraavaksi lisäämme luomamme SonarQube token Jenkinsiin **Manage Jenkins > Credentials > System > Global credentials** Global Scope Secret Text tyyppiseksi tunnukseksi ja anna sille ID, jota käytetään Jenkinsfile.

![manage jenkins credentials](/images/manage-jenkins-credentials.png)

||||
-|-|-
![system store](/images/system-store.png)|![global domain](/images/global-domain.png)|![add credentials](/images/add-credentials.png)

![alt text](/images/tokens-3.png)

### SonarQube Server

![manage jenkins system](/images/manage-jenkins-system.png)

Aseta environment variables injection päälle. Lisää SonarQube server, jos sellaista ei ole jo, ja aseta sen tokeniksi äsken luomasi secret text.

![sonarqube server settings](/images/sonarqube-servers.png)

### Jenkisn Pipeline Step

Lisää Jenkinsfile qauality gate stage ja withSonarQubeEnv maven kutsun ympärille sekä sonar:sonar kutsuun.

## Lähteet

- [**Computing for Geeks**: Install PostgreSQL 13 on Ubuntu 22.04|20.04|18.04](https://computingforgeeks.com/how-to-install-postgresql-13-on-ubuntu/)
- [**Medium**: SonarQube Installation on ubuntu 20.04](https://medium.com/@humzaarshadkhan/sonarqube-installation-on-ubuntu-20-04-9c4f8e293870)
- [**SonarQube Docs**: Setting up Jenkins for SonarQube integration](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/ci-integration/jenkins-integration/global-setup/)
- [**SonarQube Docs**: Setting up a pipeline pause until the quality gate is computed](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/ci-integration/jenkins-integration/pipeline-pause/)

---

Tehnyt Oskari Pahkala 2024
