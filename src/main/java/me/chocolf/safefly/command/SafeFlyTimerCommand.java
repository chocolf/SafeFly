package me.chocolf.safefly.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.MessageManager;
import me.chocolf.safefly.manager.SafeFlyManager;

public class SafeFlyTimerCommand implements CommandExecutor{

    private SafeFly plugin;



    public SafeFlyTimerCommand(SafeFly plugin) {
        this.plugin = plugin;
        plugin.getCommand("safeflytimer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("safeflytimer") || label.equalsIgnoreCase("sflytimer")) {

            // if command is sent from console and doesnt specify a player
            if (args.length == 0 && !(sender instanceof Player)) {
                sender.sendMessage(MessageManager.applyColour("&cYou must specify a player to toggle SafeFly."));
                return true;
            }
            MessageManager messageManager = plugin.getMessageManager();

            // if command sent is just /SafeFlyTimer
            if (args.length==0) {
                if ( !(sender.hasPermission("SafeFly.timer")) ) {
                    sender.sendMessage(messageManager.getMessage("noPermission"));
                    return true;
                }
                return false;
            }

            int time;
            try {
                time = Integer.parseInt(args[0]);
            }
            catch (Exception e) {
                return false;
            }

            Player p;
            // if command sent is /SafeFlyTimer 10
            if (args.length==1) {
                if ( !(sender.hasPermission("SafeFly.timer")) ) {
                    sender.sendMessage(messageManager.getMessage("noPermission"));
                    return true;
                }
                p = (Player) sender;


            }
            // if command sent is /SafeFlyTimer 10 PLAYER
            else {
                if ( !(sender.hasPermission("SafeFly.timer.others")) ) {
                    sender.sendMessage(messageManager.getMessage("noPermission2"));
                    return true;
                }
                p = plugin.getServer().getPlayer(args[1]);

                // if sender mistyped player name
                if ( p == null) {
                    sender.sendMessage(messageManager.getMessage("playerNotFound"));
                    return true;
                }
            }
            SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
            if ( !safeFlyManager.getPlayersInSafeFly().contains(p.getUniqueId())) {
                safeFlyManager.enableSafeFly(p);
            }
            safeFlyManager.disableSafeFlyLater(p,time);
            safeFlyManager.tenSecondWarning(p,time-10);
        }


        return true;
    }





}