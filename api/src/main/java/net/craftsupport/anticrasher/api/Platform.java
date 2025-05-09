package net.craftsupport.anticrasher.api;

import info.preva1l.trashcan.Version;
import net.craftsupport.anticrasher.api.user.User;

import java.nio.file.Path;

/**
 * This is the root interface for the AntiCrasher platform.
 * It is implemented in all platform main classes (JavaPlugin for spigot, ModInitializer for fabric, etc)
 */
public interface Platform {

    /**
     * @return The config directory as a {@link Path}
     */
    Path getConfigDirectory();

    /**
     *
     * @param pluginName The plugin (or mod) name to check.
     * @return Whether the plugin/mod is enabled or not.
     */
    boolean isPluginEnabled(String pluginName);

    /**
     * @return The current version of the platform.
     */
    Version getCurrentVersion();

    /**
     * Run a task on the main thread.
     * @param runnable The task to run.
     * @param delay The delay in ticks to wait before running the task.
     */
    void runLater(Runnable runnable, long delay);

    /**
     * @return The current user of the console.
     */
    User getConsoleUser();

    /**
     * This is used primarily in update checking.
     * @return either `plugin` or `mod`.
     */
    String getPlatformType();
}
