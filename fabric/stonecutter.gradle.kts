plugins {
    id("dev.kikugie.stonecutter")
    id("fabric-loom") version "1.17-SNAPSHOT" apply false
}
stonecutter active "1.21.11"

stonecutter registerChiseled tasks.register("chiseledBuildAndCollect", stonecutter.chiseled) {
    group = "project"
    ofTask("buildAndCollect")
}

stonecutter registerChiseled tasks.register("chiseledPublish", stonecutter.chiseled) {
    group = "project"
    ofTask("buildAndCollect")
    ofTask("modrinth")
}