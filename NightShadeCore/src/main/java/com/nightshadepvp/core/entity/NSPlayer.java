package com.nightshadepvp.core.entity;

import com.google.common.collect.Sets;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.objects.Friend;
import com.nightshadepvp.core.entity.objects.PlayerColor;
import com.nightshadepvp.core.entity.objects.PlayerEffect;
import com.nightshadepvp.core.entity.objects.PlayerTag;

import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

public class NSPlayer extends SenderEntity<NSPlayer> {
    // -------------------------------------------- //
    // META
    // -------------------------------------------- //

    public static NSPlayer get(Object oid) {
        return NSPlayerColl.get().get(oid);
    }

    // -------------------------------------------- //
    // LOAD
    // -------------------------------------------- //

    private Rank rank = Rank.PLAYER;
    private String prefix = "";

    private PlayerColor color = PlayerColor.DEFAULT;
    private MassiveList<PlayerColor> colors = new MassiveList<>(Collections.singletonList(PlayerColor.DEFAULT));

    private boolean particleSwitch = false;
    private PlayerEffect effect = PlayerEffect.NONE;
    private MassiveList<PlayerEffect> effects = new MassiveList<>(Collections.singletonList(PlayerEffect.NONE));

    private PlayerTag currentTag = PlayerTag.DEFAULT;
    private MassiveList<PlayerTag> unlockedTags = new MassiveList<>(Collections.singletonList(PlayerTag.DEFAULT));

    private boolean messageSounds = true;

    private long discordID = -1;

    private int coins = 0;

    private byte[] adminPassword = null;

    private HashSet<Friend> friends = Sets.newHashSet();
    private HashSet<UUID> outGoingFriendRequests = Sets.newHashSet();

    private boolean hasReferred = false;

    private transient boolean frozen = false;
    private transient boolean receivingPMs = true;
    private transient NSPlayer lastMessaged = null;

    // 10 second threshold for being AFK
    private transient boolean afk = false;
    private transient int totalAFKTime = 0;
    private transient int currentAFKTime = 0;

    private int spectatingTime = 0;

    // ----------------------------------
    //         Staff Toggles
    // ----------------------------------

    private transient boolean inStaffChat = false;
    private transient boolean receivingStaffChat = true;
    private transient int toggleSneakVL = 0;
    private transient boolean receivingToggleSneak = true;
    private transient boolean loggedIn = false;


    @Override
    public NSPlayer load(NSPlayer that) {
        this.setLastActivityMillis(that.lastActivityMillis);

        this.setRank(that.rank);
        this.setPrefix(that.prefix);

        this.setColor(that.color);
        this.setColors(that.colors);

        this.setParticleSwitch(that.particleSwitch);
        this.setEffect(that.effect);
        this.setEffects(that.effects);

        this.setCurrentTag(that.currentTag);
        this.setUnlockedTags(that.unlockedTags);

        this.setDiscordID(that.discordID);
        this.setCoins(that.coins);

        this.setMessageSounds(that.messageSounds);

        this.setSpectatingTime(that.spectatingTime);
        this.setAdminPassword(that.adminPassword);

        this.setFriends(that.friends);
        this.setOutGoingFriendRequests(that.outGoingFriendRequests);

        this.setHasReferred(that.hasReferred);


        return this;
    }

    public Rank getRank() {
        return rank;
    }


    public boolean hasRank(Rank rank) {
        return getRank().getValue() >= rank.getValue();
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void updateRank(Rank rank) {
        this.rank = rank;
        this.changed();
    }

    public int getCoins() {
        return coins;
    }

    public int setCoins(int coins) {
        this.coins = coins;
        return coins;
    }

    public int alterCoins(int change) {
        return setCoins(this.coins + change);
    }

    public String getPrefix() {
        return prefix.replaceAll("_", " ");
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public MassiveList<PlayerColor> getColors() {
        return colors;
    }

    public void setColors(MassiveList<PlayerColor> colors) {
        this.colors = colors;
    }

    public boolean isParticleSwitch() {
        return particleSwitch;
    }

    public void setParticleSwitch(boolean particleSwitch) {
        this.particleSwitch = particleSwitch;
    }

    public PlayerEffect getEffect() {
        return effect;
    }

    public void setEffect(PlayerEffect effect) {
        this.effect = effect;
    }

    public MassiveList<PlayerEffect> getEffects() {
        return effects;
    }

    public void setEffects(MassiveList<PlayerEffect> effects) {
        this.effects = effects;
    }

    public PlayerTag getCurrentTag() {
        return currentTag;
    }

    public void setCurrentTag(PlayerTag currentTag) {
        this.currentTag = currentTag;
    }

    public MassiveList<PlayerTag> getUnlockedTags() {
        return unlockedTags;
    }

    public void setUnlockedTags(MassiveList<PlayerTag> unlockedTags) {
        this.unlockedTags = unlockedTags;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isReceivingPMs() {
        return receivingPMs;
    }

    public void setReceivingPMs(boolean receivingPMs) {
        this.receivingPMs = receivingPMs;
    }

    public NSPlayer getLastMessaged() {
        return lastMessaged;
    }

    public void setLastMessaged(NSPlayer lastMessaged) {
        this.lastMessaged = lastMessaged;
    }

    public boolean isInStaffChat() {
        return inStaffChat;
    }

    public void setInStaffChat(boolean inStaffChat) {
        this.inStaffChat = inStaffChat;
    }


    // -------------------------------------------- //
    // FIELD: lastActivityMillis
    // -------------------------------------------- //

    private long lastActivityMillis = System.currentTimeMillis();

    public long getLastActivityMillis() {
        return this.lastActivityMillis;
    }

    public void setLastActivityMillis(long lastActivityMillis) {
        // Clean input
        long target = lastActivityMillis;

        // Detect Nochange
        if (MUtil.equals(this.lastActivityMillis, target)) return;

        // Apply
        this.lastActivityMillis = target;

        // Mark as changed
        this.changed();
    }

    public void setLastActivityMillis() {
        this.setLastActivityMillis(System.currentTimeMillis());
    }


    public int getToggleSneakVL() {
        return toggleSneakVL;
    }

    public void setToggleSneakVL(int toggleSneakVL) {
        this.toggleSneakVL = toggleSneakVL;
    }

    public void incrementToggleSneak() {
        this.toggleSneakVL++;
    }

    public long getDiscordID() {
        return discordID;
    }

    public void setDiscordID(long discordID) {
        this.discordID = discordID;
    }

    public boolean hasDiscordLinked() {
        return this.discordID != -1;
    }

    public boolean isReceivingToggleSneak() {
        return receivingToggleSneak;
    }

    public void setReceivingToggleSneak(boolean receivingToggleSneak) {
        this.receivingToggleSneak = receivingToggleSneak;
    }

    public boolean isMessageSounds() {
        return messageSounds;
    }

    public void setMessageSounds(boolean messageSounds) {
        this.messageSounds = messageSounds;
    }

    public boolean isReceivingStaffChat() {
        return receivingStaffChat;
    }

    public void setReceivingStaffChat(boolean receivingStaffChat) {
        this.receivingStaffChat = receivingStaffChat;
    }

    public boolean isAFK() {
        return afk;
    }

    public void setAFK(boolean afk) {
        this.afk = afk;
    }

    public int getTotalAFKTime() {
        return totalAFKTime;
    }

    public void setTotalAFKTime(int afkTime) {
        this.totalAFKTime = afkTime;
    }

    public int getCurrentAFKTime() {
        return currentAFKTime;
    }

    public void setCurrentAFKTime(int currentAFKTime) {
        this.currentAFKTime = currentAFKTime;
    }

    public void incrementTotalAFKTime(int input){
        this.totalAFKTime += input;
    }

    public int getSpectatingTime() {
        return spectatingTime;
    }

    public void setSpectatingTime(int spectatingTime) {
        this.spectatingTime = spectatingTime;
    }

    public byte[] getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(byte[] adminPassword) {
        this.adminPassword = adminPassword;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public HashSet<Friend> getFriends() {
        return friends;
    }

    public void setFriends(HashSet<Friend> friends) {
        this.friends = friends;
    }

    public HashSet<UUID> getOutGoingFriendRequests() {
        return outGoingFriendRequests;
    }

    public void setOutGoingFriendRequests(HashSet<UUID> outGoingFriendRequests) {
        this.outGoingFriendRequests = outGoingFriendRequests;
    }

    public Friend getFriend(UUID uuid){
        for (Friend friend : getFriends()) {
            if(friend.getFriendUUID().equals(uuid)){
                return friend;
            }
        }

        return null;
    }

    public void removeFriend(UUID uuid){
        Friend friend = getFriend(uuid);
        if(friend == null){
            return;
        }

        this.friends.remove(friend);
    }

    public boolean isHasReferred() {
        return hasReferred;
    }

    public void setHasReferred(boolean hasReferred) {
        this.hasReferred = hasReferred;
    }
}
