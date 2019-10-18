package me.blok601.nightshadeuhc.entity.object;

import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Blok on 7/10/2018.
 */
public class Reward {

    private String name; //2x Crate Key or something
    private ArrayList<ItemStack> items;


    public Reward(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemStack> items) {
        this.items = items;
    }

    public void announce(Player player){
        ChatUtils.sendAll("&a" + player.getName() + " has been rewarded with &3" + getName());
    }

    @Override
    public String toString(){
        return getName();
    }

    public void give(Player player){
        if(player.getInventory().firstEmpty() == -1){
            items.forEach(itemStack -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
            player.sendMessage(ChatUtils.message("&eYour inventory was full, dropping " + getName()));
        }else{
            player.sendMessage(ChatUtils.message("&eYou have received &3" + getName() + "&e!"));
        }
    }
}
