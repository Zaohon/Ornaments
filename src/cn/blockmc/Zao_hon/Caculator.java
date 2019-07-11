package cn.blockmc.Zao_hon;

import cn.blockmc.Zao_hon.inventory.OrnamentAttribute;
import cn.blockmc.Zao_hon.inventory.OrnamentStorager;
import cn.blockmc.Zao_hon.inventory.OrnamentType;

public class Caculator {
//	private static Ornaments plugin = Ornaments.getInstance();

//	public static int[] caculateAttributeNumbers(UUID uuid) {
//		int[] is = new int[4];
//		int h = 0;
//		int ar = 0;
//		int l = 0;
//		int ag = 0;
//		HashSet<OrnamentAttribute> attributes = caculateAttributes(uuid);
//		if (attributes != null) {
//			for (OrnamentAttribute attr : attributes) {
//				h += attr.getHealth();
//				ar += attr.getArmor();
//				l += attr.getLuck();
//				ag += attr.getAgile();
//			}
//		}
//		is[0] = h;
//		is[1] = ar;
//		is[2] = l;
//		is[3] = ag;
//		return is;
//	}
	public static int[] caculateAttributeNumbers(OrnamentStorager storage) {
	int[] is = new int[4];
	int h = 0;
	int ar = 0;
	int l = 0;
	int ag = 0;
	for(OrnamentType type:OrnamentType.values()) {
		try {
		OrnamentAttribute attr = storage.getPlayerOrnament(type).getAttribute();
		h += attr.getHealth();
		ar += attr.getArmor();
		l += attr.getLuck();
		ag += attr.getAgile();
		}catch(NullPointerException e) {
			continue;
		}
	}
	is[0] = h;
	is[1] = ar;
	is[2] = l;
	is[3] = ag;
	return is;
}

	public static boolean caculateBoolean(int i) {
		double d = caculate(i);
		double e = Math.random();
		return d >= e;
	}

	public static double caculate(int i) {
		return Math.pow(Math.log1p(i), 2.77) / 100;
	}

	// public static int caculateHealth(UUID uuid) {
	// int i = 0;
	// HashSet<OrnamentAttribute> attributes = caculateAttributes(uuid);
	// if (attributes != null) {
	// for (OrnamentAttribute attr : attributes) {
	// i += attr.getHealth();
	// }
	// }
	// return i;
	// }
	//
	// public static int caculateArmor(UUID uuid) {
	// int i = 0;
	// HashSet<OrnamentAttribute> attributes = caculateAttributes(uuid);
	// if (attributes != null) {
	// for (OrnamentAttribute attr : attributes) {
	// i += attr.getArmor();
	// }
	// }
	// return i;
	// }
	//
	// public static boolean caculateThumpable(UUID uuid) {
	// int i = 0;
	// HashSet<OrnamentAttribute> attributes = caculateAttributes(uuid);
	// if (attributes != null) {
	// for (OrnamentAttribute attr : attributes) {
	// i += attr.getLuck();
	// }
	// }
	// float f = caculateScale(i);
	// float r = (float) Math.random();
	// return f >= r;
	// }
	//
	// public static boolean caculateDodge(UUID uuid) {
	// int i = 0;
	// HashSet<OrnamentAttribute> attributes = caculateAttributes(uuid);
	// if (attributes != null) {
	// for (OrnamentAttribute attr : attributes) {
	// i += attr.getAgile();
	// }
	// }
	// float f = caculateScale(i);
	// float r = (float) Math.random();
	// return f >= r;
	// }

	public static float caculateScale(int i) {
		return (float) Math.pow(i, 0.8) / 100;
	}

//	public static HashSet<OrnamentAttribute> caculateAttributes(UUID uuid) {
//		String str = plugin.getOrnamentStr(uuid);
//		if (str.equals("")) {
//			return null;
//		} else {
//			HashSet<OrnamentAttribute> attributes = new HashSet<OrnamentAttribute>();
//			str = str.substring(0, str.length() - 1);
//			for (String st : str.split(";")) {
//				String[] s = st.split("\\.");
//				String name = s[0];
//				int level = Integer.valueOf(s[1]);
//				OrnamentAttribute attr = plugin.getOrnamentManager().getOrnament(name).getAttribute(level);
//				attributes.add(attr);
//			}
//			return attributes;
//		}
//	}
}
