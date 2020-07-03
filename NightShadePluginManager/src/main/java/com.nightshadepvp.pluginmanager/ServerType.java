package com.nightshadepvp.pluginmanager;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * Created by Blok on 2/2/2019.
 */
public enum ServerType {

    UHC(Lists.newArrayList("UHC.jar", "NightCheat.jar")), HUB(Collections.singletonList("Hub.jar")), MINIGAME(Collections.emptyList()), TOURNAMENT(Lists.newArrayList("Tournament.jar", "NightCheat.jar"));

    private List<String> need;

    ServerType(List<String> need) {
        this.need = need;
    }

    public List<String> getNeed() {
        return need;
    }
}
