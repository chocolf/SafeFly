package me.chocolf.safefly;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.plugin.java.JavaPlugin;

import me.chocolf.safefly.command.ReloadConfigCommand;
import me.chocolf.safefly.command.SafeFlyCommand;
import me.chocolf.safefly.command.SafeFlySpeedCommand;
import me.chocolf.safefly.command.SafeFlyTimerCommand;
import me.chocolf.safefly.listener.CombatListeners;
import me.chocolf.safefly.listener.OnJoinListener;
import me.chocolf.safefly.listener.PlayerInteractListeners;
import me.chocolf.safefly.listener.StatusChangeListeners;
import me.chocolf.safefly.listener.TeleportListeners;
import me.chocolf.safefly.manager.MessageManager;
import me.chocolf.safefly.manager.SafeFlyManager;
import me.chocolf.safefly.tabcompleter.SFSpeedTabCompleter;
import me.chocolf.safefly.tabcompleter.SFTabCompleter;
import me.chocolf.safefly.tabcompleter.SFTimerTabCompleter;
import me.chocolf.safefly.utils.ConfigUpdater;
import me.chocolf.safefly.utils.Metrics;

public class SafeFly extends JavaPlugin{

    private SafeFlyManager safeFlyManager;
    private MessageManager messageManager;
    private static SafeFly instance;

    @Override
    public void onEnable() {

        instance = this;

        // bstats
        new Metrics(this, 9584); // 9584 is this plugins id

        // save config and update it
        saveDefaultConfig();
        try {
            ConfigUpdater.update(this, "config.yml", new File(getDataFolder(), "config.yml"), Arrays.asList());//The list is sections you want to ignore
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();

        // listeners
        new CombatListeners(this);
        new OnJoinListener(this);
        new PlayerInteractListeners(this);
        new StatusChangeListeners(this);
        new TeleportListeners(this);

        // commands
        new SafeFlyCommand(this);
        new SafeFlySpeedCommand(this);
        new SafeFlyTimerCommand(this);
        new ReloadConfigCommand(this);
        getCommand("safefly").setTabCompleter(new SFTabCompleter(this));
        getCommand("safeflyspeed").setTabCompleter(new SFSpeedTabCompleter(this));
        getCommand("safeflytimer").setTabCompleter(new SFTimerTabCompleter(this));

        // managers
        safeFlyManager = new SafeFlyManager(this);
        messageManager = new MessageManager(this);
    }

    public SafeFlyManager getSafeFlyManager() {
        return safeFlyManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public static SafeFly getInstance(){
        return instance;
    }
}