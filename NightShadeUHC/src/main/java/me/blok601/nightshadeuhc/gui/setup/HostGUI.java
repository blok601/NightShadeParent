package me.blok601.nightshadeuhc.gui.setup;

import com.nightshadepvp.core.Core;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scenario.Scenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 12/9/2018.
 */
public class HostGUI {

    public HostGUI(Player player, GameManager gameManager) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatUtils.format("&5UHC Game Setup"));

        ItemBuilder post = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.ORANGE.getWoolData()))
                .name("&6&lSet the Matchpost");
        if (!Core.get().getMatchpost().equalsIgnoreCase("uhc.gg")) {//Its set
            post.lore("&7Current: &6" + Core.get().getMatchpost());
        }
        post.lore("&7&o(&6&oi&7&o) &6&oClick to set the matchpost for the UHC");

        ItemBuilder world = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.YELLOW.getWoolData()))
                .name("&e&lWorld Setup")
                .lore("&7&o(&6&oi&7&o) &6&oClick to do the world setup");
        if (gameManager.getWorld() != null) {
            world.lore("&7Current: &6" + gameManager.getWorld().getName());
        }

        ItemBuilder border = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.LIGHT_BLUE.getWoolData()))
                .name("&b&lBorder Configuration")
                .lore("&7&o(&6&oi&7&o) &6&oClick to do the border setup");

        ItemBuilder toggleable = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.CYAN.getWoolData()))
                .name("&3&lToggleable Options")
                .lore("&7&o(&6&oi&7&o) &6&oClick to view the toggleable options");

        ItemBuilder timers = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getWoolData()))
                .name("&2&lTimers")
                .lore("&7&o(&6&oi&7&o) &6&oClick to setup the game timers");

        ItemBuilder starterFood = new ItemBuilder(Material.COOKED_BEEF)
                .name("&5&lStarter Food")
                .lore("&7(&6i&7) &6Right click to increase by 1, left click to decrease by 1")
                .amount(gameManager.getStarterFood());

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemBuilder newSkull = new ItemBuilder(skull)
                .name("&5&lHost");
        if (gameManager.getHost() == null) {
            newSkull.lore("&6Not Set");
            newSkull.lore("&7(&6i&7) &6Click to claim host");
        } else {
            newSkull.skullOwner(gameManager.getHost().getName());
            newSkull.lore("&6" + gameManager.getHost().getName());
        }

        ItemBuilder maxPlayers = new ItemBuilder(Material.NAME_TAG)
                .name("&5&lMax Players")
                .lore("&eCurrent: " + gameManager.getMaxPlayers())
                .lore("&7&o(&6&oi&7&o) &6&oClick to increase by 5, left click to decrease by 5")
                .amount(gameManager.getMaxPlayers());

        ItemBuilder scenarios = new ItemBuilder(Material.WATCH)
                .name("&5&lScenarios");
        for (Scenario scenario : ScenarioManager.getEnabledScenarios()) {
            scenarios.lore("&6" + scenario.getName());
        }

        ItemBuilder appleRates = new ItemBuilder(Material.APPLE)
                .name("&5&lApple Rates")
                .lore("&eCurrent Apple Rates: " + gameManager.getAppleRates())
                .lore("&7&o(&6&oi&7&o) &6&oClick to increase by 0.5%, left click to decrease by 0.5%");


        ItemBuilder flintRates = new ItemBuilder(Material.FLINT)
                .name("&5&lFlint Rates")
                .lore("&eCurrent Flint Rates: " + gameManager.getFlintRates())
                .lore("&7&o(&6&oi&7&o) &6&oClick to increase by 0.5%, left click to decrease by 0.5%")
                .lore("&cWarning: Flint Rates will be overridden by CutClean if enabled!");

        ItemBuilder teamGame = new ItemBuilder(Material.SIGN)
                .name("&5&lTeam Game")
                .lore("&eCurrent: " + (gameManager.isIsTeam() ? "&aYes" : "&cNo"))
                .lore("&7&o(&6&oi&7&o) &6&oClick to toggle teams on and off");

        ItemBuilder teamMan = new ItemBuilder(Material.ARROW)
                .name("&5&lTeam Management")
                .lore("&eCurrent: " + (TeamManager.getInstance().isTeamManagement() ? "&cEnabled" : "&cDisabled"))
                .lore("&7&o(&6&oi&7&o) &6&oClick to toggle team management");

        ItemBuilder teamSize = new ItemBuilder(Material.NETHER_STAR)
                .name("&5&lTeam Size")
                .lore("&eCurrent: " + TeamManager.getInstance().getTeamSize())
                .lore("&7&o(&6&oi&7&o) &6&oClick to increase team size, right click to decrease team size")
                .amount(TeamManager.getInstance().getTeamSize());

        ItemBuilder start = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.LIME.getWoolData()))
                .name("&a&lStart the UHC")
                .lore("&7&o(&6&oi&7&o) &6&oClick to star the uhc in 3 minutes")
                .lore("&cWarning: You may not undo this process! &4Make sure all settings are correct!")
                .amount(3);

        inventory.setItem(0, post.make());
        inventory.setItem(2, world.make());
        inventory.setItem(4, border.make());
        inventory.setItem(6, toggleable.make());
        inventory.setItem(8, timers.make());
        inventory.setItem(13, start.make());
        inventory.setItem(27, starterFood.make());
        inventory.setItem(28, newSkull.make());
        inventory.setItem(29, maxPlayers.make());
        inventory.setItem(30, scenarios.make());
        inventory.setItem(31, appleRates.make());
        inventory.setItem(32, flintRates.make());
        inventory.setItem(33, teamGame.make());
        inventory.setItem(34, teamMan.make());
        inventory.setItem(35, teamSize.make());
        player.openInventory(inventory);
    }

}
