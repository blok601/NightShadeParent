package me.blok601.nightshadeuhc.listeners.gui;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.gui.setup.ComponentGUI;
import me.blok601.nightshadeuhc.gui.setup.HostGUI;
import me.blok601.nightshadeuhc.gui.setup.SettingsGUI;
import me.blok601.nightshadeuhc.gui.setup.world.BorderConfigGUI;
import me.blok601.nightshadeuhc.gui.setup.world.OverWorldGUI;
import me.blok601.nightshadeuhc.gui.setup.world.WorldGUI;
import me.blok601.nightshadeuhc.listeners.modules.ComponentHandler;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.staff.SetupStage;
import me.blok601.nightshadeuhc.teams.TeamManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 12/13/2018.
 */
public class GameSetupInventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getInventory() == null) {
            return;
        }

        if (e.getCurrentItem().getType() == Material.AIR) return;

        Player p = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();
        NSPlayer user = NSPlayer.get(p.getUniqueId());
        Inventory inventory = e.getInventory();
        int slot = e.getSlot();

        if (e.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "Game Settings")) {
            e.setCancelled(true);
        }


        if (inventory.getName().equalsIgnoreCase(ChatColor.YELLOW + "Toggleable Options")) {
            e.setCancelled(true);
            ComponentHandler.getInstance().handleClick(e.getCurrentItem(), e, e.getSlot());
        }

        if (inventory.getName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "UHC Game Setup")) {
            e.setCancelled(true);
            if (slot == 0) { //matchpost
                p.closeInventory();
                p.sendMessage(ChatUtils.message("&eChat your matchpost now, or say 'cancel' to leave the setup process"));
                GameManager.get().getSetupStageHashMap().put(p, SetupStage.MATCHPOST);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        GameManager.get().getSetupStageHashMap().remove(p);
                    }
                }.runTaskLater(UHC.get(), 2 * 20 * 60);
                return;
            } else if (slot == 2) {
                //Open world GUI
                p.sendMessage(ChatUtils.message("&eOpening the world setup gui..."));
                new WorldGUI(p);
            }
        }

        if (inventory.getName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "World Setup")) {
            e.setCancelled(true);
            if (slot == 26) {
                //back
                new HostGUI(p);
                return;
            } else if (slot == 12) {
                //Over world shit
                new OverWorldGUI(p);
                return;
            } else if (slot == 14) {
                //TODO: Finish nether
            }
        }

        if (inventory.getName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "Overworld Setup")) {
            e.setCancelled(true);
            if (slot == 3) {
                //Seed
                p.closeInventory();
                p.sendMessage(ChatUtils.message("&eChat your desired seed now, or type `cancel` to leave the setup process"));
                GameManager.get().getSetupStageHashMap().put(p, SetupStage.SEED);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        GameManager.get().getSetupStageHashMap().remove(p);
                    }
                }.runTaskLater(UHC.get(), 2 * 20 * 60);
            } else if (slot == 5) {
                //Create
                p.closeInventory();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.sendMessage(ChatUtils.message("&eYou world is now being created and pregenned..."));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv create UHC" + p.getName() + " NORMAL -s " + GameManager.get().getSetupSeed());
                    }
                }.runTaskLater(UHC.get(), 1);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.sendMessage(ChatUtils.message("&eYour world has been created!"));
                        p.sendMessage(ChatUtils.message("&ePregen will begin in 5 seconds..."));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                GameManager.get().setWorld(Bukkit.getWorld("UHC" + p.getName()));
                                p.chat("/wb " + GameManager.get().getWorld().getName() + " set " + GameManager.get().getSetupRadius() + " " + GameManager.get().getSetupRadius() + " 0 0");
                                p.chat("/wb " + GameManager.get().getWorld().getName() + " fill 250");
                                p.chat("/wb fill confirm");
                                p.sendMessage(ChatUtils.message("&aPregen in world &b" + GameManager.get().getWorld().getName() + " &ehas begun!"));
                            }
                        }.runTaskLater(UHC.get(), 5 * 20);
                    }
                }.runTaskLater(UHC.get(), 50);
            } else if (slot == 8) {
                new WorldGUI(p);
                return;
            } else if (slot == 18) {
                //- 100
                GameManager.get().setSetupRadius(GameManager.get().getSetupRadius() - 100);
                ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                        .name("&6Current Border")
                        .lore("&eCurrent Border: " + GameManager.get().getSetupRadius());
                inventory.setItem(22, currentBorder.make());
                p.updateInventory();
            } else if (slot == 19) {
                GameManager.get().setSetupRadius(GameManager.get().getSetupRadius() - 50);
                ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                        .name("&6Current Border")
                        .lore("&eCurrent Border: " + GameManager.get().getSetupRadius());
                inventory.setItem(22, currentBorder.make());
                p.updateInventory();
            } else if (slot == 20) {
                GameManager.get().setSetupRadius(GameManager.get().getSetupRadius() - 10);
                ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                        .name("&6Current Border")
                        .lore("&eCurrent Border: " + GameManager.get().getSetupRadius());
                inventory.setItem(22, currentBorder.make());
                p.updateInventory();
            } else if (slot == 21) {
                GameManager.get().setSetupRadius(GameManager.get().getSetupRadius() - 5);
                ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                        .name("&6Current Border")
                        .lore("&eCurrent Border: " + GameManager.get().getSetupRadius());
                inventory.setItem(22, currentBorder.make());
                p.updateInventory();
            } else if (slot == 23) {
                GameManager.get().setSetupRadius(GameManager.get().getSetupRadius() + 5);
                ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                        .name("&6Current Border")
                        .lore("&eCurrent Border: " + GameManager.get().getSetupRadius());
                inventory.setItem(22, currentBorder.make());
                p.updateInventory();
            } else if (slot == 24) {
                GameManager.get().setSetupRadius(GameManager.get().getSetupRadius() + 10);
                ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                        .name("&6Current Border")
                        .lore("&eCurrent Border: " + GameManager.get().getSetupRadius());
                inventory.setItem(22, currentBorder.make());
                p.updateInventory();
            } else if (slot == 25) {
                GameManager.get().setSetupRadius(GameManager.get().getSetupRadius() + 50);
                ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                        .name("&6Current Border")
                        .lore("&eCurrent Border: " + GameManager.get().getSetupRadius());
                inventory.setItem(22, currentBorder.make());
                p.updateInventory();
            } else if (slot == 26) {
                GameManager.get().setSetupRadius(GameManager.get().getSetupRadius() + 100);
                ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                        .name("&6Current Border")
                        .lore("&eCurrent Border: " + GameManager.get().getSetupRadius());
                inventory.setItem(22, currentBorder.make());
                p.updateInventory();
            }
        }

        if (inventory.getName().equalsIgnoreCase(ChatColor.YELLOW + "Border Config")) {
            e.setCancelled(true);

            if (GameState.getState() != GameState.WAITING) {
                p.sendMessage(ChatUtils.message("&cThe game has already started!"));
                return;
            }


            if (slot == 26) {
                new SettingsGUI("UHC Game Settings", 2, p);
                return;
            }

            int cBorder = GameManager.get().getShrinks()[slot];

            if (e.getClick() == ClickType.LEFT) {
                GameManager.get().getShrinks()[slot] = cBorder + 50; //Add 50
            } else if (e.getClick() == ClickType.RIGHT) {
                if (GameManager.get().getShrinks()[slot] - 50 < 0) return;
                GameManager.get().getShrinks()[slot] = cBorder - 50; //Minus 50
            } else {
                return;
            }

            ItemStack stack = e.getCurrentItem();
            ItemBuilder builder = new ItemBuilder(stack);

            int border = GameManager.get().getShrinks()[slot];
            if (border == 0 && GameState.getState() != GameState.WAITING) {
                return;
            } else {
                builder.lore(ChatUtils.format("&6" + border), true);
            }

            inventory.setItem(slot, builder.make());
            p.updateInventory();

        }

        if (inventory.getName().equalsIgnoreCase("UHC Game Settings")) {
            e.setCancelled(true);
            if (user.getRank().getValue() < Rank.HOST.getValue()) return;

            if (e.getCurrentItem().getType() == Material.ARROW) {
                //Team Manager
                if (e.getClick() == ClickType.LEFT) {
                    //Toggler
                    TeamManager.getInstance().setTeamManagement(!TeamManager.getInstance().isTeamManagement());

                    inventory.remove(e.getCurrentItem());

                    ItemBuilder builder = new ItemBuilder(Material.ARROW);
                    builder.amount(TeamManager.getInstance().getTeamSize());


                    builder.name(ChatUtils.format("&eTeam Management")).lore(ChatUtils.format(TeamManager.getInstance().isTeamManagement() ? "&aTrue" : "&cFalse"), true);


                    inventory.setItem(e.getSlot(), builder.make());
                    p.updateInventory();
                }
            }

            if (e.getCurrentItem().getType() == Material.SIGN) {
                if (GameManager.get().isIsTeam()) {
                    GameManager.get().setIsTeam(false);
                    ItemBuilder builder = new ItemBuilder(Material.SIGN);
                    builder.name(ChatUtils.format("&eTeam Game")).lore(GameManager.get().isIsTeam() ? ChatUtils.format("&aYes") : ChatUtils.format("&cNo"));
                    inventory.setItem(e.getSlot(), builder.make());
                    p.updateInventory();
                } else {
                    GameManager.get().setIsTeam(true);
                    ItemBuilder builder = new ItemBuilder(Material.SIGN);
                    builder.name(ChatUtils.format("&eTeam Game")).lore(GameManager.get().isIsTeam() ? ChatUtils.format("&aYes") : ChatUtils.format("&cNo"));
                    inventory.setItem(e.getSlot(), builder.make());
                    p.updateInventory();
                }
            }

            if (e.getCurrentItem().getType() == Material.ANVIL) {
                new ComponentGUI(p);
            }

            if (e.getCurrentItem().getType() == Material.QUARTZ_BLOCK) {
                new BorderConfigGUI(p);
            }

            //p.sendMessage(ChatUtils.message("&eToggled " + f.getName()  + f.isEnabled() ?  "&a on" : "&coff"));


            if (e.getSlot() == 0) {

                if (GameState.getState() == GameState.INGAME) {
                    p.sendMessage(ChatUtils.message("&cYou can't change this setting while the game is running!"));
                    return;
                }

                if (e.getClick() == ClickType.LEFT) {
                    GameManager.get().setFinalHealTime(GameManager.get().getFinalHealTime() + 300); // Add 5 mins
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (GameManager.get().getFinalHealTime() - 300 < 0) {
                        return;
                    }
                    GameManager.get().setFinalHealTime(GameManager.get().getFinalHealTime() - 300); // Add 5 mins
                } else {
                    return;
                }

                ItemStack itemStack = e.getCurrentItem(); // Know its the final heal one
                ItemBuilder builder = new ItemBuilder(itemStack);
                int finalHeal = GameManager.get().getFinalHealTime();
                if (finalHeal == 0 && GameState.getState() != GameState.WAITING) {
                    builder.lore(ChatUtils.format("&6Not set or already passed!"), true);
                } else {
                    builder.lore(ChatUtils.format("&6" + GameManager.get().getFinalHealTime() / 60 + " minutes"), true);
                }
                builder.lore("&7(&6&oi&r&7) &6&oTime until Final Heal is given");

                inventory.setItem(0, builder.make());
                p.updateInventory();
            } else if (e.getSlot() == 1) {
                //This is pvp time
                if (GameState.getState() == GameState.INGAME) {
                    p.sendMessage(ChatUtils.message("&cYou can't change this setting while the game is running!"));
                    return;
                }

                if (e.getClick() == ClickType.LEFT) {
                    GameManager.get().setPvpTime(GameManager.get().getPvpTime() + 300); // Add 5 mins
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (GameManager.get().getPvpTime() - 300 < 0) {
                        return;
                    }
                    GameManager.get().setPvpTime(GameManager.get().getPvpTime() - 300); // Add 5 mins
                } else {
                    return;
                }

                ItemStack itemStack = e.getCurrentItem(); // Know its the final heal one
                ItemBuilder builder = new ItemBuilder(itemStack);
                builder.removeLore(0);
                int pvpTime = GameManager.get().getPvpTime();
                if (pvpTime == 0 && GameState.getState() != GameState.WAITING) {
                    builder.lore(ChatUtils.format("&6Not set or already passed!"), true);
                } else {
                    builder.lore(ChatUtils.format("&6" + GameManager.get().getPvpTime() / 60 + " minutes"), true);
                }
                builder.lore("&7(&6&oi&r&7) &6&oTime until PvP is enabled");

                inventory.setItem(e.getSlot(), builder.make());
                p.updateInventory();
            } else if (e.getSlot() == 2) {
                //This is border time

                if (GameState.getState() == GameState.INGAME) {
                    p.sendMessage(ChatUtils.message("&cYou can't change this setting while the game is running!"));
                    return;
                }

                if (e.getClick() == ClickType.LEFT) {
                    GameManager.get().setBorderTime(GameManager.get().getBorderTime() + 300); // Add 5 mins
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (GameManager.get().getBorderTime() - 300 < 0) {
                        return;
                    }
                    GameManager.get().setBorderTime(GameManager.get().getBorderTime() - 300); // remove 5 mins
                } else {
                    return;
                }

                ItemStack itemStack = e.getCurrentItem(); // Know its the final heal one
                ItemBuilder builder = new ItemBuilder(itemStack);
                builder.removeLore(0);
                int borderTime = GameManager.get().getBorderTime();
                if (borderTime == 0 && GameState.getState() != GameState.WAITING) {
                    builder.lore(ChatUtils.format("&6Not set or already passed!"), true);
                } else {
                    builder.lore(ChatUtils.format("&6" + GameManager.get().getBorderTime() / 60 + " minutes"), true);
                }
                builder.lore("&7(&6&oi&r&7) &6&oTime until First Border Shrink");

                inventory.setItem(e.getSlot(), builder.make());
                p.updateInventory();
            } else if (e.getSlot() == 3) {
                //Meetup Time

                if (GameState.getState() == GameState.INGAME) {
                    p.sendMessage(ChatUtils.message("&cYou can't change this setting while the game is running!"));
                    return;
                }

                if (e.getClick() == ClickType.LEFT) {
                    GameManager.get().setMeetupTime(GameManager.get().getMeetupTime() + 300); // Add 5 mins
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (GameManager.get().getMeetupTime() - 300 < 0) {
                        return;
                    }
                    GameManager.get().setMeetupTime(GameManager.get().getMeetupTime() - 300); // remove 5 mins
                } else {
                    return;
                }

                ItemStack itemStack = e.getCurrentItem(); // Know its the final heal one
                ItemBuilder builder = new ItemBuilder(itemStack);
                builder.removeLore(0);
                int borderTime = GameManager.get().getMeetupTime();
                if (borderTime == 0 && GameState.getState() != GameState.WAITING) {
                    builder.lore(ChatUtils.format("&6Not set or already passed!"), true);
                } else {
                    builder.lore(ChatUtils.format("&6" + GameManager.get().getMeetupTime() / 60 + " minutes"), true);
                }
                builder.lore("&7(&6&oi&r&7) &6&oTime until \"Meetup\" begins");

                inventory.setItem(e.getSlot(), builder.make());
                p.updateInventory();

            }
        }
    }

}
