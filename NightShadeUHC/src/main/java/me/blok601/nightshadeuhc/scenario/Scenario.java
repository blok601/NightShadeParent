package me.blok601.nightshadeuhc.scenario;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static me.blok601.nightshadeuhc.utils.ChatUtils.format;

/**
 * Created by Blok on 5/12/2017.
 */
public class Scenario implements Listener {


    private String name;
    private String desc;
    private boolean enabled;
    private ItemStack item;

    private ArrayList<String> commands;

    public Scenario(String name, String desc, ItemStack item){
        this.name = name;
        this.desc = desc;
        this.item = item;
        enabled = false;
        this.commands = null;
    }

    public Scenario(String name, String desc, ItemStack item, ArrayList<String> commands){
        this.name = name;
        this.desc = desc;
        this.item = item;
        enabled = false;
        this.commands = commands;
    }


    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean en){
        this.enabled =en;
    }

    public ItemStack getItem() { return item; }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public String getPrefix(){
        return format("&4" + getName() + "&8» ");
    }
}
