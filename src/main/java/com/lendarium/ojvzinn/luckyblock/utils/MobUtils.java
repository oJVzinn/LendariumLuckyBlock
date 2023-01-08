package com.lendarium.ojvzinn.luckyblock.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;

public class MobUtils {

    public static void createMobForString(Location location, String mob) {
        String[] mobSplited = mob.split(" :: ");
        for (int i = 0; i < Integer.parseInt(mobSplited[1]); i++) {
            Entity entity = location.getWorld().spawnEntity(location, EntityType.valueOf(mobSplited[0]));
            for (int a = 2; a < mobSplited.length; a++) {
                String type = mobSplited[a].split(">")[0];
                String value = mobSplited[a].split(">")[1];

                switch (type) {

                    case "nome": {
                        entity.setCustomName(value);
                        break;
                    }

                    case "capacete": {
                        ItemStack capacete = ItemUtils.getItemStackFromString(value);
                        if (entity instanceof Monster) {
                            Monster monster = (Monster) entity;
                            monster.getEquipment().setHelmet(capacete);
                        }
                        break;
                    }

                    case "peitoral": {
                        ItemStack peitoral = ItemUtils.getItemStackFromString(value);
                        if (entity instanceof Monster) {
                            Monster monster = (Monster) entity;
                            monster.getEquipment().setChestplate(peitoral);
                        }
                        break;
                    }

                    case "calca": {
                        ItemStack calca = ItemUtils.getItemStackFromString(value);
                        if (entity instanceof Monster) {
                            Monster monster = (Monster) entity;
                            monster.getEquipment().setLeggings(calca);
                        }
                        break;
                    }

                    case "bota": {
                        ItemStack bota = ItemUtils.getItemStackFromString(value);
                        if (entity instanceof Monster) {
                            Monster monster = (Monster) entity;
                            monster.getEquipment().setBoots(bota);
                        }
                        break;
                    }

                    case "mao": {
                        ItemStack mao = ItemUtils.getItemStackFromString(value);
                        if (entity instanceof Monster) {
                            Monster monster = (Monster) entity;
                            monster.getEquipment().setItemInHand(mao);
                        }
                        break;
                    }
                }
            }
        }
    }
}
