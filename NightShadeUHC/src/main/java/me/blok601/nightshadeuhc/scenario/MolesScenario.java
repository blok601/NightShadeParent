package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.event.PvPEnableEvent;
import me.blok601.nightshadeuhc.event.ScenarioEnableEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Blok on 6/22/2018.
 */
public class MolesScenario extends Scenario{

    public static Map<UUID, Boolean> moles;


    public MolesScenario() {
        super("Moles", "Each team is assigned a mole. Their job is to eliminate their teammates", new ItemBuilder(Material.DIAMOND_SPADE).name("Moles").make());
         moles = new HashMap<>();
    }

    @EventHandler
    public void onEnable(ScenarioEnableEvent e){
        if(e.getScenario().getName().equalsIgnoreCase("Moles")){
            if(!GameManager.get().isIsTeam()){
                e.setCancelled(true);
                if(GameManager.get().getHost() != null){
                    GameManager.get().getHost().sendMessage(ChatUtils.format("&4Moles» &cI can't be enabled in a non-teams game! Enable teams first!"));
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
            new BukkitRunnable(){
                @Override
                public void run() {
                    e.getPlayer().sendMessage(ChatUtils.format(getPrefix() + "&eYou are a mole! Shh... it's a secret!"));
                    if(!moles.get(e.getPlayer().getUniqueId())){ //False means they can still get it
                        e.getPlayer().sendMessage(ChatUtils.format(getPrefix() + "&eYou can still get your mole kit! Type &5/molekit &eto redeem it!"));
                    }
                }
            }.runTaskLater(UHC.get(), 2);
        }
    }


    private void setMoles(){
        Random random = ThreadLocalRandom.current();
        OfflinePlayer offlinePlayer;
        Player online;
        for (Team team : TeamManager.getInstance().getTeams()){
            if (team.getMembers().size() == 0) {
                continue;
            }
            offlinePlayer = Bukkit.getOfflinePlayer(team.getMembers().get(random.nextInt(team.getMembers().size())));
            if (offlinePlayer == null) continue;
            team.setMole(offlinePlayer.getUniqueId()); //Clean up later
            moles.put(team.getMole(), false);

            if(Bukkit.getPlayer(offlinePlayer.getUniqueId()) != null){
                online = Bukkit.getPlayer(offlinePlayer.getUniqueId());
                online.sendMessage(ChatUtils.format(getPrefix() + "&eYou are the mole! Shhh... it's a secret!"));
                online.sendMessage(ChatUtils.format(getPrefix() + "&eYou can now pick a Mole Kit by doing /molekit"));
                //new MoleKitGUI(online);
            }
        }
    }

}
