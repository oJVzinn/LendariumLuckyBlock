package com.lendarium.ojvzinn.luckyblock.menu;

import com.lendarium.ojvzinn.luckyblock.Main;
import com.lendarium.ojvzinn.luckyblock.library.ItemUtils;
import com.lendarium.ojvzinn.luckyblock.library.menu.types.MenuPlayer;
import com.lendarium.ojvzinn.luckyblock.lucky.enuns.LuckyEnuns;
import com.lendarium.ojvzinn.luckyblock.utils.FileUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuEditLuckyBlock extends MenuPlayer {

    private boolean save = false;
    private final LuckyEnuns luckyType;

    public MenuEditLuckyBlock(LuckyEnuns luckyType, Player player) {
        super("Editando a lucky block " + luckyType.getName());
        this.luckyType = luckyType;

        this.setItem(ItemUtils.getItemStackFromString("399:0 : 1 : nome>&aConfirmar Ação : desc>&7Confirmando esta ação,;;&7você vai fazer que os itens;&7seja colodo na lucky block "  + luckyType.getName()), 49);

        register();
        open(player);
    }

    @EventHandler
    public void onPlayerQuitListners(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.getOpenInventory().getTopInventory().equals(this.getInventory())) {
            cancel();
        }
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event) {
        if (event.getInventory().equals(this.getInventory())) {
            cancel();
            if (save) {
                List<ItemStack> itens = new ArrayList<>();
                for (int i = 0; i < 6 * 9; i++) {
                    if (i != 49) {
                        itens.add(event.getInventory().getItem(i));
                    }
                }

                setupItens(itens);
            }
        }
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {
        if (event.getInventory().equals(getInventory())) {
            if (event.getWhoClicked() instanceof Player) {
                if (event.getCurrentItem().getType() != Material.AIR) {
                    Player player = (Player) event.getWhoClicked();
                    if (event.getSlot() == 49) {
                        player.sendMessage("§aVocê salvou todos os itens com sucesso!");
                        save = true;
                        player.closeInventory();
                    }
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                }
            }
            event.setCancelled(true);
        }
    }

    @Override
    public void cancel() {
        HandlerList.unregisterAll(this);
    }

    public void setupItens(List<ItemStack> itensInventory) {
        FileUtils fileUtils = new FileUtils("luckyblock", Main.getInstance().getDescription().getName(), MenuEditLuckyBlock.class);
        YamlConfiguration CONFIG = fileUtils.getYamlConfiguration();
        List<String> itensString = CONFIG.getStringList("luckyblock." + luckyType.getKey() + ".conteiner");
        for (ItemStack item : itensInventory) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item.getType().name()).append(":").append(item.getData().getData()).append(" : ").append(item.getAmount());
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasDisplayName()) {
                    stringBuilder.append(" : ").append(meta.getDisplayName());
                }

                if (meta.hasLore()) {
                    stringBuilder.append(" : ");
                    for (int i = 0; i < meta.getLore().size(); i++) {
                        stringBuilder.append(meta.getLore().size() > 1 ? i > 0 ? ";;" : "" : "").append(meta.getLore().get(i));
                    }
                }

                if (meta.hasEnchants()) {
                    stringBuilder.append(" : ");
                    int i = 0;
                    for (Enchantment enchantment : meta.getEnchants().keySet()) {
                        stringBuilder.append(meta.getEnchants().size() > 1 ? i > 0 ? ";;" : "" : "").append(enchantment).append(":").append(meta.getEnchants().get(enchantment));
                        i++;
                    }
                }
            }

            itensString.add(stringBuilder.toString());
        }

        try {
            CONFIG.set("luckyblock." + luckyType.getKey() + ".conteiner", itensString);
            CONFIG.save(fileUtils.getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
