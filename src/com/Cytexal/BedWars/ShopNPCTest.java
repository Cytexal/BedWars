package com.Cytexal.BedWars;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShopNPCTest implements Listener{

	Entity Zombie;
	String Arena;
	
	public ShopNPCTest(String Arena, int ID,Player p) {
		this.Arena = Arena;
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
		Random rnd = new Random();
		Location spawn = p.getLocation().add(rnd.nextInt(8)-5,rnd.nextInt(8)-5,rnd.nextInt(8)-5);
		Material m = Material.valueOf(Main.getInstance().getConfig().getString("Arena."+Arena+".Shop."+ID+".Item"));
		Villager z = (Villager) spawn.getWorld().spawnEntity(spawn, EntityType.VILLAGER);
		z.setCustomName("§eShop");
		z.setCanPickupItems(false);
		z.setCustomNameVisible(true);
		ItemStack a = new ItemStack(m,1,(byte) Main.getInstance().getConfig().getInt("Arena."+Arena+".Shop."+ID+".ItemData"));
		z.getEquipment().setHelmet(a);
		z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 5));
		Zombie = z;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if(z.getLocation().getX() != spawn.getX() || z.getLocation().getZ() != spawn.getZ())
				{
					z.teleport(new Location(spawn.getWorld(), spawn.getX(), z.getLocation().getY(), spawn.getZ()));
				}
			}
		}, 0, 2);
		
	}
	
	
	
	@EventHandler
	public void hit(EntityDamageEvent e)
	{
		if(e.getEntity() == Zombie)
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void Interact(PlayerInteractEntityEvent e)
	{
		if(e.getRightClicked() == Zombie)
		{
			Player p = e.getPlayer();
			e.setCancelled(true);
		p.sendMessage("Click :)");
		}
	}
}
