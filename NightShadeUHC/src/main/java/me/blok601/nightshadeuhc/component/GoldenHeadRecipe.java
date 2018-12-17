package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Created by Blok on 7/22/2017.
 */
public class GoldenHeadRecipe {

    public GoldenHeadRecipe(){
        ItemStack head = new ItemBuilder(Material.GOLDEN_APPLE).name("&6Golden Head").make();

        ShapedRecipe recipe = new ShapedRecipe(head);

        recipe.shape(new String[]{
                "@@@", "@#@", "@@@"
        });

        recipe.setIngredient('@', Material.GOLD_INGOT);
        recipe.setIngredient('#', Material.SKULL_ITEM, 3);
        Bukkit.getServer().addRecipe(recipe);
    }

}
