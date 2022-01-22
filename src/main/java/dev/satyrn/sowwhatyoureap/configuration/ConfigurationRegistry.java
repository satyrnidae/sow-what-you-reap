package dev.satyrn.sowwhatyoureap.configuration;

import dev.satyrn.papermc.api.configuration.v4.ConfigurationConsumer;
import dev.satyrn.papermc.api.configuration.v4.ConfigurationConsumerRegistry;
import dev.satyrn.sowwhatyoureap.SowWhatYouReap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class ConfigurationRegistry extends ConfigurationConsumerRegistry<Configuration> {
    private static ConfigurationRegistry instance;

    private final transient @NotNull Plugin plugin;

    private ConfigurationRegistry(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    private static ConfigurationRegistry getInstance() {
        if (instance == null) {
            instance = new ConfigurationRegistry(JavaPlugin.getPlugin(SowWhatYouReap.class));
        }
        return instance;
    }

    public static void registerConsumer(ConfigurationConsumer<Configuration> instance) {
        getInstance().plugin.getLogger().log(Level.FINE, String.format("Registered configuration consumer class %s", instance.getClass().getName()));
        getInstance().register(instance);
    }

    public static void reloadConfiguration(Configuration configuration) {
        getInstance().plugin.getLogger().log(Level.FINE, "Reloading configuration...");
        getInstance().reload(configuration);
    }
}
