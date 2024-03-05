package com.lio_e28.lottoworld.player;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class PlayerLotto {
    private final HashMap<UUID, ArrayList<HashSet<Integer>>> playerTickets = new HashMap<>();

    public void addTicket(Player player, HashSet<Integer> numbers) {
        ArrayList<HashSet<Integer>> tickets = playerTickets.computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>());
        tickets.add(numbers);
    }

    public ArrayList<HashSet<Integer>> getTickets(Player player) {
        return playerTickets.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }

}
