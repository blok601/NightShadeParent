package me.blok601.nightshadeuhc.listeners.modules;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Blok on 12/2/2018.
 */
public class NetherComponent extends Component {

    public NetherComponent() {
        super("Nether", new ItemBuilder(Material.NETHERRACK).name("&eNether").make(), false);
    }

}
