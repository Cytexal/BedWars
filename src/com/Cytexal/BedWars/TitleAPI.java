package com.Cytexal.BedWars;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleAPI {

	public static void sendTitle(Player p, String title) {
		IChatBaseComponent message = ChatSerializer.a("{\"text\": \"\"}").a(title);
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, message);

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendSubTitle(Player p, String subtitle) {
		IChatBaseComponent message = ChatSerializer.a("{\"text\": \"\"}").a(subtitle);
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, message);

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendTiming(Player p, int fadeIn, int stay, int fadeOut) {
		try {
			Object h = getHandle(p);
			Object c = getField(h.getClass(), "playerConnection").get(h);

			Object packet = PacketPlayOutTitle.class
					.getConstructor(new Class[] { PacketPlayOutTitle.class, Integer.TYPE, Integer.TYPE, Integer.TYPE })
					.newInstance(new Object[] { EnumTitleAction.TIMES, Integer.valueOf(fadeIn), Integer.valueOf(stay),
							Integer.valueOf(fadeOut) });
			getMethod(h.getClass(), "sendPacket", new Class[0]).invoke(c, new Object[] { packet });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reset(Player p) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.RESET, null);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void clear(Player p) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	private static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length) {
			return false;
		}
		for (int i = 0; i < l1.length; i++) {
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		}
		return equal;
	}

	private static Field getField(Class<?> clazz, String name) {
		try {
			Field f = clazz.getDeclaredField(name);
			f.setAccessible(true);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
		for (Method m : clazz.getMethods()) {
			if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes())))) {
				m.setAccessible(true);
				return m;
			}
		}
		return null;
	}

	private static Object getHandle(Object obj) {
		try {
			return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
