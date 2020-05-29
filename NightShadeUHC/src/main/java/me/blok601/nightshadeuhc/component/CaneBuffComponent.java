package me.blok601.nightshadeuhc.component;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BiomeDecorator;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class CaneBuffComponent extends Component {

    public CaneBuffComponent() {
        super("Sugar Cane Rates", Material.SUGAR_CANE, true, "Sugar can rates are increased");
        this.lock();

        try {
            Field extraReedField = BiomeDecorator.class.getDeclaredField("F");
            extraReedField.setAccessible(true);

            for (BiomeBase biome : BiomeBase.getBiomes()) {
                if (biome == null) {
                    continue;
                }

                BiomeDecorator decorator = biome.as;
                extraReedField.setInt(decorator, extraReedField.getInt(decorator) * 6);
            }
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }
}
