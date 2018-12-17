package me.blok601.nightshadeuhc.gui.setup;

import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Blok on 12/15/2018.
 */
public class TimerGUI {

    public TimerGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, ChatUtils.format("&5Timer Setup"));

        ItemBuilder fHeal = new ItemBuilder(Material.PAPER).name(com.nightshadepvp.core.utils.ChatUtils.format("&eFinal Heal"));
        int finalHeal = GameManager.get().getFinalHealTime();
        if (finalHeal == 0 && GameState.getState() == GameState.WAITING) {
            fHeal.lore(com.nightshadepvp.core.utils.ChatUtils.format("&6Not set"));
        } else {
            fHeal.lore(com.nightshadepvp.core.utils.ChatUtils.format("&6" + GameManager.get().getFinalHealTime() / 60 + " minutes"));
        }
        fHeal.lore("&7(&6&oi&r&7) &6&oTime until Final Heal is given");

        ItemBuilder pTime = new ItemBuilder(Material.PAPER).name(com.nightshadepvp.core.utils.ChatUtils.format("&ePvP Time"));
        int pvpTime = GameManager.get().getPvpTime();
        if (pvpTime == 0 && GameState.getState() == GameState.WAITING) {
            pTime.lore(com.nightshadepvp.core.utils.ChatUtils.format("&6Not set"));
        } else {
            pTime.lore(com.nightshadepvp.core.utils.ChatUtils.format("&6" + GameManager.get().getPvpTime() / 60 + " minutes"));
        }
        pTime.lore("&7(&6&oi&r&7) &6&oTime until PvP is enabled");

        ItemBuilder bTime = new ItemBuilder(Material.PAPER).name(com.nightshadepvp.core.utils.ChatUtils.format("&eBorder Shrink Time"));
        int borderTime = GameManager.get().getBorderTime();
        if (borderTime == 0 && GameState.getState() == GameState.WAITING) {
            bTime.lore(com.nightshadepvp.core.utils.ChatUtils.format("&6Not set"));
        } else {
            bTime.lore(com.nightshadepvp.core.utils.ChatUtils.format("&6" + GameManager.get().getBorderTime() / 60 + " minutes"));
        }
        bTime.lore("&7(&6&oi&r&7) &6&oTime until first border shrink");

        ItemBuilder mTime = new ItemBuilder(Material.PAPER).name("&eMeetup Time");
        int meetup = GameManager.get().getMeetupTime();
        if (meetup == 0 && GameState.getState() == GameState.WAITING) {
            mTime.lore("&6Not Set");
        } else {
            mTime.lore("&6" + borderTime / 60 + " minutes");
        }
        bTime.lore("&7(&6&oi&r&7) &6&oTime until \"Meetup\" begins");

        ItemBuilder back = new ItemBuilder(Material.ARROW).name("&cBack");

        inventory.setItem(0, fHeal.make());
        inventory.setItem(1, pTime.make());
        inventory.setItem(2, bTime.make());
        inventory.setItem(3, mTime.make());
        inventory.setItem(8, back.make());

        player.openInventory(inventory);

    }
}
