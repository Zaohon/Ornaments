package cn.blockmc.Zao_hon.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import cn.blockmc.Zao_hon.Ornaments;
import cn.blockmc.Zao_hon.inventory.PlayerOrnament;

public class Commands implements CommandExecutor {
	private Ornaments plugin;

	public Commands() {
		this.plugin = Ornaments.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		int lenth = args.length;
		if (lenth >= 1) {
			if (args[0].equals("give")) {
				if (lenth >= 3) {
					Player player = Bukkit.getPlayer(args[1]);
					String name = args[2];
					int level = 0;
					PlayerOrnament o =PlayerOrnament.asPlayerOrnament(plugin.getOrnamentManager().getOrnament(name),level);
					ItemStack item =PlayerOrnament.asItemStack(o);
					player.getInventory().addItem(item);
					player.sendMessage("»ñµÃ" + name);
				} else {
					sender.sendMessage("¡ìe/or give player name level");
				}
			}
			if (args[0].equals("get")) {
				Player player = (Player) sender;
				player.getInventory().addItem(plugin.getOrnamentItem());
				plugin.getOrnamentManager().getOrnaments().values().forEach(o -> {
					PlayerOrnament po = PlayerOrnament.asPlayerOrnament(o, 4);
					player.getInventory().addItem(PlayerOrnament.asItemStack(po));

				});
			}
			return true;
		}
		return true;
	}

}
