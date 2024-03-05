package com.lio_e28.lottoworld.event;

import com.lio_e28.lottoworld.player.PlayerMoneyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private PlayerMoneyManager moneyManager;

    public PlayerJoinListener(PlayerMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!moneyManager.hasAccount(event.getPlayer())) {
            moneyManager.setBalance(event.getPlayer(), 1000);
        }
    }
}
