package me.blok601.nightshadeuhc.scenario;

import com.google.common.collect.Lists;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Blok on 7/17/2018.
 */
public class SuperheroesScenario extends Scenario {

    public static HashMap<UUID, SuperHeroType> powers;

    public SuperheroesScenario() {
        super("Superheroes", "Each player will gain a special ability. The powers are speed 1, strength 1, resistance 2, invisibility, 6 extra hearts, and jump boost 4.", new ItemBuilder(Material.BREWING_STAND_ITEM).name("Superheroes").make());
        powers = new HashMap<>();
    }

    @EventHandler
    public void onGameStart(GameStartEvent e){
        if(!isEnabled()) return;
        Random random = ThreadLocalRandom.current();
        UHCPlayerColl.get().getAllOnline().stream().filter(uhcPlayer -> !uhcPlayer.isSpectator()).forEach(uhcPlayer ->{
            SuperHeroType type = SuperHeroType.values()[random.nextInt(SuperHeroType.values().length)];
            powers.put(uhcPlayer.getUuid(), type);
            if(powers.containsKey(uhcPlayer.getUuid())) {
                return;
            }

            Player p = uhcPlayer.getPlayer();
            if (type == SuperHeroType.HEALTH) {
                p.setMaxHealth(40);
                p.setHealth(40);
            } else {
                for (PotionEffect effect : type.getEffecst()) {
                    p.addPotionEffect(effect);
                }
            }

            uhcPlayer.msg(ChatUtils.format(getPrefix() + "&eYour super power is: &3" + type.getName()));
            return;
        });

    }

    public enum SuperHeroType{
        SPEED("Speed 1, Haste 2", Lists.newArrayList(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0), new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1))),
        STRENGTH("Strength 1", Lists.newArrayList(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0))),
        RES("Resistance 2, Fire Resistance 1", Lists.newArrayList(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1), new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0))),
        INVIS("Invisibility, Water Breathing", Lists.newArrayList(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0), new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0))),
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
