def gv

pipeline {
    agent any
    environment {
        ANSIBLE_SERVER = "138.197.171.181"
        VERSION = "1.1.20-21"
    }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage('copy files to ansible server') {
            steps {
                script {
                    gv.copyFilesToAnsibleServer()
                }
            }
        }
        stage('run ansible playbook') {
            steps {
                script {
                    gv.executeAnsible()
                }
            }
        }

    }
}
