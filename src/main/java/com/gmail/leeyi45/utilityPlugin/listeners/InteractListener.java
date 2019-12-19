package com.gmail.leeyi45.utilityPlugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class InteractListener implements Listener
{
    private static int genRand(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if(e.getPlayer().isSneaking()) return;

            Block clickedBlock = e.getClickedBlock();

            ItemStack willDrop, mayDrop;

            switch(clickedBlock.getType())
            {
                //Random chances retrieved from minecraft wiki
                case WHEAT:
                {
                    if(clickedBlock.getBlockData().matches(Bukkit.createBlockData("minecraft:wheat[age=7]")))
                    {
                        clickedBlock.setBlockData(Bukkit.createBlockData("minecraft:wheat[age=0]"));

                        willDrop = new ItemStack(Material.WHEAT, 1);

                        int seedCount = genRand(0, 3);
                        mayDrop = (seedCount == 0) ? null : new ItemStack(Material.WHEAT_SEEDS, seedCount);

                        break;
                    }
                    else return;
                }
                case CARROTS:
                {
                    if(clickedBlock.getBlockData().matches(Bukkit.createBlockData("minecraft:carrots[age=7]")))
                    {
                        clickedBlock.setBlockData(Bukkit.createBlockData("minecraft:carrots[age=0]"));

                        willDrop = new ItemStack(Material.CARROT, genRand(1, 5));
                        mayDrop = null;

                        break;
                    }
                    else return;
                }
                case POTATOES:
                {
                    if(clickedBlock.getBlockData().matches(Bukkit.createBlockData("minecraft:potatoes[age=7]")))
                    {
                        clickedBlock.setBlockData(Bukkit.createBlockData("minecraft:potatoes[age=0]"));

                        willDrop = new ItemStack(Material.POTATO, genRand(1, 4));

                        mayDrop = (genRand(1, 50) == 1) ?
                                null : new ItemStack(Material.POISONOUS_POTATO, 1);
                        break;
                    }
                    else return;
                }
                case BEETROOTS:
                {
                    if(clickedBlock.getBlockData().matches(Bukkit.createBlockData("minecraft:beetroots[age=7]")))
                    {
                        clickedBlock.setBlockData(Bukkit.createBlockData("minecraft:beetroots[age=0]"));

                        willDrop = new ItemStack(Material.BEETROOT, 1);

                        int count = genRand(0, 3);
                        mayDrop = (count == 0) ? null : new ItemStack(Material.BEETROOT_SEEDS);

                        break;
                    }
                    else return;
                }
                default: return;
            }

            e.setCancelled(true);

            Location location = clickedBlock.getLocation();
            clickedBlock.getWorld().dropItemNaturally(location, willDrop);
            if(mayDrop != null) clickedBlock.getWorld().dropItemNaturally(location, mayDrop);
        }
    }
}
