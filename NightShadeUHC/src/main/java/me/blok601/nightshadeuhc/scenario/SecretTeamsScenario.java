package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Blok on 12/23/2018.
 */
public class SecretTeamsScenario extends Scenario {
    public SecretTeamsScenario() {
        super("Secret Teams", "All teams are hidden", new ItemBuilder(Material.BOOK).name("&eSecret Teams").make());
    }


}
