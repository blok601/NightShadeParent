package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import net.minecraft.server.v1_8_R3.TileEntityFurnace;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftFurnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

import java.lang.reflect.Field;

public class FastSmeltingScenario extends Scenario{

    public FastSmeltingScenario(){
        super("Fast Smelting", "Furnace cook time is 1 second instead of 10", new ItemBuilder(Material.FURNACE).name("&eFast Smelting").make());
    }

    @EventHandler
    public void on(FurnaceSmeltEvent event) {
        if(!isEnabled()) return;
        makeCookTime1Second((Furnace) event.getBlock().getState());
    }

    @EventHandler
    public void on(FurnaceBurnEvent event) {
        if(!isEnabled()) return;
        event.setBurnTime(event.getBurnTime() / 10);
        makeCookTime1Second((Furnace) event.getBlock().getState());
    }

    /**
     * Makes the cook time of the given furnace to 1 second instead of 10.
     *
     * @param furnace The furnace to modify.
     */
    private void makeCookTime1Second(Furnace furnace) {
        CraftFurnace craft = (CraftFurnace) furnace;
        TileEntityFurnace tile = craft.getTileEntity();

        try {
            Field cook = TileEntityFurnace.class.getDeclaredField("cookTimeTotal");
            cook.setAccessible(true);

            cook.set(tile, 20);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
