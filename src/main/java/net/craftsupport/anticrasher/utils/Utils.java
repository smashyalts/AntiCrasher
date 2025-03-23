package net.craftsupport.anticrasher.utils;

import net.craftsupport.anticrasher.AntiCrasher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.bukkit.Bukkit.getLogger;

public class Utils {

    private final AntiCrasher plugin;

    public boolean logToFile;
    public boolean logAttempts;
    public boolean punishOnAttempt;
    public String punishCommand;
    public String dataFolder;

    public Utils(AntiCrasher plugin) {
        this.plugin = plugin;

        // Reduce code duplication
        reloadConfig();
    }

   public void reloadConfig() {
       this.logToFile = plugin.getConfig().getBoolean("log-to-file");
       this.logAttempts = plugin.getConfig().getBoolean("log-attempts");
       this.punishOnAttempt = plugin.getConfig().getBoolean("punish-on-attempt");
       this.punishCommand = plugin.getConfig().getString("punish-command");
       this.dataFolder = plugin.getDataFolder().getPath();
   }

    public void log(String message) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFolder + "/LOGS", true));

            String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            writer.write(timestamp + " - " + message);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            getLogger().info("An error occurred: " + e.getMessage());
        }
    }
}
