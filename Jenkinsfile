pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Checkout your Git repository
                git 'https://github.com/ismaiiil/MincraftHomeSystem'
                script {
                    bat 'gradlew.bat clean build'
//                     if (isUnix()) {
//                             sh 'chmod +x gradlew'
//                             sh './gradlew clean build'
//                         } else {
//
//                         }
//                     }
            }
        }
    }
}