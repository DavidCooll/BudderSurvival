package me.davidcooll13.buddersurvival2;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import me.davidcooll13.buddersurvival2.SettingsManager;

public class Main
  extends JavaPlugin
  implements Listener
{
	public final SquidEvent se = new SquidEvent();
  SettingsManager settings = SettingsManager.getInstance();
  private HashMap<Player, Player> tpaHash;

  @SuppressWarnings({ "unchecked", "rawtypes" })
public void onEnable()
  {
	Bukkit.getServer().getPluginManager().registerEvents(new SquidEvent(), this); // should work 
	
    this.settings.setup(this);
    this.tpaHash = new HashMap();
  }
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Only players can use this plugin.");
      return true;
    }
    Player player = (Player)sender;
    if (cmd.getName().equalsIgnoreCase("setspawn")) {
    	if (player.isOp()) {
        getConfig().set("spawn.world", player.getLocation().getWorld().getName());
        getConfig().set("spawn.x", player.getLocation().getX());
        getConfig().set("spawn.y", player.getLocation().getY());
        getConfig().set("spawn.z", player.getLocation().getZ());
        saveConfig();
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Succesfully set the spawn in the world '" + player.getWorld().getName() + "'." );
        return true;
    	} else {
    		player.sendMessage(ChatColor.RED + "You need to be OP to set the spawn :(");
    	}
}

if (cmd.getName().equalsIgnoreCase("spawn")) {
        if (getConfig().getConfigurationSection("spawn") == null) {
                player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.RED + "The spawn has not yet been set!");
                return true;
        }
        World w = Bukkit.getServer().getWorld(getConfig().getString("spawn.world"));
        double x = getConfig().getDouble("spawn.x");
        double y = getConfig().getDouble("spawn.y");
        double z = getConfig().getDouble("spawn.z");
        player.teleport(new Location(w, x, y, z));
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Successfully teleported to spawn!");
}
    
    if (cmd.getName().equalsIgnoreCase("tpa"))
    {
      if (args.length < 1)
      {
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Not enough arguments. Usage: /tpa <name>");
        return true;
      }
      Player target = Bukkit.getPlayer(args[0]);
      if (target == null)
      {
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.RED + args[0] + ChatColor.YELLOW + " is not online or is not a real player!");
        return true;
      }
      if (!target.isOnline())
      {
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.RED + args[0] + ChatColor.YELLOW + " is not online or is not a real player!");
        return true;
      }
      target.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + player.getName() + ChatColor.YELLOW + " is requesting to teleport to you. Use /tpaccept to accept, or /tpdeny to deny.");
      player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Teleport request successfully sent!");
      if (this.tpaHash.get(target) == null)
      {
        this.tpaHash.put(target, player);
        return true;
      }
      this.tpaHash.remove(target);
      this.tpaHash.put(target, player);
    }
    if (cmd.getName().equalsIgnoreCase("tpaccept"))
    {
      if (this.tpaHash.get(player) == null)
      {
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.RED + "No pending teleport requests.");
        return true;
      }
      Player request = (Player)this.tpaHash.get(player);
      if (!request.isOnline())
      {
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + request.getName() + ChatColor.YELLOW + " is no longer online.");
        this.tpaHash.remove(player);
        return true;
      }
      player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Teleport request accepted.");
      request.teleport(player);
      this.tpaHash.remove(player);
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("tpdeny"))
    {
      if (this.tpaHash.get(player) == null)
      {
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "No pending teleport requests.");
        return true;
      }
      Player request = (Player)this.tpaHash.get(player);
      if (!request.isOnline())
      {
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.BLUE + request.getName() + ChatColor.RED + " is no longer online.");
        this.tpaHash.remove(player);
        return true;
      }
      player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Teleport request denied.");
      this.tpaHash.remove(player);
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("home"))
    {
      if (this.settings.getData().getConfigurationSection("home." + player.getName()) == null)
      {
        player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.RED + "Your Home has not yet been set!");
        return true;
      } if(!settings.getData().contains("home." + player.getName() + ".world")) {
	      player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "You dont have any homes.");
	      return true;
      }
      World w = Bukkit.getServer().getWorld(this.settings.getData().getString("home." + player.getName() + ".world"));
      double x = this.settings.getData().getDouble("home." + player.getName() + ".x");
      double y = this.settings.getData().getDouble("home." + player.getName() + ".y");
      double z = this.settings.getData().getDouble("home." + player.getName() + ".z");
      player.teleport(new Location(w, x, y, z));
      player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Teleported to your Home.");
    } 
    if (cmd.getName().equalsIgnoreCase("sethome"))
    {
      this.settings.getData().set("home." + player.getName() + ".world", player.getLocation().getWorld().getName());
      this.settings.getData().set("home." + player.getName() + ".x", Double.valueOf(player.getLocation().getX()));
      this.settings.getData().set("home." + player.getName() + ".y", Double.valueOf(player.getLocation().getY()));
      this.settings.getData().set("home." + player.getName() + ".z", Double.valueOf(player.getLocation().getZ()));
      this.settings.saveData();
      player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Home has been set");
      return true;
    }    
    if (cmd.getName().equalsIgnoreCase("delhome")) {
    	if(settings.getData().contains("home")) {
            settings.getData().set("home." + player.getName() + ".world", null);
            settings.getData().set("home." + player.getName() + ".x", null);
            settings.getData().set("home." + player.getName() + ".y", null);
            settings.getData().set("home." + player.getName() + ".z", null);
    		settings.saveData();
    	      player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "Your home has been removed.");

    			}else{
    			      player.sendMessage(ChatColor.GREEN + "Survival >> " + ChatColor.YELLOW + "You dont have any homes.");

    			}
    		
    	
    }
    return true;
  }
}
