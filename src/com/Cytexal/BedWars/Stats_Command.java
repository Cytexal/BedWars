package com.Cytexal.BedWars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Stats_Command implements CommandExecutor {

	@Override
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