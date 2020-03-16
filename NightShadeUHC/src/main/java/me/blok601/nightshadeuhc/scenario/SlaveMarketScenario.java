package me.blok601.nightshadeuhc.scenario;

import com.nightshadepvp.core.Core;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class SlaveMarketScenario extends Scenario {

    private Core core;

    public HashMap<UUID, Integer> owners;

    public int PLAYER_INDEX;
    public int TIME_TO_BID; //Keep
    public int CURRENT_BID; //Keep
    public UUID CURRENT_SLAVE;
    public UUID CURRENT_TOP_BIDDER;
    public boolean canBid;
    public Team leftOverTeam;

    public BukkitTask initializeTask;


    public SlaveMarketScenario(Core core) {
        super("Slave Market", "Slave owners bid on their teammates", new ItemBuilder(Material.DIAMOND).name("SlaveMarket").make());
        this.core = core;

        owners = new HashMap<>();

        PLAYER_INDEX = 0;
        TIME_TO_BID = 5;
        CURRENT_BID = 0;
        CURRENT_SLAVE = null;
        CURRENT_TOP_BIDDER = null;
        canBid = false;

        leftOverTeam = new Team("UHC10000", ChatColor.WHITE);
        initializeTask = null;
    }

    public void onStart (GameStartEvent e) {
        if (!isEnabled()) return;

        owners.forEach((k, v) -> {
            Player o = Bukkit.getPlayer(k);
            if (o != null) {
                o.getInventory().addItem(new ItemStack(Material.DIAMOND, v));
                sendMessage(o, "&eYou have received &b" + v + " diamonds");
            } else {
                if (core.getLoginTasks().containsKey(k)) {
                    ArrayList<Consumer<UUID>> list = new ArrayList<>();
                    list.add(uuid -> {
                        Player player = Bukkit.getPlayer(uuid);
                        player.getInventory().addItem(new ItemStack(Material.DIAMOND, v));
                        sendMessage(player, "&eYou have received &b" + v + " diamonds");
                    });
                }
            }


        });
        ChatUtils.sendAllScenarioMessage("&bSlave Owners have received their leftover diamonds!", this);
    }
}