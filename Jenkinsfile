def gv

pipeline {
    agent any
    environment {
        ANSIBLE_SERVER = "146.190.248.126"
    }

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
