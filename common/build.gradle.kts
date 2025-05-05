plugins {
    anticrasher.`common-conventions`
}

repositories {

}

dependencies {
    compileOnly(libs.bundles.cloud.common)
    implementation(libs.configlib)
    api(project(":api"))
}