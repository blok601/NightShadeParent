package me.blok601.nightshadeuhc.manager;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.player.disguise.Undisguise;
import me.blok601.nightshadeuhc.command.staff.RatesCommand;
import me.blok601.nightshadeuhc.component.GoldenHeadConsume;
import me.blok601.nightshadeuhc.listener.game.*;
import me.blok601.nightshadeuhc.listener.gui.GameSetupInventoryClick;
import me.blok601.nightshadeuhc.listener.gui.InvClick;
import me.blok601.nightshadeuhc.listener.gui.ScenarioClick;
import me.blok601.nightshadeuhc.listener.misc.SpectatorInfoListener;
import me.blok601.nightshadeuhc.listener.misc.SpectatorListener;
import me.blok601.nightshadeuhc.listener.misc.StaffListener;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scenario.SkriptScenarios;
import me.blok601.nightshadeuhc.stat.listener.JoinListener;
import org.bukkit.event.Listener;

/**
 * Created by Blok on 8/5/2017.
 */
public class ListenerHandler {

    private static Listener[] listeners = new Listener[]{
            new LoggerListener(),
            new InvClick(),
            new SpectatorListener(),
            new GoldenHeadConsume(),
            new RatesCommand(),
            ScenarioClick.get(),
            new StaffListener(),
            new Undisguise(),
            new JoinListener(GameManager.get()),
            new GameListener(GameManager.get()),
            new PlayerListener(GameManager.get()),
            new SkriptScenarios(),
            new ArenaListener(),
            new SpectatorInfoListener(),
            new GameDeathListener(),
            new GameSetupInventoryClick(GameManager.get(), UHC.get()),
            new WorldBorderListener(GameManager.get()),
            new ScenarioManager()
    };

    public static Listener[] getListeners() {
        return listeners;
    }
}
