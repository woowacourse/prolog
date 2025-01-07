#!/bin/bash
echo "Starting application..."

source /etc/environment

nohup java -jar /home/ec2-user/app.jar > /home/ec2-user/app.log 2>&1 &

