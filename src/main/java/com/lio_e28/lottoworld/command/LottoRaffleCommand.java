package com.lio_e28.lottoworld.command;

import com.lio_e28.lottoworld.game.LottoEvaluator;
import com.lio_e28.lottoworld.player.PlayerLotto;
import com.lio_e28.lottoworld.player.PlayerMoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.List;

public class LottoRaffleCommand implements CommandExecutor {
    private PlayerLotto playerLotto;
    private LottoEvaluator evaluator;
    private PlayerMoneyManager moneyManager;

    public LottoRaffleCommand(PlayerLotto playerLotto, LottoEvaluator evaluator, PlayerMoneyManager moneyManager) {
        this.playerLotto = playerLotto;
        this.evaluator = evaluator;
        this.moneyManager = moneyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lottoraffle")) {
            HashSet<Integer> winningNumbers = evaluator.getWinningNumbers();
            sender.sendMessage(ChatColor.YELLOW + "당첨 번호: " + winningNumbers);

            Bukkit.getOnlinePlayers().forEach(player -> {
                List<HashSet<Integer>> tickets = playerLotto.getTickets(player);
                for (HashSet<Integer> ticket : tickets) {
                    int matchingNumbers = evaluator.evaluateTicket(ticket);
                    String rank = evaluator.determineRank(matchingNumbers);
                    int prize = 0;
                    switch (rank) {
                        case "1등":
                            prize = 1_000_000;
                            break;
                        case "2등":
                            prize = 500_000;
                            break;
                        case "3등":
                            prize = 50_000;
                            break;
                    }
                    if (prize > 0) {
                        moneyManager.addBalance(player, prize);
                        player.sendMessage(ChatColor.GREEN + "축하합니다! " + rank + "에 당첨되어 " + prize + "원을 받았습니다!");
                    }
                };
            });

            return true;
        }
        return false;
    }
}
