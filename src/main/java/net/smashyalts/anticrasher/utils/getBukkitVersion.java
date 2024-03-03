package net.smashyalts.anticrasher.utils;

import net.smashyalts.anticrasher.AntiCrasher;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class getBukkitVersion {
    // Error message when plugin doesn't support bukkit

    public static boolean isMC120(){
        return Bukkit.getBukkitVersion().contains("1.20");
    }
    public static boolean isMC119(){
        return Bukkit.getBukkitVersion().contains("1.19");
    }
    public static boolean isMC118(){
        return Bukkit.getBukkitVersion().contains("1.18");
    }

    public static boolean isMC117(){
        return Bukkit.getBukkitVersion().contains("1.17");
    }

    public static boolean isMC116(){
        return Bukkit.getBukkitVersion().contains("1.16");
    }
    public static boolean isMC115(){
        return Bukkit.getBukkitVersion().contains("1.15");
    }

    public static boolean isMC114(){
        return Bukkit.getBukkitVersion().contains("1.14");
    }

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
    public boolean isMC120OrNewer(){
        if (isMC120())
            return true;
        else if (isMC119()||isMC118()||isMC117()||isMC116()||isMC115()||isMC114()||isMC113()||isMC112()||isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC119OrNewer(){
        if (isMC119())
            return true;
        else if (isMC118()||isMC117()||isMC116()||isMC115()||isMC114()||isMC113()||isMC112()||isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC118OrNewer(){
        if (isMC118())
            return true;
        else if (isMC117()||isMC116()||isMC115()||isMC114()||isMC113()||isMC112()||isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC117OrNewer(){
        if (isMC117())
            return true;
        else if (isMC116()||isMC115()||isMC114()||isMC113()||isMC112()||isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC116OrNewer(){
        if (isMC116())
            return true;
        else if (isMC115()||isMC114()||isMC113()||isMC112()||isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC115OrNewer(){
        if (isMC115())
            return true;
        else if (isMC114()||isMC113()||isMC112()||isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC114OrNewer(){
        if (isMC114())
            return true;
        else if (isMC113()||isMC112()||isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public static boolean isMC113OrNewer(){
        if (isMC113())
            return true;
        else if (isMC112()||isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC112OrNewer(){
        if (isMC112()) {
            return true;
        } else if (isMC111()||isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC111OrNewer(){
        if (isMC111()) {
            return true;
        } else if (isMC110()||isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC110OrNewer(){
        if (isMC110()) {
            return true;
        } else if (isMC19()||isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }

    public boolean isMC19OrNewer(){
        if (isMC19()) {
            return true;
        } else if (isMC18()||isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }

    public boolean isMC18OrNewer(){
        if (isMC18()) {
            return true;
        } else if (isMC17()||isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC17OrNewer(){
        if (isMC17()) {
            return true;
        } else if (isMC16()||isMC15())
            return false;
        return true;
    }
    public boolean isMC16OrNewer(){
        if (isMC16()) {
            return true;
        } else if (isMC15())
            return false;
        return true;
    }
}

