package com.nightshadepvp.core.entity;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.objects.PlayerColor;
import com.nightshadepvp.core.entity.objects.PlayerEffect;
import com.nightshadepvp.core.entity.objects.PlayerTag;

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
    private MassiveList<PlayerColor> colors = new MassiveList<>();

    private boolean particleSwitch = false;
    private PlayerEffect effect = PlayerEffect.NONE;
    private MassiveList<PlayerEffect> effects = new MassiveList<>();

    private PlayerTag currentTag = PlayerTag.DEFAULT;
    private MassiveList<PlayerTag> unlockedTags = new MassiveList<>();

    private boolean messageSounds = true;

    private long discordID = -1;



    private transient boolean frozen = false;
    private transient boolean receivingPMs = true;
    private transient NSPlayer lastMessaged = null;

    // ----------------------------------
    //         Staff Toggles
    // ----------------------------------

    private transient boolean inStaffChat = false;
    private transient boolean receivingStaffChat = true;
    private transient int toggleSneakVL = 0;
    private transient boolean receivingToggleSneak = true;

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

        this.setMessageSounds(that.messageSounds);

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
}
