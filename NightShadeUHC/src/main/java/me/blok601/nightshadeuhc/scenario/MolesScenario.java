package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.events.PvPEnableEvent;
import me.blok601.nightshadeuhc.events.ScenarioEnableEvent;
import me.blok601.nightshadeuhc.gui.MoleKitGUI;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.teams.Team;
import me.blok601.nightshadeuhc.teams.TeamManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Blok on 6/22/2018.
 */
public class MolesScenario extends Scenario{

    public static Map<UUID, Boolean> moles;


    public MolesScenario() {
        super("Moles", "Each team is assigned a mole their job is to eliminate their teammates", new ItemBuilder(Material.DIAMOND_SPADE).name("Moles").make());
         moles = new HashMap<>();
    }

    @EventHandler
    public void onEnable(ScenarioEnableEvent e){
        if(e.getScenario().getName().equalsIgnoreCase("Moles")){
            if(!GameManager.isIsTeam()){
                e.setCancelled(true);
                if(GameManager.getHost() != null){
                    GameManager.getHost().sendMessage(ChatUtils.format("&4Moles» &cI can't be enabled in a non-teams game! Enable teams first!"));
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPvP(PvPEnableEvent e){
        //Assign moles here
        if(!isEnabled()) return;

        new BukkitRunnable(){
            int counter = 5;

            @Override
            public void run() {
                if(counter == 0){
                    setMoles();
                    ChatUtils.sendAll("&5Moles have been set... you know who you are!");
                    this.cancel();
                    return;
                }
                ChatUtils.sendAll("&5Moles will be set in &b" + counter);
                counter --;
            }
        }.runTaskTimer(UHC.get(), 0, 20);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!isEnabled()) return;

        if(moles.containsKey(e.getPlayer().getUniqueId())){
            e.getPlayer().sendMessage(getPrefix() + "&eYou are a mole! Shh... it's a secret!");
            if(!moles.get(e.getPlayer().getUniqueId())){
                e.getPlayer().sendMessage(getPrefix() + "&eYou can still get your mole kit! Type &5/molekit &eto redeem it!");
            }
        }
    }


    private void setMoles(){
        Random random = new Random();
        OfflinePlayer offlinePlayer;
        Player online;
        for (Team team : TeamManager.getInstance().getTeams()){
            offlinePlayer = Bukkit.getOfflinePlayer(team.getMembers().get(random.nextInt(team.getMembers().size())));
            team.setMole(offlinePlayer.getUniqueId()); //Clean up later
            moles.put(team.getMole(), false);

            if(Bukkit.getPlayer(offlinePlayer.getUniqueId()) != null){
                online = Bukkit.getPlayer(offlinePlayer.getUniqueId());
                online.sendMessage(ChatUtils.format(getPrefix() + " &eYou are the mole! Shhh... it's a secret!"));
                new MoleKitGUI(online);
            }
        }
    }

}
