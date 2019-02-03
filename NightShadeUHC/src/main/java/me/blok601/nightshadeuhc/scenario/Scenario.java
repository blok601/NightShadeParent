package me.blok601.nightshadeuhc.scenario;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static me.blok601.nightshadeuhc.util.ChatUtils.format;

/**
 * Created by Blok on 5/12/2017.
 */
public class Scenario implements Listener {


    private String name;
    private String desc;
    private boolean enabled;
    private ItemStack item;
    private String abbreviation;

    private ArrayList<String> commands;
    private ScenarioManager scenarioManager;

    public Scenario(String name, String desc, ItemStack item){
        this.name = name;
        this.desc = desc;
        this.item = item;
        this.abbreviation = null;
        enabled = false;
        this.commands = null;
    }

    public Scenario(String name, String desc, ItemStack item, ArrayList<String> commands){
        this.name = name;
        this.desc = desc;
        this.item = item;
        this.abbreviation = null;
        enabled = false;
        this.commands = commands;
    }

    public Scenario(String name, String desc, String abbreviation, ItemStack item){
        this.name = name;
        this.desc = desc;
        this.item = item;
        enabled = false;
        this.abbreviation = abbreviation;
        this.commands = null;
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
        return format("&4" + getName() + " &8» ");
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void onToggle(boolean newState) {
        onToggle(newState, null);
    }

    public void onToggle(boolean newState, Player toggler) {
        //Override method
    }

    public ScenarioManager getScenarioManager() {
        return scenarioManager;
    }

    public void setScenarioManager(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }
}
