package com.nightshadepvp.core.entity.objects;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.utils.PacketUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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
    GameProfile gameProfile;

    private JavaPlugin plugin;

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public FakePlayer(ItemStack[] armor, ItemStack itemInHand, Location loc, JavaPlugin plugin) {
        this.armor = armor;
        this.loc = loc;
        this.plugin = plugin;

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        this.gameProfile = new GameProfile(UUID.randomUUID(), "§9§lArena"); //UUID doesn't matter here since it will be changed w/ skin
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();

        this.entityPlayer = new EntityPlayer(nmsServer, world, gameProfile, new PlayerInteractManager(world)) {
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

        this.setSkin(player.getUniqueId());

        this.entityPlayer.setLocation(this.loc.getX(), this.loc.getY(), this.loc.getZ(), (loc.getPitch() * 256.0F / 360.0F), (loc.getYaw() * 256.0F / 360.0F));
        PacketUtils.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.entityPlayer));
        PacketUtils.sendPacket(player, new PacketPlayOutNamedEntitySpawn(this.entityPlayer));

        if (armor == null) {
            return;
        }

        for (int i = 0; i < armor.length; i++) {
            ItemStack thisOne = armor[i];

            if (thisOne == null) {
                continue;
            }

            PacketUtils.sendPacket(player, new PacketPlayOutEntityEquipment(entityPlayer.getId(), i + 1, CraftItemStack.asNMSCopy(thisOne)));
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
        PacketUtils.sendPacket(player, new PacketPlayOutEntityDestroy(entityPlayer.getId()));
    }

    private void hideName(Player to) {
        PacketUtils.sendPacket(to, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.entityPlayer)); //Should remove from tab

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

    public void setSkin(UUID skinId) {
        GameProfile skinProfile;
        if (Bukkit.getPlayer(skinId) != null) {
            skinProfile = PacketUtils.getHandle(Bukkit.getPlayer(skinId)).getProfile();
        } else {
            skinProfile = properties.getUnchecked(skinId);
        }
        if (skinProfile.getProperties().containsKey("textures")) {
            this.gameProfile.getProperties().removeAll("textures");
            this.gameProfile.getProperties().putAll("textures", skinProfile.getProperties().get("textures"));
        } else {
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "Skin with uuid not found: " + skinId);
        }
    }

    private final LoadingCache<UUID, GameProfile> properties = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(new CacheLoader<UUID, GameProfile>() {

                @Override
                public GameProfile load(UUID uuid) throws Exception {
                    return MinecraftServer.getServer().aD().fillProfileProperties(new GameProfile(uuid, null), true);
                }
            });
}