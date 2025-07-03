plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.plugin.shadow)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
