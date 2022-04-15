pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                sh 'echo checkout'
                checkout scm
            }
        }
        stage('build') {
            steps {
                sh 'ls'
            }
        }
    }
}
