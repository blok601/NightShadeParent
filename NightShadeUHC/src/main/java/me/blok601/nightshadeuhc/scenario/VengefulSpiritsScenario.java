package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

/**
 * Created by Blok on 9/30/2018.
 */
public class VengefulSpiritsScenario extends Scenario {

    private HashMap<Integer, ItemStack> heads;

    public VengefulSpiritsScenario() {
        super("Vengeful Spirits", "When a player dies above Y:64 a ghast will appear. You must kill that ghast in order to receive the player's head. If they die under Y:64 a blaze will spawn and you must kill it to get the player head.", "VS", new ItemBuilder(Material.GHAST_TEAR).name("Vengeful Spirits").make());
        heads = new HashMap<>();
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e) {
        if (!isEnabled()) return;

        if (GameState.gameHasStarted()) return;

        Player p = e.getKilled();
        Location location = e.getLocation();

        if (location.getY() >= 64) {

            for (ItemStack itemStack : e.getItems()) {
                if (itemStack.getType() == Material.SKULL_ITEM) {
                    e.getItems().remove(itemStack);
                    break;
                }
            }

            Ghast entity = (Ghast) location.getWorld().spawnEntity(location, EntityType.GHAST);
            entity.damage(0);

            ItemStack itemStack = new ItemStack(Material.SKULL_ITEM);
            SkullMeta skullMeta = (SkullMeta) itemStack;
            skullMeta.setOwner(p.getName());
            itemStack.setItemMeta(skullMeta);
            this.heads.put(entity.getEntityId(), itemStack);
        }

        if (location.getY() <= 64) {
            Blaze blaze = (Blaze) location.getWorld().spawnEntity(location, EntityType.BLAZE);
            blaze.damage(0);

            ItemStack itemStack = new ItemStack(Material.SKULL_ITEM);
            SkullMeta skullMeta = (SkullMeta) itemStack;
            skullMeta.setOwner(p.getName());
            itemStack.setItemMeta(skullMeta);

            for (ItemStack items : e.getItems()) {
                if (items.getType() == Material.SKULL_ITEM) {
                    e.getItems().remove(items);
                    break;
                }
            }

            this.heads.put(blaze.getEntityId(), itemStack);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (!isEnabled()) {
            return;
        }

        if (this.heads.containsKey(e.getEntity().getEntityId())) {
            e.getEntity().getLocation().getWorld().dropItemNaturally(e.getEntity().getLocation(), this.heads.get(e.getEntity().getEntityId()));
        }
    }
}
