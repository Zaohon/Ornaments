package cn.blockmc.Zao_hon.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import cn.blockmc.Zao_hon.Ornaments;
import cn.blockmc.Zao_hon.inventory.OrnamentStorager;
import cn.blockmc.Zao_hon.inventory.OrnamentType;
import cn.blockmc.Zao_hon.inventory.PlayerOrnament;

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
		path = "jdbc:sqlite:" + plugin.getDataFolder() + "/" + "datas.db";
		this.setupTable();
	}

	public Connection setupConnection() {
		try {
			Connection conn = DriverManager.getConnection(path);
			conn.setAutoCommit(false);
			return conn;
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
					"CREATE TABLE IF NOT EXISTS playerornaments(NAME TEXT , UUID TEXT PRIMARY KEY NOT NULL, NECKLACE TEXT,BRACELET TEXT,RING TEXT)");
			stat.close();
			connection.commit();
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
		try {
			setupPreparedStatement(connection, PreparedStatementType.INSERT_STORAGER);
			mInsertStorager.setString(1, name);
			mInsertStorager.setString(2, uuid);
			mInsertStorager.setString(3, null);
			mInsertStorager.setString(4, null);
			mInsertStorager.setString(5, null);
			mInsertStorager.execute();
			mInsertStorager.close();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public OrnamentStorager getPlayerStorager(UUID uuid) {
		OrnamentStorager storager = new OrnamentStorager();
		Connection connection = setupConnection();
		try {
			setupPreparedStatement(connection, PreparedStatementType.SELECT_STORAGER);
			mSelectStorager.setString(1, uuid.toString());
			ResultSet rs = mSelectStorager.executeQuery();
			if (rs.next()) {
				storager.setOrnament(OrnamentType.NECKLACE,
						PlayerOrnament.asPlayerOrnament(plugin.getOrnamentManager(), rs.getString(1)));
				storager.setOrnament(OrnamentType.BRACELET,
						PlayerOrnament.asPlayerOrnament(plugin.getOrnamentManager(), rs.getString(2)));
				storager.setOrnament(OrnamentType.RING,
						PlayerOrnament.asPlayerOrnament(plugin.getOrnamentManager(), rs.getString(3)));
			}
			mSelectStorager.close();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return storager;
	}

//	@Override
//	public void setPlayerStorager(UUID uuid,  OrnamentStorager str) {
//		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
//		Connection connection = setupConnection();
//		try {
//			setupPreparedStatement(connection, PreparedStatementType.UPDATE_STORAGER);
//			mUpdateStorager.setString(1, str.getPlayerOrnament(OrnamentType.NECKLACE).toString());
//			mUpdateStorager.setString(2,  str.getPlayerOrnament(OrnamentType.BRACELET).toString());
//			mUpdateStorager.setString(3, str.getPlayerOrnament(OrnamentType.RING).toString());
//			mUpdateStorager.setString(4, uuid.toString());
//			mUpdateStorager.execute();
//			mUpdateStorager.close();
//			connection.commit();
//			connection.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	@Override
	public void setPlayerStorager(UUID uuid,  OrnamentStorager str) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
		Connection connection = setupConnection();
		try {
			setupPreparedStatement(connection, PreparedStatementType.REPLACE_STORAGER);
			mReplaceStorager.setString(1, player.getName());
			mReplaceStorager.setString(2, uuid.toString());
			mReplaceStorager.setString(3, str.getPlayerOrnamentStr(OrnamentType.NECKLACE));
			mReplaceStorager.setString(4,  str.getPlayerOrnamentStr(OrnamentType.BRACELET));
			mReplaceStorager.setString(5, str.getPlayerOrnamentStr(OrnamentType.RING));
			mReplaceStorager.execute();
			mReplaceStorager.close();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setupPreparedStatement(Connection conn, @Nonnull PreparedStatementType type) throws SQLException {
		switch (type) {
		case INSERT_STORAGER:
			mInsertStorager = conn.prepareStatement("INSERT INTO playerornaments VALUES(?,?,?,?,?)");
			break;
		case SELECT_STORAGER:
			mSelectStorager = conn
					.prepareStatement("SELECT NECKLACE,BRACELET,RING FROM playerornaments WHERE UUID = ?");
			break;
		case UPDATE_STORAGER:
			mUpdateStorager = conn.prepareStatement(
					"UPDATE playerornaments SET NECKLACE = ? AND BRACELET = ? AND RING = ?  WHERE UUID = ?");
			break;
		case REPLACE_STORAGER:
			mReplaceStorager = conn.prepareStatement(
					"Replace INTO playerornaments VALUES(?,?,?,?,?)");
		default:
			break;

		}
	}

}
