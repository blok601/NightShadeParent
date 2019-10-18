package com.nightshadepvp.core.store;

import com.massivecraft.massivecore.store.Db;
import com.massivecraft.massivecore.store.Driver;
import com.massivecraft.massivecore.store.GsonEqualsChecker;
import com.massivecraft.massivecore.xlib.gson.JsonElement;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NSStore {
    // -------------------------------------------- //
    // CONSTANTS
    // -------------------------------------------- //
    // This class also serves the purpose of containing database related constants.

    public static final boolean DEBUG_ENABLED = false;

    // -------------------------------------------- //
    // DRIVER REGISTRY
    // -------------------------------------------- //

    private static Map<String, Driver> drivers = new HashMap<>();

    public static boolean registerDriver(Driver driver) {
        if (drivers.containsKey(driver.getDriverName())) return false;
        drivers.put(driver.getDriverName(), driver);
        return true;
    }

    public static Driver getDriver(String id) {
        return drivers.get(id);
    }

    // -------------------------------------------- //
    // ID CREATION
    // -------------------------------------------- //

    public static String createId() {
        return UUID.randomUUID().toString();
    }

    // -------------------------------------------- //
    // JSON ELEMENT EQUAL
    // -------------------------------------------- //

    public static boolean equal(JsonElement one, JsonElement two) {
        if (one == null) return two == null;
        if (two == null) return one == null;

        return GsonEqualsChecker.equals(one, two);
    }

    // -------------------------------------------- //
    // FROODLSCHTEIN
    // -------------------------------------------- //

    // We cache databases here
    private static Map<String, Db> uri2db = new HashMap<>();

    public static String resolveAlias(String alias) {
        String uri = NSStoreConf.alias2uri.get(alias);
        if (uri == null) return alias;
        return resolveAlias(uri);
    }

    public static Db getDb(String alias) {
        String uri = resolveAlias(alias);
        Db db = uri2db.get(uri);
        if (db != null) return db;

        try {
            db = getDb(new URI(uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        uri2db.put(uri, db);

        return db;
    }

    public static Db getDb() {
        return getDb(NSStoreConf.dburi);
    }

    public static Db getDb(URI uri) {
        String scheme = uri.getScheme();
        Driver driver = getDriver(scheme);
        if (driver == null) {
            return null;
        }
        return driver.getDb(uri.toString());
    }
}

