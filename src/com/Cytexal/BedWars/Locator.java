package com.Cytexal.BedWars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Locator {

	
	public static Location toLocation(String ConfigPath, String yaw,String pitch)
	{
		World w = Bukkit.getWorld(Main.getInstance().getConfig().getString(ConfigPath+".World"));
		double x = Main.getInstance().getConfig().getDouble(ConfigPath+".X");
		double y = Main.getInstance().getConfig().getDouble(ConfigPath+".Y");
		double z = Main.getInstance().getConfig().getDouble(ConfigPath+".Z");
		
		return new Location(w, x, y, z, Float.parseFloat(yaw), Float.parseFloat(pitch));
		
	}
	
	
	public static Location toShopLocation(String ConfigPath)
	{
		World w = Bukkit.getWorld(Main.Arena);
		double x = Main.getInstance().getConfig().getDouble(ConfigPath+".X");
		double y = Main.getInstance().getConfig().getDouble(ConfigPath+".Y");
		double z = Main.getInstance().getConfig().getDouble(ConfigPath+".Z");
		
		return new Location(w, x, y, z,0,0);
		
	}
}
