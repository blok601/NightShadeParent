package me.blok601.nightshadeuhc.manager;

import com.google.common.collect.Sets;
import com.nightshadepvp.core.entity.objects.FakePlayer;
import me.blok601.nightshadeuhc.UHC;

import java.util.HashSet;

/**
 * Created by Blok on 3/3/2019.
 */
public class FakePlayerManager {
    private static FakePlayerManager ourInstance = new FakePlayerManager();

    public static FakePlayerManager getInstance() {
        return ourInstance;
    }

    private FakePlayerManager() {
    }

    private HashSet<FakePlayer> npcs;

    public void setup(UHC uhc) {
        this.npcs = Sets.newHashSet();


//        ItemStack[] arenaArmor = new ItemStack[4];
//        arenaArmor[3] = new ItemBuilder(Material.IRON_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).make();
//        arenaArmor[2] = new ItemBuilder(Material.IRON_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).make();
//        arenaArmor[1] = new ItemBuilder(Material.IRON_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).make();
//        arenaArmor[0] = new ItemBuilder(Material.IRON_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).make();
//        FakePlayer arena = new FakePlayer(arenaArmor, new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL).make(), new Location(MConf.get().getSpawnLocation().asBukkitLocation().getWorld(), 0, 11, 4), uhc);
//        this.npcs.add(arena);
    }

    public HashSet<FakePlayer> getNpcs() {
        return npcs;
    }
}
