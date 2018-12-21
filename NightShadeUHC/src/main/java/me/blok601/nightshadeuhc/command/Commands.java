package me.blok601.nightshadeuhc.command;

import me.blok601.nightshadeuhc.command.player.disguise.RandomDisguiseCommand;
import me.blok601.nightshadeuhc.command.game.run.*;
import me.blok601.nightshadeuhc.command.game.setup.*;
import me.blok601.nightshadeuhc.command.player.*;
import me.blok601.nightshadeuhc.command.server.eWhitelistCommand;
import me.blok601.nightshadeuhc.command.staff.*;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scenario.cmd.*;
import me.blok601.nightshadeuhc.scenario.cmd.mole.*;
import me.blok601.nightshadeuhc.command.server.cmd.InfoCommand;
import me.blok601.nightshadeuhc.command.server.cmd.PlayerLocationCommand;
import me.blok601.nightshadeuhc.command.staff.SpectatorCommand;
import me.blok601.nightshadeuhc.command.staff.SpectatorsCommand;
import me.blok601.nightshadeuhc.command.player.SpectatorChatCommand;
import me.blok601.nightshadeuhc.stat.command.LeaderboardsCommand;
import me.blok601.nightshadeuhc.stat.command.StatsCommand;
import me.blok601.nightshadeuhc.command.player.teams.TeamBaseCommand;
import me.blok601.nightshadeuhc.command.player.teams.TeamsCommand;
import me.blok601.nightshadeuhc.command.player.teams.PMOresCommand;
import me.blok601.nightshadeuhc.command.player.teams.RvBCommand;

/**
 * Created by Blok on 6/26/2017.
 */
public class Commands {


    private static UHCCommand[] commands;

    public static void setup(){
        commands = new UHCCommand[]{
                new FeedAll(),
                new FreezeAll(),
                new HealthCommand(),
                new HealCommand(),
                new HelpopCommand(),
                new PvPCommand(),
                new WhitelistCommand(),
                new InfoCommand(),
                new StartUHCCommand(),
                new LootCrateTimeCommand(),
                new BackpackCommand(),
                new CancelGameCommand(),
                new RandomDisguiseCommand(),
                new EndGameCommand(),
                new SpectatorChatCommand(),
                new AlertsCommand(),
                new PlayerLocationCommand(),
                new CommandSpyCmd(),
                new SetTickCommand(),
                new TeleportAllCommand(),
                new ChatStopCommand(),
                new ManualGameCommand(),
                new GameSettingsCommand(),
                new ClaimHostCommand(),
                new SitCommand(),
                new SetWorldCommand(),
                new ScenarioManager(),
                new TeamsCommand(),
                new TeamBaseCommand(),
                new MaxplayersCommand(),
                new TimerCommand(),
                new ConfigCommand(),
                new LoadPresetCommand(),
                new eWhitelistCommand(),
                new RespawnCommand(),
                new LateStartCommand(),
                new KillTopCommand(),
                new DeathBanCommand(),
                new ExplainCommand(),
                new RatesCommand(),
                new FeedCommand(),
                new ToggleHelpopCommand(),
                new ScenariosCommand(),
                new ResetHealthCommand(),
                new ClearArmorCommand(),
                new CreateWorldCommand(),
                new TpWorldCommand(),
                new WorldsCommand(),
                new DeleteWorldCommand(),
                new FullBrightCommand(),
                new RvBCommand(),
                new LeaderboardsCommand(),
                new StatsCommand(),
                new MoleChatCommand(),
                new MoleKitCommand(),
                new MoleLocationCommand(),
                new MolesCommand(),
                new StaffCommand(),
                new SpectatorCommand(),
                new SetSpawnCommand(),
                new SetArenaSpawnCommand(),
                new ArenaCommand(),
                new WallsCommand(),
                new CancelPregenCommand(),
                new PowerCommand(),
                new AssignPowerCommand(),
                new AnonymousNameCommand(),
                new AssaultAndBatteryRoleCommand(),
                new KickSolosCommand(),
                new SpectatorsCommand(),
                new PermaDayCommand(),
                new AliveCommand(),
                new ClearXPCommand(),
                new TeamInventoryCommand(),
                new SpectatingTimeCommand(),
                new TopSpecCommand(),
                new HallOfFameCommand(),
                new MoleCommand(),
                new SetMoleCommand(),
                new ToggleMobsCommand(),
                new ClearTreesCommand(),
                new PMOresCommand(),
                new BetaHostCommand(),
                new ConfirmCommand()
        };
    }

    public static UHCCommand[] getCommands() {
        return commands;
    }

}
