package me.chocolf.safefly.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.SafeFlyManager;

public class TeleportListeners implements Listener{
    private SafeFly plugin;

    public TeleportListeners(SafeFly plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        if ( safeFlyManager.shouldDisableOnWorldChange() || safeFlyManager.getDisabledWorlds().contains(p.getWorld().getName()) )
            safeFlyManager.disableSafeFly(p);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();

        if (safeFlyManager.shouldDisableOnTeleport())
            safeFlyManager.disableSafeFly(e.getPlayer());
    }
}
