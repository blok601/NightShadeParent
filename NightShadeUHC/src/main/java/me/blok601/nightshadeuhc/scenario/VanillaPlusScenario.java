package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Blok on 9/30/2018.
 */
public class VanillaPlusScenario extends Scenario {

    public VanillaPlusScenario() {
        super("Vanilla+", "Apple and flint rates are higher", new ItemBuilder(Material.APPLE).name("Vanilla+").make());
    }
}
