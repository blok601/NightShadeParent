package com.nightshadepvp.core;

import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Blok on 3/2/2019.
 */
public class Announcer {

    private ArrayList<String> messages;
    private Core core;
    private Random random;
    private boolean running;

    public Announcer(Core core) {
        this.core = core;
        this.messages = MConf.get().getAnnouncerMessages();
        core.getLogManager().log(Logger.LogType.INFO, "Successfully loaded announcer messages!");
        this.random = ThreadLocalRandom.current();

        if (messages.size() == 0) {

            messages.add("&bHave a suggestion? Submit them on our Discord via >suggest!");
            messages.add("&bWant to donate and keep the server running? Visit http://nightshadepvp.com/shop/");
            messages.add("&bJoin our Discord @ http://discord.nightshadepvp.com");
            messages.add("&bFollow us on Twitter @NightShadePvPMC");
            messages.add("&bMake sure to ask the host if you have any questions with /helpop!");
            messages.add("&bBe sure to apply for staff on the forums! www.nightshadepvp.com");
            messages.add("&bFix the invis glitch by doing /show <player>");
            messages.add("&bToggle your private messages with /togglepm");
            messages.add("&BWe are always looking for new staff! Apply with /apply");

            if (ServerType.getType() == ServerType.UHC) {
                messages.add("&bUse /config to view the game config!");
                messages.add("&bSend your coordinates and ores to your teammates with /pmcoords and /pmores");
                messages.add("&bView the game scenarios by doing /scenarios");
                messages.add("&bView the matchpost with /matchpost");

            }
        }

        start();
    }

    public void start() {
        this.running = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!running) return;
                if (messages.size() == 0) return;
                if (core.getServer().getOnlinePlayers().size() == 0) {
                    return;
                }

                Bukkit.broadcastMessage(ChatUtils.format("&f&m--------------------------------------------------"));
                Bukkit.broadcastMessage(ChatUtils.message(messages.get(random.nextInt(messages.size()))));
                Bukkit.broadcastMessage(ChatUtils.format("&f&m--------------------------------------------------"));
            }
        }.runTaskTimer(core, 0, 3000L);
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
