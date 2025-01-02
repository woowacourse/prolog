#!/bin/bash
echo "Starting application..."
nohup java -jar /home/ec2-user/app/app.jar > /home/ec2-user/app.log 2>&1 &

