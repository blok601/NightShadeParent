package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;

public class TrueLoveScenario extends Scenario{

    public TrueLoveScenario(){
        super("True Love", "FFA but you can team with 1 person. If your teammate dies you can team with someone else, but only one can win", new ItemBuilder(Material.RED_ROSE).name("&eTrue Love").make());
    }
}
