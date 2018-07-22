package com.Cytexal.BedWars;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

import net.minecraft.server.v1_8_R3.EnumParticle;


public class Win implements Runnable{
	 private int pid;
	 private int counter;
	 private int heal;
	 private int time;
	 Location loc;
	 Player p;
	 public Win(Plugin p, Player pl){
	  counter = 3;
	  heal = 100;
	  time = 150;
	  this.loc = loc;
	  this.p = pl;
	  pid=Bukkit.getScheduler().scheduleSyncRepeatingTask(p,this,0,20*5);
	 }
	 @Override
	 public void run(){

		 		//p.getWorld().strikeLightning(p.getLocation());
				Particle particle = new Particle(EnumParticle.HEART, p.getLocation().add(0, 2, 0), true, 0.1F, 0.1F, 0.1F, 0.1F , 40);
				particle.sendAll();
				 //Spawn the Firework, get the FireworkMeta.
	            Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
	            FireworkMeta fwm = fw.getFireworkMeta();
	           
	            //Our random generator
	            Random r = new Random();   
	 
	            //Get the type
	            int rt = r.nextInt(5) + 1;
	            Type type = Type.BALL;       
	            if (rt == 1) type = Type.BALL;
	            if (rt == 2) type = Type.BALL_LARGE;
	            if (rt == 3) type = Type.BURST;
	            if (rt == 4) type = Type.CREEPER;
	            if (rt == 5) type = Type.STAR;
	           
	            //Get our random colours   
	            int r1i = r.nextInt(17) + 1;
	            int r2i = r.nextInt(17) + 1;
	            Color c1 = Color.fromRGB(r2i, r1i, r2i);
	            Color c2 = Color.fromRGB(r1i , r2i, r1i);
	           
	            //Create our effect with this
	            FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
	            
	            fwm.addEffect(effect);
	            
	            //Generate some random power and set it
	            int rp = r.nextInt(2) + 1;
	            fwm.setPower(rp);
	           
	            //Then apply this to our rocket
	            fw.setFireworkMeta(fwm);           
	 }
	  }