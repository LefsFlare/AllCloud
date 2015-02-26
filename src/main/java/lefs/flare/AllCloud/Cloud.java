package lefs.flare.AllCloud;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cloud implements CommandExecutor{
	public Main main;
	public Cloud(Main Main) {
		main = Main;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("cloud")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("normal") || args[0].equalsIgnoreCase("happy") || args[0].equalsIgnoreCase("sad")) {
					String mode = args[0].toLowerCase();
					if (sender instanceof Player) {
						if (sender.hasPermission("cloud.use." + mode)) {
							UUID uuid = ((Player) sender).getUniqueId();
							boolean status = toggleCloud(uuid, mode);
							sender.sendMessage((status ? ChatColor.RED + "Disabled" : ChatColor.GREEN + "Enabled") + " " + mode + " cloud");
							return true;
						} else {
							sender.sendMessage(ChatColor.RED + "Error: You don't have permission to use this command.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Error: Only players can use the command on themselves. Usage: /cloud [type].");
					}
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("normal") || args[0].equalsIgnoreCase("happy") || args[0].equalsIgnoreCase("sad")) {
					String mode = args[0].toLowerCase();
					Player player = Bukkit.getPlayer(args[1]);
					Player executor = Bukkit.getPlayer(sender.getName());
					
					if (player != null) {
						if (sender instanceof Player) {
							if (sender.hasPermission("cloud.others." + mode)) {
								UUID uuid = player.getUniqueId();
								boolean status = toggleCloud(uuid, mode);
								sender.sendMessage((status ? ChatColor.RED + "Disabled" : ChatColor.GREEN + "Enabled") + " " + mode + " cloud for " + player.getDisplayName());
								player.sendMessage(ChatColor.YELLOW + executor.getDisplayName() + " has " + (status ? ChatColor.RED + "disabled" : ChatColor.GREEN + "enabled") + ChatColor.YELLOW + " " + mode + " cloud for you.");
							}
						} else {
							UUID uuid = player.getUniqueId();
							boolean status = toggleCloud(uuid, "normal");
							sender.sendMessage((status ? ChatColor.RED + "Disabled" : ChatColor.GREEN + "Enabled") + " " + mode + " cloud for " + player.getDisplayName());
							player.sendMessage(ChatColor.YELLOW + "Console" + " has " + (status ? ChatColor.RED + "disabled" : ChatColor.GREEN + "enabled") + ChatColor.YELLOW + " " + mode + " cloud for you.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Player not found!");
					}
				}
			}
		}
		return false;
	}
	
	public boolean toggleCloud(UUID uuid, String type) {
		if (type.equalsIgnoreCase("normal")) {
			if (main.normalplayers.contains(uuid)) {
				removeCloud(uuid);
				return true;
			} else {
				main.players.add(uuid);
				main.normalplayers.add(uuid);
				return false;
			}
		} else if (type.equalsIgnoreCase("sad")) {
			if (main.sadplayers.contains(uuid)) {
				removeCloud(uuid);
				return true;
			} else {
				main.players.add(uuid);
				main.sadplayers.add(uuid);
				return false;
			}
		} else if (type.equalsIgnoreCase("happy")) {
			if (main.sadplayers.contains(uuid)) {
				removeCloud(uuid);
				return true;
			} else {
				main.players.add(uuid);
				main.happyplayers.add(uuid);
				return false;
			}
		}
		return false;
	}
	
	public void removeCloud(UUID uuid) {
		if (main.players.contains(uuid)) {
			main.players.remove(uuid);
			main.sadplayers.remove(uuid);
			main.normalplayers.remove(uuid);
			main.happyplayers.remove(uuid);
		}
	}
}
