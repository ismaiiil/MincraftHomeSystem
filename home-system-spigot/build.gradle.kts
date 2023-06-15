import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("idea")
}

group = "com.ismail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    testImplementation(platform("org.junit:junit-bom:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":home-system-common"))
}

//JAR building
tasks.named<ShadowJar>("shadowJar") {
    group = "_homeSystemTasks"
    manifest.attributes["Main-Class"] = "com.ismail.homesystem.spigot.HomeSystemPlugin" // Replace with your main class name
    archiveFileName.set("HomeSystem.jar") // Replace with your desired JAR file name
}

tasks.test {
    useJUnitPlatform()
}

java{
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}