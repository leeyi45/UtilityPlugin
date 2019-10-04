package com.gmail.leeyi45.utilityPlugin.commands.whereisCommand;

import com.gmail.leeyi45.utilityPlugin.UtilityPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WhereIsTabCompleter implements TabCompleter
{
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings)
    {
        var players = UtilityPlugin.playersByName();
        var output = new ArrayList<String>();

        for(Map.Entry<String, Player> p : players.entrySet()) output.add(p.getKey());

        return output;
    }
}
