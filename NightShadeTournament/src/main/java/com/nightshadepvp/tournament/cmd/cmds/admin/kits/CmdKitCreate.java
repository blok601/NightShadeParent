package com.nightshadepvp.tournament.cmd.cmds.admin.kits;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.utils.ItemBuilder;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.KitHandler;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdKitCreate extends NightShadeTournamentCommand {

    public CmdKitCreate() {

        this.addAliases("create");

        this.addParameter(TypeString.get(), "kitname");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.addRequirements(RequirementIsPlayer.get());
        this.setVisibility(Visibility.SECRET);

    }


    @Override
    public void perform() throws MassiveException {
        Player p = TPlayer.get(sender).getPlayer();
        String name = this.readArg();

        if(KitHandler.getInstance().isKit(name)){
            p.sendMessage(ChatUtils.message("&cThat kit has already been created!"));
            return;
        }

        Kit kit = new Kit(name);
        kit.setItems(p.getInventory().getContents());
        kit.setArmor(p.getInventory().getArmorContents());
        kit.setDisplay(new ItemBuilder(Material.DIAMOND_SWORD).name("&6" + name).make());
        KitHandler.getInstance().getKits().add(kit);
        p.sendMessage(ChatUtils.message("&bSuccessfully created kit: &f" + kit.getName()));
        p.sendMessage(ChatUtils.message("&bMake sure to set the display item and reload the kits!"));
        return;
    }
}
