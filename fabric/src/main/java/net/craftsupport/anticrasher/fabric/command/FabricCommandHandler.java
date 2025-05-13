package net.craftsupport.anticrasher.fabric.command;

import com.github.retrooper.packetevents.PacketEvents;
import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import lombok.Getter;
import net.craftsupport.anticrasher.api.AntiCrasherAPI;
import net.craftsupport.anticrasher.api.user.User;
import net.craftsupport.anticrasher.common.command.impl.ReloadCommand;
import net.craftsupport.anticrasher.common.util.ACLogger;
import net.craftsupport.anticrasher.fabric.user.FabricUser;
import net.minecraft.server.command.ServerCommandSource;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;
import org.incendo.cloud.meta.SimpleCommandMeta;

import java.util.Objects;
import java.util.UUID;

@Getter
@Service
public class FabricCommandHandler {
    @Getter public static final FabricCommandHandler instance = new FabricCommandHandler();

    private FabricServerCommandManager<User> manager;
    private AnnotationParser<User> annotationParser;

    @Configure
    public void initialise() {
        SenderMapper<ServerCommandSource, User> senderMapper = SenderMapper.create(
                serverCommandSource -> {

                    if (serverCommandSource.isExecutedByPlayer()) {
                        return Objects.requireNonNull(AntiCrasherAPI.getInstance().getUserManager().getOrCreate(
                                serverCommandSource.getPlayer().getUuid(),
                                serverCommandSource.getPlayer()
                        ));
                    }

                    return new FabricUser(UUID.randomUUID(), serverCommandSource);
                },
                sender -> (ServerCommandSource) sender.getSource()
        );

        this.manager = new FabricServerCommandManager<>(
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
