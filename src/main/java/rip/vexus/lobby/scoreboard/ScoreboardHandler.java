package rip.vexus.lobby.scoreboard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import rip.vexus.lobby.LobbyPlugin;
import rip.vexus.lobby.scoreboard.provider.DefaultScoreboardProvider;

public class ScoreboardHandler implements Listener
{
    private final Map<UUID, PlayerBoard> playerBoards;
    private final LobbyPlugin plugin;
    private final SidebarProvider defaultProvider;
    
    public ScoreboardHandler(final LobbyPlugin plugin) {
        this.playerBoards = new HashMap<UUID, PlayerBoard>();
        this.plugin = plugin;
        this.defaultProvider = new DefaultScoreboardProvider(this.plugin);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final PlayerBoard playerBoard;
            this.setPlayerBoard(player.getUniqueId(), playerBoard = new PlayerBoard(plugin, player));
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        final PlayerBoard board = new PlayerBoard(this.plugin, player);
        this.setPlayerBoard(uuid, board);
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        this.playerBoards.remove(event.getPlayer().getUniqueId()).remove();
    }
    
    public PlayerBoard getPlayerBoard(final UUID uuid) {
        return this.playerBoards.get(uuid);
    }
    
    public void setPlayerBoard(final UUID uuid, final PlayerBoard board) {
        this.playerBoards.put(uuid, board);
        board.setSidebarVisible(true);
        board.setDefaultSidebar(this.defaultProvider, 2L);
    }
    
    public void clearBoards() {
        final Iterator<PlayerBoard> iterator = this.playerBoards.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().remove();
            iterator.remove();
        }
    }
}
