package net.craftsupport.anticrasher.common.update;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import info.preva1l.trashcan.Version;
import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

@Service
public class UpdateChecker {
    @Getter public static final UpdateChecker instance = new UpdateChecker();

    private final Version CURRENT_VERSION = AntiCrasherAPI.getInstance().getPlatform().getCurrentVersion();
    private Version LATEST_VERSION;

    @Configure
    public void check() {
        CompletableFuture.supplyAsync(this::query).thenAccept(latestVersion -> {
            LATEST_VERSION = latestVersion;
            AntiCrasherAPI.getInstance().getPlatform().runLater(() -> sendNotification(AntiCrasherAPI.getInstance().getPlatform().getConsoleUser()), 60L);
        }).exceptionally(ex -> {
            throw new RuntimeException(ex);
        });
    }

    public void sendNotification(User user) {
        if (LATEST_VERSION == null || CURRENT_VERSION.compareTo(LATEST_VERSION) >= 0) return;

        user.sendMessage("<blue><bold>AntiCrasher<reset> <dark_grey>» <yellow>New version of AntiCrasher available! <grey>Current: <yellow>%s <grey>Latest: <green>%s.".formatted(
                CURRENT_VERSION,
                LATEST_VERSION
        ));
    }

    private Version query() {
        String endpoint = "https://api.modrinth.com/v2/project/anticrasher/version";
        String platform = AntiCrasherAPI.getInstance().getPlatform().getPlatformType();

        try (InputStreamReader reader = new InputStreamReader(new URL(endpoint).openConnection().getInputStream())) {
            JsonArray versions = JsonParser.parseReader(reader).getAsJsonArray();

            for (int i = 0; i < versions.size(); i++) {
                JsonObject version = versions.get(i).getAsJsonObject();
                if (version.get("version_type").getAsString().equals("release")
                        && version.get("version_number").getAsString().contains(platform)) {
                    return Version.fromString(version.get("version_number").getAsString()
                            .replaceAll(String.format("^v|\\-%s$", platform), ""));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to check for updates.", e);
        }

        return null;
    }
}
