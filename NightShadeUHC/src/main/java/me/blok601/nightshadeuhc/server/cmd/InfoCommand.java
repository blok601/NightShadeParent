package me.blok601.nightshadeuhc.server.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/29/2017.
 */
public class InfoCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "lag"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        World world;
        int animals = 0;
        int players = 0;
        int monsters = 0;
        int other = 0;
        if(args.length == 0){
            world = p.getWorld();
            for (Entity entity  : world.getEntities()){
                if(entity instanceof Monster){
                    monsters++;
                }else if(entity instanceof Animals){
                    animals++;
                }else if(entity instanceof Player){
                    players++;
                }else{
                    other++;
                }
            }

            p.sendMessage(ChatUtils.format("&5+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+="));
            p.sendMessage(ChatUtils.format("&3Lag for world: " + world.getName()));
            p.sendMessage(ChatUtils.format("&eMonsters: " + monsters));
            p.sendMessage(ChatUtils.format("&eAnimals: " + animals));
            p.sendMessage(ChatUtils.format("&ePlayers: " + players));
            p.sendMessage(ChatUtils.format("&eOther: " + other));
            p.sendMessage(ChatUtils.format("&5+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+="));
            return;
        }else if(args.length == 1){
            world = Bukkit.getWorld(args[0]);
            if(world == null){
                p.sendMessage(ChatUtils.message("&cCouldn't find that world!"));
                return;
            }
            for (Entity entity  : world.getEntities()){
                if(entity instanceof Monster){
                    monsters++;
                }else if(entity instanceof Animals){
                    animals++;
                }else if(entity instanceof Player){
                    players++;
                }else{
                    other++;
                }
            }


            p.sendMessage(ChatUtils.format("&5+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+="));
            p.sendMessage(ChatUtils.format("&3Lag for world: " + world.getName()));
            p.sendMessage(ChatUtils.format("&eMonsters: " + monsters));
            p.sendMessage(ChatUtils.format("&eAnimals: " + animals));
            p.sendMessage(ChatUtils.format("&ePlayers: " + players));
            p.sendMessage(ChatUtils.format("&eOther: " + other));
            p.sendMessage(ChatUtils.format("&5+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+="));
            return;


        }else{
            ChatUtils.message("&cUse the command like this! /info [world]");
            return;
        }
    }

    @Override
    public boolean playerOnly() {
        return false;
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
