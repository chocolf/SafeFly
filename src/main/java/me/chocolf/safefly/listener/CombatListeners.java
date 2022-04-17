package me.chocolf.safefly.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.MessageManager;
import me.chocolf.safefly.manager.SafeFlyManager;

public class CombatListeners implements Listener{
    private SafeFly plugin;

    public CombatListeners(SafeFly plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerAttackPlayer(EntityDamageByEntityEvent e) {
        // PvP
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player whoHit = (Player) e.getDamager();
            Player whoWasHit = (Player) e.getEntity();
            SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
            MessageManager messageManager = plugin.getMessageManager();

            if (safeFlyManager.shouldDisablePvP()) {
                if (safeFlyManager.getPlayersInSafeFly().contains(whoHit.getUniqueId())) {
                    whoHit.sendMessage(messageManager.getMessage("cantDamagePlayer"));
                    e.setCancelled(true);
                }
                else if (safeFlyManager.getPlayersInSafeFly().contains(whoWasHit.getUniqueId())) {
                    whoHit.sendMessage(messageManager.getMessage("cantDamagePlayer2"));
                    e.setCancelled(true);
                }
            }
            else if (safeFlyManager.shouldDisableOnPvP()) {
                safeFlyManager.disableSafeFly(whoHit);
                safeFlyManager.disableSafeFly(whoWasHit);
            }
        }
    }

    @EventHandler
    public void onPlayerAttackMob(EntityDamageByEntityEvent e) {
        // PvE
        if (e.getDamager() instanceof Player && !(e.getEntity() instanceof Player)) {

            Player whoHit = (Player) e.getDamager();
            SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
            MessageManager messageManager = plugin.getMessageManager();

            if ( safeFlyManager.shouldDisablePvE() && safeFlyManager.getPlayersInSafeFly().contains(whoHit.getUniqueId())) {

                if (e.getEntity() instanceof ItemFrame) return;

                whoHit.sendMessage(messageManager.getMessage("cantDamageEntity"));
                e.setCancelled(true);
            }
            else if ( safeFlyManager.shouldDisableOnPvE() )
                safeFlyManager.disableSafeFly(whoHit);
        }
    }

    @EventHandler
    public void onMobAttackPlayer(EntityDamageByEntityEvent e) {
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();

        if ( safeFlyManager.shouldDisableOnPvE() && !(e.getDamager() instanceof Player) && e.getEntity() instanceof Player)
            safeFlyManager.disableSafeFly((Player) e.getEntity());
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        if (safeFlyManager.shouldDisableMobTargeting() && e.getTarget() instanceof Player) {
            Player p = (Player) e.getTarget();
            if (safeFlyManager.getPlayersInSafeFly().contains(p.getUniqueId()))
                e.setCancelled(true);
        }
    }
}
