package com.gmail.leeyi45.utilityPlugin.listeners;

import com.gmail.leeyi45.utilityPlugin.NicknameProcessor;
import com.gmail.leeyi45.utilityPlugin.SleepProcessor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatEventListener implements Listener
{
    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent e)
    {
        if(e.getPlayer().hasPermission("utility.chatcolor"))
        {
           String newMsg = ChatColor.translateAlternateColorCodes('&', e.getMessage());
           e.setMessage(newMsg);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        if(NicknameProcessor.getEnabled())
        {
            String nick = NicknameProcessor.getNickname(e.getPlayer().getUniqueId());

            if (nick != null)
            {
                e.setJoinMessage(nick + " joined the game");
                e.getPlayer().setDisplayName(nick);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();

        if(SleepProcessor.getSleepingPlayers().contains(p.getDisplayName()))
            SleepProcessor.leaveBed(p);
    }
}
