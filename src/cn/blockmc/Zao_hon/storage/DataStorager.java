package cn.blockmc.Zao_hon.storage;

import java.sql.PreparedStatement;
import java.util.UUID;

import cn.blockmc.Zao_hon.inventory.OrnamentStorager;

public abstract class DataStorager {
	protected PreparedStatement mInsertStorager;
	protected PreparedStatement mSelectStorager;
	protected PreparedStatement mUpdateStorager;
	protected PreparedStatement mReplaceStorager;
	public abstract OrnamentStorager getPlayerStorager(UUID uuid);
	public abstract void setPlayerStorager(UUID uuid,OrnamentStorager storager);
	public enum PreparedStatementType {
		INSERT_STORAGER , SELECT_STORAGER,UPDATE_STORAGER,REPLACE_STORAGER;
	}

}
