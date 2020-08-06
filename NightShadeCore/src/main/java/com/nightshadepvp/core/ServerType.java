package com.nightshadepvp.core;

/**
 * Created by Blok on 9/3/2017.
 */
public enum  ServerType {

    PRACTICE, UHC, TOURNAMENT;

    private static ServerType type;

    public static ServerType getType() {
        return type;
    }

    public static void setType(ServerType type) {
        ServerType.type = type;
    }
}
