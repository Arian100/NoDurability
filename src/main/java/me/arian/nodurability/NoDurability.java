package me.arian.nodurability;

import me.arian.nodurability.command.NoDurabilityCommand;
import me.arian.nodurability.event.CombustEvent;
import me.arian.nodurability.event.DamageEvent;
import org.bukkit.Material;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * NoDurability - Removes durability from the game
 *
 * @author arian
 * @version b1
 */
public final class NoDurability extends JavaPlugin {

    private List<String> excludedMaterials;
    private final PluginManager pm = this.getServer().getPluginManager();
    private static NoDurability instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.initMaterialPermission();

        this.excludedMaterials = this.getConfig().getStringList("exclude");
        this.excludedMaterials.forEach(string -> {
            if (Material.matchMaterial(string) == null) {
                this.getLogger().warning("Invalid material: " + string);
            }
        });
        this.getLogger().info("Loaded " + excludedMaterials.size() + " Materials");

        Objects.requireNonNull(this.getCommand("removedurability")).setExecutor(new NoDurabilityCommand());
        Objects.requireNonNull(this.getCommand("removedurability")).setTabCompleter(new NoDurabilityCommand());

        pm.registerEvents(new DamageEvent(), this);
        pm.registerEvents(new CombustEvent(), this);

        if(pm.getPlugin("PlaceholderAPI") != null) {
            new NoDurabilityPAPIExtension(this);
        }
    }

    /**
     * Gets the instance of the plugin.
     *
     * @return instance
     */
    public static NoDurability get() {
        return instance;
    }

    /**
     * Gets a List of all excluded {@link Material}s.
     *
     * @return excludedMaterials
     */
    public List<String> getExcludedMaterials() {
        return excludedMaterials;
    }

    private void initMaterialPermission() {
        for (Material material : Material.values()) {
            String perm = "nodurability.exclude." + material.name().toLowerCase();

            if (getPluginManager().getPermission(perm) == null) {
                getServer().getPluginManager().addPermission(new Permission(perm));
            }
        }
    }
}
