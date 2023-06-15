plugins {
    id("java")
    id("idea")
}

group = "com.ismail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java{
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}