package me.blok601.nightshadeuhc.teams;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.entity.object.CachedColor;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scoreboard.PlayerScoreboard;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class CmdTeamBase implements CmdInterface{
	
	public static HashMap<String, String> invites = new HashMap<>();


	
	public void sendHelp(Player p){
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "Team Commands:");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team create");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team invite <player>");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team kick <player>");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team list [player]");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team join <player>");
	}
	
	public void sendAdminHelp(Player p){
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "Team Commands:");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team create");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team invite <player>");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team kick <player>");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team list [player]");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team join <player>");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "Admin commands:");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team limit <size>");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team reset");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team man <on/off>");
		p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.GREEN + "/team ff <on/off>");
	}

    @Override
    public String[] getNames() {
        return new String[]{
                "team"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        if(s instanceof Player){
            final Player p = (Player) s;
            NSPlayer user = NSPlayer.get(p.getUniqueId());
            System.out.println(args.length);

            if(args.length == 0){
                if(user.hasRank(Rank.TRIAL)){
                    sendAdminHelp(p);
                    System.out.println("Sending help line 33");
                    return;
                }else{
                    sendHelp(p);
                    System.out.println("Sending help line 36");
                    return;
                }
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("create")){
                    //Check if team man is on
                    if(TeamManager.getInstance().isTeamManagement()){
                        if(TeamManager.getInstance().getTeam(p) != null){
                            p.sendMessage(ChatUtils.message("&cYou are already on a team!"));
                            return;
                        }

                        int ts = TeamManager.getInstance().getTeams().size()+1;

                        TeamManager.getInstance().addTeam(new Team("UHC" + ts, p));
                        p.sendMessage(ChatUtils.message("&eSuccessfully created team!"));
                    }else{
                        p.sendMessage(ChatUtils.message("&cTeam management is currently disabled!"));
                        return;
                    }

                }else if(args[0].equalsIgnoreCase("list")){
                    if(TeamManager.getInstance().getTeam(p) == null){
                        p.sendMessage(ChatUtils.message("&cYou aren't on a team!"));
                        return;
                    }

                    Team t = TeamManager.getInstance().getTeam(p);
                    for (String str : t.getMembers()){
                        p.sendMessage(ChatColor.YELLOW + str + ",");
                    }
                }else if(args[0].equalsIgnoreCase("leave")){
                    if(TeamManager.getInstance().isTeamManagement()){
                        if(TeamManager.getInstance().getTeam(p) == null){
                            p.sendMessage(ChatUtils.message("&cYou aren't on a team!"));
                            return;
                        }

                        Team t = TeamManager.getInstance().getTeam(p);
                        if(t.getMembers().size() == 1){
                            t.removeMember(p);
                            TeamManager.getInstance().removeTeam(t);
                            p.sendMessage(ChatUtils.message("&eYour team has disbanded!"));
                            return;
                        }else{
                            t.removeMember(p);
                            p.sendMessage(ChatUtils.message("&eYou have left your team!"));
                            for (String str : t.getMembers()){
                                Bukkit.getPlayer(str).sendMessage(ChatUtils.message("&6" + p.getName() + " &ehas left the team!"));
                            }
                            return;
                        }

                    }else{
                        p.sendMessage(ChatUtils.message("&cTeam management is currently disabled!"));
                        return;
                    }
                }else if(args[0].equalsIgnoreCase("reset")){
                    if(user.hasRank(Rank.HOST)){
                        ScoreboardManager scoreboardManager = UHC.get().getScoreboardManager();
                        TeamManager.getInstance().getTeams().clear();
                        Scoreboard scoreboard;

                        for (Map.Entry<Player, PlayerScoreboard> playerPlayerScoreboardEntry : scoreboardManager.getPlayerScoreboards().entrySet()) {
                            if (playerPlayerScoreboardEntry.getKey() == null) continue;
                            if (playerPlayerScoreboardEntry.getValue() == null) continue;
                            scoreboard = playerPlayerScoreboardEntry.getValue().getBukkitScoreboard();
                            for (org.bukkit.scoreboard.Team team : scoreboard.getTeams()){
                                if(team == null) continue;

                                if(team.getName().startsWith("UHC")){
                                    team.unregister();
                                }

                                if(team.getScoreboard().getTeam("RED") == null && team.getScoreboard().getTeam("BLUE") == null) continue;
                                if(team.getName().equalsIgnoreCase("RED") || team.getName().equalsIgnoreCase("BLUE")){
                                    team.unregister();
                                }
                            }
                        }
                        p.sendMessage(ChatUtils.message("&eTeams have been reset!"));
                    }else{
                        p.sendMessage(ChatUtils.message("&cYou require the HOST rank to do this command!"));
                    }
                }else if(args[0].equalsIgnoreCase("color")){
                    if(user.hasRank(Rank.TRIALHOST)){
                        ScoreboardManager scoreboardManager = UHC.get().getScoreboardManager();
                        Scoreboard scoreboard;
                        for (Team team : TeamManager.getInstance().getTeams()){
                            String color = generateColor();
                            for (Map.Entry<Player, PlayerScoreboard> playerPlayerScoreboardEntry : scoreboardManager.getPlayerScoreboards().entrySet()){
                                if(playerPlayerScoreboardEntry.getValue() == null) continue;
                                if(playerPlayerScoreboardEntry.getKey() == null) continue;

                                scoreboard = playerPlayerScoreboardEntry.getValue().getBukkitScoreboard();
                                if(scoreboard.getTeam(team.getName()) != null) {
                                    scoreboard.getTeam(team.getName()).unregister();
                                }

                                org.bukkit.scoreboard.Team t = scoreboard.registerNewTeam(team.getName());
                                t.setPrefix(color);
//                                if(t.getPrefix().contains("&k") || t.getPrefix().endsWith("&r")){
//                                    t.setPrefix(generateColor());
//                                }

                                for (String mem : team.getMembers()){
                                    CachedColor cachedColor = new CachedColor(team.getName());
                                    cachedColor.setColor(color);
                                    t.addEntry(mem);
                                    cachedColor.setPlayer(mem);
                                    GameManager.getColors().add(cachedColor);
                                }

                            }

                        }

                        //Color solos
                        Player player;
                        String name;
                        for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllOnline()){
                            String color = generateColor();
                            player = uhcPlayer.getPlayer();
                            if(TeamManager.getInstance().getTeam(player) != null) continue; //Already colored
                            String playerString = player.getName().length() > 16 ? player.getName().substring(0, 12) : player.getName();
                            name = "UHC" + playerString;

                            for (Map.Entry<Player, PlayerScoreboard> playerPlayerScoreboardEntry : scoreboardManager.getPlayerScoreboards().entrySet()){
                                if(playerPlayerScoreboardEntry.getValue() == null) continue;
                                if(playerPlayerScoreboardEntry.getKey() == null) continue;

                                scoreboard = playerPlayerScoreboardEntry.getValue().getBukkitScoreboard();
                                if(scoreboard.getTeam(name) != null) {
                                    scoreboard.getTeam(name).unregister();
                                }

                                org.bukkit.scoreboard.Team t = scoreboard.registerNewTeam(name);
                                t.setPrefix(color);
                                t.addEntry(player.getName());
                                CachedColor cachedColor = new CachedColor(name);
                                cachedColor.setColor(color);
                                cachedColor.setPlayer(player.getName());
                                GameManager.getColors().add(cachedColor);
                            }
                        }

                        p.sendMessage(ChatUtils.message("&eColored all teams and solos!"));

                    }else{
                        p.sendMessage(ChatUtils.message("&cYou require the HOST rank to do this command!"));
                    }
                }else if(args[0].equalsIgnoreCase("random")){

                    if(!user.hasRank(Rank.TRIAL)){
                        p.sendMessage(ChatUtils.message("&cYou require the TRIAL rank to do this command!"));
                        return;
                    }

                    TeamManager.getInstance().setRandomTeams(true);

                    p.sendMessage(ChatUtils.message("&cGenerating random teams of ..." + TeamManager.getInstance().getTeamSize()));

                    List<Player> valid = new ArrayList<>();
                    UHCPlayer gamePlayer;
                    for (Player player : Bukkit.getOnlinePlayers()){
                       gamePlayer = UHCPlayer.get(player.getUniqueId());
                       if(gamePlayer == null) continue;
                       if(!gamePlayer.isSpectator()) valid.add(player);
                    }
                    Collections.shuffle(valid);

                    List<List<Player>> teams = splitList(valid, TeamManager.getInstance().getTeamSize());


                    ArrayList<String> templist;
                    for (List<Player> list : teams){
                        templist = new ArrayList<>();
                        for (Player player : list){
                            templist.add(player.getName());
                        }


                        Team team = new Team("UHC" + (TeamManager.getInstance().getTeams().size()+1), templist);
                        TeamManager.getInstance().addTeam(team);
                    }

                    p.sendMessage(ChatUtils.message("&eFinished creating &6" + TeamManager.getInstance().getTeams().size() + " &eteams of &6" + TeamManager.getInstance().getTeamSize()));
                }
            }else if(args.length == 2){
                if(args[0].equalsIgnoreCase("invite")){
                    //Check if team management is on or off
                    //Check if their team size is less than the limit
                    if(TeamManager.getInstance().isTeamManagement()){
                        if(TeamManager.getInstance().getTeam(p) == null){
                            p.sendMessage(ChatUtils.message("&cYou don't have a team! &eDo /team create to make one!"));
                            return;
                        }

                        Team team = TeamManager.getInstance().getTeam(p);
                        if(!team.getMembers().get(0).equalsIgnoreCase(p.getName())){
                            p.sendMessage(ChatUtils.message("&cOnly team leaders can do this command!"));
                            return;
                        }

                        final Player target = Bukkit.getPlayerExact(args[1]);
                        if(target == null){
                            p.sendMessage(ChatUtils.message("&cCouldn't find that player!"));
                            return;
                        }else{
                            if(TeamManager.getInstance().getTeam(p).getMembers().size() == TeamManager.getInstance().getTeamSize()){
                                p.sendMessage(ChatUtils.message("&cYour team is already full!"));
                                return;
                            }else{
                                invites.put(target.getName(), TeamManager.getInstance().getTeam(p).getName());
                                p.sendMessage(ChatUtils.message("&eYou have invited &6" + target.getName() + " &eto your team!"));
                                target.sendMessage(ChatUtils.message("&eYou have been invited to &6" + p.getName()+ "'s &eteam. Do /team join " + p.getName() + " to join!"));
                                new BukkitRunnable(){

                                    @Override
                                    public void run() {
                                        if(invites.containsKey(target.getName())){
                                            invites.remove(target.getName());
                                            target.sendMessage(ChatUtils.message("&cYour team invite from &6" + p.getName() + " &chas expired!"));

                                        }

                                    }

                                }.runTaskLater(UHC.get(), 20*60);
                                return;
                            }
                        }
                    }else{
                        p.sendMessage(ChatUtils.message("&cTeam management is currently disabled!"));
                        return;
                    }
                }else if(args[0].equalsIgnoreCase("join")){
                    //Check if team man is on
                    if(TeamManager.getInstance().isTeamManagement()){
                        if(TeamManager.getInstance().getTeam(p) != null){
                            p.sendMessage(ChatUtils.message("&cYou are already on a team! Do /team leave to leave!"));
                            return;
                        }

                        Player target = Bukkit.getPlayerExact(args[1]);
                        if(target == null){
                            p.sendMessage(ChatUtils.message("&cCouldn't find that player!"));
                            return;
                        }

                        if(TeamManager.getInstance().getTeam(target) == null){
                            p.sendMessage(ChatUtils.message("&cThat player doesn't have a team!"));
                            return;
                        }

                        if(TeamManager.getInstance().getTeam(target).getMembers().size() == TeamManager.getInstance().getTeamSize()){
                            p.sendMessage(ChatUtils.message("&cThat team is already full!"));
                            return;
                        }

                        if(invites.containsKey(p.getName())){
                            if(invites.get(p.getName()).equalsIgnoreCase(TeamManager.getInstance().getTeam(target).getName())){
                                TeamManager.getInstance().getTeam(target).addMember(p);
                                Team team = TeamManager.getInstance().getTeam(target);
                                for (String str : team.getMembers()){
                                    Bukkit.getPlayer(str).sendMessage(ChatUtils.message("&6" + p.getName() + " &ehas joined the team!"));
                                }
                                invites.remove(p.getName());
                                return;
                            }else{
                                p.sendMessage(ChatUtils.message("&cThat team hasn't invited you!"));
                                return;
                            }
                        }else{
                            p.sendMessage(ChatUtils.message("&cYou haven't been invited to a team!"));
                            return;
                        }
                    }else{p.sendMessage(ChatUtils.message("&cTeam management is currently disabled!"));
                        return;
                    }

                }else if(args[0].equalsIgnoreCase("man")){
                    if(user.hasRank(Rank.HOST)){
                        if(args[1].equalsIgnoreCase("on")){
                            if(TeamManager.getInstance().isTeamManagement()){
                                p.sendMessage(ChatUtils.message("&cTeam Management is already enabled!"));
                                return;
                            }
                            TeamManager.getInstance().setTeamManagement(true);
                            p.sendMessage(ChatUtils.message("&eSuccessfully enabled team management!"));
                            return;
                        }else if(args[1].equalsIgnoreCase("off")){
                            if(!TeamManager.getInstance().isTeamManagement()){
                                p.sendMessage(ChatUtils.message("&cTeam management is already disabled!"));
                                return;
                            }
                            TeamManager.getInstance().setTeamManagement(false);
                            p.sendMessage(ChatUtils.message("&eSuccessfully disabled team management!"));
                            return;
                        }else{
                            sendAdminHelp(p);
                            return;
                        }
                    }else{
                        sendHelp(p);
                        return;
                    }
                }else if(args[0].equalsIgnoreCase("limit")){
                    if(user.hasRank(Rank.HOST)){
                        if(Util.isInt(args[1])){
                            TeamManager.getInstance().setTeamSize(Integer.parseInt(args[1]));
                            p.sendMessage(ChatUtils.message("&eSet the team size to " + args[1]));
                            return;
                        }else{
                            p.sendMessage(ChatUtils.message("&cThat is not a number!"));
                            return;
                        }
                    }else{
                        sendHelp(p);
                        return;
                    }
                }else if(args[0].equalsIgnoreCase("ff")){

                    if(!user.hasRank(Rank.TRIAL)){
                        p.sendMessage(ChatUtils.message("&cYou require the TRIAL rank to do this command!"));
                        return;
                    }else{
                        if(args[1].equalsIgnoreCase("on")){
                            TeamManager.getInstance().setTeamFriendlyFire(true);
                            p.sendMessage(ChatUtils.message("&eTeam friendly fire has been enabled!"));
                            return;
                        }else if(args[1].equalsIgnoreCase("off")){
                            TeamManager.getInstance().setTeamFriendlyFire(false);
                            p.sendMessage(ChatUtils.message("&eTeam friendly fire has been disabled!"));
                            return;
                        }else{
                            sendAdminHelp(p);
                            System.out.println("Sending help line 248");
                            return;
                        }
                    }
                }else if(args[0].equalsIgnoreCase("kick")){
                    if(TeamManager.getInstance().isTeamManagement()){
                        if(TeamManager.getInstance().getTeam(p) == null){
                            p.sendMessage(ChatUtils.message("&cYou are not on a team!"));
                            return;
                        }

                        Player target = Bukkit.getPlayer(args[1]);
                        if(target == null){
                            p.sendMessage(ChatUtils.message("&cCouldn't find that player!"));
                            return;
                        }

                        if(target.getName().equalsIgnoreCase(p.getName())){
                            p.sendMessage(ChatUtils.message("&cYou can't kick yourself from the team!"));
                            return;
                        }

                        if(TeamManager.getInstance().getTeam(p).getLeader().equalsIgnoreCase(p.getName())){
                            if(TeamManager.getInstance().getTeam(target) == TeamManager.getInstance().getTeam(p)){
                                TeamManager.getInstance().getTeam(p).removeMember(target);
                                p.sendMessage(ChatUtils.message("&eYou have kicked &6" + target.getName() + " &efrom the team!"));
                                for (String string : TeamManager.getInstance().getTeam(p).getMembers()){
                                    Bukkit.getPlayer(string).sendMessage(ChatUtils.message("&6" + p.getName() + " &ehas kicked &6" + target.getName() + " &efrom the team!"));
                                }
                                target.sendMessage(ChatUtils.message("&eYou have been kicked from the team!"));
                                return;
                            }else{
                                p.sendMessage(ChatUtils.message("&cThat player is not on your team!"));
                                return;
                            }
                        }else{
                            p.sendMessage(ChatUtils.message("&cYou are not the leader of the team!9"));
                            return;
                        }
                    }else{
                        p.sendMessage(ChatUtils.message("&cTeam management is currently disabled!"));
                        return;
                    }
                }
            }else if(args.length == 3){
                if(args[0].equalsIgnoreCase("set")){
                    // ./team set <player to add> <player to add to>
                    if(!user.hasRank(Rank.TRIAL)){
                        p.sendMessage(ChatUtils.message("&cYou require the TRIAL rank to do this command!"));
                        return;
                    }

                    Player target = Bukkit.getPlayer(args[1]);
                    if(target == null){
                        p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
                        return;
                    }


                    Team targetTeam = TeamManager.getInstance().getTeambyPlayerOnTeam(args[2]);
                    if(targetTeam == null){
                        p.sendMessage(ChatUtils.message("&cThe specified team couldn't be found! Make sure you spelled the target team right!"));
                        return;
                    }

                    targetTeam.addMember(target);
                    p.sendMessage(ChatUtils.message("&eYou have added &a" + target.getName() + " &eto &a" + args[2] + "'s &eteam!"));
                    target.sendMessage(ChatUtils.message("&eYou were added to &a" + args[2] + "'s &eteam!"));
                    targetTeam.message("&a" + target.getName() + " &ehas joined your team!");
                } else if (args[0].equalsIgnoreCase("list")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        Team targetTeam = TeamManager.getInstance().getTeambyPlayerOnTeam(args[1]);
                        if (targetTeam == null) {
                            p.sendMessage(ChatUtils.message("&cThat player is not on a team!"));
                            return;
                        }

                        for (String str : targetTeam.getMembers()) {
                            p.sendMessage(ChatColor.YELLOW + str + ",");
                        }
                    } else {
                        Team targetTeam = TeamManager.getInstance().getTeam(target);
                        if (targetTeam == null) {
                            p.sendMessage(ChatUtils.message("&cThat player is not on a team!"));
                            return;
                        }
                        for (String str : targetTeam.getMembers()) {
                            p.sendMessage(ChatColor.YELLOW + str + ",");
                        }

                    }
                }
            }
        }else{
            return;
        }
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return null;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }

    private String generateColor(){
        Random random = new Random();
        ArrayList<ChatColor> colors = new ArrayList<>(Arrays.asList(ChatColor.values()));
        colors.remove(ChatColor.MAGIC);
        colors.remove(ChatColor.RESET);
        Random random1 = new Random();
        int amt = random1.nextInt(2) + 1;
        if(amt == 1){
            return String.valueOf(colors.get(random.nextInt(colors.size())));
        }else {
            String first = String.valueOf(colors.get(random.nextInt(colors.size())));
            String second = String.valueOf(colors.get(random.nextInt(colors.size())));
            return first + second;
        }
    }

    private static <T> List<List<T>> splitList(List<T> list, int size) {
        Iterator<T> iterator = list.iterator();
        List<List<T>> returnList = new ArrayList<>();
        while (iterator.hasNext()) {
            List<T> tempList = new ArrayList<>();
            for (int i = 0; i< size; i++) {
                if (!iterator.hasNext()) break;
                tempList.add(iterator.next());
            }
            returnList.add(tempList);
        }
        return returnList;
    }


}
