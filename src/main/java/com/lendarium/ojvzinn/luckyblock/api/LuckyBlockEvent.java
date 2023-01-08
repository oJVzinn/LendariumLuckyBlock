package com.lendarium.ojvzinn.luckyblock.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class LuckyBlockEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final BlockBreakEvent event;
    private final ItemStack sortedItem;
    private final Item dropedItem;
    private final Location location;

    public LuckyBlockEvent(ItemStack sortedItem, Item item, Location location, BlockBreakEvent event) {
        this.location = location;
        this.dropedItem = item;
        this.sortedItem = sortedItem;
        this.event = event;
    }

    public Location getLocation() {
        return location;
    }

    public Item getDropedItem() {
        return dropedItem;
    }

    public ItemStack getSortedItem() {
        return sortedItem;
    }

    public BlockBreakEvent getEvent() {
        return event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static void callEvent(LuckyBlockEvent event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}
