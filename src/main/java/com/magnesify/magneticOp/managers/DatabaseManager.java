package com.magnesify.magneticOp.managers;

import com.magnesify.magneticOp.MagneticOp;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.concurrent.CompletableFuture;

public class DatabaseManager {
    private final MagneticOp plugin;
    private HikariDataSource dataSource;

    public DatabaseManager(MagneticOp plugin) {
        this.plugin = plugin;
    }

    public void load() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder() + "/magnesify.db");
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(30000);
        config.setMaxLifetime(1800000);
        dataSource = new HikariDataSource(config);
    }

    public void initialize() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder() + "/magnesify.db");
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(30000);
        config.setMaxLifetime(1800000);

        dataSource = new HikariDataSource(config);

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS operators (name TEXT, uuid TEXT, last_ip TEXT, password TEXT, PRIMARY KEY(name, uuid))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized");
        }
        return dataSource.getConnection();
    }

    public Boolean isExists(String uuid) {
        load();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM operators WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPassword(String uuid) {
        load();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT password FROM operators WHERE uuid = ?")) {
            statement.setString(1, uuid);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String point = resultSet.getString("password");
                return point;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Delete(String name)  {
        load();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = getConnection();
            String sql = "DELETE FROM operators WHERE name LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%"+name+"%");
            int rowsAffected = pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC sürücüsü bulunamadı: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL hata: " + e.getMessage());
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Kapatma hatası: " + e.getMessage());
            }
        }
    }

    public CompletableFuture<Boolean> setIP(String uuid, String bool) {
        load();
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE operators SET last_ip = ? WHERE uuid = ?")) {
                statement.setString(1, bool);
                statement.setString(2, uuid);
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public CompletableFuture<Boolean> CreateOperator(Player player, String ip, String password) {
        load();
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO operators (name,uuid,last_ip,password) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, player.getName());
                statement.setString(2, player.getUniqueId().toString());
                statement.setString(3, ip);
                statement.setString(4, password);
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }
}
