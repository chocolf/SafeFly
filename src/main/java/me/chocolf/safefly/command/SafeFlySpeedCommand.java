package me.chocolf.safefly.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.chocolf.safefly.SafeFly;
import me.chocolf.safefly.manager.MessageManager;


public class SafeFlySpeedCommand implements CommandExecutor{

    private SafeFly plugin;



    public SafeFlySpeedCommand(SafeFly plugin) {
        this.plugin = plugin;
        plugin.getCommand("safeflyspeed").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("safeflyspeed") || label.equalsIgnoreCase("sflyspeed")) {

            if (args.length == 1 && !(sender instanceof Player)) {
                sender.sendMessage(MessageManager.applyColour("&cYou must specify a player to increase their fly speed."));
                return true;
            }
            MessageManager messageManager = plugin.getMessageManager();
            Player p;

            float flyingSpeed;
            try {
                flyingSpeed = Float.parseFloat(args[0]);
            }
            catch (Exception e) {
                return false;
            }

            if (flyingSpeed > 10 || flyingSpeed < 1) {
                sender.sendMessage(messageManager.getMessage("speedTooHigh"));
                return true;
            }


            // /SafeFlySpeed [Speed]
            if (args.length == 1) {
                if ( !(sender.hasPermission("SafeFly.speed")) ) {
                    sender.sendMessage(messageManager.getMessage("noPermission"));
                    return true;
                }
                p = (Player) sender;

            }
            // /SafeFlySpeed [Speed] [player]
            else{
                if ( !(sender.hasPermission("SafeFly.speed.others")) ) {
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

            // set fly speed
            p.setFlySpeed(flyingSpeed/10);
            p.sendMessage(messageManager.getMessage("changeFlyingSpeed").replace("{speed}", String.valueOf(flyingSpeed)) );
            return true;


        }
        return false;
    }

}
