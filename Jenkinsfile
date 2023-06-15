def runGradleCommand(command) {
    if (isUnix()) {
        sh "chmod +x gradlew"
        sh "./gradlew $command"
    } else {
        bat "gradlew.bat $command"
    }
}

pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                // Checkout your Git repository
                git 'https://github.com/ismaiiil/MincraftHomeSystem'
                script {
                    runGradleCommand("clean build")
                }
            }
        }

        stage('ShadowJar') {
            steps {
                runGradleCommand("clean shadowJar")
            }
        }

        stage('moveToDockerVOlume') {
            steps {
                script {
                    // Copy the JAR file to the local /data/plugins folder
                    sh 'cp $JENKINS_HOME/workspace/minecraft-plugin-ci/build/libs/HomeSystem.jar /docker_files/data/plugins/'
                }
            }
        }
    }


}