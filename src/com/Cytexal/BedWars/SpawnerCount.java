package com.Cytexal.BedWars;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

public class SpawnerCount implements Runnable {
	Plugin pl;
	public int pid;
	int Sek;
	Location loc;
	SpawnerTyp Typ;
	int intervall;
	public SpawnerCount(String arena, SpawnerTyp Typ,Plugin pl ,int intervall,Location loc){
		  this.pl = pl;
		  this.Typ = Typ;
		  this.loc = loc;
		  this.Sek = intervall;
		  this.intervall = intervall;
		  
		  pid=Bukkit.getScheduler().scheduleSyncRepeatingTask(pl,this,0,20);
		 }

	@Override
	public void run() {
		Sek -= 1;
		
		if(Sek <= 0){
			Sek = intervall;
			
			if(Typ == SpawnerTyp.BRONZE)
			{
				loc.getWorld().dropItem(loc, Utils.createItem(Material.CLAY_BRICK, "§cBronze", 1, null));
			}
			if(Typ == SpawnerTyp.EISEN)
			{
				loc.getWorld().dropItem(loc, Utils.createItem(Material.IRON_INGOT, "§7Eisen", 1, null));
			}
			if(Typ == SpawnerTyp.GOLD)
			{
				loc.getWorld().dropItem(loc, Utils.createItem(Material.GOLD_INGOT, "§6Gold", 1, null));
			}
		}
			

		
	}
	public static int zufallszahl(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}
}