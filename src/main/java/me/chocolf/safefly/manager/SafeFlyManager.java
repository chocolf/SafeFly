package me.chocolf.safefly.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import me.chocolf.safefly.SafeFly;

public class SafeFlyManager {

    private SafeFly plugin;
    private HashSet<UUID> playersInSafeFly = new HashSet<>();
    private HashSet<String> disabledItems = new HashSet<>();
    private HashSet<String> disabledWorlds = new HashSet<>();
    private HashSet<String> disabledInteractables = new HashSet<>();
    private boolean flight;
    private boolean disablePotionEffects;
    private boolean disablePvP;
    private boolean disablePvE;
    private boolean disableMobTargeting;
    private boolean disableBlockBreaking;
    private boolean disableBlockPlacing;
    private boolean loseHunger;
    private boolean loseHealth;
    private boolean disableOnWorldChange;
    private boolean disableOnTeleport;
    private boolean disableOnPvP;
    private boolean disableOnPvE;
    private boolean disableOnUseDisabledItem;
    private boolean disableOnInteractWithDisabledInteractable;
    private boolean disableOnPlaceBlock;
    private boolean disableOnBreakBlock;

    public SafeFlyManager(SafeFly plugin) {
        this.plugin = plugin;
        init();
    }

    public void init() {
        FileConfiguration config = plugin.getConfig();

        flight = config.getBoolean("Flight");
        disablePotionEffects = config.getBoolean("DisablePotionEffects");
        disablePvP = config.getBoolean("DisablePvP");
        disablePvE = config.getBoolean("DisablePvE");
        disableMobTargeting = config.getBoolean("DisableMobTargeting");
        disableBlockBreaking = config.getBoolean("DisableBlockBreaking");
        disableBlockPlacing = config.getBoolean("DisableBlockPlacing");
        loseHunger = config.getBoolean("LoseHunger");
        loseHealth = config.getBoolean("LoseHealth");
        disableOnWorldChange = config.getBoolean("DisableOnWorldChange");
        disableOnTeleport = config.getBoolean("DisableOnTeleport");
        disableOnPvP = config.getBoolean("DisableOnPvP");
        disableOnPvE = config.getBoolean("DisableOnPvE");
        disableOnUseDisabledItem = config.getBoolean("DisableOnUseDisabledItem");
        disableOnInteractWithDisabledInteractable = config.getBoolean("DisableOnInteractWithDisabledInteractable");
        disableOnPlaceBlock = config.getBoolean("DisableOnPlaceBlock");
        disableOnBreakBlock = config.getBoolean("DisableOnBreakBlock");


        disabledWorlds.clear();
        disabledItems.clear();
        disabledInteractables.clear();

        List<String> disabledWorldsInConfig = config.getStringList("DisabledWorlds");
        for (String world : disabledWorldsInConfig)
            disabledWorlds.add(world);

        List<String> disabledItemsInConfig = config.getStringList("DisabledItems");
        for (String item : disabledItemsInConfig)
            disabledItems.add(item);

        List<String> disabledInteractablesInConfig = config.getStringList("DisabledInteractables");
        for (String interactable : disabledInteractablesInConfig)
            disabledInteractables.add(interactable);
    }



    public void enableSafeFly(Player p) {
        MessageManager messageManager = plugin.getMessageManager();

        if ( disabledWorlds.contains( p.getWorld().getName() ) ) {
            p.sendMessage(messageManager.getMessage("worldIsDisabled"));
            return;
        }

        // remove potion effects
        if (shouldDisablePotionEffects())
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());

        // Enable flight
        if (shouldEnableFlight()) {
            p.setAllowFlight(true);
            p.setFlying(true);
        }

        // Disable Mob Targeting
        if (shouldDisableMobTargeting()) {
            for ( Entity entity : p.getNearbyEntities(30, 30, 30)) {
                if (entity instanceof Creature)
                    ((Creature) entity).setTarget(null);
            }
        }

        playersInSafeFly.add(p.getUniqueId());
        p.sendMessage(messageManager.getMessage("safeFlyEnabled"));
    }

    public void disableSafeFly(Player p) {
        UUID playersUUID = p.getUniqueId();
        if (!getPlayersInSafeFly().contains(playersUUID)) return;

        MessageManager messageManager = plugin.getMessageManager();
        // Disable flight
        if (shouldEnableFlight() && p.getGameMode() != GameMode.CREATIVE) {
            p.setFlying(false);
            p.setAllowFlight(false);
        }

        playersInSafeFly.remove(p.getUniqueId());
        p.sendMessage(messageManager.getMessage("safeFlyDisabled"));
    }

    public void disableSafeFlyLater(Player p, int time) {
        (new BukkitRunnable() {
            public void run() {
                disableSafeFly(p);
            }
        }).runTaskLater(this.plugin,  (long) time * 20);
    }

    public void tenSecondWarning(Player p, int time) {
        (new BukkitRunnable() {
            public void run() {
                if ( playersInSafeFly.contains(p.getUniqueId()) )
                    p.sendMessage(plugin.getMessageManager().getMessage("tenSecondWarning"));
            }
        }).runTaskLater(this.plugin,  (long) time * 20);
    }


    public Set<UUID> getPlayersInSafeFly() {
        return playersInSafeFly;
    }

    public Set<String> getDisabledItems() {
        return disabledItems;
    }

    public Set<String> getDisabledWorlds() {
        return disabledWorlds;
    }

    public Set<String> getDisabledInteractables() {
        return disabledInteractables;
    }

    public boolean shouldEnableFlight() {
        return flight;
    }

    public boolean shouldDisablePotionEffects() {
        return disablePotionEffects;
    }

    public boolean shouldDisablePvP() {
        return disablePvP;
    }

    public boolean shouldDisablePvE() {
        return disablePvE;
    }

    public boolean shouldDisableMobTargeting() {
        return disableMobTargeting;
    }

    public boolean shouldDisableBlockBreaking() {
        return disableBlockBreaking;
    }

    public boolean shouldDisableBlockPlacing() {
        return disableBlockPlacing;
    }

    public boolean shouldLoseHunger() {
        return loseHunger;
    }

    public boolean shouldLoseHealth() {
        return loseHealth;
    }

    public boolean shouldDisableOnWorldChange() {
        return disableOnWorldChange;
    }

    public boolean shouldDisableOnTeleport() {
        return disableOnTeleport;
    }

    public boolean shouldDisableOnPvP() {
        return disableOnPvP;
    }

    public boolean shouldDisableOnPvE() {
        return disableOnPvE;
    }

    public boolean shouldDisableOnUseDisabledItem() {
        return disableOnUseDisabledItem;
    }

    public boolean shouldDisableOnInteractWithDisabledInteractable() {
        return disableOnInteractWithDisabledInteractable;
    }

    public boolean shouldDisableOnPlaceBlock() {
        return disableOnPlaceBlock;
    }

    public boolean shouldDisableOnBreakBlock() {
        return disableOnBreakBlock;
    }

}
