import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    `java-library`
    id("com.gradleup.shadow")
    id("net.skullian.zenith")
}

group = rootProject.group
version = rootProject.version

val libs = the<LibrariesForLibs>()

repositories {
    mavenCentral()
    maven {
        name = "codemc-snapshots"
        url = uri("https://repo.codemc.io/repository/maven-snapshots/")
    }
    maven {
        name = "Sonatype Snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    }
    maven {
        name = "AlessioDP Snapshots"
        url = uri("https://repo.alessiodp.com/snapshots")
    }
}

dependencies {
    compileOnly(libs.gson)
    compileOnly(libs.packetevents.api)
    api(libs.bundles.adventure)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

// TODO - HACKY WORKAROUND! find a way to not make gradle make me want to kill myself
fun variables(): Map<String, String> = mapOf(
    "version" to rootProject.version.toString(),
    "adventureVersion" to libs.versions.adventure.version.get(),
    "cloudVersion" to libs.versions.cloud.version.get(),
    "reflectionsVersion" to libs.versions.reflections.version.get(),
)

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.isFork = true
        options.encoding = Charsets.UTF_8.name()
        options.release = 25
    }

    withType<ProcessResources> {
        val tokenMap = variables()
        filesMatching(listOf("**/*.json", "**/*.yml")) {
            expand(tokenMap)
        }
    }

    withType<ShadowJar> {
        archiveClassifier.set("")
        exclude(
            "**/*.kotlin_metadata",
            "**/*.kotlin_builtins",
            "META-INF/",
            "kotlin/**",
        )

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        relocate("com.github.retrooper.packetevents", "net.craftsupport.anticrasher.packetevents.api")
        relocate("io.github.retrooper.packetevents", "net.craftsupport.anticrasher.packetevents.impl")
        relocate("org.bstats", "net.craftsupport.anticrasher.bstats")

        archiveFileName.set("${rootProject.name}-${project.name}-v${rootProject.version}.jar")
        destinationDirectory.set(rootProject.rootDir.resolve("./libs"))
    }
}

tasks.getByName("build")
    .dependsOn("shadowJar")