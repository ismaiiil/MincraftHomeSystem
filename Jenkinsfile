pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                // Checkout your Git repository
                git 'https://github.com/ismaiiil/MincraftHomeSystem'
                bat 'gradlew.bat clean build'
            }
        }

        stage('ShadowJar') {
            steps {
                bat 'gradlew.bat clean shadowJar'
            }
        }

        stage('moveToDockerVolume') {
            steps {
                bat '''
                mkdir .\\docker_files\\data\\plugins
                copy .\\build\\libs\\HomeSystem.jar .\\docker_files\\data\\plugins\\
                '''
            }
        }
    }


}