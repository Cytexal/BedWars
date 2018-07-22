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

public class EquipmentAnimation implements Runnable{


	int pid;
	int counter;
	int slot;
	Plugin pl;
	Player p;
	Random rnd = new Random();
	
	public EquipmentAnimation(Plugin pl ,Player p, int slot, int time){
		  this.p = p;
		  this.counter = time;
		  this.slot = slot;
		  this.pl = pl;
		  pid=Bukkit.getScheduler().scheduleSyncRepeatingTask(pl,this,0,3);
		 }

	@Override
	public void run() {
		
		if(counter > 0){
			counter --;
			p.playSound(p.getLocation(), Sound.NOTE_BASS, 100, 1);
			Main.EquipMenüs.get(p).inv.setItem(slot, Main.poss.get(rnd.nextInt(Main.poss.size())));
		}else{
			if(slot == 16)
			{
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 100, 1);
			p.sendMessage(Utils.prefix+"Ordne die §eItems §7jetzt in §adeinem Inventar §7an");
			p.sendMessage(Utils.prefix+"§cdie Anordnung bleibt bei jedem respawn erhalten");
			Main.EquipMenüs.get(p).clickable = true;
			}
			else
			{
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 100, 1);	
			}
			Bukkit.getScheduler().cancelTask(pid);
			}
		}
		
	}
	
