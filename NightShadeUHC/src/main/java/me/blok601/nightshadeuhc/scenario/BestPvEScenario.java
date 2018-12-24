package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by Blok on 6/23/2018.
 */
public class BestPvEScenario extends Scenario{

    ArrayList<Player> players;

    public BestPvEScenario() {
        super("BestPvE", "At the start of the game, players are added to a list, called the \"Best PvE List\". If you are on that list, you will gain 1 extra heart every 10 minutes and you get 1 heart of regeneration.Once a player takes damage, the player is removed from the \"Best PvE List\". If you kill a player, you are added back to the list.", new ItemBuilder(Material.CARROT_ITEM).name("BestPvE").make());
        players = new ArrayList<>();
    }

    @EventHandler
    public void onGameStart(GameStartEvent e){

        if(!isEnabled()) return;

//        UHC.players.stream().filter(uuid -> Bukkit.getPlayer(uuid) != null).forEach(uuid -> players.add(Bukkit.getPlayer(uuid)));
        UHCPlayerColl.get().getAllPlaying().forEach(uhcPlayer -> players.add(uhcPlayer.getPlayer()));
        Bukkit.broadcastMessage(ChatUtils.format(getPrefix() + "&eBestPvE has begun!"));
        new BukkitRunnable(){
            @Override
            public void run() {
                for (Player player : players){
                    if(player == null) continue;
                    player.setMaxHealth(player.getMaxHealth() + 2.0); //Add 1 max heart
                    player.setHealth(player.getHealth() + 2.0); // Heal 1 heart
                    player.sendMessage(ChatUtils.format(getPrefix() + "&eYou gained a heart for surviving BestPvE."));
                }
            }
        }.runTaskTimer(UHC.get(),12000, 12000);
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){

        if(!isEnabled()) return;

        if(players.contains(e.getKilled())){
            e.getKilled().setMaxHealth(20.0);
            players.remove(e.getKilled());
        }

        if(e.getKiller() != null && !players.contains(e.getKiller())){
            players.add(e.getKiller());
            e.getKiller().sendMessage(ChatUtils.format(getPrefix() + "&eYou have been added to the BestPvE list!"));
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){

        if(!isEnabled()) return;

        if(!(e.getEntity() instanceof Player)){
            return;
        }

        Player p = (Player) e.getEntity();

        if(e.getFinalDamage() >= 1.0){ // If damaged 0.5 hearts or more
            if(players.contains(p)){
                players.remove(p);
                p.sendMessage(ChatUtils.format(getPrefix() + "&cYou were removed from the BestPvE list for taking damage!"));
            }
        }
    }
}
