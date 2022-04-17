package me.chocolf.safefly.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.SafeFlyManager;

public class OnJoinListener implements Listener{
    private SafeFly plugin;

    public OnJoinListener(SafeFly plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        Player p = e.getPlayer();
        if (safeFlyManager.shouldEnableFlight() && safeFlyManager.getPlayersInSafeFly().contains(p.getUniqueId())) {
            p.setAllowFlight(true);
            p.setFlying(true);
        }
    }

}
