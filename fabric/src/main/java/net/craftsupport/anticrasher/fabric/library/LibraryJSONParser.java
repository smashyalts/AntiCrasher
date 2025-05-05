package net.craftsupport.anticrasher.fabric.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.craftsupport.anticrasher.fabric.library.object.LibraryConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
