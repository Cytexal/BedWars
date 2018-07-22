package com.Cytexal.BedWars;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class BedWars_Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String args[]) {

		if (cmd.getName().equalsIgnoreCase("BW") || cmd.getName().equalsIgnoreCase("BedWars")) {
			Player p = (Player) sender;
			if(!p.isOp())
			{
				return false;
			}
			if(args.length == 0){
				
				p.sendMessage(Utils.prefix +"Arena Befehle: §c/BW arena");
				p.sendMessage(Utils.prefix +"Arena Befehle: §c/BW lobby");
				p.sendMessage(Utils.prefix +"Arena Befehle: §c/BW join");
			}
			else
			{
				if(args.length < 3)
				{
					if(args[0].equals("villagertest"))
					{
						Main.Arena = "Tropical";
						for(int i = 1; i<= Main.getInstance().getConfig().getInt("Arena."+Main.Arena+".Shops");i++)
						{
							new ShopNPCTest(Main.Arena, i,p);
						}
					}
					if(args[0].equals("eq"))
					{
						Main.SpawnEq.put(p, new Equipement(p));
					}
					if(args[0].equals("villager"))
					{
						Main.Arena = "Tropical";
						for(int i = 1; i<= Main.getInstance().getConfig().getInt("Arena."+Main.Arena+".Shops");i++)
						{
							new ShopNPC(Main.Arena, i);
						}
					}
					if(args[0].equals("arena"))
					{
						p.sendMessage(Utils.prefix +"Arena speichern: §c/BW arena save [Name]");
						p.sendMessage(Utils.prefix +"Arena erstellen: §c/BW arena create [Name]");
						p.sendMessage(Utils.prefix +"Arena konfigurieren: §c/BW arena setup [Name]");
						p.sendMessage(Utils.prefix +"Spectatorpunkt: §c/BW arena spectator [Name]");
					}
					if(args[0].equals("lobby"))
					{
						Main.getInstance().getConfig().set("Teleports.Lobby.X", p.getLocation().getX());
						Main.getInstance().getConfig().set("Teleports.Lobby.Y", p.getLocation().getY());
						Main.getInstance().getConfig().set("Teleports.Lobby.Z", p.getLocation().getZ());
						Main.getInstance().getConfig().set("Teleports.Lobby.World", p.getLocation().getWorld().getName());
						Main.getInstance().getConfig().set("Teleports.Lobby.Yaw", p.getLocation().getYaw());
						Main.getInstance().getConfig().set("Teleports.Lobby.Pitch", p.getLocation().getPitch());
						p.sendMessage(Utils.prefix +"Die §aLobby §7wurde gesetzt");
						Main.getInstance().saveConfig();
					}
					if(args[0].equals("join"))
					{
						Main.Arena = args[1];
						p.sendMessage(Utils.prefix +"Probejoin der Arena §d" + args[1]);
						Join.Join(p, args[1]);
					}
				}
				
				else
				if(args.length == 3)
				{
					if(args[1].equals("create"))
					{
						String name = args[2];
						p.sendMessage(Utils.prefix +"Die Arena §c" + name + " §7wurde erstellt");
						ArrayList<String> arenen = new ArrayList<>();
						arenen.addAll(Main.getInstance().getConfig().getStringList("Arenen"));
						arenen.add(args[2]);
						Main.getInstance().getConfig().set("Arenen", arenen);
						Main.getInstance().saveConfig();
					}
					if(args[1].equals("setup"))
					{
						p.sendMessage(Utils.prefix +"Spieler pro Team setzten: §c/BW arena setup ppt [ArenaName] [Spieler]");
						p.sendMessage(Utils.prefix +"Team erstellen (TeamItem = Item in deiner Hand muss Block sein): §c/BW arena setup team [ArenaName] [Teamname]");
						p.sendMessage(Utils.prefix +"Team spawn setzten: §c/BW arena setup teamspawn [ArenaName] [Teamname]");
						p.sendMessage(Utils.prefix +"Shop erstellen: §c/BW arena setup Shop");
					}
					if(args[1].equals("save"))
					{
						p.sendMessage(Utils.prefix +"Die Karte für die Arena §d"+args[2]+" §7wurde gespeichert");
					}
					if(args[1].equals("spectator"))
					{
						p.sendMessage(Utils.prefix +"Der Spectatorspawn für die Arena §d"+args[2]+" §7wurde gespeichert");
						Main.getInstance().getConfig().set("Arena."+args[2]+".Spectator.Location.World", p.getLocation().getWorld().getName());
						Main.getInstance().getConfig().set("Arena."+args[2]+".Spectator.Location.X", p.getLocation().getX());
						Main.getInstance().getConfig().set("Arena."+args[2]+".Spectator.Location.Y", p.getLocation().getY());
						Main.getInstance().getConfig().set("Arena."+args[2]+".Spectator.Location.Z", p.getLocation().getZ());
						Main.getInstance().saveConfig();
					}
					if(args[1].equals("reset"))
					{
						p.sendMessage(Utils.prefix +"Die Karte für die Arena §d"+args[2]+" §7wurde zurückgesetzt");
					}
					
				}
				else
				{
					
					
					if(args[2].equals("ppt"))
					{
						try{
							String Arena = args[3];
							int Spieler = Integer.parseInt(args[4]);
							Main.getInstance().getConfig().set("Arena." + Arena+ ".Teamsize", Spieler);
							Main.getInstance().saveConfig();
							p.sendMessage(Utils.prefix +"In der Arena §d" + Arena + " §7spielen jetzt §c" + Spieler + " Spieler §7pro Team");
							
						}
						catch (Exception e) {
							p.sendMessage(Utils.prefix +"Spieler pro Team setzten: §c/BW arena setup ppt [ArenaName] [Spieler]");
						}
						
						
					}
					
					
					if(args[2].equals("spawner"))
					{
						try{
							String Arena = args[3];
							String Spawner =args[4];
							if(Main.getInstance().getConfig().get("Arena."+Arena+".Spawner") == null)
							{
								Main.getInstance().getConfig().set("Arena."+Arena+".Spawner",1);
							}
							else
							{
								Main.getInstance().getConfig().set("Arena."+Arena+".Spawner",Main.getInstance().getConfig().getInt("Arena."+Arena+".Spawner")+1);	
							}
							
							
							
							
							
							Main.getInstance().getConfig().set("Arena."+Arena+".Spawn."+Main.getInstance().getConfig().get("Arena."+Arena+".Spawner")+".Location.World", p.getLocation().getWorld().getName());
							Main.getInstance().getConfig().set("Arena."+Arena+".Spawn."+Main.getInstance().getConfig().get("Arena."+Arena+".Spawner")+".Location.X", p.getLocation().getX());
							Main.getInstance().getConfig().set("Arena."+Arena+".Spawn."+Main.getInstance().getConfig().get("Arena."+Arena+".Spawner")+".Location.Y", p.getLocation().getY());
							Main.getInstance().getConfig().set("Arena."+Arena+".Spawn."+Main.getInstance().getConfig().get("Arena."+Arena+".Spawner")+".Location.Z", p.getLocation().getZ());
							Main.getInstance().getConfig().set("Arena."+Arena+".Spawn."+Main.getInstance().getConfig().get("Arena."+Arena+".Spawner")+".Item", Spawner.toUpperCase());
							Main.getInstance().saveConfig();
							p.sendMessage(Utils.prefix +"Ein "+Spawner +"-Spawner der Arena §d" + Arena + " §7wurde erstellt es sind jetzt " + Main.getInstance().getConfig().get("Arena."+Arena+".Spawner"));
							
							
							
						}
						catch (Exception e) {
							p.sendMessage(Utils.prefix +"SpawnerTypen: EISEN,GOLD,BRONZE");
						}
						
						
					}
					
					if(args[2].equals("teamspawn"))
					{
						try{
							String Arena = args[3];
							String TeamName = args[4];
							Main.getInstance().getConfig().set("Arena."+Arena+".Team."+TeamName+".Spawn.World", p.getLocation().getWorld().getName());
							Main.getInstance().getConfig().set("Arena."+Arena+".Team."+TeamName+".Spawn.X", p.getLocation().getX());
							Main.getInstance().getConfig().set("Arena."+Arena+".Team."+TeamName+".Spawn.Y", p.getLocation().getY());
							Main.getInstance().getConfig().set("Arena."+Arena+".Team."+TeamName+".Spawn.Z", p.getLocation().getZ());
							Main.getInstance().getConfig().set("Arena."+Arena+".Team."+TeamName+".Spawn.Yaw", p.getLocation().getYaw());
							Main.getInstance().getConfig().set("Arena."+Arena+".Team."+TeamName+".Spawn.Pitch", p.getLocation().getPitch());
							Main.getInstance().saveConfig();
							p.sendMessage(Utils.prefix +"Der Spawnpunkt für Team " + args[4] + " §7liegt jetzt bei §c" + p.getLocation());
							
						}
						catch (Exception e) {
							p.sendMessage(Utils.prefix +"Team spawn setzten: §c/BW arena setup teamspawn [ArenaName] [Teamname]");
						}
					}
					
					if(args[2].equals("team"))
					{
						try{
							String Arena = args[3];
							String TeamName = args[4];
							ArrayList<String> arenen = new ArrayList<>();
							arenen.addAll(Main.getInstance().getConfig().getStringList("Arena."+Arena+".Teams"));
							arenen.add(TeamName);
							Main.getInstance().getConfig().set("Arena."+Arena+".Teams", arenen);
							Main.getInstance().getConfig().set("Arena."+Arena+".Team."+TeamName+".Item", p.getItemInHand().getType().name());
							Main.getInstance().getConfig().set("Arena."+Arena+".Team."+TeamName+".ItemData", p.getItemInHand().getData().getData());
							Main.getInstance().saveConfig();
							p.sendMessage(Utils.prefix +"In der Arena §d" + Arena + " §7spielen jetzt folgende Teams:");
							for(String Team : arenen)
							{
								p.sendMessage(Utils.prefix + "TeamName: " + Team + ", Item: " + Main.getInstance().getConfig().get("Arena."+Arena+".Team."+Team+".Item")+":"+ Main.getInstance().getConfig().get("Arena."+Arena+".Team."+Team+".ItemData"));
							}
							
						}
						catch (Exception e) {
							p.sendMessage(Utils.prefix +"Team erstellen (TeamItem = Item in deiner Hand muss Block sein): §c/BW arena setup team [ArenaName] [Teamname]");
						}
						
						
					}

					
					if(args[2].equals("shop"))
					{
						try{
							String Arena = args[3];
							
							if(Main.getInstance().getConfig().get("Arena."+Arena+".Shops") == null)
							{
								Main.getInstance().getConfig().set("Arena."+Arena+".Shops",1);
							}
							else
							{
								Main.getInstance().getConfig().set("Arena."+Arena+".Shops",Main.getInstance().getConfig().getInt("Arena."+Arena+".Shops")+1);	
							}
							
							
							
							
							
							Main.getInstance().getConfig().set("Arena."+Arena+".Shop."+Main.getInstance().getConfig().get("Arena."+Arena+".Shops")+".World", p.getLocation().getWorld().getName());
							Main.getInstance().getConfig().set("Arena."+Arena+".Shop."+Main.getInstance().getConfig().get("Arena."+Arena+".Shops")+".X", p.getLocation().getX());
							Main.getInstance().getConfig().set("Arena."+Arena+".Shop."+Main.getInstance().getConfig().get("Arena."+Arena+".Shops")+".Y", p.getLocation().getY());
							Main.getInstance().getConfig().set("Arena."+Arena+".Shop."+Main.getInstance().getConfig().get("Arena."+Arena+".Shops")+".Z", p.getLocation().getZ());
							Main.getInstance().getConfig().set("Arena."+Arena+".Shop."+Main.getInstance().getConfig().get("Arena."+Arena+".Shops")+".Item", p.getItemInHand().getType().name());
							Main.getInstance().getConfig().set("Arena."+Arena+".Shop."+Main.getInstance().getConfig().get("Arena."+Arena+".Shops")+".ItemData", p.getItemInHand().getData().getData());
							Main.getInstance().saveConfig();
							p.sendMessage(Utils.prefix +"Ein Shop der Arena §d" + Arena + " §7wurde generiert es sind jetzt " + Main.getInstance().getConfig().get("Arena."+Arena+".Shops"));
							
							
							
							
							
						}
						catch (Exception e) {
							p.sendMessage(Utils.prefix +"Shop erstellen §c/BW arena setup team [ArenaName]");
						}
						
						
					}
					
					
				}
			}
			
			
		}
		else
		if(cmd.getName().equalsIgnoreCase("stats"))
		{
			Player p = (Player) sender;
			if(args.length == 1)
			{
				OfflinePlayer a = Bukkit.getOfflinePlayer(args[0]);
				if(a == null)
				{
					p.sendMessage(Utils.prefix+"§7Der Spieler §a" + args[0] +" §7wurde nicht gefunden");
				}
				else
				{
				p.sendMessage(Utils.prefix+"§a"+a.getName()+"´s " + " §6Stats:");
				p.sendMessage(" ");
				p.sendMessage(Utils.prefix+"§aKills§7: §6" + Main.f.getKills(a.getUniqueId()));
				p.sendMessage(Utils.prefix+"§cTode§7: §6" + Main.f.getDeaths(a.getUniqueId()));
				p.sendMessage(Utils.prefix+"§eRunden§7: §6" + (Main.f.getWins(a.getUniqueId())+Main.f.getLoses(a.getUniqueId())));
				Double kills = (double) Main.f.getKills(a.getUniqueId());
				Double tode = (double) Main.f.getDeaths(a.getUniqueId());
				if(kills > 0 && tode > 0)
				{
				p.sendMessage(Utils.prefix+"§9K/D§7: §6" +myRound(kills/tode, 2));
				}
				p.sendMessage(Utils.prefix+"§d§nRang§7: §6#" + Main.f.getRank(a.getUniqueId()));
				}
			}
			else
			{
				p.sendMessage(Utils.prefix+"§6Deine Stats:");
				p.sendMessage(" ");
				p.sendMessage(Utils.prefix+"§aKills§7: §6" + Main.f.getKills(p.getUniqueId()));
				p.sendMessage(Utils.prefix+"§cTode§7: §6" + Main.f.getDeaths(p.getUniqueId()));
				Double kills = (double) Main.f.getKills(p.getUniqueId());
				Double tode = (double) Main.f.getDeaths(p.getUniqueId());
				if(kills > 0 && tode > 0)
				{
				p.sendMessage(Utils.prefix+"§9K/D§7: §6" +myRound(kills/tode, 2));
				}
				p.sendMessage(Utils.prefix+"§eRunden§7: §6" + (Main.f.getWins(p.getUniqueId())+Main.f.getLoses(p.getUniqueId())));
				p.sendMessage(Utils.prefix+"§d§nRang§7: §6#" + Main.f.getRank(p.getUniqueId()));
			}
		}
		else
			if(cmd.getName().equalsIgnoreCase("start"))
			{
				Player p = (Player) sender;
				if(p.hasPermission("Knoxh.Start"))
				{
					if(Main.phase == Phasen.LOBBY)
					{
						if(Main.countdown.Sek > 15)
						{
							p.sendMessage(Utils.prefix+"Der Countdown wurde auf §a15 Sekunden §7verringert");
							Main.countdown.Sek = 15;
						}
						else
						{
							p.sendMessage(Utils.prefix+"Der Countdown liegt bereits bei §cunter §a15 Sekunden");
						}
					}
				}
				else
				{
					p.sendMessage(Utils.prefix+"Dafür hast du keine §cBerechtigung");
				}
			}
		return false;
	}
	
	static double myRound(double wert, int stellen) {
        return  Math.round(wert * Math.pow(10, stellen)) / Math.pow(10, stellen);
    }

}