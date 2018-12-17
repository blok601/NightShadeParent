package me.blok601.nightshadeuhc.utils;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayerColl;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

	public static int TICKS = 20;

	public static boolean isInt(String s){
		try{
			Integer.parseInt(s);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public static boolean isPotion(ItemStack itemStack){
		try{
			Potion.fromItemStack(itemStack);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public static boolean getChance(double c) {
		Random random = new Random();
		double r = random.nextDouble();
		return r * 100 < c;
	}
	
	public static void staffLog(String string){
		NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer -> nsPlayer.hasRank(Rank.TRIAL)).forEach(nsPlayer -> nsPlayer.msg(ChatUtils.format("&6Server: &2" + string)));
	}

	public static boolean isBoolean(String string){
		try{
			Boolean.parseBoolean(string);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public static  ArrayList<Block> getBlocks(Block start, int radius){
		ArrayList<Block> blocks = new ArrayList<Block>();
		for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
			for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
				for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
					Location loc = new Location(start.getWorld(), x, y, z);
					blocks.add(loc.getBlock());
				}
			}
		}
		return blocks;
	}

	public static boolean isBetween(int max, int min, int val){
		return min < val && val < max;
	}

	public static int randomBetween(int int1, int int2){
		return ThreadLocalRandom.current().nextInt(int1, int2 + 1);
	}

	public static boolean deleteWorldFolder(World world){
		if(!world.getWorldFolder().exists()){
			return false;
		}

		File folder = world.getWorldFolder();
		File[] files = folder.listFiles();
		if(files == null || files.length == 0){
			 return folder.delete();

		}
		for (int i = 0; i < files.length; i++){
			if(files[i].isDirectory()){
				deleteFolder(files[i]);
			}else{
				files[i].delete();
			}
		}

		return folder.delete();

	}

	private static boolean deleteFolder(File element) {
		if (element.isDirectory() && element.listFiles() != null) {
			for (File sub : element.listFiles()) {
				deleteFolder(sub);
			}
		}
		return element.delete();
	}
}
