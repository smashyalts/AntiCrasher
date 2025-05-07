package net.craftsupport.anticrasher.api;

import net.craftsupport.anticrasher.api.event.bus.EventBus;
import net.craftsupport.anticrasher.api.user.UserManager;
import org.jetbrains.annotations.ApiStatus;

/**
 * The main API class for AntiCrasher.
 * This class provides access to the various components of the API.
 */
public abstract class AntiCrasherAPI {

    private static AntiCrasherAPI instance;

    // ---- API ---- //

    /**
     * The {@link Platform} interface is implemented on each platform.
     * For Spigot, it is implemented along with the JavaPlugin class.
     * For Fabric, it is implemented along with the ModInitializer class.
     * Etc, etc.
     *
     * @return The {@link Platform} instance.
     */
    public abstract Platform getPlatform();

    /**
     * Get the User manager.
     * This facilitates the creation and management of {@link net.craftsupport.anticrasher.api.user.User}s.
     *
     * @return The {@link UserManager} instance.
     */
    public abstract UserManager getUserManager();

    /**
     * Used to register listeners to the event bus.
     * @return The {@link EventBus} instance.
     */
    public abstract EventBus getEventBus();


    // ---- INTERNAL ---- //

    /**
     * Get the instance of SkyApi.
     * This should only be called after the instance has been set (onEnable for spigot, onInitialize for fabric).
     *
     * @return The instance of {@link AntiCrasherAPI}
     * @throws IllegalStateException If the instance has not been set yet.
     */
    public static AntiCrasherAPI getInstance() {
        if (instance == null) {
            throw new IllegalStateException("""
                    The AntiCrasher API has not been initialized yet.
                    Make sure of the following things:
                    1. AntiCrasher has loaded before your plugin / mod:
                        - For Fabric: Make sure you have `AntiCrasher` added to your `depends` or `suggests` in `fabric.mod.json`.
                        - For Spigot/Paper: Make sure you have `AntiCrasher` added to your `softdepend` or `depend` in `plugin.yml`.
                    2. You have not implemented/shaded the AntiCrasher API:
                        - For Gradle: Make sure you are using `compileOnly` instead of `implementation` in your build.gradle(.kts).
                        - For Maven: Make sure you are using `<scope>provided</scope>` instead of `<scope>compile</scope>` in your pom.xml.
                    3. You are not calling AntiCrasherAPI#getInstance() before it has loaded.
                        - For Spigot/Paper: The API instance is only set on AntiCrasher's onEnable() method. If you try access it before (e.g. onLoad()), it will fail.
                        - For Fabric: Th API instance is only set on AntiCrasher's onInitialize() method.
                    """.stripIndent());
        }

        return instance;
    }

    /**
     * Set the instance of AntiCrasherAPI.
     * This should only be called by AntiCrasher itself.
     *
     * @param apiInstance The instance of AntiCrasherAPI to set.
     */
    @ApiStatus.Internal
    public static void setInstance(AntiCrasherAPI apiInstance) {
        if (instance != null) {
            throw new IllegalStateException("""
                    The AntiCrasherAPI instance has already been set!
                    If you are seeing this, then another plugin is attempting to call setInstance, when AntiCrasher has already set it.
                    
                    This is a bug, and you should report it to any plugin that hooks into AntiCrasher.
                    """);
        }

        instance = apiInstance;
    }
}
