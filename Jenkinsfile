pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
        ECR_REPO = 'tela-staff-transfer-api'
        LOCAL_SERVER = 'sadmin@192.168.3.155'
        DOCKER_IMAGE = "535693658748.dkr.ecr.ap-south-1.amazonaws.com/tela-staff-transfer-api:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_TOKEN')]) {
                        sh 'rm -rf tela-staff-transfer-api' 
                        sh 'git clone https://${GIT_USERNAME}:${GIT_TOKEN}@github.com/vijaynathji027/tela-staff-transfer-api.git'
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${ECR_REPO}:latest", ".")
                }
            }
        }
        
        stage('Push to AWS ECR') {
            steps {
                 withAWS(credentials: 'School-erp', region: 'ap-south-1') {
                     sh '''
                         aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 535693658748.dkr.ecr.ap-south-1.amazonaws.com
                         docker tag tela-staff-transfer-api:latest 535693658748.dkr.ecr.ap-south-1.amazonaws.com/tela-staff-transfer-api:latest
                         docker push 535693658748.dkr.ecr.ap-south-1.amazonaws.com/tela-staff-transfer-api:latest
                         docker rmi 535693658748.dkr.ecr.ap-south-1.amazonaws.com/tela-staff-transfer-api:latest
                     '''
                 }
             }
        }

        stage('Deploy to Local Server') {
            steps {
                script {
                    sh "ssh -o StrictHostKeyChecking=no ${LOCAL_SERVER} 'aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 535693658748.dkr.ecr.ap-south-1.amazonaws.com'"
                    sh "ssh ${LOCAL_SERVER} 'docker pull 535693658748.dkr.ecr.ap-south-1.amazonaws.com/tela-staff-transfer-api:latest'"
                    sh "ssh ${LOCAL_SERVER} 'docker rm -f tela-staff-transfer-api-container || true'"
                    sh "ssh ${LOCAL_SERVER} 'docker run -d -p 1409:1409 --name tela-staff-transfer-api-container --restart unless-stopped 535693658748.dkr.ecr.ap-south-1.amazonaws.com/tela-staff-transfer-api:latest'"
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed.'
        }
    }
}