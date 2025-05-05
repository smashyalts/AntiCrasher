import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.craftsupport.anticrasher.bundleCatalog
import net.craftsupport.anticrasher.versionCatalog

plugins {
    `java-library`
    id("com.gradleup.shadow")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
    maven {
        name = "codemc-releases"
        url = uri("https://repo.codemc.io/repository/maven-releases/")
    }
    maven {
        name = "finallyADecentReleases"
        url = uri("https://repo.preva1l.info/releases")
    }
}

dependencies {
    versionCatalog(project, "gson", ::compileOnly)
    versionCatalog(project, "packetevents-api", ::compileOnly)
    versionCatalog(project, "trashcan-common", ::implementation)
    bundleCatalog(project, "adventure", ::api)

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.fork()
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
    }

    processResources {
        val version = rootProject.version.toString()
        filesMatching(listOf("**/*.json", "**/*.yml")) {
            expand("version" to version)
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