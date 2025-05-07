plugins {
    `maven-publish`
    anticrasher.`common-conventions`
}

tasks.register<Jar>("generateJdoc") {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

publishing {
    repositories.configureRepository()

    publications {
        register<MavenPublication>("mavenJava") {
            from(components["shadow"])
            artifact(tasks["generateJdoc"])
        }
    }
}

fun RepositoryHandler.configureRepository() {
    val user: String? = properties["anticrasher_username"]?.toString() ?: System.getenv("anticrasher_username")
    val pw: String? = properties["anticrasher_password"]?.toString() ?: System.getenv("anticrasher_password")

    if (user != null && pw != null) {
        maven("https://repo.skullian.com/releases/") {
            name = "AntiCrasher"
            credentials {
                username = user
                password = pw
            }
        }

        return
    }

    println("Using AntiCrasher repository without credentials.")
    maven("https://repo.skullian.com/releases/") {
        name = "AntiCrasher"
    }
}