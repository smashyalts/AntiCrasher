import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    `java-library`
    id("net.skullian.zenith")
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
        name = "codemc-snapshots"
        url = uri("https://repo.codemc.io/repository/maven-snapshots/")
    }
    maven {
        name = "central-snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    }
    maven {
        name = "AlessioDP Snapshots"
        url = uri("https://repo.alessiodp.com/snapshots")
    }
    maven {
        name = "Nucleoid"
        url = uri("https://maven.nucleoid.xyz/")
    }
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.isFork = true
        options.encoding = Charsets.UTF_8.name()
        options.release = 25
    }
}

plugins.withId("net.skullian.zenith") {
    tasks.withType<Jar>().configureEach {
        dependsOn("generateDependencies")
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