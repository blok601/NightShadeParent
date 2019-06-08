package me.blok601.nightshadeuhc.scenario.cmd.superhero;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scenario.SuperheroesScenario;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/17/2018.
 */
public class PowerCommand implements UHCCommand{

    private ScenarioManager scenarioManager;

    public PowerCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "power"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (!scenarioManager.getScen("Superheroes").isEnabled()) {
            p.sendMessage(ChatUtils.message("&cSuperheroes isn't enabled!"));
            return;
        }

        if (!GameState.gameHasStarted()) {
            p.sendMessage(ChatUtils.message("&cThe game hasn't started yet!"));
            return;
        }

        if (args.length == 0) {

            if (!SuperheroesScenario.powers.containsKey(p.getUniqueId())) {
                p.sendMessage(ChatUtils.message("&cYou haven't been assigned a power! Ask the host to assign you one!"));
                return;
            }

            SuperheroesScenario.SuperHeroType type = SuperheroesScenario.powers.get(p.getUniqueId());
            p.sendMessage(ChatUtils.message("&eYour super power is: &3" + type.getName()));
        }
        if (args.length == 1) {
            UHCPlayer se = (UHCPlayer) p;
            if (se.isSpectator()) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(ChatUtils.message("&cThat player is not online!"));
                    return;
                }
                if (!SuperheroesScenario.powers.containsKey(target.getUniqueId())) {
                    p.sendMessage(ChatUtils.message("&cThat player has not be assigned a power!"));
                    return;
                }
                SuperheroesScenario.SuperHeroType type = SuperheroesScenario.powers.get(target.getUniqueId());
                p.sendMessage(ChatUtils.message("&e" + target.getName() + "'s Super power is: &3" + type.getName()));
                return;

            }
            else {
                p.sendMessage(ChatUtils.message("&cOnly Spectators can do this command!"));
                return;
            }


        }
    }

    @Override
    public boolean playerOnly() {
        return false;
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
