package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.event.MeetupStartEvent;
import me.blok601.nightshadeuhc.event.PvPEnableEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class FourHorsemenScenario extends Scenario {
    public FourHorsemenScenario() {
        super("Four Horsemen", "A secret, until April 19th", new ItemBuilder(Material.SKULL_ITEM).name(ChatUtils.format("Four Horsemen")).make());
    }

    int war = 0;

    @EventHandler
    public void onStart(GameStartEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isEnabled()) {
                    this.cancel();
                    return;
                }
                ChatUtils.sendAll("Pestilence Strikes...");

                UHCPlayerColl.get().getAllPlaying().stream().forEach(uhcPlayer -> {
                    Player player = (Player) uhcPlayer;
                    player.setHealth(player.getMaxHealth() - 1);
                });


            }
        }.runTaskTimer(UHC.get(), 0, 20 * 60);
    }
    @EventHandler
    public void onPvp(PvPEnableEvent e) {
        if (!isEnabled()) return;
        ChatUtils.sendAll("It has begun, for War has arrived.");
    }
    @EventHandler
    public void onDeath(CustomDeathEvent e) {
        if (!isEnabled()) return;
        if (e.getKiller() != null) {
            Player k = e.getKiller();
            k.sendMessage(ChatUtils.message("&6 War has taken its toll on you."));
            k.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 0));
            k.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 20, 0));

        }
    }
    @EventHandler
    public void onMeetup(MeetupStartEvent e) {
        if (!isEnabled()) return;
        ChatUtils.sendAll("May famine bring great sorrow unto you...");
        UHCPlayerColl.get().getAllPlaying().stream().forEach(uhcPlayer -> {

            Player p = (Player) uhcPlayer;
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, Integer.MAX_VALUE, 0));

        });

    }
    @EventHandler
    public void onEat (PlayerItemConsumeEvent e) {
        if (!isEnabled()) return;
        if (e.getItem().getType() == Material.GOLDEN_APPLE) {
            double rand = (Math.random() * 10) + 1;
            if (rand <= 2) {
                Player p = e.getPlayer();
                p.sendMessage(ChatUtils.message("&c May Death instill within you.."));
                e.setCancelled(true);
                ItemStack stack = p.getItemInHand();
                p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 5, 0));
                if (stack.getAmount() > 1) {
                    stack.setAmount(stack.getAmount() - 1);
                }
                else {
                    p.getInventory().clear(p.getInventory().getHeldItemSlot());
                }

            }
            else {
                e.getPlayer().sendMessage(ChatUtils.message("&eYou have been spared by death, this time..."));
            }
        }

    }

}