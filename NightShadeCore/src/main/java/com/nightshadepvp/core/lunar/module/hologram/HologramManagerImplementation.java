package com.nightshadepvp.core.lunar.module.hologram;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.lunar.api.module.hologram.Hologram;
import com.nightshadepvp.core.lunar.api.module.hologram.HologramManager;
import com.nightshadepvp.core.lunar.listener.HologramListener;
import com.nightshadepvp.core.utils.BufferUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public class HologramManagerImplementation extends HologramManager {

    private Core plugin;

    public HologramManagerImplementation(Core plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new HologramListener(), plugin);
    }

    @Override
    public void createHologram(String name, Location location, String... lines) {
        if (this.getHologram(name) != null){
            return;
        }
        this.hologramList.add(new HologramImplementation(UUID.randomUUID(), name, location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld(), lines));
    }

    private class HologramImplementation extends Hologram {
        public HologramImplementation(UUID id, String name, int x, int y, int z, World world, String... lines) {
            super(id, name, x, y, z, world, lines);
        }

        @Override
        public void enable(Player player) throws IOException {
            if (!Core.get().getApi().isAuthenticated(player)){
                return;
            }

            if (this.enabled.contains(player.getUniqueId())){
                this.update(player);
                return;
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            os.write(4);
            os.write(BufferUtils.getBytesFromUUID(this.id));
            os.write(BufferUtils.writeDouble(this.x));
            os.write(BufferUtils.writeDouble(this.y));
            os.write(BufferUtils.writeDouble(this.z));

            os.write(BufferUtils.writeVarInt(this.lines.size()));
            for (String text : this.lines){
                os.write(BufferUtils.writeString(text));
            }

            os.close();

            player.sendPluginMessage(plugin, "Lunar-Client", os.toByteArray());

            this.enabled.add(player.getUniqueId());
        }

        @Override
        public void update(Player player) throws IOException {
            if (!Core.get().getApi().isAuthenticated(player)){
                return;
            }

            if (!this.enabled.contains(player.getUniqueId())){
                this.enable(player);
                return;
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            os.write(5);
            os.write(BufferUtils.getBytesFromUUID(this.id));

            os.write(BufferUtils.writeVarInt(this.lines.size()));
            for (String text : this.lines){
                os.write(BufferUtils.writeString(text));
            }

            os.close();

            player.sendPluginMessage(plugin, "Lunar-Client", os.toByteArray());
        }

        @Override
        public void disable(Player player) throws IOException {
            if (!Core.get().getApi().isAuthenticated(player)){
                return;
            }

            if (!this.enabled.contains(player.getUniqueId())){
                return;
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            os.write(6);
            os.write(BufferUtils.getBytesFromUUID(this.id));

            os.close();

            player.sendPluginMessage(plugin, "Lunar-Client", os.toByteArray());

            this.enabled.remove(player.getUniqueId());
        }
    }

}
