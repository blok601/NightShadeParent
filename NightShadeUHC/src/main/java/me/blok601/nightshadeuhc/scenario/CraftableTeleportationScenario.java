package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.util.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

/**
 * Created by Blok on 6/24/2018.
 */
public class CraftableTeleportationScenario extends Scenario{


    public CraftableTeleportationScenario() {
        super("Craftable Teleportation", "If you rename an ender pearl to a online player's name it will remove the ender pearl from your inventory and teleport you within a 50 blocks radius of that player", new ItemBuilder(Material.ENDER_PEARL).name("Craftable Teleportation").make());
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
            if(e.getItem().getType() != Material.ENDER_PEARL){
                return;
            }

            Player p = e.getPlayer();

            ItemStack itemStack = e.getItem();
            if(itemStack.hasItemMeta()){
                if(itemStack.getItemMeta().hasDisplayName()){
                    ItemMeta meta = itemStack.getItemMeta();
                    String name = ChatColor.stripColor(meta.getDisplayName());
                    Player tempPlayer = null;
                    for (UUID uuid : UHC.players){
                        tempPlayer = Bukkit.getPlayer(uuid);
                        if(tempPlayer == null) continue;
                        if(name.equalsIgnoreCase(tempPlayer.getName())){
                            break;
                        }

                        tempPlayer = null;

                    }

                    if(tempPlayer != null){ //found him
                        boolean goodY = false;
                        double x = MathUtils.getRand((int) (tempPlayer.getLocation().getX() + 25), (int) (tempPlayer.getLocation().getX() - 25));
                        double z = MathUtils.getRand((int) (tempPlayer.getLocation().getZ() + 25), (int) (tempPlayer.getLocation().getZ() - 25));
                        while(!goodY){
                            double y = MathUtils.getRand((int) (tempPlayer.getLocation().getY() + 25), (int) tempPlayer.getLocation().getY() - 25);
                            Block block = tempPlayer.getWorld().getBlockAt((int) x, (int)y, (int)z);
                            if(block != null && block.getType() != Material.WATER && block.getType() != Material.LAVA && block.getType() != Material.AIR){
                                if(block.getWorld().getBlockAt(block.getX(), block.getY()+1, block.getZ()).getType() == Material.AIR){
                                    p.teleport(block.getLocation());
                                    goodY = true;
                                    p.sendMessage(ChatUtils.format(getPrefix() + "&eYou have teleported within 25 blocks of" + tempPlayer.getName()));
                                }
                            }
                        }


                    }


                }
            }
        }
    }
}
