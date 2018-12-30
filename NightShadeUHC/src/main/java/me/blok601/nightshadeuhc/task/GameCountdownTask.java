package me.blok601.nightshadeuhc.task;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.object.PlayerStatus;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ActionBarUtil;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.FreezeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by Blok on 12/22/2018.
 */
public class GameCountdownTask extends BukkitRunnable {

    private int counter = 180;
    private UHC uhc;
    private GameManager gameManager;

    public GameCountdownTask(UHC uhc, GameManager gameManager) {
        this.uhc = uhc;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (counter <= 0) {
            this.cancel();
            ArrayList<Player> valid = new ArrayList<>();
            UHCPlayer gp;
            for (Player ps : Bukkit.getOnlinePlayers()) {
                gp = UHCPlayer.get(ps.getUniqueId());
                if (!gp.isSpectator()) {
                    gp.setPlayerStatus(PlayerStatus.PLAYING);
                    valid.add(ps);
//                    if (UHC.players.contains(ps.getUniqueId())) continue;
//                    UHC.players.add(ps.getUniqueId());
                }
            }

            gameManager.getWorld().setTime(20);
            ChatUtils.setChatFrozen(true);
            Bukkit.broadcastMessage(ChatUtils.message("&eUse /helpop or message any online staff members if you need help!"));
            FreezeUtil.start();
            UHCPlayerColl.get().getAllOnline().stream().filter(UHCPlayer::isInArena).forEach(UHCPlayer::leaveArena);
            GameState.setState(GameState.STARTING);
            Bukkit.getOnlinePlayers().forEach(o -> {
                ActionBarUtil.sendActionBarMessage(o, "§5The scatter is beginning....");
                o.getInventory().clear();
                o.getInventory().setArmorContents(null);
            });
            new ScatterTask(valid, gameManager.getWorld(), gameManager.getSetupRadius(), gameManager.getHost(), gameManager.getFinalHealTime(), gameManager.getPvpTime(), gameManager.getBorderTime(), gameManager.isIsTeam(), gameManager.getFirstShrink(), gameManager.getMeetupTime(), gameManager).runTaskTimer(UHC.get(), 0, 4);
            return;
        }

        if (counter % 60 == 0) {
            Bukkit.broadcastMessage(ChatUtils.message("&eScatter will begin in &b" + (counter / 60) + " &eminute&8(&es&8)..."));
        }
        counter--;

        Bukkit.getOnlinePlayers().forEach(o -> {
            ActionBarUtil.sendActionBarMessage(o, "§5Scatter§8» " + get(counter), 1, uhc);
        });

    }

    private String get(int i) {
        int m = i / 60;
        int s = i % 60;

        return "§3" + m + "§5m§3" + s + "§5s";
    }

}
