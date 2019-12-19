package com.gmail.leeyi45.utilityPlugin.commands.nickCommand;

import com.gmail.leeyi45.utilityPlugin.UtilityPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class NickTabCompleter implements TabCompleter
{
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args)
    {
        switch(args.length)
        {
            case 1: return Arrays.asList("get", "set", "clear", "toggle", "load", "save", "check");
            case 2:
            {
                switch(args[1].toLowerCase())
                {
                    case "get":
                    case "set":
                    case "clear":
                    {
                        ArrayList<String> list = new ArrayList<>();

                        list.add("@s");
                        list.add("@a");

                        for(Player player : UtilityPlugin.getInstance().getServer().getOnlinePlayers())
                        {
                            list.add(player.getDisplayName());
                        }
                        return list;
                    }
                }
            }
        }
        return null;
    }
}
