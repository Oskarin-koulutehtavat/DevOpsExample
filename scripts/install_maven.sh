#! /bin/sh

# download maven binaries and move them to /usr/local/
wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip
unzip apache-maven-3.9.6-bin.zip
mv apache-maven-3.9.6 /usr/local/

# set M2_HOME and add /bin to PATH
echo 'export M2_HOME="/usr/local/apache-maven-3.9.6"' >> /etc/profile
echo 'export MAVEN_HOME="/usr/local/apache-maven-3.9.6"' >> /etc/profile
echo 'export PATH=$PATH:$M2_HOME/bin' >> /etc/profile

# run profile to apply changes
. /etc/profile
