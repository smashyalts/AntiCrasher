package net.craftsupport.anticrasher.packet;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEditBook;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPickItem;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCraftRecipeRequest;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerPositionAndRotation;
import me.clip.placeholderapi.PlaceholderAPI;
import net.craftsupport.anticrasher.AntiCrasher;
import net.craftsupport.anticrasher.utils.Utils;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getLogger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class WindowListener implements PacketListener {
    private final AntiCrasher plugin;
    private final Utils utilsInstance;

    public WindowListener(final AntiCrasher plugin, Utils utilsInstance) {
        this.plugin = plugin;
        this.utilsInstance = utilsInstance;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.PLUGIN_MESSAGE) {
            WrapperPlayClientPluginMessage payload = new WrapperPlayClientPluginMessage(event);
            String channel = payload.getChannelName();
            if ("MC|BEdit".equalsIgnoreCase(channel) || "MC|BSign".equalsIgnoreCase(channel)) {
                handleInvalidPacket(event);
                return;
            }
        }

        if (event.getServerVersion().isOlderThan(ServerVersion.V_1_20_5)) {
            if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
                WrapperPlayClientClickWindow click = new WrapperPlayClientClickWindow(event);
                int clickType = click.getWindowClickType().ordinal();
                int button = click.getButton();
                int windowId = click.getWindowId();
                int slot = click.getSlot();

                if ((clickType == 1 || clickType == 2) && windowId >= 0 && button < 0) {
                    handleInvalidPacket(event);
                    return;
                } else if (windowId >= 0 && clickType == 2 && slot < 0) {
                    handleInvalidPacket(event);
                    return;
                }
                if (windowId < 0 || slot < -1 || button < 0) {
                    handleInvalidPacket(event);
                    return;
                }
                if (click.getWindowClickType() == null ||
                        "EXCEPTION".equalsIgnoreCase(click.getWindowClickType().toString())) {
                    handleInvalidPacket(event);
                    return;
                }
            }
        }

        if (event.getPacketType() == PacketType.Play.Client.EDIT_BOOK) {
            WrapperPlayClientEditBook editBook = new WrapperPlayClientEditBook(event);
            String title = editBook.getTitle();
            if (title == null || title.length() > 32) {
                handleInvalidPacket(event);
                return;
            }
            List<String> pages = editBook.getPages();
            if (pages == null) {
                handleInvalidPacket(event);
                return;
            }
            final int MAX_BOOK_PAGES = 50;
            final int MAX_PAGE_CHARACTERS = 256;
            if (pages.size() > MAX_BOOK_PAGES) {
                handleInvalidPacket(event);
                return;
            }
            for (String page : pages) {
                if (page.length() > MAX_PAGE_CHARACTERS) {
                    handleInvalidPacket(event);
                    return;
                }
            }

            int maxDataSize = event.getServerVersion().isOlderThan(ServerVersion.V_1_16) ? 8192 : 32768;
            StringBuilder sb = new StringBuilder();
            sb.append(title);
            for (String page : pages) {
                sb.append(page);
            }
            byte[] dataBytes = sb.toString().getBytes(StandardCharsets.UTF_8);
            if (dataBytes.length > maxDataSize) {
                handleInvalidPacket(event);
                return;
            }
        }

        if (event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION) {
            WrapperPlayClientPlayerPositionAndRotation posRot = new WrapperPlayClientPlayerPositionAndRotation(event);
            Location loc = posRot.getLocation();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            float yaw = loc.getYaw();
            float pitch = loc.getPitch();
            if (Math.abs(x) > 30000000 || Math.abs(y) > 30000000 || Math.abs(z) > 30000000) {
                handleInvalidPacket(event);
                return;
            }
            if (yaw < -360 || yaw > 360 || pitch < -90 || pitch > 90) {
                handleInvalidPacket(event);
                return;
            }
        }

        if (event.getPacketType() == PacketType.Play.Client.PICK_ITEM) {
            WrapperPlayClientPickItem pickItem = new WrapperPlayClientPickItem(event);
            if (pickItem.getSlot() < 0) {
                handleInvalidPacket(event);
                return;
            }
        }

        if (event.getPacketType() == PacketType.Play.Client.CRAFT_RECIPE_REQUEST) {
            WrapperPlayClientCraftRecipeRequest recipe = new WrapperPlayClientCraftRecipeRequest(event);
            String recipeId = recipe.getRecipeId().toString();
            if (recipeId.length() > 64 || !recipeId.contains(":")) {
                handleInvalidPacket(event);
                return;
            }
        }
    }

    public void handleInvalidPacket(PacketReceiveEvent event) {
        event.setCancelled(true);
        event.getUser().closeConnection();
        if (utilsInstance.logToFile) {
            utilsInstance.log(event.getUser().getName() + " Tried to use the Crash Exploit");
        }
        if (utilsInstance.logAttempts) {
            getLogger().warning(event.getUser().getName() + " Tried to use the Crash Exploit");
        }
        if (utilsInstance.punishOnAttempt) {
            String replacedString = utilsInstance.punishCommand.replace("%player%", event.getUser().getName());
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (plugin.isPAPIEnabled()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(event.getUser().getUUID()), replacedString));
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replacedString);
                }
            });
        }
    }
}
