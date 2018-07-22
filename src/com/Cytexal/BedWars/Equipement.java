package com.Cytexal.BedWars;

import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Equipement implements Listener {

	HashMap<ItemStack, Integer> items = new HashMap<>();
	Inventory inv;
	boolean close = false;
	boolean clickable = false;
	
	public Equipement(Player p)
	{
		inv = Bukkit.createInventory(p, 27,"§cBed§fWars §7- §fEquipment");
		Collections.shuffle(Main.poss);
		int counter = 5;
		for(int i = 10; i<17;i++)
		{
			new EquipmentAnimation(Main.getInstance(), p, i, counter);
			counter+=5;
			inv.setItem(i, Main.poss.get(i));
		}
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
		Main.EquipMenüs.put(p, this);
		p.openInventory(inv);
		
		
		
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if(p.getOpenInventory().getTitle().equals(Main.EquipMenüs.get(p).inv.getTitle()))
				{
					p.getInventory().setContents(Main.EquipMenüs.get(p).inv.getContents());
					close = true;
					int f = -1;
					
					for(ItemStack i : p.getInventory().getContents())
					{
						f++;
						if(i!=null)
						{
							items.put(i.clone(), f);
						}
					}
					p.getOpenInventory().close();
					p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 100, 1);
					p.sendMessage(Utils.prefix+"§aDeine Items und deren Anordnungen wurden gespeichert");
				}
			}
		}, 20*30);
		
	}
	
	@EventHandler
	public void Close(InventoryCloseEvent e)
	{
		if(inv==Main.EquipMenüs.get(e.getPlayer()).inv && close == false)
		{
			
			
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					if(clickable == true)
					{
						int a = 0;
						for(ItemStack i : inv.getContents())
						{
							if(i != null)
							a++;
						}
						if(a==0)
						{
							close = true;
							int f = -1;
							for(ItemStack i : e.getPlayer().getInventory().getContents())
							{
								f++;
								if(i!=null)
								{
									items.put(i.clone(), f);
								}
							}
							e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 100, 1);
							e.getPlayer().sendMessage(Utils.prefix+"§aDeine Items und deren Anordnungen wurden gespeichert");
						}
						else
						{
						e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.GLASS, 100, 1);
						e.getPlayer().sendMessage(Utils.prefix+"Ordne die §eItems §7jetzt in §adeinem Inventar §7an");
						e.getPlayer().sendMessage(Utils.prefix+"§cdie Anordnung bleibt bei jedem respawn erhalten");
						e.getPlayer().openInventory(Main.EquipMenüs.get(e.getPlayer()).inv);
						
						}
					}
					else
					{
						e.getPlayer().openInventory(Main.EquipMenüs.get(e.getPlayer()).inv);
					}
					
				}
			},1);
		}
	}
	
	@EventHandler
	public void Click(InventoryClickEvent e)
	{
		if(inv==Main.EquipMenüs.get(e.getWhoClicked()).inv && clickable == false)
		{
			e.setCancelled(true);
		}
	}

	public void setItems(Player p) {
		for(ItemStack i : items.keySet())
		{
			p.getInventory().setItem(items.get(i), i.clone());
		}
	}
	
}
