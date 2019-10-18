package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.UUID;

public class LimitationScenario extends Scenario{
    HashMap<UUID, Integer> diamonds = new HashMap<>();
    HashMap<UUID, Integer> gold = new HashMap<>();

    HashMap<UUID, Integer> iron = new HashMap<>();

    public LimitationScenario() {
        super("Limitations", "You can only mine 16 Diamonds, 32 Gold, and 64 Iron the entire game.", new ItemBuilder(Material.COBBLESTONE).name("Limitations").make());
    }



    @EventHandler
    public void onStart(GameStartEvent e) {
        if (!isEnabled()) return;

        diamonds.clear();
        gold.clear();
        iron.clear();

    }



    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (!isEnabled()) return;

        Block b = e.getBlock();
        Player p = e.getPlayer();

        if (b.getType() == Material.DIAMOND_ORE) {

            if (diamonds.containsKey(e.getPlayer().getUniqueId())) {
                if (diamonds.get(e.getPlayer().getUniqueId()) >= 16) {
                    p.sendMessage(ChatUtils.message("&cYou cannot mine any more diamonds!"));
                    e.setCancelled(true);
                    e.getBlock().setType(Material.AIR);
                    return;
                }
                diamonds.put(p.getUniqueId(), diamonds.get(p.getUniqueId()) + 1);
                return;

            }
            else {
                diamonds.put(p.getUniqueId(), 1);
                return;
            }
        }
        if (b.getType() == Material.GOLD_ORE) {
            if (gold.containsKey(e.getPlayer().getUniqueId())) {
                if (gold.get(e.getPlayer().getUniqueId()) >= 32) {
                    p.sendMessage(ChatUtils.message("&cYou cannot mine any more gold!"));
                    e.setCancelled(true);
                    e.getBlock().setType(Material.AIR);
                    return;
                }
                gold.put(p.getUniqueId(), gold.get(p.getUniqueId()) + 1);
                return;

            }
            else {
                gold.put(p.getUniqueId(), 1);
                return;
            }
        }
        if (b.getType() == Material.IRON_ORE) {
            if (iron.containsKey(e.getPlayer().getUniqueId())) {
                if (iron.get(e.getPlayer().getUniqueId()) >= 64) {
                    p.sendMessage(ChatUtils.message("&cYou cannot mine any more iron!"));
                    e.setCancelled(true);
                    e.getBlock().setType(Material.AIR);
                    return;
                }
                iron.put(p.getUniqueId(), iron.get(p.getUniqueId()) + 1);
                return;
            }
            iron.put(p.getUniqueId(), 1);
            return;
        }



    }


}
