package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class SwitcherooScenario extends Scenario{

    public SwitcherooScenario() {
        super("Switcheroo", "Shooting someone with an arrow causes you to switch places with them", new ItemBuilder(Material.ARROW).name("Switcherro").make());
    }

    @EventHandler
    public void  onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(!isEnabled()){
            return;
        }

        if (e.getEntity() instanceof Player) {
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();

                if(e.getFinalDamage() >= ((Player) e.getEntity()).getHealth()){
                    return;
                }

                if (arrow.getShooter() instanceof Player) {
                    Player shooter = (Player) arrow.getShooter();
                    Player damaged = (Player) e.getEntity();
                    Location sLocation = shooter.getLocation();
                    Location dLocation = damaged.getLocation();
                    shooter.teleport(dLocation);
                    damaged.teleport(sLocation);
                    shooter.sendMessage(ChatUtils.format(getPrefix()+ " &aYou got &eswitcheroo'd &awith &e" + damaged.getName()));
                    damaged.sendMessage(ChatUtils.format(getPrefix()+ " &aYou got &eswitcheroo'd &awith &e" + shooter.getName()));
                }
            }
        }
    }
}
