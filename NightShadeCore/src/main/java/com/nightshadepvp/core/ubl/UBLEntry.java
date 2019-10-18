package com.nightshadepvp.core.ubl;

import lombok.Data;

import java.util.UUID;

/**
 * Created by Blok on 12/17/2018.
 */
@Data
public class UBLEntry {

    private String ign;
    private UUID uuid;
    private String reason;
    private String created;
    private String length;
    private String expires;
    private String banPost;

}
