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


java{
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}