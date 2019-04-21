package me.blok601.nightshadeuhc.scenario.cmd.superhero;

import com.google.common.collect.Sets;
import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.scenario.Scenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scenario.SuperheroesScenario;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Blok on 2/12/2019.
 */
public class ClearPowerCommand implements UHCCommand {

    private ScenarioManager scenarioManager;

    public ClearPowerCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "clearpower"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        Scenario scenario = scenarioManager.getScen("Superheroes");

        if (!scenario.isEnabled()) {
            p.sendMessage(ChatUtils.message("&cSuperheroes isn't enabled!"));
            return;
        }

        if (!GameState.gameHasStarted()) {
            p.sendMessage(ChatUtils.message("&cThe game hasn't started yet!"));
            return;
        }

        if (args.length == 0) {
            //Self
            if (!SuperheroesScenario.powers.containsKey(p.getUniqueId())) {
                p.sendMessage(ChatUtils.message("&cYou haven't been assigned a power! Ask the host to assign you one!"));
                return;
            }

            p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            SuperheroesScenario.powers.remove(p.getUniqueId());
            p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eYour powers have been cleared"));
            return;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("*") || args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("-a")) {
                //All
                HashSet<UUID> removed = Sets.newHashSet();
                for (UUID key : SuperheroesScenario.powers.keySet()) {
                    Player target = Bukkit.getPlayer(key);
                    if (target == null) continue;

                    target.getActivePotionEffects().forEach(potionEffect -> target.removePotionEffect(potionEffect.getType()));
                    removed.add(target.getUniqueId());
                }

                SuperheroesScenario.powers.keySet().removeAll(removed);
                p.sendMessage(ChatUtils.format(scenario.getPrefix() + "&eAll online player's superhero type has been cleared!"));
                return;
            }

            String name = args[0];
            Player target = Bukkit.getPlayer(name);
            if (target == null) {
                p.sendMessage(ChatUtils.message("&cThat player is not online!"));
                return;
            }

            target.getActivePotionEffects().forEach(potionEffect -> target.removePotionEffect(potionEffect.getType()));
            SuperheroesScenario.powers.remove(target.getUniqueId());
            p.sendMessage(scenario.getPrefix() + "&eRemoved &b" + target.getName() + "'s &esuper power!");
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
