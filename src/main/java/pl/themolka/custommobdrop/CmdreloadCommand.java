package pl.themolka.custommobdrop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import pl.themolka.custommobdrop.api.CMD;
import pl.themolka.custommobdrop.api.MobsConfigLoader;

public class CmdreloadCommand implements CommandExecutor {
    public static final String PREFIX = ChatColor.AQUA + "[CMD] " + ChatColor.RESET;
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cmdreload")) {
            if (!sender.hasPermission("cmd.reload")) {
                sender.sendMessage(CmdreloadCommand.PREFIX + ChatColor.RED + "You don't have permission to execute this command.");
                return true;
            }
            sender.sendMessage(CmdreloadCommand.PREFIX + ChatColor.GREEN + "Reloading your config.yml file...");
            long reload = System.currentTimeMillis();
            if (!this.reloadConfig(sender)) {
                sender.sendMessage(CmdreloadCommand.PREFIX + ChatColor.RED + "Reload failure. :(");
                return true;
            }
            MobsConfigLoader.load();
            int mobs = CMD.getMobs().size();
            int drops = CMD.getAmountOfLoadedDrops();
            reload = System.currentTimeMillis() - reload;
            sender.sendMessage(CmdreloadCommand.PREFIX + ChatColor.GREEN + "Reloaded successfully. Loaded " + mobs + " mobs and " + drops + " drops in " + reload + " ms.");
            return true;
        }
        return false;
    }
    
    private boolean reloadConfig(CommandSender sender) {
        File file = new File(CustomMobDrop.getInstance().getDataFolder() + File.separator + "config.yml");
        try {
            InputStream input = new FileInputStream(file);
            CMD.getConfigFile().load(input);
            return true;
        } catch (FileNotFoundException ex) {
            sender.sendMessage(CmdreloadCommand.PREFIX + ChatColor.RED + "File config.yml was not found - " + ex.getMessage() + ". Creating a new one...");
            CustomMobDrop.getInstance().saveDefaultConfig();
        } catch (IOException ex) {
            sender.sendMessage(CmdreloadCommand.PREFIX + ChatColor.RED + "Error in the file location - " + ex.getMessage() + ". Please reload your server.");
        } catch (InvalidConfigurationException ex) {
            sender.sendMessage(CmdreloadCommand.PREFIX + ChatColor.RED + "Bad YAML file format! Please check your syntax and try again - " + ex.getMessage());
        }
        return false;
    }
}
