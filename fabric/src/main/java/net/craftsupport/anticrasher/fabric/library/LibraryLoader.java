package net.craftsupport.anticrasher.fabric.library;

import com.alessiodp.libby.FabricLibraryManager;
import com.alessiodp.libby.Library;
import com.alessiodp.libby.logging.LogLevel;
import net.craftsupport.anticrasher.common.library.LibraryJSONParser;
import net.craftsupport.anticrasher.common.library.object.LibraryConfig;
import net.craftsupport.anticrasher.fabric.AntiCrasher;

public class LibraryLoader {

    private final FabricLibraryManager libraryLoader;
    private final LibraryJSONParser jsonParser = new LibraryJSONParser();

    public LibraryLoader() {
        this.libraryLoader = new FabricLibraryManager(
                "anticrasher",
                AntiCrasher.instance.logger,
                "libs"
        );
    }

    public void load() {
        libraryLoader.addMavenCentral();
        LibraryConfig libraryConfig = jsonParser.getLibraryConfig();

        libraryConfig.repositories().forEach(libraryLoader::addRepository);
        libraryConfig.artifacts().forEach( library -> {
            String groupId = library.groupId().replace(".", "{}");
            Library lib = Library.builder()
                    .groupId(groupId)
                    .artifactId(library.artifactId())
                    .resolveTransitiveDependencies(library.parseTransitive())
                    .version(library.version())
                    .build();

            libraryLoader.loadLibrary(lib);
        });
    }
}
