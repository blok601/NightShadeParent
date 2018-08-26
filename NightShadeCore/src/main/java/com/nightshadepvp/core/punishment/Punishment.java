package com.nightshadepvp.core.punishment;

import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 8/25/2018.
 */
public class Punishment extends AbstractPunishment {

    private String command;

    public Punishment(String name, ItemStack itemStack,  AbstractPunishment parent, String command){
        super(name, itemStack, parent.getPunishmentType());
        this.command = command;
        parent.addChild(this, 1);
    }

    public void execute(Player staff){
        if(!PunishmentHandler.getInstance().getPunishing().containsKey(staff)){
            return;
        }

        String name = PunishmentHandler.getInstance().getPunishing().get(staff);
        Player target = Bukkit.getPlayer(name);
        if(target == null){
            staff.sendMessage(ChatUtils.message("&eSuccessfully executed punishment."));
            staff.chat(command.replaceAll("%player%", name));
        }else{
            staff.sendMessage(ChatUtils.message("&eSuccessfully punished&8: " + target.getName()));
            target.damage(0); //Damage effect
            target.setHealth(0);
            staff.chat(command.replaceAll("%player%", target.getName()));
        }

        PunishmentHandler.getInstance().getPunishing().remove(staff);


    }


}
