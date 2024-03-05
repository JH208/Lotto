package com.lio_e28.lottoworld.command;

import com.lio_e28.lottoworld.gui.LottoGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LottoGuiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            LottoGUI.openLottoGui(player);
            return true;
        }
        return false;
    }
}
