package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Blok on 6/21/2018.
 */
public class PainfulStones extends Scenario{

    public PainfulStones() {
        super("PainfulStones", "You take ½ heart walking on gravel unless you wear boots", new ItemBuilder(Material.GRAVEL).name("PainfulStones").make());
    }
}
