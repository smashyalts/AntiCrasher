plugins {
    anticrasher.`fabric-conventions`
    alias(libs.plugins.fabric.loom)
}

class ModDependencies {
    operator fun get(name: String) = property("deps.$name").toString()
}

val mcVersion = stonecutter.current.version
val deps = ModDependencies()
val modVersion = rootProject.version.toString()

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings("net.fabricmc:yarn:$mcVersion+build.${deps["yarn_build"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${deps["fabric_loader"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${deps["fabric_api"]}")

    implementation(libs.libby)
    include(libs.libby)

    val adventurePlatform = "net.kyori:adventure-platform-fabric:${deps["adventure_platform"]}"
    modImplementation(adventurePlatform)
    include(adventurePlatform)

    val papi = "eu.pb4:placeholder-api:${deps["papi"]}"
    modImplementation(papi)
    include(papi)

    compileOnly(libs.packetevents.fabric)

    modImplementation(libs.cloud.fabric)
    include(libs.cloud.fabric)
    compileOnly(libs.cloud.annotations)

    api(project(":common"))
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
    val java = if (stonecutter.eval(mcVersion, ">=1.20.6")) JavaVersion.VERSION_21 else JavaVersion.VERSION_17
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.processResources {
    val tokenMap = variables()
    filesMatching("**/*.json") {
        expand(tokenMap)
    }
}

fun variables(): Map<String, String> = mapOf(
    "version" to rootProject.version.toString(),
    "adventureVersion" to libs.versions.adventure.version.get(),
    "cloudVersion" to libs.versions.cloud.version.get(),
)