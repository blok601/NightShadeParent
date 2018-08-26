package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BloodDiamondsScenario extends Scenario{


    public BloodDiamondsScenario() {
        super("Blood Diamonds", "When a player mines a diamond, they lose half a heart", new ItemBuilder(Material.DIAMOND).name("Blood Diamonds").make());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){

        if(!isEnabled()) return;

        if (e.getBlock().getType() == Material.DIAMOND_ORE){
            e.getPlayer().damage(1.0);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BAT_DEATH, 3, 3);
        }
    }
}
