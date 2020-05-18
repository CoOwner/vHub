package rip.vexus.lobby.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SelectorInventory implements Listener {
	
	public static Inventory menu = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Server Selector");
	/*
	 * 00 01 02 03 04 05 06 07 08
	 * 09 10 -- 12 -- 14 -- 16 17
	 * 18 19 20 21 22 23 24 25 26
	 */
//	private Config config = Config.getInstance();
	@EventHandler
	public void openInventory(InventoryOpenEvent e) {
		//Player p = (Player) e.getPlayer();
		if(e.getInventory().getName().equals(menu.getName())) {
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(" ");
			item.setItemMeta(meta);
			
			for(int i = 0; i < menu.getSize(); i++) {
				if(menu.getItem(i) == null) {
					menu.setItem(i, item);
				}
				
			}

			item = new ItemStack(Material.INK_SACK, 1, (short)6);
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lSQUADS"));
			if(ServerHandler.isOnline("Squads")) {
				if(ServerHandler.isWhitelisted("Squads")) {
					meta.setLore(Arrays.asList(
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
							ChatColor.RED.toString() + ChatColor.BOLD + "SQUADS" + ChatColor.RED + " is currently " + ChatColor.RED.toString() + ChatColor.BOLD + "WHITELISTED" + ChatColor.RED + ".",
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
				} else {
					meta.setLore(Arrays.asList(
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
							ChatColor.DARK_RED + "Server Information" + ChatColor.GRAY + " (Players " + LobbyPlugin.getPlugin().getServers().get("Squads") + ")",
							ChatColor.DARK_RED + " Enchant Limits" + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + "Protection 1, Sharpness 1",
							ChatColor.DARK_RED + " Faction Limits" + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + "10 Members, 0 Allies", "",
							ChatColor.DARK_RED.toString() + ChatColor.BOLD + "RIGHT CLICK" + ChatColor.RED + " to connect to " + ChatColor.DARK_RED + ChatColor.BOLD + "SQUADS" + ChatColor.RED + ".",
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
				}
			} else {
				meta.setLore(Arrays.asList(
						ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
						ChatColor.RED.toString() + ChatColor.BOLD + "SQUADS" + ChatColor.RED + " is currently " + ChatColor.RED.toString() + ChatColor.BOLD + "OFFLINE" + ChatColor.RED + ".",
						ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
			}
			item.setItemMeta(meta);
			menu.setItem(11, item);

			item = new ItemStack(Material.INK_SACK, 1, (short)12);
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lKITS"));
			if(ServerHandler.isOnline("Kits")) {
				if(ServerHandler.isWhitelisted("Kits")) {
					meta.setLore(Arrays.asList(
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
							ChatColor.RED.toString() + ChatColor.BOLD + "KITS" + ChatColor.RED + " is currently " + ChatColor.RED.toString() + ChatColor.BOLD + "WHITELISTED" + ChatColor.RED + ".",
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
				} else {
					meta.setLore(Arrays.asList(
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
							ChatColor.DARK_RED + "Server Information" + ChatColor.GRAY + " (Players " + LobbyPlugin.getPlugin().getServers().get("Kits") + ")",
							ChatColor.DARK_RED + " Enchant Limits" + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + "Protection 1, Sharpness 1",
							ChatColor.DARK_RED + " Faction Limits" + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + "10 Members, 0 Allies", "",
							ChatColor.DARK_RED.toString() + ChatColor.BOLD + "RIGHT CLICK" + ChatColor.RED + " to connect to " + ChatColor.DARK_RED + ChatColor.BOLD + "KITS" + ChatColor.RED + ".",
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
				}
			} else {
				meta.setLore(Arrays.asList(
						ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
						ChatColor.RED.toString() + ChatColor.BOLD + "KITS" + ChatColor.RED + " is currently " + ChatColor.RED.toString() + ChatColor.BOLD + "OFFLINE" + ChatColor.RED + ".",
						ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
			}
			item.setItemMeta(meta);
			menu.setItem(13, item);

			item = new ItemStack(Material.INK_SACK, 1, (short)14);
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lTEAMS"));
			if(ServerHandler.isOnline("Teams")) {
				if(ServerHandler.isWhitelisted("Teams")) {
					meta.setLore(Arrays.asList(
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
							ChatColor.RED.toString() + ChatColor.BOLD + "TEAMS" + ChatColor.RED + " is currently " + ChatColor.RED.toString() + ChatColor.BOLD + "WHITELISTED" + ChatColor.RED + ".",
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
				} else {
					meta.setLore(Arrays.asList(
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
							ChatColor.DARK_RED + "Server Information" + ChatColor.GRAY + " (Players " + LobbyPlugin.getPlugin().getServers().get("Squads") + ")",
							ChatColor.DARK_RED + " Enchant Limits" + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + "Protection 1, Sharpness 1",
							ChatColor.DARK_RED + " Team Limits" + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + "10 Members, 0 Allies", "",
							ChatColor.DARK_RED.toString() + ChatColor.BOLD + "RIGHT CLICK" + ChatColor.RED + " to connect to " + ChatColor.DARK_RED + ChatColor.BOLD + "TEAMS" + ChatColor.RED + ".",
							ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
				}
			} else {
				meta.setLore(Arrays.asList(
						ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
						ChatColor.RED.toString() + ChatColor.BOLD + "TEAMS" + ChatColor.RED + " is currently " + ChatColor.RED.toString() + ChatColor.BOLD + "OFFLINE" + ChatColor.RED + ".",
						ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
			}
			item.setItemMeta(meta);
			menu.setItem(15, item);
		}
		
	}
	@EventHandler
	public void onInventoryClick(InventoryDragEvent event) {
		if (event.getInventory().getName().equals(menu.getName())) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryInteractEvent event) {
		if (event.getInventory().getName().equals(menu.getName())) {
			event.setCancelled(true);
			return;
		}
	}
}

