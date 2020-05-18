package rip.vexus.lobby.scoreboard;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.google.common.base.Strings;

public abstract class SidebarProvider {
    protected static final String SCOREBOARD_TITLE;
    protected static final String STRAIGHT_LINE;
    
    public abstract String getTitle(final Player p0);
    
    public abstract List<SidebarEntry> getLines(final Player p0);
    
    static {
        SCOREBOARD_TITLE = ChatColor.AQUA.toString() + ChatColor.BOLD + "Vexus Network";
        STRAIGHT_LINE = ChatColor.STRIKETHROUGH.toString() + Strings.repeat("-", 256).substring(0, 10);
    }
}
