package me.lifelessnerd.purekitpvp.customitems.commands;

import me.lifelessnerd.purekitpvp.Subcommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class GetGoldenHead extends Subcommand {
    @Override
    public String getName() {
        return "golden_head";
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Put the golden head special item in your inventory to use in kits.";
    }

    @Override
    public String getSyntax() {
        return "/getitem golden_head";
    }

    @Override
    public boolean perform(Player player, String[] args) {
//        System.out.println("dab");
//        ItemStack goldenHead = new ItemStack(Material.PLAYER_HEAD);
//        SkullMeta meta = (SkullMeta) goldenHead.getItemMeta();
//        meta.displayName(Component.text("Golden Head"));
//        String[] loreList = {"Healing Item"};
//        meta.setLore(Arrays.asList(loreList));
//        meta.setOwningPlayer(Bukkit.getOfflinePlayer("PhantomTupac"));
//        goldenHead.setItemMeta(meta);
//
//        player.getInventory().addItem(goldenHead);

        return true;
    }
}
