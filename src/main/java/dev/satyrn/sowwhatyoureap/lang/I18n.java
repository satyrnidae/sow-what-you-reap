package dev.satyrn.sowwhatyoureap.lang;

import dev.satyrn.papermc.api.configuration.v4.ConfigurationConsumer;
import dev.satyrn.sowwhatyoureap.configuration.Configuration;
import dev.satyrn.sowwhatyoureap.configuration.ConfigurationRegistry;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Message internationalization for the plugin
 *
 * @author Isabel Maskrey
 * @since 1.0-SNAPSHOT
 */
public final class I18n extends dev.satyrn.papermc.api.lang.v1.I18n implements ConfigurationConsumer<Configuration> {
    // The base name for all locales without a language name.
    private static final String BASE_NAME = "sowwhatyoureap";
    // The internationalization instance.
    private static I18n instance;

    /**
     * Initializes a new I18n instance.
     *
     * @param plugin The parent plugin instance.
     */
    public I18n(final Plugin plugin, Configuration configuration) {
        super(plugin, ResourceBundle.getBundle(BASE_NAME, DEFAULT_LOCALE, new Utf8LangFileControl()));
        this.reloadConfiguration(configuration);
        ConfigurationRegistry.registerConsumer(this);
    }

    /**
     * Reloads the configuration instance.
     * @param configuration The configuration instance.
     */
    @Override
    public void reloadConfiguration(@NotNull Configuration configuration) {
        this.setLocale(configuration.locale.value());
    }

    /**
     * Translates a resource string to the current locale.
     *
     * @param key    The translation key.
     * @param format The translation format.
     * @return The translated message.
     */
    public static @NotNull String tr(@NotNull final String key, @NotNull final Object... format) {
        return instance.translate(key, format);
    }

    /**
     * Gets a resource bundle for the given locale.
     * @param locale The current locale
     * @return The resource bundle for the current locale.
     */
    @Override
    protected @NotNull ResourceBundle getResourceBundleForLocale(@NotNull Locale locale) {
        return ResourceBundle.getBundle(BASE_NAME, locale, new Utf8LangFileControl());
    }

    /**
     * Enables the i18n handler.
     */
    public void enable() {
        instance = this;
    }

    /**
     * Disables the i18n handler.
     */
    public void disable() {
        instance = null;
    }
}
