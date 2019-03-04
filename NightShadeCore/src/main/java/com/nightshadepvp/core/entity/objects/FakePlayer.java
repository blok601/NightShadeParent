package com.nightshadepvp.core.entity.objects;

import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    public FakePlayer(ItemStack[] armor, ItemStack itemInHand, Location loc) {
        this.armor = armor;
        this.loc = loc;

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
        PacketContainer.fromPacket(spawn).getSpecificModifier(UUID.class).write(0, player.getUniqueId());
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(spawn);
        craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutEntityHeadRotation(entityHuman, (byte) (int) (loc.getYaw() * 256.0F / 360.0F)));


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

    public void hideName(){
        this.entityHuman.setCustomName("Test");
        this.entityHuman.setCustomNameVisible(true);
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