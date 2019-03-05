package com.nightshadepvp.core.entity.objects;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
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
import org.bukkit.plugin.java.JavaPlugin;

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
    private final EntityPlayer entityPlayer;
    private Consumer<Player> whenClicked;

    private JavaPlugin plugin;

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public FakePlayer(ItemStack[] armor, ItemStack itemInHand, Location loc, JavaPlugin plugin) {
        this.armor = armor;
        this.loc = loc;
        this.plugin = plugin;

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        GameProfile profile = new GameProfile(UUID.fromString("818056f1-7402-487c-b117-934286ec4411"), "Toastinq");
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();

        this.entityPlayer = new EntityPlayer(nmsServer, world, profile, new PlayerInteractManager(world)) {
            @Override
            public boolean isSpectator() {
                return false;
            }
        };
        this.entityPlayer.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

        if (itemInHand == null) {
            return;
        }

        PlayerInventory inventory = this.entityPlayer.inventory;
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


        this.entityPlayer.setLocation(this.loc.getX(), this.loc.getY(), this.loc.getZ(), (loc.getPitch() * 256.0F / 360.0F), (loc.getYaw() * 256.0F / 360.0F));
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.entityPlayer));
        playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.entityPlayer));

        if (armor == null) {
            return;
        }

        for (int i = 0; i < armor.length; i++) {
            ItemStack thisOne = armor[i];

            if (thisOne == null) {
                continue;
            }

            playerConnection.sendPacket(new PacketPlayOutEntityEquipment(entityPlayer.getId(), i + 1, CraftItemStack.asNMSCopy(thisOne)));
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
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entityPlayer.getId()));
    }

    private void hideName(Player to) {
        PlayerConnection playerConnection = ((CraftPlayer) to).getHandle().playerConnection;
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.entityPlayer)); //Should remove from tab

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
        return entityPlayer.getId();
    }

    /**
     * Get the entity player of the NPC.
     *
     * @return The entity player.
     */
    public EntityHuman getEntityHuman() {
        return entityPlayer;
    }

    public void setWhenClicked(Consumer<Player> consumer) {
        this.whenClicked = consumer;
    }

    public void onClick(Player player) {
        this.whenClicked.accept(player);
    }
}