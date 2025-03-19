# 15e: CI/CD Pipeline for Docker Compose Deployment on EC2 with Ansible

This project demonstrates a CI/CD pipeline using Jenkins and Ansible to deploy a Java Maven application within Docker containers on an Amazon EC2 instance. It uses a dedicated Ansible server.

## Tasks from Module 15

### **"Ansible Integration in Jenkins"**

- Automated Docker installation and configuration on an EC2 instance using Ansible.

## Project Overview

This project automates the deployment of a containerized Java application stack on AWS EC2. It handles Docker installation, user management, and application deployment via Docker Compose.

The pipeline:

1.  Loads custom functions from `script.groovy`.
2.  Copies files to the Ansible server.
3.  Executes the Ansible playbook to deploy the application.

## Learning Progression/Steps

The `Jenkinsfile` defines the CI/CD pipeline, which includes the following key steps:

1.  **Connect to the Ansible server:** Establishes a connection to the dedicated Ansible control node.

2.  **Prepare the Ansible server:** Copies Ansible playbooks, configuration files, and SSH keys to the Ansible server and installs necessary dependencies (Ansible, Python3, Boto3).

3.  **Execute the Ansible playbook:** Configures the EC2 instances, installs Docker and Docker Compose, creates a user and adds them to the Docker group, and deploys the application's containers using `docker-compose.yaml`.  This project assumes that three EC2 instances are already running in AWS, as described in [Module 15c - Dynamically Deploy Ansible playbook in Terraform](https://gitlab.com/mcfwesh/module-15c-dynamically-deploy-ansible-playbook-in-terraform). Ansible automatically connects to these instances.

## Key Configuration

-   **`Jenkinsfile`**: Defines the CI/CD pipeline.
    -   `ANSIBLE_SERVER`: Ansible control node IP.
    -   `VERSION`: Application version.
-   **`script.groovy`**: Contains functions for copying files and executing the playbook.
-   **`ansible/deploy-docker-ec2.yaml`**: Ansible playbook for EC2 configuration and deployment.
-   **`ansible/inventory_aws_ec2.yaml`**: Defines the dynamic inventory plugin for AWS EC2.
-   **`docker-compose.yaml`**: Defines the application's Docker containers.
-   **`prepare-ansible-server.sh`**: Installs Ansible and dependencies on the control node.

## Technologies Used

-   Ansible
-   Jenkins
-   Docker
-   Docker Compose
-   EC2
-   Boto3

## Pipeline Details

The `Jenkinsfile` orchestrates:

1.  **Initialization:** Loads `script.groovy`.
2.  **Copy Files to Ansible Server:** Copies playbooks, `docker-compose.yaml`, and SSH keys to the Ansible control node.
3.  **Run Ansible Playbook:** Executes `prepare-ansible-server.sh` and the `deploy-docker-ec2.yaml` playbook.

## Ansible Playbook Details

The Ansible playbook:

1.  Installs Docker and Docker Compose.
2.  Creates a user and adds them to the Docker group.
3.  Deploys containers defined in `docker-compose.yaml`.

## Screenshots