package net.craftsupport.anticrasher

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.logging.Logger

// Many thanks to https://discuss.gradle.org/t/using-version-catalog-from-buildsrc-buildlogic-xyz-common-conventions-scripts/48534/10
// This lets us use the version catalog in buildSrc conventions
class VersionCatalog(project: Project) {

    private val versionCatalog = project.extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    private val logger: Logger = project.logger

    fun dependencyFromCatalog(libName: String, dependency: (Any) -> Dependency?) {
        versionCatalog.findLibrary(libName).ifPresentOrElse(
            { dependency(it) },
            { logger.warn("Library '$libName' not found in version catalog.") }
        )
    }

    fun bundleFromCatalog(bundleName: String, dependency: (Any) -> Dependency?) {
        versionCatalog.findBundle(bundleName).ifPresentOrElse(
            { dependency(it) },
            { logger.warn("Bundle '$bundleName' not found in version catalog.") }
        )
    }
}

fun versionCatalog(project: Project, libName: String, dependency: (Any) -> Dependency?) =
    VersionCatalog(project).dependencyFromCatalog(libName, dependency)
fun bundleCatalog(project: Project, libName: String, dependency: (Any) -> Dependency?) =
    VersionCatalog(project).bundleFromCatalog(libName, dependency)