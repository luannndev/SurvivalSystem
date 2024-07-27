package dev.subscripted.survivalsystem;

import dev.subscripted.survivalsystem.modules.api.CooldownManager;
import dev.subscripted.survivalsystem.modules.api.LuckpermsService;
import dev.subscripted.survivalsystem.modules.api.cooldownmanager.Events;
import dev.subscripted.survivalsystem.modules.chat.ChatFilter;
import dev.subscripted.survivalsystem.modules.chat.Format;
import dev.subscripted.survivalsystem.modules.chat.WrongSyntax;
import dev.subscripted.survivalsystem.modules.connect.JoinQuit;
import dev.subscripted.survivalsystem.modules.database.MySQL;
import dev.subscripted.survivalsystem.modules.database.connections.Coins;
import dev.subscripted.survivalsystem.modules.death.PlayerDeathService;
import dev.subscripted.survivalsystem.modules.economy.BankCommand;
import dev.subscripted.survivalsystem.modules.economy.BankUIListener;
import dev.subscripted.survivalsystem.modules.economy.MarketCommand;
import dev.subscripted.survivalsystem.modules.economy.MarketListener;
import dev.subscripted.survivalsystem.modules.fly.FlyCommand;
import dev.subscripted.survivalsystem.modules.fly.FlyService;
import dev.subscripted.survivalsystem.modules.gamemode.GamemodeSwitcher;
import dev.subscripted.survivalsystem.modules.msg.MsgCommand;
import dev.subscripted.survivalsystem.modules.msg.ReplyCommand;
import dev.subscripted.survivalsystem.modules.spawn.SetSpawnCommand;
import dev.subscripted.survivalsystem.modules.tablist.TablistService;
import dev.subscripted.survivalsystem.modules.teleport.TeleportCommand;
import dev.subscripted.survivalsystem.modules.utilcommands.CraftCommand;
import dev.subscripted.survivalsystem.modules.utilcommands.EnderChestCommand;
import dev.subscripted.survivalsystem.modules.utilcommands.SeeInventory;
import dev.subscripted.survivalsystem.modules.vanish.VanishCommand;
import dev.subscripted.survivalsystem.modules.vanish.VanishService;
import dev.subscripted.survivalsystem.utils.BankPaymentSerivce;
import dev.subscripted.survivalsystem.utils.SoundLibrary;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;


    @Getter
    TablistService tablistService;
    @Getter
    LuckpermsService lpservice;
    @Getter
    FlyService flyService = new FlyService();
    @Getter
    MySQL mySQL = new MySQL();
    @Getter
    Coins coins = new Coins(mySQL);
    @Getter
    SoundLibrary library = new SoundLibrary();
    @Getter
    BankPaymentSerivce serivce = new BankPaymentSerivce();
    @Getter
    String prefix = "§8» §8| §x§B§F§A§3§B§A§lN§x§B§0§9§3§B§2§lo§x§A§1§8§3§A§B§lv§x§9§2§7§3§A§3§li§x§8§3§6§2§9§B§lb§x§7§4§5§2§9§4§le§x§6§5§4§2§8§C§ls §8» §r";
    @Getter
    VanishService service;
    @Getter
    CooldownManager cooldownManager;


    @Override
    public void onEnable() {

        getLogger().info("\n" +
                "[]=====================================================[] \n" +
                "    _   __           _ __                   __   \n" +
                "   / | / /___ _   __(_) /_  ___  _____ ____/ /__ \n" +
                "  /  |/ / __ \\ | / / / __ \\/ _ \\/ ___// __  / _ \\\n" +
                " / /|  / /_/ / |/ / / /_/ /  __(__  )/ /_/ /  __/\n" +
                "/_/ |_/\\____/|___/_/_.___/\\___/____(_)__,_/\\___/ \n" +
                "                                                 \n" +
                "[]=====================================================[] \n");

        String prefixfarbe = "§x§8§D§6§D§A§0§l";
        instance = this;
        saveDefaultConfig();
        lpservice = new LuckpermsService();
        service = new VanishService();
        tablistService = new TablistService(instance);
        cooldownManager = new CooldownManager(instance);


        getLogger().info("\n" +
                "[]=====================================================[] \n" +
                "  _____                _ _               _____                                          _     \n" +
                " |  __ \\              (_) |             / ____|                                        | |    \n" +
                " | |__) |___  __ _ ___ _| |_ ___ _ __  | |     ___  _ __ ___  _ __ ___   __ _ _ __   __| |___ \n" +
                " |  _  // _ \\/ _` / __| | __/ _ \\ '__| | |    / _ \\| '_ ` _ \\| '_ ` _ \\ / _` | '_ \\ / _` / __|\n" +
                " | | \\ \\  __/ (_| \\__ \\ | ||  __/ |    | |___| (_) | | | | | | | | | | | (_| | | | | (_| \\__ \\\n" +
                " |_|  \\_\\___|\\__, |___/_|\\__\\___|_|     \\_____\\___/|_| |_| |_|_| |_| |_|\\__,_|_| |_|\\__,_|___/\n" +
                "              __/ |                                                                           \n" +
                "             |___/                                                                            \n" +
                "[]=====================================================[]\n");

        getCommand("ec").setExecutor(new EnderChestCommand(library));
        getCommand("tpa").setExecutor(new TeleportCommand(instance, library));
        getCommand("tp").setExecutor(new TeleportCommand(instance, library));
        getCommand("tpahere").setExecutor(new TeleportCommand(instance, library));
        getCommand("tphere").setExecutor(new TeleportCommand(instance, library));
        getCommand("tpaaccept").setExecutor(new TeleportCommand(instance,library ));
        getCommand("tpadeny").setExecutor(new TeleportCommand(instance,library ));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(instance));
        getCommand("gm").setExecutor(new GamemodeSwitcher(library));
        getCommand("craft").setExecutor(new CraftCommand(library));
        getCommand("seeinventory").setExecutor(new SeeInventory(library));
        getCommand("tpa").setTabCompleter(new TeleportCommand(instance, library));
        getCommand("tp").setTabCompleter(new TeleportCommand(instance,library));
        getCommand("tpahere").setTabCompleter(new TeleportCommand(instance, library));
        getCommand("tphere").setTabCompleter(new TeleportCommand(instance, library));
        getCommand("tpaaccept").setTabCompleter(new TeleportCommand(instance, library));
        getCommand("tpadeny").setTabCompleter(new TeleportCommand(instance, library));
        getCommand("gm").setTabCompleter(new GamemodeSwitcher(library));
        getCommand("market").setExecutor(new MarketCommand(library));
        getCommand("bank").setExecutor(new BankCommand(coins));
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("v").setExecutor(new VanishCommand());
        getCommand("fly").setExecutor(new FlyCommand(flyService, library));
        getCommand("msg").setExecutor(new MsgCommand(instance));
        getCommand("r").setExecutor(new ReplyCommand(instance));

        getLogger().info("\n" +
                "[]=====================================================[] \n" +
                "  _____                _ _              _      _     _                       \n" +
                " |  __ \\              (_) |            | |    (_)   | |                      \n" +
                " | |__) |___  __ _ ___ _| |_ ___ _ __  | |     _ ___| |_ ___ _ __   ___ _ __ \n" +
                " |  _  // _ \\/ _` / __| | __/ _ \\ '__| | |    | / __| __/ _ \\ '_ \\ / _ \\ '__|\n" +
                " | | \\ \\  __/ (_| \\__ \\ | ||  __/ |    | |____| \\__ \\ ||  __/ | | |  __/ |   \n" +
                " |_|  \\_\\___|\\__, |___/_|\\__\\___|_|    |______|_|___/\\__\\___|_| |_|\\___|_|   \n" +
                "              __/ |                                                          \n" +
                "             |___/                                                           \n" +
                "[]=====================================================[] \n");

        getServer().getPluginManager().registerEvents(new MarketListener(library), instance);
        getServer().getPluginManager().registerEvents(new BankUIListener(serivce, library), instance);
        getServer().getPluginManager().registerEvents(new Format(), instance);
        getServer().getPluginManager().registerEvents(new JoinQuit(service, tablistService, lpservice), instance);
        getServer().getPluginManager().registerEvents(new WrongSyntax(library), instance);
        getServer().getPluginManager().registerEvents(new PlayerDeathService(library), instance);
        getServer().getPluginManager().registerEvents(new ChatFilter(library), instance);
        getServer().getPluginManager().registerEvents(new Events(cooldownManager), instance);

    }

    @Override
    public void onDisable() {
        for (Player p : getServer().getOnlinePlayers()) {
            p.kickPlayer(Main.getInstance().getPrefix() + "§eServer Restart");
        }
        mySQL.close();
    }
}
