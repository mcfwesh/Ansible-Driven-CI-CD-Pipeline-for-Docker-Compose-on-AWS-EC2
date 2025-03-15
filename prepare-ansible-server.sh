#!/bin/env bash

apt udate
apt install ansible -y
apt install python3-boto3
export IMAGE_NAME=$1