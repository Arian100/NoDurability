package me.arian.nodurability.command;

import me.arian.nodurability.NoDurability;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class NoDurabilityCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                final Material m = Material.matchMaterial(args[0]);
                for (ItemStack i : player.getInventory().getContents()) {
                    if (i.getType().equals(m) && i instanceof Damageable damageable) {
                        damageable.setDamage(0);
                        i.getItemMeta().setUnbreakable(true);
                        sender.sendMessage(NoDurability.get().getConfig().getString("lang.reset-durability-item"));
                        return true;
                    }
                }
            }

            for (ItemStack item : player.getInventory().getContents()) {
                if (item instanceof Damageable damageable) {
                    damageable.setDamage(0);
                    item.getItemMeta().setUnbreakable(true);
                    sender.sendMessage(NoDurability.get().getConfig().getString("lang.reset-durability-message"));
                    return true;
                }
            }
        } else {
            sender.sendMessage(NoDurability.get().getConfig().getString("lang.only-a-player"));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> materials = new ArrayList<>();
            for (Material material : Material.values()) {
                materials.add(material.name());
            }
            return materials;
        }
        return Collections.emptyList();
    }
}
