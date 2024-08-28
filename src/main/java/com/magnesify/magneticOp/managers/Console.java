package com.magnesify.magneticOp.managers;

import com.magnesify.magneticOp.MagneticOp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Console {
    public Console(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.prefix")) + message);
    }
}
