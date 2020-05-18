package rip.vexus.lobby.user;

import java.util.List;

import org.bukkit.entity.Player;

public class User {

	private Player player;
	
	private String lastRepliedTo;
	private List<String> ignoredList;
	private boolean socialSpy;
	private boolean toggleMessages;
	private boolean playerVisibility;
	private boolean scoreboardVisibility;
	
	public User(Player player) {
		this.player = player;

		this.ignoredList = null;
		this.lastRepliedTo = null;
		this.socialSpy = false;
		this.toggleMessages = false;
		this.playerVisibility = false;
		this.scoreboardVisibility = true;
	}

	
	
	public boolean getSocialSpy() {
		return this.socialSpy;
	}
	
	public void setSocialSpy(boolean socialSpy) {
		this.socialSpy = socialSpy;
	}
	
	
	public boolean getScoreboardVisibility() {
		return this.scoreboardVisibility;
	}
	
	public void setScoreboardVisibility(boolean hideScoreboard) {
		this.scoreboardVisibility = hideScoreboard;
	}
	
	public boolean getPlayerVisibility() {
		return this.playerVisibility;
	}
	
	public void setPlayerVisibility(boolean hideScoreboard) {
		this.playerVisibility = hideScoreboard;
	}
	
	public boolean getMessages() {
		return this.toggleMessages;
	}
	
	public void setMessages(boolean toggleMessages) {
		this.toggleMessages = toggleMessages;
	}
	
	public String getRepliedTo() {
		return this.lastRepliedTo;
	}
	
	public void setLastRepliedTo(String lastRepliedTo) {
		this.lastRepliedTo = lastRepliedTo;
	}
	
	public List<String> getIgnoredList() {
		return this.ignoredList;
	}
	
	public void setIgnoredList(List<String> ignoredList) {
		this.ignoredList = ignoredList;
	}
	
	
	public Player getPlayer() {
		return this.player;
	}
}
