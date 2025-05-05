package net.craftsupport.anticrasher.fabric.library;

import com.alessiodp.libby.FabricLibraryManager;
import com.alessiodp.libby.Library;
import com.alessiodp.libby.logging.LogLevel;
import net.craftsupport.anticrasher.fabric.AntiCrasher;
import net.craftsupport.anticrasher.fabric.library.object.LibraryConfig;

public class LibraryLoader {

    private FabricLibraryManager libraryLoader;
    private final LibraryJSONParser jsonParser = new LibraryJSONParser();

    public LibraryLoader() {
        this.libraryLoader = new FabricLibraryManager(
                "anticrasher",
                AntiCrasher.instance.logger,
                "libs"
        );

        this.libraryLoader.setLogLevel(LogLevel.DEBUG);
    }

    public void load() {
        libraryLoader.addMavenCentral();
        LibraryConfig libraryConfig = jsonParser.getLibraryConfig();

        libraryConfig.repositories().forEach(repository -> libraryLoader.addRepository(repository));
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
