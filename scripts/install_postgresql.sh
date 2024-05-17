#! /bin/sh

# add postgresql keys
curl -fsSL https://www.postgresql.org/media/keys/ACCC4CF8.asc | \
  sudo gpg --dearmor -o /etc/apt/trusted.gpg.d/postgresql.gpg
# add postgresql apt source
echo "deb http://apt.postgresql.org/pub/repos/apt/ `lsb_release -cs`-pgdg main" | \
  sudo tee  /etc/apt/sources.list.d/pgdg.list

# install postgresql components
sudo apt install postgresql-13 postgresql-client-13 postgresql-contrib -y
