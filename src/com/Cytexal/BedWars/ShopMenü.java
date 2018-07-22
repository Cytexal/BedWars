package com.Cytexal.BedWars;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;

public class ShopMenü implements Listener {

	String Team;
	Inventory inv;
	Material blocks;
	int data;

	public ShopMenü(Player p, String Team, String Arena) {
		
		this.Team = Team;
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
		Utils.stack(p);
		blocks = Material.valueOf(Main.getInstance().getConfig().getString("Arena." + Arena + ".Team." + Team + ".Item"));
		data = Main.getInstance().getConfig().getInt("Arena." + Arena + ".Team." + Team + ".ItemData");
		Inventory Menü = Bukkit.createInventory(p, 18, "§cBed§fWars §7- §fShop");
		ItemStack blöcke = new ItemStack(blocks, 1, (short) data);
		ItemMeta blockMeta = blöcke.getItemMeta();
		blockMeta.setDisplayName("§8» §eBlöcke");
		blockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		blockMeta.setLore(null);
		blöcke.setItemMeta(blockMeta);

		// §8»
		Menü.setItem(0, blöcke);
		Menü.setItem(1, Utils.createItem(Material.CHAINMAIL_CHESTPLATE, "§8» §7Rüstung", 1, null));
		Menü.setItem(2, Utils.createItem(Material.IRON_PICKAXE, "§8» §5Spitzhacken", 1, null));
		Menü.setItem(3, Utils.createItem(Material.WOOD_SWORD, "§8» §dSchwerter", 1, null));
		Menü.setItem(4, Utils.createItem(Material.BOW, "§8» §eBögen", 1, null));
		Menü.setItem(5, Utils.createItem(Material.ENDER_CHEST, "§8» §9Kisten", 1, null));
		Menü.setItem(6, Utils.createItem(Material.CAKE, "§8» §cEssen", 1, null));
		Menü.setItem(7, Utils.createItem(Material.POTION, "§8» §dTränke", 1, null));
		Menü.setItem(8, Utils.createItem(Material.EMERALD, "§8» §aSpezials", 1, null));
		inv = Menü;
		p.openInventory(Menü);
		Main.menüs.put(p, this);
	}

	@EventHandler
	public void Close(InventoryCloseEvent e) {
		if (Main.menüs.get(e.getPlayer()).inv == inv) {
			HandlerList.unregisterAll(Main.menüs.get(e.getPlayer()));
		}
	}

	@EventHandler
	public void Click(InventoryClickEvent e) {
		if (Main.menüs.get(e.getWhoClicked()).inv == inv) {
			e.setCancelled(true);
			if (e.getSlot() < 9) {
				for (int i = 0; i < 9; i++) {
					Main.menüs.get(e.getWhoClicked()).inv.getItem(i).removeEnchantment(Enchantment.LUCK);
				}
				for (int i = 9; i < 18; i++) {
					Main.menüs.get(e.getWhoClicked()).inv.setItem(i, Utils.createItem(Material.STAINED_GLASS_PANE, "§e", 1, null));
				}

				Main.menüs.get(e.getWhoClicked()).inv.getItem(e.getSlot()).addUnsafeEnchantment(Enchantment.LUCK, 1);

				if (e.getSlot() == 0) {

					ArrayList<String> lore = new ArrayList<>();
					lore.add(" ");
					lore.add("§7Kosten:");
					lore.add("§c1*Bronze");

					ItemStack blöcke = new ItemStack(blocks, 2,(short) data);
					ItemMeta blockMeta = blöcke.getItemMeta();
					blockMeta.setDisplayName("" + Team.replace("&", "§"));
					blockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					blockMeta.setLore(lore);
					blöcke.setItemMeta(blockMeta);

					Main.menüs.get(e.getWhoClicked()).inv.setItem(11, blöcke);

					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c3*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(12, Utils.createItem(Material.ENDER_STONE, "§eEndstein", 1, lore));

					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§71*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(13, Utils.createItem(Material.SOUL_SAND, "§7Seelensand", 1, lore));
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c4*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(14, Utils.createItem(Material.GLASS, "§9Glas", 1, lore));
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c4*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(15, Utils.createItem(Material.GLOWSTONE, "§6Glowstone", 4, lore));

				}

				if (e.getSlot() == 1) {

					ArrayList<String> lore = new ArrayList<>();
					lore.add(" ");
					lore.add("§7Kosten:");
					lore.add("§c1*Bronze");
					ItemStack blöcke = new ItemStack(Material.LEATHER_BOOTS, 1);
					LeatherArmorMeta blockMeta = (LeatherArmorMeta) blöcke.getItemMeta();
					blockMeta.setDisplayName("§cLeder-Schuhe");
					blockMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					blockMeta.setLore(lore);
					blöcke.setItemMeta(blockMeta);
					Main.menüs.get(e.getWhoClicked()).inv.setItem(12, blöcke);
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c1*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(11, Utils.createItem(Material.LEATHER_LEGGINGS, "§cLeder-Hose", 1, lore));
					lore.clear();
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c1*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(10, Utils.createItem(Material.LEATHER_HELMET, "§cLeder-Helm", 1, lore));

					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§72*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(14, Utils.createItem(Material.CHAINMAIL_CHESTPLATE, "§7Kettenbrust LV.I", 1, lore));

					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§74*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(15, Utils.createItemNO(Material.CHAINMAIL_CHESTPLATE, "§7Kettenbrust LV.II", 1, lore));
					Main.menüs.get(e.getWhoClicked()).inv.getItem(15).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§78*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(16, Utils.createItemNO(Material.CHAINMAIL_CHESTPLATE, "§7Kettenbrust LV.III", 1, lore));
					Main.menüs.get(e.getWhoClicked()).inv.getItem(16).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

				}
				if (e.getSlot() == 2) {
					ArrayList<String> lore = new ArrayList<>();
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c4*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(12, Utils.createItemNO(Material.WOOD_PICKAXE, "§cHolz-Spitzhacke", 1, lore));
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§71*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(13, Utils.createItemNO(Material.STONE_PICKAXE, "§8Stein-Spitzhacke", 1, lore));
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§74*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(14, Utils.createItemNO(Material.IRON_PICKAXE, "§7Eisen-Spitzhacke", 1, lore));
				}

				if (e.getSlot() == 3) {
					ArrayList<String> lore = new ArrayList<>();
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c4*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(11, Utils.createItemNO(Material.STICK, "§cKnockback-Stick", 1, lore));
					Main.menüs.get(e.getWhoClicked()).inv.getItem(11).addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c4*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(12, Utils.createItemNO(Material.WOOD_SWORD, "§cHolzschwert", 1, lore));
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§72*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(13, Utils.createItemNO(Material.WOOD_SWORD, "§cHolzschwert LV.I", 1, lore));
					Main.menüs.get(e.getWhoClicked()).inv.getItem(13).addEnchantment(Enchantment.DAMAGE_ALL, 1);
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§74*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(14, Utils.createItemNO(Material.WOOD_SWORD, "§cHolzschwert LV.II", 1, lore));
					Main.menüs.get(e.getWhoClicked()).inv.getItem(14).addEnchantment(Enchantment.DAMAGE_ALL, 2);
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§61*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(15, Utils.createItemNO(Material.IRON_SWORD, "§7Eisenschwert", 1, lore));
					Main.menüs.get(e.getWhoClicked()).inv.getItem(15).addEnchantment(Enchantment.DAMAGE_ALL, 1);
				}
				if (e.getSlot() == 4) {
					ArrayList<String> lore = new ArrayList<>();
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§63*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(11, Utils.createItemNO(Material.BOW, "§6Bogen", 1, lore));
					Main.menüs.get(e.getWhoClicked()).inv.getItem(11).addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
					Main.menüs.get(e.getWhoClicked()).inv.getItem(11).addEnchantment(Enchantment.ARROW_INFINITE, 1);
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§66*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(12, Utils.createItemNO(Material.BOW, "§6Bogen LV.I", 1, lore));
					Main.menüs.get(e.getWhoClicked()).inv.getItem(12).addEnchantment(Enchantment.ARROW_DAMAGE, 1);
					Main.menüs.get(e.getWhoClicked()).inv.getItem(12).addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
					Main.menüs.get(e.getWhoClicked()).inv.getItem(12).addEnchantment(Enchantment.ARROW_INFINITE, 1);
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§69*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(13, Utils.createItemNO(Material.BOW, "§6Bogen LV.II", 1, lore));
					Main.menüs.get(e.getWhoClicked()).inv.getItem(13).addEnchantment(Enchantment.ARROW_DAMAGE, 2);
					Main.menüs.get(e.getWhoClicked()).inv.getItem(13).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
					Main.menüs.get(e.getWhoClicked()).inv.getItem(13).addEnchantment(Enchantment.ARROW_INFINITE, 1);
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§61*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(15, Utils.createItemNO(Material.ARROW, "§7Pfeil", 1, lore));
				}
				if (e.getSlot() == 6) {
					ArrayList<String> lore = new ArrayList<>();
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c1*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(11, Utils.createItemNO(Material.APPLE, "§cApfel", 1, lore));
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c3*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(12, Utils.createItemNO(Material.COOKED_BEEF, "§8Steak", 1, lore));
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§72*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(13, Utils.createItemNO(Material.CAKE, "§fKuchen", 1, lore));
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§62*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(15, Utils.createItemNO(Material.GOLDEN_APPLE, "§6Goldapfel", 1, lore));
				}
				if (e.getSlot() == 5) {
					ArrayList<String> lore = new ArrayList<>();
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§71*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(13, Utils.createItemNO(Material.CHEST, "§cKiste", 1, lore));
				}
				if (e.getSlot() == 7) {
					ArrayList<String> lore = new ArrayList<>();
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§78*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(9, Utils.createPotion(Material.POTION, "§dHeilung", 1, (byte)8261, lore));
			
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§712*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(17, Utils.createPotion(Material.POTION, "§3Regenration", 1, (byte)8193, lore));
					
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§62*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(11, Utils.createPotion(Material.POTION, "§cStärke", 1, (byte)8201, lore));
		
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§78*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(13, Utils.createPotion(Material.POTION, "§aSprungkraft", 1, (byte)8267, lore));
				
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§74*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(15, Utils.createPotion(Material.POTION, "§9Nachtsicht", 1, (byte)8230, lore));
				}if (e.getSlot() == 8) {
					ArrayList<String> lore = new ArrayList<>();
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§c2*Bronze");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(9, Utils.createItem(Material.LADDER, "§cLeiter", 1, lore));
			
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§78*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(10, Utils.createItem(Material.SULPHUR, "§dTeleporter", 1, lore));
					
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§74*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(11, Utils.createItem(Material.WEB, "§fSpinnennetz", 1, lore));
		
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§78*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(12, Utils.createItem(Material.STRING, "§6Falle", 1, lore));
				
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§74*Eisen");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(13, Utils.createItem(Material.FISHING_ROD, "§9Angel", 1, lore));
					
					
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§68*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(14,Utils.createItem(Material.TNT, "§cSprengstoff", 1, lore));
					
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§62*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(15, Utils.createItem(Material.FIREWORK_CHARGE, "§dBlendgranate", 1, lore));
					
					
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§64*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(16, Utils.createItem(Material.ENDER_PEARL, "§5Enderperle", 1, lore));
					
					lore.clear();
					lore.add(" ");
					lore.add("§8Kosten:");
					lore.add("§612*Gold");
					Main.menüs.get(e.getWhoClicked()).inv.setItem(17, Utils.createItem(Material.RED_ROSE, "§8Wächter", 1, lore));
				}
				

			} else {

				ItemStack Item = e.getCurrentItem();
				if (Item.getType() == Material.STAINED_GLASS_PANE) {
					return;
				}

				Player p = (Player) e.getWhoClicked();
				String BKosten;
				String EKosten;
				if (Item.getItemMeta().getLore().size() == 3) {
					BKosten = Item.getItemMeta().getLore().get(Item.getItemMeta().getLore().size() - 1);
					int costs = BKosten.split("\\*")[0].length();
					String währung = BKosten.split("\\*")[1];

					BKosten = BKosten.split("\\*")[0].substring(2, costs);
					if (währung.equalsIgnoreCase("Bronze")) {
						ItemStack Bronze = null;
						for (ItemStack bronze : p.getInventory()) {
							if (bronze != null) {
								if (bronze.getType() == Material.CLAY_BRICK
										&& bronze.getAmount() >= Integer.parseInt(BKosten)) {
									Bronze = bronze;
									break;
								}
							}
						}
						if (Bronze != null) {
							if (Bronze.getAmount() < Integer.parseInt(BKosten)) {
								p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 100, 1);
							} else {

								
								if(e.isShiftClick())
								{
									p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 100, 1);

									while(Bronze.getAmount() - Integer.parseInt(BKosten) >= 0)
									{
									if (Bronze.getAmount() - Integer.parseInt(BKosten) == 0) {
										p.getInventory().remove(Bronze);
									}

									Bronze.setAmount(Bronze.getAmount() - Integer.parseInt(BKosten));
									ItemStack i = new ItemStack(e.getCurrentItem().getType(),
											e.getCurrentItem().getAmount(),e.getCurrentItem().getData().getData());
									ItemMeta iMeta = i.getItemMeta();
									iMeta.setDisplayName(e.getCurrentItem().getItemMeta().getDisplayName());
									if (e.getCurrentItem().getItemMeta().hasEnchants()) {
										iMeta.addEnchant(
												(Enchantment) e.getCurrentItem().getEnchantments().keySet().toArray()[0],
												e.getCurrentItem().getEnchantments().get(
														e.getCurrentItem().getEnchantments().keySet().toArray()[0]),
												true);
									}
									i.setItemMeta(iMeta);
									i.setData(e.getCurrentItem().getData());
									p.getInventory().addItem(i);
									}
								}
								else
								{
									p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 100, 1);

									if (Bronze.getAmount() - Integer.parseInt(BKosten) == 0) {
										p.getInventory().remove(Bronze);
									}

									Bronze.setAmount(Bronze.getAmount() - Integer.parseInt(BKosten));
									ItemStack i = new ItemStack(e.getCurrentItem().getType(),
											e.getCurrentItem().getAmount(),e.getCurrentItem().getData().getData());
									ItemMeta iMeta = i.getItemMeta();
									iMeta.setDisplayName(e.getCurrentItem().getItemMeta().getDisplayName());
									if (e.getCurrentItem().getItemMeta().hasEnchants()) {
										for(int a = 0; a < e.getCurrentItem().getItemMeta().getEnchants().keySet().size();a++)
										{
											iMeta.addEnchant(
													(Enchantment) e.getCurrentItem().getEnchantments().keySet().toArray()[a],
													e.getCurrentItem().getEnchantments().get(
															e.getCurrentItem().getEnchantments().keySet().toArray()[a]),
													true);
										}
									}
									i.setItemMeta(iMeta);
									i.setData(e.getCurrentItem().getData());
									p.getInventory().addItem(i);
								}
							}
						} else {
							p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 100, 1);
						}

					} else if (währung.equalsIgnoreCase("Eisen")) {
						ItemStack Bronze = null;
						for (ItemStack bronze : p.getInventory()) {
							if (bronze != null) {
								if (bronze.getType() == Material.IRON_INGOT
										&& bronze.getAmount() >= Integer.parseInt(BKosten)) {
									Bronze = bronze;
									break;
								}
							}
						}
						if (Bronze != null) {
							if (Bronze.getAmount() < Integer.parseInt(BKosten)) {
								p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 100, 1);
							} else {

								if(e.isShiftClick())
								{
									p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 100, 1);


									while(Bronze.getAmount() - Integer.parseInt(BKosten) >= 0)
									{
									if (Bronze.getAmount() - Integer.parseInt(BKosten) == 0) {
										p.getInventory().remove(Bronze);
									}

									Bronze.setAmount(Bronze.getAmount() - Integer.parseInt(BKosten));
									ItemStack i = new ItemStack(e.getCurrentItem().getType(),
											e.getCurrentItem().getAmount(),e.getCurrentItem().getData().getData());
									ItemMeta iMeta = i.getItemMeta();
									iMeta.setDisplayName(e.getCurrentItem().getItemMeta().getDisplayName());
									if (e.getCurrentItem().getItemMeta().hasEnchants()) {
										for(int a = 0; a < e.getCurrentItem().getItemMeta().getEnchants().keySet().size();a++)
										{
											iMeta.addEnchant(
													(Enchantment) e.getCurrentItem().getEnchantments().keySet().toArray()[a],
													e.getCurrentItem().getEnchantments().get(
															e.getCurrentItem().getEnchantments().keySet().toArray()[a]),
													true);
										}
									}
									i.setItemMeta(iMeta);
									p.getInventory().addItem(i);
									}
								}
								else
								{
									p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 100, 1);

									if (Bronze.getAmount() - Integer.parseInt(BKosten) == 0) {
										p.getInventory().remove(Bronze);
									}

									Bronze.setAmount(Bronze.getAmount() - Integer.parseInt(BKosten));
									ItemStack i = new ItemStack(e.getCurrentItem().getType(),
											e.getCurrentItem().getAmount(),e.getCurrentItem().getData().getData());
									ItemMeta iMeta = i.getItemMeta();
									iMeta.setDisplayName(e.getCurrentItem().getItemMeta().getDisplayName());
									if (e.getCurrentItem().getItemMeta().hasEnchants()) {
										for(int a = 0; a < e.getCurrentItem().getItemMeta().getEnchants().keySet().size();a++)
										{
											iMeta.addEnchant(
													(Enchantment) e.getCurrentItem().getEnchantments().keySet().toArray()[a],
													e.getCurrentItem().getEnchantments().get(
															e.getCurrentItem().getEnchantments().keySet().toArray()[a]),
													true);
										}
									}
									i.setItemMeta(iMeta);
									p.getInventory().addItem(i);
								}
							}
						} else {
							p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 100, 1);
						}
					} else if (währung.equalsIgnoreCase("Gold")) {
						ItemStack Bronze = null;
						for (ItemStack bronze : p.getInventory()) {
							if (bronze != null) {
								if (bronze.getType() == Material.GOLD_INGOT
										&& bronze.getAmount() >= Integer.parseInt(BKosten)) {
									Bronze = bronze;
									break;
								}
							}
						}
						if (Bronze != null) {
							if(e.isShiftClick())
							{
								p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 100, 1);

								while(Bronze.getAmount() - Integer.parseInt(BKosten) >= 0)
								{
								if (Bronze.getAmount() - Integer.parseInt(BKosten) == 0) {
									p.getInventory().remove(Bronze);
								}

								Bronze.setAmount(Bronze.getAmount() - Integer.parseInt(BKosten));
								ItemStack i = new ItemStack(e.getCurrentItem().getType(),
										e.getCurrentItem().getAmount(),e.getCurrentItem().getData().getData());
								ItemMeta iMeta = i.getItemMeta();
								iMeta.setDisplayName(e.getCurrentItem().getItemMeta().getDisplayName());
								if (e.getCurrentItem().getItemMeta().hasEnchants()) {
									iMeta.addEnchant(
											(Enchantment) e.getCurrentItem().getEnchantments().keySet().toArray()[0],
											e.getCurrentItem().getEnchantments().get(
													e.getCurrentItem().getEnchantments().keySet().toArray()[0]),
											true);
								}
								i.setItemMeta(iMeta);
								i.setData(e.getCurrentItem().getData());
								p.getInventory().addItem(i);
								}
							}
							else
							{
								p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 100, 1);

								if (Bronze.getAmount() - Integer.parseInt(BKosten) == 0) {
									p.getInventory().remove(Bronze);
								}

								Bronze.setAmount(Bronze.getAmount() - Integer.parseInt(BKosten));
								ItemStack i = new ItemStack(e.getCurrentItem().getType(),
										e.getCurrentItem().getAmount(),e.getCurrentItem().getData().getData());
								ItemMeta iMeta = i.getItemMeta();
								iMeta.setDisplayName(e.getCurrentItem().getItemMeta().getDisplayName());
								if (e.getCurrentItem().getItemMeta().hasEnchants()) {
									for(int a = 0; a < e.getCurrentItem().getItemMeta().getEnchants().keySet().size();a++)
									{
										iMeta.addEnchant(
												(Enchantment) e.getCurrentItem().getEnchantments().keySet().toArray()[a],
												e.getCurrentItem().getEnchantments().get(
														e.getCurrentItem().getEnchantments().keySet().toArray()[a]),
												true);
									}
								}
								i.setItemMeta(iMeta);
								i.setData(e.getCurrentItem().getData());
								p.getInventory().addItem(i);
							}
						} else {
							p.playSound(p.getLocation(), Sound.NOTE_BASS_DRUM, 100, 1);
						}
					}
				}
			}
		}
	}

}
