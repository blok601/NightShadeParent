package com.nightshadepvp.nightcheat.cheat.combat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.nightshadepvp.nightcheat.NightCheat;
import com.nightshadepvp.nightcheat.cheat.Cheat;
import com.nightshadepvp.nightcheat.cheat.CheatType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;

public class NoSwingCheat extends Cheat {

    private NightCheat plugin;
    public NoSwingCheat(NightCheat plugin) {
        super("NoSwing", CheatType.COMBAT);
        this.plugin = plugin;
    }

    private void addPacketListener(){
        protocolManager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer pa = event.getPacket();
                Player attacker = event.getPlayer();
                final Entity entity = pa.getEntityModifier(event).read(0);
                if(!(entity instanceof Player)) return;
                EnumWrappers.EntityUseAction entityUse = pa.getEntityUseActions().read(0); //Note: Can often be null!
                if(entityUse == EnumWrappers.EntityUseAction.ATTACK){
                    //They attacked when sending this packet
                    FightData fightData = FightData.getFightData(attacker);
                    fightData.setLastDamage(System.currentTimeMillis());
                    fightData.incrementHits();
                    debug("&fDamage packet from " + attacker.getName() + " received at " + System.currentTimeMillis());
                }
            }
        });
    }

    @EventHandler
    public void onAnimation(PlayerAnimationEvent event){
       if(event.getAnimationType() != PlayerAnimationType.ARM_SWING) return;
       Player player = event.getPlayer();
       if(player.getItemInHand() == null || player.getItemInHand().getType().toString().endsWith("SWORD")){
           FightData fightData = FightData.getFightData(player);
           fightData.setSwungArm(true);
           fightData.incrementSwings();
           debug("&fAnimation Event from " + player.getName() + " called at " + System.currentTimeMillis());
       }
    }

}
