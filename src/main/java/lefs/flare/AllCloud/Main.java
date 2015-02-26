package lefs.flare.AllCloud;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.darkblade12.particleeffect.ParticleEffect;

public class Main extends JavaPlugin {
	private List<UUID> players = new ArrayList<UUID>();
	
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
				for (UUID uuid : players) {
					Player player = Bukkit.getPlayer(uuid);
					if (player != null) {
						Location location = player.getLocation();
						ParticleEffect.CLOUD.display(.4f, .5f, .4f, 0, 50, location.clone().add(0, 6, 0), 20);
						ParticleEffect.WATER_DROP.display(.4f, .5f, .4f, 0, 100, location.clone().add(0, 5, 0), 20);
					}
				}
			}
		}.runTaskTimer(this, 2, 2);
	}
}
