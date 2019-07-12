package cn.blockmc.Zao_hon.inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cn.blockmc.Zao_hon.Ornaments;

public class OrnamentManager {
	private Ornaments plugin;
	private HashMap<String, Ornament> ornaments = new HashMap<String, Ornament>();
	private HashMap<UUID, OrnamentStorager> playerStorager = new HashMap<UUID, OrnamentStorager>();
	private ItemStack ornamentitem = null;

	public OrnamentManager(Ornaments plugin) {
		this.plugin = plugin;
		saveDefaultOrnaments();
		loadOrnaments();
	}

	public void saveDefaultOrnaments() {
		File file = new File(plugin.getDataFolder(), "ornaments.yml");
		if (!file.exists()) {
			plugin.saveResource("ornaments.yml", false);
		}
	}

	public void loadOrnaments() {
		File file = new File(plugin.getDataFolder(), "ornaments.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.getKeys(false).forEach(name -> {
			ConfigurationSection conf = config.getConfigurationSection(name);
			Ornament ornament = new Ornament(OrnamentType.valueOf(conf.getString("Type", "NECKLACE")), name);
			Set<String> keys = conf.getKeys(false);
			int lenth = keys.size() - 1;
			OrnamentAttribute[] attributes = new OrnamentAttribute[lenth];
			conf.getKeys(false).forEach(level -> {
				try {
					int l = Integer.valueOf(level);
					ConfigurationSection con = conf.getConfigurationSection(level);
					String displayname = con.getString("DisplayName");
					List<String> lores = con.getStringList("Lores");
					Material material = Material.valueOf(con.getString("Material"));
					int health = con.getInt("Health");
					int armor = con.getInt("Armor");
					int luck = con.getInt("Luck");
					int agile = con.getInt("Agile");
					boolean enc = con.getBoolean("Enchantment", false);
					OrnamentAttribute attr = new OrnamentAttribute(displayname, lores, material, health, armor, luck,
							agile, enc);
					attributes[l] = attr;
				} catch (NumberFormatException e) {
					// ignore
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			ornament.setAttribute(attributes);
			ornaments.put(name, ornament);
		});
	}
	public void loadOrnamentItem() {
		String displayname = plugin.getConfig().getString("Open-Item.DisplayName","°Ïe Œ∆∑");
		List<String> lores = plugin.getConfig().getStringList("Open-Item.Lores");
		Material m = Material.valueOf(plugin.getConfig().getString("Open-Item.Material","SUNFLOWER"));
		boolean b = plugin.getConfig().getBoolean("Open-Item.Enchantment",false);
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		meta.setLore(lores);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		if (b) {
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		ornamentitem = item;

	}

	public Ornament getOrnament(String str) {
		if (ornaments.containsKey(str)) {
			return ornaments.get(str);
		} else {
			return null;
		}
	}
	public HashMap<String, Ornament> getOrnaments(){
		return ornaments;
	}

	public OrnamentStorager getPlayerStorager(UUID uuid) {
		if(playerStorager.containsKey(uuid)){
			return playerStorager.get(uuid);
		}
		OrnamentStorager storager = plugin.getDataStorager().getPlayerStorager(uuid);
		playerStorager.put(uuid, storager);
		return storager;
	}
	public void setPlayerStorager(UUID uuid,OrnamentStorager storager) {
		playerStorager.put(uuid, storager);
		plugin.getDataStorager().setPlayerStorager(uuid, storager);
	}
	
	public Ornaments getPlugin(){
		return plugin;
	}
	public ItemStack getOrnamentItem() {
		return ornamentitem.clone();
	}


}
