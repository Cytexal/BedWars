package com.Cytexal.BedWars;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Countdown implements Runnable {
	Plugin pl;
	public int Sek;
	public int pid;
	String prefix;
	Phasen phase;
	
	public Countdown(Plugin pl ,int sek, int time, String prefix, Phasen phase){
		if(Main.Countdown != false)
		{
			return;
		}
		try {
			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				
				@Override
				public void run() {
					for(Entity e : Bukkit.getWorld(Main.Arena).getEntities())
					{
						e.remove();
					}
				}
			},20*8);
		} catch (Exception a) {
			a.printStackTrace();
			Bukkit.broadcastMessage("§8[§dDragonAttack§8] §7Ein Fehler ist aufgetreten: " + a);
		}
		
		
		
		
		Main.getInstance().reloadConfig();
		Main.phase = Phasen.LOBBY;
		ArrayList<String> teams = new ArrayList<>();
		teams.addAll(Main.getInstance().getConfig().getStringList("Arena." + Main.Arena + ".Teams"));
		Main.Team.addAll(teams);
		Main.Maxplayers = (Main.getInstance().getConfig().getInt("Arena." + Main.Arena + ".Teamsize")*Main.Team.size());
		try {
			((Main) Main.getInstance()).setMaxPlayers(Main.Maxplayers = (Main.getInstance().getConfig().getInt("Arena." + Main.Arena + ".Teamsize")*Main.Team.size()));
		} catch (ReflectiveOperationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.pl = pl;
		  this.prefix = prefix;
		  this.phase = phase;
		  Main.Countdown = true;
		  this.Sek = sek;
		  pid=Bukkit.getScheduler().scheduleSyncRepeatingTask(pl,this,0,time);
		 }

	@Override
	public void run() {
		
		if(Main.count&&Bukkit.getOnlinePlayers().size() > 1)
		{
		if(Sek > 0){
			for(Player p : Bukkit.getOnlinePlayers())
			{
				
				if(Main.phase == Phasen.LOBBY){
				Scoreboard.updateScoreboard(p);
				}
				p.setLevel(Sek);
			}
			
			if(Main.Team.size()==2)
			{
				Main.Maxplayers = 4;
				try {
					((Main) Main.getInstance()).setMaxPlayers(4);
				} catch (ReflectiveOperationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
			Main.Maxplayers = (Main.getInstance().getConfig().getInt("Arena." + Main.Arena + ".Teamsize")*Main.Team.size())-1;
			try {
				((Main) Main.getInstance()).setMaxPlayers(Main.Maxplayers = (Main.getInstance().getConfig().getInt("Arena." + Main.Arena + ".Teamsize")*Main.Team.size())-1);
			} catch (ReflectiveOperationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			if(Sek == 180 || Sek == 120 || Sek == 60 || Sek == 30 || Sek == 15 || Sek == 10 ||Sek == 5 ||Sek == 4 || Sek == 3 ||  Sek == 2 || Sek == 1)
			for(Player p : Bukkit.getOnlinePlayers())
			{
				
				p.sendMessage(Utils.prefix+"§7" + prefix + " §c" + Sek + " Sekunden");
			}
			
			if(Sek == 6)
			{
				for(Player a : Bukkit.getOnlinePlayers())
				{
					
					if(!Main.Teams.containsKey(a))
					{
						for(String team : Main.Team)
						{
							int x = 0;
							for(Player e : Main.Teams.keySet())
							{
								
								if(Main.Teams.get(e).equals(team.replace("&", "§")))
								{
									x++;
								}
							}
							if(x<Main.getInstance().getConfig()
									.getInt("Arena." + Main.Arena + ".Teamsize"))
							{
							Main.Teams.put(a, team.replace("&", "§"));
							a.sendMessage(Utils.prefix + "§7Du wurdest automatisch dem Team " + team.replace("&", "§") + " §7zugewiesen");
							a.setPlayerListName("§" + team.charAt(1) + a.getName());
							a.getOpenInventory().close();
							break;
							}
						}
					}
				}
			}
		
			if(Sek == 5)
			{
				Location loc = Locator.toLocation("Arena."+Main.Arena+".Spectator.Location", "0","0");
				loc.setY(loc.getY() + 2);
				for(Player a: Bukkit.getOnlinePlayers())
				{
					//Main.noMove.put(a, loc);
					a.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*5, 2));
					//a.teleport(loc);
				}
				for(String team : Main.Team)
				{
					int x = 0;
					for(Player e : Main.Teams.keySet())
					{
						
						if(Main.Teams.get(e).equals(team.replace("&", "§")))
						{
							x++;
						}
						Main.spawnprot.remove(e);
					}
					if(x!=0)
					{
					Main.BettenStatus.put(team, true);
					}
				}
				Main.phase = this.phase;
			}
			
			Sek--;
			
		}else{
			new Spawner(Main.Arena);
			
			Location loc = Locator.toLocation("Arena."+Main.Arena+".Spectator.Location", "0", "0");
			loc.setX(loc.getY() - 1);
			loc.getBlock().setType(Material.BARRIER);
					for(Entity e: loc.getWorld().getEntities())
					{
						e.remove();
					}
			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				
				@Override
				public void run() {
					for(int i = 1; i<= Main.getInstance().getConfig().getInt("Arena."+Main.Arena+".Shops");i++)
					{
						new ShopNPC(Main.Arena, i);
					}
					
				}
			},20*13);
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(!Main.Teams.containsKey(p))
				{
					continue;
				}
				
				p.setLevel(0);
				p.getInventory().clear();
				p.setVelocity(p.getVelocity().setY(4.0F));
				Main.spawnprot.add(p);
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 100, 1);
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 4));
			  Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				
				@Override
				public void run() {
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 100, 1);
				
				p.teleport(Locator.toLocation("Arena."+ Main.Arena +".Team."+ Main.Teams.get(p).replace("§", "&") +".Spawn", Main.getInstance().getConfig().getString("Arena."+Main.Arena+".Team."+Main.Teams.get(p).replace("§", "&") +".Spawn.Yaw"), Main.getInstance().getConfig().getString("Arena."+Main.Arena+".Team."+Main.Teams.get(p).replace("§", "&") +".Spawn.Pitch")));
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						Main.SpawnEq.put(p, new Equipement(p));
						
						
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
							
							@Override
							public void run() {
								Main.spawnprot.remove(p);
								p.sendMessage(Utils.prefix+"§a+10 Rang-Punkte");
								Main.f.setPunkte(p, Main.f.getPunkte(p.getUniqueId())+10);
							}
						},20*5);
					}
				},20*2);
			}
			},20*4);	
			Scoreboard.updateScoreboard(p);
			p.sendMessage(Utils.prefix+"§7Möge das bessere §cTeam §7gewinnen");
			}
			
			
			Bukkit.getScheduler().cancelTask(pid);
		}
			
	  }
		
	}
	public static int zufallszahl(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}
}