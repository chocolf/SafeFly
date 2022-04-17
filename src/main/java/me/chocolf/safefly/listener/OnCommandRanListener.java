package me.chocolf.safefly.listener;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.SafeFlyManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnCommandRanListener implements Listener {
    private SafeFly plugin;

    public OnCommandRanListener(SafeFly plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCommandRan(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
        String command = e.getMessage().split(" ")[0].toLowerCase().replace("/","");

        if (safeFlyManager.isInSafeFly(p) && safeFlyManager.getDisabledCommands().contains(command)){
            e.setCancelled(true);
            p.sendMessage(plugin.getMessageManager().getMessage("disabledCommandMessage"));
        }
    }
}
