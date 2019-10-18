package com.nightshadepvp.core.entity.objects;

import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Blok on 8/9/2018.
 */
public class Clan {

    private UUID leader;
    private UUID coLeader;
    private HashSet<UUID> officers;
    private HashSet<UUID> members;

    public Clan(UUID leader) {
        this.leader = leader;
        this.coLeader = null;
        this.officers = new HashSet<>();
        this.members = new HashSet<>();
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public ArrayList<UUID> getAllMembers() {
        ArrayList<UUID> toReturn = new ArrayList<>();
        toReturn.add(leader);
        if(coLeader != null) toReturn.add(coLeader);

        toReturn.addAll(this.officers);
        toReturn.addAll(this.members);
        return toReturn;
    }

    public UUID getCoLeader() {
        return coLeader;
    }

    public void setCoLeader(UUID coLeader) {
        this.coLeader = coLeader;
    }

    public HashSet<UUID> getOfficers() {
        return officers;
    }

    public void addOfficer(UUID uuid){
        this.officers.add(uuid);
    }

    public HashSet<UUID> getMembers() {
        return members;
    }

    public void addMember(UUID uuid){
        this.members.add(uuid);
    }

    public void msg(String message){
        NSPlayer nsPlayer;
        for (UUID uuid : getAllMembers()){
            nsPlayer = NSPlayer.get(uuid);
            if(nsPlayer.isOnline()){
                nsPlayer.msg(ChatUtils.format(message));
            }
        }
    }
}
