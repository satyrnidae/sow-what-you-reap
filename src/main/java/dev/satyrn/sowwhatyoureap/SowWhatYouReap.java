package dev.satyrn.sowwhatyoureap;

import dev.satyrn.sowwhatyoureap.configuration.Configuration;
import dev.satyrn.sowwhatyoureap.lang.I18n;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public final class SowWhatYouReap extends JavaPlugin {
    private @Nullable I18n i18n;

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


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents(final Permission permission) {

    }

    private void registerCommands(final Permission permission) {

    }
}
