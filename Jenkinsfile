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


    }


}