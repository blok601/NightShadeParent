package me.blok601.nightshadeuhc.staff.cmd;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 7/28/2017.
 */
public class AlertsCommand implements CmdInterface{


    @Override
    public String[] getNames() {
        return new String[]{
                "alerts"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        openGUI(p);

    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIAL;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }

    private void openGUI(Player player){
        UHCPlayer uhcPlayer = UHCPlayer.get(player);
        NSPlayer nsPlayer = NSPlayer.get(player);
        Inventory inv = Bukkit.createInventory(null, 9, "Alert Toggles");



        ItemStack specInfo = new ItemBuilder(Material.TORCH).name(ChatUtils.format("&5Mining Alerts")).lore(uhcPlayer.isReceivingSpectatorInfo() ? ChatUtils.format("&aEnabled") : ChatUtils.format("&cDisabled")).make();
        ItemStack toggleSneakAlerts = new ItemBuilder(Material.SIGN).name("&5Toggle Sneak Alerts").lore(nsPlayer.isReceivingToggleSneak() ? "&aEnabled" : "&cDisabled").make();
        ItemStack toggleStaffChat = new ItemBuilder(Material.PAPER).name("&5Toggle Staff Chat").lore(nsPlayer.isReceivingStaffChat() ? "&aEnabled" : "&cDisabled").make();
        ItemStack cmdSpy = new ItemBuilder(Material.COMPASS).name("&5Command Spy").lore(uhcPlayer.isReceivingCommandSpy() ? "&aEnabled" : "&cDisabled").make();
        ItemStack toggleOresFound = new ItemBuilder(Material.DIAMOND_ORE).name("&5Toggle Mining Alerts").lore(uhcPlayer.isReceivingMiningAlerts() ? "&aEnabled" : "&cDisabled").make();
        ItemStack toggleAntiCheatAlerts = new ItemBuilder(Material.DIAMOND_SWORD).name("&5NightCheat Alerts").lore("&4&lComing soon...").make();

        inv.addItem(specInfo);
        inv.addItem(toggleSneakAlerts);
        inv.addItem(toggleStaffChat);
        inv.addItem(cmdSpy);
        inv.addItem(toggleOresFound);
        inv.addItem(toggleAntiCheatAlerts);

        player.openInventory(inv);
    }
}
