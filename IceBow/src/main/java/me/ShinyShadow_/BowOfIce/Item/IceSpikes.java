package me.ShinyShadow_.BowOfIce.Item;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class IceSpikes {
	
	private double spikeTimer = 10D;
	private double iceTimer = 4D;
	private double radius = 10;
	
	private boolean snow = true;
	private boolean startIceTimer = false;
	
	private Location test;

		public IceSpikes(double Offset, Location rainLoc, Player player, JavaPlugin plugin) {
			
			Random r = new Random();
		    double randomRadius = r.nextDouble() * radius;
		    double theta =  Math.toRadians(r.nextDouble() * 360);
		    double phi = Math.toRadians(r.nextDouble() * 180);
		 
		    double x = randomRadius * Math.cos(theta) * Math.sin(phi);
		  //  double y = randomRadius * Math.sin(theta) * Math.cos(phi);
		    double z = randomRadius * Math.cos(phi);
		    Location newLoc = rainLoc.clone().add(x, 15, z);
		    Entity iceSpike = player.getWorld().spawnFallingBlock(newLoc, Material.ICE.createBlockData());
		   
		    ItemDisplay spike0 = player.getWorld().spawn(iceSpike.getLocation(), ItemDisplay.class);
		    spike0.setItemStack(new ItemStack(Material.ICE));
		    
		    ItemDisplay spike1 = player.getWorld().spawn(iceSpike.getLocation(), ItemDisplay.class);
		    spike1.setItemStack(new ItemStack(Material.ICE));
		    spike1.setTransformation(new Transformation(new Vector3f(0, 0, 0), new AxisAngle4f(),
		    										  new Vector3f(0.8f, 1.3f, 0.8f), new AxisAngle4f(0, 0, 0, 0)));
		    ItemDisplay spike2 = player.getWorld().spawn(iceSpike.getLocation(), ItemDisplay.class);
		    spike2.setItemStack(new ItemStack(Material.ICE));
		    spike2.setTransformation(new Transformation(new Vector3f(0, 0, 0), new AxisAngle4f(),
		    										  new Vector3f(0.6f, 1.3f, 0.6f), new AxisAngle4f(0, 0, 0, 0)));

	        new BukkitRunnable() {
	                 @Override
	                 public void run() {
	                	 spikeTimer -= 0.09D;
	                	
	                	 if(iceSpike.getLocation() != null && !iceSpike.isOnGround()) {
	                		 spike0.teleport(iceSpike.getLocation().add(0, 0, 0));
	                		 spike1.teleport(iceSpike.getLocation().add(0, -0.8, 0));
	                		 spike2.teleport(iceSpike.getLocation().add(0, -1.6, 0));
	                		 
	           
	                	Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(iceSpike.getLocation(), 0.4, 2, 0.4);
	                			for (Entity target : nearbyEntities) {
	                			//i should change this ngl but i did it the lazy way
	                		        if (target instanceof LivingEntity && target.getUniqueId() != player.getUniqueId() && !target.isDead()) {	            
	                						startIceTimer = true;
	                						((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4, 40));
	                					    target.setVelocity(new Vector(0, 0, 0));
	                					    ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4, 40));
	                						target.getWorld().getBlockAt(target.getLocation().add(0, 2, 0)).setType(Material.ICE);
	                						target.getWorld().getBlockAt(target.getLocation().add(1, 0, 0)).setType(Material.ICE);
	                						target.getWorld().getBlockAt(target.getLocation().add(1, 1, 0)).setType(Material.ICE);
	                						target.getWorld().getBlockAt(target.getLocation().add(0, 0, 1)).setType(Material.ICE);
	                						target.getWorld().getBlockAt(target.getLocation().add(0, 1, 1)).setType(Material.ICE);
	                						target.getWorld().getBlockAt(target.getLocation().add(-1, 0, 0)).setType(Material.ICE);
	                						target.getWorld().getBlockAt(target.getLocation().add(-1, 1, 0)).setType(Material.ICE);
	                						target.getWorld().getBlockAt(target.getLocation().add(0, 0, -1)).setType(Material.ICE);
	                						target.getWorld().getBlockAt(target.getLocation().add(0, 1, -1)).setType(Material.ICE);
	                		    }
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
	                		        }
	                		  }
	                			
	                			if(startIceTimer) {
	                				iceTimer -= 0.09D;
	                			}
	                	 }
	                if(iceSpike.getWorld().getBlockAt(iceSpike.getLocation().add(0, -2.5, 0)).isPassable()) {
	                		 player.getWorld().spawnParticle(Particle.BLOCK_CRACK, iceSpike.getLocation().add(0, -1, 0), 30, 0.25, 0.3, 0.25, 0, Material.ICE.createBlockData());
	                		 player.getWorld().spawnParticle(Particle.SNOW_SHOVEL, iceSpike.getLocation().add(0, -1, 0), 30, 0.25, 0.3, 0.25, 0);
	                	}
                	 if(!iceSpike.getWorld().getBlockAt(iceSpike.getLocation().add(0, -2.5, 0)).isPassable()) {
                		 
                	 if(snow) {
                		 player.getWorld().playSound(iceSpike.getLocation(), Sound.BLOCK_GLASS_BREAK, 2.5f, -2f);
                		 player.getWorld().spawnParticle(Particle.BLOCK_CRACK, iceSpike.getLocation(), 80, 1, 1, 1, 2, Material.ICE.createBlockData());
                		 player.getWorld().spawnParticle(Particle.SNOW_SHOVEL, iceSpike.getLocation(), 80, 1, 1, 1, 2);
             		    player.getWorld().spawnFallingBlock(spike1.getLocation(), Material.SNOW.createBlockData());
             		    int Offset = ThreadLocalRandom.current().nextInt(1, 2);
            		    player.getWorld().spawnFallingBlock(spike1.getLocation().add(Offset, 0, -Offset), Material.SNOW.createBlockData());
            		    player.getWorld().spawnFallingBlock(spike1.getLocation().add(-Offset, 0, Offset), Material.SNOW.createBlockData());
            		    player.getWorld().spawnFallingBlock(spike1.getLocation().add(-Offset, 0, -Offset), Material.SNOW.createBlockData());
            		    player.getWorld().spawnFallingBlock(spike1.getLocation().add(Offset, 0, Offset), Material.SNOW.createBlockData());
            		    snow = false;
                		 }
                		 	iceSpike.remove();
	                		 
	                	 }
            		 	if(spikeTimer <= 0D) {               		 		
            		 		spike0.remove();
            		 		spike1.remove();
            		 		spike2.remove();
            		 		this.cancel();
            		 	}
	                 }
	        	 }.runTaskTimer(plugin, 0L, 1L);
	         }
		}

