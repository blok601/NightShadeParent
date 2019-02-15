package me.blok601.nightshadeuhc.scenario;

import com.google.common.collect.Lists;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SuperHeroesTeamScenario extends Scenario{
    private GameManager gameManager;
    public static HashMap<UUID, SuperheroesScenario.SuperHeroType> powers;



    public SuperHeroesTeamScenario(GameManager gameManager) {
        super("Superheroes Teams", "Each player will gain a special ability. The powers are speed 1, strength 1, resistance 2, invisibility, 10 extra hearts, and jump boost 4.", new ItemBuilder(Material.BREWING_STAND_ITEM).name("Superheroes").make());
        this.gameManager = gameManager;
        powers = new HashMap<>();
    }


    @EventHandler
    public void onGameStart(GameStartEvent e){
        if(!isEnabled()) return;
        Random random = ThreadLocalRandom.current();
        Player tempPlayer;
        for (Team team : TeamManager.getInstance().getTeams()) {
            Collections.shuffle(team.getMembers());
            int times = team.getMembers().size() - 1;
            for (int i = 0; i >= times; i++){
                tempPlayer = Bukkit.getPlayer(team.getMembers().get(i));

                if(powers.containsKey(tempPlayer.getUniqueId())) {
                    return;
                }

                SuperheroesScenario.SuperHeroType type = SuperheroesScenario.SuperHeroType.values()[i];

                powers.put(tempPlayer.getUniqueId(), type);

                if (type == SuperheroesScenario.SuperHeroType.HEALTH) {
                    tempPlayer.setMaxHealth(40);
                    tempPlayer.setHealth(40);
                } else {
                    for (PotionEffect effect : type.getEffecst()) {
                        tempPlayer.addPotionEffect(effect);
                    }
                }
            }

        }
    }

    public enum SuperHeroType{
        SPEED("Speed 1, Haste 2", Lists.newArrayList(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0), new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1))),
        STRENGTH("Strength 1", Lists.newArrayList(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0))),
        RES("Resistance 2, Fire Resistance 1", Lists.newArrayList(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1), new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0))),
        HEALTH("10 Extra Hearts", Collections.emptyList()),
        JUMP("Jump Boost 4, Haste 2, Saturation", Lists.newArrayList(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 3), new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1), new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 9)));

        private String name;
        private List<PotionEffect> effect;

        SuperHeroType(String name, List<PotionEffect> effect) {
            this.name = ChatUtils.format(name);
            this.effect = effect;
        }

        public String getName() {
            return name;
        }

        public List<PotionEffect> getEffecst() {
            return effect;
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){

        if(!isEnabled()){
            return;
        }

        if(e.getEntity() instanceof Player){
            if(e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
            }
        }
    }

}


