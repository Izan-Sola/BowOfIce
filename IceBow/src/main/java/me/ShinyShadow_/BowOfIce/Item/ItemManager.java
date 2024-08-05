package me.ShinyShadow_.BowOfIce.Item;

import java.awt.Color;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemManager {
	
		public static ItemStack Ice_Bow; 
		public static void init() {
			
			createIce_Bow();
			//getCommand("givefirebow").setExecutor(new fireBowCommand());
		}
		private static void createIce_Bow() {
			
			ItemStack item = new ItemStack(Material.BOW, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.AQUA + "Elemental Bow: Ice.");
			meta.setLore(Arrays.asList("Normal attack: Frost Bite.",
										"Shoot a frozen arrow that will trap",
										"the entity shot in ice blocks.",
										"Additionally, the frozen arrow will break",
										"causing the explosion to slow and damage",
										"all entities in the area of effect.",
										"",
										"Special Attack: Ice Storm.",
										"Summon a big storm which greatly",
										"reduces the temperature of the area",
										"slowing and freezing all the entities",
										"caught in the storm while summoning falling",
										"ice spikes that will trap the entity hit."));
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(meta);
			Ice_Bow = item;
		}
}