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
    val user: String? = properties["repository_username"]?.toString() ?: System.getenv("repository_username")
    val pw: String? = properties["repository_password"]?.toString() ?: System.getenv("repository_password")

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