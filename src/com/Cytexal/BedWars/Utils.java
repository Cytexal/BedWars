package com.Cytexal.BedWars;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class Utils {
	public static String prefix = "§e★§8–§cBed§fWars§8–§e★ §7";
	public static String prefixCloud = "§8–§6Cloud §8–§7";
	public static String keineRechte = prefix + "§cDir fehlen die Rechte um diesen Command auszuführen!";
	public static String unbekannterCommand = prefix + "§cDiesen Command gibt es nicht!";
	public static String nichtOnline = prefix + "§cDieser Spieler ist momentan nicht Online!";

	public static ItemStack createItem(Material m, String name, int b, int a) {
		ItemStack item = new ItemStack(m, a, (byte) b);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack createItem(Material m, String name, int ammount, ArrayList<String> lore) {
		ItemStack item = new ItemStack(m, ammount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("" + name);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		StringBuilder sb = new StringBuilder(name);
		sb.deleteCharAt(0);
		sb.deleteCharAt(0);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createPotion(Material m, String name, int ammount,byte Data, ArrayList<String> lore) {
		ItemStack item = new ItemStack(m, ammount,Data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("" + name);
		item.getData().setData(Data);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		StringBuilder sb = new StringBuilder(name);
		sb.deleteCharAt(0);
		sb.deleteCharAt(0);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	

	public static ItemStack createItemNO(Material m, String name, int ammount, ArrayList<String> lore) {
		ItemStack item = new ItemStack(m, ammount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("" + name);
		StringBuilder sb = new StringBuilder(name);
		sb.deleteCharAt(0);
		sb.deleteCharAt(0);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static void stack(Player p) {
		org.bukkit.inventory.Inventory inv = p.getInventory();
		for (int i = 0; i < inv.getSize() - 1; i++) {
			ItemStack current = inv.getItem(i);
			
			if (current == null)
			{
				continue;
			}
			Material mat = current.getType();
			if (current.getAmount() >= 64)
				continue;
			if (mat != Material.CLAY_BRICK||mat != Material.IRON_INGOT||mat != Material.GOLD_INGOT)
				continue;
			for (int j = i + 1; j < inv.getSize(); j++) {
				ItemStack add = inv.getItem(j);
				if (add == null)
					continue;
				if (mat == add.getType() && mat != Material.AIR) {
					int max_to_add = 64 - current.getAmount();
					if (add.getAmount() < max_to_add) {
						current.setAmount(current.getAmount() + add.getAmount());
						inv.setItem(j, new ItemStack(Material.AIR));
					} else {

						current.setAmount(64);
						add.setAmount(add.getAmount() - max_to_add);
					}
				}
			}
		}
	}

	public static ItemStack addGlowEffect(ItemStack item) {
		net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if (tag == null)
			tag = nmsStack.getTag();
		NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}

	public static Player toPlayer(String s) {
		return Bukkit.getPlayerExact(s);
	}
}
