package me.blok601.nightshadeuhc.command.player.teams;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Blok on 5/28/2018.
 */
public class RvBCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "rvb"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
//        Player p = (Player) s;
//        if(args.length == 0){
//            p.sendMessage(ChatUtils.message("cR&ev&9b &eCommands:"));
//            p.sendMessage(ChatUtils.format("&8- &e/rvb enable - Sets the game type to red vs blue"));
//            p.sendMessage(ChatUtils.format("&8- &e/rvb assign - Assigns the teams and sets colors"));
//            p.sendMessage(ChatUtils.format("&8- &e/rvb scattertype <solo/team>"));
//            p.sendMessage(ChatUtils.format("&8- &e/rvb winner <red/blue> - Sets the winner and gives ranks"));
//            return;
//        }
//
//        if(args.length == 1){
//            if(args[0].equalsIgnoreCase("enable")){
//                TeamManager.getInstance().setRvB(true);
//                TeamManager.getInstance().setTeamFriendlyFire(false);
//                TeamManager.getInstance().setTeamManagement(false);
//                TeamManager.getInstance().setTeamSize(-1);
//                GameManager.get().setIsTeam(true);
//
////                GameManager.get().getScoreboard().getObjective("board").getScore(ChatColor.DARK_RED.toString()).setScore(0);
////                GameManager.get().getScoreboard().getObjective("board").getScore(ChatColor.BLUE.toString()).setScore(1);
////                GameManager.get().getScoreboard().getObjective("board").getScore(ChatColor.DARK_GRAY.toString()).setScore(-1);
////                GameManager.get().getScoreboard().getTeam("players").unregister();
//
//                p.sendMessage(ChatUtils.message("&eYou have enabled RvB! Make sure to assign teams and scatter!"));
//                return;
//            }else if(args[0].equalsIgnoreCase("assign")){
//                p.sendMessage(ChatUtils.message("&eRvB teams will be assigned in 5 seconds. Make sure all spectators are spectator mode so they are not assigned a team!"));
//                new BukkitRunnable(){
//                    int counter = 5;
//                    @Override
//                    public void run() {
//                        if(counter == 0){
//                            Team red = new Team("RED");
//                            Team blue = new Team("BLUE");
//
//                            int i = 0;
//                            UHCPlayer uhcPlayer;
//                            for (Player player : Bukkit.getOnlinePlayers()){
//                                uhcPlayer = UHCPlayer.get(player);
//                                if(!uhcPlayer.isOnline()) continue;
//                                if(uhcPlayer.isSpectator()) continue;
//                                if(i%2 == 0){
//                                    red.addMember(player);
//                                    player.sendMessage(ChatUtils.message("&cYou are now on the red team!"));
//                                }else{
//                                    blue.addMember(player);
//                                    player.sendMessage(ChatUtils.message("&9You are now on the blue team!"));
//                                }
//
//                                i++;
//                            }
//
//                            org.bukkit.scoreboard.Team redTeam = GameManager.get().getScoreboard().registerNewTeam(red.getName());
//                            org.bukkit.scoreboard.Team blueTeam = GameManager.get().getScoreboard().registerNewTeam(blue.getName());
//
//                            redTeam.setPrefix(ChatUtils.format("&c"));
//                            blueTeam.setPrefix(ChatUtils.format("&9"));
//                            TeamManager.getInstance().addTeam(red);
//                            TeamManager.getInstance().addTeam(blue);
//
//                            /*
//                            Color the teams
//                             */
//                            Player temp;
//                            for (String mem : red.getMembers()){
//                                temp = Bukkit.getPlayer(mem);
//                                redTeam.addEntry(temp.getName());
//                            }
//
//                            for (String mem : blue.getMembers()){
//                                temp = Bukkit.getPlayer(mem);
//                                blueTeam.addEntry(temp.getName());
//                            }
//
//                            p.sendMessage(ChatUtils.message("&eRvB teams have been assigned!"));
//                            this.cancel();
//                            return;
//                        }
//                        p.sendMessage(ChatUtils.message("&eAssigning RvB teams in..." + counter));
//                        counter--;
//                    }
//                }.runTaskTimer(UHC.get(), 0, 20);
//
//            }
//        }else if(args.length == 2){
//            if(args[0].equalsIgnoreCase("scattertype")){
//                if(args[1].equalsIgnoreCase("solo")){
//                    TeamManager.getInstance().setRvBScatterType(0);
//                    p.sendMessage(ChatUtils.message("&eSet the RvB scatter type to solo!"));
//                    return;
//                }else if(args[1].equalsIgnoreCase("team") || args[1].equalsIgnoreCase("teams")){
//                    TeamManager.getInstance().setRvBScatterType(1);
//                    p.sendMessage(ChatUtils.message("&eSet the RvB scatter type to solo!"));
//                    return;
//                }else{
//                    p.sendMessage(ChatUtils.format("&8- &e/rvb enable - Sets the game type to red vs blue"));
//                    p.sendMessage(ChatUtils.format("&8- &e/rvb assign - Assigns the teams and sets colors"));
//                    p.sendMessage(ChatUtils.format("&8- &e/rvb scattertype <solo/team>"));
//                    p.sendMessage(ChatUtils.format("&8- &e/rvb winner <red/blue> - Sets the winner and gives ranks"));
//                }
//            }else if(args[0].equalsIgnoreCase("winner")){
//                if(args[1].equalsIgnoreCase("red")){
//                    Player player;
//                    NSPlayer user;
//                    UHCPlayer gamePlayer;
//                    Team redTeam = TeamManager.getInstance().getTeam("RED");
//
//                    for (String mem : redTeam.getMembers()){
//                        player = Bukkit.getPlayer(mem);
//                        if(player == null) continue;
//
//                        user = NSPlayer.get(player.getUniqueId());
//                        if(user == null) continue;
//                        gamePlayer = UHCPlayer.get(player.getUniqueId());
//                        if(gamePlayer == null) continue;
//                        user.setPrefix(ChatColor.RED + "[Winner] ");
//                        gamePlayer.setGamesWon(gamePlayer.getGamesWon() + 1);
//                        gamePlayer.changed();
//                        user.changed();
//                    }
//
//                    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Congratulations!\",\"color\":\"red\",\"bold\":true}"), 0, 60, 0);
//                    redTeam.getMembers().stream().filter(s1 -> Bukkit.getPlayer(s1) != null).forEach(s1 ->{
//                        ((CraftPlayer) Bukkit.getPlayer(s1)).getHandle().playerConnection.sendPacket(title);
//                    });
//
//                    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Red has won!\",\"color\":\"red\"}"), 0, 60 ,0);
//                    PacketPlayOutTitle newTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Game Over!\",\"color\":\"dark_aqua\",\"bold\":true}"), 0, 60, 0);
//
//                    Bukkit.getOnlinePlayers().forEach((Consumer<Player>) lamba ->{
//                        ((CraftPlayer) lamba).getHandle().playerConnection.sendPacket(subtitle);
//                        if(!redTeam.getMembers().contains(lamba.getName())){
//                            ((CraftPlayer) lamba).getHandle().playerConnection.sendPacket(newTitle);
//                        }
//
//                    });
//
//                    Util.staffLog("&4The server will restart in 15 seconds!");
//
//                    new BukkitRunnable(){
//                        @Override
//                        public void run() {
//                            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.teleport(MConf.get().getSpawnLocation().asBukkitLocation(true)));
//                            Bukkit.unloadWorld(GameManager.get().getWorld(), false);
//                            Util.deleteWorldFolder(GameManager.get().getWorld());
//                        }
//                    }.runTaskLater(UHC.get(), 20*15);
//
//                    new BukkitRunnable(){
//                        @Override
//                        public void run() {
//                            Bukkit.getOnlinePlayers().stream().filter(o -> !NSPlayer.get(o.getUniqueId()).hasRank(Rank.TRIAL)).forEach(o -> ProxyUtil.sendToServer(o, "lobby"));
//                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spigot:restart");
//                        }
//                    }.runTaskLater(UHC.get(), 30*20);
//
//
//
//                }else if(args[1].equalsIgnoreCase("blue")){
//                    Player player;
//                    NSPlayer user;
//                    UHCPlayer gamePlayer;
//                    Team blueTeam = TeamManager.getInstance().getTeam("BLUE");
//
//                    for (String mem : blueTeam.getMembers()){
//                        player = Bukkit.getPlayer(mem);
//                        if(player == null) continue;
//
//                        user = NSPlayer.get(player.getUniqueId());
//                        if(user == null) continue;
//                        gamePlayer = UHCPlayer.get(player.getUniqueId());
//                        if(gamePlayer == null) continue;
//                        user.setPrefix(ChatColor.RED + "[Winner] ");
//                        gamePlayer.setGamesWon(gamePlayer.getGamesWon() + 1);
//                        gamePlayer.changed();
//                        user.changed();
//                    }
//
//                    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Congratulations!\",\"color\":\"blue\",\"bold\":true}"), 0, 60, 0);
//                    blueTeam.getMembers().stream().filter(s1 -> Bukkit.getPlayer(s1) != null).forEach(s1 ->{
//                        ((CraftPlayer) Bukkit.getPlayer(s1)).getHandle().playerConnection.sendPacket(title);
//                    });
//
//                    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Blue has won!\",\"color\":\"blue\"}"), 0, 60 ,0);
//                    PacketPlayOutTitle newTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Game Over!\",\"color\":\"dark_aqua\",\"bold\":true}"), 0, 60, 0);
//
//                    Bukkit.getOnlinePlayers().forEach((Consumer<Player>) lamba ->{
//                        ((CraftPlayer) lamba).getHandle().playerConnection.sendPacket(subtitle);
//                        if(!blueTeam.getMembers().contains(lamba.getName())){
//                            ((CraftPlayer) lamba).getHandle().playerConnection.sendPacket(newTitle);
//                        }
//
//                    });
//
//                    Util.staffLog("&4The server will restart in 15 seconds!");
//
//                    new BukkitRunnable(){
//                        @Override
//                        public void run() {
//                            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.teleport(MConf.get().getSpawnLocation().asBukkitLocation(true)));
//                            Bukkit.unloadWorld(GameManager.get().getWorld(), false);
//                            Util.deleteWorldFolder(GameManager.get().getWorld());
//                        }
//                    }.runTaskLater(UHC.get(), 20*15);
//
//                    new BukkitRunnable(){
//                        @Override
//                        public void run() {
//                            Bukkit.getOnlinePlayers().stream().filter(o -> !NSPlayer.get(o.getUniqueId()).hasRank(Rank.TRIAL)).forEach(o -> ProxyUtil.sendToServer(o, "lobby"));
//                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spigot:restart");
//                        }
//                    }.runTaskLater(UHC.get(), 30*20);
//
//
//                }else{
//                    p.sendMessage(ChatUtils.format("&8- &e/rvb enable - Sets the game type to red vs blue"));
//                    p.sendMessage(ChatUtils.format("&8- &e/rvb assign - Assigns the teams and sets colors"));
//                    p.sendMessage(ChatUtils.format("&8- &e/rvb scattertype <solo/team>"));
//                    p.sendMessage(ChatUtils.format("&8- &e/rvb winner <red/blue> - Sets the winner and gives ranks"));
//                }
//            }
//        }

    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.ADMIN;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
