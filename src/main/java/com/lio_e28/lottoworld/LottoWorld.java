package com.lio_e28.lottoworld;

import com.lio_e28.lottoworld.command.*;

import com.lio_e28.lottoworld.event.*;
import com.lio_e28.lottoworld.game.LottoEvaluator;
import com.lio_e28.lottoworld.game.LottoNumberGenerator;
import com.lio_e28.lottoworld.player.PlayerLotto;
import com.lio_e28.lottoworld.player.PlayerMoneyManager;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class LottoWorld extends JavaPlugin {
    private PlayerLotto playerLotto;
    private Map<Material, Integer> itemSellPrices;

    @Override
    public void onEnable() {
        playerLotto = new PlayerLotto();
        LottoEvaluator evaluator = new LottoEvaluator();
        LottoNumberGenerator numberGenerator = new LottoNumberGenerator();
        PlayerMoneyManager moneyManager = new PlayerMoneyManager(this);
        MoneyCommand moneyCommand = new MoneyCommand(moneyManager);
        this.itemSellPrices = initializeItemSellPrices();

        this.getCommand("lottosetting").setExecutor(new LottoSettingCommand(evaluator));
        this.getCommand("lottoGui").setExecutor(new LottoGuiCommand());
        this.getCommand("lotto").setExecutor(new LottoCommand(playerLotto));
        this.getCommand("lottocheck").setExecutor(new LottoCheckCommand(playerLotto, evaluator));
        this.getCommand("lottoedit").setExecutor(new LottoEditCommand(evaluator));
        this.getCommand("givelotto").setExecutor(new LottoGiveCommand());
        this.getCommand("상점").setExecutor(new ShopCommand());
        this.getCommand("money").setExecutor(new MoneyCommand(moneyManager));
        this.getCommand("money").setTabCompleter(moneyCommand);
        this.getCommand("lottoraffle").setExecutor(new LottoRaffleCommand(playerLotto,evaluator,moneyManager));
        this.getCommand("moneyrank").setExecutor(new MoneyRankCommand(moneyManager));
        this.getCommand("spawn").setExecutor(new SpawnCommand());
        this.getCommand("timer").setExecutor(new TimerCommand(this));

        getServer().getPluginManager().registerEvents(new LottoNumberClick(playerLotto),this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(moneyManager), this);
        getServer().getPluginManager().registerEvents(new LottoItemListener(), this);
        getServer().getPluginManager().registerEvents(new ShopEventListener(moneyManager), this);
        getServer().getPluginManager().registerEvents(new AutoLottoListener(playerLotto, numberGenerator), this);
        getServer().getPluginManager().registerEvents(new RandomBoxListener(this), this);
        getServer().getPluginManager().registerEvents(new ShopListener(moneyManager, itemSellPrices), this);


    }
    private HashMap<Material, Integer> initializeItemSellPrices() {
        HashMap<Material, Integer> prices = new HashMap<>();
        // 나무 원목 가격 설정
        prices.put(Material.OAK_LOG, 100);
        prices.put(Material.SPRUCE_LOG, 100);
        prices.put(Material.BIRCH_LOG, 100);
        prices.put(Material.JUNGLE_LOG, 100);
        prices.put(Material.ACACIA_LOG, 100);
        prices.put(Material.DARK_OAK_LOG, 100);
        prices.put(Material.MANGROVE_LOG, 100);
        prices.put(Material.CHERRY_LOG, 100);

        // 광물류 가격 설정
        prices.put(Material.COPPER_INGOT, 150);
        prices.put(Material.GOLD_INGOT, 300);
        prices.put(Material.IRON_INGOT, 250);
        prices.put(Material.LAPIS_LAZULI, 200);
        prices.put(Material.DIAMOND, 800);
        prices.put(Material.NETHERITE_SCRAP, 1200);
        prices.put(Material.NETHERITE_INGOT, 8000);

        // 물고기류 가격 설정
        prices.put(Material.COD, 100);
        prices.put(Material.SALMON, 150);
        prices.put(Material.PUFFERFISH, 200);
        prices.put(Material.TROPICAL_FISH, 300);

        // 몬스터 전리품 가격 설정
        prices.put(Material.ROTTEN_FLESH, 50);
        prices.put(Material.BONE, 100);
        prices.put(Material.SPIDER_EYE, 120);
        prices.put(Material.GUNPOWDER, 100);
        prices.put(Material.ENDER_PEARL, 500);
        prices.put(Material.BLAZE_ROD, 600);

        return prices;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
