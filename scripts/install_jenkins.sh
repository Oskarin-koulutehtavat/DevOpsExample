#! /bin/sh

# download signing keys
wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key

# add jenkins apt source
echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null

# refresh path for jenkins user
sudo -i -u jenkins . /etc/profile

# refresh apt and install jenkins
apt update
apt install jenkins
