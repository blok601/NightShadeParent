package me.blok601.nightshadeuhc.gui;

import com.nightshadepvp.core.utils.ItemBuilder;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 11/5/2017.
 */
public class ConfigGUI {

    public ConfigGUI(Player player) {
        GuiBuilder menu = new GuiBuilder();
        menu.name(ChatUtils.format("&5Game Settings"));
        menu.rows(5);

        ItemStack paper = new ItemBuilder(Material.PAPER).name(ChatUtils.format("&6Time Left")).lore(ChatUtils.format("&3Final Heal» &e" + GameManager.get().getFinalHealTime() / 60  + " minutes"))
                .lore(ChatUtils.format("&3PvP Enabled» &e" + GameManager.get().getPvpTime() / 60  + " minutes")).lore(ChatUtils.format("&3Meetup» &e" + GameManager.get().getBorderTime() / 60 + " minutes")).make();

        ItemStack mining = new ItemBuilder(Material.IRON_PICKAXE).name(ChatUtils.format("&6Mining Information")).lore(ChatUtils.format("&3Stripmining» &eAbove y32 &cONLY")).lore(ChatUtils.format("&3Rollercoastering» &e&aAllowed"))
                .make();

        ItemStack apples = new ItemBuilder(Material.APPLE).name(ChatUtils.format("&6Apples")).lore(ChatUtils.format("&3Apple rates» &e"+ GameManager.get().getAppleRates() + "%")).make();

        ItemStack healing =new ItemBuilder(Material.GOLDEN_APPLE).name(ChatUtils.format("&6Healing")).lore(ChatUtils.format("&3Golden Heads» &eHeal 4 hearts")).lore(ChatUtils.format("&3Health Potions» &aOff")).make();

        ItemStack potions = new ItemBuilder(Material.POTION).name(ChatUtils.format("&6Potions")).lore(ChatUtils.format("&3Nether» &cDisabled")).lore(ChatUtils.format("&3Tier 2 Potions» &cOff")).make();

        ItemStack combat = new ItemBuilder(Material.DIAMOND_SWORD).name(ChatUtils.format("&6PvP")).lore(ChatUtils.format("&3iPvP» &cNot Allowed")).lore(ChatUtils.format("&3iPvP» &eBannable")).make();

        ItemStack bannable = new ItemBuilder(Material.ANVIL).name(ChatUtils.format("&6Bannable Offenses")).lore(ChatUtils.format("&3Hacked Clients» &e2 Months &8(&5Subject to UBL&8)")).lore(ChatUtils.format("&3Xray» &e2 Months &8(&5Subject to UBL&8)"))
                .lore(ChatUtils.format("&3iPvP» &e3 days")).lore(ChatUtils.format("&3Chat Spam» &e15 minute mute")).lore(ChatUtils.format("&3Racism» &eMute (length + punishment type can change)")).make();

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        me.blok601.nightshadeuhc.utils.ItemBuilder newSkull = new me.blok601.nightshadeuhc.utils.ItemBuilder(skull);
        newSkull.name(ChatUtils.format("&6Host")).lore(ChatUtils.format("&3Host» &e" + GameManager.get().getHost().getName()));
        newSkull.skullOwner(GameManager.get().getHost().getName());

        ItemStack server = new ItemBuilder(Material.PAPER).name(ChatUtils.format("&6Server Information")).lore(ChatUtils.format("&3Owners» &eBL0K, Milan and CarterAimz"))
                .lore(ChatUtils.format("&3Provider» &eOVH")).lore(ChatUtils.format("&3Developers» &eBL0K, Braidenn_, Database and Austin")).lore(ChatUtils.format("&3Website» &ewww.nightshadepvp.com")).lore(ChatUtils.format("&3Twitter» &e@NightShadePVPMC"))
                .lore(ChatUtils.format("&3Discord» &5discord.me/NightShadeMC"))
                .make();

        menu.item(1, paper);
        menu.item(4, mining);
        menu.item(7, apples);
        menu.item(19, healing);
        menu.item(22, potions);
        menu.item(25, combat);
        menu.item(37, newSkull.make());
        menu.item(40, server);
        menu.item(43, bannable);

        player.openInventory(menu.make());
    }
}
