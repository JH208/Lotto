package com.lio_e28.lottoworld.event;

import com.lio_e28.lottoworld.player.PlayerLotto;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class LottoNumberClick implements Listener {
    private final PlayerLotto playerLotto;

    public LottoNumberClick(PlayerLotto playerLotto) {
        this.playerLotto = playerLotto;
    }

    // 각 플레이어의 선택한 번호를 저장하는 맵
    private final HashMap<UUID, HashSet<Integer>> playerNumbers = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory openInv = event.getClickedInventory();

        if (openInv != null && event.getView().getTitle().contains("로또 번호 추첨")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                String displayName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

                if ("확인".equals(displayName)) {
                    HashSet<Integer> numbers = playerNumbers.get(player.getUniqueId());
                    if (numbers != null && numbers.size() == 6) {
                        player.sendMessage(ChatColor.GOLD + "로또 번호가 확인되었습니다!");
                        playerLotto.addTicket(player, numbers);
                        playerNumbers.remove(player.getUniqueId());
                        player.closeInventory();
                    }
                    return;
                }

                try {
                    int number = Integer.parseInt(displayName);
                    HashSet<Integer> numbers = playerNumbers.computeIfAbsent(player.getUniqueId(), k -> new HashSet<>());
                    if (numbers.contains(number)) {
                        player.sendMessage(ChatColor.RED + "이미 선택한 번호입니다!");
                        return;
                    }

                    if (numbers.size() >= 6) {
                        player.sendMessage(ChatColor.RED + "모든 번호를 이미 선택했습니다.");
                        return;
                    }

                    numbers.add(number);
                    player.sendMessage(ChatColor.GREEN + "선택한 번호: " + number);

                    if (numbers.size() == 6) {
                        player.sendMessage(ChatColor.GOLD + "모든 번호를 선택했습니다. 확인 버튼을 누르세요.");
                        openInv.setItem(17, createConfirmItem());
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (event.getView().getTitle().contains("로또 번호 추첨")) {
            if (playerNumbers.containsKey(player.getUniqueId())) {
                playerNumbers.remove(player.getUniqueId());
                player.sendMessage(ChatColor.RED + "로또 번호 선택이 초기화되었습니다.");
            }
        }
    }

    private ItemStack createConfirmItem() {
        ItemStack confirmItem = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = confirmItem.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GREEN + "확인");
            confirmItem.setItemMeta(meta);
        }
        return confirmItem;
    }
}
