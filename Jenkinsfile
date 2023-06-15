pipeline {
    agent any

    stages {

        stage('BuildAndTest') {
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

        stage('runDocker') {
            steps {
                script {
                    // Check if Docker container is running
                    def containerStatus = bat(returnStdout: true, script: 'docker inspect -f "{{.State.Running}}" mcserver')

                    if (containerStatus.contains('true')) {
                        // Docker container is already running, stop it first
                        echo 'docker is already running stopping'
                        bat 'docker stop mcserver'
                    }

                    // Change working directory to docker_files
                    dir('docker_files') {
                        // Start Docker container
                        bat 'docker-compose up -d'
                    }

                    def maxRetries = 10
                    def retryCount = 0

                    containerStatus = bat(returnStdout: true, script: 'docker inspect -f "{{.State.Running}}" mcserver')

                    while (retryCount < maxRetries) {
                        if(!containerStatus.contains('true')){
                            retryCount++
                            sleep(5) // Wait for 5 seconds before retrying
                        }else{
                            break;
                        }
                    }

                    // Verify container status
                    if (containerStatus.contains('true')) {
                        echo 'Docker container is running.'
                    } else {
                        error 'Failed to start Docker container.'
                    }
                }
            }
        }

    }


}