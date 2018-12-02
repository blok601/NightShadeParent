package me.blok601.nightshadeuhc.teams;

import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Blok on 9/24/2017.
 */
public class Team {

    private String name;
    private ArrayList<String> members;
    private String prefix;

    private UUID mole;

    private UUID melee;
    private UUID bow;

    public Team(String name, Player player) {
        this.name = name;
        this.members = new ArrayList<>();
        if(player != null){
            this.members.add(player.getName());
        }

    }

    public Team(String name){
        this.name = name;
        this.members = new ArrayList<>();
    }

    public Team(String name, ArrayList<String> members){
        this.name = name;
        this.members = members;
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return this.members.get(0);
    }

    public void message(String msg){
        Player player;
        for (String string : this.members){
            player = Bukkit.getPlayer(string);
            if(player == null) continue;

            player.sendMessage(ChatUtils.format("&3Team&8» &e" + msg));
        }
    }

    public void removeMember(Player player){
        if(this.members.contains(player.getName())) this.getMembers().remove(player.getName());
    }

    public void addMember(Player player){
        this.members.add(player.getName());
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public UUID getMole() {
        return mole;
    }

    public void setMole(UUID mole) {
        this.mole = mole;
    }

    public UUID getMelee() {
        return melee;
    }

    public void setMelee(UUID melee) {
        this.melee = melee;
    }

    public UUID getBow() {
        return bow;
    }

    public void setBow(UUID bow) {
        this.bow = bow;
    }
}
