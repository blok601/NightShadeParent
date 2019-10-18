package me.blok601.nightshadeuhc.gui;


import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Master on 9/2/2017.
 */
public class GuiBuilder {

    private String name;
    private int rows;
    private HashMap<Integer, ItemStack> items;

    public GuiBuilder(){
        this.name = "Inventory";
        this.rows = 1;
        this.items = new HashMap<>();
    }

    public GuiBuilder rows(int newRows){
        this.rows = newRows;
        return  this;
    }

    public GuiBuilder name(String newName){
        this.name = ChatUtils.format(newName);
        return this;
    }

    public GuiBuilder item(int slot, ItemStack item){
        items.put(slot, item);
        return this;
    }

    public Inventory make(){

        if(rows*9 > 54){
            throw new IllegalArgumentException("Too many rows in the created inventory!");
        }

        Inventory inv = Bukkit.createInventory(null, rows*9, name);
        for (int f : items.keySet()){
            inv.setItem(f, items.get(f));
        }

        return inv;
    }

    public HashMap<Integer, ItemStack> getItems() {
        return items;
    }
}
