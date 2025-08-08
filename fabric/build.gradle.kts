plugins {
    anticrasher.`fabric-conventions`
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.minotaur)
}

class ModDependencies {
    operator fun get(name: String) = property("deps.$name").toString()
}

val mcVersion = stonecutter.current.version
val deps = ModDependencies()
val modVersion = rootProject.version.toString()

base {
    archivesName.set("AntiCrasher-fabric-$mcVersion")
}

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings("net.fabricmc:yarn:$mcVersion+build.${deps["yarn_build"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${deps["fabric_loader"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${deps["fabric_api"]}")

    implementation(libs.libby.fabric)
    include(libs.libby.fabric)

    val adventurePlatform = "net.kyori:adventure-platform-fabric:${deps["adventure_platform"]}"
    modImplementation(adventurePlatform)
    include(adventurePlatform)

    modImplementation("eu.pb4:placeholder-api:${deps["papi"]}")

    compileOnly(libs.packetevents.fabric)

    modImplementation(libs.cloud.fabric)
    include(libs.cloud.fabric)
    compileOnly(libs.cloud.annotations)

    modImplementation(libs.fabric.permissions.api)
    include(libs.fabric.permissions.api)

    api(project(":common"))
    zipConfig(project(":common"))
}

loom {
    runConfigs.all {
        ideConfigGenerated(true)
        vmArgs("-Dmixin.debug.export=true")
        runDir = "../../run"
    }
}

java {
    withSourcesJar()
    val java = JavaVersion.VERSION_21
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.processResources {
    val tokenMap = variables()
    filesMatching("**/*.json") {
        expand(tokenMap)
    }
}

tasks.register<Copy>("buildAndCollect") {
    group = "build"
    from(tasks.remapJar.get().archiveFile)
    into(rootProject.rootDir.resolve("libs/"))
    dependsOn("build")
}

fun variables(): Map<String, String> = mapOf(
    "version" to rootProject.version.toString(),
    "adventureVersion" to libs.versions.adventure.version.get(),
    "cloudVersion" to libs.versions.cloud.version.get(),
    "reflectionsVersion" to libs.versions.reflections.version.get(),
)

modrinth {
    token.set(properties["MODRINTH_TOKEN"]?.toString() ?: System.getenv("MODRINTH_TOKEN"))
    projectId.set("anticrasher")
    versionNumber.set("v${rootProject.version}-mod")
    versionName.set("AntiCrasher Fabric $mcVersion")
    versionType.set("release")
    uploadFile.set(tasks.remapJar.get().archiveFile)
    gameVersions.add(property("mod.mc_version").toString())
    loaders.add("fabric")
    changelog.set(rootProject.rootDir.resolve(".github/CHANGELOG.md").readText())

    dependencies {
        required.project("fabric-api")
    }
}