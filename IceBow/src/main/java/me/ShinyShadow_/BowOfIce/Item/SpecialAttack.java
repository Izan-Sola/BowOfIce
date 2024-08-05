package me.ShinyShadow_.BowOfIce.Item;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

//Amber's ult :3
public class SpecialAttack {

	private Entity specialArrow;
	
	private double areaTimer = 25D;
	private double circleSize = 3D;
	private double stormSize = 1D;
	private double spawnDelay = 0D;

	private Location eyeLoc;
	private Location areaLoc;
	private Location particleLoc;
	
		public SpecialAttack(Player player, JavaPlugin plugin) {			
			eyeLoc = player.getEyeLocation();
			specialArrow = player.getWorld().spawn(eyeLoc, Arrow.class);
			
        	Vector direction = player.getEyeLocation().getDirection();
        	Vector diagonalDirection = direction.add(new Vector(0, 0.1, 0).normalize().multiply(0.5D));
        	specialArrow.setVelocity(diagonalDirection);
        	 new BukkitRunnable() {
                 @Override
                 public void run() {
                     if (specialArrow.isDead() || specialArrow.isOnGround()) {
                    	 areaLoc = specialArrow.getLocation();
                    	 iceStorm(player, areaLoc, plugin);
                    	 this.cancel();
                         return;
                     }
                     specialArrow.getWorld().spawnParticle(Particle.SNOWBALL, specialArrow.getLocation(), 20, 0.8, 0.5, 0.5, 0.1);
                     specialArrow.getWorld().spawnParticle(Particle.REDSTONE, specialArrow.getLocation(), 20, 0.5, 0.5, 0.8, 0.1, new DustOptions(Color.AQUA, 1));
                  
                 }
             }.runTaskTimer(plugin, 0L, 1L);		
}
		
			public void iceStorm(Player player, Location rainLoc, JavaPlugin plugin) {
				
				   new BukkitRunnable() {
			            Location pLocation = areaLoc.add(0, 5, 0).clone();
			            @Override
			            public void run() {
			            	 player.getWorld().playSound(rainLoc, Sound.WEATHER_RAIN, 0.5f, -1.3f);
			          	  for (int d = 0; d <= 160; d += 1) {
			          		particleLoc = new Location(player.getWorld(), rainLoc.getX(), rainLoc.getY()+10,rainLoc.getZ());
			          		particleLoc.setX(rainLoc.getX() + Math.cos(d) * circleSize);
			          		particleLoc.setZ(rainLoc.getZ() + Math.sin(d) * circleSize);
			          
			          		player.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 2, 0.25, 0.25, 0.25, 0, new DustOptions(Color.AQUA, 1));
			          		player.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 2, 0.25, 0.25, 0.25, 0, new DustOptions(Color.SILVER, 1));
			          		player.getWorld().spawnParticle(Particle.SNOWFLAKE, particleLoc, 1, 0.25, 0.25, 0.25, 0);
			          	
			          		}
			          	  	player.getWorld().spawnParticle(Particle.REDSTONE, pLocation.getX(), pLocation.getY()+9.5, pLocation.getZ(),
			          	  									100, stormSize, 0.25, stormSize, 0, new DustOptions(Color.SILVER, 1));
			          	    player.getWorld().spawnParticle(Particle.BLOCK_CRACK, pLocation.getX(), pLocation.getY()+9.5, pLocation.getZ(),
			          			  							100, stormSize, 0.25, stormSize, 0, Material.FROSTED_ICE.createBlockData());
			                player.getWorld().spawnParticle(Particle.SNOWFLAKE, pLocation, 58, stormSize, 6, stormSize, 0.12);
			                player.getWorld().spawnParticle(Particle.REDSTONE, pLocation, 32, stormSize, 5.8, stormSize, 0.15, new DustOptions(Color.AQUA, 1));
		                	Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(areaLoc, 5, 5, 5);
                			for (Entity entity : nearbyEntities) {
                                if (entity instanceof LivingEntity /*&& entity.getUniqueId() != player.getUniqueId()*/) {
            
                                	((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 2));
                                	entity.setFreezeTicks(200);
                                }
                				
                			}
			                   spawnDelay -= 0.09D;
			                if(spawnDelay <= 0D) {
			                	int Offset = ThreadLocalRandom.current().nextInt(2, 5 + 1);
			                	new IceSpikes(Offset, rainLoc, player, plugin);
			                	spawnDelay = 1D;
			                }
			               
			                if(circleSize <= 10D) {
			                	 circleSize += 0.18D;
			                }
			                if(stormSize <= 4.6D) {
			                	stormSize += 0.09D;
			                }
			                   areaTimer -= 0.09D;
			                if(areaTimer <= 0D) {
			                	
			                	this.cancel();
			                }
			            }
			        }.runTaskTimer(plugin, 0, 1L).getTaskId();		
			}
}
/*	  for (int d = 0; d <= 160; d += 1) {
particleLoc = new Location(player.getWorld(), rainLoc.getX(), rainLoc.getY()+10,rainLoc.getZ());
particleLoc.setX(rainLoc.getX() + Math.cos(d) * circleSize);
particleLoc.setZ(rainLoc.getZ() + Math.sin(d) * circleSize);
// player.getWorld().spawnParticle(Particle.FALLING_DUST, rainLoc.getX(), rainLoc.getY()+10, rainLoc.getZ(), 20, 3, 0.5, 3, 2, Material.BLUE_ICE.createBlockData());
player.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 3, 0.25, 0.25, 0.25, 0, new DustOptions(Color.AQUA, 1));
player.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 3, 0.25, 0.25, 0.25, 0, new DustOptions(Color.GRAY, 1));
//  player.getWorld().spawnParticle(Particle.BLOCK_CRACK, specialArrow.getLocation(), 14, 0.25, 0.25, 0.25, 0, Material.FROSTED_ICE.createBlockData());
}*/