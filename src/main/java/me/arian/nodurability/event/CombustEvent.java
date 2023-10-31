package me.arian.nodurability.event;

import me.arian.nodurability.NoDurability;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.inventory.ItemStack;

public final class CombustEvent implements Listener {

    /**
     * Prevent {@link ItemStack} form combusting.
     *
     * @param event {@link EntityCombustEvent}
     */
    @EventHandler
    public void onCombust(EntityCombustEvent event) {
        if (!NoDurability.get().getConfig().getBoolean("combust-items")) {
            event.setCancelled(true);
        }
    }
}
