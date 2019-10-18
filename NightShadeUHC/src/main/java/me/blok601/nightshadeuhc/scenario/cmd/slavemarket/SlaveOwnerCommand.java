package me.blok601.nightshadeuhc.scenario.cmd.slavemarket;

import com.nightshadepvp.core.Rank;
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

public class SlaveOwnerCommand implements UHCCommand{

    private SlaveMarketScenario slaveMarketScenario;

    public SlaveOwnerCommand(SlaveMarketScenario slaveMarketScenario) {
        this.slaveMarketScenario = slaveMarketScenario;
    }


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
            if (args[0].equalsIgnoreCase("reset")) {
                slaveMarketScenario.owners.clear();
                ChatUtils.sendAllScenarioMessage("&eSlave owners have been reset!", slaveMarketScenario);
                return;
            }
            p.sendMessage(ChatUtils.message("&cInvalid arguments! Usage: /owners <add/remove/reset> <player>"));
            return;
        }
        if (!(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("reset"))) {
            p.sendMessage(ChatUtils.message("&cInvalid aguments! Usage: /owners <add/remove/reset> <player>"));
            return;

        } else {


            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                p.sendMessage(ChatUtils.message("&cThat player is offline!"));
                return;
            }
            if (!GameManager.get().isIsTeam()) {
                p.sendMessage(ChatUtils.message("&cSlave owners can only be set in a teams game!"));
                return;
            }
            if (args[0].equalsIgnoreCase("add")){
                if (slaveMarketScenario.owners.containsKey(target.getUniqueId())) {
                    p.sendMessage(ChatUtils.message("&cThat player is already an owner!"));
                }
                slaveMarketScenario.owners.put(target.getUniqueId(), 64);
                int ts = TeamManager.getInstance().getTeams().size()+1;
                TeamManager.getInstance().addTeam(new Team("UHC" + ts, p));
                ChatUtils.sendAllScenarioMessage("&b" + target.getName() + " has been added as a slave owner!", slaveMarketScenario);
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
