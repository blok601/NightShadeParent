package me.blok601.nightshadeuhc.gui;

import com.nightshadepvp.core.utils.ChatUtils;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.Scenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.teams.TeamManager;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 9/10/2017.
 */
public class SettingsGUI {

    public SettingsGUI(String name, int rows, Player player) {
        GuiBuilder guiBuilder = new GuiBuilder();
        guiBuilder.name(name);
        guiBuilder.rows(rows);

        ItemBuilder fHeal = new ItemBuilder(Material.PAPER).name(ChatUtils.format("&eFinal Heal"));
        int finalHeal = GameManager.getFinalHealTime();
        if (finalHeal == 0 && GameState.getState() == GameState.WAITING) {
            fHeal.lore(ChatUtils.format("&6Not set"));
        } else {
            fHeal.lore(ChatUtils.format("&6" + GameManager.getFinalHealTime() / 60 + " minutes"));
        }
        fHeal.lore("&7(&6oi&r&7) &6&oTime until Final Heal is given");

        ItemBuilder pTime = new ItemBuilder(Material.PAPER).name(ChatUtils.format("&ePvP Time"));
        int pvpTime = GameManager.getPvpTime();
        if (pvpTime == 0 && GameState.getState() == GameState.WAITING) {
            pTime.lore(ChatUtils.format("&6Not set"));
        } else {
            pTime.lore(ChatUtils.format("&6" + GameManager.getPvpTime() / 60 + " minutes"));
        }
        pTime.lore("&7(&6oi&r&7) &6&oTime until PvP is enabled");

        ItemBuilder bTime = new ItemBuilder(Material.PAPER).name(ChatUtils.format("&eBorder Shrink Time"));
        int borderTime = GameManager.getBorderTime();
        if (borderTime == 0 && GameState.getState() == GameState.WAITING) {
            bTime.lore(ChatUtils.format("&6Not set"));
        } else {
            bTime.lore(ChatUtils.format("&6" + GameManager.getBorderTime() / 60 + " minutes"));
        }
        bTime.lore("&7(&6oi&r&7) &6&oTime until first border shrink");

        ItemBuilder mTime = new ItemBuilder(Material.PAPER).name("&eMeetup Time");
        int meetup = GameManager.getMeetupTime();
        if (meetup == 0 && GameState.getState() == GameState.WAITING) {
            mTime.lore("&6Not Set");
        } else {
            mTime.lore("&6" + borderTime / 60 + " minutes");
        }
        bTime.lore("&7(&6oi&r&7) &6&oTime until \"Meetup\" begins");

        ItemBuilder isTeam = new ItemBuilder(Material.SIGN).name(ChatUtils.format("&eTeam Game"));
        isTeam.lore(GameManager.isIsTeam() ? ChatUtils.format("&aYes") : ChatUtils.format("&cNo"));

        ItemBuilder scenarios = new ItemBuilder(Material.WATCH).name(ChatUtils.format("&eScenarios"));
        for (Scenario scenario : ScenarioManager.getEnabledScenarios()){
            scenarios.lore(ChatUtils.format("&6" + scenario.getName()));
        }

        ItemStack config = new ItemBuilder(Material.ANVIL).name(ChatUtils.format("&eToggleable Options")).lore(ChatUtils.format("&5Click to access the toggleable options")).make();
        ItemStack borderConfig = new ItemBuilder(Material.QUARTZ_BLOCK).name(ChatUtils.format("&eBorder Configuration")).lore(ChatUtils.format("&5Click to access the border config")).make();


        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemBuilder newSkull = new ItemBuilder(skull);
        if (GameManager.getHost() == null) {
            newSkull.lore(ChatUtils.format("&6Not set"));
        } else {
            newSkull.skullOwner(GameManager.getHost().getName());
            newSkull.lore(ChatUtils.format("&6" + GameManager.getHost().getName()));
        }

        ItemBuilder arrow = new ItemBuilder(Material.ARROW);
        arrow.name(ChatUtils.format("&eTeam Management")).lore(ChatUtils.format(TeamManager.getInstance().isTeamManagement() ? "&aTrue" : "&cFalse"));
        arrow.amount(TeamManager.getInstance().getTeamSize());

        guiBuilder.item(0, fHeal.make());
        guiBuilder.item(1, pTime.make());
        guiBuilder.item(2, bTime.make());
        guiBuilder.item(3, mTime.make());
        guiBuilder.item(5, isTeam.make());
        guiBuilder.item(8, newSkull.make());
        guiBuilder.item(7, scenarios.make());
        guiBuilder.item(6, arrow.make());
        guiBuilder.item(12, config);
        guiBuilder.item(14, borderConfig);

        player.openInventory(guiBuilder.make());
    }

}
