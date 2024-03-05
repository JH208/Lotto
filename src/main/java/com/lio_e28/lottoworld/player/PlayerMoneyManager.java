package com.lio_e28.lottoworld.player;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class PlayerMoneyManager {
    private JavaPlugin plugin;
    private HashMap<UUID, Integer> playerBalances;

    public PlayerMoneyManager(JavaPlugin plugin) {
        this.plugin = plugin;
        playerBalances = new HashMap<>();
        startBalanceDisplayTask();
    }

    private void startBalanceDisplayTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                updateActionBar(player);
            }
        }, 0L, 20L);
    }

    public void addBalance(Player player, int amount) {
        UUID playerId = player.getUniqueId();
        playerBalances.put(playerId, playerBalances.getOrDefault(playerId, 0) + amount);
        updateActionBar(player);
    }

    public void subtractBalance(Player player, int amount) {
        UUID playerId = player.getUniqueId();
        int newBalance = playerBalances.getOrDefault(playerId, 0) - amount;
        if (newBalance < 0) {
            newBalance = 0;
        }
        playerBalances.put(playerId, newBalance);
        updateActionBar(player);
    }

    public int getBalance(Player player) {
        return playerBalances.getOrDefault(player.getUniqueId(), 0);
    }

    private void updateActionBar(Player player) {
        int balance = getBalance(player);
        String message = ChatColor.YELLOW + "돈: " + balance;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public boolean hasAccount(Player player) {
        return playerBalances.containsKey(player.getUniqueId());
    }

    public void setBalance(Player player, int amount) {
        playerBalances.put(player.getUniqueId(), amount);
        updateActionBar(player);
    }
}
