package me.blok601.nightshadeuhc.manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Settings {
	
	private Settings() { }

	private static Settings instance = new Settings();
	
	public static Settings getInstance() {
		return instance;
	}
	
	public void setup(Plugin p) {
		if (!p.getDataFolder().exists()) p.getDataFolder().mkdir();
		
		file = new File(p.getDataFolder(), "teams.yml");
		
		if (!file.exists()) {
			try { file.createNewFile(); }
			catch (Exception e) { e.printStackTrace(); }
		}
		
		config = YamlConfiguration.loadConfiguration(file);

		sFile = new File(p.getDataFolder(), "server.yml");

		if(!sFile.exists()){
			try{
				sFile.createNewFile();
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		server = YamlConfiguration.loadConfiguration(sFile);

		pFile = new File(p.getDataFolder(), "players.yml");

		if(!pFile.exists()){
			try{
				pFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		players = YamlConfiguration.loadConfiguration(pFile);
	}
	
	private File file;
	private FileConfiguration config;

	private File sFile;
	private FileConfiguration server;

	private File pFile;
	private FileConfiguration players;
	
	public void set(String path, Object value) {
		config.set(path, value);
		save();
	}
	
	public ConfigurationSection createConfigurationSection(String path) {
		ConfigurationSection cs = config.createSection(path);
		save();
		return cs;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String path) {
		return (T) config.get(path);
	}
	
	public boolean contains(String path) {
		return config.contains(path);
	}
	
	public void save() {
		try { config.save(file); }
		catch (Exception e) { e.printStackTrace(); }
	}

	public FileConfiguration getServer() {
		return server;
	}

	public void saveServer(){
		try{
			server.save(sFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void savePlayers(){
		try{
			players.save(pFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public FileConfiguration getPlayers() {
        return players;
    }
}
