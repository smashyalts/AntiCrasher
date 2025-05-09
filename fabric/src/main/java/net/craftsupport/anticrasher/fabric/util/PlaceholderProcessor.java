package net.craftsupport.anticrasher.fabric.util;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PlaceholderProcessor {

    public static String processPlaceholders(String message, ServerCommandSource player) {
        return Placeholders.parseText(Text.literal(message), PlaceholderContext.of(player)).getString();
    }
}
