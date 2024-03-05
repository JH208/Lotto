package com.lio_e28.lottoworld.command;

import com.lio_e28.lottoworld.game.LottoEvaluator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;

public class LottoEditCommand implements CommandExecutor {
    private LottoEvaluator evaluator;

    public LottoEditCommand(LottoEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "사용법: /lottoedit <기존 번호> <새 번호>");
            return true;
        }

        try {
            int oldNumber = Integer.parseInt(args[0]);
            int newNumber = Integer.parseInt(args[1]);

            HashSet<Integer> winningNumbers = evaluator.getWinningNumbers();

            if (winningNumbers.contains(oldNumber)) {
                winningNumbers.remove(oldNumber);
                winningNumbers.add(newNumber);
                evaluator.setWinningNumbers(winningNumbers);
                sender.sendMessage(ChatColor.GREEN + "당첨번호가 수정되었습니다: " + winningNumbers);
            } else {
                sender.sendMessage(ChatColor.RED + "기존 번호가 당첨번호에 없습니다.");
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "숫자를 올바르게 입력해주세요.");
        }

        return true;
    }
}
