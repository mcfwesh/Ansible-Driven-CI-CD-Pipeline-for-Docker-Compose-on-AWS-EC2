def copyFilesToAnsibleServer() {
    echo "Copying ansible folder, docker-compose file, server prep file to ansible connection server..."
    sshagent(['ansible-server']) {
        sh """
             scp -o StrictHostKeyChecking=no ansible/* root@$ANSIBLE_SERVER:/root
             scp -o StrictHostKeyChecking=no prepare-ansible-server.sh root@$ANSIBLE_SERVER:/root

             sed "s/VERSION/{{ version }}/" docker-compose.yaml > docker-compose.yaml.j2
             scp -o StrictHostKeyChecking=no docker-compose.yaml.j2 root@$ANSIBLE_SERVER:/root/docker-compose.yaml.j2
        """

    echo "Copying ec2 server credentials to ansible connection server..."
        withCredentials([sshUserPrivateKey(credentialsId: 'regular-ec2-server', keyFileVariable: 'SSH_KEY_FILE', usernameVariable: 'USER')]) {
            sh 'scp -o StrictHostKeyChecking=no $SSH_KEY_FILE  root@$ANSIBLE_SERVER:/root/ssh_key'
        }
    }
}

def executeAnsible() {
    def remote = [:]
    remote.name = 'ansible server'
    remote.host = ANSIBLE_SERVER
    remote.allowAnyHosts = true

    withCredentials([sshUserPrivateKey(credentialsId: 'ansible-server', keyFileVariable: 'SSH_KEY_FILE', usernameVariable: 'USER')]) {
        remote.user = USER
        remote.identityFile = SSH_KEY_FILE

        echo "Installing ansible, and related packages..."
        sshScript remote: remote, script: "prepare-ansible-server.sh"

        echo "Running ansible playbook..."
        sshCommand remote: remote, command: "ansible-playbook deploy-docker-ec2.yaml --extra-vars \"version=${VERSION}\""
    }

}

return this