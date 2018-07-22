package com.Cytexal.BedWars;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PartAnimations {

	public static void Sule(final Location loc, final ParticleEffect effect)
	{
		new BukkitRunnable() {
			double t = 0;
			double r = 2;
			
			@Override
			public void run() {
				
				t = t + Math.PI/16;
				double x = r*Math.cos(t);
				double y = 0.5*t;
				double z = r*Math.sin(t);
				loc.add(x,y,z);
				ParticleEffect.fromName(effect.getName()).display(0, 0, 0, 1, 1, loc, 15);
				loc.subtract(x,y,z);
				if(t > Math.PI*8)
				{
					this.cancel();
				}
			}
		}.runTaskTimer(Main.pl, 0, 1);
	}
	
	public static void Schockwelle(final Location loc)
	{
	 new BukkitRunnable(){
         double t = Math.PI/4;
         @Override
		public void run(){
                 t = t + 0.1*Math.PI;
                 for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
                         double x = t*Math.cos(theta);
                         double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
                         double z = t*Math.sin(theta);
                         loc.add(x,y,z);
                         ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 0, 1, loc, 15);
                         loc.subtract(x,y,z);
                        
                         theta = theta + Math.PI/64;
                        
                         x = t*Math.cos(theta);
                         y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
                         z = t*Math.sin(theta);
                         loc.add(x,y,z);
                         ParticleEffect.SPELL_WITCH.display(0, 0, 0, 1, 1, loc, 15);
                         loc.subtract(x,y,z);
                 }
                 if (t > 20){
                         this.cancel();
                 }
         }
                                
	 	}.runTaskTimer(Main.pl, 0, 1);
	}
	
	
	public static void Aura(final Location loc, boolean water, final int repeats)
	{
		new BukkitRunnable(){
	         double phi = 0;
	         @Override
			public void run(){
	        	 phi += Math.PI/10;
	                 for (double theta = 0; theta <= 2*Math.PI; theta += Math.PI/40){
	                        
	                	 	double r = 1.5;
	                	 	double x = r*Math.cos(theta)*Math.sin(phi);
	                	 	double y = r*Math.cos(phi) + 1.5;
	                	 	double z = r*Math.sin(theta) * Math.sin(phi);
	                        loc.add(x,y,z);
	                        ParticleEffect.DRIP_WATER.display(0, 0, 0, 1, 1, loc, 15);
	                        loc.subtract(x,y,z);
	                 }
	                 if (phi > repeats*Math.PI){
	                         this.cancel();
	                 }
	         }
	                                
		 	}.runTaskTimer(Main.pl, 0, 1);
	}
	
	public static void BlutHelix(final Location loc){
        new BukkitRunnable(){
                double phi = 0;
                @Override
				public void run(){
                        phi = phi + Math.PI/16;                                 
                        double x, y, z;                
                       
                        Location location1 = loc;
                        for (double t = 0; t <= 2*Math.PI; t = t + Math.PI/16){
                                for (double i = 0; i <= 1; i += 1){
                                        x = 0.15*(2*Math.PI-t)*0.5*Math.cos(t + phi + i*Math.PI);
                                        y = 0.5*t;
                                        z = 0.15*(2*Math.PI-t)*0.5*Math.sin(t + phi + i*Math.PI);
                                        location1.add(x, y, z);
                                        ParticleEffect.REDSTONE.display(0, 0, 0, 0, 1, loc, 15);
                                        location1.subtract(x,y,z);
                                }
                               
                        }              
                       
                        if(phi > 10*Math.PI){                                          
                                this.cancel();
                        }
                }      
        }.runTaskTimer(Main.pl, 0, 1);                       
}
	
	public static void frostLord(final Player p ,final boolean flying){
        new BukkitRunnable(){
                double t = 0;
                @Override
				public void run(){
                        t += + Math.PI/16;                                 
                        double x = 0, y = 0, z = 0;                
                       
                        Location location1 = p.getLocation();
                        for (double phi = 0; phi <= 2*Math.PI;phi += Math.PI/2){
                              
                                        x = 0.3*(4*Math.PI-t)*0.5*Math.cos(t + phi);
                                        y = 0.2*t;
                                        z = 0.3*(4*Math.PI-t)*0.5*Math.sin(t + phi);
                                        location1.add(x, y, z);
                                        ParticleEffect.SNOW_SHOVEL.display(0, 0, 0, 1, 1, location1, 1);
                                        location1.subtract(x,y,z);
                               
                        }              
                       
         
                        if(t >= 4*Math.PI){   
                        	location1.add(x,y,z);
                        	ParticleEffect.SNOW_SHOVEL.display(0, 0, 0, 50, 1, location1, 1);
                                this.cancel();
                        }
                }      
        }.runTaskTimer(Main.pl, 0, 3);                       
}
	
}
