package me.blok601.nightshadeuhc.utils;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.manager.GameManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
	
	public static void start(final String time){

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HH:mm");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		
		new BukkitRunnable(){
			
			@Override
			public void run(){
				String f = dateFormatGmt.format(new Date());
                //System.out.println(f);
				if(f.equalsIgnoreCase(time)){
                    GameManager.setWhitelistEnabled(false);
						cancel();
				}
			}
		}.runTaskTimerAsynchronously(UHC.get(), 0, 20);
	}

	public static String formatSecondsToTime(int secondtTime)
	{
		TimeZone tz = TimeZone.getTimeZone("UTC");
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		df.setTimeZone(tz);
		String time = df.format(new Date(secondtTime*1000L));
        // Time example: 12:13:04 --> 2, 5, 6
        // Time example: 120:13:04 --> 2, 5, 6

        char[] chars = time.toCharArray();
        if (chars[2] != ':') {
            //Spectated over 100 hours
            chars[3] = 'h';
            chars[6] = 'm';
        } else {
            chars[2] = 'h';
            chars[5] = 'm';
        }

        return time + "s";
	}

    public static boolean isTime(String input) {
        String[] array;
        if (input.contains("m")) {
            array = input.split("m");
            String number = array[0];
            return Util.isInt(number);
        } else if (input.contains("mins")) {
            array = input.split("mins");
            String number = array[0];
            return Util.isInt(number);
        } else if (input.contains("minutes")) {
            array = input.split("minutes");
            String number = array[0];
            return Util.isInt(number);
        }

        return false;
    }

    public static String parseAsInt(String time) {
        String[] array;
        if (time.contains("m")) {
            array = time.split("m");
            return array[0];
        } else if (time.contains("mins")) {
            array = time.split("mins");
            return array[0];
        } else if (time.contains("minutes")) {
            array = time.split("minutes");
            return array[0];
        }

        return null;
    }

    public static int parseTimeAsSeconds(String input) {
        //Input would be
        String[] array;
        if (input.contains("m")) {
            array = input.split("m");
            String number = array[0];
            if (Util.isInt(number)) {
                int i = Integer.parseInt(number);
                return i * 60;
            }
        } else if (input.contains("mins")) {
            array = input.split("mins");
            String number = array[0];
            if (Util.isInt(number)) {
                int i = Integer.parseInt(number);
                return i * 60;
            }
        } else if (input.contains("minutes")) {
            array = input.split("minutes");
            String number = array[0];
            if (Util.isInt(number)) {
                int i = Integer.parseInt(number);
                return i * 60;
            }
        }

        return 0;
    }

    public static String formatSeconds(int seconds) {
        int mins = seconds / 60;
        int s = seconds % 60;

        return mins + "minutes and " + s + " seconds";
    }
}
