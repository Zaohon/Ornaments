package cn.blockmc.Zao_hon.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import cn.blockmc.Zao_hon.Ornaments;

public class OrnamentGUIListener implements Listener {
//	private Ornaments plugin;
	private OrnamentGUI gui;
	//
	private int i = 0;

	public OrnamentGUIListener(OrnamentGUI gui) {
//		this.plugin = Ornaments.getInstance();
		this.gui = gui;
	}

	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		// guiinventory
		Inventory inventory = gui.getInventory();
		ItemStack clicked = e.getCurrentItem();
		if (p.getUniqueId().equals(gui.getPlayer().getUniqueId())) {
			if (clicked != null && clicked.hasItemMeta()) {
				e.setCancelled(true);
//				String lname = clicked.getItemMeta().getLocalizedName();
//				String[] str = lname.split("\\.");
//				String oname = str[0];
//				Ornament o = plugin.getOrnament(oname);
//				}
//				if (o != null) {
//					Inventory clickedinv = e.getClickedInventory();
//					Inventory pinventory = p.getInventory();
//					int slot = o.getType().getSlot();
				PlayerOrnament ornament = PlayerOrnament.asPlayerOrnament(gui.getManager(), clicked);
				if (ornament != null) {
					Inventory clickedinv = e.getClickedInventory();
					Inventory pinventory = p.getInventory();
					int slot = ornament.getOrnament().getType().getSlot();
					/// Main Code
					if (clickedinv.equals(inventory)) {
						// full inventory
						int empty = pinventory.firstEmpty();
						if (empty < 0) {
							p.sendMessage("¡ìcFull inventory");
							////////////////
						} else {
							inventory.clear(slot);
							pinventory.addItem(clicked);
							//
							gui.updateHead();
							//
						}
						p.updateInventory();
					} else {
						ItemStack oitem = inventory.getItem(slot);
						int pslot = e.getSlot();
						inventory.setItem(slot, clicked);
						pinventory.setItem(pslot, oitem);
						p.updateInventory();
						//
						gui.updateHead();
						//
					}
					return;
				}
				/////////////
			}

		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Inventory inv = e.getInventory();
		if (inv != null && inv.equals(gui.getInventory())) {
			gui.close();
			;
		}

	}

}
