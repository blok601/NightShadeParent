package com.nightshadepvp.core.punishment;

import com.nightshadepvp.core.punishment.type.ban.FlyInSpawnPunishment;
import com.nightshadepvp.core.punishment.type.ban.HackedClientPunishment;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Blok on 8/24/2018.
 */
public class PunishmentHandler {

    private static PunishmentHandler ourInstance = new PunishmentHandler();

    public static PunishmentHandler getInstance() {
        return ourInstance;
    }

    private HashMap<Player, String> punishing; //Staff -> Player Name

    private PunishmentHandler() {
    }

    private ArrayList<AbstractPunishment> punishments;
    private final ItemStack childStack = new ItemStack(Material.INK_SACK, 1, (short) 8);

    public void setup(){
        this.punishing = new HashMap<>();
        this.punishments= new ArrayList<>();
        this.punishments.add(new HackedClientPunishment());
        this.punishments.add(new FlyInSpawnPunishment());
    }

    public ItemStack getChildStack() {
        return childStack;
    }

    public ItemStack getBackButton(){
        ItemStack itemStack = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
        return new ItemBuilder(itemStack).name("&eBack").lore("&eClick to return to the punishment menu").make();
    }

    public HashMap<Player, String> getPunishing() {
        return punishing;
    }

    public void createGUI(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, "Punishment Menu");
        int i = 18;
        ItemStack skullStack = new ItemStack(Material.SKULL_ITEM);
        SkullMeta skullMeta = (SkullMeta) skullStack.getItemMeta();
        skullMeta.setOwner(Bukkit.getOfflinePlayer(getPunishing().get(player)).getName());
        skullStack.setItemMeta(skullMeta);

        inventory.setItem(5, skullStack);
        inventory.setItem(3, new ItemBuilder(Material.PAPER).lore("&eClick to view player history").name("&5Player History").make());
        for (AbstractPunishment punishment : this.punishments){
            inventory.setItem(i, punishment.getItemStack());
            i++;
        }
    }

    private AbstractPunishment getAbstractPunishment(ItemStack itemStack){
        return punishments.stream().filter(abstractPunishment -> abstractPunishment.getItemStack().equals(itemStack)).findAny().orElse(null);
    }

    public AbstractPunishment getAbstractPunishment(String name){
        return punishments.stream().filter(abstractPunishment -> abstractPunishment.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public void handleClick(ItemStack stack, InventoryClickEvent e){
        if(getAbstractPunishment(stack) != null){
            AbstractPunishment abstractPunishment = getAbstractPunishment(stack);
            abstractPunishment.click(e);
        }else{
            e.getWhoClicked().sendMessage(ChatUtils.message("&cThere was an error loading that punishment! Please try again later!"));
        }
    }


}
