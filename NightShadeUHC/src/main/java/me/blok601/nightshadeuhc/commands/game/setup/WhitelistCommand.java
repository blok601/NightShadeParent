package me.blok601.nightshadeuhc.commands.game.setup;


import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/21/2017.
 */
public class WhitelistCommand implements UHCCommand {

    @Override
    public String[] getNames() {
        return new String[]{
                "whitelist"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        NSPlayer user = NSPlayer.get(p.getUniqueId());
        if(args.length == 0){
            if(user.getRank().getValue() > Rank.YOUTUBE.getValue()){
                //Staff member
                sendHelp(p, true);
            }else{
                sendHelp(p, false);
            }
        }

        if(args.length == 1){
            //List
            //Clear
            if(user.hasRank(Rank.TRIAL)){
               if(args[0].equalsIgnoreCase("list")){
                   StringBuilder builder = new StringBuilder();
                   for (String string : GameManager.get().getWhitelist()){
                       builder.append("&6").append(string).append(",");
                   }
                   p.sendMessage(ChatUtils.message("&eWhitelisted players:"));
                   p.sendMessage(ChatUtils.format(builder.toString()));
               }else if(args[0].equalsIgnoreCase("clear")){
                   GameManager.get().setWhitelistEnabled(false);
                   GameManager.get().getWhitelist().clear();
                   p.sendMessage(ChatUtils.message("&eThe whitelist has been cleared!"));
               }else if(args[0].equalsIgnoreCase("on")){
                   if(GameManager.get().isWhitelistEnabled()){
                       p.sendMessage(ChatUtils.message("&cThe whitelist is already enabled!"));
                       return;
                   }

                   GameManager.get().setWhitelistEnabled(true);
                   p.sendMessage(ChatUtils.message("&eThe whitelist has been enabled!"));
               }else if(args[0].equalsIgnoreCase("off")){
                   if(!GameManager.get().isWhitelistEnabled()){
                       p.sendMessage(ChatUtils.message("&cThe whitelist is already disabled!"));
                       return;
                   }

                   GameManager.get().setWhitelistEnabled(false);
                   p.sendMessage(ChatUtils.message("&eThe whitelist has been disabled!"));
               }else{
                   sendHelp(p, true);
               }
            }else{
                sendHelp(p, false);
            }
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("add")){
                // They did /wl add arg

                String name = args[1];

                if(name.equalsIgnoreCase("all") || name.equalsIgnoreCase("*")){
                    if(!user.hasRank(Rank.TRIAL)){
                        p.sendMessage(ChatUtils.message("&cYour rank only allows adding players one at a time!"));
                        return;
                    }

                    for (Player player : Bukkit.getOnlinePlayers()){
                        if (!GameManager.get().getWhitelist().contains(player.getName().toLowerCase()))  {
                            GameManager.get().getWhitelist().add(player.getName().toLowerCase());
                        }
                    }

                    p.sendMessage(ChatUtils.message("&eSuccessfully added everyone to the whitelist!"));
                    return;
                }

                if(GameManager.get().getWhitelist().contains(name.toLowerCase())){
                    p.sendMessage(ChatUtils.message("&e" + name  + " &cis already whitelisted!"));
                    return;
                }

                GameManager.get().getWhitelist().add(name.toLowerCase());
                p.sendMessage(ChatUtils.message("&e" + name + " &ahas been whitelisted!"));
            }else if(args[0].equalsIgnoreCase("remove")){
                if(user.hasRank(Rank.TRIAL)){
                    String target = args[1];
                    if(!GameManager.get().getWhitelist().contains(target.toLowerCase())){
                        p.sendMessage(ChatUtils.message("&cThat player isn't on the whitelist!"));
                        return;
                    }

                    if(target.equalsIgnoreCase("all") || target.equalsIgnoreCase("*")){
                        GameManager.get().getWhitelist().clear();
                        p.sendMessage(ChatUtils.message("&eThe whitelist has been cleared!"));
                        return;
                    }

                    GameManager.get().getWhitelist().remove(target.toLowerCase());
                    p.sendMessage(ChatUtils.message("&e" + target + " &ahas been removed from the whitelist!"));
                    return;
                }else{
                    p.sendMessage(ChatUtils.message("&cYour rank only allows adding players to the whitelist!"));
                    return;
                }
            }else if(args[0].equalsIgnoreCase("off")){
                //They did /wl off time

            }
        }
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.YOUTUBE;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }

    private void sendHelp(Player player, boolean staff){

        if(!staff){
            player.sendMessage(ChatUtils.message("&e/wl add <player>"));
            return;
        }

        player.sendMessage(ChatUtils.message("&aWhitelist Help:"));
        player.sendMessage(ChatUtils.format("&e/wl add [player/*] - Add everyone or a single player"));
        player.sendMessage(ChatUtils.format("&e/wl remove [player/*] - Remove a single player or everyone"));
        player.sendMessage(ChatUtils.format("&e/wl clear - Turns whitelist off and removes everyone")); //DONE
        player.sendMessage(ChatUtils.format("&e/wl list - Lists everyone on the whitelist")); //DONE
        player.sendMessage(ChatUtils.format("&e/wl on/off - Toggles whitelist")); //DONE
    }
}
