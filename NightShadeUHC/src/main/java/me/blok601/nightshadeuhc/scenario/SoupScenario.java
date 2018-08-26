package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Blok on 7/19/2018.
 */
public class SoupScenario extends Scenario{

    public SoupScenario() {
        super("Soup", "Eating a soup will heal you 2.5 hearts", new ItemBuilder(Material.MUSHROOM_SOUP).name("Soup").make());
    }

    @EventHandler
    public void onEat(PlayerInteractEvent e){
        if(!isEnabled()) return;

        Player p = e.getPlayer();

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (p.getItemInHand().getType() == Material.MUSHROOM_SOUP){
                if(p.getHealth() >= p.getMaxHealth()-5){
                    p.setHealth(p.getMaxHealth());
                }else{
                    p.setHealth(p.getHealth()+5);
                }

                p.getItemInHand().setType(Material.BOWL);
            }
        }
    }
}
