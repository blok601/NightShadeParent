package me.blok601.nightshadeuhc.command.game.setup;

import com.nightshadepvp.core.Rank;
import com.wimbli.WorldBorder.Config;
import com.wimbli.WorldBorder.WorldBorder;
import com.wimbli.WorldBorder.WorldFillTask;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.MathUtils;
import me.blok601.nightshadeuhc.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Blok on 3/23/2018.
 */
public class CreateWorldCommand implements UHCCommand{

    @Override
    public String[] getNames() {
        return new String[]{
                "createworlds"
        };
    }

    public static HashSet<UUID> pendingGen = new HashSet<>();

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length != 3){
            p.sendMessage(ChatUtils.message("Usage: /createworlds <seed> <nether true/false>, <int radius>"));
            return;
        }

        if (!Util.isBoolean(args[1])) {
            p.sendMessage(ChatUtils.message("Usage: /createworlds <seed> <nether true/false>"));
            return;
        }

        if(!MathUtils.isInt(args[2])){
            p.sendMessage(ChatUtils.message("&cPlease supply a number!"));
            return;
        }

        int radius = Integer.valueOf(args[2]);
        boolean nether = Boolean.parseBoolean(args[1]);
        Random random = ThreadLocalRandom.current();
        String worldName = p.getName() + "UHC";

        p.sendMessage(ChatUtils.message("&eCreating the world for your UHC..."));
        UHC.getMultiverseCore().getMVWorldManager().addWorld(worldName, World.Environment.NORMAL, args[0], WorldType.NORMAL, true, null);
        p.sendMessage(ChatUtils.message("&aThe world has been created!"));
        UHC.get().getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + worldName + " set " + radius + " " + radius + " 0 0");
        UHC.get().getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb shape square");
        World overWorld = Bukkit.getWorld(worldName);
        if (overWorld == null) {
            p.sendMessage(ChatUtils.message("&4There was a problem creating the world! Please report this to Blok!"));
            return;
        }

        overWorld.setSpawnLocation(0, 75, 0);
        p.teleport(overWorld.getSpawnLocation());
        p.sendMessage(ChatUtils.message("&eYou have been teleported to your world!"));

        if(nether){
            p.sendMessage("&eCreating the nether world for your UHC!");
            UHC.getMultiverseCore().getMVWorldManager().addWorld(worldName + "NETHER", World.Environment.NETHER, String.valueOf(random.nextInt(100)), WorldType.NORMAL, true, null);
        }
        p.sendMessage(ChatUtils.message("&aThe nether world has been created, starting pregen for the first world in 10 seconds...do /cancelpregen to cancel the start of the pregeneration."));
        pendingGen.add(p.getUniqueId());

        new BukkitRunnable(){
            int counter = 10;
            int ticks = 20/250;
            int repeats = 250/20;
            @Override
            public void run() {
                if(counter == 0){
                    cancel();

                    Config.fillTask = new WorldFillTask(Bukkit.getServer(), p, worldName, 208, repeats, ticks, false);
                    if(Config.fillTask.valid()){
                        int task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(WorldBorder.plugin, Config.fillTask, ticks, ticks);
                        Config.fillTask.setTaskID(task);
                        p.sendMessage(ChatUtils.message("&eStarted the pregenation of world: " + worldName));
                    }
                    return;
                }

                if(!pendingGen.contains(p.getUniqueId())){
                    cancel();
                    return;
                }

                counter--;
            }
        }.runTaskTimer(UHC.get(), 0, 20);


        p.sendMessage(ChatUtils.message("&eA preliminary WorldBorder has been set to 100. Make sure to pregen to your desired radius!"));
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
