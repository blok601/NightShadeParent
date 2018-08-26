package me.blok601.nightshadeuhc.gui;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/6/2018.
 */
public class MoleKitGUI {

    public MoleKitGUI(Player player){
        GuiBuilder builder = new GuiBuilder();
        builder.name(ChatColor.DARK_AQUA + "Mole Kit Selector");
        builder.rows(1);

        ItemBuilder troll = new ItemBuilder(Material.WEB).name("&6Troll Kit").lore("&5Troll Kit&8»").lore("&e- 16 Cobwebs").lore("&e- 5 TNT").lore("&e- 1 Flint and Steel");
        ItemBuilder potter = new ItemBuilder(Material.POTION).name("&6The Potter").lore("&5Potter Kit&8»").lore("&e -1 Speed 2 Potion").lore("&e- 1 Splash Potion of Weakness").lore("&e- 1 Splash Potion of Poison 2");
        ItemBuilder fighter = new ItemBuilder(Material.DIAMOND_SWORD).name("&6The Fighter").lore("&5Fighter Kit&&8»").lore("&e- 1 Diamond Sword").lore("&e- 1 Golden Apple").lore("&e- 1 Fishing Rod");
        ItemBuilder trapper = new ItemBuilder(Material.TNT).amount(3).name("&6The Trapper").lore("&5Trapper Kit&8»").lore("&e- 16 TNT").lore("&e- 1 Sticky and 1 Normal Piston").lore("&e- 1 Flint and Steel");
        ItemBuilder tank = new ItemBuilder(Material.DIAMOND_CHESTPLATE).name("&6The Tank").lore("&65ank Kit8»").lore("&e- Full Diamond Armor with 5 durability left");
        ItemBuilder enchanter = new ItemBuilder(Material.ENCHANTMENT_TABLE).name("&6The Enchanter").lore("&5Enchanter Kit8»").lore("&e- 1 Enchanting Table").lore("&e- 64 XP Bottles").lore("&e- 8 Lapis Blocks");
        ItemBuilder healer = new ItemBuilder(Material.GOLDEN_APPLE).name("&6The Healer").lore("&5Healer Kit8»").lore("&e- 2 Golden Apples").lore("&e- 1 Splash Healing Potion");
        ItemBuilder projectile = new ItemBuilder(Material.BOW).name("&6The Projectile").lore("&5Projectile Kit8»").lore("&e- 1 Bow").lore("&e- 64 Arrows").lore("&e- 1 Fishing Rod");

        builder.item(0, troll.make());
        builder.item(1, potter.make());
        builder.item(2, fighter.make());
        builder.item(3, trapper.make());
        builder.item(4, tank.make());
        builder.item(5, enchanter.make());
        builder.item(6, healer.make());
        builder.item(7, projectile.make());

        player.openInventory(builder.make());
    }

}
