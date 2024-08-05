package me.ShinyShadow_.BowOfIce.Item;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.RegionAccessor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import org.bukkit.entity.Display;

import org.bukkit.entity.Player;

public class ItemListener implements Listener{
	  private final JavaPlugin plugin;
	  private Location eyeLoc;
	  
	  private Player player;
	  
	  private int arrowsShot = 3;
	  
	  private ItemStack item;
	  private ItemMeta meta;
 
	  private boolean isSpecial = false;
	  
	  private double iceTimer = 4D;
	private final Map<UUID, Integer> arrowTasks = new HashMap<>();
	
	   public ItemListener(JavaPlugin plugin) {
	        this.plugin = plugin;
	    }
	@EventHandler
	public void onArrowShoot(ProjectileLaunchEvent event) {
			
		if (event.getEntity() instanceof Arrow ) {

			 player = (Player) event.getEntity().getShooter();
			 
			 player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHAIN_BREAK, 2f, -1f);
			 player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHAIN_BREAK, 0.75f, 2f);
			 
		//player.setCooldown(Material.BOW, 50);

		if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains("Normal attack: Frost Bite.")) {

			eyeLoc = player.getEyeLocation();
            Entity arrow = event.getEntity(); 
            arrow.setSilent(true);
            
            if(arrowsShot == 3) {
            	 isSpecial = false;
            }
            arrowsShot -= 1;
  	        item = player.getInventory().getItemInMainHand();
     	    meta = item.getItemMeta();
            if(arrowsShot > 0 && player.getInventory().getItemInMainHand().getItemMeta().getLore().contains("Normal attack: Frost Bite.")) {
                  meta.setDisplayName(ChatColor.AQUA+ "Elemental Bow: Ice ("+arrowsShot+")");     
                 
            }             
            if(arrowsShot == 0) {                 	
                  meta.setDisplayName(ChatColor.AQUA + "SPECIAL SHOT READY");    
                  
            }
            if(arrowsShot < 0) {
            	 isSpecial = true;
                new SpecialAttack(player, plugin);
                arrow.remove();
                arrowsShot = 3;
            }
                  
            item.setItemMeta(meta);
        	BlockDisplay IceArrow = (BlockDisplay) arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.BLOCK_DISPLAY);
        	IceArrow.setBlock(Material.ICE.createBlockData());
            if(!isSpecial) {  
            	  
            int taskId = new BukkitRunnable() {
            	 double t = 0;
                @Override
                public void run() {
             	
                if(!isSpecial) {
                	arrow.getWorld().spawnParticle(Particle.SNOWFLAKE, arrow.getLocation(), 8, 0.25, 0.25, 0.25, 0);
                	arrow.getWorld().spawnParticle(Particle.REDSTONE, arrow.getLocation(), 6, 0.25, 0.25, 0.25, 0, new DustOptions(Color.AQUA, 1));
                	arrow.getWorld().spawnParticle(Particle.BLOCK_CRACK, arrow.getLocation(), 14, 0.25, 0.25, 0.25, 0, Material.FROSTED_ICE.createBlockData());
                	
                	IceArrow.teleport(arrow);
                	//	IceArrow.setBlock(Material.ICE.createBlockData());
                //	IceArrow.remove(); 
                	
                	if (arrow.isDead() || arrow.isOnGround()) {
                    	//arrow.getWorld().spawnParticle(Particle.FLAME, arrow.getLocation().add(0, 0.5, 0), 200, 0.1, 0.1, 0.1, 0.2);
            		 	//arrow.getWorld().spawnParticle(Particle.SMOKE_NORMAL, arrow.getLocation().add(0, 0.5, 0), 200, 0.1, 0.1, 0.1, 0.2);
                       // arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 1.8F);
                		IceArrow.remove();                    
                        this.cancel();
                        arrowTasks.remove(arrow.getUniqueId());
                        return;
                    }                               
                }              
              }
            }.runTaskTimer(plugin, 0L, 0L).getTaskId();
          }
		}
	  }
   }
	@EventHandler
	public void onArrowHit(ProjectileHitEvent event) {
		Entity target =	event.getHitEntity();
		target.getWorld().playSound(target.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.5f, 2f);
		target.setVelocity(new Vector(0, 0, 0));
  	  	player.getWorld().spawnParticle(Particle.REDSTONE, target.getLocation().add(0, 1, 0), 260, 1.8, 1.8, 1.8, 1, new DustOptions(Color.AQUA, 1));
  	  	player.getWorld().spawnParticle(Particle.BLOCK_CRACK, target.getLocation().add(0, 1, 0), 260, 1.8, 1.8, 1.8, 1, Material.FROSTED_ICE.createBlockData());
		((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4, 40));
		
 		 
    	Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(target.getLocation(), 4, 4, 4);
    			for (Entity entity : nearbyEntities) {
    			
    					entity.getWorld().getBlockAt(entity.getLocation()).setType(Material.SNOW);
    		        if (entity instanceof LivingEntity && target.getUniqueId() != player.getUniqueId() && !target.isDead()) {	
    		        	((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 2));

    		        	((LivingEntity)entity).damage(3);     
    		        }
    		        
    		        }
		
			if(!event.getHitEntity().isDead()) {
					
					((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4, 40));
				    target.setVelocity(new Vector(0, 0, 0));
					target.getWorld().getBlockAt(target.getLocation().add(0, 2, 0)).setType(Material.ICE);
					target.getWorld().getBlockAt(target.getLocation().add(1, 0, 0)).setType(Material.ICE);
					target.getWorld().getBlockAt(target.getLocation().add(1, 1, 0)).setType(Material.ICE);
					target.getWorld().getBlockAt(target.getLocation().add(0, 0, 1)).setType(Material.ICE);
					target.getWorld().getBlockAt(target.getLocation().add(0, 1, 1)).setType(Material.ICE);
					target.getWorld().getBlockAt(target.getLocation().add(-1, 0, 0)).setType(Material.ICE);
					target.getWorld().getBlockAt(target.getLocation().add(-1, 1, 0)).setType(Material.ICE);
					target.getWorld().getBlockAt(target.getLocation().add(0, 0, -1)).setType(Material.ICE);
					target.getWorld().getBlockAt(target.getLocation().add(0, 1, -1)).setType(Material.ICE);
		   new BukkitRunnable() {
		                @Override
		                public void run() {		   
		                	
		                iceTimer -= 0.09D;
		                if(iceTimer <= 0) {
							target.getWorld().getBlockAt(target.getLocation().add(0, 2, 0)).breakNaturally();
							target.getWorld().getBlockAt(target.getLocation().add(1, 0, 0)).breakNaturally();
							target.getWorld().getBlockAt(target.getLocation().add(1, 1, 0)).breakNaturally();
							target.getWorld().getBlockAt(target.getLocation().add(0, 0, 1)).breakNaturally();
							target.getWorld().getBlockAt(target.getLocation().add(0, 1, 1)).breakNaturally();
							target.getWorld().getBlockAt(target.getLocation().add(-1, 0, 0)).breakNaturally();
							target.getWorld().getBlockAt(target.getLocation().add(-1, 1, 0)).breakNaturally();
							target.getWorld().getBlockAt(target.getLocation().add(0, 0, -1)).breakNaturally();
							target.getWorld().getBlockAt(target.getLocation().add(0, 1, -1)).breakNaturally();
							iceTimer = 4D;
							this.cancel();
		                }
		                }
		            }.runTaskTimer(plugin, 0L, 0L).getTaskId();
			}
	}
}     		
