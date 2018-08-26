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

    PLAYER(0, "", ""),
    FRIEND(0, "&8[&aFriend&8] &a", "&a"),
    WINNER(0, "&8[&cWinner&8] &c", "&c"),
    DRAGON(1, "&8[&aDragon&8] &a", "&a"),
    COMET(2, "&8[&eComet&8] &e", "&e"),
    GUARDIAN(3, "&8[&cGuardian&8] &c", "&c"),
    YOUTUBE(4, ChatUtils.format("&8[&eMedia&8] &e"), "&e"),
    TRIAL(5, "&8[&bTrial-Mod&8] &b", "&b"),
    MOD(6, "&8[&cMod&8] &c", "&c"),
    TRIALHOST(7, "&8[&bTrial-Host&8] &b", "&b"),
    HOST(7, "&8[&cHost&8] &c", "&c"),
    SRMOD(7, "&8[&dSrMod&8] &d", "&d"),
    SRHOST(8, "&8[&dSrHost&8] &d", "&d"),
    ADMIN(10, "&8[&6Admin&8] &6", "&6"),
    DEVELOPER(15, ChatUtils.format("&8[&dDeveloper&8] &d"), "&d"),
    PLATFORMADMIN(15, "&8[&3Platform Admin&8] &3", "&3"),
    HEADADMIN(15, "&8[&9Head Admin&8] &9", "&9"),
    COOWNER(20, "&8[&5Co-Owner&8] &5", "&5"),
    OWNER(30, "&8[&5Owner&8] &5", "&5");

    private int value;
    private String prefix;
    private String nameColor;
    private ItemStack item;

    Rank(int value, String prefix, String nameColor) {
        this.prefix = ChatUtils.format(prefix);
        this.value = value;
        this.nameColor = ChatUtils.format(nameColor);
        item = new ItemBuilder(Material.PAPER).name(getNameColor() + name()).make();
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


}
