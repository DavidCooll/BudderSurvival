package me.davidcooll13.buddersurvival2;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class SquidEvent implements Listener {
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
	  
	     LivingEntity entity = e.getEntity();

	    if(entity.getType() == EntityType.SQUID) {
	    	ItemStack budder = new ItemStack(Material.GOLD_INGOT, 1);
	    	ItemMeta meta = budder.getItemMeta();
	    	meta.setDisplayName(ChatColor.GOLD + "Budder");
	    	meta.setLore(Arrays.asList("Right click me to get some XP!"));
	    	budder.setItemMeta(meta);
	         entity.getKiller().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
	         entity.getKiller().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
	         entity.getKiller().getInventory().addItem(budder);
	    }

	}
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent ev){   
        Player player = ev.getPlayer();
    	ItemStack budder = new ItemStack(Material.GOLD_INGOT, 1);
    	ItemMeta meta = budder.getItemMeta();
    	meta.setDisplayName(ChatColor.GOLD + "Budder");
    	meta.setLore(Arrays.asList("Right click me to get some XP!"));
    	budder.setItemMeta(meta);
    	if(player.getItemInHand().getType()==Material.GOLD_INGOT && player.getItemInHand().getItemMeta().equals(meta) && (ev.getAction().equals(Action.RIGHT_CLICK_AIR) || ev.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
            player.setLevel(player.getLevel() + 1);
            player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Gained 1 experience");
    		player.getInventory().removeItem(budder);
            }
        }
    }
