package com.lendarium.ojvzinn.luckyblock.listeners;

import com.lendarium.ojvzinn.luckyblock.api.LuckyBlockEvent;
import com.lendarium.ojvzinn.luckyblock.lucky.LuckyManager;
import com.lendarium.ojvzinn.luckyblock.lucky.enuns.LuckyEnuns;
import com.lendarium.ojvzinn.luckyblock.lucky.objects.LuckyBlocks;
import com.lendarium.ojvzinn.luckyblock.utils.StringUtils;
import me.caneca.lendarium.chest.shop.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class Listeners implements Listener {

    public static void setupListeners() {
        Bukkit.getPluginManager().registerEvents(new Listeners(), Main.getInstance());
    }

    @EventHandler
    public void onPlayerBreakLuckyBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        if (LuckyManager.findByLocation(location.getBlock().getRelative(BlockFace.DOWN).getLocation()) != null) {
            LuckyBlocks luckyBlocks = LuckyManager.findByLocation(location.getBlock().getRelative(BlockFace.DOWN).getLocation());
            luckyBlocks.sortedReward(player, event);
            event.setCancelled(true);
        } else {
            if (block.getType().equals(Material.STONE)) {
                if (ThreadLocalRandom.current().nextDouble(100.0) < 0.2) {
                    //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lb give " + player.getName() + " " + LuckyEnuns.FERRAMENTAS + " " + 1);
                }

                if (ThreadLocalRandom.current().nextDouble(100.0) < 0.2) {
                    //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lb give " + player.getName() + " " + LuckyEnuns.ARMADURAS + " " + 1);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBuildLuckyblock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack lucky = player.getItemInHand();

        if (event.getClickedBlock() != null) {
            if (lucky.getType().equals(Material.SKULL_ITEM)) {
                if (LuckyManager.findByLocation(event.getClickedBlock().getLocation()) == null) {
                    if (lucky.hasItemMeta() && lucky.getItemMeta().hasDisplayName()) {
                        String displayname = lucky.getItemMeta().getDisplayName();
                        LuckyEnuns enuns = LuckyEnuns.findByName(StringUtils.stripColors(displayname));
                        if (enuns != null) {
                            LuckyManager.addLuckyBlock(LuckyManager.findByType(enuns), event.getClickedBlock().getLocation());
                            player.getInventory().remove(lucky);
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
