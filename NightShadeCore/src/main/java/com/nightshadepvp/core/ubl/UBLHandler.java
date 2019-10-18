package com.nightshadepvp.core.ubl;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.utils.ChatUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Blok on 12/17/2018.
 */
public class UBLHandler implements Runnable {

    private Core core;

    public UBLHandler(Core core) {
        this.core = core;
    }

    private HashMap<UUID, UBLEntry> entries;

    // -------------------------------------
    //               UBL Settings
    // -------------------------------------
    private static final String BANLIST = "https://docs.google.com/spreadsheet/ccc?key=0AjACyg1Jc3_GdEhqWU5PTEVHZDVLYWphd2JfaEZXd2c&output=csv";

    /**
     * How often should the  ubl list automatically update?
     */
    private static final Duration AUTO_UPDATE_TIMER = Duration.ofMinutes(30);

    /**
     * How many seconds should we wait until we successfully connect and read the doc?
     */
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    /**
     * Format that downloaded google doc works with
     */
    public static final CSVFormat FORMAT = CSVFormat.RFC4180;

    private long lastUpdate;

    /**
     * Setup the maps and start the runnable
     */
    public void setup() {
        this.entries = new HashMap<>();


        Bukkit.getScheduler().runTaskTimerAsynchronously(core, this, 0L, AUTO_UPDATE_TIMER.getSeconds() * 20); //Use ticks
    }

    public void update() {
        Bukkit.getScheduler().runTaskAsynchronously(core, this);
    }

    @Override
    public void run() {
        if (Bukkit.isPrimaryThread()) { //Make sure if we are the only thread to run on, call it
            update();
            return;
        }

        lastUpdate = System.currentTimeMillis();
        ChatUtils.sendAll("&bUpdating the UBL...", Rank.TRIAL);

        URL url;
        try {
            url = new URL(BANLIST);
        } catch (MalformedURLException e) {
            core.getLogManager().log(Logger.LogType.WARNING, "There was an error when trying to download the UBL List! Loading from backup...");
            core.getLogManager().log(Logger.LogType.DEBUG, "Error:");
            e.printStackTrace();
            loadFromBackUpFile("URL Errors");
            return; // Stop it here
        }

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            core.getLogManager().log(Logger.LogType.WARNING, "There was an error when trying to download the UBL List! Loading from backup...");
            core.getLogManager().log(Logger.LogType.DEBUG, "Error:");
            e.printStackTrace();
            loadFromBackUpFile("Download Error");
            return; // Stop it here
        }

        int timeout = (int) TIMEOUT.toMillis(); //AKA multiply by 1000

        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.addRequestProperty("User-Agent", "NightUBL Request"); //Connection is all setup

        CSVParser parser; //To parse file
        List<CSVRecord> records; //Each row

        try (Reader reader = new InputStreamReader(connection.getInputStream())) { // try with resources is more efficient
            parser = FORMAT.withFirstRecordAsHeader().parse(reader); // Make sure to skip that top row aka the headers
            records = parser.getRecords(); //Would prefer it uses sets for performance but its okay
        } catch (IOException e) {
            core.getLogManager().log(Logger.LogType.WARNING, "There was an error when reading the banlist! Loading from backup...");
            core.getLogManager().log(Logger.LogType.DEBUG, "Error:");
            e.printStackTrace();
            //TODO: Load from backup
            return;
        } finally {
            connection.disconnect(); //Regardless of what happens, disconnect so resources aren't waste and no mem leaks
        }

        setBanlist(records); //Updated
        getBackUp().delete();

        //Save the backup now
        try {
            FileWriter writer = new FileWriter(getBackUp());
            CSVPrinter printer = new CSVPrinter(writer, FORMAT);
            printer.printRecord(parser.getHeaderMap().keySet()); //Add the headers to the file
            printer.printRecords(this.entries.values().stream().map(entry -> new Object[]{
                    entry.getIgn(),
                    entry.getUuid(),
                    entry.getReason(),
                    entry.getCreated(),
                    entry.getLength(),
                    entry.getExpires(),
                    entry.getBanPost()
            }).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
            core.getLogManager().log(Logger.LogType.SEVERE, "Couldn't save a UBL Backup!");
        }
    }

    private void setBanlist(List<CSVRecord> records) {
        HashMap<UUID, UBLEntry> bans = new HashMap<>();
        HashSet<Long> invalid = new HashSet<>(); //Invalid lines

        for (CSVRecord record : records) {
            try {
                String u = record.get("UUID").trim(); //Get the uuid from the first column

                if (u.length() == 32) { //Handy method to trim UUID's if they don't have a hyphen, but are in the correct UUID format
                    u = u.substring(0, 8) + "-" +
                            u.substring(8, 12) + "-" +
                            u.substring(12, 16) + "-" +
                            u.substring(16, 20) + "-" +
                            u.substring(20, 32);
                }

                UUID uuid = UUID.fromString(u);
                UBLEntry entry = new UBLEntry();
                entry.setUuid(uuid);
                entry.setIgn(record.get("IGN"));
                entry.setReason(record.get("Reason"));
                entry.setCreated(record.get("Date Banned"));
                entry.setLength(record.get("Length of Ban"));
                entry.setExpires(record.get("Expiry Date"));
                entry.setBanPost(record.get("Case"));
                bans.put(uuid, entry);
            } catch (Exception e) {
                invalid.add(record.getRecordNumber() + 1); // Skip the header
            }
        }

        if (!invalid.isEmpty()) {
            //There were some issues
            core.getLogManager().log(Logger.LogType.INFO, "&eInvalid UBL rows: &c" + Joiner.on("&7, &c").join(invalid));
        }

        this.entries.clear();
        this.entries.putAll(bans);

        long totalTimeInMillis = System.currentTimeMillis() - lastUpdate;
        long seconds = totalTimeInMillis / 1000;
        long millis = totalTimeInMillis % 1000;

        ChatUtils.sendAll("&bFinished updating the UBL, &8(&a" + seconds + "." + millis + "s&8)", Rank.TRIAL);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isUBLed(player.getUniqueId()) && !isExempt(player.getName())) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.kickPlayer(getBanMessage(player.getUniqueId())); //Give them the boot
                    }
                }.runTask(Core.get());
            }
        }
    }

    private File getBackUp() {
        return new File(core.getDataFolder(), "ubl.backup");
    }

    private void loadFromBackUpFile(String ex) {
        ChatUtils.sendAll("&cCouldn't load the UBL from the sheet because " + ex, Rank.TRIAL);
        ChatUtils.sendAll("&eLoading a recent copy from backup...", Rank.TRIAL);

        if (!getBackUp().exists()) {
            ChatUtils.sendAll("&4There was no UBL Backup found! Contact Blok immediately!", Rank.TRIAL);
            return;
        }

        List<CSVRecord> records;

        try (FileReader reader = new FileReader(getBackUp())) {
            records = FORMAT.parse(reader).getRecords();
        } catch (IOException e) {
            ChatUtils.sendAll("&cCould not parse UBL backup!", Rank.TRIAL);
            return;
        }

        setBanlist(records);
    }

    public UBLEntry getEntry(UUID uuid) {
        return entries.get(uuid);
    }

    public boolean isUBLed(UUID uuid) {
        return getEntry(uuid) != null;
    }

    public Collection<UBLEntry> getBanEntries() {
        return ImmutableList.copyOf(entries.values());
    }

    public String getBanMessage(UUID uuid) {
        UBLEntry entry = getEntry(uuid);
        if (entry == null) return null;

        return "\n" +
                "§7§m---§8§m[-§r §5You are UBLed §8§m-]§7§m---§f"
                + "\n" + "\n" +
                "§7► §8Banned on§7: " + entry.getCreated()
                + "\n" +
                "§7► §8Reason§7: " + entry.getReason()
                + "\n" +
                "§7► §8Banned Length§7: " + entry.getLength() + "\n" +
                "\n" +
                "§7► §8Ban Expires§7: " + entry.getExpires() +
                "\n" +
                "§7► §8Case§7: " + entry.getBanPost() + "\n" +
                "\n" +
                "§cTo Appeal this ban, contact the courtroom @" + "\n" +
                "reddit.com/r/UHCCourtroom";

    }

    public String getTargetBanMessage(UBLEntry entry) {
        return "\n" + "§5&m--------------------------------" +
                "§7► §8Banned on§7: " + entry.getCreated()
                + "\n" +
                "§7► §8Reason§7: " + entry.getReason()
                + "\n" +
                "§7► §8Banned Length§7: " + entry.getLength() + "\n" +
                "\n" +
                "§7► §8Ban Expires§7: " + entry.getExpires() +
                "\n" +
                "§7► §8Case§7: " + entry.getBanPost() + "\n" +
                "§5&m--------------------------------";
    }

    public UBLEntry getEntry(String name) {
        for (UBLEntry entry : this.getBanEntries()) {
            if (entry.getIgn().equalsIgnoreCase(name)) {
                return entry;
            }
        }

        return null;
    }

    public boolean isExempt(String name) {
        return MConf.get().getExempt().contains(name.toLowerCase());
    }


}
