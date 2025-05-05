import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    `kotlin-dsl`
}

group = "net.craftsupport.anticrasher"
version = libs.versions.plugin.version.get()

allprojects {
    repositories {
        mavenCentral()
    }
}