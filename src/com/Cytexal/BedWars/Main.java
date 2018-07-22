package com.Cytexal.BedWars;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import at.TimoCraft.TimoCloud.api.TimoCloudAPI;

public class Main extends JavaPlugin {

	public static Plugin pl;
	public static HashMap<Player, ShopMenü> menüs;
	public static HashMap<Player, TeamMenü> Teammenüs;
	public static HashMap<Player, Equipement> EquipMenüs;
	public static HashMap<String, Boolean> BettenStatus;
	public static HashMap<Player, String> Teams;
	public static HashMap<Player, Equipement> SpawnEq;
	public static ArrayList<String> Team;
	public static HashMap<Location, String> Traps;
	public static Phasen phase;
	public static Funktionen f;
	public static String Arena;
	public static Countdown countdown;
	public static boolean count = true;
	public static boolean won = false;
	public static ArrayList<ItemStack> poss;
	public static ArrayList<Player> spawnprot;
	public static HashMap<Player, Location> noMove;
	public static ArrayList<Location> blocks;
	public static boolean Countdown = false;
	public static int Maxplayers;
	public static com.Cytexal.BedWars.MySQL sql;

	public void Init() {
		SpawnEq = new HashMap<>();
		Countdown = false;
		menüs = new HashMap<>();
		Teammenüs = new HashMap<>();
		EquipMenüs = new HashMap<>();
		BettenStatus = new HashMap<>();
		Team = new ArrayList<>();
		Traps = new HashMap<>();
		won = false;
		count = false;
		blocks = new ArrayList<>();
		noMove = new HashMap<>();
		Teams = new HashMap<>();
		spawnprot = new ArrayList<>();
		poss = new ArrayList<>();
	}

	@Override
	public void onDisable() {
		TimoCloudAPI.getBukkitInstance().setState("RESTART");
	}

	public static Funktionen getFunktion() {
		return f;

	}

	public MySQL getConnection() {
		return sql;
	}

	public static void Send(Player p, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF("Connect");
		out.writeUTF(server);

		p.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
	}

	@Override
	public void onEnable() {
		Init();
		getCommand("BW").setExecutor(new BedWars_Command());
		getCommand("stats").setExecutor(new BedWars_Command());
		getCommand("start").setExecutor(new BedWars_Command());
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		pl = this;
		ItemStack stick = Utils.createItemNO(Material.STICK, "§cKnockback-Stick", 1, null);
		stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		Main.poss.add(Utils.createItemNO(Material.IRON_PICKAXE, "§7Eisen-Spitzhacke", 1, null));
		Main.poss.add(stick);
		Main.poss.add(Utils.createItemNO(Material.ARROW, "§7Pfeil", 1, null));
		Main.poss.add(Utils.createItemNO(Material.CAKE, "§fKuchen", 1, null));
		Main.poss.add(Utils.createItem(Material.LADDER, "§cLeiter", 32, null));
		Main.poss.add(Utils.createItem(Material.STRING, "§6Falle", 1, null));
		Main.poss.add(Utils.createItemNO(Material.WOOD_SWORD, "§cHolzschwert", 1, null));
		Main.poss.add(Utils.createItemNO(Material.IRON_SWORD, "§7Eisenschwert", 1, null));
		Main.poss.add(Utils.createItemNO(Material.COOKED_BEEF, "§8Steak", 20, null));
		Main.poss.add(Utils.createItemNO(Material.COOKED_BEEF, "§8Steak", 20, null));
		Main.poss.add(Utils.createItemNO(Material.CLAY_BRICK, "§cBronze", 35, null));
		Main.poss.add(Utils.createItemNO(Material.CLAY_BRICK, "§cBronze", 25, null));
		Main.poss.add(Utils.createItemNO(Material.IRON_INGOT, "§7Eisen", 5, null));
		Main.poss.add(Utils.createItemNO(Material.IRON_INGOT, "§7Eisen", 8, null));
		Main.poss.add(Utils.createItemNO(Material.GOLDEN_APPLE, "§6Goldapfel", 1, null));
		Main.poss.add(Utils.createPotion(Material.POTION, "§dHeilung", 1, (byte) 8261, null));
		Main.poss.add(Utils.createPotion(Material.POTION, "§3Regenration", 1, (byte) 8193, null));
		Main.poss.add(Utils.createPotion(Material.POTION, "§cStärke", 1, (byte) 8201, null));
		Main.poss.add(Utils.createPotion(Material.POTION, "§aSprungkraft", 1, (byte) 8267, null));
		Main.poss.add(Utils.createItem(Material.WEB, "§fSpinnennetz", 5, null));
		Main.poss.add(Utils.createItem(Material.SULPHUR, "§dTeleporter", 1, null));
		Main.poss.add(Utils.createItem(Material.FISHING_ROD, "§9Angel", 1, null));
		Main.poss.add(Utils.createItem(Material.TNT, "§cSprengstoff", 1, null));
		Main.poss.add(Utils.createItem(Material.FIREWORK_CHARGE, "§dBlendgranate", 1, null));
		Main.poss.add(Utils.createItem(Material.ENDER_PEARL, "§5Enderperle", 1, null));
		Main.poss.add(Utils.createItem(Material.ENDER_STONE, "§eEndstein", 30, null));
		Main.poss.add(Utils.createItem(Material.GLASS, "§9Glas", 30, null));
		Main.poss.add(Utils.createItem(Material.WOOD, "§eHolz", 30, null));
		Bukkit.getPluginManager().registerEvents(new PlayerChatComplete(), getInstance());
		Bukkit.getPluginManager().registerEvents(new InteractEvent(), getInstance());
		Bukkit.getPluginManager().registerEvents(new MineCart(), getInstance());
		Bukkit.getPluginManager().registerEvents(new BedDestroy(), getInstance());
		reloadConfig();
		ArrayList<String> arenen = new ArrayList<>();
		arenen.addAll(Main.getInstance().getConfig().getStringList("Arenen"));
		Collections.shuffle(arenen);
		Main.Arena = arenen.get(0);
		
		sql = new MySQL();
		new Funktionen(this);
		Broadcaster();
		Main.phase = Phasen.LOBBY;
		TimoCloudAPI.getBukkitInstance().setState("ONLINE");
		TimoCloudAPI.getBukkitInstance().setExtra(""+Main.Arena);
		try {
			setMaxPlayers(4);
		} catch (ReflectiveOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void setMaxPlayers(int slots)
		    throws ReflectiveOperationException
		  {
		    Object playerlist = getServer().getClass().getDeclaredMethod("getHandle", new Class[0]).invoke(getServer(), new Object[0]);
		    Field maxPlayers = playerlist.getClass().getSuperclass().getDeclaredField("maxPlayers");
		    maxPlayers.setAccessible(true);
		    maxPlayers.set(playerlist, Integer.valueOf(slots));
		  }
	public static Plugin getInstance() {
		return pl;
	}

	private void Broadcaster() {
		Random rnd = new Random();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					int message = rnd.nextInt(6);
					String msg = "";
					switch (message) {
					case 0:

						msg = "§8▏ §6✦ §8▏  §7Du bist aktuell auf Rang §d" + f.getRank(p.getUniqueId())
								+ " §7im gesamt Ranking §8▏ §6✦ §8▏ ";

						break;

					case 1:

						msg = "§8▏ §6✦ §8▏  §7Dein aktueller Score beträgt §d" + f.getPunkte(p.getUniqueId())
								+ " §7Punkte §8▏ §6✦ §8▏ ";

						break;

					case 2:
						int games = f.getLoses(p.getUniqueId()) + f.getWins(p.getUniqueId());
						msg = "§8▏ §6✦ §8▏  §7Du hast bereits §d" + games
								+ " §7Runden §cBed§fWars §7gespielt §8▏ §6✦ §8▏ ";

						break;
					case 4:

						msg = "§8▏ §6✦ §8▏  §7Du hast bereits §d" + f.getKills(p.getUniqueId())
								+ " §7Spieler §cgetötet §8▏ §6✦ §8▏ ";

						break;
					case 5:

						msg = "§8▏ §6✦ §8▏  §7Du bist bereits §d" + f.getLoses(p.getUniqueId())
								+ " §7mal §cgestorben §8▏ §6✦ §8▏ ";

						break;
					case 6:

						msg = "§8▏ §6✦ §8▏  §7Du hast bereits §d" + f.getBetten(p.getUniqueId())
								+ " §7Betten §czerstört §8▏ §6✦ §8▏ ";

						break;
					}

					ActionBarAPI.sendActionBar(p, msg);
				}
			}
		}, 0, 20 * 20);

	}

}
