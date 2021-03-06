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
    maven("https://oss.sonatype.org/content/groups/public/") {
            name = "sonatype-oss-snapshots"
    }
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")
}

dependencies {
    implementation(libs.jodatime)

    compileOnly(libs.paperapi)
    compileOnly(libs.luckperms)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.jobs){
        isTransitive = false
    }

    implementation(libs.bundles.jackson)

    testImplementation(libs.bundles.junit)


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
        minecraftVersion("1.18.2")
    }
    test {
        useJUnitPlatform()
    }
}
