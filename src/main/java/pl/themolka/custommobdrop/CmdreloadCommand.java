package pl.themolka.custommobdrop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
            CustomMobDrop.getInstance().reloadConfig(); // Use FileConfiguration.load(InputStream) for custom error msgs?
            MobsConfigLoader.load();
            int mobs = CMD.getMobs().size();
            int drops = CMD.getAmountOfLoadedDrops();
            sender.sendMessage(CmdreloadCommand.PREFIX + ChatColor.GREEN + "Reloaded successfully. Loaded " + mobs + " mobs and " + drops + " drops.");
            return true;
        }
        return false;
    }
}
