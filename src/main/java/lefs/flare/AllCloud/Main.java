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
	public List<UUID> players = new ArrayList<UUID>();
	public List<UUID> sadplayers = new ArrayList<UUID>();
	public List<UUID> normalplayers = new ArrayList<UUID>();
	public List<UUID> happyplayers = new ArrayList<UUID>();
	
	@Override
	public void onEnable() {
		getCommand("sad").setExecutor(new SadCloud(this));
		
		new BukkitRunnable() {
			public void run() {
				for (UUID uuid : sadplayers) {
					Player player = Bukkit.getPlayer(uuid);
					if (player != null) {
						Location location = player.getLocation();
						ParticleEffect.CLOUD.display(.4f, .5f, .4f, 0, 50, location.clone().add(0, 6, 0), 20);
						ParticleEffect.WATER_DROP.display(.4f, .5f, .4f, 0, 100, location.clone().add(0, 5, 0), 20);
					}
				}
			}
		}.runTaskTimer(this, 2, 2);
		new BukkitRunnable() {
			public void run() {
				for (UUID uuid : normalplayers) {
					Player player = Bukkit.getPlayer(uuid);
					if (player != null) {
						Location location = player.getLocation();
						ParticleEffect.CLOUD.display(.4f, .5f, .4f, 0, 50, location.clone().add(0, 6, 0), 20);						ParticleEffect.WATER_DROP.display(.4f, .5f, .4f, 0, 100, location.clone().add(0, 5, 0), 20);
					}
				}
			}
		}.runTaskTimer(this, 2, 2);
		new BukkitRunnable() {
			public void run() {
				for (UUID uuid : happyplayers) {
					Player player = Bukkit.getPlayer(uuid);
					if (player != null) {
						Location location = player.getLocation();
						ParticleEffect.CLOUD.display(.4f, .5f, .4f, 0, 50, location.clone().add(0, 6, 0), 20);
						ParticleEffect.FIREWORKS_SPARK.display(.2f, .5f, .2f, 0, 30, location.clone().add(0, 5, 0), 20);
					}
				}
			}
		}.runTaskTimer(this, 2, 2);
	}
}
