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

        stage('runDocker') {
            steps {
                script {
                    // Check if Docker container is running
                    def containerStatus = bat(returnStdout: true, script: 'docker inspect -f "{{.State.Running}}" mcserver')

                    if (containerStatus.trim() == 'true') {
                        // Docker container is already running, stop it first
                        bat 'docker stop mcserver'
                    }

                    // Change working directory to docker_files
                    dir('docker_files') {
                        // Start Docker container
                        bat 'docker-compose up -d'
                    }

                    // Perform health checks
                    def isContainerRunning = false
                    def maxRetries = 10
                    def retryCount = 0

                    while (!isContainerRunning && retryCount < maxRetries) {
                        try {
                            bat 'docker inspect -f "{{.State.Running}}" mcserver'
                            isContainerRunning = true
                        } catch (Exception ex) {
                            retryCount++
                            sleep(5) // Wait for 5 seconds before retrying
                        }
                    }

                    // Verify container status
                    if (isContainerRunning) {
                        echo 'Docker container is running.'
                    } else {
                        error 'Failed to start Docker container.'
                    }
                }
            }
        }

    }


}