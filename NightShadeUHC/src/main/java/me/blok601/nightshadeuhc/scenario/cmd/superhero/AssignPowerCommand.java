package me.blok601.nightshadeuhc.scenario.cmd.superhero;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scenario.SuperheroesScenario;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Blok on 7/17/2018.
 */
public class AssignPowerCommand implements UHCCommand{

    private ScenarioManager scenarioManager;

    public AssignPowerCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "assignpower"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /assignpower <player>"));
            return;
        }

        if(!(scenarioManager.getScen("Superheroes").isEnabled() || scenarioManager.getScen("Superheroes Teams").isEnabled())){
            p.sendMessage(ChatUtils.message("&cSuperheroes isn't enabled!"));
            return;
        }

        if(!GameState.gameHasStarted()){
            p.sendMessage(ChatUtils.message("&cThe game hasn't started yet!"));
            return;
        }

        if (args[0].equalsIgnoreCase("*")) {

            for (Player target : Bukkit.getServer().getOnlinePlayers()) {
                if (SuperheroesScenario.powers.containsKey(target.getUniqueId())) {
                    return;
                }

                Random random = ThreadLocalRandom.current();
                SuperheroesScenario.SuperHeroType type = SuperheroesScenario.SuperHeroType.values()[random.nextInt(SuperheroesScenario.SuperHeroType.values().length)];
                SuperheroesScenario.powers.put(target.getUniqueId(), type);

                if (type == SuperheroesScenario.SuperHeroType.HEALTH) {
                    target.setMaxHealth(40);

                    target.setHealth(40);
                } else {
                    for (PotionEffect effect : type.getEffecst()) {
                        target.addPotionEffect(effect);
                    }
                }

                target.sendMessage(ChatUtils.message("&eYour super power is: &3" + type.getName()));
                p.sendMessage(ChatUtils.message("&eYou have assigned &a" + target.getName() + "'s &epower to &3" + type.getName()));
            }
        }


        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            p.sendMessage(ChatUtils.message("&cThat player is offline!"));
            return;
        }

        if(SuperheroesScenario.powers.containsKey(target.getUniqueId())) {
            p.sendMessage(ChatUtils.message("&cThat player has already been assigned a power!"));
            return;
        }

        Random random = ThreadLocalRandom.current();
        SuperheroesScenario.SuperHeroType type = SuperheroesScenario.SuperHeroType.values()[random.nextInt(SuperheroesScenario.SuperHeroType.values().length)];
        SuperheroesScenario.powers.put(target.getUniqueId(), type);

        if (type == SuperheroesScenario.SuperHeroType.HEALTH) {
            target.setMaxHealth(40);

            target.setHealth(40);
        } else {
            for (PotionEffect effect : type.getEffecst()) {
                target.addPotionEffect(effect);
            }
        }

        target.sendMessage(ChatUtils.message("&eYour super power is: &3" + type.getName()));
        p.sendMessage(ChatUtils.message("&eYou have assigned &a" + target.getName() + "'s &epower to &3" + type.getName()));
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
