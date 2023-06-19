@file:Suppress("VulnerableLibrariesLocal")

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
    maven("https://repo.codemc.org/repository/maven-public")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    testImplementation(platform("org.junit:junit-bom:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":home-system-common"))
    implementation(project(":home-system-api"))
    implementation("dev.jorel:commandapi-bukkit-shade:9.0.3");
    compileOnly("dev.jorel:commandapi-annotations:9.0.3")
    annotationProcessor("dev.jorel:commandapi-annotations:9.0.3")
    implementation("commons-io:commons-io:2.13.0")
//    implementation("de.tr7zw:item-nbt-api:2.11.2");
}


tasks {
    named<ShadowJar>("shadowJar") {
        group = "_homeSystemTasks"

        archiveFileName.set("HomeSystem.jar")
        manifest {
            attributes(mapOf("Main-Class" to "com.ismail.homesystem.spigot.HomeSystemPlugin"))
        }

        relocate("org.hibernate", "com.ismail.homesystem.libs.hibernate")
        relocate("dev.jorel.commandapi", "com.ismail.homesystem.libs.commandapi")

        relocate("org.jboss", "com.ismail.homesystem.libsjboss")
        relocate("org.antlr", "com.ismail.homesystem.libs.antlr")
        relocate("org.glassfish", "com.ismail.homesystem.libs.glassfish")
        relocate("org.eclipse", "com.ismail.homesystem.libs.eclipse")
        relocate("jakarta", "com.ismail.homesystem.libs.jakarta")

        relocate("com.google", "com.ismail.homesystem.libs.google")
        relocate("google.protobuf", "com.ismail.homesystem.libs.google.protobuf")

        relocate("net.bytebuddy", "com.ismail.homesystem.libs.bytebuddy")

        relocate("com.mysql", "com.ismail.homesystem.libs.mysql")
        relocate("com.fasterxml", "com.ismail.homesystem.libs.fasterxml")
        relocate("com.sun", "com.ismail.homesystem.libs.sun")

//        relocate("de.tr7zw.changeme.nbtapi", "com.ismail.homesystem.libs.nbtapi")
//        relocate("de.tr7zw.annotations", "com.ismail.homesystem.libs.annotations")
    }
}

tasks.test {
    useJUnitPlatform()
}

java{
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}