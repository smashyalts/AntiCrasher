plugins {
    anticrasher.`common-conventions`
    alias(libs.plugins.runvelocity)
    alias(libs.plugins.minotaur)
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

    api(project(":common"))
    implementation(libs.bundles.cloud.velocity)
    implementation(libs.packetevents.velocity)
}
