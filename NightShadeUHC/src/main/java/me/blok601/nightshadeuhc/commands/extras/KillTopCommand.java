package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Blok on 12/25/2017.
 */
public class KillTopCommand implements CmdInterface{

    @Override
    public String[] getNames() {
        return new String[]{
                "killtop"
        };
    }


    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(GameState.getState() == GameState.WAITING || GameState.getState() == GameState.STARTING){
            p.sendMessage(ChatUtils.message("&cThe game hasn't started!"));
            return;
        }

        if(!GameManager.getWorld().getPVP()){
            p.sendMessage(ChatUtils.message("&cPvP has not yet been enabled!"));
            return;
        }

//        Map<String, Integer> k = GamePlayerHandler.get().getGamePlayers().stream()
//                .filter(gamePlayer -> gamePlayer.getGameKills() > 0)
//                .collect(Collectors.toMap(gamePlayer -> gamePlayer.getName(), gamePlayer -> gamePlayer.getGameKills()));

        HashMap<String, Integer> k1 = new HashMap<>();

        UHCPlayer gamePlayer;
        for (Map.Entry<UUID, Integer> entry : GameManager.getKills().entrySet()){
            gamePlayer = UHCPlayer.get(entry.getKey());
            if(Bukkit.getPlayer(entry.getKey()) == null){
                if(GameManager.getDeathBans().contains(entry.getKey())){
                    k1.put("&c" + gamePlayer.getName(), entry.getValue());
                }else{
                    k1.put("&7" + gamePlayer.getName(), entry.getValue());
                }
            }else{
                k1.put("&e" + gamePlayer.getName(), entry.getValue());
            }
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<>(k1.entrySet());

        if(list.size() == 0){
            p.sendMessage(ChatUtils.message("&cThere is nobody with any kills yet!"));
            return;
        }


        p.sendMessage(ChatUtils.message("&3KillTop:"));
        p.sendMessage(ChatUtils.format("&5&m-----------------------------------"));

        list.stream()
        .sorted(((o1, o2) -> o2.getValue().compareTo(o1.getValue()))).limit(10)
                .forEach(entry -> p.sendMessage(ChatUtils.format( entry.getKey() + " &e- " + entry.getValue())));

        p.sendMessage(ChatUtils.format("&5&m-----------------------------------"));

    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return null;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }
}
