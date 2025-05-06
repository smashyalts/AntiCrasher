package net.craftsupport.anticrasher.fabric.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
//? if <1.19.2 {
/*import eu.pb4.placeholders.PlaceholderAPI;
*///?} elif >=1.19.2 {
import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
//?}
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PlaceholderProcessor {

    public static String processPlaceholders(String message, ServerCommandSource player) {
        //? if <1.19.2 {
        /*try {
            return PlaceholderAPI.parseText(Text.of(message), player.getPlayer()).getString();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return message;
        }*///?} elif >=1.19.2 {
        return Placeholders.parseText(Text.literal(message), PlaceholderContext.of(player)).getString();
        //?}
    }
}
