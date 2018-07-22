package com.Cytexal.BedWars;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class TabModifizierer {

	public static void setTab(Player p, String header, String footer) {
		if (header == null)
			header = "";
		if (footer == null)
			footer = "";
		header = ChatColor.translateAlternateColorCodes('&', header);
		footer = ChatColor.translateAlternateColorCodes('&', footer);
		header = header.replaceAll("%PLAYER%", p.getDisplayName());
		footer = footer.replaceAll("%PLAYER%", p.getDisplayName());
		PlayerConnection con = ((CraftPlayer) p).getHandle().playerConnection;
		IChatBaseComponent tabheader = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabfooter = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabheader);
		try {
			Field f = null;
			try {
				f = packet.getClass().getDeclaredField("b");
			} catch (NoSuchFieldException e) {
			} catch (SecurityException e) {
			}
			f.setAccessible(true);
			f.set(packet, tabfooter);
			con.sendPacket(packet);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
	}

}
