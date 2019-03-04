package com.nightshadepvp.core.entity.objects;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.nightshadepvp.core.packet.WrapperPlayServerEntityMetadata;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * NPC player class.
 *
 * @author Swedz
 */
@SuppressWarnings("unused")
public class FakePlayer {
    private final ItemStack[] armor;
    private final Location loc;
    private final EntityHuman entityHuman;
    private Consumer<Player> whenClicked;

    private JavaPlugin plugin;

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public FakePlayer(ItemStack[] armor, ItemStack itemInHand, Location loc, JavaPlugin plugin) {
        this.armor = armor;
        this.loc = loc;
        this.plugin = plugin;

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        GameProfile profile = new GameProfile(null, "Santa");
        this.entityHuman = new EntityHuman(world, profile) {
            @Override
            public boolean isSpectator() {
                return false;
            }
        };
        this.entityHuman.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

        if (itemInHand == null) {
            return;
        }

        PlayerInventory inventory = this.entityHuman.inventory;
        inventory.setItem(inventory.itemInHandIndex, CraftItemStack.asNMSCopy(itemInHand));

    }

    private Set<UUID> players = Sets.newHashSet();

    /**
     * Spawn this NPC player for the given player.
     *
     * @param player The player to spawn it for.
     */
    public void spawnFor(Player player) {
        if (player == null) {
            return;
        }

        this.players.add(player.getUniqueId());

        PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(entityHuman);

        PacketContainer packetContainer = PacketContainer.fromPacket(spawn);
        packetContainer.getSpecificModifier(UUID.class).write(0, player.getUniqueId());
        WrappedDataWatcher watcher;
////        if (this.getEntityHuman() != null) {
////            watcher = new WrappedDataWatcher(this.getEntityHuman().getBukkitEntity());
////        } else {
////            watcher = new WrappedDataWatcher();
////        }
////
////        watcher.setObject(8, WrappedDataWatcher.Registry.get(Integer.class), 0); //Hide potion effects
////        watcher.setObject(13, WrappedDataWatcher.Registry.get(Byte.class), (byte) 127); // Shows 3D skin parts/capes
//        packetContainer.getDataWatcherModifier().write(0, watcher);

        CraftPlayer craftPlayer = (CraftPlayer) player;
        try {
            protocolManager.sendServerPacket(player, packetContainer);
            craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutEntityHeadRotation(entityHuman, (byte) (int) (loc.getYaw() * 256.0F / 360.0F)));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        if (armor == null) {
            return;
        }

        for (int i = 0; i < armor.length; i++) {
            ItemStack thisOne = armor[i];

            if (thisOne == null) {
                continue;
            }

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(entityHuman.getId(), i + 1, CraftItemStack.asNMSCopy(thisOne)));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                hideName(player);
            }
        }.runTaskLater(plugin, 20L);

    }

    /**
     * Despawn this NPC player for the given player.
     *
     * @param player The player to despawn it for.
     */
    public void despawnFor(Player player) {
        if (player == null) {
            return;
        }

        this.players.remove(player.getUniqueId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entityHuman.getId()));
    }

    private void hideName(Player to) {
        WrapperPlayServerEntityMetadata metadataWrapper = new WrapperPlayServerEntityMetadata();
        metadataWrapper.setEntityID(this.getEntityID());

        WrappedDataWatcher metaWatcher = new WrappedDataWatcher();

        metaWatcher.setObject(2, WrappedDataWatcher.Registry.get(String.class), "");
        metaWatcher.setObject(3, WrappedDataWatcher.Registry.get(Boolean.class), false, false);

        metadataWrapper.setMetadata(metaWatcher.getWatchableObjects());
        try {
            protocolManager.sendServerPacket(to, metadataWrapper.getHandle());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the given player can see this NPC.
     *
     * @param player The player to check.
     * @return True if he can, false otherwise.
     */
    public boolean canSee(Player player) {
        return players.contains(player.getUniqueId());
    }

    /**
     * Get the location of this NPC.
     *
     * @return The location.
     */
    public Location getLocation() {
        return loc;
    }

    /**
     * Get the entity id of the NPC.
     *
     * @return The entity id.
     */
    public int getEntityID() {
        return entityHuman.getId();
    }

    /**
     * Get the entity player of the NPC.
     *
     * @return The entity player.
     */
    public EntityHuman getEntityHuman() {
        return entityHuman;
    }

    public void setWhenClicked(Consumer<Player> consumer) {
        this.whenClicked = consumer;
    }

    public void onClick(Player player) {
        this.whenClicked.accept(player);
    }
}