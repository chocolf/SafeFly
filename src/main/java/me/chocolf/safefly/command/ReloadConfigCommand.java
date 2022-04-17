package me.chocolf.safefly.command;

import me.chocolf.safefly.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.chocolf.safefly.SafeFly;

public class ReloadConfigCommand implements CommandExecutor{

    private SafeFly plugin;


    public ReloadConfigCommand(SafeFly plugin) {
        this.plugin = plugin;
        plugin.getCommand("safeflyreload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("SafeFly.reload")) {
            plugin.reloadConfig();
            plugin.getSafeFlyManager().init();
            plugin.getMessageManager().init();
            sender.sendMessage(MessageManager.applyColour("&9The SafeFly Config was reloaded!"));
        }
        else
            sender.sendMessage(plugin.getMessageManager().getMessage("noPermission"));

        return true;
    }

}
