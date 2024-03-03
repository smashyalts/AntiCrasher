package net.smashyalts.anticrasher.utils;

import org.bukkit.Bukkit;
public class getBukkitVersion {
    public static boolean isMC113(){
        return Bukkit.getBukkitVersion().contains("1.13");
    }

    public static boolean isMC112(){
        return Bukkit.getBukkitVersion().contains("1.12");
    }

    public static boolean isMC111(){
        return Bukkit.getBukkitVersion().contains("1.11");
    }

    public static boolean isMC110(){
        return Bukkit.getBukkitVersion().contains("1.10");
    }

    public static boolean isMC19(){
        return Bukkit.getBukkitVersion().contains("1.9");
    }

    public static boolean isMC18(){
        return Bukkit.getBukkitVersion().contains("1.8");
    }

    public static boolean isMC17(){
        return Bukkit.getBukkitVersion().contains("1.7");
    }
    public static boolean isMC16(){
        return Bukkit.getBukkitVersion().contains("1.6");
    }
    public static boolean isMC15(){
        return Bukkit.getBukkitVersion().contains("1.5");
    }
    public static boolean isMC113OrNewer(){
        if (isMC113())
            return true;
        else if (isMC112()||isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
}

