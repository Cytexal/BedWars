package com.Cytexal.BedWars;

import java.util.ArrayList;

import javax.xml.stream.Location;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import at.TimoCraft.TimoCloud.api.TimoCloudAPI;


public class Join {

	public static void Join(Player p, String Arena)
	{
		if(Main.f.getLoses(p.getUniqueId()) == -1)
		{
			Main.f.setup(p.getUniqueId());
		}
		
		Main.spawnprot.add(p);
		
		if(Bukkit.getOnlinePlayers().size() == 1)
		{
			Main.getInstance().getConfig()
			.set("Arena." + Arena + ".Teamsize",1);
			Main.getInstance().saveConfig();
		}
		if(Bukkit.getOnlinePlayers().size() == 2)
		{
			Main.getInstance().getConfig()
			.set("Arena." + Arena + ".Teamsize",1);
			Main.getInstance().saveConfig();
		}
		if(Bukkit.getOnlinePlayers().size() == 3)
		{
			Main.getInstance().getConfig()
			.set("Arena." + Arena + ".Teamsize",2);
			Main.getInstance().saveConfig();
		}
		if(Bukkit.getOnlinePlayers().size() == 4)
		{
			Main.getInstance().getConfig()
			.set("Arena." + Arena + ".Teamsize",3);
			Main.getInstance().saveConfig();
		}
		p.setGameMode(GameMode.SURVIVAL);
		p.getInventory().clear();
		p.setFoodLevel(20);
		p.setHealthScaled(true);
		p.setHealthScale(20);
		p.setMaxHealth(20);
		p.setWalkSpeed((float) 0.2);
		p.setHealth(p.getMaxHealth());
		p.getInventory().setArmorContents(null);
		TabModifizierer.setTab(p, "\n§6Knoxh.de §8» §eNetwork\n§7Du möchtest einen Hacker reporten? §c/report §7[Name]", "\n§7Erstelle §dPartys §7und entdecke einzigartige §bMinispiele§7,\nauf der Lobby oder mit: §d/party§7");
		p.setPlayerListName("§7" + p.getName());
		for(Player g : Main.Teams.keySet())
		{
			g.setPlayerListName("§" + Main.Teams.get(g).charAt(1) + g.getName());
		}
		p.getInventory().setItem(0, Utils.createItemNO(Material.BED, "§dTeamauswahl §7§o(Rechtsklick)", 1, null));
		p.getInventory().getItem(0).addUnsafeEnchantment(Enchantment.LUCK, 1);
		p.teleport(Locator.toLocation("Teleports.Lobby",Main.getInstance().getConfig().getString("Teleports.Lobby.Yaw"), Main.getInstance().getConfig().getString("Teleports.Lobby.Pitch")));
		p.sendMessage(Utils.prefix+"Karte: §d" + Main.Arena);
		
		
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta Meta = (SkullMeta) item.getItemMeta();
		Meta.setOwner(p.getName());
		Meta.setDisplayName("§aFreunde §7(Rechtsklick)");
		item.setItemMeta(Meta);
		p.getInventory().setItem(8, item);
		
		
		p.getInventory().setItem(4, Utils.createItemNO(Material.MAGMA_CREAM, "§cVerlassen §7§o(Rechtsklick)", 1, null));
		
		for(PotionEffect e : p.getActivePotionEffects())
		{
			p.removePotionEffect(e.getType());
		}
		
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if(Main.countdown==null){Main.countdown = new Countdown(Main.getInstance(), 60, 20, "Die Lobbyphase endet in", Phasen.INGAME);}
				new TeamMenü(p, Arena);
			}
		},10);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				TitleAPI.sendTitle(p, Utils.prefix);
				TitleAPI.sendSubTitle(p, "§6Map: §a" + Main.Arena);
				for(PotionEffect e : p.getActivePotionEffects())
				{
					p.removePotionEffect(e.getType());
				}
			}
		},20*4);
		
	}
	
	
	
	
	
}
