package rip.vexus.lobby.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import rip.vexus.lobby.LobbyPlugin;
import net.md_5.bungee.api.ChatColor;

public class UserManager implements Listener {

    private Map<UUID, User> user;
    
    public UserManager() {
        this.user = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
        	this.loadProfie(player);
        }
        Bukkit.getPluginManager().registerEvents(this, LobbyPlugin.getPlugin());
    }

    public void loadProfie(Player player) {
    	player.sendMessage(ChatColor.YELLOW + "We are searching for your user, please wait...");
    	long time = System.currentTimeMillis();
    	User data = new User(player);
        this.user.put(player.getUniqueId(), data);
        player.sendMessage(ChatColor.GREEN + "Your user has been located and was intialised in " + ChatColor.BOLD + (System.currentTimeMillis() - time) + "ms" + ChatColor.GREEN + ".");
    }
    public Map<UUID, User> getData() {
        return user;
    }

    public User getProfile(Player target) {
        return user.get(target.getUniqueId());
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.loadProfie(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(user.containsKey(player.getUniqueId())) {
            new BukkitRunnable() {
                public void run() {
                    user.remove(player.getUniqueId());
                }
            }.runTaskLater(LobbyPlugin.getPlugin(), 2L);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        if(user.containsKey(player.getUniqueId())) {
            new BukkitRunnable() {
                public void run() {
                    user.remove(player.getUniqueId());
                }
            }.runTaskLater(LobbyPlugin.getPlugin(), 2L);
        }
    }

}