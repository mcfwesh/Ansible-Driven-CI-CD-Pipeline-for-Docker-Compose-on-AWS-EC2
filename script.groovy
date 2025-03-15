def copyFilesToAnsibleServer() {
    echo "Copying ansible folder to ansibe connection server..."

    sshAgent(['ansible-server']) {
        sh 'scp -o StrictHostKeyChecking=no ansible/* root@146.190.248.126:/root'
        sh 'scp -o StrictHostKeyChecking=no docker-compose.yml root@146.190.248.126:/root'

        withCredentials([sshUserPrivateKey(credentialsId: 'docker-ec2-server', keyFileVariable: 'SSH_KEY_FILE', usernameVariable: 'USER')]) {
            sh 'scp -o StrictHostKeyChecking=no $SSH_KEY_FILE  root@146.190.248.126:/root/ssh_key'
        }
    }
}

def executeAnsible() {
    def remote = [:]
    remote.name = 'ansible server'
    remote.host = '146.190.248.126'
    remote.allowAnyHosts = true

    withCredentials([sshUserPrivateKey(credentialsId: 'docker-ec2-server', keyFileVariable: 'SSH_KEY_FILE', usernameVariable: 'USER')]) {
        remote.user = USER
        remote.identityFile = SSH_KEY_FILE
        echo "Testing ssh pipeline steps"
        sshCommand remote: remote, script: "prepare-ansible-server.sh"
        sshCommand remote: remote, command: 'ls -l'
        echo "Ansible playbook run successful!"
    }
}

return this