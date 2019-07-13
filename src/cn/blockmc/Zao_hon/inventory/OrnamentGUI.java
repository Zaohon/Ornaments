package cn.blockmc.Zao_hon.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import cn.blockmc.Zao_hon.Caculator;

public class OrnamentGUI {
	{
		blank = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = blank.getItemMeta();
		meta.setDisplayName(" ");
		blank.setItemMeta(meta);
	}

	private static ItemStack blank;
	private ItemStack head;
	private OrnamentManager manager;
	private OrnamentStorager storager;
	private Player player;
	private Inventory inventory = Bukkit.createInventory(null, 54, "§e饰品栏");
	private OrnamentGUIListener listener = null;
	private boolean open;

	public OrnamentGUI(OrnamentManager manager,Player p) {
		this.manager = manager;
		this.player = p;
		int[] is = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 16, 17, 18, 19, 20, 21, 23, 24, 25, 26, 27, 28, 29,
				30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53 };
		for (int i : is) {
			inventory.setItem(i, blank.clone());
		}

		head = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		meta.setOwningPlayer(p);
		head.setItemMeta(meta);

		storager = manager.getPlayerStorager(p.getUniqueId());
		updateHead();
		
		listener = new OrnamentGUIListener(this);
	}

	public void updateHead() {
		for (OrnamentType type : OrnamentType.values()) {
			ItemStack item = inventory.getItem(type.getSlot());
			storager.setOrnament(type, PlayerOrnament.asPlayerOrnament(manager, item));
		}
		int[] i = Caculator.caculateAttributeNumbers(storager);
		ItemMeta meta = head.getItemMeta();
		meta.setDisplayName("§d总属性加成:");
		ArrayList<String> lores = new ArrayList<String>();
		lores.add("§d血量: §e+" + i[0] + " §d护甲: §e+" + i[1]);
		lores.add("§d幸运: §e+" + i[2] + " §d敏捷: §e+" + i[1]);
		meta.setLore(lores);
		head.setItemMeta(meta);
		inventory.setItem(22, head);
	}

	public void open() {
		if (!open) {
			open = true;
			player.openInventory(inventory);
			player.updateInventory();
			manager.getPlugin().getServer().getPluginManager().registerEvents(listener, manager.getPlugin());
		}
	}

	public void close() {
		if (open) {
			open = false;
			manager.setPlayerStorager(player.getUniqueId(), storager);
			manager.getPlugin().updatePlayerAttribute(player);
			HandlerList.unregisterAll(listener);
		}
	}
//
//	public void savePlayerOrnaments() {
//		String str = "";
//		for (OrnamentType type : OrnamentType.values()) {
//			ItemStack item = inventory.getItem(type.getSlot());
//			if (item != null && item.hasItemMeta()) {
//				str = str + item.getItemMeta().getLocalizedName() + ";";
//			}
//		}
//		plugin.setOrnamentStr(player.getUniqueId(), str);
//	}

	public Player getPlayer() {
		return player;
	}

	public Inventory getInventory() {
		return inventory;
	}
	public OrnamentManager getManager() {
		return manager;
	}

	public static OrnamentGUI getPlayerGUI(OrnamentManager manager,Player player) {
		if(playerguis.containsKey(player.getUniqueId())){
			return playerguis.get(player.getUniqueId());
		}else{
			OrnamentGUI gui = new OrnamentGUI(manager,player);
			playerguis.put(player.getUniqueId(), gui);
			return gui;
		}
	}

	public static HashMap<UUID, OrnamentGUI> playerguis = new HashMap<UUID, OrnamentGUI>();
}
