package com.magnesify.magneticOp.commands;

import com.magnesify.magneticOp.MagneticOp;
import com.magnesify.magneticOp.managers.Chat;
import com.magnesify.magneticOp.managers.Console;
import com.magnesify.magneticOp.managers.DatabaseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Iterator;

public class Login implements CommandExecutor {
    public Login(MagneticOp magneticOp) {}

    /** @deprecated */
    @Deprecated
    public boolean onCommand(CommandSender sender,Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player)sender).getPlayer();
            if (player.hasPermission("mop.auth")) {
                if (MagneticOp.authOpHandlers.contains(player.getUniqueId())) {
                    DatabaseManager databaseManager = new DatabaseManager(MagneticOp.getPlugin(MagneticOp.class));
                    if (databaseManager.isExists(player.getUniqueId().toString())) {
                        if (command.getName().equalsIgnoreCase("Giriş")) {
                            if (args[0].equalsIgnoreCase(databaseManager.getPassword(player.getUniqueId().toString()))) {
                                MagneticOp.authOpHandlers.remove(player.getUniqueId());
                                new Chat(sender, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.success.authed"));
                                Iterator var6 = player.getActivePotionEffects().iterator();

                                while(var6.hasNext()) {
                                    PotionEffect e = (PotionEffect)var6.next();
                                    player.removePotionEffect(e.getType());
                                }
                            } else {
                                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.kick.incorect-password").replace("<nl>", "\n")));
                            }
                        }
                    } else {
                        new Chat(sender, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.error.not-exists"));
                    }
                } else {
                    new Chat(sender, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.error.already-authed"));
                }
            } else {
                new Chat(sender, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.error.no-permission"));
            }
        } else {
            new Console(MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.console.only-in-game"));
        }

        return true;
    }
}
