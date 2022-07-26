package me.lifelessnerd.purekitpvp.customitems;

import me.lifelessnerd.purekitpvp.Subcommand;
import me.lifelessnerd.purekitpvp.files.LootTablesConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetCustomItem extends Subcommand {
    @Override
    public String getName() {
        return "getcustomitem";
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Get a custom item to use in a kit";
    }

    @Override
    public String getSyntax() {
        return "/purekitpvp getcustomitem <golden_head/random_chest>";
    }

    @Override
    public boolean perform(Player player, String[] args) {

        if (args[1].equalsIgnoreCase("golden_head")){

            ItemStack goldenHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) goldenHead.getItemMeta();
            meta.displayName(Component.text("Golden Head"));
            String[] loreList = {"Healing Item"};
            meta.setLore(Arrays.asList(loreList));
            meta.setOwningPlayer(Bukkit.getOfflinePlayer("PhantomTupac"));
            goldenHead.setItemMeta(meta);

            player.getInventory().addItem(goldenHead);

            return true;


        } else if (args[1].equalsIgnoreCase("random_chest")){

            if (!(LootTablesConfig.get().isSet(args[2]))){
                player.sendMessage("Such loot table does not exist!");
                return false;
            }
            String lore = LootTablesConfig.get().getString(args[2] + ".desiredLore");
            String displayName = LootTablesConfig.get().getString(args[2] + ".displayName");

            ItemStack chestItem = new ItemStack(Material.CHEST);
            ItemMeta itemMeta = chestItem.getItemMeta();
            List<String> loreList = new ArrayList<>();
            loreList.add(ChatColor.translateAlternateColorCodes('&', lore));
            itemMeta.setLore(loreList);
            itemMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&',displayName)));
//        itemMeta.setCustomModelData(1);
            chestItem.setItemMeta(itemMeta);
            player.getInventory().addItem(chestItem);

            return true;

        }
        return false;
    }
}
