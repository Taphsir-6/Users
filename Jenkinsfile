pipeline {
    agent any  // Exécute sur n'importe quel nœud Jenkins

    stages {
        // Étape 1 : Récupérer le code depuis GitHub
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Taphsir-6/Users.git'
            }
        }

        // Étape 2 : Build avec Maven
        stage('Build') {
            steps {
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