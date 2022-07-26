package me.lifelessnerd.purekitpvp.perks.perkCommand;

import me.lifelessnerd.purekitpvp.files.KitConfig;
import me.lifelessnerd.purekitpvp.files.PerkData;
import me.lifelessnerd.purekitpvp.perks.perkfirehandler.PerkLib;
import me.lifelessnerd.purekitpvp.utils.ComponentUtils;
import me.lifelessnerd.purekitpvp.utils.MyStringUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PerkCommand implements CommandExecutor {
    Plugin plugin;

    public PerkCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;

        if (!(player.getWorld().getName().equalsIgnoreCase(plugin.getConfig().getString("world")))){
            player.sendMessage(ChatColor.RED + "You can only use this menu in " + ChatColor.GRAY + plugin.getConfig().getString("world"));
            return true;
        }
        //Create inventory GUI
        TextComponent invTitle = Component.text("Perks Menu").color(TextColor.color(255, 150, 20));
        Inventory perksInventory = Bukkit.createInventory(null, 54, invTitle);

        //Info item
        ItemStack infoItem = new ItemStack(Material.BOOK);
        TextComponent infoItemName = Component.text("Perks Info").color(TextColor.color(233, 67, 47));
        ItemMeta infoMeta = infoItem.getItemMeta();
        infoMeta.displayName(infoItemName);
        infoMeta = ComponentUtils.setLore(infoMeta, "&aYou can equip a total of &e5 &aperks total.", 0);
        infoMeta = ComponentUtils.setLore(infoMeta, "&7Click on a perk slot to choose a perk for that slot.", 1);
        infoMeta = ComponentUtils.setLore(infoMeta, "&7It will replace any perk currently in that slot.", 2);
        infoMeta = ComponentUtils.setLore(infoMeta, "&7Perks are abilities that are always active.", 3);
        infoMeta = ComponentUtils.setLore(infoMeta, "&7Duplicate perks do not stack.", 4);
        infoItem.setItemMeta(infoMeta);
        perksInventory.setItem(13, infoItem);


        //Fill perk slots with either empty or actual perk icon
        for (int index = 20; index <= 24; index++){
            int slot = index - 19;
            String fileContent = PerkData.get().getString(player.getName() + ".slot" + slot);
            //System.out.println(fileContent);
            if (fileContent == null){
                // No perk selected in this slot; show red stained glass
                ItemStack perkSlot = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                TextComponent perkSlotName = Component.text("Perk Slot " + slot).color(TextColor.color(233, 67, 47));
                ItemMeta perkSlotMeta = perkSlot.getItemMeta();
                perkSlotMeta.displayName(perkSlotName);
                perkSlotMeta = ComponentUtils.setLore(perkSlotMeta, "&aClick to select a perk for this slot!", 0);
                perkSlot.setItemMeta(perkSlotMeta);
                perksInventory.setItem(index, perkSlot);
            } else {
                PerkLib perkLib = new PerkLib();
                ItemStack perkSlot = new ItemStack(perkLib.perkIcons.get(fileContent));
                TextComponent perkSlotName = Component.text("Perk Slot " + slot).color(TextColor.color(233, 67, 47));
                ItemMeta perkSlotMeta = perkSlot.getItemMeta();
                perkSlotMeta.displayName(perkSlotName);

                //Component style - not my own class util (okay, only the decoder)
                ArrayList<Component> loreTBA = new ArrayList<>();
                TextComponent loreTitle = Component.text(fileContent).color(TextColor.color(0, 230, 0));
                loreTBA.add(loreTitle);
                if (perkLib.perks.get(fileContent).contains("\n")) {
                    String[] decodedLore = MyStringUtils.perkLoreDecoder(perkLib.perks.get(fileContent));
                    for (String line : decodedLore) {
                        loreTBA.add(Component.text(line).color(TextColor.color(150, 150, 150)));
                    }
                } else {
                    loreTBA.add(Component.text(perkLib.perks.get(fileContent)).color(TextColor.color(150, 150, 150)));
                }
                perkSlotMeta.lore(loreTBA);
                perkSlotMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                perkSlotMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                perkSlot.setItemMeta(perkSlotMeta);
                perksInventory.setItem(index, perkSlot);


            }
        }

        player.openInventory(perksInventory);
        return true;
    }
}
