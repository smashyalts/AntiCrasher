import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    anticrasher.`common-conventions`
    alias(libs.plugins.runpaper)
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        name = "placeholderapi"
        url = uri("https://repo.extendedclip.com/releases/")
    }

    maven {
        name = "clojars"
        url = uri("https://repo.clojars.org/")
    }
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.placeholderapi)

    api(project(":common"))
    implementation(libs.bundles.cloud.paper)
    implementation(libs.packetevents.spigot)
    implementation(libs.multilib)
}

tasks {
    runServer {
        minecraftVersion("1.21.1")
    }
}