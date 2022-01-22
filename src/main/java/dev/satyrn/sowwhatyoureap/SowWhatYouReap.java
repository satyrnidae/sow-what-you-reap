package dev.satyrn.sowwhatyoureap;

import dev.satyrn.sowwhatyoureap.configuration.Configuration;
import dev.satyrn.sowwhatyoureap.events.SowWhatYouReapEvents;
import dev.satyrn.sowwhatyoureap.lang.I18n;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.logging.Level;

/**
 * Manages the lifecycle of the Sow What You Reap plugin.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class SowWhatYouReap extends JavaPlugin {
    // The internationalization provider instance.
    private @Nullable I18n i18n;

    /**
     * Handles the plugin enable lifecycle event.
     *
     * @since 0.0.0-SNAPSHOT
     */
    @Override
    public void onEnable() {
        // Cancel load if Vault is not present.
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            this.getLogger().log(Level.SEVERE, "Failed to acquire Vault plugin. This plugin will be disabled.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Save default config file.
        this.saveDefaultConfig();

        // Initialize configuration handler.
        final Configuration configuration = new Configuration(this);

        // Setup log level.
        if (configuration.debug.value()) {
            this.getLogger().setLevel(Level.ALL);
        }

        // Initialize I18n backend.
        this.i18n = new I18n(this, configuration);
        this.i18n.enable();

        // Initialize permissions provider.
        final @Nullable RegisteredServiceProvider<Permission> registeredServiceProvider = this.getServer().getServicesManager().getRegistration(Permission.class);
        if (registeredServiceProvider == null) {
            this.getLogger().log(Level.SEVERE, "Failed to acquire Vault permission provider. This plugin will be disabled.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        final @NotNull Permission permission = registeredServiceProvider.getProvider();

        // Register events and permissions.
        this.registerEvents(permission, configuration);
        this.registerCommands(permission, configuration);

        // Initialize metrics
        if (configuration.metrics.value()) {
            final Metrics metrics = new Metrics(this, 14022);
            metrics.addCustomChart(new SimplePie("require_hoe", configuration.requireHoe.value()::toString));
            metrics.addCustomChart(new SimplePie("damage_hoe", configuration.damageHoe.value()::toString));
            metrics.addCustomChart(new SimplePie("apply_fortune", configuration.applyFortune.value()::toString));
            metrics.addCustomChart(new SimplePie("require_seeds_to_replant", configuration.requireSeedsToReplant.value()::toString));
            metrics.addCustomChart(new SimplePie("hand_yield", () -> configuration.handYield.value().toString().toLowerCase(Locale.ROOT)));
            metrics.addCustomChart(new SimplePie("locale", configuration.locale::value));
        }
    }

    /**
     * Handles the plugin disable lifecycle event.
     *
     * @since 0.0.0-SNAPSHOT
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (this.getServer().isStopping()) {
            this.getLogger().log(Level.INFO, "Server shutting down. Thanks for using Sow What You Reap!");
        } else {
            this.getLogger().log(Level.WARNING, "Plugin disabled before server shutdown! Either you are using a plugin manager or a dependency failed to load! See log for more details.");
        }

        if (this.i18n != null) {
            this.i18n.disable();
        }
    }

    /**
     * Registers events for the plugin.
     *
     * @param permission The permission manager.
     * @param configuration The configuration instance.
     * @since 0.0.0-SNAPSHOT
     */
    private void registerEvents(final Permission permission, final Configuration configuration) {
        this.getServer().getPluginManager().registerEvents(new SowWhatYouReapEvents(this, permission, configuration), this);
    }

    private void registerCommands(final Permission permission, final Configuration configuration) {

    }
}
