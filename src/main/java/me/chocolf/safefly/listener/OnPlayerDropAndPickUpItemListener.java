package me.chocolf.safefly.listener;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.SafeFlyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnPlayerDropAndPickUpItemListener implements Listener {
    private SafeFly plugin;

    public OnPlayerDropAndPickUpItemListener(SafeFly plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent e){
        if (!(e.getEntity() instanceof Player))
            return;
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        Player p = (Player) e.getEntity();
        if (!safeFlyManager.shouldDisablePickup() && safeFlyManager.isInSafeFly(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e){
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        Player p = e.getPlayer();
        if (!safeFlyManager.shouldDisableDropItem() && safeFlyManager.isInSafeFly(p)){
            p.sendMessage(plugin.getMessageManager().getMessage("droppedItemMessage"));
            e.setCancelled(true);
        }
    }
}
