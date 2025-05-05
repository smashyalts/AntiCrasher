import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    `kotlin-dsl`
    alias(libs.plugins.shadow)
}

group = "net.craftsupport.anticrasher"
version = libs.versions.plugin.version.get()

allprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.36")
        annotationProcessor("org.projectlombok:lombok:1.18.36")
    }
}

subprojects {
    apply(plugin = "com.gradleup.shadow")

    sourceSets {
        main {
            java {
                setSrcDirs(listOf("src/main/java"))
            }
        }
    }

    tasks.withType<ShadowJar> {
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

    tasks.getByName("build")
        .dependsOn("shadowJar")

    tasks.processResources {
        val version = rootProject.version.toString()
        filesMatching(listOf("**/*.json", "**/*.yml")) {
            expand("version" to version)
        }
    }
}