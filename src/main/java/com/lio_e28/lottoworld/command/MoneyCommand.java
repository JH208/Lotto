package com.lio_e28.lottoworld.command;

import com.lio_e28.lottoworld.player.PlayerMoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoneyCommand implements CommandExecutor, TabCompleter {
    private final PlayerMoneyManager moneyManager;

    public MoneyCommand(PlayerMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "사용법: /money set|add|remove <플레이어> <금액>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "플레이어를 찾을 수 없습니다.");
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "set":
                moneyManager.setBalance(target, amount);
                sender.sendMessage(ChatColor.GREEN + target.getName() + "의 돈을 " + amount + "으로 설정했습니다.");
                break;
            case "add":
                moneyManager.addBalance(target, amount);
                sender.sendMessage(ChatColor.GREEN + target.getName() + "에게 " + amount + "만큼 돈을 추가했습니다.");
                break;
            case "remove":
                moneyManager.subtractBalance(target, amount);
                sender.sendMessage(ChatColor.GREEN + target.getName() + "의 돈에서 " + amount + "만큼 제거했습니다.");
                break;
            default:
                sender.sendMessage(ChatColor.RED + "사용법: /money set|add|remove <플레이어> <금액>");
                break;
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("set", "add", "remove");
        } else if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
