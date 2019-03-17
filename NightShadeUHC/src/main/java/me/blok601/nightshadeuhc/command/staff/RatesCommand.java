package me.blok601.nightshadeuhc.command.staff;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.MathUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
public class RatesCommand implements UHCCommand, Listener{

    private GameManager gameManager;
    private ScenarioManager scenarioManager;

    public RatesCommand(GameManager gameManager, ScenarioManager scenarioManager) {
        this.gameManager = gameManager;
        this.scenarioManager = scenarioManager;
    }

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
           p.sendMessage(ChatUtils.format("&3Apple Rates&8: &5" + gameManager.getAppleRates()));
            p.sendMessage(ChatUtils.format("&3Flint Rates&8: &5" + gameManager.getFlintRates()));
        }

        if(args.length != 0 && args.length != 2){
            p.sendMessage(ChatUtils.message("&cUsage: /rates <apple/flint> <rate>"));
            return;
        }

        if(args[0].equalsIgnoreCase("apple") || args[0].equalsIgnoreCase("apples")){
            String arg = args[1];
            int rate;
            if(MathUtil.isInt(arg)){
                rate  = Integer.parseInt(arg);
            }else{
                p.sendMessage(ChatUtils.message("&cPlease supply a number!"));
                return;
            }

            gameManager.setAppleRates(rate);
            p.sendMessage(ChatUtils.message("&eUpdated apple rates to &6" + rate + "&e."));
        }else if(args[0].equalsIgnoreCase("flint")){
            String arg = args[1];
            int rate;
            if(MathUtil.isInt(arg)){
                rate  = Integer.parseInt(arg);
            }else{
                p.sendMessage(ChatUtils.message("&cPlease supply a number!"));
                return;
            }

            gameManager.setFlintRates(rate);
            p.sendMessage(ChatUtils.message("&eUpdated flint rates to &6" + rate + "&e."));
        }else{
            p.sendMessage(ChatUtils.message("&cUsage: /rates <apple/flint> <number>"));
            return;
        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){

        Block block = e.getBlock();

        if(e.getBlock().getType() == Material.GRAVEL){

            if(scenarioManager.getScen("CutClean").isEnabled()){
                e.getBlock().setType(Material.AIR);
                e.getBlock().getDrops().clear();
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.FLINT));
                return;
            }

            if(MathUtil.getChance(gameManager.getFlintRates())){
                e.getBlock().setType(Material.AIR);
                e.getBlock().getDrops().clear();
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.FLINT));
            }



        }
    }

    @EventHandler
    public void decay(LeavesDecayEvent e){
        if(e.getBlock().getType() == Material.LEAVES || e.getBlock().getType() == Material.LEAVES_2){

            if(MathUtil.getChance(gameManager.getAppleRates())){
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
