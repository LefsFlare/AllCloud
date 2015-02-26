package lefs.flare.AllCloud;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SadCloud implements CommandExecutor{
	public Main main;
	public SadCloud(Main Main) {
		main = Main;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sad")) {
			if (args.length == 0) {
				if (sender instanceof Player) {
					if (sender.hasPermission("sad.use")) {
						UUID uuid = ((Player) sender).getUniqueId();
						boolean status = main.toggleCloud(uuid);
						sender.sendMessage((status ? ChatColor.RED + "Disabled" : ChatColor.GREEN + "Enabled") + " sad cloud");
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Error: You don't have permission to use this command");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Error: Only players can use the command on themselves. Usage: /sad [otherPlayer]");
				}
			} else if (args.length == 1) {
				if (sender.hasPermission("sad.other")) {
					Player player = Bukkit.getPlayer(args[0]);
					
					if (player != null) {
						UUID uuid = player.getUniqueId();
						
						boolean status = main.toggleCloud(uuid);
						
						sender.sendMessage((status ? ChatColor.RED + "Disabled" : ChatColor.GREEN + "Enabled") + " sad cloud for " + player.getDisplayName());
						
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Error: Cannot find player \"" + args[0] + "\"");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Error: You don't have permission to use this command");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Usage: /sad [otherPlayer]");
			}
		}
		return false;
	}
}
