package com.Cytexal.BedWars;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamMenü implements Listener {

	Inventory inv;
	String Arena;

	public TeamMenü(Player p, String Arena) {
		this.Arena = Arena;
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	
		Inventory Menü = Bukkit.createInventory(p, 9, "§cBed§fWars §7- §fTeams");
		inv = Menü;
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
		for (String s : Main.Team) {
			Material m = Material
					.valueOf(Main.getInstance().getConfig().getString("Arena." + Arena + ".Team." + s + ".Item"));
			ItemStack TeamItem = new ItemStack(m, 1,
					(byte) Main.getInstance().getConfig().getInt("Arena." + Arena + ".Team." + s + ".ItemData"));

			ItemMeta TeamItemMeta = TeamItem.getItemMeta();
			TeamItemMeta.setDisplayName("§cTeam: §3" + s.replace("&", "§"));
			ArrayList<String> lore = new ArrayList<>();
			lore.add("§cSpieler:");
			int Player = 0;
			for (Player pa : Main.Teams.keySet()) {

				if (Main.Teams.get(pa).equals(s.replace("&", "§"))) {
					lore.add("§f+ " + pa.getName());
					Utils.addGlowEffect(TeamItem);
					TeamItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					Player++;
				}
			}
			lore.add(" ");
			TeamItem.setAmount(Player);
			lore.add("§7(§c" + Player + "§7/§f" + Main.getInstance().getConfig().get("Arena." + Arena + ".Teamsize")
					+ "§7)");
			TeamItemMeta.setLore(lore);
			TeamItem.setItemMeta(TeamItemMeta);
			Menü.addItem(TeamItem);
			
		}
		inv = Menü;
		p.openInventory(Menü);
		Main.Teammenüs.put(p, this);

	}

	@EventHandler
	public void Close(InventoryCloseEvent e) {
		if (Main.Teammenüs.get(e.getPlayer()).inv == inv) {
			Scoreboard.updateScoreboard((Player) e.getPlayer());
			if (!Main.Teams.containsKey(e.getPlayer())) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

					@Override
					public void run() {
						e.getPlayer().sendMessage(Utils.prefix + "§7Bitte wähle ein §cTeam");
						HandlerList.unregisterAll(Main.Teammenüs.get(e.getPlayer()));
						new TeamMenü((Player) e.getPlayer(), Arena);
					}
				}, 1);
			}
			else
			{
			HandlerList.unregisterAll(Main.Teammenüs.get(e.getPlayer()));
			}
		}
	}

	@EventHandler
	public void Click(InventoryClickEvent e) {
		if (Main.Teammenüs.get((Player)e.getWhoClicked()).inv == inv) {

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
			
			
			
			Main.getInstance().reloadConfig();
			e.setCancelled(true);
			Scoreboard.updateScoreboard((Player) e.getWhoClicked());
			if(e.getCurrentItem().hasItemMeta())
			{
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Team"))
			{
			String Team = e.getCurrentItem().getItemMeta().getDisplayName().split(":")[1].substring(3,
					e.getCurrentItem().getItemMeta().getDisplayName().split(":")[1].length());
			
				int Player = 0;
				for (Player pa : Main.Teams.keySet()) {
					if (Main.Teams.get(pa).equals(Team.replace("&", "§"))) {
						Player++;
					}
				}
			
			if (Player == Main.getInstance().getConfig()
					.getInt("Arena." + Arena + ".Teamsize")) {
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(Utils.prefix + "§7Dieses Team ist bereits §cvoll");
				e.getView().close();
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						HandlerList.unregisterAll(Main.Teammenüs.get((org.bukkit.entity.Player)e.getWhoClicked()));
						new TeamMenü((org.bukkit.entity.Player) e.getWhoClicked(), Arena);
					}
				},10);
			} else {
				Main.Teams.put((Player) e.getWhoClicked(), Team);
				e.getView().close();
				e.getWhoClicked().sendMessage(Utils.prefix + "§7Du bist jetzt in Team " + Team);
				for(Player g : Main.Teams.keySet())
				{
					g.setPlayerListName("§" + Main.Teams.get(g).charAt(1) + g.getName());
				}
				HandlerList.unregisterAll(Main.Teammenüs.get((org.bukkit.entity.Player)e.getWhoClicked()));
			}
			}
			}
		}
	}
}
