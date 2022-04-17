package me.chocolf.safefly.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.SafeFlyManager;


public class StatusChangeListeners implements Listener{
    private SafeFly plugin;

    public StatusChangeListeners(SafeFly plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent e) {
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        if (safeFlyManager.shouldLoseHunger())
            return;

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            int oldFoodLevel = p.getFoodLevel();
            int newFoodLevel = e.getFoodLevel();

            if ( oldFoodLevel > newFoodLevel && safeFlyManager.getPlayersInSafeFly().contains(p.getUniqueId()) )
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHealthChange(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
            if (safeFlyManager.shouldLoseHealth())
                return;
            if (safeFlyManager.getPlayersInSafeFly().contains(e.getEntity().getUniqueId())) {
                e.getEntity().setFireTicks(0);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPotionEffectApply(EntityPotionEffectEvent e) {
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        if (e.getEntity() instanceof Player && safeFlyManager.shouldDisablePotionEffects() && safeFlyManager.getPlayersInSafeFly().contains(e.getEntity().getUniqueId()))
            e.setCancelled(true);
    }
}
