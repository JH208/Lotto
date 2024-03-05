package com.lio_e28.lottoworld.event;

import com.lio_e28.lottoworld.item.RandomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class  RandomBoxListener implements Listener {
    private final JavaPlugin plugin;
    private List<ItemStack> possibleItems = new ArrayList<>();
    private final Random random = new Random();

    public RandomBoxListener(JavaPlugin plugin) {
        this.plugin = plugin;
        initializeItems();
    }
    private void initializeItems() {
        possibleItems.add(RandomItem.questionbook);
        possibleItems.add(new ItemStack(Material.DIAMOND, 1));
        possibleItems.add(new ItemStack(Material.GOLD_INGOT,1));
        possibleItems.add(new ItemStack(Material.COOKED_BEEF,16));
        possibleItems.add(new ItemStack(Material.IRON_PICKAXE,1));
        possibleItems.add(new ItemStack(Material.IRON_AXE,1));
    }
    @EventHandler
    public void onPlayerUseRandomBox(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.SHULKER_BOX && item.hasItemMeta()
                && item.getItemMeta().getDisplayName().equals("랜덤 박스")
                && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            event.setCancelled(true);

            Location blockLocation = event.getClickedBlock() != null ? event.getClickedBlock().getLocation() : player.getLocation();
            Location spawnLocation = blockLocation.clone().add(0.5, 0, 0.5); // 아머 스탠드 위치 조정

            World world = spawnLocation.getWorld();

            ArmorStand armorStand = world.spawn(spawnLocation, ArmorStand.class, stand -> {
                stand.setVisible(false);
                stand.setGravity(false);
                stand.setBasePlate(false);
                stand.setHelmet(new ItemStack(Material.SHULKER_BOX));
            });

            new BukkitRunnable() {
                int count = 20 + random.nextInt(10);
                double radians = 0;

                @Override
                public void run() {
                    if (count-- == 0) {
                        ItemStack finalItem = possibleItems.get(random.nextInt(possibleItems.size()));
                        world.dropItem(spawnLocation.add(0, 1, 0), new ItemStack(finalItem)); // 최종 아이템 드롭 위치 조정
                        world.spawnParticle(Particle.EXPLOSION_LARGE, spawnLocation, 10, 0.5, 0.5, 0.5, 0);
                        world.playSound(spawnLocation, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        armorStand.remove();
                        cancel();
                    } else {
                        radians += Math.PI / 10; // 회전 각도 증가
                        armorStand.setHeadPose(new EulerAngle(0, radians, 0)); // 아머 스탠드의 머리 회전 적용
                        world.playSound(spawnLocation, Sound.BLOCK_NOTE_BLOCK_HAT, 0.5F, 1.0F);
                    }
                }
            }.runTaskTimer(plugin, 0L, 5L); // 0.25초 간격으로 실행

            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().removeItemAnySlot(item);
            }
        }
    }
}
