package me.blok601.nightshadeuhc.listeners.modules;

import me.blok601.nightshadeuhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Blok on 12/10/2017.
 */
public class ComponentHandler {
    private static ComponentHandler ourInstance = new ComponentHandler();

    public static ComponentHandler getInstance() {
        return ourInstance;
    }

    private ComponentHandler() {
    }

    private ArrayList<Component> components;

    public void setup(){
        this.components = new ArrayList<>();

        addComponent(new EnderpearlComponent());
        addComponent(new AbsorptionComponent());
        addComponent(new SaturationModule());
        addComponent(new GodAppleComponent());
        addComponent(new CobbleComponent());

    }


    public void addComponent(Component component){
        this.components.add(component);
        Bukkit.getPluginManager().registerEvents(component, UHC.get());
    }

    public Component getComponent(String name){
        for (Component component : this.components){
            if(component.getName().equalsIgnoreCase(name)){
                return component;
            }
        }

        return null;
    }

    public Component getComponent(ItemStack itemStack){
        for (Component component : this.components) {
            if(component.getItemStack().equals(itemStack)){
                return component;
            }
        }

        return null;
    }

    public boolean handleClick(ItemStack stack, InventoryClickEvent e, int slot){

        if(getComponent(stack) != null){
            Component c  = getComponent(stack);

            c.click(e, slot);
        }else{
            return false;
        }

        return true;

    }

    public ArrayList<Component> getComponents() {
        return components;
    }
}
