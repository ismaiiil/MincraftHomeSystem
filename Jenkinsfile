pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Checkout your Git repository
                git 'https://github.com/ismaiiil/MincraftHomeSystem'

                // Build the Gradle project
                sh './gradlew clean build'
            }
        }

    }
}