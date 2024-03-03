package net.craftsupport.anticrasher.utils;

import net.craftsupport.anticrasher.AntiCrasher;

public class utils {
    private final AntiCrasher plugin;
    public boolean logtofile;
    public boolean logattempts;
    public boolean punishonattempt;
    public String punishcommand;
public String dataFolder;
    public utils(AntiCrasher plugin) {
        this.plugin = plugin;
        this.logtofile = plugin.getConfig().getBoolean("log-to-file");
        this.logattempts = plugin.getConfig().getBoolean("log-attempts");
        this.punishonattempt = plugin.getConfig().getBoolean("punish-on-attempt");
        this.punishcommand = plugin.getConfig().getString("punish-command");
        this.dataFolder = plugin.getDataFolder().getPath();
    }
   public void reloadConfig() {
        this.logtofile = plugin.getConfig().getBoolean("log-to-file");
        this.logattempts = plugin.getConfig().getBoolean("log-attempts");
        this.punishonattempt = plugin.getConfig().getBoolean("punish-on-attempt");
        this.punishcommand = plugin.getConfig().getString("punish-command");
        this.dataFolder = plugin.getDataFolder().getPath();
    }
}
