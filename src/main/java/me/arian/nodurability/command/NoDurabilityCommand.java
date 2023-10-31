package me.arian.nodurability.command;

import me.arian.nodurability.NoDurability;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class NoDurabilityCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null) {
                    if (item instanceof Damageable damageable) {
                        damageable.setDamage(0);
                        return true;
                    }
                    Objects.requireNonNull(item.getItemMeta()).setUnbreakable(true);
                    return true;
                }
            }

            sender.sendMessage(NoDurability.get().getConfig().getString("lang.reset-durability-message"));
        } else {
            sender.sendMessage(NoDurability.get().getConfig().getString("lang.only-a-player"));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
