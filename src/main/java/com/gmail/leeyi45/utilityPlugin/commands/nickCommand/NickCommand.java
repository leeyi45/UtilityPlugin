package com.gmail.leeyi45.utilityPlugin.commands.nickCommand;

import com.gmail.leeyi45.utilityPlugin.NicknameProcessor;
import com.gmail.leeyi45.utilityPlugin.UtilityPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NickCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if(args.length >= 1)
        {
            HashMap<String, Player> players = UtilityPlugin.playersByName();

            switch(args[0].toLowerCase())
            {
                case "get":
                {
                    if(!sender.hasPermission("utility.nick.get")) sender.sendMessage("You do not have permission to use this command");
                    else
                    {
                        if (args.length != 2) sender.sendMessage("/nick get <player>");
                        else
                        {
                            Player player = players.getOrDefault(args[1], null);

                            if (player != null)
                            {
                                String nick = NicknameProcessor.getNickname(player.getUniqueId());

                                sender.sendMessage(args[1] +
                                        (nick != null ? "'s nickname is " + nick : " has no nickname"));
                            }
                            else sender.sendMessage("Couldn't find player " + args[1]);
                        }
                    }
                    break;
                }
                case "set":
                {
                    if(args.length < 3) sender.sendMessage("/nick set <player> <nickname>");
                    else
                    {
                        Player player = players.getOrDefault(args[1], null);

                        if(!sender.hasPermission("utility.nick.set"))
                        {
                            sender.sendMessage("You do not have permission to use this command");
                            break;
                        }
                        else if(player != sender && !sender.hasPermission("utility.nick.set.others"))
                        {
                            sender.sendMessage("You do not have permission to set the nickname for others");
                            break;
                        }

                        if(player != null)
                        {
                            String str = ChatColor.translateAlternateColorCodes( '&',
                                            String.join(" ", Arrays.copyOfRange(args, 2, args.length)));

                            for(Map.Entry<UUID, String> each : NicknameProcessor.getNicknames().entrySet())
                            {
                                if(each.getValue().equalsIgnoreCase(str))
                                {
                                    Player existing = UtilityPlugin.getInstance().getServer().getPlayer(each.getKey());
                                    sender.sendMessage(String.format("Another player %s already has the nickname '%s§f'!",
                                            existing.getName(), str));
                                    return true;
                                }
                            }

                            NicknameProcessor.setNickname(player.getUniqueId(), str);
                            sender.sendMessage("Nickname for " + args[1] + " set to " + str);
                        }
                        else sender.sendMessage("Couldn't find player " + args[1]);
                    }
                    break;
                }
                case "clear":
                {
                    if(args.length != 2) sender.sendMessage("/nick clear <player>");
                    else
                    {
                        Player player = players.getOrDefault(args[1], null);

                        if(!sender.hasPermission("utility.nick.set"))
                        {
                            sender.sendMessage("You do not have permission to use this command");
                            break;
                        }
                        else if(player != sender && !sender.hasPermission("utility.nick.set.others"))
                        {
                            sender.sendMessage("You do not have permission to set the nickname for others");
                            break;
                        }

                        if(player != null)
                        {
                            NicknameProcessor.clearNickname(player.getUniqueId());
                            sender.sendMessage("Cleared the nickname for " + args[0]);
                        }
                        else sender.sendMessage("Couldn't find player " + args[1]);
                    }
                    break;
                }
                case "load":
                {
                    if(sender.hasPermission("utility.nick.load")) NicknameProcessor.loadNicknames(sender);
                    else sender.sendMessage("You do not have permission to use this command");
                    break;
                }
                case "save":
                {
                    if(sender.hasPermission("utility.nick.save")) NicknameProcessor.saveNicknames(sender);
                    else sender.sendMessage("You do not have permission to use this command");
                    break;
                }
                case "toggle":
                {
                    if(sender.hasPermission("utility.nick.toggle"))
                    {
                        NicknameProcessor.setEnabled(!NicknameProcessor.getEnabled());
                        sender.sendMessage("Nicknames are currently " + (NicknameProcessor.getEnabled() ? "enabled" : "disabled"));
                    }
                    else sender.sendMessage("You do not have permission to use this command");
                    break;
                }
                case "check":
                {
                    if(args.length <= 1) sender.sendMessage("/nick check <nickname>");
                    else
                    {
                        String nickStr = ChatColor.stripColor(String.join(" ",
                                Arrays.copyOfRange(args, 2, args.length)));

                        HashMap<UUID, String> nicknames = NicknameProcessor.getNicknames();

                        for(Map.Entry<UUID, String> entry : nicknames.entrySet())
                        {
                            if(ChatColor.stripColor(entry.getValue()).equalsIgnoreCase(nickStr))
                            {
                                Player p = UtilityPlugin.getInstance().getServer().getPlayer(entry.getKey());

                                if(p == null)
                                {
                                    sender.sendMessage("Failed to get nickname");
                                    return true;
                                }

                                sender.sendMessage(String.format("Player '%s' has nickname '%s'",
                                        p.getDisplayName(), nickStr));
                                return true;
                            }
                        }

                        sender.sendMessage("There is no such player with that nickname");
                    }
                    break;
                }
                default: return false;
            }
            return true;
        }
        else return false;
    }
}
