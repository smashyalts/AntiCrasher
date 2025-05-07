package net.skullian.anticrasher.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import lombok.Getter;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.command.impl.ReloadCommand;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.skullian.anticrasher.velocity.AntiCrasher;
import net.skullian.anticrasher.velocity.user.VelocityUser;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.meta.SimpleCommandMeta;
import org.incendo.cloud.velocity.VelocityCommandManager;

import java.util.UUID;

@Getter
@Service
public class VelocityCommandHandler {
    public static final VelocityCommandHandler instance = new VelocityCommandHandler();

    private VelocityCommandManager<User> manager;
    private AnnotationParser<User> annotationParser;

    @Configure
    public void initialise() {
        SenderMapper<CommandSource, User> senderMapper = SenderMapper.create(
                commandSource -> {
                    if (commandSource instanceof Player player) {
                        return AntiCrasherAPI.getInstance().getUserManager().create(player.getUniqueId(), commandSource);
                    }

                    return new VelocityUser(UUID.randomUUID(), commandSource);
                },
                sender -> (CommandSource) sender.getSource()
        );

        this.manager = new VelocityCommandManager<>(
                AntiCrasher.getInstance().getPluginContainer(),
                AntiCrasher.getInstance().server,
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
