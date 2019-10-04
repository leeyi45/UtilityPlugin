package com.gmail.leeyi45.utilityPlugin.commands.sleepersCommand;

import com.gmail.leeyi45.utilityPlugin.SleepProcessor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SleepersCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if(args.length == 0)
        {
            if(SleepProcessor.getEnabled())
            {
                var players = SleepProcessor.getSleepingPlayers();

                if (players.size() > 0)
                {
                    var str = new StringBuilder("Players who are currently sleeping\n\n");

                    for (int i = 0; i < players.size(); i++)
                    {
                        str.append(i + 1);
                        str.append(". ");
                        str.append(players.get(i).getDisplayName());
                        str.append("\n");
                    }
                    sender.sendMessage(str.toString());
                }
                else sender.sendMessage(SleepProcessor.getPrefix() + " There are no sleeping players");
            }
            else sender.sendMessage(SleepProcessor.getPrefix() + " Sleep plugin is currently disabled!");

            return true;
        }
        else
        {
            if(!sender.hasPermission("utility.sleepers.admin"))
            {
                sender.sendMessage("You do not have permission to use this command!");
                return true;
            }
            else
            {
                switch (args[0].toLowerCase())
                {
                    case "reset":
                    {
                        SleepProcessor.resetSleepers();
                        sender.sendMessage(SleepProcessor.getPrefix() + " Reset internal sleeping players array");
                        return true;
                    }
                    case "toggle":
                    {
                        SleepProcessor.setEnabled(!SleepProcessor.getEnabled());
                        sender.sendMessage(String.format("%s %s sleep plugin",
                            SleepProcessor.getPrefix(),
                            (SleepProcessor.getEnabled() ? "Enabled" : "Disabled")));
                        return true;
                    }
                    default:
                        return false;
                }
            }
        }
    }
}
