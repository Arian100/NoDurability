package me.arian.nodurability.event;

import me.arian.nodurability.NoDurability;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public final class DamageEvent implements Listener {

    /**
     * Prevent {@link ItemStack} form being damaged.
     *
     * @param event {@link PlayerItemDamageEvent}
     */
    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        Material itemMaterial = event.getItem().getType();

        if (NoDurability.get().getExcludedMaterials().contains(itemMaterial.name())) {
            return;
        }

        if (event.getPlayer().hasPermission("nodurability.exclude." + itemMaterial.name().toLowerCase())) {
            return;
        }

        event.setCancelled(true);
    }
}
