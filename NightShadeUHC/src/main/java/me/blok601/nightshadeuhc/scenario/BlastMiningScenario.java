package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.util.MathUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlastMiningScenario extends Scenario{

    public BlastMiningScenario() {
        super("Blast Mining", "When a player mines any ore (including nether quartz), there is a 5% chance that a creeper will spawn (creepers are given slowness 2 for 2 seconds, to give the player some time to react) and there's a 3% chance an ignited TNT will spawn. This will never happen simultaneously, meaning you'll only ever have to deal with one thing at a time.", new ItemBuilder(Material.TNT).name("Blast Mining").make());
    }


    @EventHandler
    public void onMine(BlockBreakEvent event){
        if(!isEnabled()) return;
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if(block.getType().name().toUpperCase().contains("ORE")){
            if(MathUtils.getChance(5)){
                Creeper creeper = (Creeper) block.getWorld().spawnEntity(block.getLocation(), EntityType.CREEPER);
                creeper.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, true, true));
                sendMessage(player, "&eA creeper has spawned!");
                return;
            }

            if(MathUtils.getChance(3)){
                TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);
                tnt.setFuseTicks(100);
                sendMessage(player, "&eTnT has spawned! It will explode in 5 seconds!");
                return;
            }
        }
    }

}
