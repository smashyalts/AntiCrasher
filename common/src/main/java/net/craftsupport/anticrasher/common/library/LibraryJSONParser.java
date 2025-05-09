package net.craftsupport.anticrasher.common.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.craftsupport.anticrasher.common.library.object.LibraryConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// Only used for Fabric and Velocity
public class LibraryJSONParser {

    private final Gson gson = new GsonBuilder().create();

    public LibraryConfig getLibraryConfig() {
        try (InputStream inputStream = getClass().getResourceAsStream("/libs.json")) {
            InputStreamReader reader = new InputStreamReader(inputStream);

            return gson.fromJson(reader, LibraryConfig.class);
        } catch (IOException error) {
            throw new RuntimeException(error);
        }
    }
}
