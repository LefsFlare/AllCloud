package lefs.flare.AllCloud;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.darkblade12.particleeffect.ParticleEffect;

public class Main extends JavaPlugin {
	private List<UUID> players = new ArrayList<UUID>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sad")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(sender.hasPermission("sad.use")) {
						UUID uuid = ((Player) sender).getUniqueId();
						
						boolean status = toggleCloud(uuid);
						
						sender.sendMessage((status ? ChatColor.RED + "Disabled" : ChatColor.GREEN + "Enabled") + " sad cloud");
						
						return true;
					}
					else {
						sender.sendMessage(ChatColor.RED + "Error: You don't have permission to use this command");
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Error: Only players can use the command on themselves. Usage: /sad [otherPlayer]");
				}
			}
			else if(args.length == 1) {
				if(sender.hasPermission("sad.other")) {
					Player player = Bukkit.getPlayer(args[0]);
					
					if(player != null) {
						UUID uuid = player.getUniqueId();
						
						boolean status = toggleCloud(uuid);
						
						sender.sendMessage((status ? ChatColor.RED + "Disabled" : ChatColor.GREEN + "Enabled") + " sad cloud for " + player.getDisplayName());
						
						return true;
					}
					else {
						sender.sendMessage(ChatColor.RED + "Error: Cannot find player \"" + args[0] + "\"");
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Error: You don't have permission to use this command");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "Usage: /sad [otherPlayer]");
			}
		}
		
		return false;
	}
	
	public boolean toggleCloud(UUID uuid) {
		boolean contains = players.contains(uuid);
		
		if(contains)
			players.remove(uuid);
		else
			players.add(uuid);
		
		return contains;
	}
	
	@Override
	public void onEnable() {
		new BukkitRunnable() {
			public void run() {
				for(UUID uuid : players) {
					Player player = Bukkit.getPlayer(uuid);
					
					if(player != null) {
						Location location = player.getLocation();
						
						//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + player.getName() + " ~ ~6 ~ particle cloud ~ ~ ~ 0.4 0.5 0.4 0 50 force");
						//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + player.getName() + " ~ ~5 ~ particle splash ~ ~ ~ 0.4 0.5 0.4 0 100 force");
						
						ParticleEffect.CLOUD.display(.4f, .5f, .4f, 0, 50, location.clone().add(0, 6, 0), 20);
						ParticleEffect.WATER_DROP.display(.4f, .5f, .4f, 0, 100, location.clone().add(0, 5, 0), 20);
					}
				}
			}
		}.runTaskTimer(this, 2, 2);
	}
}
