package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Blok on 12/10/2017.
 */
public abstract class Component implements Listener{

    private String name;
    private boolean enabled;
    private boolean locked;
    private Material material;
    private boolean defaultState;

    public Component(String name, Material material, boolean defaultState) {
        this.name = name;
        this.material = material;
        this.defaultState = defaultState;
        this.enabled = defaultState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void lock(){
        if(locked){
            this.locked = false;
        }else{
            this.locked = true;
        }
    }

    public void toggle(){
        this.enabled = !this.enabled;
    }

    public Material getMaterial() {
        return material;
    }


    public void click(InventoryClickEvent e, int slot){
        toggle();
        Player p = (Player) e.getWhoClicked();

        ItemBuilder builder = new ItemBuilder(material);
        builder.lore(isEnabled() ? ChatUtils.format("&aEnabled") : ChatUtils.format("&cDisabled"), true);

        e.getInventory().setItem(slot, builder.make());
        p.updateInventory();

        onToggle(isEnabled(), p);
        if (isEnabled()) {
            p.sendMessage(ChatUtils.message("&eSuccessfully &aenabled &3" + getName() + "&e."));
        } else {
            p.sendMessage(ChatUtils.message("&eSuccessfully &cdisabled &3" + getName() + "&e."));
        }
        p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 3, 3);
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean getDefaultState() {
        return defaultState;
    }

    public void onToggle(boolean newState, Player p) {

    }
}
