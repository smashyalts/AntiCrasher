
# 🛡️ AntiCrasher

## 🚀 Overview

**AntiCrasher** is a powerful and lightweight Minecraft server plugin designed to **secure your server** against various client-side crash exploits. It meticulously validates and filters malicious packets to ensure maximum server stability and player security. 🔒

## ✨ Features

- ✅ **Advanced Packet Validation** – Proactively identifies and neutralizes exploit packets.
- 📁 **Detailed Logging** – Tracks exploit attempts in console and log files.
- ⚔️ **Automated Punishments** – Customizable commands to punish malicious activities.
- 📋 **PlaceholderAPI Support** – Enhance commands with dynamic placeholders.
- 📡 **PacketEvents Integration** – Efficient and reliable packet management.

## 📌 Implemented Listeners & Checks

### 📢 ChannelListener
- 🚩 Blocks malformed plugin messages.
- 🔍 Validates `register` and `unregister` payload lengths.

### 🔍 TabCompleteListener
- 🛑 Prevents malicious tab-completion requests.
- 🧩 Incorporates Paper's exploit fix.

### 🖱️ WindowListener
- 🚧 Ensures correct inventory interactions and window clicks.
- 📖 Validates book editing packets.
.....

## ⚙️ Configuration

Easily configurable via `config.yml`:

```yaml
log-to-file: true
log-attempts: true
punish-on-attempt: true
punish-command: "kick %player% 🚫 Exploit usage detected!"
```

- **`log-to-file`** 📜 Logs exploit attempts to a dedicated file.
- **`log-attempts`** 📢 Logs attempts to console.
- **`punish-on-attempt`** ⚔️ Enables punishment upon exploit detection.
- **`punish-command`** 🛠️ Customizable punishment commands.

## 🎮 Commands

| Command       | Permission            | Description                          |
|---------------|-----------------------|--------------------------------------|
| `/acreload` 🔄| `anticrasher.reload`  | Reload plugin configuration.         |

## 🔑 Permissions

| Permission             | Description                                 |
|------------------------|---------------------------------------------|
| `anticrasher.reload` 🔄| Allows access to `/acreload` command.       |
| `anticrasher.bypass` 🚨| Bypasses all anti-crasher protections.      |

## 📦 Dependencies

- 📡 [PacketEvents](https://github.com/retrooper/packetevents)
- 🧩 [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (optional)
- 📈 [bStats](https://bstats.org/) (metrics)

## 📥 Installation

1. 📂 Place `AntiCrasher.jar` in your server's `plugins` directory.
2. 🔄 Restart your server.
3. 📝 Configure via `config.yml`.

## 🤝 Contributing

Pull requests and contributions are warmly welcomed! ✨

## 📄 License

Licensed under the MIT License. See the `LICENSE` file for more details.

## 💬 Support

Have questions or issues? Open an issue or start a discussion on GitHub!
