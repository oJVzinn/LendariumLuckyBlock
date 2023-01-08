package com.lendarium.ojvzinn.luckyblock.lucky.enuns;

import com.lendarium.ojvzinn.luckyblock.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum LuckyEnuns {
    CAIXAS("Caixas", "caixas", "95:4 : 1", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZkY2ZiNDAwOTc2YmY3M2VjMzJjMWI5OTYyYzgzMGZjM2Q3MDA2ZDc0OWY4ZjNkYTNiNmUwZmI4MjkwOWIyOCJ9fX0="),
    ARMADURAS("Armaduras", "armaduras", "95:9 : 1", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM5Nzg0M2ZkM2E2ODUyOGEyODY3NDE2OWI5YjgzMWMxNjU4ZmIxZDg2YTcyMTgyNmRjZWQ1MDM0MmUzMjVmMiJ9fX0="),
    SPAWNERS("Spawners", "spawners","95:14 : 1", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM0ZjM2ZDJkZmQwNjBkOTVmODk1MzA1NDA2ZjFiODQ1YmFlMDAyNzY0NjVhNDU0MjhkM2NmZDg2MzBkNGU2MSJ9fX0="),
    ESPECIAIS("Especiais", "especiais", "95:2 : 1", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ViZjQzZWNkOGQ4ZmYwZTJhNTMzYjE1ZWE5YTkxZTQxMmFkYTI2OTZhZjVmNGE5MzY4ZGEyNDMwOWIyMjdkZSJ9fX0="),
    FERRAMENTAS("Ferramentas", "ferramentas", "95:15 : 1", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzcxOTMwMjc4ZjAyOWM1Nzc4Yzg1YzA0NzVhMzdmYWM4YWNkMzg1MDc0MmFhZTZhMzU0MmFjZGU0NDg3ZDYzIn19fQ==");

    private final String name;
    private final String skin;
    private final String key;
    private final ItemStack itemStack;

    public static LuckyEnuns findByName(String name) {
        return Arrays.stream(LuckyEnuns.values()).filter(luckyEnuns -> luckyEnuns.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    LuckyEnuns(String name, String key, String item, String skin) {
        this.name = name;
        this.skin = skin;
        this.key = key;
        this.itemStack = ItemUtils.getItemStackFromString(item);
    }

    public String getName() {
        return name;
    }

    public String getSkin() {
        return skin;
    }

    public String getKey() {
        return key;
    }

    public ItemStack getGlass() {
        return itemStack;
    }
}
