package com.lio_e28.lottoworld.command;

import com.lio_e28.lottoworld.game.LottoEvaluator;
import com.lio_e28.lottoworld.game.LottoNumberGenerator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.List;

public class LottoSettingCommand implements CommandExecutor {
    private LottoNumberGenerator generator = new LottoNumberGenerator();
    private LottoEvaluator evaluator;

    public LottoSettingCommand(LottoEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lottosetting")) {
            List<Integer> winningNumbersList = generator.generateLottoNumbers();
            HashSet<Integer> winningNumbers = new HashSet<>(winningNumbersList);

            // LottoEvaluator에 당첨 번호를 저장합니다.
            evaluator.setWinningNumbers(winningNumbers);

            // 플레이어에게 당첨 번호를 알립니다.
            sender.sendMessage(ChatColor.GREEN + "로또 당첨 번호: " + winningNumbers.toString());
            return true;
        }
        return false;
    }
}
