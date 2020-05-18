package rip.vexus.lobby.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import rip.vexus.lobby.LobbyPlugin;
import rip.vexus.lobby.user.User;

public class PlayerListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if(e.isCancelled()) return;
		e.setCancelled(true);
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', LobbyPlugin.getPlugin().getPermissionsService().getPlayerPrefix(e.getPlayer().getUniqueId())) + e.getPlayer().getName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + e.getMessage());
		}
	}
	
	@EventHandler
	public void onInteract(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onInteract(PlayerPickupItemEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getItem().getType() == Material.ENDER_PEARL) {
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				e.getPlayer().launchProjectile(EnderPearl.class);
				
				e.setCancelled(true);
				e.getPlayer().updateInventory();
				return;
			}
			return;
		} else if(e.getItem().getItemMeta().getDisplayName().contains("Hidden")) {
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				LobbyPlugin.getPlugin().getProfileManager().getProfile(e.getPlayer()).setPlayerVisibility(!LobbyPlugin.getPlugin().getProfileManager().getProfile(e.getPlayer()).getPlayerVisibility());
				giveItems(e.getPlayer());
				e.setCancelled(true);
				return;
			}
			return;
		} else if(e.getItem().getItemMeta().getDisplayName().contains("Visible")) {
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				LobbyPlugin.getPlugin().getProfileManager().getProfile(e.getPlayer()).setPlayerVisibility(!LobbyPlugin.getPlugin().getProfileManager().getProfile(e.getPlayer()).getPlayerVisibility());
				giveItems(e.getPlayer());
				e.setCancelled(true);
				return;
			}
			return;
		}
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
	    Projectile proj = event.getEntity();
	    if (proj instanceof EnderPearl) {
	        EnderPearl pearl = (EnderPearl)proj;
	        ProjectileSource source = pearl.getShooter();
	        if (source instanceof Player) {
	            Player player = (Player)source;
	            pearl.setPassenger(player);
	            pearl.setVelocity(player.getLocation().getDirection().normalize().multiply(2.0F));
	            player.spigot().setCollidesWithEntities(false);
	        }
	    }
	}
	
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == TeleportCause.ENDER_PEARL){
			e.setCancelled(true);
		}
	}
		
		
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
		e.getEntity().setHealth(20);
	}
	
	@EventHandler
	public void onEntityDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		e.getEntity().setHealth(20);
		e.getDrops().clear();
		giveItems(e.getEntity());
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		e.setJoinMessage(null);
		Player p = e.getPlayer();
		p.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + 
    "---------------------------------------------------");
	    p.sendMessage(ChatColor.AQUA + "Welcome to the " + ChatColor.BOLD.toString() + ChatColor.BOLD + 
	    	      "Visage Network");
	    	    p.sendMessage("");
	    	    p.sendMessage(ChatColor.AQUA + "Teamspeak" + ChatColor.DARK_RED + " " + ChatColor.WHITE + 
	    	      "ts.visage.rip" + ChatColor.WHITE);
	    	    p.sendMessage(ChatColor.AQUA + "Twitter" + ChatColor.DARK_RED + " " + ChatColor.WHITE + 
	    	      "twitter.com/VisageNetwork" + ChatColor.WHITE);
	    	    p.sendMessage(ChatColor.AQUA + "Website" + ChatColor.DARK_RED + " " + ChatColor.WHITE + 
	    	      "www.visage.rip" + ChatColor.WHITE);
	    	    p.sendMessage(ChatColor.AQUA + "Store" + ChatColor.DARK_RED + " " + ChatColor.WHITE + 
	    	      "store.visage.rip" + ChatColor.WHITE);
	    	    p.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + 
	    	    	    "---------------------------------------------------");
		giveItems(e.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
	
	@EventHandler
	public void onTabCokmplete(PlayerChatTabCompleteEvent e) {
		if(e.getChatMessage().contains(":") || this.betterContains(e.getChatMessage(), "?") || this.betterContains(e.getChatMessage(), "help") || this.betterContains(e.getChatMessage(), "pl") || this.betterContains(e.getChatMessage(), "plugins") || this.betterContains(e.getChatMessage(), "bukkit:") || this.betterContains(e.getChatMessage(), "me") || this.betterContains(e.getChatMessage(), "minecraft:")){
			e.getTabCompletions().clear();
		}
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if(this.betterContains(e.getMessage(), "version") || this.betterContains(e.getMessage(), "about") || this.betterContains(e.getMessage(), "ver") || this.betterContains(e.getMessage(), "icanhasbukkit") || e.getMessage().contains(":") || this.betterContains(e.getMessage(), "?") || this.betterContains(e.getMessage(), "help") || this.betterContains(e.getMessage(), "pl") || this.betterContains(e.getMessage(), "plugins") || this.betterContains(e.getMessage(), "bukkit:") || this.betterContains(e.getMessage(), "me") || this.betterContains(e.getMessage(), "minecraft:")){
			e.getPlayer().sendMessage(ChatColor.RED +  "You do not have permission to execute this command! ");
			e.setCancelled(true);
			return;
		}
	}
	
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(e.getTo().getY() < 0) {
			e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
			return;
		}
	}
	public boolean betterContains(String msg, String name) {
		if(msg.toLowerCase().startsWith("/" + name + " ") || msg.toLowerCase().equals("/" + name )) {
			return true;
		}
		return false;
	}
	public static void giveItems(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, Integer.MAX_VALUE));
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		item = new ItemStack(Material.COMPASS, 1);
		meta.setDisplayName(ChatColor.AQUA.toString() + "Server Selector" + ChatColor.GRAY + " (Right Click)");
		item.setItemMeta(meta);
		p.getInventory().setItem(3, item);
		

		
		item = new ItemStack(Material.ENDER_PEARL, 1);
		meta.setDisplayName(ChatColor.AQUA.toString() + "Enderbutts" + ChatColor.GRAY + " (Right Click)");
		item.setItemMeta(meta);
		p.getInventory().setItem(5, item);
		p.updateInventory();
		item = new ItemStack(Material.INK_SACK, 1, (short)10);
		User user = LobbyPlugin.getPlugin().getProfileManager().getProfile(p);
		if(!user.getPlayerVisibility()) {
			item = new ItemStack(Material.INK_SACK, 1, (short)10);
			meta.setDisplayName(ChatColor.AQUA.toString() + "Player Visibility" + ChatColor.GRAY + " (Status: " + ChatColor.GREEN + "Visible" + ChatColor.GRAY + ")");
			item.setItemMeta(meta);
			p.getInventory().setItem(8, item);
		} else {
			item = new ItemStack(Material.INK_SACK, 1, (short)8);
			meta.setDisplayName(ChatColor.AQUA.toString() + "Player Visibility" + ChatColor.GRAY + " (Status: " + ChatColor.RED + "Hidden" + ChatColor.GRAY + ")");
			item.setItemMeta(meta);
			p.getInventory().setItem(8, item);
		}
		
	}
	

	@EventHandler
	public void onBuild(BlockPlaceEvent e) {
		if(!e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBuild(BlockBreakEvent e) {
		if(!e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}
}
