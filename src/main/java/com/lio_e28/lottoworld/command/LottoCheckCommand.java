package com.lio_e28.lottoworld.command;

import com.lio_e28.lottoworld.game.LottoEvaluator;
import com.lio_e28.lottoworld.player.PlayerLotto;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;

public class LottoCheckCommand implements CommandExecutor {
    private PlayerLotto playerLotto;
    private LottoEvaluator evaluator;

    public LottoCheckCommand(PlayerLotto playerLotto, LottoEvaluator evaluator) {
        this.playerLotto = playerLotto;
        this.evaluator = evaluator;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "사용법: /lottocheck <player>");
            return true;
        }

        // 플레이어 이름으로 플레이어 객체를 가져옵니다.
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "플레이어를 찾을 수 없습니다.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("lottocheck")) {
            List<HashSet<Integer>> tickets = playerLotto.getTickets(targetPlayer);
            HashSet<Integer> winningNumbers = evaluator.getWinningNumbers();

            if (tickets.isEmpty()) {
                sender.sendMessage(ChatColor.RED + targetPlayer.getName() + "님은 보유한 로또 티켓이 없습니다.");
                return true;
            }

            sender.sendMessage(ChatColor.YELLOW + "당첨 번호: " + winningNumbers);
            for (int i = 0; i < tickets.size(); i++) {
                int matchingNumbers = evaluator.evaluateTicket(tickets.get(i));
                String rank = evaluator.determineRank(matchingNumbers);
                sender.sendMessage(ChatColor.GOLD + "[" + (i + 1) + "]" + ChatColor.GREEN + tickets.get(i) + " - " + rank);
            }
            return true;
        }
        return false;
    }
}
