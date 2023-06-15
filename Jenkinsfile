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

        stage('moveToDockerVOlume') {
            steps {
                bat 'dir'
                bat 'copy .\\build\\libs\\HomeSystem.jar .\\docker_files\\data\\plugins\\'
            }
        }
    }


}