package me.blok601.nightshadeuhc.scenario;

import de.robingrether.idisguise.disguise.PlayerDisguise;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

/**
 * Created by Blok on 8/3/2018.
 */
public class AnonymousScenario extends Scenario {

    private static  String disuigse;

    public AnonymousScenario() {
        super("Anonymous", "Everyone is disguised as the same person", "Anon", new ItemBuilder(Material.NAME_TAG).name("Anonymous").make());
        disuigse = "Notch";
    }

    @EventHandler
    public void onGameStart(GameStartEvent e){
        if(!isEnabled()) return;
        assign();
    }

    public static String getDisuigse() {
        return disuigse;
    }

    public static void setDisuigse(String disuigse) {
        AnonymousScenario.disuigse = disuigse;
    }

    public static void assign(){
        UHCPlayerColl.get().getAllOnline().stream().filter(uhcPlayer -> !uhcPlayer.isSpectator()).forEach(uhcPlayer -> {
            UHC.getApi().disguise(uhcPlayer.getPlayer(), new PlayerDisguise(disuigse));
            uhcPlayer.msg(ChatUtils.format( "&4Anonymous&8» &eYou are now disguised as" + disuigse));
            uhcPlayer.setDisguised(true);
            uhcPlayer.setDisguisedName(disuigse);
        });
    }


}
