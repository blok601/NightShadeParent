package me.blok601.nightshadeuhc.manager;

import me.blok601.nightshadeuhc.commands.extras.RatesCommand;
import me.blok601.nightshadeuhc.commands.extras.disguise.Undisguise;
import me.blok601.nightshadeuhc.listeners.game.*;
import me.blok601.nightshadeuhc.listeners.gui.GameSetupInventoryClick;
import me.blok601.nightshadeuhc.listeners.gui.InvClick;
import me.blok601.nightshadeuhc.listeners.gui.ScenarioClick;
import me.blok601.nightshadeuhc.listeners.modules.GoldenHeadConsume;
import me.blok601.nightshadeuhc.listeners.modules.NetherEvent;
import me.blok601.nightshadeuhc.logger.LoggerEvents;
import me.blok601.nightshadeuhc.scenario.SkriptScenarios;
import me.blok601.nightshadeuhc.staff.listener.CommandSpyListener;
import me.blok601.nightshadeuhc.staff.listener.StaffListener;
import me.blok601.nightshadeuhc.staff.spec.SpecEvents;
import me.blok601.nightshadeuhc.staff.spec.info.SpectatorInfoListener;
import me.blok601.nightshadeuhc.stats.listener.JoinListener;
import me.blok601.nightshadeuhc.teams.FriendlyFire;
import org.bukkit.event.Listener;

/**
 * Created by Blok on 8/5/2017.
 */
public class ListenerHandler {

    private static Listener[] listeners = new Listener[]{
            new LoggerEvents(),
            new InvClick(),
            new SpecEvents(),
            new FriendlyFire(),
            new NetherEvent(),
            new GoldenHeadConsume(),
            new RatesCommand(),
            ScenarioClick.get(),
            new StaffListener(),
            new Undisguise(),
            new CommandSpyListener(),
            new JoinListener(),
            new GameListener(),
            new PlayerListener(),
            new BorderActionBar(),
            new SkriptScenarios(),
            new ArenaListener(),
            new SpectatorInfoListener(),
            new GameDeathListener(),
            new GameSetupInventoryClick()
    };

    public static Listener[] getListeners() {
        return listeners;
    }
}
