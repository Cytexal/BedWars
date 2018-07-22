package com.Cytexal.BedWars;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BedDestroy implements Listener {

	@EventHandler
	public void BlockPlace(BlockPlaceEvent e) {
		if(e.getBlockPlaced().getType()==Material.TNT)
		{
			e.setCancelled(true);
			if(e.getPlayer().getItemInHand().getAmount() > 1)
			{
				e.getPlayer().getItemInHand().setAmount(e.getItemInHand().getAmount()-1);
			}
			else
			{
				e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			}
			TNTPrimed tnt = (TNTPrimed) e.getBlockPlaced().getWorld().spawnEntity(e.getBlockPlaced().getLocation(), EntityType.PRIMED_TNT);
		}
		else 
		{
		Main.blocks.add(e.getBlock().getLocation());
		}
	}
		
	
	
	@EventHandler
	public void BedBreak(BlockBreakEvent e) {
		if(e.getPlayer().getGameMode()==GameMode.CREATIVE)
		{
			return;
		}
		
		if(Main.phase == Phasen.INGAME)
		{
			if(e.getBlock().getType() == Material.BED_BLOCK)
			{
			Block mat = e.getBlock().getWorld().getBlockAt(new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY() - 1, e.getBlock().getLocation().getZ()));
			
			for (String s : Main.Team) {
				Material m = Material
						.valueOf(Main.getInstance().getConfig().getString("Arena." + Main.Arena + ".Team." + s + ".Item"));
				int b = Main.getInstance().getConfig().getInt("Arena." + Main.Arena + ".Team." + s + ".ItemData");
				
				if(m == mat.getType() && mat.getData() == b)
				{
					if(s.replace("&", "§").equals(Main.Teams.get(e.getPlayer())))
					{
						Random rnd = new Random();
						switch(rnd.nextInt(3))
						{
						case 0:
							e.getPlayer().sendMessage(Utils.prefix+"Du solltest dein §aeigenes Bett §7lieber in Ruhe lassen");
							break;
						case 1:
							e.getPlayer().sendMessage(Utils.prefix+"Finger §cweg§7!");
							break;
						case 2:
							e.getPlayer().sendMessage(Utils.prefix+"Lass das in §cRuhe");
							break;
						}
						e.setCancelled(true);
						return;
					}
					
					
					e.getBlock().setType(Material.AIR);
					Main.BettenStatus.put(s, false);
					for(Player p : Bukkit.getOnlinePlayers())
					{
						p.playSound(p.getLocation(), Sound.WITHER_DEATH, 1, 100);
						Scoreboard.updateScoreboard(p);
					}
					Main.f.addBett(e.getPlayer());
					e.getPlayer().sendMessage(Utils.prefix+"§a+50 Rang-Punkte");
					Main.f.setPunkte(e.getPlayer(), Main.f.getPunkte(e.getPlayer().getUniqueId())+50);
					
					
					Bukkit.broadcastMessage(Utils.prefix + "§" + Main.Teams.get(e.getPlayer()).charAt(1)+ e.getPlayer().getName() + " §7hat das Bett von Team " + s.replace("&", "§") + " §7zerstört");
					
					
					int b1 = 0;
		            for (Player a : Main.Teams.keySet())
		            {
		            	if(Main.Teams.get(a).equals(s.replace("&", "§")))
		            	{
		            		b1++;
		            	}
		            }
		            
		            if(b1 == 0)
		            {
		            	Bukkit.broadcastMessage(" ");
		            	Bukkit.broadcastMessage(Utils.prefix + "Das Team " + s.replace("&", "§"));
		            	Bukkit.broadcastMessage(Utils.prefix + "wurde vollständig besiegt.");
		 	            Main.BettenStatus.remove(s.replace("§", "&"));
		 	            for (Player a : Bukkit.getOnlinePlayers())
		 	            {
		 	            Scoreboard.updateScoreboard(a);
		 	            }
		            }
					
					
					
					break;
				 }
				
				
			  }
			
			
			}
			else
			{
					if(Main.blocks.contains(e.getBlock().getLocation()))
					{
					Main.blocks.remove(e.getBlock().getLocation());
					if(e.getBlock().getType() == Material.WEB)
					{
						e.getBlock().setType(Material.AIR);
					}
					}
					else
					{
					if(e.getBlock().getType() == Material.GRASS || e.getBlock().getType() == Material.LONG_GRASS || e.getBlock().getType() == Material.YELLOW_FLOWER || e.getBlock().getType() == Material.RED_ROSE)
					{
						e.getBlock().setType(Material.AIR);
					}
					else
					{
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
