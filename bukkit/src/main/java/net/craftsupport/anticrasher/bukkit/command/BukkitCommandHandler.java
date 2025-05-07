package net.craftsupport.anticrasher.bukkit.command;

import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import lombok.Getter;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.command.impl.ReloadCommand;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.craftsupport.anticrasher.bukkit.AntiCrasher;
import net.craftsupport.anticrasher.bukkit.user.BukkitUser;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.meta.SimpleCommandMeta;
import org.incendo.cloud.paper.LegacyPaperCommandManager;

import java.util.UUID;

@Getter
@Service
public class BukkitCommandHandler {
    @Getter public static final BukkitCommandHandler instance = new BukkitCommandHandler();

    private LegacyPaperCommandManager<User> manager;
    private AnnotationParser<User> annotationParser;

    @Configure
    public void initialise() {
        SenderMapper<CommandSender, User> senderMapper = SenderMapper.create(
                commandSender -> {
                    if (commandSender instanceof Player player) {
                        return AntiCrasherAPI.getInstance().getUserManager().create(player.getUniqueId(), commandSender);
                    }

                    return new BukkitUser(UUID.randomUUID(), commandSender);
                },
                sender -> (CommandSender) sender.getSource()
        );

        this.manager = new LegacyPaperCommandManager<>(
                AntiCrasher.getInstance(),
                ExecutionCoordinator.asyncCoordinator(),
                senderMapper
        );
        this.annotationParser = new AnnotationParser<>(
                this.manager,
                User.class,
                params -> SimpleCommandMeta.empty()
        );

        registerSubCommands();

        ACLogger.info("Registered commands.");
    }

    private void registerSubCommands() {
        annotationParser.parse(
                new ReloadCommand()
        );
    }
}
