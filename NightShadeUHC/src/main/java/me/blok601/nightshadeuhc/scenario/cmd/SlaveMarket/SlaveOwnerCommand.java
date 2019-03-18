package me.blok601.nightshadeuhc.scenario.cmd.SlaveMarket;

import com.nightshadepvp.core.Rank;
import lombok.Getter;
import lombok.Setter;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scenario.SlaveMarketScenario;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
@Getter
@Setter

public class SlaveOwnerCommand implements UHCCommand{

    @Getter
    @Setter
     private static Player topBidder = null;
    @Getter
    @Setter
        private  static Player Slave = null;

        @Override
        public String[] getNames() {
            return new String[]{
                    "owners"
            };
        }




    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
            Player p = (Player) s;
            UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if(!uhcPlayer.isSpectator()){
            p.sendMessage(ChatUtils.message("&cOnly the host can do that command!"));
            return;
        }

        if(!GameManager.get().getHost().getName().equalsIgnoreCase(p.getName())){
            p.sendMessage(ChatUtils.message("&cOnly the host can do that command!"));
            return;
        }

        if(args.length == 1){
            p.sendMessage(ChatUtils.message("&c Invalid arguments! Usage: /owners <add/remove/reset> <player>"));
            return;
        }
        if (!(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("reset"))) {
            p.sendMessage(ChatUtils.message("&cInvalid aguments! Usage: /owners <add/remove/reset> <player>"));
            return;

        }
        if (args[0].equalsIgnoreCase("reset")) {
            SlaveMarketScenario.owners.clear();
            p.sendMessage(ChatUtils.message("&e Slave owners have been reset!"));
            return;
        }
        else {


            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                p.sendMessage(ChatUtils.message("&cThat player is offline, dog."));
                return;
            }
            if (!GameManager.get().isIsTeam()) {
                ChatUtils.sendAll("&cSlave owners can only be set in a teams game!");
                return;
            }
            if (args[0].equalsIgnoreCase("add")){
                SlaveMarketScenario.owners.put(target.getUniqueId(), 64);
                int ts = TeamManager.getInstance().getTeams().size()+1;
                TeamManager.getInstance().addTeam(new Team("UHC" + ts, p));
                p.sendMessage(ChatUtils.message(target.getName() + " has been added as a slave owner!"));



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
        return false;
    }
}
