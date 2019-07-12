package cn.blockmc.Zao_hon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import cn.blockmc.Zao_hon.inventory.OrnamentGUI;

public class EventListener implements Listener {
	private Ornaments plugin;

	public EventListener() {
		this.plugin = Ornaments.getInstance();
	}

	@EventHandler
	public void onClickOrnament(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			ItemStack item = e.getItem();
			if (item != null && item.isSimilar(plugin.getOrnamentItem())) {
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
		ItemStack item = (ItemStack) e.getItemDrop();
		if(item.isSimilar(plugin.getOrnamentItem())){
			e.setCancelled(true);
		}
	}
}
