package com.lendarium.ojvzinn.luckyblock.utils;

import com.lendarium.ojvzinn.luckyblock.Main;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

    public static String setLocationInString(Location location) {
        String x = String.valueOf(location.getX());
        String y = String.valueOf(location.getY());
        String z = String.valueOf(location.getZ());
        String pitch = String.valueOf(location.getPitch());
        String yaw = String.valueOf(location.getYaw());
        String world = location.getWorld().getName();

        return x + ";" + y + ";" + z + ";" + pitch + ";" + yaw + ";" + world;
    }

    public static Location getLocationForString(String loc) {
        double x = Double.parseDouble(loc.split(";")[0]);
        double y = Double.parseDouble(loc.split(";")[1]);
        double z = Double.parseDouble(loc.split(";")[2]);
        float pitch = Float.parseFloat(loc.split(";")[3]);
        float yaw = Float.parseFloat(loc.split(";")[4]);
        World world = Main.getInstance().getServer().getWorld(loc.split(";")[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
