package com.lio_e28.lottoworld.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {

    public static final ItemStack filter = buildItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1," ");
    public static final ItemStack lotto = buildItem(Material.PAPER, 1, ChatColor.GOLD + "로또");
    public static final ItemStack autolotto = buildItem(Material.PAPER, 1, ChatColor.GOLD + "자동 로또");
    public static final ItemStack shop = buildItem(Material.NETHER_STAR, 1, "상점");
    public static final ItemStack randomitem = buildItem(Material.SHULKER_BOX,1,"랜덤 박스");

    public static ItemStack buildItem(Material type, int amount, String displayName, String... lore) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null){
            meta.setDisplayName(displayName);
            meta.setLore(Arrays.asList(lore));
            stack.setItemMeta(meta);
        } else {
            System.err.println("ItemMeta is null for item: " + type.toString());
        }
        return stack;
    }
}