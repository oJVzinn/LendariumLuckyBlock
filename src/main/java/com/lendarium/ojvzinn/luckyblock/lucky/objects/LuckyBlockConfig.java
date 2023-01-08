package com.lendarium.ojvzinn.luckyblock.lucky.objects;

import com.lendarium.ojvzinn.luckyblock.Main;
import com.lendarium.ojvzinn.luckyblock.lucky.enuns.LuckyEnuns;

import java.util.ArrayList;
import java.util.List;

public class LuckyBlockConfig {

    private final LuckyEnuns type;
    private final List<String> HOLOGRAM_LINES = new ArrayList<>();
    private final List<String> CONTEINER = new ArrayList<>();

    public LuckyBlockConfig(String type, List<String> CONTEINER) {
        this.type = LuckyEnuns.findByName(type);
        this.CONTEINER.addAll(CONTEINER);
        this.HOLOGRAM_LINES.addAll(Main.getInstance().getConfig().getStringList("luckyblock.hologram." + type));
    }

    public List<String> getCONTEINER() {
        return CONTEINER;
    }

    public LuckyEnuns getType() {
        return type;
    }

    public List<String> getHOLOGRAM_LINES() {
        return HOLOGRAM_LINES;
    }
}
