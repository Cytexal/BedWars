package com.Cytexal.BedWars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Wächter implements Listener {

	IronGolem g;
	String team;

	public Wächter(String team, Location loc) {
		this.team = team;
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
		g = (IronGolem) loc.getWorld().spawnEntity(loc, EntityType.IRON_GOLEM);
		g.setCustomNameVisible(true);
		g.setCustomName(team);
		Material blocks = Material.valueOf(Main.getInstance().getConfig()
				.getString("Arena." + Main.Arena + ".Team." + team.replace("§", "&") + ".Item"));
		short data = (short) Main.getInstance().getConfig()
				.getInt("Arena." + Main.Arena + ".Team." + team.replace("§", "&") + ".ItemData");
		ItemStack blöcke = new ItemStack(blocks, 1, data);
		g.getEquipment().setItemInHand(blöcke);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				for (Entity e : g.getNearbyEntities(8, 8, 8)) {
					if (e instanceof Player) {
						Player p = (Player) e;
						if (Main.Teams.containsKey(e)) {
							if (!Main.Teams.get(e).equals(team)) {
								g.setTarget(p);
							}
						}
					}
				}
			}
		}, 0, 20);

	}

	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if (e.getEntity() == g) {
			if (e.getDamager() instanceof Player) {
				if (Main.Teams.get(e.getDamager()).equals(team)) {
					e.setCancelled(true);
				}

			}
		}
	}

}
