package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantedDeathScenario extends Scenario{


    public EnchantedDeathScenario() {
        super("Enchanted Death", "Enchanting Tables are Uncraftable and players drop one upon death", new ItemBuilder(Material.ENCHANTMENT_TABLE).name(ChatUtils.format("Enchanted Death")).make());
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){
        if(!isEnabled()){
            return;
        }

        ItemStack etable = new ItemStack(Material.ENCHANTMENT_TABLE, 1);

        e.getItems().add(etable);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){
        if (!isEnabled()) return;
        if(e.getWhoClicked() == null) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getInventory() == null || e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)return;

        Player p = (Player) e.getWhoClicked();

        if(e.getRecipe().getResult().getType() == Material.ENCHANTMENT_TABLE){
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(ChatUtils.format(getPrefix() + "&fEnchanting Tables are &cuncraftable&f, did you read the matchpost?"));

        }
    }
}