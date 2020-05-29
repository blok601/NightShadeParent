package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.gui.setup.HostGUI;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Blok on 12/10/2017.
 */
public class ComponentHandler {

    private GameManager gameManager;
    private ScenarioManager scenarioManager;

    public ComponentHandler(GameManager gameManager, ScenarioManager scenarioManager) {
        this.gameManager = gameManager;
        this.scenarioManager = scenarioManager;
    }

    private ArrayList<Component> components;

    public void setup(){
        this.components = new ArrayList<>();
        this.gameManager = GameManager.get();

        addComponent(new AbsorptionComponent());
        addComponent(new CobbleComponent());
        addComponent(new DeathLightningComponent());
        addComponent(new EnderpearlDamageComponent());
        addComponent(new GodAppleComponent());
        addComponent(new HorseComponent());
        addComponent(new NetherComponent());
        addComponent(new NetherQuartzXPNerfFeature(gameManager));
        addComponent(new SaturationComponent());
        addComponent(new ShearsComponent(gameManager));
        addComponent(new SportsmanshipComponent());
        addComponent(new StripminingComponent());
        addComponent(new StrCompentent());
        addComponent(new HardEnchantComponent());
        addComponent(new CaneBuffComponent());

        this.components.sort(Comparator.comparing(Component::getName));
    }

    private void addComponent(Component component) {
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

    public Component getComponent(Material material) {
        for (Component component : this.components) {
            if (component.getMaterial() == material) {
                return component;
            }
        }

        return null;
    }

    public boolean handleClick(ItemStack stack, InventoryClickEvent e, int slot){

        if (slot == 26) { //main menu slot
            new HostGUI((Player) e.getWhoClicked(), gameManager, scenarioManager);
        }

        if (getComponent(stack.getType()) != null) {
            Component c = getComponent(stack.getType());

            if(c.isLocked()){
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                player.closeInventory();
                player.sendMessage(ChatUtils.message("&cThis feature is locked and can't be changed!"));
                return false;
            }

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
