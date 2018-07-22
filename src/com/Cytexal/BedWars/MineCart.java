package com.Cytexal.BedWars;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Rails;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MineCart implements Listener{
	
	
	@EventHandler
	public void onVehichDestroy(VehicleDestroyEvent event) {
	       event.getVehicle().remove();
	}
	
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem() != null && event.getItem().getType() == Material.MINECART) {
			Location loc = event.getClickedBlock().getLocation().add(0,1,0);
			if(event.getPlayer().getItemInHand().getAmount() == 1)
			{
				event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			}
			else
			{
				event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount()-1);
			}
			
			
			
			
			
			Minecart m = (Minecart) loc.getWorld().spawnEntity(loc, EntityType.MINECART);
			m.setDerailedVelocityMod(new Vector(1, 1, 1));
			m.setPassenger(event.getPlayer());
			
			switch(yawToFace(loc.getYaw(), false))
			{
			case EAST:
				m.setVelocity(new Vector(2,0,0));
			break;
			case WEST:
				m.setVelocity(new Vector(-2,0,0));
			break;
			case NORTH:
				m.setVelocity(new Vector(0,0,-2));
			break;
			case SOUTH:
				m.setVelocity(new Vector(0,0,2));
			break;
			}
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
				
					
					
					if(loc.distance(m.getLocation())>=80)
					{
						m.remove();
						cancel();
					}
					
					Block b1 = m.getLocation().getBlock();
					Block b2 = m.getLocation().add(0, -1, 0).getBlock();
					Material mat = Material
							.valueOf(Main.getInstance().getConfig().getString("Arena." + Main.Arena + ".Team." + Main.Teams.get(event.getPlayer()).replace("§", "&")+ ".Item"));
					
					if((b2.getType() == Material.AIR || b2.getType() == mat))
					{
						b2.setType(mat);
						b2.setData((byte) Main.getInstance().getConfig().getInt("Arena." + Main.Arena + ".Team."  + Main.Teams.get(event.getPlayer()).replace("§", "&")+ ".ItemData"));
						Main.blocks.add(b2.getLocation());
						if((b1.getType() == Material.AIR || b1.getType() == Material.RAILS))
						{
					
							b1.setType(Material.RAILS);
							setRails(b1, (yawToFace(loc.getYaw(), false)));
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								
								@Override
								public void run() {
									b1.setType(Material.AIR);
								}
							},40);
						}
						else 
						{
							m.remove();
							cancel();
						}
					}
				}
			}.runTaskTimer(Main.getInstance(), 0, 1);
		}
	}
	

	public static void setRails(Block rails,BlockFace direction){
		  Material type=rails.getType();
		  if (type == Material.RAILS) {
		    if (direction == BlockFace.NORTH) {
		      direction=BlockFace.SOUTH;
		    }
		 else     if (direction == BlockFace.EAST) {
		      direction=BlockFace.WEST;
		    }
		    byte olddata=rails.getData();
		    Rails r=(Rails)type.getNewData(olddata);
		    r.setDirection(direction,r.isOnSlope());
		    byte newdata=r.getData();
		    if (olddata != newdata) {
		      rails.setData(newdata);
		    }
		  }
		}
	
	public BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections)
            return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();
     
        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }
 
private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
private static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };
	
}
