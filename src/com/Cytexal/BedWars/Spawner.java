package com.Cytexal.BedWars;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Spawner {

	public Spawner(String Arena)
	{
		for(int i = 1; i<= Main.getInstance().getConfig().getInt("Arena."+Arena+".Spawner");i++)
		{
				
			Location loc = Locator.toLocation("Arena."+Arena+".Spawn."+i+".Location", "0", "0");
			if(Main.getInstance().getConfig().getString("Arena."+Arena+".Spawn."+i+".Item").equals("BRONZE"))
			{
				new SpawnerCount(Arena, SpawnerTyp.BRONZE,Main.getInstance(), 1, loc);
			}
			if(Main.getInstance().getConfig().getString("Arena."+Arena+".Spawn."+i+".Item").equals("EISEN"))
			{
				new SpawnerCount(Arena, SpawnerTyp.EISEN,Main.getInstance(), 8, loc);
			}
			if(Main.getInstance().getConfig().getString("Arena."+Arena+".Spawn."+i+".Item").equals("GOLD"))
			{
				new SpawnerCount(Arena, SpawnerTyp.GOLD,Main.getInstance(), 20, loc);
			}
		}
	}
	
}
