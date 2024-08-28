package com.magnesify.magneticOp.managers;

import com.magnesify.magneticOp.MagneticOp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Spawn {
    public Spawn() {
    }

    public static boolean isSpawnSetted() {
        return MagneticOp.getPlugin(MagneticOp.class).getConfig().getBoolean("magnetic-op.spawn.setted");
    }

    public static void teleport(Player player) {
        double x = Double.parseDouble(MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("magnetic-op.spawn.x"));
        double y = Double.parseDouble(MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("magnetic-op.spawn.y"));
        double z = Double.parseDouble(MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("magnetic-op.spawn.z"));
        World world = Bukkit.getWorld(MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("magnetic-op.spawn.world"));
        Location location = new Location(world, x, y, z);
        player.teleport(location);
    }
}