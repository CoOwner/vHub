package rip.vexus.lobby;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.spigotmc.SpigotConfig;
import org.tyrannyofheaven.bukkit.zPermissions.ZPermissionsService;

import rip.vexus.lobby.commands.LivesCommand;
import rip.vexus.lobby.listeners.InventoryListener;
import rip.vexus.lobby.listeners.PlayerListener;
import rip.vexus.lobby.scoreboard.ScoreboardHandler;
import rip.vexus.lobby.user.UserManager;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class LobbyPlugin extends JavaPlugin implements PluginMessageListener {

	public static LobbyPlugin plugin;
	private ScoreboardHandler scoreboardHandler;
	private UserManager userManager;
	private ZPermissionsService permissionsService;
	private ConcurrentHashMap<String, Integer> listServers;
	private ConcurrentHashMap<String, Short> serverPorts;
	@Getter private ConfigFile databaseFile;
	@Getter private LobbyDatabase suiteDatabase;
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) return;
		
		try {
			
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
			String subchannel = in.readUTF();
			if (subchannel.equals("QueuePosition")) {
			
				player.sendMessage(in.readUTF().toString());
				
			}
			
		} catch (Exception ex) {}   
		
		try {
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
			String command = in.readUTF();
                    
			if (command.equals("PlayerCount")) {
				String server = in.readUTF();
				int playerCount = in.readInt();

				listServers.put(server, playerCount);
				
			}
		} catch (Exception e) {

		}
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("ServerIP")) {
            String serverName = in.readUTF();
            short port = in.readShort();
            Bukkit.broadcastMessage("" + serverName + " PORT: " + port);
            serverPorts.put(serverName, port);
          
        }
     }
	 
	@Override
	public void onEnable() {
		plugin = this;
		try {
			permissionsService = Bukkit.getServicesManager().load(ZPermissionsService.class);
		} catch (NoClassDefFoundError e) {
			getLogger().severe("[LobbyPlugin] zPermissions cannot be found, Lobby plugin has been disabled.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		 if (!new File("plugins/Hub/config.yml").exists()) {
	            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "vLobby - config.yml is being generated...");
	            this.saveDefaultConfig();
	            this.getConfig().options().copyDefaults(true);
	            this.saveConfig();
	        }
		SpigotConfig.tabComplete = 99999;
        this.databaseFile = new ConfigFile(this, "database");
        this.suiteDatabase = new LobbyDatabase(this);
		this.listServers = new ConcurrentHashMap<String, Integer>();
        this.serverPorts = new ConcurrentHashMap<String, Short>();
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		try {
			permissionsService = Bukkit.getServicesManager().load(ZPermissionsService.class);
		} catch (NoClassDefFoundError e) {
			Bukkit.getPluginManager().disablePlugin(this);
		}
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		this.userManager = new UserManager();
		this.scoreboardHandler = new ScoreboardHandler(this);
		this.getCommand("lives").setExecutor(new LivesCommand());
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
            	try {
            		ByteArrayOutputStream b = new ByteArrayOutputStream();
            		DataOutputStream out = new DataOutputStream(b);

            		out.writeUTF("PlayerCount");
            		out.writeUTF("ALL");
            		getServer().sendPluginMessage(LobbyPlugin.getPlugin(), "BungeeCord", b.toByteArray());
            	} catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            	try {
            		ByteArrayOutputStream b = new ByteArrayOutputStream();
            		DataOutputStream out = new DataOutputStream(b);

            		out.writeUTF("PlayerCount");
            		out.writeUTF("Kits");
            		getServer().sendPluginMessage(LobbyPlugin.getPlugin(), "BungeeCord", b.toByteArray());
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            	try {
            		ByteArrayOutputStream b = new ByteArrayOutputStream();
            		DataOutputStream out = new DataOutputStream(b);

            		out.writeUTF("PlayerCount");
            		out.writeUTF("HCF");
            		getServer().sendPluginMessage(LobbyPlugin.getPlugin(), "BungeeCord", b.toByteArray());
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
                      
                     try {
                    	 ByteArrayOutputStream b = new ByteArrayOutputStream();
                    	 DataOutputStream out = new DataOutputStream(b);

                         out.writeUTF("PlayerCount");
                         out.writeUTF("Squads");
                         getServer().sendPluginMessage(LobbyPlugin.getPlugin(), "BungeeCord", b.toByteArray());
          } catch (Exception e) {
                  e.printStackTrace();
          }
            }         
    }, 20L, 100);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public Map<String, Integer> getServers() {
		return listServers;
	}
	
	public Map<String, Short> getPorts() {
		return serverPorts;
	}
	
	public UserManager getProfileManager() {
		return this.userManager;
	}
	
	public ScoreboardHandler getScoreboardHandler() {
		return this.scoreboardHandler;
	}
	
	public ZPermissionsService getPermissionsService() {
		return permissionsService;
	}
	
	public static LobbyPlugin getPlugin() {
		return plugin;
	}
}
