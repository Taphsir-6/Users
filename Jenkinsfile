pipeline {
    agent any

    tools {
        maven 'Maven-3.9.9'
        dockerTool 'docker-windows'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Docker Version') {
            steps {
                sh 'docker --version'
            }
        }
    }
}
