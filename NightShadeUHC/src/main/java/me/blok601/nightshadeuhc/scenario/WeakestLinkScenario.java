package me.blok601.nightshadeuhc.scenario;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class WeakestLinkScenario extends Scenario{


    private static final Ordering<Player> BY_HEALTH = new Ordering<Player>() { //Orders by health in reverse order
        @Override
        public int compare(Player p1, Player p2) {
            return Double.compare(p1.getHealth(), p2.getHealth());
        }
    };

    public WeakestLinkScenario() {
        super("WeakestLink", "Every 10 minutes, the person with the least health will be killed.", new ItemBuilder(Material.GOLDEN_CARROT).name("WeakestLink").make());
    }


    private static final Predicate<Player> IS_SPECTATOR = player -> {
        UHCPlayer gamePlayer = UHCPlayer.get(player);
        if (gamePlayer == null) return false;
        return gamePlayer.isSpectator();
    };
    private static final Predicate<Player> NOT_SPECTATOR = Predicates.not(IS_SPECTATOR);

    @EventHandler
    public void onStart(GameStartEvent e){


        if(!isEnabled()) return;
        new BukkitRunnable(){
            @Override
            public void run() {

                if(GameState.getState() == GameState.MEETUP){
                    Bukkit.broadcastMessage(ChatUtils.format(getPrefix()+ " &eWeakest Link has been disabled since meetup as started!"));
                    cancel();
                    return;
                }

                if(!isEnabled()){
                    cancel();
                    return;
                }

                Player toDamage = getLowestPlayer();

                if (toDamage == null) {
                    Bukkit.broadcastMessage(ChatUtils.format(getPrefix()+ " &eEveryone has survived!"));
                } else {
                    toDamage.damage(0);
                    toDamage.getLocation().getWorld().strikeLightningEffect(toDamage.getLocation());
                    toDamage.setHealth(0); //Killed with effects
                    Bukkit.broadcastMessage(ChatUtils.format(getPrefix()+ " &e" + toDamage.getName() + " was the weakest link!"));
                }
            }
        }.runTaskTimer(UHC.get(), 12000, 12000); //Every 60 seconds
    }

    private Player getLowestPlayer() {
        Player lowest = BY_HEALTH.min(Iterables.filter(Bukkit.getOnlinePlayers(), Predicates.not(IS_SPECTATOR))); //Why not use Google's API when Java's is so ugly

        if (isAllSameHealth() || lowest == null) {
            return null;
        }

        return lowest;

    }

    private boolean isAllSameHealth() {
        boolean isSame = true;
        Player lastPlayer = null;

        for (Player online : Iterables.filter(Bukkit.getOnlinePlayers(), NOT_SPECTATOR)) { //Same thing with iterables
            if (lastPlayer != null && lastPlayer.getHealth() != online.getHealth()) {
                isSame = false;
                break;
            }

            lastPlayer = online;
        }

        return isSame;
    }
}
