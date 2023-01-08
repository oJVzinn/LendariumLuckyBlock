package com.lendarium.ojvzinn.luckyblock.lucky;

import com.lendarium.ojvzinn.luckyblock.Main;
import com.lendarium.ojvzinn.luckyblock.lucky.enuns.LuckyEnuns;
import com.lendarium.ojvzinn.luckyblock.lucky.objects.LuckyBlockConfig;
import com.lendarium.ojvzinn.luckyblock.lucky.objects.LuckyBlocks;
import com.lendarium.ojvzinn.luckyblock.utils.FileUtils;
import com.lendarium.ojvzinn.luckyblock.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class LuckyManager {

    private static final List<LuckyBlocks> LUCKY_LOCATIONS = new ArrayList<>();
    private static final List<LuckyBlockConfig> LUCKY_CONFIG = new ArrayList<>();

    public static void setupLuckyBlocks() {
        FileUtils fileUtils = new FileUtils("luckyblock", Main.getInstance().getDescription().getName(), LuckyManager.class);
        fileUtils.loadFileYaml();
        YamlConfiguration yamlConfiguration = fileUtils.getYamlConfiguration();
        for (String key : Arrays.stream(LuckyEnuns.values()).map(LuckyEnuns::getKey).collect(Collectors.toList())) {
            List<String> CONTEINER = yamlConfiguration.getStringList("luckyblock." + key + ".conteiner");
            LuckyBlockConfig CONFIG = new LuckyBlockConfig(key, CONTEINER);
            LUCKY_CONFIG.add(CONFIG);
        }

        fileUtils = new FileUtils("locations", Main.getInstance().getDescription().getName(), LuckyManager.class);
        fileUtils.loadFileYaml();

        if (fileUtils.getFile().length() > 1) {
            if (fileUtils.getYamlConfiguration().get("locations") == null) {
                return;
            }
            List<String> a = fileUtils.getYamlConfiguration().getStringList("locations");
            for (String key : a) {
                Location location = LocationUtils.getLocationForString(key.split(" ; ")[0]);
                addLuckyBlock(findByType(LuckyEnuns.findByName(key.split(" ; ")[1])), location);
            }
        }

        Main.getInstance().sendMessage("As LuckyBlocks foram carregas com sucesso!");
    }

    public static void addLuckyBlock(LuckyBlockConfig lukyBlock, Location location) {
        LuckyBlocks luckyBlocks = new LuckyBlocks(location, lukyBlock);
        luckyBlocks.createLuckyBlock();
        try {
            FileUtils fileUtils = new FileUtils("locations", Main.getInstance().getDescription().getName(), LuckyManager.class);
            fileUtils.loadFileYaml();
            File file = fileUtils.getFile();
            YamlConfiguration CONFIG = fileUtils.getYamlConfiguration();
            LUCKY_LOCATIONS.add(luckyBlocks);
            List<String> LUCKYS = new ArrayList<>();
            for (LuckyBlocks blocks : LUCKY_LOCATIONS) {
                LUCKYS.add(LocationUtils.setLocationInString(blocks.getLocation())  + " ; " +  blocks.getLuckyBlock().getType().getName());
            }
            CONFIG.set("locations", LUCKYS);
            CONFIG.save(file);
        } catch (Exception ignored) {}
    }

    public static void deleteLuckyBlock(LuckyBlocks luckyBlock) {
        try {
            FileUtils fileUtils = new FileUtils("locations", Main.getInstance().getDescription().getName(), LuckyManager.class);
            fileUtils.loadFileYaml();
            File file = fileUtils.getFile();
            YamlConfiguration CONFIG = fileUtils.getYamlConfiguration();
            List<String> LUCKYS = new ArrayList<>();
            LUCKY_LOCATIONS.remove(luckyBlock);
            if (LUCKY_LOCATIONS.isEmpty()) {
                CONFIG.set("locations", null);
                CONFIG.save(file);
            } else {
                for (LuckyBlocks blocks : LUCKY_LOCATIONS) {
                    LUCKYS.add(LocationUtils.setLocationInString(blocks.getLocation()) + " ; " + blocks.getLuckyBlock().getType().getName());
                }
                CONFIG.set("locations", LUCKYS);
                CONFIG.save(file);
            }
        } catch (Exception ignored) {}
    }

    public static LuckyBlockConfig findByType(LuckyEnuns enuns) {
        return LUCKY_CONFIG.stream().filter(luckyBlockConfig -> luckyBlockConfig.getType().equals(enuns)).findFirst().orElse(null);
    }

    public static LuckyBlocks findByLocation(Location location) {
        return LUCKY_LOCATIONS.stream().filter(luckyBlocks -> luckyBlocks.getLocation().equals(location)).findFirst().orElse(null);
    }

    public static List<LuckyBlocks> getLuckyLocations() {
        return LUCKY_LOCATIONS;
    }
}
