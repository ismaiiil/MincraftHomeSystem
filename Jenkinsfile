pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Checkout your Git repository
                git clone 'https://github.com/ismaiiil/MincraftHomeSystem'
                sh 'chmod +x gradlew'
                // Build the Gradle project
                sh './gradlew clean build'
            }
        }

    }
}