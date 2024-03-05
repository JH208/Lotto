package com.lio_e28.lottoworld.command;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimerCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private BossBar timerBar;

    public TimerCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            try {
                if (timerBar != null) {
                    timerBar.removeAll();
                }
                int minutes = Integer.parseInt(args[0]);
                createNewTimerBar();
                startTimer(minutes);
                return true;
            } catch (NumberFormatException e) {
                sender.sendMessage("숫자를 입력해주세요.");
                return false;
            }
        }
        return false;
    }

    private void createNewTimerBar() {
        timerBar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        for (Player player : Bukkit.getOnlinePlayers()) {
            timerBar.addPlayer(player);
        }
        timerBar.setVisible(true);
        timerBar.setProgress(0);
    }

    private void startTimer(int minutes) {
        new BukkitRunnable() {
            double time = minutes * 60;
            double initialTime = time;

            @Override
            public void run() {
                if (time <= 0) {
                    timerBar.setVisible(false);
                    timerBar.removeAll();
                    this.cancel();
                } else {
                    int minutesLeft = (int) time / 60;
                    int secondsLeft = (int) time % 60;
                    timerBar.setTitle(String.format("제한 시간: %02d:%02d", minutesLeft, secondsLeft));
                    time--;
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // 1초마다 타이머 갱신
    }




}
