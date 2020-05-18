package rip.vexus.lobby.scoreboard.provider;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import rip.vexus.lobby.LobbyPlugin;
import rip.vexus.lobby.scoreboard.SidebarEntry;
import rip.vexus.lobby.scoreboard.SidebarProvider;

public class DefaultScoreboardProvider extends SidebarProvider {
	
    private final LobbyPlugin plugin;
    
    public DefaultScoreboardProvider(final LobbyPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getTitle(final Player player) {
        return DefaultScoreboardProvider.SCOREBOARD_TITLE;
    }
    @Override
    public List<SidebarEntry> getLines(final Player player) {
        final List<SidebarEntry> lines = new ArrayList<SidebarEntry>();
      //  this.plugin.getBungeeHandler().requestCount();
        lines.add(new SidebarEntry(ChatColor.GRAY, DefaultScoreboardProvider.STRAIGHT_LINE, DefaultScoreboardProvider.STRAIGHT_LINE));
        lines.add(new SidebarEntry(ChatColor.AQUA.toString() + ChatColor.BOLD, "Online ", "Players:"));
        lines.add(new SidebarEntry(ChatColor.WHITE + "  " + plugin.getServers().get("ALL"), ChatColor.WHITE + " / 2,0", /*Players go on this section*/ChatColor.WHITE.toString() + "00"));
        lines.add(new SidebarEntry("  "));
        lines.add(new SidebarEntry(ChatColor.AQUA.toString() + ChatColor.BOLD + "Rank:"));
        final String rankName = (plugin.getPermissionsService().getPlayerPrimaryGroup(player.getUniqueId()).equals("default") ? ChatColor.WHITE + "None" : plugin.getPermissionsService().getPlayerPrimaryGroup(player.getUniqueId()));
        lines.add(new SidebarEntry(ChatColor.WHITE.toString() + rankName));
//        if(QueueInfo.getQueue(player.getName()) != null) {
//       	 lines.add(new SidebarEntry(ChatColor.BLUE + " "));
//       	 lines.add(new SidebarEntry(ChatColor.AQUA.toString() + ChatColor.BOLD + "Que", "ue", ":"));
//       	lines.add(new SidebarEntry(ChatColor.WHITE + "  ", QueueInfo.getQueue(player.getName()) , ""));
//      	 lines.add(new SidebarEntry(ChatColor.GREEN + " "));
//       	 lines.add(new SidebarEntry(ChatColor.AQUA.toString() + ChatColor.BOLD + "Pos", "ition", ":"));
//       	lines.add(new SidebarEntry(ChatColor.WHITE.toString() + QueueInfo.getQueueInfo(QueueInfo.getQueue(player.getName())).getPosition(player.getName()), ChatColor.WHITE + "/" , "" + QueueInfo.getQueueInfo(QueueInfo.getQueue(player.getName())).getPlayersInQueue()));
//      }
//        Profile profile = Profile.getByUuid(player.getUniqueId());
//        if(profile.getDeathban() == null) {
//        	lines.add(new SidebarEntry("   "));
//        	lines.add(new SidebarEntry(ChatColor.AQUA.toString() + ChatColor.BOLD + "Death", "ban: " + ChatColor.WHITE, "None"));
//        } else {
//        	lines.add(new SidebarEntry("   "));
//        	lines.add(new SidebarEntry(ChatColor.AQUA.toString() + ChatColor.BOLD + "Death", "" + ChatColor.WHITE, DurationFormatter.(profile.getDeathban().get));
//        }
        lines.add(new SidebarEntry("    "));
        lines.add(new SidebarEntry(ChatColor.GRAY.toString() + "www.", "visage", ".rip"));
        lines.add(new SidebarEntry(ChatColor.GRAY, ChatColor.STRIKETHROUGH + DefaultScoreboardProvider.STRAIGHT_LINE, DefaultScoreboardProvider.STRAIGHT_LINE));
        return lines;
    }
    
}

