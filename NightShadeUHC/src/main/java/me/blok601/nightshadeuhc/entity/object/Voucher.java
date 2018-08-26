package me.blok601.nightshadeuhc.entity.object;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.objects.PlayerColor;
import com.nightshadepvp.core.entity.objects.PlayerEffect;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Blok on 7/10/2018.
 */
public class Voucher {

    private String name;
    private VoucherType type;
    private Rank rank;
    private PlayerColor playerColor;
    private PlayerEffect playerEffect;
    private int XP;


    public Voucher(String name, VoucherType type) {
        this.name = name;
        this.type = type;
    }

    public VoucherType getType() {
        return type;
    }

    public void setType(VoucherType type) {
        this.type = type;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public PlayerEffect getPlayerEffect() {
        return playerEffect;
    }

    public void setPlayerEffect(PlayerEffect playerEffect) {
        this.playerEffect = playerEffect;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public ItemBuilder getItem(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER);
        itemBuilder.lore("&eRight click with this voucher to redeem your reward!");
        if(type == VoucherType.COLOR){
            itemBuilder.name(getPlayerColor().getColor() + "1x " + getPlayerColor().toString() + " Player Color");
        }else if(type == VoucherType.EFFECT){
            itemBuilder.name("&a1x " + getPlayerEffect().getName() + " Player Effect");
        }else if(type == VoucherType.RANK){
            itemBuilder.name(getRank().getNameColor() + "1x " + getRank().getPrefix() + " Rank");
        }else if(type == VoucherType.XP){
            itemBuilder.name("&a" + getXP() + " XP Points");
        }

        return itemBuilder;
    }

}
