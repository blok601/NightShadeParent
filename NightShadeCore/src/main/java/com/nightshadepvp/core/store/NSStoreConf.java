package com.nightshadepvp.core.store;

import com.massivecraft.massivecore.SimpleConfig;
import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.Core;

import java.util.Map;

public class NSStoreConf extends SimpleConfig
{
    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static transient NSStoreConf i = new NSStoreConf();
    public static NSStoreConf get() { return i; }
    public NSStoreConf() { super(Core.get()); }

    // -------------------------------------------- //
    // FIELDS
    // -------------------------------------------- //

    public static String dburi = "default";

    public static Map<String, String> alias2uri = MUtil.map(
            "default", "flatfile",
            "flatfile", "flatfile://nsstore",
            "mongodb", "mongodb://localhost:27017/nsstore"
    );

}
