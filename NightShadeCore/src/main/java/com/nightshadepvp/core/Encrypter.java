package com.nightshadepvp.core;

/**
 * Created by Blok on 6/20/2019.
 */
public interface Encrypter {

    byte[] encrypt (byte[] data);
    byte[] decrypt (byte[] data);

}
