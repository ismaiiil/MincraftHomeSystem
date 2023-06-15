import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.process.internal.ExecException

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.ismail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
}

//JAR building
tasks.named<ShadowJar>("shadowJar") {
    group = "_homeSystemTasks"
    manifest.attributes["Main-Class"] = "com.ismail.homesystem.HomeSystemMain" // Replace with your main class name
    archiveFileName.set("HomeSystem.jar") // Replace with your desired JAR file name
}

tasks.register("jarToDocker") {
    group = "_homeSystemTasks"
    doLast {
        val outputDir = File("docker_files/data/plugins") // Replace with the actual path to your PaperMC server's plugins folder
        val jarFile = tasks.named<ShadowJar>("shadowJar").get().archiveFile.get().asFile

        outputDir.mkdirs()
        jarFile.copyTo(outputDir.resolve(jarFile.name), true)
        println("Jar file copied to server plugins folder.")
    }
}

//docker state management
tasks.register("dockerRestart") {
    group = "_homeSystemTasks"
    description = "Stop the Docker container"
    doLast {
        project.exec {
            commandLine = listOf("docker", "restart", "mcserver")
            // Configure the standard output and error handling
            standardOutput = System.out // Redirect the output to the console
            errorOutput = System.err
        }
    }
}

tasks.register("dockerComposeUp") {
    group = "_homeSystemTasks"
    description = "Start the Docker containers using docker-compose up"

    doLast {
        project.exec {
            commandLine = listOf("docker-compose", "up")
            // You can also specify the working directory if needed
            workingDir = file("/docker_files")

            // Configure the standard output and error handling
            standardOutput = System.out // Redirect the output to the console
            errorOutput = System.err
        }
    }
}


tasks.register("dockerStop") {
    group = "_homeSystemTasks"
    description = "Stop the Docker container"
    doLast {
        project.exec {
            commandLine = listOf("docker", "stop", "mcserver")
            // Configure the standard output and error handling
            standardOutput = System.out // Redirect the output to the console
            errorOutput = System.err
        }
    }
}


java{
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}