package cn.blockmc.Zao_hon.inventory;

import javax.annotation.Nonnull;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class PlayerOrnament {
	private Ornament ornament;
	private int level;
	private OrnamentAttribute attribute;

	private PlayerOrnament(Ornament ornament, int level) {
		this.ornament = ornament;
		this.level = level;
		this.attribute = ornament.getAttribute(level);
	}

	public Ornament getOrnament() {
		return ornament;
	}

	public int getLevel() {
		return level;
	}

	public OrnamentAttribute getAttribute() {
		return attribute;
	}

	public static PlayerOrnament asPlayerOrnament(Ornament ornament, int level) {
		return new PlayerOrnament(ornament, level);
	}


	public static PlayerOrnament asPlayerOrnament(OrnamentManager manager, String str) {
		try {
			String[] s = str.split("\\.");
			String name = s[0];
			int level = Integer.valueOf(s[1]);
			Ornament ornament = manager.getOrnament(name);
			return new PlayerOrnament(ornament, level);
		} catch (Exception e) {
			// ignore
		}
		return null;
	}
	public static PlayerOrnament asPlayerOrnament(OrnamentManager manager, @Nonnull ItemStack item) {
		return asPlayerOrnament(manager,item.getItemMeta().getLocalizedName());
	}

	public static ItemStack asItemStack(PlayerOrnament pornament) {
		ItemStack item = new ItemStack(pornament.getAttribute().getMaterial());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(pornament.getAttribute().getDisplayName());
		meta.setLore(pornament.getAttribute().getLores());
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		if (pornament.getAttribute().isEnchantment()) {
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		return item;
	}
}
