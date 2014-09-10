package pl.themolka.custommobdrop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DisabledCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cmdreload")) {
            sender.sendMessage(CmdreloadCommand.PREFIX + ChatColor.GREEN + "This plugin is disabled. Enable it in your config.yml and reload or restart the server.");
            return true;
        }
        return false;
    }
}
