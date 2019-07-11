package cn.blockmc.Zao_hon.inventory;

public enum OrnamentType {
	NECKLACE(11), BRACELET(15), RING(40);

	private int slot;

	OrnamentType(int slot) {
		this.slot = slot;
	}

	public int getSlot() {
		return this.slot;
	}
}
