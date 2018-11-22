package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.entity.Chicken;


public class EggsScenario extends Scenario {
  public EggsScenario() {
    super("Eggs", "Throwing an Egg Spawns a random mob, chickens have a change of dropping eggs on death.", new ItemBuilder(Material.EGG).name(ChatUtils.format("Eggs")).make());
   }
   @EventHandler
   public void onHit (ProjectileHitEvent e) {
     Projectile pr = e.getEntity();
     if (!isEnabled()) return;
     if (!(pr instanceof Egg)) {
       return;
     }
     Random rand = new Random();
     ArrayList<EntityType> types = new ArrayList<EntityType>();

     for (EntityType type : EntityType.values()) {
       if (!type.isAlive() || !type.isSpawnable()) {
         continue;
       }

       types.add(type);
     }
     EntityType type = types.get(rand.nextInt(types.size()));

     // get the location and world the projectile hit in.
     Location loc = pr.getLocation();
     World world = pr.getWorld();

     world.spawnEntity(loc, type);
   }
   @EventHandler
   public void onDeath (EntityDeathEvent e) {

       // check if the entity was a chicken, if not return.
       if (!(e.getEntity() instanceof Chicken)) {
         return;
       }

       Random rand = new Random();
       double chance = 0.05;

       // check if the random value is more than the chance, if so return.
       if (rand.nextDouble() > chance) {
         return;
       }

       // add an egg to the drops of the chicken.
       e.getDrops().add(new ItemStack(Material.EGG));

   }
  }
