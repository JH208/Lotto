package com.lio_e28.lottoworld.command;

import com.lio_e28.lottoworld.item.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LottoGiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            switch (args.length){
                case 0:
                    sender.sendMessage("로또가 지급되었습니다.");
                    player.getInventory().addItem(ItemManager.lotto);
                    break;
                case 1:
                    try{
                        int amount = Integer.parseInt(args[0]);
                        ItemStack tpplayer = new ItemStack(ItemManager.lotto);
                        tpplayer.setAmount(amount);
                        player.getInventory().addItem(tpplayer);
                        break;
                    }catch (NumberFormatException e){
                        player.sendMessage("명령어를 재대로 입력해주세요.");
                        return false;
                    }catch (Exception e){
                        player.sendMessage("명령어를 재대로 입력해주세요.");
                        return false;
                    }
            }
        }else{
            sender.sendMessage("콘솔 불가능");
        }
        return false;
    }
}
