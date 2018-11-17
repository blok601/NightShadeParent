package me.blok601.nightshadeuhc.commands;

import me.blok601.nightshadeuhc.commands.extras.*;
import me.blok601.nightshadeuhc.commands.extras.disguise.RandomDisguiseCommand;
import me.blok601.nightshadeuhc.commands.game.run.*;
import me.blok601.nightshadeuhc.commands.game.setup.*;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scenario.cmd.*;
import me.blok601.nightshadeuhc.scenario.cmd.mole.*;
import me.blok601.nightshadeuhc.server.cmd.InfoCommand;
import me.blok601.nightshadeuhc.server.cmd.PlayerLocationCommand;
import me.blok601.nightshadeuhc.staff.cmd.*;
import me.blok601.nightshadeuhc.staff.spec.SpecCommand;
import me.blok601.nightshadeuhc.staff.spec.SpecsCommand;
import me.blok601.nightshadeuhc.staff.spec.SpectatorChatCommand;
import me.blok601.nightshadeuhc.stats.command.LeaderboardsCommand;
import me.blok601.nightshadeuhc.stats.command.StatsCommand;
import me.blok601.nightshadeuhc.teams.CmdTeamBase;
import me.blok601.nightshadeuhc.teams.CmdTeams;
import me.blok601.nightshadeuhc.teams.RvBCommand;

/**
 * Created by Blok on 6/26/2017.
 */
public class Commands {


    private static CmdInterface[] commands;

    public static void setup(){
        commands = new CmdInterface[]{
                new FeedAll(),
                new FreezeAll(),
                new GetHealth(),
                new HealCommand(),
                new Helpop(),
                new PvP(),
                new WhitelistCommand(),
                new InfoCommand(),
                new CmdStartUHC(),
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
                new CmdTeams(),
                new CmdTeamBase(),
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
                new PregenCommand(),
                new LeaderboardsCommand(),
                new StatsCommand(),
                new MoleChatCommand(),
                new MoleKitCommand(),
                new MoleLocationCommand(),
                new MolesCommand(),
                new StaffCommand(),
                new SpecCommand(),
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
                new SpecsCommand(),
                new PermaDayCommand(),
                new AliveCommand(),
                new ClearXPCommand(),
                new TeamInventoryCommand(),
                new SpectatingTimeCommand(),
                new TopSpecCommand(),
                new HallOfFameCommand(),
                new MoleCommand(),
                new SetMoleCommand()
        };
    }

    public static CmdInterface[] getCommands() {
        return commands;
    }

}
