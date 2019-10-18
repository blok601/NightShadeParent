package me.blok601.nightshadeuhc.scenario;

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

import java.util.HashMap;
import java.util.UUID;

public class SevenDeadlySinsScenario extends Scenario{

    private GameManager gm;
    public HashMap<UUID, String> sins = new HashMap<>();

    public SevenDeadlySinsScenario(GameManager gmc) {
        super("Seven Deadly Sins", "Every member of your team gets a sin assigned to them. The assignments are ", new ItemBuilder(Material.SKULL_ITEM).name("Seven Deadly Sins").make());
        gm = gmc;
    }

    @EventHandler
    public void onstart (GameStartEvent e) {

        if (!isEnabled()) return;
        for (Team team : TeamManager.getInstance().getTeams()) {
            int count = 0;
            if (team.getMembers().size() == 0) {
                continue;
            }
            for (String s : team.getMembers()) {
                Player p = Bukkit.getPlayer(s);
                if (p == null) continue;
                if (count == 0) sins.put(p.getUniqueId(), "Lust");
                if (count == 1) sins.put(p.getUniqueId(), "Gluttony");
                if (count == 2) sins.put(p.getUniqueId(), "Envy");
                if (count == 3) sins.put(p.getUniqueId(), "Greed");
                if (count == 4) sins.put(p.getUniqueId(), "Sloth");
                if (count == 5) sins.put(p.getUniqueId(), "Wrath");
                if (count == 6) sins.put(p.getUniqueId(), "Pride");
                count++;
                p.sendMessage(ChatUtils.message("&7[&c" + sins.get(p.getUniqueId()) + "&7]" + "&cMay the weight of your sins be a burden on your soul."));
            }

        }
        Bukkit.broadcastMessage(ChatUtils.message("&c For the wages of sin is death."));
    }





}
