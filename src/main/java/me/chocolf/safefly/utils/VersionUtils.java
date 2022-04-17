package me.chocolf.safefly.utils;

import me.chocolf.safefly.SafeFly;
import org.bukkit.Bukkit;


public class VersionUtils {

    private static final String v = Bukkit.getServer().getClass().getPackage().getName();
    private static final String nmsVersion =  v.substring(v.lastIndexOf('.') + 1);
    private static final String bukkitVersion = Bukkit.getServer().getBukkitVersion().split("-")[0];
    private static final int versionNumber = Integer.parseInt(nmsVersion.split("_")[1]);

    public static String getNMSVersion() {
        return nmsVersion;
    }

    public static String getBukkitVersion() {
        return bukkitVersion;
    }

    public static int getVersionNumber() {
        return versionNumber;
    }

    public static double getPluginVersion() {
        return Double.parseDouble(SafeFly.getInstance().getDescription().getVersion());
    }

}
