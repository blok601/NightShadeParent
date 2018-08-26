package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 2/10/2018.
 */
public class RatesCommand implements CmdInterface, Listener{
    @Override
    public String[] getNames() {
        return new String[]{
                "rates"
        };
    }



    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length ==0){
           p.sendMessage(ChatUtils.message("&eApple and Flint Rates&8:"));
           p.sendMessage(ChatUtils.format("&3Apple Rates&8: &5" + GameManager.getAppleRates()));
            p.sendMessage(ChatUtils.format("&3Flint Rates&8: &5" + GameManager.getFlintRates()));
        }

        if(args.length != 0 && args.length != 2){
            p.sendMessage(ChatUtils.message("&cUsage: /rates <apple/flint> <rate>"));
            return;
        }

        if(args[0].equalsIgnoreCase("apple") || args[0].equalsIgnoreCase("apples")){
            String arg = args[1];
            int rate;
            if(Util.isInt(arg)){
                rate  = Integer.parseInt(arg);
            }else{
                p.sendMessage(ChatUtils.message("&cPlease supply a number!"));
                return;
            }

            GameManager.setAppleRates(rate);
            p.sendMessage(ChatUtils.message("&eUpdated apple rates to &6" + rate + "&e."));
        }else if(args[0].equalsIgnoreCase("flint")){
            String arg = args[1];
            int rate;
            if(Util.isInt(arg)){
                rate  = Integer.parseInt(arg);
            }else{
                p.sendMessage(ChatUtils.message("&cPlease supply a number!"));
                return;
            }

            GameManager.setFlintRates(rate);
            p.sendMessage(ChatUtils.message("&eUpdated flint rates to &6" + rate + "&e."));
        }else{
            p.sendMessage(ChatUtils.message("&cUsage: /rates <apple/flint> <number>"));
            return;
        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(e.getBlock().getType() == Material.GRAVEL){

            if(ScenarioManager.getScen("CutClean").isEnabled()){
                e.getBlock().setType(Material.AIR);
                e.getBlock().getDrops().clear();
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.FLINT));
                return;
            }

            if(Util.getChance(GameManager.getFlintRates())){
                e.getBlock().setType(Material.AIR);
                e.getBlock().getDrops().clear();
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.FLINT));
            }



        }
    }

    @EventHandler
    public void decay(LeavesDecayEvent e){
        if(e.getBlock().getType() == Material.LEAVES || e.getBlock().getType() == Material.LEAVES_2){

            if(Util.getChance(GameManager.getAppleRates())){
                e.getBlock().setType(Material.AIR);
                e.getBlock().getDrops().clear();
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.APPLE));
            }
        }
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIAL;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
