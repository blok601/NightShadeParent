package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.utils.ChatUtils;
import litebans.api.Database;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdUnbanAll extends NightShadeCoreCommand{

    private static CmdUnbanAll i = new CmdUnbanAll();

    public static CmdUnbanAll get() {
        return i;
    }

    public CmdUnbanAll() {

        this.setAliases("unbanall");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.OWNER));
    }

    @Override
    public void perform() throws MassiveException {
        ArrayList<UUID> uuids = new ArrayList<>();
        sender.sendMessage(ChatUtils.message("&eStarting to unban all users"));
        new BukkitRunnable(){
            @Override
            public void run() {
                String query = "SELECT * FROM {bans} WHERE active=1";
                try(PreparedStatement statement = Database.get().prepareStatement(query)){
                    try(ResultSet set = statement.executeQuery()){
                        while(set.next()){
                            uuids.add(UUID.fromString(set.getString("uuid")));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                int count = uuids.size();

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        if(uuids.size() == 0){
                            Bukkit.broadcastMessage(ChatUtils.format("&eThe Unban All has completed!" + count + " users were pardoned!"));
                            this.cancel();
                            return;
                        }

                        UUID uuid = uuids.get(0);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unban " + uuid.toString() + " NightShadePvP is Revamping! -s");
                        Bukkit.broadcastMessage(ChatUtils.message("&a" + Bukkit.getOfflinePlayer(uuid).getName() + " &ehas been unbanned!"));
                        uuids.remove(uuid);
                    }
                }.runTaskTimer(Core.get(), 0, 5);
            }
        }.runTaskAsynchronously(Core.get());

    }
}
