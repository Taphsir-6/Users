pipeline {
    agent any
    tools {
        maven 'MAVEN-3.9.9'  // Nom de l'installation Maven
        docker 'docker-windows'
    }
    stages {
        stage('Build JAR') {
            steps {
                bat 'mvn clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("mon-app:${env.BUILD_NUMBER}", ".")
                }
            }
        }
        stage('Run Container') {
            steps {
                script {
                    docker.image("mon-app:${env.BUILD_NUMBER}").run("--name mon-app -p 8080:8080 -d")
                }
            }
        }
    }
}