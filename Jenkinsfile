pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Checkout your Git repository
                git 'https://github.com/ismaiiil/MincraftHomeSystem'
                script {
                    if (isUnix()) {
                            sh 'chmod +x gradlew'
                            sh './gradlew clean build'
                        } else {
                            bat 'gradlew.bat clean build'
                        }
                    }
            }
        }
    }
}