package com.lio_e28.lottoworld.event;

import com.lio_e28.lottoworld.gui.LottoGUI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LottoItemListener implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            ItemStack item = event.getItem();
            if (item != null && item.getType() == Material.PAPER && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD+ "로또")) {
                Player player = event.getPlayer();
                LottoGUI.openLottoGui(player);
                item.setAmount(item.getAmount() - 1);
                event.setCancelled(true);
            }
        }
    }
}