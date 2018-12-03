package me.blok601.nightshadeuhc.listeners.gui;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.gui.BorderConfigGUI;
import me.blok601.nightshadeuhc.gui.ComponentGUI;
import me.blok601.nightshadeuhc.gui.leaderboards.StatsGUI;
import me.blok601.nightshadeuhc.gui.leaderboards.TopKillsGUI;
import me.blok601.nightshadeuhc.gui.leaderboards.TopWinnersGUI;
import me.blok601.nightshadeuhc.listeners.modules.ComponentHandler;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.MolesScenario;
import me.blok601.nightshadeuhc.scenario.Scenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.teams.TeamManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class InvClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getInventory() == null) {
            return;
        }

        NSPlayer user = NSPlayer.get(p.getUniqueId());


        if(e.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "Game Settings")){
            e.setCancelled(true);
        }
        

        if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Toggleable Options")){
            e.setCancelled(true);
            ComponentHandler.getInstance().handleClick(e.getCurrentItem(), e, e.getSlot());
        }

        if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Border Config")){
            e.setCancelled(true);

            if(GameState.getState() != GameState.WAITING){
                p.sendMessage(ChatUtils.message("&cThe game has already started!"));
                return;
            }

            int slot = e.getSlot();
            int cBorder = GameManager.getShrinks()[slot];

            if(e.getClick() == ClickType.LEFT){
                GameManager.getShrinks()[slot] = cBorder+50; //Add 50
            }else if(e.getClick() == ClickType.RIGHT){
                if(GameManager.getShrinks()[slot] - 50 < 0)return;
                GameManager.getShrinks()[slot] = cBorder-50; //Minus 50
            }else{
                return;
            }

            ItemStack stack = e.getCurrentItem();
            ItemBuilder builder = new ItemBuilder(stack);

            int border = GameManager.getShrinks()[slot];
            if(border ==0 && GameState.getState() != GameState.WAITING){
                return;
            }else{
                builder.lore(ChatUtils.format("&6" + border), true);
            }

            e.getInventory().setItem(slot, builder.make());
            p.updateInventory();

        }

        if (e.getInventory().getName().equalsIgnoreCase("UHC Game Settings")) {
            e.setCancelled(true);
            if (user.getRank().getValue() < Rank.HOST.getValue()) return;

            if(e.getCurrentItem().getType() == Material.ARROW){
                //Team Manager
                if(e.getClick() == ClickType.LEFT){
                    //Toggler
                    TeamManager.getInstance().setTeamManagement(!TeamManager.getInstance().isTeamManagement());

                    e.getInventory().remove(e.getCurrentItem());

                    ItemBuilder builder = new ItemBuilder(Material.ARROW);
                    builder.amount(TeamManager.getInstance().getTeamSize());


                    builder.name(ChatUtils.format("&eTeam Management")).lore(ChatUtils.format(TeamManager.getInstance().isTeamManagement() ? "&aTrue" : "&cFalse"), true);


                    e.getInventory().setItem(e.getSlot(), builder.make());
                    p.updateInventory();
                }
            }

            if(e.getCurrentItem().getType() == Material.SIGN){
                if(GameManager.isIsTeam()){
                    GameManager.setIsTeam(false);
                    ItemBuilder builder = new ItemBuilder(Material.SIGN);
                    builder.name(ChatUtils.format("&eTeam Game")).lore(GameManager.isIsTeam() ? ChatUtils.format("&aYes") : ChatUtils.format("&cNo"));
                    e.getInventory().setItem(e.getSlot(), builder.make());
                    p.updateInventory();
                }else{
                    GameManager.setIsTeam(true);
                    ItemBuilder builder = new ItemBuilder(Material.SIGN);
                    builder.name(ChatUtils.format("&eTeam Game")).lore(GameManager.isIsTeam() ? ChatUtils.format("&aYes") : ChatUtils.format("&cNo"));
                    e.getInventory().setItem(e.getSlot(), builder.make());
                    p.updateInventory();
                }
            }

            if(e.getCurrentItem().getType() == Material.ANVIL){
                new ComponentGUI(p);
            }

            if(e.getCurrentItem().getType() == Material.QUARTZ_BLOCK){
                new BorderConfigGUI(p);
            }

            //p.sendMessage(ChatUtils.message("&eToggled " + f.getName()  + f.isEnabled() ?  "&a on" : "&coff"));


            if (e.getSlot() == 0) {

                if (GameState.getState() == GameState.INGAME) {
                    p.sendMessage(ChatUtils.message("&cYou can't change this setting while the game is running!"));
                    return;
                }

                if (e.getClick() == ClickType.LEFT) {
                    GameManager.setFinalHealTime(GameManager.getFinalHealTime() + 300); // Add 5 mins
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (GameManager.getFinalHealTime() - 300 < 0) {
                        return;
                    }
                    GameManager.setFinalHealTime(GameManager.getFinalHealTime() - 300); // Add 5 mins
                } else {
                    return;
                }

                ItemStack itemStack = e.getCurrentItem(); // Know its the final heal one
                ItemBuilder builder = new ItemBuilder(itemStack);
                int finalHeal = GameManager.getFinalHealTime();
                if (finalHeal == 0 && GameState.getState() != GameState.WAITING) {
                    builder.lore(ChatUtils.format("&6Not set or already passed!"), true);
                } else {
                    builder.lore(ChatUtils.format("&6" + GameManager.getFinalHealTime() / 60 + " minutes"), true);
                }
                builder.lore("&7(&6oi&r&7) &6&oTime until Final Heal is given");

                e.getInventory().setItem(0, builder.make());
                p.updateInventory();
            } else if (e.getSlot() == 1) {
                //This is pvp time
                if (GameState.getState() == GameState.INGAME) {
                    p.sendMessage(ChatUtils.message("&cYou can't change this setting while the game is running!"));
                    return;
                }

                if (e.getClick() == ClickType.LEFT) {
                    GameManager.setPvpTime(GameManager.getPvpTime() + 300); // Add 5 mins
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (GameManager.getPvpTime() - 300 < 0) {
                        return;
                    }
                    GameManager.setPvpTime(GameManager.getPvpTime() - 300); // Add 5 mins
                } else {
                    return;
                }

                ItemStack itemStack = e.getCurrentItem(); // Know its the final heal one
                ItemBuilder builder = new ItemBuilder(itemStack);
                builder.removeLore(0);
                int pvpTime = GameManager.getPvpTime();
                if (pvpTime == 0 && GameState.getState() != GameState.WAITING) {
                    builder.lore(ChatUtils.format("&6Not set or already passed!"), true);
                } else {
                    builder.lore(ChatUtils.format("&6" + GameManager.getPvpTime() / 60 + " minutes"), true);
                }
                builder.lore("&7(&6oi&r&7) &6&oTime until PvP is enabled");

                e.getInventory().setItem(e.getSlot(), builder.make());
                p.updateInventory();
            } else if (e.getSlot() == 2) {
                //This is border time

                if (GameState.getState() == GameState.INGAME) {
                    p.sendMessage(ChatUtils.message("&cYou can't change this setting while the game is running!"));
                    return;
                }

                if (e.getClick() == ClickType.LEFT) {
                    GameManager.setBorderTime(GameManager.getBorderTime() + 300); // Add 5 mins
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (GameManager.getBorderTime() - 300 < 0) {
                        return;
                    }
                    GameManager.setBorderTime(GameManager.getBorderTime() - 300); // remove 5 mins
                } else {
                    return;
                }

                ItemStack itemStack = e.getCurrentItem(); // Know its the final heal one
                ItemBuilder builder = new ItemBuilder(itemStack);
                builder.removeLore(0);
                int borderTime = GameManager.getBorderTime();
                if (borderTime == 0 && GameState.getState() != GameState.WAITING) {
                    builder.lore(ChatUtils.format("&6Not set or already passed!"), true);
                } else {
                    builder.lore(ChatUtils.format("&6" + GameManager.getBorderTime() / 60 + " minutes"), true);
                }
                builder.lore("&7(&6oi&r&7) &6&oTime until First Border Shrink");

                e.getInventory().setItem(e.getSlot(), builder.make());
                p.updateInventory();
            } else if (e.getSlot() == 3) {
                //Meetup Time

                if (GameState.getState() == GameState.INGAME) {
                    p.sendMessage(ChatUtils.message("&cYou can't change this setting while the game is running!"));
                    return;
                }

                if (e.getClick() == ClickType.LEFT) {
                    GameManager.setMeetupTime(GameManager.getMeetupTime() + 300); // Add 5 mins
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (GameManager.getMeetupTime() - 300 < 0) {
                        return;
                    }
                    GameManager.setMeetupTime(GameManager.getMeetupTime() - 300); // remove 5 mins
                } else {
                    return;
                }

                ItemStack itemStack = e.getCurrentItem(); // Know its the final heal one
                ItemBuilder builder = new ItemBuilder(itemStack);
                builder.removeLore(0);
                int borderTime = GameManager.getMeetupTime();
                if (borderTime == 0 && GameState.getState() != GameState.WAITING) {
                    builder.lore(ChatUtils.format("&6Not set or already passed!"), true);
                } else {
                    builder.lore(ChatUtils.format("&6" + GameManager.getMeetupTime() / 60 + " minutes"), true);
                }
                builder.lore("&7(&6oi&r&7) &6&oTime until \"Meetup\" begins");

                e.getInventory().setItem(e.getSlot(), builder.make());
                p.updateInventory();

            }
        }

        if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Leaderboards")){
            e.setCancelled(true);
            if(e.getSlot() == 11){
                p.sendMessage(ChatUtils.message("&eOpening the winner leaderboard!"));
               new TopWinnersGUI(p);
            }else if(e.getSlot() == 13){
                p.sendMessage(ChatUtils.message("&eOpening the winner leaderboard!"));
                new TopKillsGUI(p);
            }else if(e.getSlot() == 15){
                //open all around
            }
        }

        if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Top 10 Winners")){
            e.setCancelled(true);

            ItemStack item = e.getCurrentItem();
            if(item.getType() != Material.SKULL_ITEM){
                return;
            }

            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            String name = skullMeta.getOwner();
            UHCPlayer gamePlayer = UHCPlayer.get(ChatColor.stripColor(name));
            if(gamePlayer == null){
                p.sendMessage(ChatUtils.message("&cThere was an error loading that player's stats! Please try again later!"));
                p.closeInventory();
                return;
            }

            new StatsGUI(gamePlayer, p);
        }

        if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Top 10 Killers")){
            e.setCancelled(true);

            ItemStack item = e.getCurrentItem();
            if(item.getType() != Material.SKULL_ITEM){
                return;
            }

            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            String name = skullMeta.getOwner();
            UHCPlayer gamePlayer = UHCPlayer.get(ChatColor.stripColor(name));
            if(gamePlayer == null){
                p.sendMessage(ChatUtils.message("&cThere was an error loading that player's stats! Please try again later!"));
                p.closeInventory();
                return;
            }

            new StatsGUI(gamePlayer, p);
        }

        if(e.getInventory().getName().toLowerCase().contains("stats")){
            e.setCancelled(true);
        }

        if(e.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_AQUA + "Mole Kit Selector")){
            Scenario scenario = ScenarioManager.getScen("Moles");
            if(scenario == null || !scenario.isEnabled()){
                p.sendMessage(ChatUtils.message("&cMoles isn't enabled!"));
                return;
            }

            if(!MolesScenario.moles.containsKey(p.getUniqueId())){
                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&cYou are not a mole!"));
            }
            
            if(MolesScenario.moles.get(p.getUniqueId())){
                p.sendMessage(ChatUtils.format(ScenarioManager.getScen("Moles").getPrefix() + "&cYou have already gotten your mole kit!"));
                return;
            }

            e.setCancelled(true);
            int slot = e.getSlot();

            if(slot == 0){
                p.getInventory().addItem(new ItemStack(Material.WEB, 16));
                p.getInventory().addItem(new ItemStack(Material.TNT, 5));
                p.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL, 1));
                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eYou selected the &6Troll Kit&e!"));

                MolesScenario.moles.replace(p.getUniqueId(), true);
            }else if(slot == 1) {
                //Potter later
                Potion speed = new Potion(PotionType.SPEED);
                speed.setLevel(2);

                Potion weakness = new Potion(PotionType.WEAKNESS);
                weakness.setSplash(true);

                Potion poison = new Potion(PotionType.POISON);
                poison.setSplash(true);
                poison.setLevel(2);

                p.getInventory().addItem(speed.toItemStack(1), weakness.toItemStack(1), poison.toItemStack(1));
                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eYou selected the &6Potter Kit&e!"));

                MolesScenario.moles.replace(p.getUniqueId(), true);
            }else if(slot == 2){
                p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD, 1));
                p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                p.getInventory().addItem(new ItemStack(Material.FISHING_ROD, 1));
                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eYou selected the &6Fighter Kit&e!"));

                MolesScenario.moles.replace(p.getUniqueId(), true);
            }else if(slot == 3){
                p.getInventory().addItem(new ItemStack(Material.TNT, 16));
                p.getInventory().addItem(new ItemStack(Material.PISTON_STICKY_BASE), new ItemStack(Material.PISTON_BASE));
                p.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL, 1));
                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eYou selected the &6Trapper Kit&e!"));

                MolesScenario.moles.replace(p.getUniqueId(), true);
            }else if(slot == 4){
                ItemBuilder helmet = new ItemBuilder(Material.DIAMOND_HELMET).durability(358);
                ItemBuilder chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE).durability(523);
                ItemBuilder leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS).durability(490);
                ItemBuilder boots = new ItemBuilder(Material.DIAMOND_BOOTS).durability(424);

                p.getInventory().setHelmet(helmet.make());
                p.getInventory().setChestplate(chestplate.make());
                p.getInventory().setLeggings(leggings.make());
                p.getInventory().setBoots(boots.make());

                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eYou selected the &6Tank Kit&e!"));

                MolesScenario.moles.replace(p.getUniqueId(), true);
            }else if(slot == 5){
                p.getInventory().addItem(new ItemStack(Material.ENCHANTMENT_TABLE));
                p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 64));
                p.getInventory().addItem(new ItemStack(Material.LAPIS_BLOCK, 8));
                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eYou selected the &6Enchanter Kit&e!"));

                MolesScenario.moles.replace(p.getUniqueId(), true);
            }else if(slot == 6){
                Potion potion = new Potion(PotionType.INSTANT_HEAL);
                potion.setLevel(2);
                potion.setSplash(true);
                p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
                p.getInventory().addItem(new ItemStack(potion.toItemStack(1)));
                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eYou selected the &6Healer Kit&e!"));

                MolesScenario.moles.replace(p.getUniqueId(), true);
            }else if(slot == 7){
                p.getInventory().addItem(new ItemStack(Material.BOW));
                p.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eYou selected the &6Projectile Kit&e!"));

                MolesScenario.moles.replace(p.getUniqueId(), true);
            }

        }

    }

}
