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
    gameVersions.addAll("1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4", "1.21.5", "1.21.6", "1.21.7")
    loaders.add("velocity")
    changelog.set(rootProject.rootDir.resolve(".github/CHANGELOG.md").readText())
}

tasks {
    runVelocity {
        version("3.4.0-SNAPSHOT")

        downloadPlugins {
            modrinth("LuckPerms", "v5.4.145-velocity")
        }
    }
}