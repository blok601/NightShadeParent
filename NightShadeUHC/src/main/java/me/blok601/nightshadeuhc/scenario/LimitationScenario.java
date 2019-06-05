package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class LimitationScenario extends Scenario{

    public LimitationScenario() {
        super("Limitations", "You can only mine 16 Diamonds, 32 Gold, and 64 Iron the entire game.", new ItemBuilder(Material.COBBLESTONE).name("Limitations").make());
    }
    HashMap<Player, Integer> diamonds = new HashMap<>();
    HashMap<Player, Integer> gold = new HashMap<>();

    HashMap<Player, Integer> iron = new HashMap<>();


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
        Location loc = e.getBlock().getLocation();

        if (b.getType() == Material.DIAMOND) {
            if (diamonds.containsKey(e.getPlayer())) {
                if (diamonds.get(e.getPlayer()) >= 16) {
                    p.sendMessage(ChatUtils.message("&cYou cannot mine any more diamonds!"));
                    e.setCancelled(true);
                    e.getBlock().setType(Material.AIR);
                    return;
                }
                diamonds.put(p, diamonds.get(p) + 1);
                return;

            }
            else {
                diamonds.put(p, 1);
                return;
            }
        }
        if (b.getType() == Material.GOLD_ORE) {
            if (gold.containsKey(e.getPlayer())) {
                if (gold.get(e.getPlayer()) >= 32) {
                    p.sendMessage(ChatUtils.message("&cYou cannot mine any more gold!"));
                    e.setCancelled(true);
                    e.getBlock().setType(Material.AIR);
                    return;
                }
                gold.put(p, gold.get(p) + 1);
                return;

            }
            else {
                gold.put(p, 1);
                return;
            }
        }
        if (b.getType() == Material.IRON_ORE) {
            if (iron.containsKey(e.getPlayer())) {
                if (iron.get(e.getPlayer()) >= 64) {
                    p.sendMessage(ChatUtils.message("&cYou cannot mine any more iron!"));
                    e.setCancelled(true);
                    e.getBlock().setType(Material.AIR);
                    return;
                }
                iron.put(p, iron.get(p) + 1);
                return;
            }
            iron.put(p, 1);
            return;
        }



    }


}
