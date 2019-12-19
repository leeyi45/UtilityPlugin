package com.gmail.leeyi45.utilityPlugin.commands.whereisCommand;

import com.gmail.leeyi45.utilityPlugin.UtilityPlugin;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WhereIsCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if(args.length != 1) return false;
        else
        {
            HashMap<String, Player> players = UtilityPlugin.playersByName();

            if(players.containsKey(args[0]))
            {
                Player player = players.get(args[0]);

                String dim = "";
                switch(player.getWorld().getEnvironment())
                {
                    case NORMAL: dim = "overworld"; break;
                    case NETHER: dim = "nether"; break;
                    case THE_END: dim = "end"; break;
                }

                Location loc = player.getLocation();

                sender.sendMessage(String.format("%s is at (%d, %d, %d) in the %s",
                        args[0], loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), dim));
            }
            else sender.sendMessage("No such player found");

            return true;
        }
    }
}
