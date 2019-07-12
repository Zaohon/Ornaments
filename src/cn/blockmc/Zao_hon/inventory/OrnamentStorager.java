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
	@Override
	public String toString(){
		String sNecklace = necklace==null?"null":necklace.toString();
		String sBracelet = bracelet==null?"null":bracelet.toString();
		String sRing =  ring==null?"null": ring.toString();
		return "NECKLACE:"+sNecklace+"\nBRACELET:"+sBracelet+"\nRING:"+sRing;
	}
	public String getPlayerOrnamentStr(@Nonnull OrnamentType type){
		PlayerOrnament po = getPlayerOrnament(type);
		return po==null?null:po.toString();
	}

}
