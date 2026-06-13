plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.skullian.com/releases/")
}

dependencies {
    implementation(libs.plugin.shadow)
    implementation(libs.plugin.zenith)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
