package net.craftsupport.anticrasher.common.util;

import lombok.experimental.UtilityClass;
import net.craftsupport.anticrasher.api.util.objects.Tuple;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.List;

@UtilityClass
public class TextUtil {

    private final MiniMessage miniMessage = MiniMessage.builder().build();

    @SafeVarargs
    public Component text(String message, Tuple<String, Object>... args) {
        TagResolver[] resolvers = new TagResolver[args.length];

        for (int i = 0; i < args.length; i++) {
            Tuple<String, Object> argument = args[i];
            resolvers[i] = Placeholder.parsed(argument.first(), String.valueOf(argument.second()));
        }

        return miniMessage.deserialize(message, TagResolver.resolver(resolvers));
    }

    @SafeVarargs
    public Component text(List<String> message, Tuple<String, Object>... args) {
        return text(String.join("\n", message), args);
    }
}
