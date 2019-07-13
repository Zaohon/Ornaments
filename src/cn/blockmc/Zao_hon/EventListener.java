package cn.blockmc.Zao_hon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import cn.blockmc.Zao_hon.inventory.OrnamentGUI;
import cn.blockmc.Zao_hon.inventory.PlayerOrnament;

public class EventListener implements Listener {
	private Ornaments plugin;

	public EventListener() {
		this.plugin = Ornaments.getInstance();
	}

	@EventHandler
	public void onClickOrnament(PlayerInteractEvent e) {
		ItemStack item = e.getItem();
		PlayerOrnament o = PlayerOrnament.asPlayerOrnament(plugin.getOrnamentManager(),item);
		if(o!=null){
			e.setCancelled(true);
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();

			if (item != null && item.isSimilar(plugin.getOrnamentManager().getOrnamentItem())) {
				e.setCancelled(true);
				OrnamentGUI gui = OrnamentGUI.getPlayerGUI(plugin.getOrnamentManager(), p);
				gui.open();
			}
		}
	}

	@EventHandler
	public void onDamagedEntity(EntityDamageByEntityEvent e) {
		// Player is Damager
		if (e.getDamager() instanceof Player) {

			Player player = (Player) e.getDamager();
			int[] attributes = Caculator.caculateAttributeNumbers(plugin.getOrnamentManager().getPlayerStorager(player.getUniqueId()));
			if (Caculator.caculateBoolean(attributes[2])) {
				e.setDamage(e.getDamage() * 2);
			}
		}

		// Player is Damaged
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			int[] attributes = Caculator.caculateAttributeNumbers(plugin.getOrnamentManager().getPlayerStorager(player.getUniqueId()));
			if (Caculator.caculateBoolean(attributes[3])) {
				e.setDamage(0);
				return;
			}
			e.setDamage(e.getDamage() * (1 - Caculator.caculate(attributes[1])));
			return;
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		plugin.updatePlayerAttribute(player);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		plugin.resetPlayerAttribute(player);
	}
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e){
		ItemStack item = e.getItemDrop().getItemStack();
		if(item.isSimilar(plugin.getOrnamentManager().getOrnamentItem())){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerClickinv(InventoryClickEvent e){
		ItemStack item1 = e.getCurrentItem();
		if(item1!=null&&item1.isSimilar(plugin.getOrnamentManager().getOrnamentItem())){
			e.setCancelled(true);
			return;
		}
		ItemStack item2 = e.getCursor();
		if(item2!=null&&item2.isSimilar(plugin.getOrnamentManager().getOrnamentItem())){
			e.setCancelled(true);
			return;
		}
		
	}

}
