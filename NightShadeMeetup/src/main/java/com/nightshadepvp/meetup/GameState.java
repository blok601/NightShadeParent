package com.nightshadepvp.meetup;

import com.nightshadepvp.meetup.utils.ChatUtils;

/**
 * Created by Blok on 10/15/2018.
 */
public enum GameState {

    WAITING("&eWaiting..."), STARTING("&eStarting..."), INGAME("&eIngame"), ENDING("&eEnding");

    private String formatted;

    GameState(String formatted) {
        this.formatted = formatted;
    }

    public String getFormatted() {
        return ChatUtils.format(formatted);
    }



}
