package com.xhizors.TeamManager;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class TeamManager extends JavaPlugin{
	
	private final TeamManagerEntityListener entityListener = new TeamManagerEntityListener(this);
	private PermissionHandler permissionHandler = null;
	public static final Logger log = Logger.getLogger("Minecraft");
	public static String name;
	public ArrayList<String> worlds;
	public TeamManagerReader reader;
	private PluginManager pm;
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		log.info("["+name+"] is now disabled.");
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		pm = getServer().getPluginManager();
		PluginDescriptionFile pdf = this.getDescription();
		name = pdf.getName();
		reader = new TeamManagerReader(this);
		setupPermissions();
		worlds = reader.readWorldFile();
		
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.High, this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return false;
	}
	
	private void setupPermissions() {
	    if (permissionHandler != null) {
	        return;
	    }
	    
	    Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
	    
	    if (permissionsPlugin == null) {
	        log.warning("["+name+"] Permissions System not found. Disabling plugin.");
	        setEnabled(false);
	        return;
	    }
	    
	    permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	}
	
	public PermissionHandler getPermissionHandler() {
		return permissionHandler;
	}

}
