package com.nightshadepvp.core.punishment;

import com.nightshadepvp.core.punishment.type.ban.*;
import com.nightshadepvp.core.punishment.type.dq.AvoidingMeetupPunishment;
import com.nightshadepvp.core.punishment.type.dq.BenefitingPunishment;
import com.nightshadepvp.core.punishment.type.dq.CampingPunishment;
import com.nightshadepvp.core.punishment.type.dq.StalkingPunishment;
import com.nightshadepvp.core.punishment.type.mute.DisrespectPunishment;
import com.nightshadepvp.core.punishment.type.mute.HackusationPunishment;
import com.nightshadepvp.core.punishment.type.mute.SpamPunishment;
import com.nightshadepvp.core.punishment.type.mute.ToxcictyPunishment;
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
import java.util.stream.Collectors;

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

    public void setup(){
        this.punishing = new HashMap<>();
        this.punishments= new ArrayList<>();
        this.punishments.add(new ExploitingBugsPunishment());
        this.punishments.add(new HackedClientPunishment());
        this.punishments.add(new HostLyingPunishment());
        this.punishments.add(new iPvPPunishment());
        this.punishments.add(new LagMachinePunishment());
        this.punishments.add(new SpoilingPunishment());
        this.punishments.add(new XrayPunishment());
        this.punishments.add(new BenefitingPunishment());
        this.punishments.add(new CampingPunishment());
        this.punishments.add(new StalkingPunishment());
        this.punishments.add(new HackusationPunishment());
        this.punishments.add(new SpoilingPunishment());
        this.punishments.add(new SpamPunishment());
        this.punishments.add(new ToxcictyPunishment());
        this.punishments.add(new DisrespectPunishment());
        this.punishments.add(new AvoidingMeetupPunishment());
    }

    public ItemBuilder getChildStack() {
        return new ItemBuilder( new ItemStack(Material.INK_SACK, 1, (short) 8));
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
        ItemStack skullStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skullStack.getItemMeta();
        skullMeta.setOwner(getPunishing().get(player));
        skullStack.setItemMeta(skullMeta);

        inventory.setItem(3, new ItemBuilder(Material.PAPER).lore("&eClick to view player history").name("&5Player History").make());
        inventory.setItem(4, skullStack);
        inventory.setItem(5, new ItemBuilder(Material.PACKED_ICE).name("&5Freeze player").lore("&eClick to freeze player").make());
        ArrayList<AbstractPunishment> bans = this.punishments.stream().filter(abstractPunishment -> abstractPunishment.getPunishmentType() == PunishmentType.BAN).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<AbstractPunishment> mutes = this.punishments.stream().filter(abstractPunishment -> abstractPunishment.getPunishmentType() == PunishmentType.MUTE).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<AbstractPunishment> warns = this.punishments.stream().filter(abstractPunishment -> abstractPunishment.getPunishmentType() == PunishmentType.WARNING).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<AbstractPunishment> dqs = this.punishments.stream().filter(abstractPunishment -> abstractPunishment.getPunishmentType() == PunishmentType.DQ).collect(Collectors.toCollection(ArrayList::new));
        for (AbstractPunishment abstractPunishment : bans) {
            inventory.setItem(i, abstractPunishment.getItemStack());
            i++;
        }

        for (AbstractPunishment abstractPunishment : mutes) {
            inventory.setItem(i, abstractPunishment.getItemStack());
            i++;
        }

        for (AbstractPunishment abstractPunishment : warns) {
            inventory.setItem(i, abstractPunishment.getItemStack());
            i++;
        }

        for (AbstractPunishment abstractPunishment : dqs) {
            inventory.setItem(i, abstractPunishment.getItemStack());
            i++;
        }

        player.openInventory(inventory);

    }

    public AbstractPunishment getAbstractPunishment(ItemStack itemStack){
        return punishments.stream().filter(abstractPunishment -> abstractPunishment.getItemStack().getType() == itemStack.getType()).findAny().orElse(null);
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
