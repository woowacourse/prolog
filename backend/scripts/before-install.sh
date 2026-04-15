#!/bin/bash
echo "Preparing for application installation..."
rm -rf /home/ec2-user/app.jar
sudo chown ec2-user:ec2-user /home/ec2-user/app.log 2>/dev/null || true
