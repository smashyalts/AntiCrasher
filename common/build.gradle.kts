import net.skullian.zenith.model.ZenithModules

plugins {
    anticrasher.`common-conventions`
}

zenith {
    modules(ZenithModules.CORE)
}

dependencies {
    compileOnly(libs.bundles.cloud.common)
    implementation(libs.configlib)
    api(project(":api"))
}