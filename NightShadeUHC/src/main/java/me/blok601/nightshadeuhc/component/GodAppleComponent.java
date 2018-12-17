package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 2/10/2018.
 */
public class GodAppleComponent extends Component{

    public GodAppleComponent() {
        super("God Apples", new ItemBuilder(new ItemStack(Material.getMaterial(322), 1, (short) 1)).name("&eGod Apples").make(), false);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){

        if(isEnabled()){
            return;
        }

        ItemStack item = e.getCurrentItem();
        final short id = 1;
        final ItemStack gapple = new ItemStack(Material.getMaterial(322), 1, id);
        if (item != null && (item.equals(gapple))) {
            e.setCancelled(true);
        }
    }
}
