package net.skullian.anticrasher.velocity.library;

import com.alessiodp.libby.Library;
import com.alessiodp.libby.VelocityLibraryManager;
import net.craftsupport.anticrasher.common.library.LibraryJSONParser;
import net.craftsupport.anticrasher.common.library.object.LibraryConfig;
import net.skullian.anticrasher.velocity.AntiCrasher;

public class LibraryLoader {

    private final VelocityLibraryManager libraryLoader;
    private final LibraryJSONParser jsonParser = new LibraryJSONParser();

    public LibraryLoader() {
        this.libraryLoader = new VelocityLibraryManager(
                AntiCrasher.getInstance(),
                AntiCrasher.getInstance().logger,
                AntiCrasher.getInstance().getConfigDirectory(),
                AntiCrasher.getInstance().server.getPluginManager()
        );
    }

    public void load() {
        libraryLoader.addMavenCentral();
        LibraryConfig libraryConfig = jsonParser.getLibraryConfig();

        libraryConfig.getRepositories().forEach(libraryLoader::addRepository);
        libraryConfig.getArtifacts().forEach( library -> {
            String groupId = library.getGroupId().replace(".", "{}");
            Library lib = Library.builder()
                    .groupId(groupId)
                    .artifactId(library.getArtifactId())
                    .resolveTransitiveDependencies(library.isParseTransitive())
                    .version(library.getVersion())
                    .build();

            libraryLoader.loadLibrary(lib);
        });
    }

}
