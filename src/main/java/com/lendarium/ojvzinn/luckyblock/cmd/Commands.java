package com.lendarium.ojvzinn.luckyblock.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

import java.util.Arrays;

public abstract class Commands extends Command {

    protected Commands(String name, String... aliases) {
        super(name);
        this.setAliases(Arrays.asList(aliases));

        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getMethod("getCommandMap").invoke(Bukkit.getServer());
            simpleCommandMap.register("LendariumLuckyBlocks", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        performace(commandSender, s, strings);
        return true;
    }

    abstract void performace(CommandSender sender, String s, String[] args);

    public static void setupCommands() {
        new LuckyCommand("luckyblock", "lb");
    }
}
