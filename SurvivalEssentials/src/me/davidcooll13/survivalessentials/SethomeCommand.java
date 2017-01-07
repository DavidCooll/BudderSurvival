package me.DavidCooll13.survivalEssentials;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SethomeCommand implements CommandExecutor {
	
	File file = new File("plugins/SurvivalEssentials", "homes.yml");
	FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		Player p = (Player) sender;
		
		if (args.length==1) {
			if (!cfg.contains(args[0])) {
			String world = p.getWorld().getName();
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			double yaw = p.getLocation().getYaw();
			double pitch = p.getLocation().getPitch();
			
			cfg.set(p.getName() + "." + args[0] + ".world", world);
			cfg.set(p.getName() + "." + args[0] + ".x", x);
			cfg.set(p.getName() + "." + args[0] + ".y", y);
			cfg.set(p.getName() + "." + args[0] + ".z", z);
			cfg.set(p.getName() + "." + args[0] + ".yaw", yaw);
			cfg.set(p.getName() + "." + args[0] + ".pitch", pitch);
			try {
				cfg.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			p.sendMessage(ChatColor.GREEN+ "Home " + args[0] + " set!");
			} else {
				p.sendMessage(ChatColor.RED + "The home " + args[0] + " Already exists!");
			}
		} else {
			p.sendMessage("§4Usage: /sethome [name]");
		}
	return true;
	}
					
}
