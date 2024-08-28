package com.magnesify.magneticOp;

import com.magnesify.magneticOp.commands.Login;
import com.magnesify.magneticOp.commands.Manage;
import com.magnesify.magneticOp.commands.Register;
import com.magnesify.magneticOp.events.OperatorJoinEvent;
import com.magnesify.magneticOp.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public final class MagneticOp extends JavaPlugin {
    public static ArrayList<UUID> authOpHandlers = new ArrayList();
    public static ArrayList<UUID> registerHandler = new ArrayList();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.initialize();
        Bukkit.getPluginManager().registerEvents(new OperatorJoinEvent(this), this);
        getCommand("Giriş").setExecutor(new Login(this));
        getCommand("Kaydol").setExecutor(new Register(this));
        getCommand("MagneticOp").setExecutor(new Manage(this));
    }
}
