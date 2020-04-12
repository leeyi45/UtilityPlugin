package com.gmail.leeyi45.utilityPlugin.listeners;

import com.gmail.leeyi45.utilityPlugin.SleepProcessor;
import com.gmail.leeyi45.utilityPlugin.UtilityPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class BedEventListener implements Listener
{
    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event)
    {
        SleepProcessor.enterBed(event);
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event)
    {
        SleepProcessor.leaveBed(event.getPlayer());

        int totalPlayers = UtilityPlugin.getInstance().getServer().getOnlinePlayers().size();
        int sleepCount = SleepProcessor.getSleepingPlayers().size();
        int percent = (int)Math.round((double)sleepCount/totalPlayers*100);

        SleepProcessor.playerChanged(percent, event.getBed().getWorld());
    }
}
