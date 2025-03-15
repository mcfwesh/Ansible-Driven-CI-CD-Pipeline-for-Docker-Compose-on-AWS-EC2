def gv

pipeline {

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
