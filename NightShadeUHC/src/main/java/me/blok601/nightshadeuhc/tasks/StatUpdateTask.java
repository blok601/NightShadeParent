package me.blok601.nightshadeuhc.tasks;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.stats.CachedGame;
import me.blok601.nightshadeuhc.stats.handler.StatsHandler;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Blok on 8/9/2018.
 */
public class StatUpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        StatsHandler.getInstance().getWinners().clear();
        StatsHandler.getInstance().getKills().clear();

        ArrayList<UHCPlayer> temp = new ArrayList<>(UHCPlayerColl.get().getAll());

        temp.sort(Comparator.comparing(UHCPlayer::getGamesWon).reversed());
        StatsHandler.getInstance().getWinners().addAll(temp);

        //Sorted all of them

        temp.clear();
        temp = new ArrayList<>(UHCPlayerColl.get().getAll());
        temp.sort(Comparator.comparing(UHCPlayer::getKills).reversed());
        //Sorted all kills
        StatsHandler.getInstance().getKills().addAll(temp);

        //Hall of fame inventory

        ArrayList<CachedGame> cachedGames = new ArrayList<>();
        FindIterable<Document> documents = UHC.get().getGameCollection().find();
        MongoCursor<Document> cursor = documents.iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                CachedGame cachedGame = new CachedGame(doc.getString("host"));
                cachedGame.setWinners((List<String>) doc.get("winners"));
                cachedGame.setScenarios((List<String>) doc.get("scenarios"));
                cachedGame.setFill(doc.getInteger("players"));
                cachedGame.setWinnerKills((Map<String, Integer>) doc.get("winnerKills"));
                cachedGame.setTeamType(doc.getString("teamType"));
                cachedGame.setStart(doc.getLong("startTime"));
                cachedGame.setStart(doc.getLong("endTime"));
                cachedGame.setServer(doc.getString("server"));
//                if (cachedGame.getFill() > 10)
//                    cachedGames.add(cachedGame);
            }
        } finally {
            cursor.close();
        }

        //sort the timestamps
        cachedGames.sort(Comparator.comparing(CachedGame::getEnd));
        ArrayList<ItemStack> items = new ArrayList<>();
        SkullMeta skullMeta;
        ItemStack head;
        ItemBuilder itemBuilder;
        UHCPlayer host;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss aa MM/dd/YYYY");
        for (CachedGame cachedGame : cachedGames) {
            host = UHCPlayer.get(UUID.fromString(cachedGame.getHost()));
            head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            skullMeta = (SkullMeta) head.getItemMeta();
            skullMeta.setOwner(host.getName());
            head.setItemMeta(skullMeta);
            itemBuilder = new ItemBuilder(head);
            itemBuilder.name("&6" + host.getName() + "&8»");
            itemBuilder.lore("&eStart Time&8» &b" + simpleDateFormat.format(new Date(cachedGame.getStart())));
            //itemBuilder.lore("&eEnd Time&8» &b" + simpleDateFormat.format(new Date(cachedGame.getEnd())));
            itemBuilder.lore(" ");
            itemBuilder.lore("&eFill&8» &b" + cachedGame.getFill());
            itemBuilder.lore("&eTeam Type&8» &b" + cachedGame.getTeamType());
            itemBuilder.lore(" ");
            if (cachedGame.getWinners().size() > 1) {
                //FFA
                String winnerName = UHCPlayer.get(UUID.fromString(cachedGame.getWinners().get(0))).getName();
                itemBuilder.lore("&eWinner&8» &b" + winnerName + " &8[&3" + cachedGame.getWinnerKills().get(cachedGame.getWinners().get(0)) + "&8]");
            } else {
                itemBuilder.lore("&eWinners&8»");
                String winnerName;
                for (String winner : cachedGame.getWinners()) {
                    winnerName = UHCPlayer.get(UUID.fromString(winner)).getName();
                    itemBuilder.lore("  &8→ &b" + winnerName + "&8[&3" + cachedGame.getWinnerKills().get(winner) + "&8]");
                }
            }
            itemBuilder.lore(" ");
            itemBuilder.lore("&eScenarios&8»");
            for (String scenario : cachedGame.getScenarios()) {
                itemBuilder.lore("  &8→ &b" + scenario);
            }
            items.add(itemBuilder.make());
        }
        StatsHandler.getInstance().getHallOfFameInventory().clear();
        StatsHandler.getInstance().getHallOfFameInventory().addAll(items);
    }
}
