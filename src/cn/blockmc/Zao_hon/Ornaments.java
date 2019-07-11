package cn.blockmc.Zao_hon;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import cn.blockmc.Zao_hon.commands.Commands;
import cn.blockmc.Zao_hon.inventory.OrnamentManager;
import cn.blockmc.Zao_hon.storage.DataStorager;

public class Ornaments extends JavaPlugin {
	private ItemStack ornamentitem = null;
	private HashMap<UUID, HashMap<Attribute, AttributeModifier>> attributemodifiers = new HashMap<UUID, HashMap<Attribute, AttributeModifier>>();
//	private HashMap<UUID, String> pornaments = new HashMap<UUID, String>();
	private DataStorager dataStorager;
	private OrnamentManager ornamentManager;

	@Override
	public void onEnable() {
		instance = this;
		this.saveDefaultConfig();

		ornamentManager = new OrnamentManager(this);

//		this.loadPlayerOrnaments();
		this.loadOrnamentItem();
		Bukkit.getOnlinePlayers().forEach(player -> {
			this.updatePlayerAttribute(player);
		});

		this.getServer().getPluginManager().registerEvents(new EventListener(), this);
		this.getCommand("Ornaments").setExecutor(new Commands());

//		Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
//			savePlayerOrnaments();
//		} , 100, 10 * 60 * 1000);

		PR("========================");
		PR("      Ornaments         ");
		PR("     Version: " + this.getDescription().getVersion());
		PR("     Author:Zao_hon           ");
		PR("========================");
	}

	@Override
	public void onDisable() {
//		this.savePlayerOrnaments();
		Bukkit.getOnlinePlayers().forEach(player -> {
			this.resetPlayerAttribute(player);
		});
	}

//	public void saveDefaultOrnaments() {
//		File file = new File(this.getDataFolder(), "ornaments.yml");
//		if (!file.exists()) {
//			this.saveResource("ornaments.yml", false);
//		}
//	}

	public void loadOrnamentItem() {
		String displayname = this.getConfig().getString("Open-Item.DisplayName");
		List<String> lores = this.getConfig().getStringList("Open-Item.Lores");
		Material m = Material.valueOf(this.getConfig().getString("Open-Item.Material"));
		boolean b = this.getConfig().getBoolean("Open-Item.Enchantment");
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

//	public void savePlayerOrnaments() {
//		File folder = new File(this.getDataFolder(), "playerdata");
//		pornaments.keySet().forEach(uuid -> {
//			File file = new File(folder, uuid.toString());
//			if (!file.exists()) {
//				try {
//					file.createNewFile();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
//			conf.set("str", pornaments.get(uuid));
//			try {
//				conf.save(file);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//	}

	// public void loadPlayerOrnaments() {
	// File file = new File(this.getDataFolder(), "playerdata");
	// if (!file.exists()) {
	// file.mkdir();
	// }
	// for (File f : file.listFiles()) {
	// UUID uuid = UUID.fromString(f.getName());
	// FileConfiguration conf = YamlConfiguration.loadConfiguration(f);
	// String str = conf.getString("str");
	// pornaments.put(uuid, str);
	// }
	// }

//	public ItemStack getItemStack(Ornament o, int level) {
//		OrnamentAttribute attr = o.getAttribute(level);
//		ItemStack item = new ItemStack(attr.getMaterial());
//		ItemMeta meta = item.getItemMeta();
//		meta.setDisplayName(attr.getDisplayName());
//		meta.setLocalizedName(o.getName() + "." + level);
//		meta.setLore(attr.getLores());
//		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//		item.setItemMeta(meta);
//		if (attr.isEnchantment()) {
//			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
//		}
//		return item;
//	}

	public void updatePlayerAttribute(Player player) {
		UUID uuid = player.getUniqueId();
		int[] is = Caculator.caculateAttributeNumbers(ornamentManager.getPlayerStorager(uuid));
		AttributeModifier hm = new AttributeModifier(uuid + ".health", is[0], AttributeModifier.Operation.ADD_NUMBER);
		// AttributeModifier arm = new AttributeModifier(uuid+".armor", is[1],
		// AttributeModifier.Operation.ADD_NUMBER);
		// AttributeModifier lm = new AttributeModifier(uuid+".luck", is[2],
		// AttributeModifier.Operation.ADD_NUMBER);
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(hm);
		// player.getAttribute(Attribute.GENERIC_ARMOR).addModifier(arm);
		// player.getAttribute(Attribute.GENERIC_LUCK).addModifier(lm);
		resetPlayerAttribute(player);
		HashMap<Attribute, AttributeModifier> modifiers = new HashMap<Attribute, AttributeModifier>();
		modifiers.put(Attribute.GENERIC_MAX_HEALTH, hm);
		// modifiers.put(Attribute.GENERIC_ARMOR, arm);
		// modifiers.put(Attribute.GENERIC_LUCK, lm);
		attributemodifiers.put(player.getUniqueId(), modifiers);
	}

	public void resetPlayerAttribute(Player player) {
		if (attributemodifiers.containsKey(player.getUniqueId())) {
			attributemodifiers.get(player.getUniqueId()).forEach((attribute, modifier) -> {
				player.getAttribute(attribute).removeModifier(modifier);
			});
		}
	}

	public void PR(String str) {
		this.getLogger().info(str);
	}
//
//	public String getOrnamentStr(UUID uuid) {
//		return pornaments.getOrDefault(uuid, "");
//	}
//
//	public void setOrnamentStr(UUID uuid, String str) {
//		pornaments.put(uuid, str);
//	}

	public DataStorager getDataStorager() {
		return dataStorager;
	}
	public OrnamentManager getOrnamentManager(){
		return ornamentManager;
	}

	private static Ornaments instance = null;

	public static Ornaments getInstance() {
		return instance;
	}

	public ItemStack getOrnamentItem() {
		return ornamentitem.clone();
	}

}
