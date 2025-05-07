rootProject.name = "AntiCrasher"
include(":common", ":api", ":bukkit", ":fabric", ":velocity")

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.6"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

stonecutter {
    create("fabric") {
        versions("1.21.4", "1.21.1", "1.20.4", "1.20.2", "1.20.1", "1.19.4", "1.19.2", "1.18.2")
    }
}