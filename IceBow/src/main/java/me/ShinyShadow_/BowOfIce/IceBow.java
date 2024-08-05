package me.ShinyShadow_.BowOfIce;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.ShinyShadow_.BowOfIce.Item.ItemListener;
import me.ShinyShadow_.BowOfIce.Item.ItemManager;
import me.ShinyShadow_.BowOfIce.commands.Commands;

public final class IceBow extends JavaPlugin {

	
	public void onEnable() {
		
		ItemManager.init();
		getCommand("givebowofice").setExecutor(new Commands());
		
		Bukkit.getPluginManager().registerEvents(new ItemListener(this), this);
	}
	
	public void onDisable() {
		
	}
}
