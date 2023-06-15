import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.process.internal.ExecException

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("idea")
}

group = "com.ismail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}

java{
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
