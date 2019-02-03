package com.nightshadepvp.pluginmanager;

import java.util.Collections;
import java.util.List;

/**
 * Created by Blok on 2/2/2019.
 */
public enum ServerType {

    UHC(Collections.singletonList("UHC.jar")), HUB(Collections.singletonList("Hub.jar")), MINIGAME(Collections.emptyList());

    private List<String> need;

    ServerType(List<String> need) {
        this.need = need;
    }

    public List<String> getNeed() {
        return need;
    }
}
