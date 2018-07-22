package com.Cytexal.BedWars;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.coloredcarrot.api.sidebar.Sidebar;
import com.coloredcarrot.api.sidebar.SidebarString;

public class Scoreboard {

	public static void updateScoreboard(Player p) {
		if (Main.phase == Phasen.LOBBY) {
			SidebarString line1 = new SidebarString("§r");
			SidebarString line2 = new SidebarString("§6Karte:");
			SidebarString line3 = new SidebarString("§8 §e➣ §a" + Main.Arena);
			SidebarString line4 = new SidebarString("§a");
			SidebarString line5 = new SidebarString("§6Team:");
			SidebarString line6;
			if (Main.Teams.containsKey(p)) {
				line6 = new SidebarString("§8 §e➣ §a" + Main.Teams.get(p));
			} else {
				line6 = new SidebarString("§8 §e➣ §c-/-");
			}
			SidebarString line7 = new SidebarString("§c");
			SidebarString line8 = new SidebarString("§6Spieler:");
			SidebarString line9 = new SidebarString(
					"§8 §e➣ §e" + Main.Teams.size() + "§9/§6" + Main.Maxplayers);
			SidebarString line10 = new SidebarString("§b");

			Sidebar scoreboard = new Sidebar("§6Knoxh.de §8– §cBed§fWars", Main.getInstance(), 60, line1, line2,
					line3, line4, line5, line6, line7, line8, line9, line10);
			scoreboard.showTo(p);
		} else {
				SidebarString line1 = new SidebarString("§r");
				SidebarString line2 = new SidebarString("§6Karte:");
				SidebarString line3 = new SidebarString("§8 §e➣ §a" + Main.Arena);
				SidebarString line4 = new SidebarString("§a");
				Sidebar scoreboard = new Sidebar("§6Knoxh.de §8– §cBed§fWars", Main.getInstance(), 60, line1, line2,
						line3, line4);
				int key = 0;
				for (String team : Main.BettenStatus.keySet()) {
					if (Main.BettenStatus.get(team)) {
						scoreboard.addEntry(new SidebarString("§"+key+"§c❤ " + team.replace("&", "§")));
					} else {
						scoreboard.addEntry(new SidebarString("§"+key+"§7❤ " + team.replace("&", "§")));
					}
					key++;
				}
				
				if(Main.BettenStatus.keySet().size() == 1 && Main.won == false)
				{
					Main.won = true;
					for(Player a : Bukkit.getOnlinePlayers())
					{
						Locator.toLocation("Teleports.Lobby",Main.getInstance().getConfig().getString("Teleports.Lobby.Yaw"), Main.getInstance().getConfig().getString("Teleports.Lobby.Pitch")).getWorld().setTime(18000);
						a.setGameMode(GameMode.SURVIVAL);
						a.setPlayerListName("§f" + a.getName());
						Main.spawnprot.add(a);
						a.getInventory().clear();
						a.setFoodLevel(20);
						a.playSound(p.getLocation(), Sound.ENDERDRAGON_DEATH, 100, 1);
						a.setHealth(a.getMaxHealth());
						a.teleport(Locator.toLocation("Teleports.Lobby",Main.getInstance().getConfig().getString("Teleports.Lobby.Yaw"), Main.getInstance().getConfig().getString("Teleports.Lobby.Pitch")));
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() 
							{
								Main.Send(a, "Lobby-1");
								
							}
						},20*10);
						
						
						if(Main.Teams.containsKey(a))
						{
							if(Main.Teams.get(a).replace("§", "&").equals(Main.BettenStatus.keySet().toArray()[0]))
							{
								new Win(Main.getInstance(),a);
								Main.f.addWin(a);
								a.sendMessage(Utils.prefix+"§a+150 Rang-Punkte");
								Main.f.setPunkte(a, Main.f.getPunkte(a.getUniqueId())+150);
							}
						}
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						
						@Override
						public void run() {
							Bukkit.shutdown();
						}
					},20*14);
					
					Bukkit.broadcastMessage(Utils.prefix+"§7Das Team " + ((String)Main.BettenStatus.keySet().toArray()[0]).replace("&", "§") + " §7hat das Spiel §agewonnen");
				}
				
				scoreboard.showTo(p);
		}
	}

}
