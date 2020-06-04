package me.blok601.nightshadeuhc.component;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.Map;

public class FireAspectComponent extends Component{

    public FireAspectComponent() {
        super("Fire Aspect", Material.BOOK, true, "Should fire aspect be on or off?");
    }

    private Enchantment fireAspectEnchant = Enchantment.FIRE_ASPECT;
   // private Enchantment sharpnessEnchant = Enchantment.DAMAGE_ALL;

    @EventHandler
    public void on(EnchantItemEvent event) {
        if(!isEnabled()) return;

        Map<Enchantment, Integer> toAdd = event.getEnchantsToAdd();

        if (!toAdd.containsKey(fireAspectEnchant)) {
            return;
        }

        toAdd.remove(fireAspectEnchant);

        if (toAdd.containsKey(Enchantment.DAMAGE_ALL) || toAdd.containsKey(Enchantment.DAMAGE_UNDEAD) || toAdd.containsKey(Enchantment.DAMAGE_ARTHROPODS)) {
            return;
        }

        toAdd.put(Enchantment.DAMAGE_ALL, Math.min(event.whichButton() + 1, 3));
    }

}
