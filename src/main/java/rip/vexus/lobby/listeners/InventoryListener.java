package rip.vexus.lobby.listeners;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rip.vexus.lobby.LobbyPlugin;
import rip.vexus.lobby.profile.Profile;
import rip.vexus.lobby.profile.ProfileProtectionLifeType;

public class InventoryListener implements Listener {

	LobbyPlugin plugin = LobbyPlugin.getPlugin();
	
	public void refresh() {
	       Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
	            @Override
	            public void run() {
	                for (Player p : Bukkit.getOnlinePlayers()) {
	                    if (p.getOpenInventory() == null) {
	                        return;
	                    }

	                    if (p.getOpenInventory().getTitle().contains("Server Selector")) {
	                        InventoryView menu = p.getOpenInventory();
	                        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
	        				ItemMeta meta = item.getItemMeta();
	        				meta.setDisplayName(" ");
	        				item.setItemMeta(meta);

	        				item = new ItemStack(Material.INK_SACK, 1, (short)6);
	        				Profile profile = Profile.getByUuid(p.getUniqueId());
	        				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lHCFactions &7(4-Mans)"));

	        				if(profile.getDeathban() == null) {
	        					meta.setLore(Arrays.asList(
	        							ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
	        							ChatColor.AQUA + "Server Information" + ChatColor.GRAY + " (" + LobbyPlugin.getPlugin().getServers().get("hcf") + "/500)",
	        							ChatColor.AQUA + "  Status: " + ChatColor.WHITE + "Online",
	        							ChatColor.AQUA + "  Faction Limits: " + ChatColor.WHITE + "4 Members, 0 Allies.",
	        							ChatColor.AQUA + "  Enchant Limits: " + ChatColor.WHITE + "Protection 1, Sharpness 1","",
	        							ChatColor.RED + "You are not death-banned!", "",
	        							ChatColor.AQUA.toString() + ChatColor.BOLD + "RIGHT CLICK" + ChatColor.AQUA + " to connect to " + ChatColor.AQUA + ChatColor.BOLD + "HCFACTIONS" + ChatColor.AQUA + "."
	        							,ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
	        					item.setItemMeta(meta);
	        					menu.setItem(3, item);
	        				} else {
	        					meta.setLore(Arrays.asList(
	        							ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
	        							ChatColor.AQUA + "Server Information" + ChatColor.GRAY + " (" + LobbyPlugin.getPlugin().getServers().get("hcf") + "/500)",
	        							ChatColor.AQUA + "  Status: " + ChatColor.WHITE + "Online",
	        							ChatColor.AQUA + "  Faction Limits: " + ChatColor.WHITE + "4 Members, 0 Allies.",
	        							ChatColor.AQUA + "  Enchant Limits: " + ChatColor.WHITE + "Protection 1, Sharpness 1","",
	        							ChatColor.RED + "You are death-banned for " + profile.getDeathban().getTimeLeft(), ChatColor.GREEN + "You have " + profile.getLives().get(ProfileProtectionLifeType.SOULBOUND).intValue() + " lives." + ChatColor.GRAY + " (Click to use)", "",
	        							ChatColor.AQUA.toString() + ChatColor.BOLD + "RIGHT CLICK" + ChatColor.AQUA + " to connect to " + ChatColor.AQUA + ChatColor.BOLD + "HCFACTIONS" + ChatColor.AQUA + "."
	        							,ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
	        					item.setItemMeta(meta);
	        					menu.setItem(3, item);
	        				}
	        					item = new ItemStack(Material.INK_SACK, 1, (short)12);
	        					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lKits &7(15 Mans)"));
	        		
	        						meta.setLore(Arrays.asList(
	        								ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------",
	        								ChatColor.AQUA + "Server Information" + ChatColor.GRAY + " (" + LobbyPlugin.getPlugin().getServers().get("hcf") + "/500)",
	        								ChatColor.AQUA + "  Status: " + ChatColor.WHITE + "Online",
	        								ChatColor.AQUA + "  Faction Limits: " + ChatColor.WHITE + "15 Members, 0 Allies.",
	        								ChatColor.AQUA + "  Enchant Limits: " + ChatColor.WHITE + "Protection 1, Sharpness 1","",
	        								
	        								ChatColor.AQUA.toString() + ChatColor.BOLD + "RIGHT CLICK" + ChatColor.AQUA + " to connect to " + ChatColor.AQUA + ChatColor.BOLD + "KITS" + ChatColor.AQUA + "."
	        								,ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------------------"));
	        						item.setItemMeta(meta);
	        						menu.setItem(5, item);
	                    }
	                }
	            
	            }
	        }, 0, 20L);
	}
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getItem().getType() == null) return;
			if(e.getItem().getType() == Material.COMPASS) {
				Inventory menu = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY + "Server Selector");
				e.getPlayer().openInventory(menu);
				refresh();
				e.getPlayer().updateInventory();
				return;
			}
			return;
		}
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if(e.getCurrentItem() != null) {
			Profile profile = Profile.getByUuid(e.getWhoClicked().getUniqueId());
			Player p = ((Player)e.getWhoClicked());
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains("HCFactions")) {
				if(profile.getDeathban() != null) {
					if(profile.getLives().get(ProfileProtectionLifeType.SOULBOUND) <= 0) {
						p.sendMessage(ChatColor.RED + "You do not have any lives. Purcahse them at store.visage.rip");
						return;
					}
						profile.getLives().put(ProfileProtectionLifeType.SOULBOUND, profile.getLives().get(ProfileProtectionLifeType.SOULBOUND) - 1);
						profile.setDeathban(null);
						profile.save();
						p.sendMessage(ChatColor.GREEN + "You have revived yourself. You now have " + profile.getLives().get(ProfileProtectionLifeType.SOULBOUND).intValue() + " lives.");

					
					return;
				}
				((Player)e.getWhoClicked()).chat("/play hcf");
			} else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Kits")) {
				((Player)e.getWhoClicked()).chat("/play kitmap");
			} else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Practice")) {
				 ((Player)e.getWhoClicked()).chat("/play Practice");
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryDragEvent event) {
		event.setCancelled(true);
	}

	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		event.setCancelled(true);
	}
	


	@EventHandler
	public void onInventoryClick(InventoryInteractEvent event) {
		event.setCancelled(true);
	}
}
