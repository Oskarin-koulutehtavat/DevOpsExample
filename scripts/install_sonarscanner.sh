#! /bin/sh

# download and move to /opt/
wget https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-5.0.1.3006-linux.zip
unzip sonar-scanner-cli-5.0.1.3006-linux.zip
mv sonar-scanner-5.0.1.3006-linux sonar-scanner
mv sonar-scanner /opt/

# set SONAR_SCANNER_HOME and add /bin to PATH
echo 'export SONAR_SCANNER_HOME="/opt/sonar-scanner-5.0.1.3006-linux"' >> /etc/profile
echo 'export PATH=$PATH:$SONAR_SCANNER_HOME/bin' >> /etc/profile

# run profile to apply changes
. /etc/profile
