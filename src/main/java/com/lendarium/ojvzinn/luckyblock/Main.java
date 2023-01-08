package com.lendarium.ojvzinn.luckyblock;

import com.lendarium.ojvzinn.luckyblock.cmd.Commands;
import com.lendarium.ojvzinn.luckyblock.listeners.Listeners;
import com.lendarium.ojvzinn.luckyblock.lucky.LuckyManager;
import com.lendarium.ojvzinn.luckyblock.lucky.objects.LuckyBlocks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        new BukkitRunnable() {
            @Override
            public void run() {
                LuckyManager.setupLuckyBlocks();
                Listeners.setupListeners();
                Commands.setupCommands();
            }
        }.runTaskLater(Main.getInstance(), 20);
        sendMessage("O plugin iniciou com sucesso!");
    }


    @Override
    public void onDisable() {
        LuckyManager.getLuckyLocations().forEach(LuckyBlocks::kill);
        sendMessage("O plugin desligou com sucesso!");
    }

    public static Main getInstance() {
        return instance;
    }

    public void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage("§a[" + getDescription().getName() +"] " + message);
    }

    public void sendMessage(String message, String color) {
        Bukkit.getConsoleSender().sendMessage("§" + color + "[" + getDescription().getName() +"] " + message);
    }
}
