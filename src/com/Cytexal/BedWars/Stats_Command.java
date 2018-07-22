package com.Cytexal.BedWars;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Stats_Command implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String lable, String args[]) {

		if (cmd.getName().equalsIgnoreCase("stats") || cmd.getName().equalsIgnoreCase("st")) {
			Player p = (Player) sender;
			if(!p.isOp())
			{
				return false;
			}	
		}
		return false;
	}

}