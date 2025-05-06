package net.craftsupport.anticrasher.common.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.YamlConfigurations;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;

import java.io.File;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Configuration
@SuppressWarnings("FieldMayBeFinal")
public class Config {
    private static Config instance;

    // -- CONFIG -- //

    @Getter
    @Configuration
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Logging {
        @Comment("Log exploit detections to console.")
        private boolean console = true;
        @Comment("Log exploit detections to a file in the /logs directory.")
        private boolean file = true;

        private Chat chat = new Chat();

        @Configuration
        @Getter
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class Chat {
            @Comment("Log exploit detections to users with the `anticrasher.alerts` permission")
            private boolean chatAlerts = true;

            @Comment("""
                
                Alerts format for chat alerts. (MiniMessage only)
                Available placeholders:
                <player_name> - The name of the player who triggered the alert
                <exploit_name> - The name of the exploit that was triggered (e.g. Book, PluginMessage, etc.)
                <exploit_type> - The type of the check that was triggered (e.g. A, B, C, D)
                <exploit_description> - The description of the exploit that was triggered""")
            private String alertsFormat = "<blue><bold>AntiCrasher<reset> <dark_grey>» <red><player_name> <grey>failed <red><exploit_name> <gray>[<red>Type <exploit_type></red>]";

            // adding spacing for configs
            @Comment("""
                    
                    Format for the hover text of the chat alert.
                    Same placeholders as above.""")
            private List<String> alertsHoverFormat = List.of(
                    "<gray>Exploit: <red><exploit_name>",
                    "<gray>Exploit Description: <red><exploit_description>"
            );
        }
    }

    private Logging logging = new Logging();

    @Comment("""
            
            List of commands to run if someone attempts to abuse an exploit.
            Supports PlaceholderAPI placeholders, %player_name% is a built-in placeholder into AntiCrasher.
            Do not include the slash at thew beginning of the command.
            Supports MiniMessage colour formatting - serialized into legacy section signs (hover etc not functional).""")
    private List<String> punishments = List.of(
            "kick %player_name% <dark_red>Kicked for Exploiting."
    );

    public static Config i() {
        if (instance != null) {
            return instance;
        }

        return instance = YamlConfigurations.update(
                AntiCrasherAPI.getInstance().getPlatform().getConfigDirectory().resolve("config.yml"),
                Config.class
        );
    }

    public static void reload() {
        instance = YamlConfigurations.update(
                AntiCrasherAPI.getInstance().getPlatform().getConfigDirectory().resolve("config.yml"),
                Config.class
        );
    }
}
