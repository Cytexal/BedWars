package com.Cytexal.BedWars;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blendgranate implements Runnable{

	
	Item bomb;
	int pid;
	int counter;
	Plugin pl;
	Player p;
	
	public Blendgranate(Plugin pl ,Player p, int time, Item e){
		  counter = time*2;
		  bomb = e;
		  this.p = p;
		  this.pl = pl;
		  pid=Bukkit.getScheduler().scheduleSyncRepeatingTask(pl,this,0,5);
		 }

	@Override
	public void run() {
		
		if(bomb.isOnGround())
		{
		if(counter > 0){
			counter --;
			for(Entity a: bomb.getNearbyEntities(14, 14, 14))
			{
				if(a instanceof Player)
				((Player)a).playSound(bomb.getLocation(), Sound.NOTE_PLING, 100, 1);
			}
		}else{
			bomb.getLocation().getWorld().createExplosion(bomb.getLocation(), 2F);
			for(Entity e : bomb.getNearbyEntities(4, 4, 4))
			{
				if(e instanceof Player)
				{
				e.setVelocity(((LivingEntity) e).getEyeLocation().getDirection().multiply(2).setY(0.1F));
				Player p = (Player) e;
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 6*20, 2));
				}
			}
			bomb.remove();
			Bukkit.getScheduler().cancelTask(pid);
			}
		}
		else
		{
			ParticleEffect.FLAME.display(0, 0, 0, 1, 2, bomb.getLocation(), 20);
		}
	}
		
	}
	
