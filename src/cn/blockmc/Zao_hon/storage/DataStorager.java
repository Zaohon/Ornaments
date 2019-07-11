package cn.blockmc.Zao_hon.storage;

import java.util.UUID;

public abstract class DataStorager {
	public abstract String[] getPlayerStorager(UUID uuid);
	public abstract void setPlayerStorager(UUID uuid,String[] str);
}
