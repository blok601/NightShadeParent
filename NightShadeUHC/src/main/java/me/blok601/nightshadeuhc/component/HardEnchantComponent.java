package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class HardEnchantComponent extends Component {
    static ShapedRecipe l;

    public HardEnchantComponent() {
        super("Hard Enchant", Material.ENCHANTED_BOOK, false, "Toggle Extra diamonds for hard enchant");

    }
    public void onToggle() {
        ItemStack r = new ItemStack(Material.ENCHANTMENT_TABLE);
        ItemMeta p = r.getItemMeta();
        p.setDisplayName("Hard Enchant");
        r.setItemMeta(p);
        ShapedRecipe o = new ShapedRecipe(r);
        l = o;
        o.shape("DBD", "DOD", "OOO");
        o.setIngredient('D', Material.DIAMOND);
        o.setIngredient('B', Material.BOOK);
        o.setIngredient('O', Material.OBSIDIAN);
        Bukkit.getServer().addRecipe(o);
        l = o;
        Bukkit.getServer().addRecipe(l);


    }









    @EventHandler
    public void onCraft(CraftItemEvent e){
        if (!isEnabled()) return;
        if(e.getWhoClicked() == null) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getInventory() == null || e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)return;

        Player p = (Player) e.getWhoClicked();

        if(e.getRecipe().getResult().getType() == Material.ENCHANTMENT_TABLE){
            if (e.getRecipe().getResult().getItemMeta().getDisplayName().equals("Hard Enchant")) {
                return;
            }
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(ChatUtils.format("&fOops use more diamonds please, extra diamonds around the book"));

        }
    }
}
