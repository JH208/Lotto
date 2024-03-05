package com.lio_e28.lottoworld.event;

import com.lio_e28.lottoworld.player.PlayerMoneyManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ShopListener implements Listener {
    private final PlayerMoneyManager moneyManager;
    private final Map<Material, Integer> itemPrices;

    public ShopListener(PlayerMoneyManager moneyManager, Map<Material, Integer> itemPrices) {
        this.moneyManager = moneyManager;
        this.itemPrices = itemPrices;
    }



    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        // 상점 GUI에서의 클릭 이벤트인지 확인
        if (event.getView().getTitle().equals("상점")) {
            // 클릭된 인벤토리가 상점 GUI 인벤토리인 경우에만 처리
            if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null && itemPrices.containsKey(clickedItem.getType())) {
                    event.setCancelled(true); // 이벤트 취소

                    Material material = clickedItem.getType();
                    int price = itemPrices.get(material);
                    int amountToSell = event.isRightClick() ? clickedItem.getAmount() : 1; // 우클릭은 전체 판매, 좌클릭은 1개 판매

                    if (player.getInventory().containsAtLeast(new ItemStack(material), amountToSell)) {
                        // 플레이어 인벤토리에서 아이템 제거
                        player.getInventory().removeItem(new ItemStack(material, amountToSell));

                        // 돈 추가
                        moneyManager.addBalance(player, price * amountToSell);

                        player.sendMessage(ChatColor.GREEN + "성공적으로 " + amountToSell + "개의 " + material.name() + "을(를) 판매했습니다. +" + (price * amountToSell) + "원");
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                    } else {
                        player.sendMessage(ChatColor.RED + "판매할 충분한 아이템이 없습니다.");
                    }
                }
            }
        }
    }


}