package com.nightshadepvp.core.entity.objects;

import com.nightshadepvp.core.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 4/14/2018.
 */
public enum PlayerEffect {

    NONE(new ItemBuilder(Material.BARRIER).name("&eNone").make(), null, "&7Default"),
    FLAME(new ItemBuilder(Material.FLINT_AND_STEEL).name("&eFlames").make(), EnumParticle.FLAME, "&6Flames"),
    LAVA(new ItemBuilder(Material.LAVA_BUCKET).name("&eLava Drip").make(), EnumParticle.LAVA, "&4Lava"),
    CRIT(new ItemBuilder(Material.GOLD_AXE).name("&eMagic Crit").make(), EnumParticle.CRIT_MAGIC, "&bCrits");


    private ItemStack item;
    private EnumParticle effect;
    private String name;

    PlayerEffect(ItemStack item, EnumParticle effect, String name) {
        this.item = item;
        this.effect = effect;
        this.name = name; //NAME FOR USE BY PLUGIN ONLY
    }

    public ItemStack getItem() {
        return item;
    }

    public EnumParticle getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }

    public static PlayerEffect getEffect(ItemStack item){
        for (PlayerEffect r : PlayerEffect.values()) {
            if (r.getItem().equals(item)) return r;
        }

        return  null;
    }
}
