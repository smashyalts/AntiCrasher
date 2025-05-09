plugins {
    anticrasher.`common-conventions`
    alias(libs.plugins.runpaper)
    alias(libs.plugins.minotaur)
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
    compileOnly(libs.bundles.cloud.paper)

    api(project(":common"))
    implementation(libs.packetevents.spigot)
    implementation(libs.multilib)
}

tasks {
    runServer {
        minecraftVersion("1.21.1")

        downloadPlugins {
            modrinth("LuckPerms", "v5.4.145-bukkit")
        }
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("anticrasher")
    versionNumber.set("v${rootProject.version}-plugin")
    versionName.set("AntiCrasher Bukkit ${rootProject.version}")
    versionType.set("release")
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll("1.17", "1.17.1", "1.18", "1.18.1", "1.18.2", "1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4", "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5", "1.20.6", "1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4")
    loaders.addAll(listOf("folia", "paper", "spigot", "bukkit", "purpur"))
    changelog.set(rootProject.rootDir.resolve(".github/CHANGELOG.md").readText())
}