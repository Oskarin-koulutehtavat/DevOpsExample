#! /bin/sh

# install openjdk-17-jre
apt install openjdk-17-jre -y

# set JAVA_HOME and add /bin to PATH
echo 'export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:/bin/java::")' >> /etc/profile
echo 'export PATH=$PATH:$JAVA_HOME/bin' >> /etc/profile

# run profile to apply changes
. /etc/profile
