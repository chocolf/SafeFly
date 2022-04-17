package me.chocolf.safefly.manager;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.chocolf.safefly.utils.VersionUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import me.chocolf.safefly.SafeFly;

public class MessageManager {

    private SafeFly plugin;
    private HashMap<String, String> messagesMap = new HashMap<>();
    private static final Pattern pattern = Pattern.compile("#([A-Fa-f0-9]){6}");

    public MessageManager(SafeFly plugin) {
        this.plugin = plugin;
        init();
    }

    public void init() {
        FileConfiguration config = plugin.getConfig();
        messagesMap.clear();
        messagesMap.put("safeFlyEnabled", MessageManager.applyColour(config.getString("SafeFlyEnabledMessage")));
        messagesMap.put("safeFlyDisabled", MessageManager.applyColour(config.getString("SafeFlyDisabledMessage")));
        messagesMap.put("cantDamagePlayer", MessageManager.applyColour(config.getString("CantDamagePlayerMessage")));
        messagesMap.put("cantDamagePlayer2", MessageManager.applyColour(config.getString("CantDamagePlayerMessage2")));
        messagesMap.put("cantDamageEntity", MessageManager.applyColour(config.getString("CantDamageEntityMessage")));
        messagesMap.put("cantBreakBlock", MessageManager.applyColour(config.getString("CantBreakBlockMessage")));
        messagesMap.put("cantPlaceBlock", MessageManager.applyColour(config.getString("CantPlaceBlockMessage")));
        messagesMap.put("cantUseItem", MessageManager.applyColour(config.getString("CantUseItemMessage")));
        messagesMap.put("cantInteractWithBlock", MessageManager.applyColour(config.getString("CantInteractWithBlockMessage")));
        messagesMap.put("playerNotFound", MessageManager.applyColour(config.getString("PlayerNotFoundMessage")));
        messagesMap.put("noPermission", MessageManager.applyColour(config.getString("NoPermissionMessage")));
        messagesMap.put("noPermission2", MessageManager.applyColour(config.getString("NoPermissionMessage2")));
        messagesMap.put("changeFlyingSpeed", MessageManager.applyColour(config.getString("ChangeFlyingSpeedMessage")));
        messagesMap.put("speedTooHigh", MessageManager.applyColour(config.getString("SpeedTooHighMessage")));
        messagesMap.put("tenSecondWarning", MessageManager.applyColour(config.getString("TenSecondWarningMessage")));
        messagesMap.put("worldIsDisabled", MessageManager.applyColour(config.getString("WorldIsDisabledMessage")));
        messagesMap.put("droppedItemMessage", MessageManager.applyColour(config.getString("DroppedItemMessage")));
        messagesMap.put("disabledCommandMessage", MessageManager.applyColour(config.getString("DisabledCommandMessage")));
    }

    public String getMessage(String messageName) {
        return messagesMap.get(messageName);
    }

    public static String applyColour (String msg) {
        if ( VersionUtils.getVersionNumber() > 15) {
            Matcher match = pattern.matcher(msg);
            while (match.find()) {
                String color = msg.substring(match.start(), match.end());
                msg = msg.replace(color, ChatColor.of(color) + "");
                match = pattern.matcher(msg);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
