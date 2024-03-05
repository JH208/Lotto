package com.lio_e28.lottoworld.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.lio_e28.lottoworld.item.ItemManager.*;

public class ShopGUI {

    public static void openMainGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "상점");
        addItemWithLore(inv, 0, Material.OAK_LOG, "나무 원목",  "판매: 100원");
        addItemWithLore(inv, 1, Material.SPRUCE_LOG, "가문비 나무 원목", "판매: 100원");
        addItemWithLore(inv, 2, Material.BIRCH_LOG, "자작 나무 원목", "판매: 100원");
        addItemWithLore(inv, 3, Material.JUNGLE_LOG, "정글 나무 원목", "판매: 100원");
        addItemWithLore(inv, 4, Material.ACACIA_LOG, "아카시아 나무 원목", "판매: 100원");
        addItemWithLore(inv, 5, Material.DARK_OAK_LOG, "어두운 참나무 원목", "판매: 100원");
        addItemWithLore(inv, 6, Material.MANGROVE_LOG, "맹그로브 나무 원목", "판매: 100원");
        addItemWithLore(inv, 7, Material.CHERRY_LOG, "체리 나무 원목", "판매: 100원");

        // 광물류
        addItemWithLore(inv, 9, Material.COPPER_INGOT, "구리괴", "판매: 150원");
        addItemWithLore(inv, 10, Material.GOLD_INGOT, "금괴", "판매: 300원");
        addItemWithLore(inv, 11, Material.IRON_INGOT, "철괴", "판매: 250원");
        addItemWithLore(inv, 12, Material.LAPIS_LAZULI, "청금석", "판매: 200원");
        addItemWithLore(inv, 13, Material.DIAMOND, "다이아몬드", "판매: 800원");
        addItemWithLore(inv, 14, Material.NETHERITE_SCRAP, "네더라이트 조각", "판매: 1200원");
        addItemWithLore(inv, 15, Material.NETHERITE_INGOT, "네더라이트 잉곳", "판매: 8000원");

        // 몬스터 전리품
        addItemWithLore(inv, 18, Material.ROTTEN_FLESH, "썩은 살점", "판매: 50원");
        addItemWithLore(inv, 19, Material.BONE, "뼈", "판매: 100원");
        addItemWithLore(inv, 20, Material.SPIDER_EYE, "거미 눈", "판매: 120원");
        addItemWithLore(inv, 21, Material.GUNPOWDER, "화약", "판매: 100원");
        addItemWithLore(inv, 22, Material.ENDER_PEARL, "엔더 진주", "판매: 500원");
        addItemWithLore(inv, 23, Material.BLAZE_ROD, "블레이즈 막대", "판매: 600원");

        // 물고기류
        addItemWithLore(inv, 27, Material.COD, "대구", "판매: 100원");
        addItemWithLore(inv, 28, Material.SALMON, "연어", "판매: 150원");
        addItemWithLore(inv, 29, Material.PUFFERFISH, "복어", "판매: 200원");
        addItemWithLore(inv, 30, Material.TROPICAL_FISH, "열대어", "판매: 300원");

        inv.setItem(47, lotto);
        inv.setItem(49, autolotto);
        inv.setItem(51, randomitem);
        player.openInventory(inv);
    }

    private static void addItemWithLore(Inventory inv, int slot, Material material, String name, String... lores) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GREEN + name);
            List<String> loreList = new ArrayList<>();
            for (String lore : lores) {
                loreList.add(ChatColor.GRAY + lore);
            }
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
        inv.setItem(slot, item);
    }

}