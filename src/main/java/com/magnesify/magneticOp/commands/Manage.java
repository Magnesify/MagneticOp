package com.magnesify.magneticOp.commands;

import com.magnesify.magneticOp.MagneticOp;
import com.magnesify.magneticOp.managers.Chat;
import com.magnesify.magneticOp.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Manage implements CommandExecutor {

    public Manage(MagneticOp magneticOp) {
    }

    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args) {
        if (sender.hasPermission("mop.admin")) {
            if (command.getName().equalsIgnoreCase("MagneticOp")) {
                if (args.length != 0 && !args[0].equalsIgnoreCase("help")) {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("setspawn")) {
                            if (sender instanceof Player) {
                                Player player = ((Player) sender).getPlayer();

                                MagneticOp.getPlugin(MagneticOp.class).getConfig().set("magnetic-op.spawn.setted", true);
                                MagneticOp.getPlugin(MagneticOp.class).getConfig().set("magnetic-op.spawn.x", player.getLocation().getX());
                                MagneticOp.getPlugin(MagneticOp.class).getConfig().set("magnetic-op.spawn.y", player.getLocation().getY());
                                MagneticOp.getPlugin(MagneticOp.class).getConfig().set("magnetic-op.spawn.z", player.getLocation().getZ());
                                MagneticOp.getPlugin(MagneticOp.class).getConfig().set("magnetic-op.spawn.world", player.getLocation().getWorld().getName());
                                MagneticOp.getPlugin(MagneticOp.class).saveConfig();
                                new Chat(sender, "Başlangıç seçildi...");
                            } else {
                                new Chat(sender, "Komut yanlızca oyundan kullanılabilir.");
                            }
                        } else if (args[0].equalsIgnoreCase("reload")) {
                            MagneticOp.getPlugin(MagneticOp.class).reloadConfig();
                            MagneticOp.getPlugin(MagneticOp.class).saveConfig();
                            new Chat(sender, "Eklenti başarıyla yenilendi...");
                        } else {
                            new Chat(sender, "/MagneticOp setspawn");
                            new Chat(sender, "/MagneticOp reload");
                            new Chat(sender, "/MagneticOp delete <Oyuncu>");
                            new Chat(sender, "/MagneticOp register <Oyuncu> <Şifre>");
                        }
                    } else if (args.length == 2) {
                        if(args[0].equalsIgnoreCase("delete")) {
                            Player tar = Bukkit.getPlayer(args[1]);
                            if (tar != null) {
                                DatabaseManager databaseManager = new DatabaseManager(MagneticOp.getPlugin(MagneticOp.class));
                                if(databaseManager.isExists(tar.getPlayer().getUniqueId().toString())) {
                                    databaseManager.Delete(tar.getName());
                                    new Chat(sender, String.format("%s başarıyla silindi.", tar.getName()));
                                } else {
                                    new Chat(sender, "Bu isimde bir oyuncu kayıtlı değil.");
                                }
                            } else {
                                new Chat(sender, "Bu isimde bir oyuncu bulunamadı...");
                            }
                        } else {
                            new Chat(sender, "/MagneticOp setspawn");
                            new Chat(sender, "/MagneticOp reload");
                            new Chat(sender, "/MagneticOp delete <Oyuncu>");
                            new Chat(sender, "/MagneticOp register <Oyuncu> <Şifre>");
                        }
                    } else if (args.length == 3) {
                        if (args[0].equalsIgnoreCase("register")) {
                            Player tar = Bukkit.getPlayer(args[1]);
                            if (tar != null) {
                                DatabaseManager databaseManager = new DatabaseManager(MagneticOp.getPlugin(MagneticOp.class));
                                if(databaseManager.isExists(tar.getPlayer().getUniqueId().toString())) {
                                    new Chat(sender, "Bu isimde bir oyuncu zaten kayıtlı.");
                                } else {
                                    databaseManager.CreateOperator(tar, tar.getAddress().getHostName(), args[2]);
                                    new Chat(sender, String.format("%s için bir MOP hesabı başarıyla oluşturuldu.", tar.getName()));
                                }
                            } else {
                                new Chat(sender, "Bu isimde bir oyuncu bulunamadı...");
                            }
                        } else {
                            new Chat(sender, "/MagneticOp setspawn");
                            new Chat(sender, "/MagneticOp reload");
                            new Chat(sender, "/MagneticOp delete <Oyuncu>");
                            new Chat(sender, "/MagneticOp register <Oyuncu> <Şifre>");
                        }
                    } else {
                        new Chat(sender, "/MagneticOp setspawn");
                        new Chat(sender, "/MagneticOp reload");
                        new Chat(sender, "/MagneticOp delete <Oyuncu>");
                        new Chat(sender, "/MagneticOp register <Oyuncu> <Şifre>");
                    }
                } else {
                    new Chat(sender, "/MagneticOp setspawn");
                    new Chat(sender, "/MagneticOp reload");
                    new Chat(sender, "/MagneticOp delete <Oyuncu>");
                    new Chat(sender, "/MagneticOp register <Oyuncu> <Şifre>");
                }
            }
        } else {
            new Chat(sender, MagneticOp.getPlugin(MagneticOp.class).getConfig().getString("messages.error.no-permission"));
        }
        return true;
    }
}
