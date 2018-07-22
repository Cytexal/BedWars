package com.Cytexal.BedWars;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathEntity;
public class InteractEvent implements Listener {

	@EventHandler
	public void Mover(PlayerMoveEvent e)
	{
		if(e.getTo().getBlock().getType() == Material.TRIPWIRE)
		{
			if(Main.Traps.keySet().contains(e.getTo().clone().subtract(0,1,0).getBlock().getLocation()))
			{
				if(!Main.Teams.get(e.getPlayer()).equals(Main.Traps.get(e.getTo().clone().subtract(0,1,0).getBlock().getLocation())))
				{
				e.getTo().getBlock().setType(Material.AIR);
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 1));
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*5, 2));
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.GLASS, 100, 1);
				for(Player p : Bukkit.getOnlinePlayers())
				{
					if(Main.Teams.get(p).equals(Main.Traps.get(e.getTo().clone().subtract(0,1,0).getBlock().getLocation())))
					{
						p.playSound(p.getLocation(), Sound.WOLF_HOWL, 100, 1);
						p.sendMessage(Utils.prefix+"§7Eine Falle wurde §aaktiviert");
					}
				}
				
				
				Main.Traps.remove(e.getTo().clone().subtract(0,1,0).getBlock());
				}
			}
		}
		
		
		
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
		{
			
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				if(p.getItemInHand().getType() == Material.STRING)
				{
					p.sendMessage(Utils.prefix+"§7Eine §cFalle §7wurde §ainstalliert");
					Main.Traps.put(e.getClickedBlock().getLocation(),Main.Teams.get(p));
				}
			}
			
			if(e.getPlayer().getItemInHand().getType() == Material.BED && Main.phase == Phasen.LOBBY)
			{
				e.setCancelled(true);
				new TeamMenü(e.getPlayer(), Main.Arena);
			}
			else
			if(e.getPlayer().getItemInHand().getType() == Material.SKULL_ITEM&& Main.phase == Phasen.LOBBY)
			{
				e.setCancelled(true);
				p.chat("/freund");
			}
			else
				if(e.getPlayer().getItemInHand().getType() == Material.MAGMA_CREAM&& Main.phase == Phasen.LOBBY)
				{
					e.setCancelled(true);
					Main.Send(p, "Lobby-1");
				}
			
			if(e.getPlayer().getItemInHand().getType() == Material.RED_ROSE)
			{
				if(e.getPlayer().getItemInHand().getAmount() > 1)
				{
					e.getPlayer().getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
				}
				else
				{
					e.getPlayer().getInventory().remove(e.getPlayer().getItemInHand());
					
				}
				new Wächter(Main.Teams.get(p), new Location(e.getPlayer().getLocation().getWorld(), e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY() + 1, e.getPlayer().getLocation().getZ()));
			}
			
			if(e.getPlayer().getItemInHand().getType() == Material.SULPHUR)
			{
				if(e.getPlayer().getItemInHand().getAmount() > 1)
				{
					e.getPlayer().getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
				}
				else
				{
					e.getPlayer().getInventory().remove(e.getPlayer().getItemInHand());
				}
				p.sendMessage(Utils.prefix+"Teleportation wurde §agestartet");
				PartAnimations.Aura(p.getLocation(), true, 6);
				new Teleport(Main.getInstance(), e.getPlayer(), 5, Locator.toLocation("Arena."+ Main.Arena +".Team."+ Main.Teams.get(p).replace("§", "&") +".Spawn", Main.getInstance().getConfig().getString("Arena."+Main.Arena+".Team."+Main.Teams.get(p).replace("§", "&") +".Spawn.Yaw"), Main.getInstance().getConfig().getString("Arena."+Main.Arena+".Team."+Main.Teams.get(p).replace("§", "&") +".Spawn.Pitch")));
			}
			
			if(e.getPlayer().getItemInHand().getType() == Material.FIREWORK_CHARGE)
			{
				{		
					Random rnd = new Random();
					ArrayList<String> lore = new ArrayList<>();
					final Item axt = p.getWorld().dropItem(p.getEyeLocation(), Utils.createItem(Material.FIREWORK_CHARGE,  "" + rnd.nextInt(100) + "a"+ rnd.nextInt(100), 0, 1));				
					axt.setVelocity(p.getEyeLocation().getDirection().multiply(1.5));
					if(e.getPlayer().getItemInHand().getAmount() > 1)
					{
						e.getPlayer().getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
					}
					else
					{
						e.getPlayer().getInventory().remove(e.getPlayer().getItemInHand());
					}
					new Blendgranate(Main.getInstance(), p, 7, axt);
				}
			}
			
		}
	}
	@EventHandler
	public void Drop(PlayerDropItemEvent e)
	{
		if(Main.spawnprot.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void Pickup(PlayerPickupItemEvent e)
	{
		if(e.getItem().getItemStack().getType() == Material.FIREWORK_CHARGE ||e.getItem().getItemStack().getType() == Material.BED)
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void DMG(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
		if(Main.spawnprot.contains(e.getEntity())){
			e.setCancelled(true);
		}
		}
		
		
	}
	
	
	@EventHandler
	public void DMG(EntityDamageByEntityEvent e)
	{
		if(Main.phase == Phasen.INGAME)
		{
		if(e.getEntity() instanceof Player)
		{
			if(Main.Teams.get(e.getEntity()).equals(Main.Teams.get(e.getDamager()))){
				e.setCancelled(true);
			}
		}
		}
		
	}
	
	@EventHandler
	public void BB(BlockBreakEvent e)
	{
		if(Main.spawnprot.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void Hunger(FoodLevelChangeEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			if(Main.spawnprot.contains(e.getEntity())){
				e.setFoodLevel(20);
			}
		}
	}
	
	
	
	@EventHandler
	public void Explosion(EntityExplodeEvent e)
	{
		ArrayList<Block> noexp = new ArrayList<>();
		for(Block b : e.blockList())
		{
			if(!Main.blocks.contains(b.getLocation()))
			{
				noexp.add(b);
			}
		}
		for(Block ea : noexp)
		{
			e.blockList().remove(ea);
		}
	}
	
	
	@EventHandler
	public void Explosion(BlockExplodeEvent e)
	{
		ArrayList<Block> noexp = new ArrayList<>();
		for(Block b : e.blockList())
		{
			if(!Main.blocks.contains(b.getLocation()))
			{
				noexp.add(b);
			}
		}
		for(Block ea : noexp)
		{
			e.blockList().remove(ea);
		}
	}
	
	@EventHandler
	public void Move(PlayerMoveEvent e)
	{
		if(Main.noMove.containsKey(e.getPlayer()))
		{
			e.getPlayer().teleport(Main.noMove.get(e.getPlayer()));
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
    public void onWeatherChange(WeatherChangeEvent event) {
      
        boolean rain = event.toWeatherState();
        if(rain)
            event.setCancelled(true);
    }
  
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onThunderChange(ThunderChangeEvent event) {
      
        boolean storm = event.toThunderState();
        if(storm)
            event.setCancelled(true);
    }
	
	
	@EventHandler
	public void CreatureSpawn(CreatureSpawnEvent e)
	{
		if(e.getSpawnReason() != SpawnReason.CUSTOM)
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void Join(PlayerJoinEvent e)
	{
		e.setJoinMessage(null);
		if(Main.phase == Phasen.INGAME)
		{
		 e.getPlayer().setGameMode(GameMode.SPECTATOR);
		 Location loc = Locator.toLocation("Arena."+Main.Arena+".Spectator.Location", "0","0");
		 e.getPlayer().teleport(loc);
		 e.getPlayer().sendMessage(Utils.prefix+ "§7Du §ebeobachtest §7jetzt das Spiel auf der Karte §d" + Main.Arena);
		}
		else
		{
			if(Main.count == false)
			{
				if(Bukkit.getOnlinePlayers().size() >= 2)
				{
					Main.count = true;
					try{
						Main.countdown.Sek = 60;
						}catch (Exception en) {
							
						}
				}
			}
			Bukkit.broadcastMessage(Utils.prefix+"§a" + e.getPlayer().getName() + " §7hat das Spiel §abetreten");
			Join.Join(e.getPlayer(), Main.Arena);
		}
	}
	
	
	@EventHandler
	public void Quit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		e.setQuitMessage("");
		
		Bukkit.broadcastMessage(Utils.prefix+"§a" + p.getName() + " §7hat das Spiel §cverlassen");
		if(Main.phase == Phasen.LOBBY)
		{
			if(Main.Teams.containsKey(p))
			{
				Main.Teams.remove(p);
			}
			
			if(Bukkit.getOnlinePlayers().size() <=2)
			{
				Main.count = false;
				try{
				Main.countdown.Sek = 60;
				}catch (Exception en) {
					
				}
				
				Bukkit.broadcastMessage(Utils.prefix+"§cEs wird auf weitere Spieler gewartet..");
			}
		}
		else
		{
			if(Main.Teams.keySet().size() >= 2)
			{
			Main.f.addLose(e.getPlayer());
			e.getPlayer().sendMessage(Utils.prefix+"§c-10 Rang-Punkte");
			Main.f.setPunkte(e.getPlayer(), Main.f.getPunkte(e.getPlayer().getUniqueId())-10);
			int b = 0;
            for (Player a : Main.Teams.keySet())
            {
            	if(Main.Teams.get(a).equals(Main.Teams.get(p)))
            	{
            		b++;
            	}
            }
            
            if(b == 1)
            {
            	p.sendMessage(" ");
            	p.sendMessage(Utils.prefix + "Das Team " + Main.Teams.get(p));
 	            p.sendMessage(Utils.prefix + "wurde vollständig besiegt.");
 	            Main.BettenStatus.remove(Main.Teams.get(p).replace("§", "&"));
 	            for (Player a : Bukkit.getOnlinePlayers())
 	            {
 	            Scoreboard.updateScoreboard(a);
 	            }
            }
            Main.Teams.remove(p);
			}
			else
			{
				Player a = null;
				for(Player t : Bukkit.getOnlinePlayers())
				{
					if(t!=p)
					{
						a=t;
					}
				}
				Bukkit.broadcastMessage(Utils.prefix+"§7Das Team " + Main.Teams.get(a) + " §7hat das Spiel §agewonnen");
				new Win(Main.getInstance(), a);
				Main.f.addWin(e.getPlayer());
				e.getPlayer().sendMessage(Utils.prefix+"§a+150 Rang-Punkte");
				Main.f.setPunkte(a, Main.f.getPunkte(a.getUniqueId())+150);
			}
		}
		
		
	}
	
	@EventHandler
	public void Chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(Main.phase == Phasen.LOBBY)
		{
		if (p.hasPermission("UC.Team")) {
			e.setFormat("§c§nTeam§r §e★ §7" + e.getPlayer().getName() + " §8● §7" + e.getMessage());
		} else if (p.hasPermission("pingo03.sup") || p.hasPermission("pingo03.mod")) {
			e.setFormat("§9Supporter §e★ §7" + e.getPlayer().getName() + " §8● §7" + e.getMessage());
		} else if (p.hasPermission("UC.Vip")) {
			e.setFormat("§e★§6VIP§e★ §7" + e.getPlayer().getName() + " §8● §7" + e.getMessage());
		} else {
			e.setFormat("§9Spieler §e★ §7" + e.getPlayer().getName() + " §8● §7" + e.getMessage());
		}
		}
		else
		if(Main.phase == Phasen.INGAME)
		{
			if(Main.Teams.containsKey(p))
			{
				if(e.getMessage().startsWith("@"))
				{
				e.setMessage(e.getMessage().replace("@", ""));
				
			    e.setFormat("§7(§cG§7) "+Main.Teams.get(p)+" §e★ §7" + e.getPlayer().getName() + " §8● §7" + e.getMessage());
				}
				else
				{
					e.getRecipients().clear();
					for (Player a : Main.Teams.keySet())
		            {
		            	if(Main.Teams.get(a).equals(Main.Teams.get(p)))
		            	{
		            		e.getRecipients().add(a);
		            	}
		            }
					e.setFormat(Main.Teams.get(p)+" §e★ §7" + e.getPlayer().getName() + " §8● §7" + e.getMessage());
				}
			}
			else
			{
				e.setFormat("§7Spectator §e★ §7" + e.getPlayer().getName() + " §8● §7" + e.getMessage());
				for (Player a : Main.Teams.keySet())
	            {
					e.getRecipients().remove(a);
	            }
				
				
			}
		}

	}
	
	
	@EventHandler
	public void Death(PlayerDeathEvent e)
	{
			Player p = (Player) e.getEntity();
			e.setDeathMessage("");
			e.setKeepInventory(true);
		
			Main.f.addDeath(p);
			
			if(Main.BettenStatus.get(Main.Teams.get(p).replace("§", "&")))
			{
			
			Main.spawnprot.add(p);
			
			if(p.getKiller() != null)
			{
				Bukkit.broadcastMessage(Utils.prefix + "§" + Main.Teams.get(p).charAt(1)+ p.getName() + " §7wurde von §" + Main.Teams.get(p.getKiller()).charAt(1)+ p.getKiller().getName() + " §7getötet");	
				Main.f.addKill(p.getKiller());
				p.getKiller().sendMessage(Utils.prefix+"§a+70 Rang-Punkte");
				Main.f.setPunkte(p.getKiller(), Main.f.getPunkte(p.getKiller().getUniqueId())+70);
			}
			else
			{
				Bukkit.broadcastMessage(Utils.prefix + "§" + Main.Teams.get(p).charAt(1)+ p.getName() + " §7ist gestorben");
			}
			
			
			p.setHealth(p.getMaxHealth());
			
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            p.teleport(Locator.toLocation("Arena."+ Main.Arena +".Team."+ Main.Teams.get(p).replace("§", "&") +".Spawn", Main.getInstance().getConfig().getString("Arena."+Main.Arena+".Team."+Main.Teams.get(p).replace("§", "&") +".Spawn.Yaw"), Main.getInstance().getConfig().getString("Arena."+Main.Arena+".Team."+Main.Teams.get(p).replace("§", "&") +".Spawn.Pitch")));
            //Our random generator
            Random r = new Random();   
            p.setFoodLevel(20);
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
            
            
        	Main.SpawnEq.get(e.getEntity()).setItems(p);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					Main.spawnprot.remove(p);
				}
			},5*20);
			}
			else
			{
				if(p.getKiller() != null)
				{
					Bukkit.broadcastMessage(Utils.prefix + "§" + Main.Teams.get(p).charAt(1)+ p.getName() + " §7wurde von §" + Main.Teams.get(p.getKiller()).charAt(1)+ p.getKiller().getName() + " §7getötet");	
					Main.f.addKill(p.getKiller());
					p.getKiller().sendMessage(Utils.prefix+"§a+100 Rang-Punkte");
					Main.f.setPunkte(p.getKiller(), Main.f.getPunkte(p.getKiller().getUniqueId())+100);
				}
				else
				{
					Bukkit.broadcastMessage(Utils.prefix + "§" + Main.Teams.get(p).charAt(1)+ p.getName() + " §7ist gestorben");
				}
				p.setHealth(p.getMaxHealth());
				String Team = Main.Teams.get(p);
				
				p.setGameMode(GameMode.SPECTATOR);
				
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
	            FireworkMeta fwm = fw.getFireworkMeta();
	            Location loc = Locator.toLocation("Arena."+Main.Arena+".Spectator.Location", "0", "0");
	            p.teleport(loc);
	            //Our random generator
	            Random r = new Random();   
	            p.sendMessage(" ");
	            Main.f.addLose(p);
	            p.sendMessage(Utils.prefix + "Da dein Bett §czerstört §7wurde");
	            p.sendMessage(Utils.prefix + "kannst du nicht §awiederbelebt §7werden,");
	            p.sendMessage(Utils.prefix + "du §fbeobachtest §7das Spiel nun");
	            p.sendMessage(" ");
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
	            
	            int b = 0;
	            
	            for (Player a : Main.Teams.keySet())
	            {
	            	if(Main.Teams.get(a).equals(Main.Teams.get(p)))
	            	{
	            		b++;	
	            	}
	            }
	            
	            Main.Teams.remove(p);
	            
	            if(b == 1)
	            {
	            	Bukkit.broadcastMessage(" ");
	            	Bukkit.broadcastMessage(Utils.prefix + "Das Team " + Team);
	            	Bukkit.broadcastMessage(Utils.prefix + "wurde vollständig besiegt.");
	 	            Main.BettenStatus.remove(Team.replace("§", "&"));
	 	            Scoreboard.updateScoreboard(p);
	            }
	            
	            
	            
			}
		}
	}

