package me.blok601.nightshadeuhc.packet;

import me.blok601.nightshadeuhc.UHC;
import net.minecraft.server.v1_8_R3.ContainerEnchantTable;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

/**
 * Created by Blok on 8/21/2018.
 */
public class PrepareItemEnchant_1_8_R3 implements PrepareItemEnchant{

	private Random rand = new Random();

	private final JavaPlugin plugin;

	public PrepareItemEnchant_1_8_R3(UHC plugin){
		this.plugin = plugin;
	}

	@Override
	public void randomizeSeed(PrepareItemEnchantEvent e){
		CraftInventoryView view = (CraftInventoryView) e.getView();
		ContainerEnchantTable table = (ContainerEnchantTable) view.getHandle();
		table.f = rand.nextInt();// Set the enchantment seed
	}

	@Override
	public void oldEnchantCosts(PrepareItemEnchantEvent e){
		CraftInventoryView view = (CraftInventoryView) e.getView();
		ContainerEnchantTable table = (ContainerEnchantTable) view.getHandle();
		generateNewCosts(table.costs, rand, Math.min(e.getEnchantmentBonus(), 15));
	}

	@Override
	public void hideEnchants(PrepareItemEnchantEvent e){
		CraftInventoryView view = (CraftInventoryView) e.getView();
		ContainerEnchantTable table = (ContainerEnchantTable) view.getHandle();
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()-> {// Remove the display of what enchantment you will get
			clearArray(table.h);
		}, 1);
	}
}
