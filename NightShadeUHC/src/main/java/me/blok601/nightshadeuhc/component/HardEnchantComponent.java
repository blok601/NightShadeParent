package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class HardEnchantComponent extends Component {
    private ShapedRecipe shapedRecipe;

    public HardEnchantComponent() {
        super("Hard Enchant", Material.ENCHANTED_BOOK, false, "Toggle Extra diamonds for hard enchant");

    }
    public void onToggle() {
        if (isEnabled()) {
            ItemStack r = new ItemStack(Material.ENCHANTMENT_TABLE);
            ItemMeta itemMeta = r.getItemMeta();
            itemMeta.setDisplayName("Hard Enchant");
            r.setItemMeta(itemMeta);
            ShapedRecipe o = new ShapedRecipe(r);
            this.shapedRecipe = o;
            o.shape("DBD", "DOD", "OOO");
            o.setIngredient('D', Material.DIAMOND);
            o.setIngredient('B', Material.BOOK);
            o.setIngredient('O', Material.OBSIDIAN);
            Bukkit.getServer().addRecipe(o);
            this.shapedRecipe = o;
            Bukkit.getServer().addRecipe(this.shapedRecipe);
        } else {
            if (this.shapedRecipe != null) {
                this.shapedRecipe.getResult().setType(Material.AIR); //Remove the recipe
            }
        }

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
