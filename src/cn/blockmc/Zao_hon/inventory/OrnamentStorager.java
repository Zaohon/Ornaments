package cn.blockmc.Zao_hon.inventory;

import javax.annotation.Nonnull;

public class OrnamentStorager {
	private PlayerOrnament necklace;
	private PlayerOrnament bracelet;
	private PlayerOrnament ring;

	public void setOrnament(@Nonnull OrnamentType type, PlayerOrnament ornament) {
		switch (type) {
		case NECKLACE:
			this.necklace = ornament;
			return;
		case BRACELET:
			this.bracelet = ornament;
			return;
		case RING:
			this.bracelet = ornament;
			return;
		}

	}

	public PlayerOrnament getPlayerOrnament(@Nonnull OrnamentType type) {
		switch (type) {
		case NECKLACE:
			return necklace;
		case BRACELET:
			return bracelet;
		case RING:
			return ring;
		default:
			return null;
		}
	}

}
