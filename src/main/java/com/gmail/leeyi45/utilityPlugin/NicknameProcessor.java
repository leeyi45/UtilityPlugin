package com.gmail.leeyi45.utilityPlugin;

import org.bukkit.command.CommandSender;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NicknameProcessor
{
    private static HashMap<UUID, String> nicknames;
    private static boolean changesMade = false;
    private static boolean enabled = true;

    public static boolean hasChanges() { return changesMade; }

    public static void setEnabled(boolean val) { enabled = val; }

    public static boolean getEnabled() { return enabled; }

    public static UUID getPlayer(String name)
    {
        if(nicknames.containsValue(name))
        {
            for(Map.Entry<UUID, String> thing : nicknames.entrySet())
            {
                if(thing.getValue().equalsIgnoreCase(name)) return thing.getKey();
            }
            throw new NullPointerException();
        }
        else return null;
    }

    public static String getNickname(UUID player) { return nicknames.getOrDefault(player, null); }

    public static HashMap<UUID, String> getNicknames() { return nicknames; }

    public static void clearNickname(UUID player)
    {
        changesMade = true;
        nicknames.remove(player);
    }

    public static void setNickname(UUID player, String name)
    {
        changesMade = true;
        nicknames.put(player, name);
    }

    public static void saveNicknames(CommandSender sender)
    {
        try
        {
            PrintWriter writer = new PrintWriter(new FileWriter("nicknames.txt"));
            for (Map.Entry<UUID, String> entry : nicknames.entrySet())
            {
                writer.println(entry.getKey().toString() + "," + entry.getValue());
            }

            writer.close();
            changesMade = false;
            sender.sendMessage("Successfully saved nicknames file");
        }
        catch(IOException e)
        {
            sender.sendMessage("IOException occurred when saving nicknames file");
        }
    }

    public static void loadNicknames(CommandSender sender)
    {
        HashMap<UUID, String> temp = new HashMap<>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("nicknames.txt"));

            String st;
            int line = 0;
            while ((st = reader.readLine()) != null)
            {
                line++;
                try
                {
                    String[] args = st.split(",");
                    temp.put(UUID.fromString(args[0]), args[1]);
                }
                catch(IllegalArgumentException e)
                {
                    sender.sendMessage("Error parsing UUID at line " + line);
                }
                catch(IndexOutOfBoundsException e)
                {
                    sender.sendMessage("Invalid nickname specified at line " + line);
                }
            }

            reader.close();
        }
        catch(IOException e)
        {
            sender.sendMessage("IOException occurred when loading nicknames file");
        }

        nicknames = temp;
        sender.sendMessage("Successfully loaded from nicknames file");
    }
}
