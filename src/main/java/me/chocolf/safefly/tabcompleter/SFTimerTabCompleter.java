package me.chocolf.safefly.tabcompleter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.chocolf.safefly.SafeFly;


public class SFTimerTabCompleter implements TabCompleter{

    private SafeFly plugin;

    public SFTimerTabCompleter(SafeFly plugin) {
        this.plugin = plugin;
    }

    List<String> arguments1 = new ArrayList<>();
    List<String> arguments2 = new ArrayList<>();
    List<String> nothing = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if ( arguments1.isEmpty() ) {
            arguments1.add("10");
            arguments1.add("30");
            arguments1.add("60");
            arguments1.add("300");
            arguments1.add("86400");
        }

        for ( Player p : plugin.getServer().getOnlinePlayers()) {
            arguments2.add(p.getName());
        }

        List<String> result1 = new ArrayList<>();
        List<String> result2 = new ArrayList<>();
        if (args.length == 1) {
            for (String a : arguments1){
                if ( a.toLowerCase().startsWith(args[0].toLowerCase()) ){
                    result1.add(a);
                }
            }
            return result1;
        }
        if (args.length == 2) {
            for (String a : arguments2){
                if ( a.toLowerCase().startsWith(args[1].toLowerCase()) ){
                    result2.add(a);
                }
            }
            return result2;


        }
        return nothing;
    }
}
