package cn.blockmc.Zao_hon.inventory;

import java.util.List;

import org.bukkit.Material;

public class OrnamentAttribute {
	private String displayname = "";
	private List<String> lores = null;
	private Material material;
	private int health = 0;
	private int armor = 0;
	private int luck = 0;
	private int agile = 0;
	private boolean enchantment = false;

	public OrnamentAttribute(final String displayname, final List<String> lores, final Material material,
			final int health, final int armor, final int luck, final int agile, final boolean enchantment) {
		this.displayname = displayname;
		this.lores = lores;
		this.material = material;
		this.health = health;
		this.armor = armor;
		this.luck = luck;
		this.agile = agile;
		this.enchantment = enchantment;
	}

	public String getDisplayName() {
		return this.displayname;
	}

	public List<String> getLores() {
		return this.lores;
	}

	public Material getMaterial() {
		return this.material

		;
	}

	public int getHealth() {
		return this.health;
	}

	public int getArmor() {
		return this.armor;
	}

	public int getLuck() {
		return this.luck;
	}

	public int getAgile() {
		return this.agile;
	}

	public boolean isEnchantment() {
		return this.enchantment;
	}
}