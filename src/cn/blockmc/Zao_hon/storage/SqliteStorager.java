package cn.blockmc.Zao_hon.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import cn.blockmc.Zao_hon.Ornaments;

public class SqliteStorager extends DataStorager {
	private Ornaments plugin;
	private final String path;

	public SqliteStorager(Ornaments plugin) {
		this.plugin = plugin;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			plugin.PR("加载JDBC数据库失败");
			plugin.onDisable();
		}
		path = "jdbc:sqlite:" + plugin.getDataFolder() + "/" + "portals.db";
		this.setupTable();
	}

	public Connection setupConnection() {
		try {
			return DriverManager.getConnection(path);
		} catch (SQLException e) {
			plugin.PR("连接数据库失败");
			plugin.onDisable();
			return null;
		}
	}

	public void setupTable() {
		Connection connection = setupConnection();
		try {
			Statement stat = connection.createStatement();
			stat.execute(
					"CREATE TABLE IF NOT EXISTS playerornaments(NAME TEXT , UUID TEXT , NECKLACE TEXT,BRACELET TEXT,RING TEXT)");
			stat.close();
			connection.close();
		} catch (SQLException e) {
			plugin.PR("创建初始表失败");
			e.printStackTrace();
		}

	}

	public void addPlayerStorager(OfflinePlayer player) {
		Connection connection = setupConnection();
		String name = player.getName();
		String uuid = player.getUniqueId().toString();
		PreparedStatement ps = setupPreparedStatement(connection, PreparedStatementType.INSERT_STORAGER);
		try {
			ps.setString(1, name);
			ps.setString(2, uuid);
			ps.setString(3, null);
			ps.setString(4, null);
			ps.setString(5, null);
			ps.execute();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String[] getPlayerStorager(UUID uuid) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
		String[] str = new String[3];
		Connection connection = setupConnection();
		PreparedStatement ps = setupPreparedStatement(connection, PreparedStatementType.SELECT_STORAGER);
		try {
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				str[0] = rs.getString(1);
				str[1] = rs.getString(2);
				str[2] = rs.getString(3);
			} else {
				addPlayerStorager(player);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public void setPlayerStorager(UUID uuid, String[] str) {
		Connection connection = setupConnection();
		PreparedStatement ps = setupPreparedStatement(connection, PreparedStatementType.SELECT_STORAGER);
		try {
			ps.setString(1, uuid.toString());
			ps.setString(2, str[0]);
			ps.setString(3, str[1]);
			ps.setString(4, str[2]);
			ps.execute();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public PreparedStatement setupPreparedStatement(Connection conn, @Nonnull PreparedStatementType type) {
		String str = "";
		switch (type) {
		case INSERT_STORAGER:
			str = "INSERT INTO playerornaments VALUE(?,?,?,?,?)";
			break;
		case SELECT_STORAGER:
			str = "SELECT NECKLACE,BRACELET,RING FROM playerornaments WHERE UUID = ?";
			break;
		case UPDATE_STORAGER:
			str = "UPDATE playerornaments WHERE UUID = ? SET NECKLACE = ? AND BRACELET = ? AND RING = ?";
			break;
		default:
			break;
		}
		try {
			return conn.prepareStatement(str);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
