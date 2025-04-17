pipeline {
    agent any  // Exécute sur n'importe quel nœud Jenkins

     tools {
            maven 'Maven-3.9.9' // Correspond au nom configuré ci-dessus
            jdk 'jdk17' // Si vous utilisez Java
     }
     environment {
             LC_ALL = 'en_US.UTF-8'
             DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
         }

     stages {
            stage('Checkout') {
                steps {
                    checkout scm // Supprimez le double checkout
                }
            }

        // Étape 2 : Build avec Maven
        stage('Build') {
            steps {
                sh 'mvn --version'
                sh 'mvn clean package'  // Compile et package le projet
            }
        }

        // Étape 3 : Exécuter les tests
        stage('Test') {
            steps {
                sh 'mvn test'  // Lance les tests
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'  // Publish test results
                }
            }
        }

        // Étape 4 : Déploiement (exemple : copier le JAR sur un serveur)
        stage('Deploy') {
            steps {
                sh 'echo "Déploiement en cours..."'
                // Exemple : Copie via SSH ou déploiement Docker
                // sh 'scp target/*.jar user@server:/path/'
            }
        }

    }

    // Actions post-build
    post {
        success {
            echo 'Pipeline réussi ! ✅'
        }
        failure {
            echo 'Pipeline échoué ! ❌'
        }
    }
}