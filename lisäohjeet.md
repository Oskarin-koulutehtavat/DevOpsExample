# Hyödyllisiä Lisäohjeita

## Metropolian Virtuaalipalvelimen Tilaaminen

Virtuaalipalvelimen tilaamiseen ja siihen yhdistämiseen tarvitaan aina Metropolian VPN yhteys. Seuraa [tietohallinnon ohjeita](https://wiki.metropolia.fi/pages/viewpage.action?pageId=257364221) virtuaalipalvelimen tilaamiseen. Small palvelin täyttää Jenkinsin vaatimukset. Lease time kannattaa ottaa tarpeeksi pitkä, että kattaa koko tarvittavan ajan, sillä saat hakea siihen kahden viikon lisäajan vain kerran, ja sen hakemusta ei välttämättä hyväksytä. Hakiessa annettu tai asettamasi salasana kannattaa ottaa talteen. Hakemuksen lähetyksen jälkeen salasanan voi tarkistaa virtuaalipalvelimen Cloud Config osiosta.

## SSH Yhteyden Avainpohjainen Todennus

SSH etäyhteyden luomisen helpottamiseksi kannattaa salasanan sijaan ottaa käyttöön avainpohjainen todennus.

Avainparin luominen tehdään erikseen jokaisella tietokoneella ja käyttäjällä, josta aiot ottaa yhteyden palvelimelle. Seuraava komento luo avainparin.

```sh
ssh-keygen -t ed25519
```

Avainta lodesssa kysytään sijaintia ja salasanaa, joiden arvot voi yleensä jättää oletuksiksi. Ilman salasanaa kuka tahansa jolla on pääsy koneelle ja käyttäjälle jolle avain on luotu voi muodostaa SSH yhteyden palvelimeen, joten tämän ollessa mahdollista kannattaa harkita salasanan asettamista.

![ssh-keygen exapmle](/images/ssh-keygen.png)

Seuraava komento kopioi julkisen osan avainparia palvelimelle. Korvaa `[]` arvot edellä luomasi julkisen avaimen sijainnilla sekä palvelimen käyttäjätunnuksella ja IP osoitteella.

```sh
ssh-copy-id -i [path/to.pub] [username]@[remote-host-address]
```

Kopiointi pyytää palvelimen käyttäjän salasanaa.

## Githubin Webhook Palomuurin Läpi smee.io Avulla

### Smee Käyttöönotto

Asenna nvm hallintatyökalu, jonka avulla asennat viimeisimät versiot node ja npm.

```sh
sudo curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
. /etc/profile
. ~/.bashrc
nvm install node
npm config set prefix /usr/local
```

Asenna pm2 globaalisti.

```sh
npm install -g pm2
```

Käy hakemassa webhook channel [smee.io](https://smee.io) sivulta. Saat linkin, joka näyttää tältä `https://smee.io/xxxxxxxxxxxxxxxx`.

Luo smee kansio johonkin sopivaan paikkaan ja vaihda työhakemisto siihen.

Tämän jälkeen asenna `smee-client`.

```sh
npm install smee-client
```

Smee-client vastaanottaa webhook viestejä, ja pm2 luo prosessin. Luo seuraava .js tiedosto, johon vaihdat source smee linkkisi, ja targetin portin Jenkinsin porttiin, jos olet muuttanut sitä.

```js
const SmeeClient = require('smee-client');

const smee = new SmeeClient({
  source: 'https://smee.io/xxxxxxxxxxxxxxxx',
  target: 'http://localhost:8080/github-webhook/',
  logger: console
});

const events = smee.start();
```

Aloita se prosessina komennolla:

```sh
pm2 start smee.js
```

### GitHub Webhook Lisäys

Githubissa lisää reposi asetuksista webhook. Webhook `Payload URL` on smee linkkisi, ja `Content type` on `application/json`. `Just the push event` trigger riittää Jenkinsille, mutta voit tarvittaessa valikoida mistä tapahtumista webhook trigger lähetetään, tai voit lähettää kaikkista tapahtumista triggerin.

![github settings add webhook](/images/webhook.png)

Jenkinsissä käy asettamassa asetus `GitHub hook trigger for GITScm polling` päälle.

![alt text](/images/build-triggers.png)

## Maven Manuaalinen Asennus

Maven asennukseen löytyy scripti. Jos suoritat scriptin, voit jättää välistä seuraavat asennus vaiheet.

Lataa ja pura Maven, ja siirrä se kansioon `/usr/local/`

```
sudo wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip
sudo unzip apache-maven-3.9.6-bin.zip
sudo mv apache-maven-3.9.6 /usr/local/
```

Tämän jälkeen lisää Mavenin sijainti `M2_HOME` ja `MAVEN_HOME` muuttujiin ja lisää `bin` kansio `PATH` muuttujaan. Se tapahtuu lisäämällä `/etc/profile` tiedostoon seuraavat rivit

```sh
export M2_HOME="/usr/local/apache-maven-3.9.6"
export MAVEN_HOME=$M2_HOME
export PATH=$PATH:$M2_HOME/bin
```

Tallennuksen jälkeen suorita `profile` komennolla

```sh
. /etc/profile
```

### SonarScanner Manuaalinen Asennus

SonarScanner asennukseen löytyy scripti. Jos suoritat scriptin, voit jättää välistä seuraavat asennus vaiheet.

```sh
sudo wget https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-5.0.1.3006-linux.zip
sudo unzip sonar-scanner-cli-5.0.1.3006-linux.zip
sudo mv sonar-scanner-5.0.1.3006-linux sonar-scanner
sudo mv sonar-scanner /opt/
```

Tämän jälkeen lisää SonarScanner sijainti `SONAR_SCANNER_HOME` muuttujaan ja lisää `bin` kansio `PATH` muuttujaan. Se tapahtuu lisäämällä `/etc/profile` tiedostoon seuraavat rivit

```sh
export SONAR_SCANNER_HOME="/opt/sonar-scanner-5.0.1.3006-linux"
export PATH=$PATH:$SONAR_SCANNER_HOME/bin
```

Tallennuksen jälkeen suorita `profile` komennolla

```sh
. /etc/profile
```

## Lähteet

- [**Metropolia Tietohallinto**: Opetuskäytön ecloud-virtuaalipalvelimet](https://wiki.metropolia.fi/pages/viewpage.action?pageId=257364221)
- [**Metropolia Tietohallinto**: VPN-yhteys GlobalProtect-palvelun kautta](https://wiki.metropolia.fi/display/tietohallinto/VPN-yhteys+GlobalProtect-palvelun+kautta)
- [**Oracle Help Center**: Working with SSH Key Pairs](https://docs.oracle.com/en/operating-systems/oracle-linux/openssh/openssh-WorkingwithSSHKeyPairs.html)
- [**Node.js**: Download Node.js using a package-manager](https://nodejs.org/en/download/package-manager)
- [**StackOverflow**: How to reload profile settings without logging out and back in again?](https://stackoverflow.com/questions/2518127/how-to-reload-bashrc-settings-without-logging-out-and-back-in-again)
- [**GitHub**: probot/smee-client](https://github.com/probot/smee-client)
- [**npm**: pm2](https://www.npmjs.com/package/pm2)
- [**Apache Maven**: Installing Apache Maven](https://maven.apache.org/install.html)
- [**Apache Maven Cookbook**: Installing Maven on Linux](https://subscription.packtpub.com/book/cloud-and-networking/9781785286124/1/ch01lvl1sec12/installing-maven-on-linux)

---

Tehnyt Oskari Pahkala 2024
