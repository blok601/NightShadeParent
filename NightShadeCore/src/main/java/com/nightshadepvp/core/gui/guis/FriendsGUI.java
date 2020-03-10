package com.nightshadepvp.core.gui.guis;

import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.objects.Friend;
import com.nightshadepvp.core.gui.GuiBuilder;
import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.HashSet;

public class FriendsGUI {

    public FriendsGUI(NSPlayer player) {
        if(!player.isOnline()){
            return;
        }

        HashSet<Friend> friends = player.getFriends();
        GuiBuilder gui = new GuiBuilder()
                .name("&bFriends")
                .rows(6);

        ItemBuilder itemBuilder;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        ItemStack skullStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skullStack.getItemMeta();
        int slot = 0;
        String name;
        for (Friend friend : friends){
            name = Bukkit.getOfflinePlayer(friend.getFriendUUID()).getName();
            skullMeta.setOwner(name);
            skullStack.setItemMeta(skullMeta);
            itemBuilder = new ItemBuilder(skullStack);
            itemBuilder.name("&e" + name);
            itemBuilder.lore("&bFriends Since: " + simpleDateFormat.format(friend.getDateAdded()));
            gui.item(slot, itemBuilder.make());
            slot++;
        }

        player.getPlayer().openInventory(gui.make());
    }
}
