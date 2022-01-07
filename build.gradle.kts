/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    java
    `maven-publish`
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.3")
    compileOnly("me.clip:placeholderapi:2.11.1")
    compileOnly("com.github.Zrips:Jobs:4.17.2")

    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.1")


}

group = "com.github.yannicklamprecht.modernchat"
version = "1.0-SNAPSHOT"
description = "ModernChat"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}


tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    runServer {
        minecraftVersion("1.18.1")
    }
}
