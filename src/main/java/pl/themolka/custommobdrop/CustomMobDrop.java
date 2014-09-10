package pl.themolka.custommobdrop;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;
import pl.themolka.custommobdrop.api.CMD;
import pl.themolka.custommobdrop.api.MobsConfigLoader;

public class CustomMobDrop extends JavaPlugin {
    private static CustomMobDrop instance;
    public String version;
    
    @Override
    public void onEnable() {
        long initMs = System.currentTimeMillis();
        CustomMobDrop.instance = this;
        this.version = this.getDescription().getVersion();
        this.saveDefaultConfig();
        if (!this.getConfig().getBoolean("enabled", true)) {
            this.getCommand("cmdreload").setExecutor(new DisabledCommand());
            this.getLogger().log(Level.INFO, "You have setup variable 'enabled' to false. Disabling...");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        this.getCommand("cmdreload").setExecutor(new CmdreloadCommand());
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        MobsConfigLoader.load();
        initMs = System.currentTimeMillis() - initMs;
        this.getLogger().log(Level.INFO, "CustomMobDrop enabled in {0} ms. Loaded {1} mobs and {2} drops.",
                new Object[] {initMs, CMD.getMobs().size(), CMD.getAmountOfLoadedDrops()});
    }
    
    public static CustomMobDrop getInstance() {
        return CustomMobDrop.instance;
    }
}
