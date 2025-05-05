import net.craftsupport.anticrasher.bundleCatalog
import net.craftsupport.anticrasher.versionCatalog

plugins {
    `java-library`
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
    versionCatalog(project, "reflections", ::implementation)
    bundleCatalog(project, "adventure", ::api)
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.fork()
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
    }
}