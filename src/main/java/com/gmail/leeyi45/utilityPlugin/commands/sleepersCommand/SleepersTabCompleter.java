package com.gmail.leeyi45.utilityPlugin.commands.sleepersCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class SleepersTabCompleter implements TabCompleter
{
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args)
    {
        return Arrays.asList("toggle", "reset");
    }
}
