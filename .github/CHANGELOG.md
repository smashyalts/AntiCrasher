# 🚀 AntiCrasher v2.0.8
A small update adding another placeholder to the config.

## Changelog
- Bumped PacketEvents to 2.9.5 (bug fixes)
- Added `<player_uuid>` placeholder to config

## Patches
The following exploits are currently patched in this plugin/mod:
- **Bundle Crash Exploit**
- **Book Dupe Exploit**
- **Negative Slot ID Crash Exploit**
- **NBT Tab Completion Crash Exploit**

## Installation
### Paper/Folia/Velocity
>[!NOTE]
>You only need to run this on the backends, or the proxy, **not on both**.

1. Download the latest version from [Modrinth](https://modrinth.com/plugin/anticrasher/versions?l=spigot&l=purpur&l=paper&l=bukkit&l=folia) and place it in the `plugins` folder. Restart the server/proxy.

### Fabric
>[!WARNING]
> PacketEvents Fabric is currently broken for online mode servers running 1.20.4-1.21.7 and onwards.
> There is no workaround for this issue at the moment. If you run a Velocity proxy, you can use the proxy version there instead.

1. Download the latest version from [Modrinth](https://modrinth.com/plugin/anticrasher/versions?l=fabric) and place it in the `mods` folder.
2. Install the latest [Fabric API](https://modrinth.com/mod/fabric-api/versions) for your server.
3. Download the latest `fabric-build` package from [Axionize/packetevents](https://github.com/Axionize/packetevents/actions/workflows/gradle-publish.yml?query=branch%3Afix%2Ffabric-events+is%3Asuccess), and place it in the `mods` folder. Restart your server.
   Use the [Official PacketEvents Builds](https://modrinth.com/plugin/packetevents) for 1.21.8+.

## Commands
- /ac reload - Reloads configs. `anticrasher.command.reload`

## Permissions
- `anticrasher.alerts` - Receive alerts when a player attempts to run an exploit.
- `anticrasher.bypass` - Bypass all AntiCrasher checks.
- `anticrasher.updates` - Receive plugin update notifications