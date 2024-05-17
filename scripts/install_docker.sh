#! /bin/sh

# download signing keys
wget -O /etc/apt/keyrings/docker.asc \
  https://download.docker.com/linux/ubuntu/gpg

# add docker apt source
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] \
  https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# refresh apt and install docker components
apt update
apt install docker-ce docker-ce-cli containerd.io \
  docker-buildx-plugin docker-compose-plugin -y
