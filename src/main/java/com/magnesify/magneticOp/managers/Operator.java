package com.magnesify.magneticOp.managers;

import com.magnesify.magneticOp.MagneticOp;
import org.bukkit.entity.Player;

public class Operator {

    private Player player;
    private String password, ip;

    public Operator(Player player, String password, String ip) {
        this.player = player;
        this.password = password;
        this.ip = ip;
    }

    public boolean Create() {
        DatabaseManager databaseManager = new DatabaseManager(MagneticOp.getPlugin(MagneticOp.class));
        if(!databaseManager.isExists(player.getUniqueId().toString())) {
            databaseManager.CreateOperator(player, password, ip);
            return true;
        } else {
            return false;
        }
    }

    public String getPassword() {
        return password;
    }

    public String getIp() {
        return ip;
    }

}
