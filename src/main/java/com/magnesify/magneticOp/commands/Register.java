package com.magnesify.magneticOp.commands;

import com.magnesify.magneticOp.MagneticOp;
import com.magnesify.magneticOp.managers.Chat;
import com.magnesify.magneticOp.managers.Console;
import com.magnesify.magneticOp.managers.DatabaseManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Iterator;
import java.util.Random;

public class Register implements CommandExecutor {
    public Register(MagneticOp magneticOp) {}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (player.hasPermission("mop.auth")) {
                if (MagneticOp.registerHandler.contains(player.getUniqueId())) {
                    Random random;
                    int nextInt;
                    Iterator var8;
                    PotionEffect e;
                    DatabaseManager databaseManager = new DatabaseManager(MagneticOp.getPlugin(MagneticOp.class));
                    if (!databaseManager.isExists(player.getUniqueId().toString())) {
                        random = new Random();
                        nextInt = random.nextInt() + 99999;
                        if (command.getName().equalsIgnoreCase("Kaydol")) {
                            if (args.length == 1) {
                                databaseManager.CreateOperator(player, player.getAddress().getHostName(), args[0]);
                                MagneticOp.registerHandler.remove(player.getUniqueId());
                                new Chat(player, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.success.registered"));
                                var8 = player.getActivePotionEffects().iterator();

                                while (var8.hasNext()) {
                                    e = (PotionEffect) var8.next();
                                    player.removePotionEffect(e.getType());
                                }
                            } else {
                                new Chat(player, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.error.not-enough-argument").replace("<random-password>", String.valueOf(nextInt)));
                            }
                        } else {
                            new Chat(player, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.error.already-authed"));
                        }
                    } else {
                        new Chat(player, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.error.already-exists"));
                    }
                } else {
                    new Chat(player, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.error.no-permission"));
                }
            } else {
                new Console(MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.console.only-in-game"));
            }
        }
        return true;
    }
}
