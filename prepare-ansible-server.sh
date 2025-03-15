#!/bin/env bash

apt udate
apt install ansible -y
apt install python3-boto3
ansible-galaxy collection install community.docker:==4.5.1 --force