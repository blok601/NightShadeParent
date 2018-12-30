package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.util.MathUtil;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Blok on 8/22/2018.
 */
public class KingsScenario extends Scenario {

    private static HashMap<UUID, Team> kings = new HashMap<>();
    private HashSet<UUID> effectQueue = new HashSet<>();
    private HashSet<UUID> debuffQueue = new HashSet<>();

    public KingsScenario() {
        super("Kings", "Team based fun, where one member on your team is the king. The King has some buffs. When the King dies, the remaining teammates get debuffs.", new ItemBuilder(Material.DIAMOND_BLOCK).name("Kings").make());
    }

    @EventHandler
    public void onStart(GameStartEvent e) {
        if (!isEnabled()) {
            return;
        }

        if (!GameManager.get().isIsTeam()) return;
        assignKings();
        new BukkitRunnable() {
            int count = 5;

            @Override
            public void run() {
                if (count == 0) {
                    Player player;
                    PacketPlayOutWorldParticles packetPlayOutWorldParticles;
                    for (Map.Entry<UUID, Team> teamEntry : kings.entrySet()) {
                        if (Bukkit.getPlayer(teamEntry.getKey()) != null) {
                            player = Bukkit.getPlayer(teamEntry.getKey());
                            player.setMaxHealth(40.0);
                            player.setHealth(player.getMaxHealth());
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0));
                            player.getWorld().strikeLightningEffect(player.getLocation());
                            player.sendMessage(getPrefix() + "&&5You are the King, be careful. Your team depends on you to survive.");
                            for (Location location : MathUtil.getCircle(player.getLocation(), 2.0, 1)) {
                                packetPlayOutWorldParticles = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, 0);
                                for (String teamMemberName : teamEntry.getValue().getMembers()) {
                                    if (Bukkit.getPlayer(teamMemberName) != null) {
                                        ((CraftPlayer) Bukkit.getPlayer(teamMemberName)).getHandle().playerConnection.sendPacket(packetPlayOutWorldParticles);
                                    }
                                }
                            }

                            teamEntry.getValue().message(ChatUtils.message(getPrefix()  + "&5" + player.getName() + " &eis your King!"));
                        } else {
                            effectQueue.add(teamEntry.getKey());
                            teamEntry.getValue().message(ChatUtils.message(getPrefix() + "&5" + Bukkit.getOfflinePlayer(teamEntry.getKey()).getName() + " &eis your King!"));
                        }
                    }
                    cancel();
                    return;
                }

                Bukkit.getOnlinePlayers().forEach(o -> o.sendMessage(ChatUtils.format(getPrefix() + "&eKings will be set in &3" + count + "&e...")));
                count--;
            }
        }.runTaskTimer(UHC.get(), 0, 20);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!isEnabled()) return;
        Player player = e.getPlayer();
        if(this.effectQueue.contains(player.getUniqueId())){
            player.setMaxHealth(40.0);
            player.setHealth(player.getMaxHealth());
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0));
            player.getWorld().strikeLightningEffect(player.getLocation());
            player.sendMessage(getPrefix() + getPrefix() + "&&5You are the King, be careful. Your team depends on you to survive.");
            this.effectQueue.remove(player.getUniqueId());
            return;
        }

        if(this.debuffQueue.contains(player.getUniqueId())){
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30*20, 0)); //Poison 1 for 30 seconds
            player.sendMessage(ChatUtils.format(getPrefix() + "&eYour king died while you were offline..."));
            return;
        }

        if(KingsScenario.kings.containsKey(player.getUniqueId())){
            player.sendMessage(ChatUtils.format(getPrefix() + "&eRemember, you are your team's king..."));
        }
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){
        Player player = e.getKilled();
        if(!isEnabled()) return;
        if(kings.containsKey(player.getUniqueId())){
            //They are a king
            player.sendMessage(ChatUtils.format(getPrefix() + "&eYou have died! Your teammates are feeling the consequences."));

            player.setMaxHealth(20.0);
            Team team = kings.get(player.getUniqueId());
            if(team == null) return;
            kings.remove(player.getUniqueId());
            //Do the stuff
            Player p;
            for (String name : team.getMembers()){
                p = Bukkit.getPlayer(name);
                if(p == null){
                    // offline
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
                    this.debuffQueue.add(offlinePlayer.getUniqueId());
                }else{
                    // online
                    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30*20, 0)); //Poison 1 for 30 seconds
                    p.sendMessage(ChatUtils.format(getPrefix() + "&eYour king has died, you are feeling the effects..."));
                }
            }
        }
    }

    private void assignKings() {
        Random random = new Random();
        OfflinePlayer offlinePlayer;
        Player online;
        for (Team team : TeamManager.getInstance().getTeams()) {
            offlinePlayer = Bukkit.getOfflinePlayer(team.getMembers().get(random.nextInt(team.getMembers().size())));
            kings.put(offlinePlayer.getUniqueId(), team); //Just used offline players for now
        }
    }
}
