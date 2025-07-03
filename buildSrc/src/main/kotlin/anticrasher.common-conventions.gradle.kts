import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    `java-library`
    id("com.gradleup.shadow")
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
        name = "finallyADecentReleases"
        url = uri("https://repo.preva1l.info/releases")
    }
    maven {
        name = "Sonatype Snapshots"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly(libs.gson)
    compileOnly(libs.packetevents.api)
    implementation(libs.trashcan.common)
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
        options.fork()
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
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