package rip.vexus.lobby.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rip.vexus.lobby.profile.Profile;

public class LivesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player p = ((Player)sender);
		Profile profile = Profile.getByUuid(p.getUniqueId());
		if(args[0].equalsIgnoreCase("clear")) {
			profile.setDeathban(null);
			profile.save();
			return true;
		}
		p.sendMessage(ChatColor.RED + "Your deathban status is " + (profile.getDeathban() == null));	
		return true;
	}

}
