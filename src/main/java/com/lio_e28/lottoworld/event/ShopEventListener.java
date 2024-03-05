package com.lio_e28.lottoworld.event;

import com.lio_e28.lottoworld.player.PlayerMoneyManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import com.lio_e28.lottoworld.gui.ShopGUI;
import org.bukkit.ChatColor;
import static com.lio_e28.lottoworld.item.ItemManager.*;


public class ShopEventListener implements Listener {
    private final PlayerMoneyManager moneyManager;
    private final int itemCost = 1000; // 구매 가격

    public ShopEventListener(PlayerMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();


        if (event.getView().getTitle().equals("상점") && clickedItem != null && event.getClickedInventory() != player.getInventory()) {
            event.setCancelled(true); // 이벤트 취소

            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

            String viewTitle = event.getView().getTitle();
            if (viewTitle.equals("상점")) {
                if (moneyManager.getBalance(player) >= itemCost) {
                    switch (itemName) {
                        case "로또":
                            giveItemToPlayer(player, lotto);
                            player.sendMessage(ChatColor.GREEN + "로또 티켓을 구매했습니다!");
                            break;
                        case "자동 로또":
                            giveItemToPlayer(player, autolotto);
                            player.sendMessage(ChatColor.GREEN + "자동 로또 티켓을 구매했습니다!");
                            break;
                        case "랜덤 박스":
                            giveItemToPlayer(player, randomitem);
                            player.sendMessage(ChatColor.GREEN + "랜덤 박스를 구매했습니다!");
                            break;
                        default:
                            break;
                    }
                    moneyManager.subtractBalance(player, itemCost);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                } else {
                    player.sendMessage(ChatColor.RED + "잔액이 부족합니다!");
                }
            }
        }
    }

    private void giveItemToPlayer(Player player, ItemStack itemStack) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            player.sendMessage(ChatColor.RED + "인벤토리가 가득 찼습니다. 아이템이 지면에 드롭되었습니다.");
        } else {
            player.getInventory().addItem(itemStack);
        }
    }



    @EventHandler
    public void onPlayerUseShopItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                item != null && item.hasItemMeta() && "상점".equals(item.getItemMeta().getDisplayName())) {
            ShopGUI.openMainGUI(player);
            event.setCancelled(true);
        }
    }
}
