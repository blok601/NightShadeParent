package me.blok601.nightshadeuhc.tasks;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayerColl;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.commands.extras.Freeze;
import me.blok601.nightshadeuhc.commands.extras.PvP;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.teams.TeamManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


/**
 * Created by Blok on 3/16/2017.
 */
public class GameStartTask extends BukkitRunnable {

    private Player p;
    private int finalHealTime;
    private int pvpTime;
    private World world;
    private int borderTime;
    private int firstShrink;
    private boolean isTeam;
    private int counter;

    public GameStartTask(Player p, int finalHealTime, int pvpTime, int borderTime, World world, int firstShrink, boolean isTeam) {
        this.p = p;
        this.finalHealTime = finalHealTime;
        this.pvpTime = pvpTime;
        this.world = world;
        this.borderTime = borderTime;
        this.firstShrink = firstShrink;
        this.isTeam = isTeam;
        this.counter = 120;
        if (TeamManager.getInstance().isTeamManagement()) TeamManager.getInstance().setTeamManagement(false);
        if (GameManager.isIsTeam()) {
            if (GameManager.getHost() == null) {
                NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer -> nsPlayer.hasRank(Rank.TRIALHOST)).findFirst().ifPresent(nsPlayer -> nsPlayer.getPlayer().chat("/team color"));
            } else {
                GameManager.getHost().chat("/team color");
            }
        }
    }


    @Override
    public void run() {
        if (counter >= 0) {
            if (counter % 60 == 0 && counter != 0) {
                ChatUtils.sendAll("The game will start in " + counter / 60 + " minutes!");
            } else if (counter <= 10) {
               if (counter > 0) {
                   PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + counter + "\",\"color\":\"dark_aqua\",\"bold\":true}"), 0, 20, 0);
                   for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllOnline()) {
                       if (uhcPlayer.isUsingOldVersion()) {
                           uhcPlayer.msg(ChatUtils.message("&3The game will start in &e" + counter + " &3seconds"));
                           continue;
                       }
                       ((CraftPlayer) uhcPlayer.getPlayer()).getHandle().playerConnection.sendPacket(packet);
                   }
                } else {
                   PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Go!\",\"color\":\"dark_aqua\",\"bold\":true}"), 0, 10, 0);
                   for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllOnline()) {
                       if (uhcPlayer.isUsingOldVersion()) {
                           uhcPlayer.msg(ChatUtils.message("&3The game has started!"));
                           continue;
                       }
                       ((CraftPlayer) uhcPlayer.getPlayer()).getHandle().playerConnection.sendPacket(packet);
                   }
                    GameManager.world = world;
                    GameManager.setDate();
                    PvP.disablePvP();
                    Freeze.stop();

                    Bukkit.getOnlinePlayers().forEach(o -> o.setMaxHealth(20.0));
                    Bukkit.getOnlinePlayers().forEach(o -> o.setHealth(o.getMaxHealth()));
                    Bukkit.getOnlinePlayers().forEach(o -> o.setFoodLevel(20));
                    Bukkit.getOnlinePlayers().forEach(o -> o.setLevel(0));
                    Bukkit.getOnlinePlayers().forEach(o -> o.setExp(0));



                    Core.get().getLogManager().log(Logger.LogType.INFO, "Everyone has been healed and fed!");
                    Bukkit.getOnlinePlayers().forEach(o -> o.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10)));
                    TimerTask timerTask = GameManager.getTimer();
                    timerTask.start();
                    Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent());
                    StringBuilder builder = new StringBuilder();
                    ScenarioManager.getEnabledScenarios().forEach(scenario -> builder.append(scenario.getName()).append(", "));
                    Bukkit.getOnlinePlayers().forEach(p ->{
                        p.sendMessage(ChatUtils.format("&5&m-----------------------------------"));

                        p.sendMessage(ChatUtils.format("&e&lHost: &3" + GameManager.getHost().getName()));
                        if(builder.length() > 0){
                            p.sendMessage(ChatUtils.format("&e&lScenarios: &3" + builder.toString().substring(0, builder.length()-1)));
                        }else{
                            p.sendMessage(ChatUtils.format("&e&lScenarios: &3None"));
                        }

                        p.sendMessage(" ");
                        p.sendMessage(ChatUtils.format("&e&lFinal Heal Time: " + GameManager.getFinalHealTime() /60 + " minutes"));
                        p.sendMessage(ChatUtils.format("&e&lPvP Time: " + GameManager.getPvpTime() /60 + " minutes"));
                        p.sendMessage(ChatUtils.format("&e&lMeetup Time: " + GameManager.getBorderTime() /60 + " minutes"));

                        p.sendMessage(ChatUtils.format("&5&m-----------------------------------"));

                        p.playSound(p.getLocation(), Sound.BAT_DEATH, 5, 5);
                    });

                   FinalHealTask task = new FinalHealTask(finalHealTime);
                   if(finalHealTime >0){
                       task.runTaskTimer(UHC.get(), 0, Util.TICKS);
                   }
                   GameManager.setFinalHealTask(task);

                   PvPTask pvpTask = new PvPTask(pvpTime, world);
                   if(pvpTime > 0){
                       pvpTask.runTaskTimer(UHC.get(), 0, Util.TICKS);
                   }
                   GameManager.setPvpTask(pvpTask);


                   WorldBorderTask worldBorderTask = new WorldBorderTask(borderTime, world, firstShrink);
                   if(borderTime > 0){
                       worldBorderTask.runTaskTimer(UHC.get(), 0, Util.TICKS);
                   }
                   GameManager.setWorldBorderTask(worldBorderTask);


                   GameState.setState(GameState.INGAME);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        for (Player p2 : Bukkit.getOnlinePlayers()) {
                            UHCPlayer gamePlayer = UHCPlayer.get(p2.getUniqueId());
                            if (!gamePlayer.isSpectator()) {
                                p.showPlayer(p2);
                            }else{
                                p.hidePlayer(p2);
                            }
                        }
                    }
                    counter = -1;
                    cancel();
                    return;
                }
            }
        }
        counter--;
    }
}

