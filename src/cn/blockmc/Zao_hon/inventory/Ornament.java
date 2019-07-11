package cn.blockmc.Zao_hon.inventory;

public class Ornament {
	private String name = "Æ½·²µÄÊ×ÊÎ";
	private OrnamentType type = null;
	private OrnamentAttribute[] attributes = null;

	public Ornament(OrnamentType type, String name) {
		this.type = type;
		this.name = name;
	}

	public OrnamentAttribute getAttribute(int level) {
		return attributes[level];
	}

	public void setAttribute(OrnamentAttribute[] attributes) {
		this.attributes = attributes;
	}

	public OrnamentType getType() {
		return type;
	}

	public String getName() {
		return this.name;
	}

}
