package com.gmail.leeyi45.utilityPlugin.listeners;

import com.gmail.leeyi45.utilityPlugin.SleepProcessor;
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
    }
}
