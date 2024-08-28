package com.magnesify.magneticOp.events;

import com.magnesify.magneticOp.MagneticOp;
import com.magnesify.magneticOp.managers.Chat;
import com.magnesify.magneticOp.managers.DatabaseManager;
import com.magnesify.magneticOp.managers.Password;
import com.magnesify.magneticOp.managers.Spawn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OperatorJoinEvent implements Listener {
    public OperatorJoinEvent(MagneticOp magneticOp) {}

    @EventHandler
    public void joinHandler(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("mop.auth") && !MagneticOp.authOpHandlers.contains(player.getUniqueId())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 31));
            DatabaseManager databaseManager = new DatabaseManager(MagneticOp.getPlugin(MagneticOp.class));
            if (databaseManager.isExists(player.getUniqueId().toString())) {
                MagneticOp.authOpHandlers.add(player.getUniqueId());
                databaseManager.setIP(player.getUniqueId().toString(), player.getAddress().getHostName());
                new Chat(player, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.warnings.please-log-in"));
            } else {
                MagneticOp.registerHandler.add(player.getUniqueId());
                new Chat(player, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.warnings.please-register"));
                if (MagneticOp.getPlugin(MagneticOp.class).getConfig().getBoolean("magnetic-op.send-random-password-with-register-message")) {
                    new Chat(player, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.tips.simple-password-generator").replace("<password>", Password.generateRandomPassword(MagneticOp.getPlugin(MagneticOp.class).getConfig().getInt("magnetic-op.random-password-lenght"))));
                }
            }

            if (Spawn.isSpawnSetted()) {
                Spawn.teleport(player);
            } else if (player.hasPermission("mop.admin") && MagneticOp.getPlugin(MagneticOp.class).getConfig().getBoolean("magnetic-op.debug")) {
                new Chat(player, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.error.spawn-not-found"));
            }
        }

    }

    @EventHandler
    public void quitHandler(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("mop.auth")) {
            if (MagneticOp.authOpHandlers.contains(player.getUniqueId())) {
                MagneticOp.authOpHandlers.remove(player.getUniqueId());
            }

            if (MagneticOp.registerHandler.contains(player.getUniqueId())) {
                MagneticOp.registerHandler.remove(player.getUniqueId());
            }
        }

    }

    @EventHandler
    public void commandHandler(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String[] argument = event.getMessage().split(" ");
        if (player.hasPermission("mop.auth")) {
            if (MagneticOp.authOpHandlers.contains(player.getUniqueId()) && !argument[0].equalsIgnoreCase("/Giriş")) {
                event.setCancelled(true);
            }

            if (MagneticOp.registerHandler.contains(player.getUniqueId()) && !argument[0].equalsIgnoreCase("/Kaydol")) {
                event.setCancelled(true);
            }
        }

    }


    /** @deprecated */
    @Deprecated
    @EventHandler
    public void chatHandler(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("mop.auth")) {
            if (MagneticOp.authOpHandlers.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }

            if (MagneticOp.registerHandler.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

    }

    /** @deprecated */
    @Deprecated
    @EventHandler
    public void moveHandler(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("mop.auth")) {
            if (MagneticOp.authOpHandlers.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }

            if (MagneticOp.registerHandler.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

    }
}
