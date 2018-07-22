package com.Cytexal.BedWars;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Teleport implements Runnable{

	
	ArrayList<Item> knochen =new ArrayList<Item>(); 
	ArrayList<Bat> stands =new ArrayList<Bat>(); 
	int pid;
	int counter;
	int heal;
	Location loc;
	Plugin pl;
	String name;
	Player p;
	
	public Teleport(Plugin pl ,Player p, int time, Location target){
		  counter = time;
		  this.p = p;
		  this.loc = target;
		  this.pl = pl;
		  pid=Bukkit.getScheduler().scheduleSyncRepeatingTask(pl,this,0,20);
		 }

	@Override
	public void run() {
		
		if(counter > 0){
			counter --;
			for(Player a: Bukkit.getOnlinePlayers())
			{
				a.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 100, 1);
			}
			if(counter == 3)
			{
				  p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*8, 2), true);
			}
		}else{
			p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 100, 1);
			Bukkit.getScheduler().cancelTask(pid);
			p.teleport(loc);
			p.getActivePotionEffects().clear();
			p.sendMessage(Utils.prefix + "§7Du wurdest zurück in deine §aBasis §7teleportiert");
			p.getInventory().remove(Material.GLOWSTONE_DUST);
			}
		}
		
	}
	
