#!/bin/bash

# Use this script in AWS Console ('Configure Instance Details' page -> 'Advanced Details') to configure Amazon Linux AMI instance during launch

sudo yum update -y

if [ -x "$(command -v javac)" ]; then
  echo 'Error: Environment already initialized. Exiting...' >&2
  exit 1
fi

# Create directories
mkdir /home/ec2-user/selenium
sudo chgrp -R ec2-user /home/ec2-user/selenium
sudo chown -R ec2-user /home/ec2-user/selenium
sudo mkdir /opt/selenium

# Install JDK 8 and remove old version
sudo yum install -y java-1.8.0-openjdk-devel.x86_64
sudo yum remove -y java-1.7.0-openjdk

# Install headless Chrome and dependancies easy way
curl https://intoli.com/install-google-chrome.sh | bash

# Setup chromedriver binary for local (scheduled) test running
wget https://chromedriver.storage.googleapis.com/77.0.3865.40/chromedriver_linux64.zip
sudo unzip chromedriver_linux64.zip -d /opt/selenium

# Setup Selenium Server binary for remote test running
wget https://selenium-release.storage.googleapis.com/3.141/selenium-server-standalone-3.141.59.jar
sudo mv selenium-server-standalone-*.jar /opt/selenium/selenium-server-standalone.jar
java -jar -Dwebdriver.chrome.driver=/opt/selenium/chromedriver /opt/selenium/selenium-server-standalone.jar -port 5555 &

# Automatically run test every day at 3:00 AM
sudo crontab -l -u ec2-user | { cat; echo "0 3 * * * /home/ec2-user/selenium/./gradlew test > /dev/null 2>&1"; } | sudo crontab -u ec2-user -

# BONUS: Install Apache to read html reports easily
sudo yum install -y httpd24
sudo service httpd start
sudo groupadd www
sudo usermod -a -G www ec2-user
sudo chgrp -R www /var/www
sudo chmod 2775 /var/www
find /var/www -type d -exec sudo chmod 2775 {} +
find /var/www -type f -exec sudo chmod 0664 {} +
sudo chown ec2-user /var/www/html