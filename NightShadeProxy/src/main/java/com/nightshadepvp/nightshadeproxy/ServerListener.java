package com.nightshadepvp.nightshadeproxy;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Map;

/**
 * Created by Blok on 1/2/2019.
 */
public class ServerListener implements Listener {
    
    private NightShadeProxy nightShadeProxy;

    public ServerListener(NightShadeProxy nightShadeProxy) {
        this.nightShadeProxy = nightShadeProxy;
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent ev){
        if(ev.getTag().contains("staffchat")){
            ByteArrayDataInput in = ByteStreams.newDataInput(ev.getData());
            String player = in.readUTF();
            String msg = in.readUTF();

            Map<String, ServerInfo> servers = nightShadeProxy.getProxy().getServers();

            ServerInfo serverInfo;
            for (Map.Entry<String, ServerInfo> entry : servers.entrySet()){
                String name = entry.getKey();
                serverInfo = nightShadeProxy.getProxy().getServerInfo(name);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(stream);

                try{
                    out.writeUTF(player);
                    out.writeUTF(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

                serverInfo.sendData("staffchat", stream.toByteArray());

            }
        }else if(ev.getTag().contains("BungeeCord")){
            ByteArrayDataInput in = ByteStreams.newDataInput(ev.getData());
            String player = in.readUTF();
            boolean state = in.readBoolean();

            Map<String, ServerInfo> servers = nightShadeProxy.getProxy().getServers();
            ServerInfo serverInfo;
            for (Map.Entry<String, ServerInfo> entry : servers.entrySet()){
                String name = entry.getKey();
                serverInfo = nightShadeProxy.getProxy().getServerInfo(name);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(stream);

                try{
                    out.writeUTF(player);
                    out.writeBoolean(state);
                }catch (Exception e){
                    e.printStackTrace();
                }

                serverInfo.sendData("BungeeCord", stream.toByteArray());
            }
        }
    }
}
