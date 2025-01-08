#!/bin/bash
echo "Stopping application..."

sudo pkill -f app.jar || true
