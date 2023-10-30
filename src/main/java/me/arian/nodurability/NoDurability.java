package me.arian.nodurability;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * NoDurability - Removes durability from the game
 *
 * @author arian
 * @version b1
 */
@SuppressWarnings("deprectaion")
public final class NoDurability extends JavaPlugin implements Listener {

    private List<String> excludedMaterials;
    private final PluginManager pm = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.excludedMaterials = this.getConfig().getStringList("exclude");
        this.excludedMaterials.forEach(string -> {
            if (Material.matchMaterial(string) == null) {
                this.getLogger().warning("Invalid material: " + string);
            }
        });
        this.getLogger().info("Loaded " + excludedMaterials.size() + " Materials");

        Objects.requireNonNull(this.getCommand("removedurability")).setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player player) {
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null) {
                        if (item instanceof Damageable damageable) {
                            damageable.setDamage(0);
                        }
                        Objects.requireNonNull(item.getItemMeta()).setUnbreakable(true);
                    }
                }

                sender.sendMessage(this.getConfig().getString("lang.reset-durability-message"));
            } else {
                sender.sendMessage(this.getConfig().getString("lang.only-a-player"));
            }
            return true;
        });

        Objects.requireNonNull(this.getCommand("removedurability")).setTabCompleter((sender, command, alias, args) -> Collections.emptyList());

        pm.registerEvents(this, this);

        if(pm.getPlugin("PlaceholderAPI") != null) {
            new NoDurabilityPAPIExtension(this);
        }
    }

    /**
     * Prevent {@link ItemStack} form being damaged.
     *
     * @param event {@link PlayerItemDamageEvent}
     */
    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        Material itemMaterial = event.getItem().getType();

        if (this.excludedMaterials.contains(itemMaterial.name())) {
            return;
        }

        event.setCancelled(true);
    }

    /**
     * Prevent {@link ItemStack} form combusting.
     *
     * @param event {@link EntityCombustEvent}
     */
    @EventHandler
    public void onCombust(EntityCombustEvent event) {
        if (!this.getConfig().getBoolean("combust-items")) {
            event.setCancelled(true);
        }
    }
}
