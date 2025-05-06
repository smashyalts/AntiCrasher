package net.craftsupport.anticrasher.paper.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderProcessor {

    public static String processPlaceholders(String message, Player player) {
        return PlaceholderAPI.setPlaceholders(player, message);
    }
}
