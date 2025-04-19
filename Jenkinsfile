/*
pipeline {
    agent any
    tools {
        maven 'MAVEN-3.9.9'   // Nom de l'installation Maven dans Jenkins
        jdk 'JDK23'           // Nom de l'installation JDK dans Jenkins
    }
    environment {
        DOCKER_IMAGE = "votre-dockerhub-username/mon-app-spring:${env.BUILD_NUMBER}"
    }
    stages {
        // Étape 1 : Récupération du code
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        // Étape 2 : Build avec Maven
        stage('Build JAR') {
            steps {
                bat 'mvn clean package'
            }
        }

        // Étape 3 : Construction de l'image Docker
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${env.DOCKER_IMAGE}")
                }
            }
        }

        // Étape 4 : Pousser l'image sur Docker Hub (optionnel)
        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                        docker.image("${env.DOCKER_IMAGE}").push()
                    }
                }
            }
        }

        // Étape 5 : Déploiement
        stage('Deploy') {
            steps {
                bat """
                    docker stop spring-app || true
                    docker rm spring-app || true
                    docker run -d -p 8080:8080 --name spring-app ${env.DOCKER_IMAGE}
                """
            }
        }
    }
    post {
        success {
            echo '✅ Pipeline réussi ! Application déployée sur http://localhost:8080'
        }
        failure {
            echo '❌ Pipeline échoué. Consultez les logs.'
        }
    }
} */
