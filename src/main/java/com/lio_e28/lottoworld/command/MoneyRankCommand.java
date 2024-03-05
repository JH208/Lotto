package com.lio_e28.lottoworld.command;

import com.lio_e28.lottoworld.player.PlayerMoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MoneyRankCommand implements CommandExecutor {
    private PlayerMoneyManager moneyManager;

    public MoneyRankCommand(PlayerMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moneyrank")) {
            Map<Player, Integer> sortedBalances = Bukkit.getOnlinePlayers().stream()
                    .collect(Collectors.toMap(player -> player, player -> moneyManager.getBalance(player)))
                    .entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder())) // 내림차순 정렬
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            StringBuilder rankMessage = new StringBuilder(ChatColor.GOLD + "보유 금액 순위:\n");
            int rank = 1;
            for (Map.Entry<Player, Integer> entry : sortedBalances.entrySet()) {
                rankMessage.append(ChatColor.YELLOW).append(rank).append(". ")
                        .append(entry.getKey().getName()).append(": ")
                        .append(ChatColor.GREEN).append(entry.getValue()).append("원\n");
                rank++;
            }

            Bukkit.broadcastMessage(rankMessage.toString());
            return true;
        }

        return false;
    }
}
