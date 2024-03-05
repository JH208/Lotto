package com.lio_e28.lottoworld.event;

import com.lio_e28.lottoworld.game.LottoNumberGenerator;
import com.lio_e28.lottoworld.player.PlayerLotto;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;

public class AutoLottoListener implements Listener {

    private PlayerLotto playerLotto;
    private LottoNumberGenerator numberGenerator;

    public AutoLottoListener(PlayerLotto playerLotto, LottoNumberGenerator numberGenerator) {
        this.playerLotto = playerLotto;
        this.numberGenerator = numberGenerator;
    }

    @EventHandler
    public void onPlayerUseAutoLottoItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (event.getAction().isRightClick() && item != null && item.getType() == Material.PAPER && item.hasItemMeta()
                && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "자동 로또")) {

            List<Integer> autoNumbers = numberGenerator.generateLottoNumbers();
            playerLotto.addTicket(player, new HashSet<>(autoNumbers));
            player.sendMessage(ChatColor.GREEN + "자동 로또 번호가 생성되었습니다: " + autoNumbers);

            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().removeItem(item);
            }

            event.setCancelled(true);
        }
    }
}
