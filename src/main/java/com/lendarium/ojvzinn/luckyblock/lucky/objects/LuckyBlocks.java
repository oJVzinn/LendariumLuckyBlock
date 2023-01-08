package com.lendarium.ojvzinn.luckyblock.lucky.objects;

import com.lendarium.ojvzinn.luckyblock.Main;
import com.lendarium.ojvzinn.luckyblock.api.LuckyBlockEvent;
import com.lendarium.ojvzinn.luckyblock.lucky.LuckyManager;
import com.lendarium.ojvzinn.luckyblock.utils.ItemUtils;
import com.lendarium.ojvzinn.luckyblock.utils.MobUtils;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LuckyBlocks {

    private final LuckyBlockConfig luckyBlock;
    private final Location location;
    private Hologram hologram;
    private ArmorStand armorStand;

    public LuckyBlocks(Location location, LuckyBlockConfig luckyBlock) {
        this.luckyBlock = luckyBlock;
        this.location = location;
    }

    public void sortedReward(Player player, BlockBreakEvent event) {
        this.location.getWorld().spawn(this.location, Firework.class);
        String sorted = luckyBlock.getCONTEINER().get(ThreadLocalRandom.current().nextInt(luckyBlock.getCONTEINER().size()));
        String typeSorted = sorted.split(" ; ")[0];
        Item item = null;
        ItemStack itemStack = null;
        switch (typeSorted) {

            case "ITEM": {
                itemStack = ItemUtils.getItemStackFromString(sorted.split(" ; ")[1]);
                item = player.getWorld().dropItem(event.getBlock().getLocation(), itemStack);
                break;
            }

            case "SPAWN": {
                MobUtils.createMobForString(location.getBlock().getRelative(BlockFace.UP).getLocation(), sorted.split(" ; ")[1]);
                break;
            }

            case "COMMAND": {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), sorted.split(" ; ")[1].replace("{player}", player.getName()));
                break;
            }
        }
        LuckyManager.deleteLuckyBlock(this);
        LuckyBlockEvent luckyBlockEvent = new LuckyBlockEvent(itemStack, item, event.getBlock().getLocation(), event);
        LuckyBlockEvent.callEvent(luckyBlockEvent);
        kill();
    }

    @Deprecated
    public void createLuckyBlock() {
        Location finalLoc = this.location.getBlock().getRelative(BlockFace.UP).getLocation();
        if (finalLoc.getBlock().getType().equals(Material.AIR)) {
            Block block = finalLoc.getBlock();
            block.setType(this.luckyBlock.getType().getGlass().getType());
            block.setData(this.luckyBlock.getType().getGlass().getData().getData());
            block.getState().update();
            this.armorStand = this.location.getWorld().spawn(this.location.clone().add(0.5, 0.5, 0.5), ArmorStand.class);
            this.armorStand.setArms(false);
            this.armorStand.setVisible(false);
            this.armorStand.setSmall(true);
            this.armorStand.setHelmet(ItemUtils.getItemStackFromString("397:3 : 1 : nome>§b" + luckyBlock.getType().getName() + " : desc>&7Clique com o direito no chão\n&7para criar uma luckyblock! : skin>" + luckyBlock.getType().getSkin()));
            HolographicDisplaysAPI api = HolographicDisplaysAPI.get(Main.getInstance());
            this.hologram = api.createHologram(this.location.getBlock().getRelative(BlockFace.UP).getLocation().clone().add(0.5, 2.0, 0.5));
            List<String> LINES = Main.getInstance().getConfig().getStringList("luckyblock.hologram." + this.luckyBlock.getType().getKey());
            for (String line : LINES) {
                this.hologram.getLines().appendText(line);
            }
        }
    }

    public void kill() {
        this.armorStand.getHelmet().setType(Material.AIR);
        this.armorStand.remove();
        this.armorStand = null;
        this.hologram.delete();
        this.hologram = null;
        this.location.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
        this.location.getBlock().getState().update(true);
    }

    public Location getLocation() {
        return location;
    }

    public LuckyBlockConfig getLuckyBlock() {
        return luckyBlock;
    }
}
