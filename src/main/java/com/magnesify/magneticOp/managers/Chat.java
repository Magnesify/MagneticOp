package com.magnesify.magneticOp.managers;

import com.magnesify.magneticOp.MagneticOp;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Chat {
    public Chat(CommandSender player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', message));
    }
}
