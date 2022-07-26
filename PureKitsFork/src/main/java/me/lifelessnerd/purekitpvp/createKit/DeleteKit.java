package me.lifelessnerd.purekitpvp.createKit;

import me.lifelessnerd.purekitpvp.Subcommand;
import me.lifelessnerd.purekitpvp.files.KitConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeleteKit extends Subcommand {
    Plugin plugin;

    public DeleteKit(Plugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public String getName() {
        return "deletekit";
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Remove a kit";
    }

    @Override
    public String getSyntax() {
        return "/purekitpvp deletekit <kitName>";
    }

    @Override
    public boolean perform(Player player, String[] args) {

        if (!player.hasPermission("purekitpvp.admin.deletekit")){
            player.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }

        if (!(args.length >= 2)){
            player.sendMessage(ChatColor.RED + "Please provide arguments!");
            return false;
        }

        String kitName = args[1].toLowerCase();
        kitName = kitName.substring(0, 1).toUpperCase() + kitName.substring(1);

        if(KitConfig.get().get("kits." + kitName) == null){

            player.sendMessage(ChatColor.GRAY + "That kit does not exist.");
            return true;
        }

        KitConfig.get().getConfigurationSection("kits").set(kitName, null);
        KitConfig.save();
        KitConfig.reload();
        player.sendMessage("You removed kit " + ChatColor.BLUE + kitName);


        return true;
    }
}
