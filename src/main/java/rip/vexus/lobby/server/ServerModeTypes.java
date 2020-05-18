package rip.vexus.lobby.server;

import org.bukkit.ChatColor;

public enum ServerModeTypes {
	
	OFFLINE("Offline", ChatColor.RED),
	ONLINE("Online", ChatColor.GREEN),
	WHITELISTED("Maintenance", ChatColor.DARK_RED);
	
	private String name;
	private ChatColor color;

	
	ServerModeTypes(String name, ChatColor color) {
		this.name = name;
		this.color = color;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
}
