package me.DavidCooll13.survivalEssentials;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class HomeCommand implements CommandExecutor {
	
	File file = new File("plugins/SurvivalEssentials", "homes.yml");
	FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (args.length == 1) {
			
			String world = cfg.getString(p.getName() + "." + args[0] + ".world");
			double x = cfg.getDouble(p.getName() + "." + args[0] + ".x");
			double y = cfg.getDouble(p.getName() + "." + args[0] + ".y");
			double z = cfg.getDouble(p.getName() + "." + args[0] + ".z");
			double yaw = cfg.getDouble(p.getName() + "." + args[0] + ".yaw");
			double pitch = cfg.getDouble(p.getName() + "." + args[0] + ".pitch");
			
			Location loc = new Location(Bukkit.getWorld(world), x, y, z);
			loc.setPitch((float) pitch);
			loc.setYaw((float) yaw);
			
			p.teleport(loc);
			p.sendMessage(ChatColor.GREEN + "Teleported to home " + args[0] + "!");
			
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /home <name>");
		}
		return true;
	}
	
	
	
	
	
}
