#!/bin/bash -xe
# Set profiles
echo "export SPRING_PROFILES_ACTIVE=${environment}" | sudo tee -a /etc/environment
source /etc/environment

# Set Timezone
sudo timedatectl set-timezone Asia/Seoul

# Set Java
sudo wget https://corretto.aws/downloads/latest/amazon-corretto-21-aarch64-linux-jdk.rpm
sudo yum localinstall -y amazon-corretto-21-aarch64-linux-jdk.rpm

# Set Code Deploy Agent
sudo yum install -y ruby
sudo yum install -y wget
cd /home/ec2-user
wget https://aws-codedeploy-${region}.s3.${region}.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
sudo service codedeploy-agent start
sudo systemctl enable codedeploy-agent

# Create and grant logging folder
sudo mkdir -p /logs/error
sudo chmod -R 755 /logs
sudo chown -R ec2-user:ec2-user /logs

# Download application from S3
aws s3 cp s3://${bucket_name}/app.zip /home/ec2-user/app.zip

# Unzip
unzip app.zip

# Start the application
nohup java -jar /home/ec2-user/app.jar > /home/ec2-user/app.log 2>&1 &
