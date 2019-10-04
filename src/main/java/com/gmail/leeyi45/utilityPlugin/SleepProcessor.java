package com.gmail.leeyi45.utilityPlugin;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_14_R1.ChatComponentText;
import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.ArrayList;

public class SleepProcessor
{
    private static ArrayList<Player> sleepingPlayers = new ArrayList<>();
    private static boolean timeSetting = false;
    private static boolean enabled = true;
    private static String prefix;

    public static String getPrefix() { return prefix; }
    public static void setPrefix(String p)
    {
        prefix = ChatColor.translateAlternateColorCodes('&', p);
    }

    private static int threshold;
    public static void setThreshold(int x) { threshold = x; }

    public static ArrayList<Player> getSleepingPlayers() { return sleepingPlayers; }

    public static void resetSleepers() { sleepingPlayers = new ArrayList<>(); }
    public static void setEnabled(boolean value) { enabled = value; }
    public static boolean getEnabled() { return enabled; }

    public static void leaveBed(Player player)
    {
        if(timeSetting || !enabled) return;

        sleepingPlayers.remove(player);

        int totalPlayers = UtilityPlugin.getInstance().getServer().getOnlinePlayers().size();
        int sleepCount = sleepingPlayers.size();
        int percent = (int)Math.round((double)sleepCount/totalPlayers*100);

        Bukkit.broadcastMessage(String.format("%s %s left a bed, %d out of %d player%s %s (%d%%) now sleeping",
            prefix,
            player.getDisplayName(),
            sleepCount, totalPlayers,
            totalPlayers == 1 ? "" : "s",
            sleepCount == 1 ? " is" : "are", percent));
    }

    public static void enterBed(PlayerBedEnterEvent event)
    {
        PlayerBedEnterEvent.BedEnterResult result = event.getBedEnterResult();
        Player player = event.getPlayer();

        if(result == PlayerBedEnterEvent.BedEnterResult.OK)
        {
            if(!enabled) return;

            sleepingPlayers.add(player);
            int totalPlayers = UtilityPlugin.getInstance().getServer().getOnlinePlayers().size();

            int sleepCount = sleepingPlayers.size();
            int percent = (int)Math.round((double)sleepCount/totalPlayers*100);

            Bukkit.broadcastMessage(String.format("%s %s entered a bed, %d out of %d player%s %s (%d%%) now sleeping",
                prefix,
                player.getDisplayName(),
                sleepCount, totalPlayers,
                totalPlayers == 1 ? "" : "s",
                sleepCount == 1 ? "is" : "are", percent));

            if(percent >= threshold)
            { //Skip time
                timeSetting = true;
                World w = event.getBed().getWorld();
                w.setTime(0);
                w.setStorm(false);
                w.setThundering(false);
                Bukkit.broadcastMessage(prefix + " Skipping time to day...");

                new Thread(() ->
                {
                    try { Thread.sleep(200); }
                    catch(InterruptedException ignore) { }
                    timeSetting = false;
                }).run();

                sleepingPlayers.forEach(p -> p.setStatistic(Statistic.TIME_SINCE_REST, 0));
                sleepingPlayers.clear();
            }
        }
        else if(result == PlayerBedEnterEvent.BedEnterResult.NOT_POSSIBLE_NOW)
        {
            player.setBedSpawnLocation(event.getBed().getLocation());

            PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText("Spawnpoint has been set"), ChatMessageType.GAME_INFO);

            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
            event.setCancelled(true);
        }
    }
}
