package com.nightshadepvp.core;


import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Blok on 7/1/2017.
 */
public enum Rank {

    PLAYER(0, "", "", "Player"),
    FRIEND(0, "&7[&aFriend&7] &a", "&a", "Friend"),
    WINNER(0, "&7[&cWinner&7] &c", "&c", "Player"),
    DRAGON(1, "&7[&aDragon&7] &a", "&a", "Dragon"),
    COMET(2, "&7[&eComet&7] &e", "&e", "Comet"),
    GUARDIAN(3, "&7[&cGuardian&7] &c", "&c", "Guardian"),
    YOUTUBE(4, ChatUtils.format("&7[&eMedia&7] &e"), "&e", "YouTube"),
    TRIAL(5, "&7[&dTrial-Mod&7] &d", "&d", "Trial"),
    MOD(6, "&7[&dMod&7] &d", "&d", "Staff"),
    TRIALHOST(7, "&7[&6Trial-Host&7] &6", "&6", "Trial"),
    HOST(7, "&7[&6Host&7] &6", "&6", "Staff"),
    SRMOD(7, "&7[&bSrMod&7] &b", "&b", "Senior"),
    SRHOST(8, "&7[&bSrHost&7] &b", "&b", "Senior"),
    ADMIN(10, "&7[&cAdmin&7] &c", "&c", "Administrator"),
    DEVELOPER(15, ChatUtils.format("&7[&5Developer&7] &5"), "&5", "Developer"),
    PLATFORMADMIN(15, "&7[&cPlat-Admin&7] &c", "&c", "Platform Admin"),
    HEADADMIN(15, "&7[&9Manager&7] &9", "&9", "Manager"),
    COOWNER(20, "&7[&5Co-Owner&7] &5", "&5", "Owner"),
    OWNER(100, "&7[&5Owner&7] &5", "&5", "Owner");

    private int value;
    private String prefix;
    private String nameColor;
    private ItemStack item;
    private String discordRankName;

    Rank(int value, String prefix, String nameColor, String discordRankName) {
        this.prefix = ChatUtils.format(prefix);
        this.value = value;
        this.nameColor = ChatUtils.format(nameColor);
        item = new ItemBuilder(Material.PAPER).name(getNameColor() + name()).make();
        this.discordRankName = discordRankName;
    }

    public boolean bypassWhitelist(Rank rank) {
        ArrayList<Rank> bypassWhitelist = new ArrayList<>();
        for (Rank r : Rank.values()) {
            if (r.getValue() >= Rank.DRAGON.getValue()) {
                bypassWhitelist.add(r);
            }

            if (r == Rank.FRIEND) {
                bypassWhitelist.add(r);
            }
        }

        return bypassWhitelist.contains(rank);
    }

    public ItemStack getItem() {
        return item;
    }

    public int getValue() {
        return value;
    }

    public boolean hasRequiredRank(Rank rank) {
        return rank.getValue() >= this.getValue();
    }

    public boolean isHigherThan(Rank rank) {
        return this.value > rank.getValue();
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNameColor() {
        return nameColor;
    }

    public static Rank getRank(ItemStack item) {
        for (Rank r : Rank.values()) {
            if (r.getItem().equals(item)) return r;
        }

        return null;
    }

    public boolean isStaff(Rank rank) {
        return rank.getValue() >= Rank.TRIAL.getValue();
    }

    public boolean isPlayerLadder(Rank rank) {
        return rank.getValue() < Rank.DRAGON.getValue();
    }

    public boolean isDonorRank(Rank rank) {
        return rank.getValue() >= Rank.DRAGON.getValue() && rank.getValue() <= Rank.GUARDIAN.getValue();
    }

    public boolean isDeathSpectate(Rank rank) {
        return rank.getValue() > PLAYER.getValue();
    }

    public String getDiscordRankName() {
        return discordRankName;
    }
}
