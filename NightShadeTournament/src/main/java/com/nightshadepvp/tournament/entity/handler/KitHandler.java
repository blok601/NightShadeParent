package com.nightshadepvp.tournament.entity.handler;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.entity.objects.PlayerInv;
import com.nightshadepvp.core.utils.InventoryUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import com.nightshadepvp.tournament.Settings;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Blok on 6/12/2018.
 */
public class KitHandler {

    private static KitHandler ourInstance = new KitHandler();

    public static KitHandler getInstance() {
        return ourInstance;
    }

    private KitHandler() {
    }

    private ArrayList<Kit> kits;
    private FileConfiguration configuration;

    public void setup(){
        this.kits = new ArrayList<>();
        configuration = Settings.getSettings().getKitsFile();
        ConfigurationSection section;
        Kit k;
        for (String kit : configuration.getKeys(false)){
            section = configuration.getConfigurationSection(kit);
            k = new Kit(kit);
            PlayerInv inv = InventoryUtils.playerInventoryFromString(section.getString("inventory"));
            ItemStack[] items = inv.getContents();
            ItemStack[] armor = inv.getArmorContents();
            ItemStack display;
            if(section.contains("display")){
                display = section.getItemStack("display");
            }else{
                display = new ItemBuilder(Material.DIAMOND_SWORD).name("&6" + kit).make();
            }

            if (section.contains("build")) {
                k.setBuild(section.getBoolean("build"));
            } else {
                k.setBuild(false);
            }

            if (section.contains("regen")) {
                k.setAllowRegen(section.getBoolean("regen"));
            } else {
                k.setAllowRegen(true);
            }

            k.setArmor(armor);
            k.setItems(items);
            k.setDisplay(display);
            this.kits.add(k);
            Core.get().getLogManager().log(Logger.LogType.DEBUG, "Registered Kit: " + kit);
        }
    }

    public void reload(){
        save();
        this.kits.clear();
        ConfigurationSection section;
        Kit k;
        for (String kit : configuration.getKeys(false)){
            section = configuration.getConfigurationSection(kit);
            k = new Kit(kit);
            PlayerInv inv = InventoryUtils.playerInventoryFromString(section.getString("inventory"));
            ItemStack[] items = inv.getContents();
            ItemStack[] armor = inv.getArmorContents();
            ItemStack display;
            if(section.contains("display")){
                display = section.getItemStack("display");
            }else{
                display = new ItemBuilder(Material.DIAMOND_SWORD).name("&6" + kit).make();
            }

            if (section.contains("build")) {
                k.setBuild(section.getBoolean("build"));
            } else {
                k.setBuild(false);
            }

            if (section.contains("regen")) {
                k.setAllowRegen(section.getBoolean("regen"));
            } else {
                k.setAllowRegen(true);
            }

            k.setArmor(armor);
            k.setItems(items);
            k.setDisplay(display);
            this.kits.add(k);
            Core.get().getLogManager().log(Logger.LogType.DEBUG, "Registered Kit: " + kit);
        }
    }

    public void save(){
        for (Kit kit : this.kits){
            PlayerInv playerInv = new PlayerInv();
            playerInv.setArmorContents(kit.getArmor());
            playerInv.setContents(kit.getItems());
            configuration.set(kit.getName() + ".inventory", InventoryUtils.playerInvToString(playerInv));
            configuration.set(kit.getName() + ".display", kit.getDisplay());
            configuration.set(kit.getName() + ".build", kit.isBuild());
            configuration.set(kit.getName() + ".regen", kit.isAllowRegen());
        }

        Settings.getSettings().saveKitsFile();
    }

    public ArrayList<Kit> getKits() {
        return kits;
    }

    public Kit getKit(String name){
        return this.kits.stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void removeKit(Kit kit){
        this.kits.remove(kit);
        configuration.set(kit.getName(), null);
        Settings.getSettings().saveKitsFile();
    }

    public boolean isKit(String name){
        return getKit(name) != null;
    }

    public Kit getKit(ItemStack display){
        return this.kits.stream().filter(kit -> kit.getDisplay().equals(display)).findFirst().orElse(null);
    }

}
