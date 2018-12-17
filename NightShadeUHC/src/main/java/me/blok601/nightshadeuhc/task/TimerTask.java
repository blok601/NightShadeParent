package me.blok601.nightshadeuhc.task;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.util.Util;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 10/1/2017.
 */
public class TimerTask {

    private String time;
    private boolean running;
    private int taskID;

    public TimerTask(){
    }

    public void start(){
        this.running = true;
        this.taskID =  new BukkitRunnable(){
            int mins = 0;
            int seconds = 0;
            String rt = "00:00";
            @Override
            public void run() {
                if(running){
                    seconds+=5;
                    if(seconds >= 60){
                        mins++;
                        seconds = 0;
                    }

                    if(seconds < 10){
                        rt = mins + ":0" + seconds;
                    }else{
                        rt = mins + ":" + seconds;
                    }
                    time = rt;
                }else{
                    rt = "&3No Game Running!";
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(UHC.get(), 0, Util.TICKS * 5).getTaskId(); //5 seconds
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getTime() {
        return time;
    }

    public int getTaskID() {
        return taskID;
    }
}
