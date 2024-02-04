package me.arian.nodurability;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NoDurabilityPAPIExtension extends PlaceholderExpansion {

    private final NoDurability plugin;

    public NoDurabilityPAPIExtension(NoDurability plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "Arian01";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "nd";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("durability")) {
            if (player != null && player.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof Damageable damageable) {
                return String.valueOf(damageable.getDamage());
            } else if (player != null && !(player.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof Damageable)) {
                return plugin.getConfig().getString("lang.papi.no-durability");
            } else { return null; }
        }

        return null;
    }
}
