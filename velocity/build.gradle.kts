import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers

plugins {
    anticrasher.`common-conventions`
    alias(libs.plugins.runvelocity)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.idea)
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
    compileOnly(libs.bundles.cloud.velocity)

    api(project(":common"))
    implementation(libs.packetevents.velocity)
    implementation(libs.libby.velocity)
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
    "reflectionsVersion" to libs.versions.reflections.version.get(),
)

val templateSource = file("src/main/templates")
val templateDest = layout.buildDirectory.dir("generated/sources/templates")
val generateTemplates = tasks.register<Copy>("generateTemplates") {
    val props = mapOf("version" to project.version)
    inputs.properties(props)

    from(templateSource)
    into(templateDest)
    expand(props)
}

sourceSets["main"].java.srcDir(generateTemplates.map { it.outputs })
project.idea.project?.settings?.taskTriggers?.afterSync(generateTemplates)

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("anticrasher")
    versionNumber.set("v${rootProject.version}-proxy")
    versionName.set("AntiCrasher Velocity ${rootProject.version}")
    versionType.set("release")
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll("1.21.x", "1.20.x", "1.19.x", "1.18.x", "1.17.x", "1.16.x", "1.15.x", "1.14.x", "1.13.x")
    loaders.add("velocity")
}

tasks {
    runVelocity {
        version("3.4.0-SNAPSHOT")

        downloadPlugins {
            modrinth("LuckPerms", "v5.4.145-velocity")
        }
    }
}