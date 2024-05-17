#! /bin/sh

# download sonarqube binaries and move the to /opt/
wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-9.9.5.90363.zip
unzip sonarqube-9.9.5.90363.zip
mv sonarqube-9.9.5.90363 sonarqube
mv sonarqube /opt/

# create sonar group and sonar user with /opt/sonarqube as home directory
groupadd sonar
useradd -d /opt/sonarqube -g sonar sonar
chown sonar:sonar /opt/sonarqube -R

# create service file /etc/systemd/system/sonar.service
echo \
"[Unit]
Description=SonarQube service
After=syslog.target network.target

[Service]
Type=forking
ExecStart=/opt/sonarqube/bin/linux-x86-64/sonar.sh start
ExecStop=/opt/sonarqube/bin/linux-x86-64/sonar.sh stop
User=sonar
Group=sonar
Restart=always
LimitNOFILE=65536
LimitNPROC=4096

[Install]
WantedBy=multi-user.target" \
> /etc/systemd/system/sonar.service

# raise system limits
echo \
"vm.max_map_count=524288
fs.file-max=65536
ulimit -n 65536
ulimit -u 4096" \
>> /etc/sysctl.conf

echo "Restart to finish installation"
