pipeline{
    agent any
    tools{
        jdk 'JDK17'  
        maven 'Maven-3.9.9'     
    }
    environment {
        JAVA_HOME = tool name: 'JDK17', type: 'hudson.model.JDK'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Taphsir-6/Users']])
                sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t taphsir/devops-integration .'
                }
            }
        }
        stage('Push image to hub'){
            steps{
                script{
                     withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                     sh'docker login -u taphsir -p ${dockerhubpwd}'
}
                     sh 'docker push taphsir/devops-integration'
                }
            }
        }
    }
}