package com.lio_e28.lottoworld.command;

import com.lio_e28.lottoworld.player.PlayerLotto;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;

public class LottoCommand implements CommandExecutor {
    private PlayerLotto playerLotto;

    public LottoCommand(PlayerLotto playerLotto) {
        this.playerLotto = playerLotto;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            return true;
        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("lotto")) {
            ArrayList<HashSet<Integer>> tickets = playerLotto.getTickets(player);
            if (tickets.isEmpty()) {
                player.sendMessage(ChatColor.RED + "보유한 로또 티켓이 없습니다.");
            } else {
                player.sendMessage(ChatColor.YELLOW + "당신의 로또 티켓:");
                for (int i = 0; i < tickets.size(); i++) {
                    player.sendMessage(ChatColor.GOLD + "[" + (i+1) + "]" + ChatColor.GREEN + tickets.get(i).toString());
                }
            }
            return true;
        }

        return false;
    }
}
