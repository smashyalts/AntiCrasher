import gradle.kotlin.dsl.accessors._983bb327668533c52660ac523168b406.annotationProcessor
import gradle.kotlin.dsl.accessors._983bb327668533c52660ac523168b406.compileOnly
import gradle.kotlin.dsl.accessors._983bb327668533c52660ac523168b406.implementation
import net.craftsupport.anticrasher.versionCatalog
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    `java-library`
}

group = rootProject.group
version = rootProject.version

configurations {
    create("zipConfig")
}

val libs = the<LibrariesForLibs>()

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
    maven {
        name = "Sonatype Snapshots"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
    maven {
        name = "Nucleoid"
        url = uri("https://maven.nucleoid.xyz/")
    }
    maven {
        name = "CodeMC Snapshots"
        url = uri("https://repo.codemc.io/repository/maven-snapshots/")
    }
}

dependencies {
    compileOnly(libs.trashcan.common)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.fork()
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
    }
}

// god, I hate this, but it's the only way to get Fabric to cooperate w/ dependencies
tasks.jar {
    dependsOn(":common:classes", ":api:classes")
    from(project(":common").sourceSets["main"].output)
    from(project(":api").sourceSets["main"].output)

    mustRunAfter(":common:jar", ":api:jar")

    from({
        configurations.runtimeClasspath.get().filter { it.name.contains("libby") }.map { zipTree(it) }
    }) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    }

    from({
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        configurations.getByName("zipConfig").map { if (it.isDirectory) it else zipTree(it) }
    }) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA", "javax/**", "jakarta/**", "io/**", "com/**", "jetty-dir.css", "org/**")
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}