package me.chocolf.safefly.command;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.MessageManager;
import me.chocolf.safefly.manager.SafeFlyManager;


public class SafeFlyCommand implements CommandExecutor{

    private SafeFly plugin;



    public SafeFlyCommand(SafeFly plugin) {
        this.plugin = plugin;
        plugin.getCommand("safefly").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("safefly") || label.equalsIgnoreCase("sfly")) {


            // if command is sent from console and doesnt specify a player
            if (args.length == 0 && !(sender instanceof Player)) {
                sender.sendMessage(MessageManager.applyColour("&cYou must specify a player to toggle SafeFly."));
                return true;
            }
            Player p;
            UUID playersUUID;
            SafeFlyManager safeFlyManager = plugin.getSafeFlyManager();
            MessageManager messageManager = plugin.getMessageManager();

            // if command sent is just /SafeFly
            if (args.length==0) {
                if ( !(sender.hasPermission("SafeFly.use")) ) {
                    sender.sendMessage(messageManager.getMessage("noPermission"));
                    return true;
                }

                p = (Player) sender;
                playersUUID = p.getUniqueId();
            }

            // if command sent is /SafeFly <player>
            else {
                p = plugin.getServer().getPlayer(args[0]);

                // if sender mistyped player name
                if ( p == null ) {
                    sender.sendMessage(messageManager.getMessage("playerNotFound"));
                    return true;
                }

                if (!sender.equals(p) && !(sender.hasPermission("SafeFly.use.others")) ) {
                    sender.sendMessage(messageManager.getMessage("noPermission2"));
                    return true;
                }

                playersUUID = p.getUniqueId();

                // if command sent is /SafeFly <player> <on/off>
                if (args.length>=2) {
                    String onOrOff = args[1];
                    if (onOrOff.equalsIgnoreCase("on")) {
                        if ( !safeFlyManager.getPlayersInSafeFly().contains(playersUUID))
                            safeFlyManager.enableSafeFly(p);
                        return true;
                    }
                    else if (onOrOff.equalsIgnoreCase("off") ) {
                        safeFlyManager.disableSafeFly(p);
                        return true;
                    }
                    return false;
                }
            }

            // Turn SafeFly On
            if ( !safeFlyManager.getPlayersInSafeFly().contains(playersUUID))
                safeFlyManager.enableSafeFly(p);

                // Turn SafeFly Off
            else
                safeFlyManager.disableSafeFly(p);

            return true;
        }
        return false;
    }
}
