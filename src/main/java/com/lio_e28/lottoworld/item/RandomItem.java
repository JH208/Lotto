package com.lio_e28.lottoworld.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static com.lio_e28.lottoworld.item.ItemManager.buildItem;

public class RandomItem {
    public static final ItemStack randomitem = buildItem(Material.SHULKER_BOX,1,"랜덤 박스");
    public static final ItemStack questionbook = buildItem(Material.BOOK,1,"예/아니오 질문권","운영자에게 질문할수 있습니다.");

}
