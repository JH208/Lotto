package com.lio_e28.lottoworld.gui;

import com.lio_e28.lottoworld.item.ItemManager;
import com.lio_e28.lottoworld.item.Number;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class LottoGUI {

    public static void openLottoGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 18, ChatColor.YELLOW+"로또 번호 추첨");

        inv.setItem(0, Number.number1);
        inv.setItem(1, Number.number2);
        inv.setItem(2, Number.number3);
        inv.setItem(3, Number.number4);
        inv.setItem(4, Number.number5);
        inv.setItem(5, Number.number6);
        inv.setItem(6, Number.number7);
        inv.setItem(7, Number.number8);
        inv.setItem(8, Number.number9);
        inv.setItem(9, Number.number10);
        inv.setItem(10, Number.number11);
        inv.setItem(11, Number.number12);
        inv.setItem(12, Number.number13);
        inv.setItem(13, Number.number14);
        inv.setItem(14, Number.number15);
        inv.setItem(15, ItemManager.filter);
        inv.setItem(16, ItemManager.filter);
        inv.setItem(17, ItemManager.filter);

        player.openInventory(inv);
    }
}
