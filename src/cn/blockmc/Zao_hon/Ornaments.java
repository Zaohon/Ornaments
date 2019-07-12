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
import cn.blockmc.Zao_hon.storage.SqliteStorager;

public class Ornaments extends JavaPlugin {
	private HashMap<UUID, HashMap<Attribute, AttributeModifier>> attributemodifiers = new HashMap<UUID, HashMap<Attribute, AttributeModifier>>();
	private DataStorager dataStorager;
	private OrnamentManager ornamentManager;

	@Override
	public void onEnable() {
		instance = this;
		this.saveDefaultConfig();

		ornamentManager = new OrnamentManager(this);
		dataStorager = new SqliteStorager(this);

//		this.loadOrnamentItem();
		Bukkit.getOnlinePlayers().forEach(player -> {
			this.updatePlayerAttribute(player);
		});

		this.getServer().getPluginManager().registerEvents(new EventListener(), this);
		this.getCommand("Ornaments").setExecutor(new Commands());

		PR("========================");
		PR("      Ornaments         ");
		PR("     Version: " + this.getDescription().getVersion());
		PR("     Author:Zao_hon           ");
		PR("========================");
	}

	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(player -> {
			this.resetPlayerAttribute(player);
		});
	}

	

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

	public DataStorager getDataStorager() {
		return dataStorager;
	}

	public OrnamentManager getOrnamentManager() {
		return ornamentManager;
	}

	private static Ornaments instance = null;

	public static Ornaments getInstance() {
		return instance;
	}


}
