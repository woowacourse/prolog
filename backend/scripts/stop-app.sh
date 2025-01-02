#!/bin/bash
echo "Stopping application..."
pkill -f app.jar || true
