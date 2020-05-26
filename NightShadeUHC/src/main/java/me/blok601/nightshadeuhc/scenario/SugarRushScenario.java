package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class SugarRushScenario extends Scenario {

    private HashSet<UUID> cooldown;
    private UHC plugin;

    public SugarRushScenario(UHC plugin) {
        super("Sugar Rush", "Right clicking sugar gives a Speed II boost for 5 seconds", new ItemBuilder(Material.SUGAR).name("Sugar Rush").make());
        cooldown = new HashSet<>();
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!isEnabled()) return;
        Player player = e.getPlayer();
        if (e.getItem().getType() != Material.SUGAR) return;

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (cooldown.contains(player.getUniqueId())) {
                sendMessage(player, "&cYou can't do this yet!");
                return;
            }

            ItemStack sugar = e.getItem();
            if (sugar.getAmount() == 1) {
                player.getInventory().remove(sugar);
            } else {
                sugar.setAmount(sugar.getAmount() - 1);
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1, false, true));
            sendMessage(player, "&eSpeed activated!");
            cooldown.add(player.getUniqueId());
            new BukkitRunnable() {
                @Override
                public void run() {
                    cooldown.remove(player.getUniqueId());
                }
            }.runTaskLater(plugin, 100);
        }
    }
}
