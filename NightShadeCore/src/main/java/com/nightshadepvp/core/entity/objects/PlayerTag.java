package com.nightshadepvp.core.entity.objects;

import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 8/3/2018.
 */
public enum PlayerTag {

    DEFAULT("", new ItemBuilder(Material.NAME_TAG).name("&7None").make()),
    BLUE_HEART("&9❤", new ItemBuilder(Material.NAME_TAG).name("&9Blue Heart &8(&9❤&8)").make()),
    PINK_HEART("&d❤", new ItemBuilder(Material.NAME_TAG).name("&dPink Heart &8(&d❤&8)").make()),
    YELLOW_HEART("&e❤", new ItemBuilder(Material.NAME_TAG).name("&eYellow Heart &8(&e❤&8)").make()),
    GOLD_HEART("&6❤", new ItemBuilder(Material.NAME_TAG).name("&6Gold Heart &8(&6❤&8)").make()),
    AQUA_HEART("&b❤", new ItemBuilder(Material.NAME_TAG).name("&bAqua Heart &8(&b❤&8)").make()),
    PINK_CHECK("&d✓", new ItemBuilder(Material.NAME_TAG).name("&dPink Check &8(&d✓&8)").make()),
    YELLOW_CHECK("&e✓", new ItemBuilder(Material.NAME_TAG).name("&eYellow Check &8(&e✓&8)").make()),
    GOLD_CHECK("&6✓", new ItemBuilder(Material.NAME_TAG).name("&6Gold Check &8(&6✓&8)").make()),
    AQUA_CHECK("&b✓", new ItemBuilder(Material.NAME_TAG).name("&bAqua Check &8(&b✓&8)").make()),
    CLEANER("&5#Cleaner", new ItemBuilder(Material.NAME_TAG).name("&5Cleaner Tag &8(&5#Cleaner&8)").make()),
    BOWSPAM("&4BowSpam", new ItemBuilder(Material.NAME_TAG).name("&4BowSpam Tag &8(&5BowSpam&8)").make()),
    TOXIC("&2Toxic", new ItemBuilder(Material.NAME_TAG).name("&2Toxic Tag &8(&2Toxic&8)").make()),
    EZ("&9EZ", new ItemBuilder(Material.NAME_TAG).name("&9EZ Tag &8(&9EZ&8)").make()),
    CUTIE("&dCutie", new ItemBuilder(Material.NAME_TAG).name("&9Cutie Tag &8(&dCutie&8)").make()),
    SWEAT("&eSweat", new ItemBuilder(Material.NAME_TAG).name("&eSweat Tag &8(&eSweat&8)").make()),
    TRIAL_PLAYER("&bTrial Player", new ItemBuilder(Material.NAME_TAG).name("&eTrial Player Tag &8(&bTrial Player&8)").make()),
    FURRY("&eFurry", new ItemBuilder(Material.NAME_TAG).name("&3Furry Tag &8(&eFurry&8)").make()),
    EGIRL("&d&nEGirl", new ItemBuilder(Material.NAME_TAG).name("&d&nEGirl Tag &8(&d&nEGirl&8)").make()),
    OG("&bOG", new ItemBuilder(Material.NAME_TAG).name("&bOG Tag &8(&bOG&8)").make()),
    DISCOVER_CARD("&fDisc&6o&fver&6Card", new ItemBuilder(Material.NAME_TAG).name("&&fDisc&6o&fver&6Card &fTag &8(&fDisc&6o&fver&6Card&8)").make()),
    SHADY("&7&oShady", new ItemBuilder(Material.NAME_TAG).name("&7&oShady &fTag &8(&7&oShady&8)").make()),
    CTRL_C("&c&lCTRL-C", new ItemBuilder(Material.NAME_TAG).name("&c&lCTRL-C Tag &8(&c&lCTRL-C&8)").make());
    OWO("&cOwO", new ItemBuilder(Material.NAME_TAG).name("&cOwO Tag &8(&cOwO)").make());
    FRUIT_OWNER("&bFruit Owner", new ItemBuilder(Material.NAME_TAG).name("&bFruit Owner Tag &8(&bFruit Owner)").make());

    private String title;
    private ItemStack stack;

    PlayerTag(String title, ItemStack stack) {
        this.title = ChatUtils.format(title);
        this.stack = stack;
    }

    public String getTitle() {
        return title;
    }

    public ItemStack getStack() {
        return stack;
    }

    public static PlayerTag getTag(ItemStack itemStack){
        for (PlayerTag playerTag : PlayerTag.values()){
            if(playerTag.getStack().equals(itemStack)){
                return playerTag;
            }
        }

        return null;
    }

    public String getFormatted(){
        if(getTitle().equalsIgnoreCase("")){
            return "";
        }
        return ChatUtils.format("&8[" + getTitle() + "&8]");
    }
}
