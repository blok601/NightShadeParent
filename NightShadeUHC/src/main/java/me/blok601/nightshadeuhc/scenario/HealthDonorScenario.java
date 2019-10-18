package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;

public class HealthDonorScenario extends Scenario {
    public HealthDonorScenario() {
        super("Health Donor", "You can donate health to other players using /donatehealth", new ItemBuilder(Material.GOLDEN_APPLE).name("Health Donor").make());
    }

}
