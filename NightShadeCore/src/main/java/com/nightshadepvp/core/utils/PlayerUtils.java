package com.nightshadepvp.core.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Blok on 6/20/2019.
 */
public class PlayerUtils {

    @Getter
    private static HashMap<UUID, Runnable> toConfirm = new HashMap<>();
}
