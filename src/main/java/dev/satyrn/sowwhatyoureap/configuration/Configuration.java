package dev.satyrn.sowwhatyoureap.configuration;

import dev.satyrn.papermc.api.configuration.v1.*;
import dev.satyrn.papermc.api.configuration.v2.EnumListNode;
import dev.satyrn.sowwhatyoureap.SowWhatYouReap;
import dev.satyrn.sowwhatyoureap.utils.HandYields;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * Represents the configuration of the plugin.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public class Configuration extends ConfigurationContainer {
    /**
     * A list of materials which should count as hoes.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull EnumListNode<Material> hoeItems = new EnumListNode<Material>(this, "hoeItems") {
        @Override
        protected @NotNull Material parse(@NotNull String value) throws IllegalArgumentException {
            try {
                return Material.valueOf(value);
            } catch (IllegalArgumentException ex) {
                JavaPlugin.getPlugin(SowWhatYouReap.class)
                        .getLogger()
                        .log(Level.WARNING, String.format("Hoe Items configuration contains an invalid entry %s. This entry has been ignored.", value));
                throw ex;
            }
        }
    };

    /**
     * Whether the plugin requires a player to be using a hoe to harvest items.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull BooleanNode requireHoe = new BooleanNode(this, "requireHoe");

    /**
     * Whether using a hoe will damage it.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull BooleanNode damageHoe = new BooleanNode(this, "damageHoe") {
        @Override
        public @NotNull Boolean defaultValue() {
            return true;
        }
    };

    /**
     * Whether the Fortune enchantment will be applied to crop yields.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull BooleanNode applyFortune = new BooleanNode(this, "applyFortune") {
        @Override
        public @NotNull Boolean defaultValue() {
            return true;
        }
    };

    /**
     * Whether the player requires seeds of a matching crop type to be able to replant crops.
     * This also counts dropped seeds if any.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull BooleanNode requireSeedsToReplant = new BooleanNode(this, "requireSeedsToReplant");

    /**
     * The yield for crops which are harvested by hand.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull EnumNode<HandYields> handYield = new EnumNode<HandYields>(this, "handYield") {
        @Override
        protected @NotNull HandYields parse(@NotNull String value) throws IllegalArgumentException {
            try {
                return HandYields.valueOf(value);
            } catch (IllegalArgumentException ex) {
                JavaPlugin.getPlugin(SowWhatYouReap.class)
                        .getLogger()
                        .log(Level.WARNING, String.format("Hand yields config is set to an invalid value %s. The default value %s has been used.", value, this.getDefault()));
                throw ex;
            }
        }

        @Override
        protected @NotNull HandYields getDefault() {
            return HandYields.MINIMUM;
        }
    };

    /**
     * Yield configuration per crop.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull YieldsConfig yields = new YieldsConfig(this);

    /**
     * Whether debug output should be enabled.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull BooleanNode debug = new BooleanNode(this, "debug");

    /**
     * Whether plugin metrics should be enabled.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull BooleanNode metrics = new BooleanNode(this, "metrics");

    /**
     * The locale to use.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final transient @NotNull StringNode locale = new StringNode(this, "locale");

    /**
     * Initializes a new instance of the plugin configuration.
     *
     * @param plugin The plugin instance.
     * @since 0.0.0-SNAPSHOT
     */
    public Configuration(final @NotNull Plugin plugin) {
        super(plugin.getConfig());
    }

    /**
     * Represents a configuration section which contains yields per crop.
     *
     * @author Isabel Maskrey
     * @since 0.0.0-SNAPSHOT
     */
    public static final class YieldsConfig extends ConfigurationContainer {
        /**
         * Yields for the wheat crop.
         *
         * @since 0.0.0-SNAPSHOT
         */
        public final transient @NotNull SeededYieldConfig wheat = new SeededYieldConfig(this, "wheat");

        /**
         * Yields for the beetroot crop.
         *
         * @since 0.0.0-SNAPSHOT
         */
        public final transient @NotNull SeededYieldConfig beetroot = new SeededYieldConfig(this, "beetroot");

        /**
         * Yields for the carrot crop.
         *
         * @since 0.0.0-SNAPSHOT
         */
        public final transient @NotNull YieldConfig carrot = new YieldConfig(this, "carrot");

        /**
         * Yields for the potato crop.
         *
         * @since 0.0.0-SNAPSHOT
         */
        public final transient @NotNull PotatoYieldConfig potato = new PotatoYieldConfig(this, "potato");

        /**
         * Yields for the nether wart crop.
         *
         * @since 0.0.0-SNAPSHOT
         */
        public final transient @NotNull YieldConfig netherWart = new YieldConfig(this, "netherWart");

        /**
         * Creates a "yields" configuration container instance with a pathname of "yields".
         *
         * @param parent The parent configuration container.
         * @since 0.0.0-SNAPSHOT
         */
        YieldsConfig(final @NotNull ConfigurationContainer parent) {
            super(parent, "yields");
        }

        /**
         * Models a basic yield information section.
         *
         * @author Isabel Maskrey
         * @since 0.0.0-SNAPSHOT
         */
        public static class YieldConfig extends ConfigurationContainer {
            /**
             * The minimum yield for a given crop.
             *
             * @since 0.0.0-SNAPSHOT
             */
            public final transient @NotNull IntegerNode minimumYield = new IntegerNode(this, "minimumYield");
            /**
             * The maximum yield for a given crop.
             *
             * @since 0.0.0-SNAPSHOT
             */
            public final transient @NotNull IntegerNode maximumYield = new IntegerNode(this, "maximumYield");

            /**
             * Creates a yield configuration container.
             *
             * @param parent The parent container.
             * @param name   The configuration section's trunk name.
             * @since 0.0.0-SNAPSHOT
             */
            YieldConfig(final @NotNull ConfigurationContainer parent, final @NotNull String name) {
                super(parent, name);
            }
        }

        /**
         * Models yield information for a crop with seeds.
         *
         * @author Isabel Maskrey
         * @since 0.0.0-SNAPSHOT
         */
        public static final class SeededYieldConfig extends YieldConfig {
            /**
             * Minimum number of seeds which can drop from harvesting.
             *
             * @since 0.0.0-SNAPSHOT
             */
            public final transient @NotNull IntegerNode minimumSeeds = new IntegerNode(this, "minimumSeeds");

            /**
             * Maximum number of seeds which can drop from harvesting.
             *
             * @since 0.0.0-SNAPSHOT
             */
            public final transient @NotNull IntegerNode maximumSeeds = new IntegerNode(this, "maximumSeeds");

            /**
             * Creates a new seeded crop yield configuration.
             *
             * @param parent The parent container.
             * @param name The configuration section's trunk name.
             * @since 0.0.0-SNAPSHOT
             */
            SeededYieldConfig(final @NotNull ConfigurationContainer parent, final @NotNull String name) {
                super(parent, name);
            }
        }

        /**
         * Models yield information for a potato-like crop which can drop a secondary type of item.
         *
         * @author Isabel Maskrey
         * @since 0.0.0-SNAPSHOT
         */
        public static final class PotatoYieldConfig extends YieldConfig {
            /**
             * Chance that the crop's secondary drop will be yielded.
             *
             * @since 0.0.0-SNAPSHOT
             */
            public final transient @NotNull DoubleNode secondaryDropChance = new DoubleNode(this, "secondaryDropChance");

            /**
             * Creates a new potato-like crop yield configuration.
             *
             * @param parent The parent container.
             * @param name The configuration section's trunk name
             * @since 0.0.0-SNAPSHOT
             */
            PotatoYieldConfig(final @NotNull ConfigurationContainer parent, final @NotNull String name) {
                super(parent, name);
            }
        }
    }
}
