package com.gmail.leeyi45.utilityPlugin.listeners;

import com.gmail.leeyi45.utilityPlugin.NicknameProcessor;
import com.gmail.leeyi45.utilityPlugin.SleepProcessor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatEventListener implements Listener
{
    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent e)
    {
        String nick = NicknameProcessor.getNickname(e.getPlayer().getUniqueId());

        if(nick != null)
        {
            String newMsg = String.format("<%s> %s", NicknameProcessor.getEnabled() ? nick : e.getPlayer().getDisplayName(), e.getMessage());

            if(e.getPlayer().hasPermission("utility.chatcolor"))
            {
                newMsg = ChatColor.translateAlternateColorCodes('&', newMsg);
            }

            e.setFormat(newMsg);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        if(!NicknameProcessor.getEnabled()) return;

        String nick = NicknameProcessor.getNickname(e.getPlayer().getUniqueId());

        if(nick != null)
        {
            String joinMsg = e.getJoinMessage();
            e.setJoinMessage(joinMsg.replace(e.getPlayer().getDisplayName(), nick));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e)
    {
        if(!NicknameProcessor.getEnabled()) return;

        Player p = e.getPlayer();

        String nick = NicknameProcessor.getNickname(p.getUniqueId());

        if(nick != null)
        {
            String quitMsg = e.getQuitMessage();
            e.setQuitMessage(quitMsg.replace(p.getDisplayName(), nick));
        }

        if(SleepProcessor.getSleepingPlayers().contains(p.getDisplayName()))
            SleepProcessor.leaveBed(p);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        if(!NicknameProcessor.getEnabled()) return;

        String nick = NicknameProcessor.getNickname(e.getEntity().getUniqueId());

        if(nick != null)
        {
            String deathMessage = e.getDeathMessage();
            e.setDeathMessage(deathMessage.replace(e.getEntity().getDisplayName(), nick));
        }
    }
}
