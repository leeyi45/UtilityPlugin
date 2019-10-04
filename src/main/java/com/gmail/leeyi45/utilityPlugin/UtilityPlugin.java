package com.gmail.leeyi45.utilityPlugin;

import com.gmail.leeyi45.utilityPlugin.commands.nickCommand.*;
import com.gmail.leeyi45.utilityPlugin.commands.sleepersCommand.*;
import com.gmail.leeyi45.utilityPlugin.commands.whereisCommand.*;
import com.gmail.leeyi45.utilityPlugin.listeners.BedEventListener;
import com.gmail.leeyi45.utilityPlugin.listeners.ChatEventListener;
import com.gmail.leeyi45.utilityPlugin.listeners.InteractListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class UtilityPlugin extends JavaPlugin
{
    private static UtilityPlugin instance;
    private static FileConfiguration config;

    public static UtilityPlugin getInstance() { return instance; }

    public static FileConfiguration getConfigFile() { return config; }

    public static HashMap<String, Player> playersByName()
    {
        var output = new HashMap<String, Player>();

        for(Player p : instance.getServer().getOnlinePlayers())
        {
            output.put(p.getName(), p);
        }

        return output;
    }

    @Override
    public void onEnable()
    {
        instance = this;

        config = getConfig();

        var manager = getServer().getPluginManager();
        manager.registerEvents(new BedEventListener(), this);
        manager.registerEvents(new ChatEventListener(), this);
        manager.registerEvents(new InteractListener(), this);

        getCommand("sleepers").setExecutor(new SleepersCommand());
        getCommand("sleepers").setTabCompleter(new SleepersTabCompleter());

        getCommand("nick").setExecutor(new NickCommand());
        getCommand("nick").setTabCompleter(new NickTabCompleter());

        getCommand("whereis").setExecutor(new WhereIsCommand());
        getCommand("whereis").setTabCompleter(new WhereIsTabCompleter());

        NicknameProcessor.loadNicknames(getServer().getConsoleSender());
        NicknameProcessor.setEnabled(config.getBoolean("nick.enabled", true));

        SleepProcessor.setEnabled(config.getBoolean("sleep.enabled", true));
        SleepProcessor.setPrefix(config.getString("sleep.prefix", "[Sleep Plugin]"));
        SleepProcessor.setThreshold(config.getInt("sleep.threshold", 50));
    }

    @Override
    public void onDisable()
    {
        if(NicknameProcessor.hasChanges()) NicknameProcessor.saveNicknames(getServer().getConsoleSender());
    }
}
