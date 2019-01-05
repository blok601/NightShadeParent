package me.blok601.nightshadeuhc.command;

import com.google.common.collect.Sets;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.game.run.*;
import me.blok601.nightshadeuhc.command.game.setup.*;
import me.blok601.nightshadeuhc.command.player.*;
import me.blok601.nightshadeuhc.command.player.disguise.RandomDisguiseCommand;
import me.blok601.nightshadeuhc.command.player.teams.*;
import me.blok601.nightshadeuhc.command.server.eWhitelistCommand;
import me.blok601.nightshadeuhc.command.staff.*;
import me.blok601.nightshadeuhc.command.staff.admin.SetArenaSpawnCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scenario.cmd.*;
import me.blok601.nightshadeuhc.scenario.cmd.mole.*;
import me.blok601.nightshadeuhc.stat.command.LeaderboardsCommand;
import me.blok601.nightshadeuhc.stat.command.StatsCommand;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Blok on 6/26/2017.
 */
public class CommandHandler implements CommandExecutor {


    private Core core;
    private UHC uhc;
    private GameManager gameManager;
    private ScenarioManager scenarioManager;
    private HashSet<UHCCommand> commands;

    public CommandHandler(UHC uhc, GameManager gameManager, ScenarioManager scenarioManager) {
        this.gameManager = gameManager;
        this.scenarioManager = scenarioManager;
        this.commands = Sets.newHashSet();
        registerCommand(new FeedAll());
        registerCommand(new FreezeAll());
        registerCommand(new HealthCommand());
        registerCommand(new HealCommand());
        registerCommand(new HelpopCommand());
        registerCommand(new PvPCommand());
        registerCommand(new WhitelistCommand());
        registerCommand(new LootCrateTimeCommand(scenarioManager));
        registerCommand(new BackpackCommand(scenarioManager));
        registerCommand(new CancelGameCommand(gameManager));
        registerCommand(new RandomDisguiseCommand());
        registerCommand(new EndGameCommand(scenarioManager));
        registerCommand(new SpectatorChatCommand());
        registerCommand(new AlertsCommand());
        registerCommand(new CommandSpyCmd());
        registerCommand(new SetTickCommand());
        registerCommand(new TeleportAllCommand());
        registerCommand(new ChatStopCommand());
        registerCommand(new ManualGameCommand());
        registerCommand(new ClaimHostCommand());
        registerCommand(new SitCommand());
        registerCommand(new ScenarioManager());
        registerCommand(new TeamsCommand(scenarioManager));
        registerCommand(new TeamBaseCommand(scenarioManager));
        registerCommand(new MaxplayersCommand());
        registerCommand(new TimerCommand());
        registerCommand(new ConfigCommand());
        registerCommand(new LoadPresetCommand());
        registerCommand(new eWhitelistCommand());
        registerCommand(new RespawnCommand());
        registerCommand(new LateStartCommand());
        registerCommand(new KillTopCommand());
        registerCommand(new DeathBanCommand());
        registerCommand(new ExplainCommand(scenarioManager));
        registerCommand(new RatesCommand(gameManager, scenarioManager));
        registerCommand(new FeedCommand());
        registerCommand(new ToggleHelpopCommand());
        registerCommand(new ScenariosCommand(scenarioManager));
        registerCommand(new ResetHealthCommand());
        registerCommand(new ClearArmorCommand());
        registerCommand(new CreateWorldCommand());
        registerCommand(new TpWorldCommand());
        registerCommand(new WorldsCommand());
        registerCommand(new DeleteWorldCommand());
        registerCommand(new FullBrightCommand());
        registerCommand(new RvBCommand());
        registerCommand(new LeaderboardsCommand());
        registerCommand(new StatsCommand());
        registerCommand(new MoleChatCommand(scenarioManager));
        registerCommand(new MoleKitCommand(scenarioManager));
        registerCommand(new MoleLocationCommand(scenarioManager));
        registerCommand(new MolesCommand());
        registerCommand(new StaffCommand());
        registerCommand(new SpectatorCommand());
        registerCommand(new SetSpawnCommand());
        registerCommand(new SetArenaSpawnCommand());
        registerCommand(new ArenaCommand());
        registerCommand(new WallsCommand());
        registerCommand(new CancelPregenCommand());
        registerCommand(new PowerCommand(scenarioManager));
        registerCommand(new AssignPowerCommand(scenarioManager));
        registerCommand(new AnonymousNameCommand(scenarioManager));
        registerCommand(new AssaultAndBatteryRoleCommand(scenarioManager));
        registerCommand(new KickSolosCommand());
        registerCommand(new SpectatorsCommand());
        registerCommand(new PermaDayCommand());
        registerCommand(new AliveCommand());
        registerCommand(new ClearXPCommand());
        registerCommand(new TeamInventoryCommand(scenarioManager));
        registerCommand(new SpectatingTimeCommand());
        registerCommand(new TopSpecCommand());
        registerCommand(new HallOfFameCommand());
        registerCommand(new MoleCommand());
        registerCommand(new SetMoleCommand());
        registerCommand(new ToggleMobsCommand());
        registerCommand(new ClearTreesCommand());
        registerCommand(new PMOresCommand());
        registerCommand(new HostCommand(gameManager, scenarioManager));
        registerCommand(new ConfirmCommand());
        registerCommand(new SetBorderCommand());
        registerCommand(new DoubleDatesCommand(gameManager, scenarioManager));
        registerCommand(new CheckTeamCommand(scenarioManager));
        registerCommand(new TeamChatCommand());
        registerCommand(new SendCoordsCommand());
    }


    public HashSet<UHCCommand> getCommands() {
        return commands;
    }

    private void registerCommand(UHCCommand uhcCommand) {
        if (this.commands.contains(uhcCommand)) return;
        this.commands.add(uhcCommand);
    }

//    public void checkCommands() {
//        if (this.commands == null || this.commands.isEmpty()) {
//            new BukkitRunnable() {
//                @Override
//                public void run() {
//                    checkCommands();
//                    return;
//                }
//            }.runTaskLater(uhc, 100);
//        }
//
//        HashSet<String> unregistered = Sets.newHashSet();
//        PluginDescriptionFile pdf = uhc.getDescription();
//        for (String cmdName : pdf.getCommands().keySet()) {
//            if (getCommandByName(cmdName) == null) {
//                //unregistered.add(getCommandByName(cmdName));
//            }
//        }
//
//        if (!unregistered.isEmpty()) {
//            core.getLogManager().log(Logger.LogType.WARNING, "&cThere are unregistered commands: " + Joiner.on("&7, &c").join(unregistered));
//        }
//
//    }

//    private UHCCommand getCommandByName(String test) {
//        return this.commands.stream().filter(uhcCommand -> uhc.getName().equalsIgnoreCase(test)).findFirst().orElse(null);
//    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (this.getCommands() == null || this.commands.isEmpty()) {
            new CommandHandler(uhc, gameManager, scenarioManager);
        }

        for (UHCCommand ci : this.getCommands()) {
            List<String> cmds = new ArrayList<String>();
            if (ci.getNames() != null) {
                //Add all the possible aliases
                cmds.addAll(Arrays.asList(ci.getNames()));
            }

            for (String n : cmds) {
                if (cmd.getName().equalsIgnoreCase(n)) {
                    if (ci.playerOnly()) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(ChatUtils.message("&cThis is a player only command!"));
                            return false;
                        }
                    }

                    if (!(sender instanceof Player)) {
                        try {
                            ci.onCommand(sender, cmd, label, args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }

                    Player p = (Player) sender;
                    NSPlayer user = NSPlayer.get(p.getUniqueId());
                    if (ci.hasRequiredRank()) {
                        if (!(user.hasRank(ci.getRequiredRank()))) {
                            p.sendMessage(com.nightshadepvp.core.utils.ChatUtils.message("&cYou require the " + ci.getRequiredRank().getPrefix() + "&crank to do this command!"));
                            return false;
                        }
                    }

                    try {
                        ci.onCommand(sender, cmd, label, args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
