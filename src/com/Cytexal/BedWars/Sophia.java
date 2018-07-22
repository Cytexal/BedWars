package com.Cytexal.BedWars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Sophia implements Listener {
	Sheep s;
	int pid;
	int ttl = 10;

	public Sophia(Location loc, Player p) {
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
		Sheep s = (Sheep) p.getWorld().spawnEntity(loc.add(0, 1, 0), EntityType.SHEEP);
		TNTPrimed tnt = (TNTPrimed) p.getWorld().spawnEntity(loc.add(0, 1, 0), EntityType.PRIMED_TNT);
		tnt.setFuseTicks(20*20+5);
		s.setPassenger(tnt);
		s.setCustomName(Main.Teams.get(p));
		s.setCustomNameVisible(true);

		pid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (ttl > 0) {
					for (Entity e : s.getNearbyEntities(50, 50, 50)) {
						if (e instanceof Player) {
							followPlayer(s, (Player) e, 2.0F);
						}
					}
					ttl--;
				} else {
					s.remove();
				}
			}
		}, 0, 20*2);

	}

	public void followPlayer(Creature creature, Player player, double speed) {
		Location location = player.getLocation();
		((CraftCreature) creature).getHandle().getNavigation().a(location.getX(), location.getY(), location.getZ(),
				speed);
	}

	@EventHandler
	public void Hit(EntityDamageEvent e) {
		if (e.getEntity() == s) {
			e.setCancelled(true);
		}
	}
}
