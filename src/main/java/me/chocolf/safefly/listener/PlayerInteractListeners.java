package me.chocolf.safefly.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.SafeFlyManager;

public class PlayerInteractListeners implements Listener{
    private SafeFly plugin;

    public PlayerInteractListeners(SafeFly plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        Player p = e.getPlayer();
        if ( !safeFlyManager.getPlayersInSafeFly().contains(p.getUniqueId()) ) return;


        if ( e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK )
            return;
        if (e.getItem()==null)
            return;

        if (safeFlyManager.getDisabledItems().contains(e.getItem().getType().toString())) {
            if (safeFlyManager.shouldDisableOnUseDisabledItem())
                safeFlyManager.disableSafeFly(p);
            else {
                e.setCancelled(true);
                p.sendMessage(plugin.getMessageManager().getMessage("cantUseItem"));
            }
        }
    }

    @EventHandler
    public void onPlayerInteractWithBlock(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) return;
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        Player p = e.getPlayer();
        if ( !safeFlyManager.getPlayersInSafeFly().contains(p.getUniqueId()) ) return;
        if (e.getClickedBlock() == null) return;

        if (safeFlyManager.getDisabledInteractables().contains(e.getClickedBlock().getType().toString())) {
            if (safeFlyManager.shouldDisableOnInteractWithDisabledInteractable())
                safeFlyManager.disableSafeFly(p);
            else {
                e.setCancelled(true);
                p.sendMessage(plugin.getMessageManager().getMessage("cantInteractWithBlock"));
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        Player p = e.getPlayer();
        if (safeFlyManager.shouldDisableBlockBreaking()) {
            if (safeFlyManager.getPlayersInSafeFly().contains(p.getUniqueId())) {
                p.sendMessage(plugin.getMessageManager().getMessage("cantBreakBlock"));
                e.setCancelled(true);
            }
        }
        else if (safeFlyManager.shouldDisableOnBreakBlock())
            safeFlyManager.disableSafeFly(p);

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        Player p = e.getPlayer();
        if (safeFlyManager.shouldDisableBlockPlacing()) {
            if (safeFlyManager.getPlayersInSafeFly().contains(p.getUniqueId())) {
                p.sendMessage(plugin.getMessageManager().getMessage("cantPlaceBlock"));
                e.setCancelled(true);
            }
        }
        else if (safeFlyManager.shouldDisableOnPlaceBlock())
            safeFlyManager.disableSafeFly(p);
    }
}
