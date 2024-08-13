package vounchable.core;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import vounchable.core.enchant.EnchantManager;
import vounchable.core.provider.*;
import vounchable.core.player.Player;
import vounchable.core.API.*;
import vounchable.core.Task.*;
import vounchable.core.Task.event.FactionTask;
import vounchable.core.block.Block;
import vounchable.core.listeners.Listeners;
import vounchable.core.commands.Commands;
import vounchable.core.item.Items;
import vounchable.core.entities.Entitys;
import nukkit.fakeinventory.FakeMenuFactory;
import nukkit.fakeinventory.menu.FakeMenuType;
import cn.nukkit.permission.PermissionAttachment;
import vounchable.core.cooldowns.Cooldowns;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main extends PluginBase {

    public static final String INV_MENU_TYPE_WORKBENCH = "portablecrafting:workbench";

        private static Main instance;

            public static final String[] appleenchanted = {}, rogue = {};
                private final Map<String, PermissionAttachment> permission = new HashMap<>();
                    public static final Map<String, Object> data = new HashMap<>();

                        @Override
                            public void onLoad() {
                                    instance = this;
                                        }

                                            @Override
                                                public void onEnable() {
                                                        if (!getDataFolder().exists()) {
                                                                    getDataFolder().mkdir();
                                                                            }

                                                                                    File backupFolder = new File(getDataFolder(), "backup");
                                                                                            if (!backupFolder.exists()) {
                                                                                                        backupFolder.mkdirs();
                                                                                                                }

                                                                                                                        File playersFolder = new File(getDataFolder(), "players");
                                                                                                                                if (!playersFolder.exists()) {
                                                                                                                                            playersFolder.mkdirs();
                                                                                                                                                    }

                                                                                                                                                            if (!FakeMenuFactory.isRegistered(INV_MENU_TYPE_WORKBENCH)) {
                                                                                                                                                                        FakeMenuFactory.registerMenu(INV_MENU_TYPE_WORKBENCH, new FakeMenuType());
                                                                                                                                                                                }

                                                                                                                                                                                        new Cooldowns();
                                                                                                                                                                                                getServer().getNetwork().setName("§l§dSAKURA");
                                                                                                                                                                                                        MysqlProvider.connect();
                                                                                                                                                                                                                SQLite3Provider.connect();

                                                                                                                                                                                                                        Listeners.init();
                                                                                                                                                                                                                                Block.init();
                                                                                                                                                                                                                                        Commands.init();
                                                                                                                                                                                                                                                Items.init();
                                                                                                                                                                                                                                                        Entitys.init();
                                                                                                                                                                                                                                                                YamlProvider.init();
                                                                                                                                                                                                                                                                        Factions.init();

                                                                                                                                                                                                                                                                                getScheduler().scheduleRepeatingTask(new BardTask(), 20);
                                                                                                                                                                                                                                                                                        getScheduler().scheduleRepeatingTask(new GhostTask(), 20);
                                                                                                                                                                                                                                                                                                getScheduler().scheduleRepeatingTask(new ArcherTask(), 20);
                                                                                                                                                                                                                                                                                                        getScheduler().scheduleRepeatingTask(new MageTask(), 20);
                                                                                                                                                                                                                                                                                                                getScheduler().scheduleRepeatingTask(new FactionTask(), 5 * 60 * 40);
                                                                                                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                                                                                                        @Override
                                                                                                                                                                                                                                                                                                                            public void onDisable() {
                                                                                                                                                                                                                                                                                                                                    SQLite3Provider.disconnect();
                                                                                                                                                                                                                                                                                                                                            MysqlProvider.disconnect();
                                                                                                                                                                                                                                                                                                                                                    YamlProvider.save();
                                                                                                                                                                                                                                                                                                                                                        }

                                                                                                                                                                                                                                                                                                                                                            public static Main getMain() {
                                                                                                                                                                                                                                                                                                                                                                    return instance;
                                                                                                                                                                                                                                                                                                                                                                        }

                                                                                                                                                                                                                                                                                                                                                                            public static SQLite3Provider getProvider() {
                                                                                                                                                                                                                                                                                                                                                                                    return new SQLite3Provider();
                                                                                                                                                                                                                                                                                                                                                                                        }

                                                                                                                                                                                                                                                                                                                                                                                            public static Scoreboards getScoreboard() {
                                                                                                                                                                                                                                                                                                                                                                                                    return new Scoreboards();
                                                                                                                                                                                                                                                                                                                                                                                                        }

                                                                                                                                                                                                                                                                                                                                                                                                            public static Object getDefaultConfig(String configuration) {
                                                                                                                                                                                                                                                                                                                                                                                                                    return getMain().getConfig().get(configuration);
                                                                                                                                                                                                                                                                                                                                                                                                                        }

                                                                                                                                                                                                                                                                                                                                                                                                                            public static Config getConfiguration(String configuration) {
                                                                                                                                                                                                                                                                                                                                                                                                                                    return new Config(getMain().getDataFolder() + "/" + configuration + ".yml", Config.YAML);
                                                                                                                                                                                                                                                                                                                                                                                                                                        }

                                                                                                                                                                                                                                                                                                                                                                                                                                            public PermissionAttachment getPermission(Player player) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                    return permission.computeIfAbsent(player.getName(), k -> player.addAttachment(this));
                                                                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                    