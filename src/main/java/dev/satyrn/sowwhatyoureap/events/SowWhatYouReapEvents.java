package dev.satyrn.sowwhatyoureap.events;

import dev.satyrn.papermc.api.configuration.v4.ConfigurationConsumer;
import dev.satyrn.sowwhatyoureap.configuration.Configuration;
import dev.satyrn.sowwhatyoureap.configuration.ConfigurationRegistry;
import dev.satyrn.sowwhatyoureap.utils.HandYields;
import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SowWhatYouReapEvents implements Listener, ConfigurationConsumer<Configuration> {

    private final @NotNull Plugin plugin;

    private List<Material> hoeItems;

    private HandYields handYields = HandYields.MINIMUM;

    private boolean requireHoe;
    private boolean damageHoe;
    private boolean applyFortune;
    private boolean requireSeedsToReplant;

    private int wheatMinYield;
    private int wheatMaxYield;
    private int wheatMinSeeds;
    private int wheatMaxSeeds;

    private int beetMinYield;
    private int beetMaxYield;
    private int beetMinSeeds;
    private int beetMaxSeeds;

    private int carrotMinYield;
    private int carrotMaxYield;

    private int potatoMinYield;
    private int potatoMaxYield;
    private double potatoPoisonChance;

    private int netherWartMinYield;
    private int netherWartMaxYield;

    public SowWhatYouReapEvents(final @NotNull Plugin plugin, final @NotNull Configuration configuration) {
        this.plugin = plugin;
        this.reloadConfiguration(configuration);
        ConfigurationRegistry.registerConsumer(this);
    }

    @Override
    public void reloadConfiguration(@NotNull Configuration configuration) {
        this.hoeItems = configuration.hoeItems.value();
        this.handYields = configuration.handYield.value();
        this.requireHoe = configuration.requireHoe.value();
        this.damageHoe = configuration.damageHoe.value();
        this.applyFortune = configuration.applyFortune.value();
        this.requireSeedsToReplant = configuration.requireSeedsToReplant.value();
        this.wheatMinYield = configuration.yields.wheat.minimumYield.value();
        this.wheatMaxYield = configuration.yields.wheat.maximumYield.value();
        this.wheatMinSeeds = configuration.yields.wheat.minimumSeeds.value();
        this.wheatMaxSeeds = configuration.yields.wheat.maximumSeeds.value();
        this.beetMinYield = configuration.yields.beetroot.minimumYield.value();
        this.beetMaxYield = configuration.yields.beetroot.maximumYield.value();
        this.beetMinSeeds = configuration.yields.beetroot.minimumSeeds.value();
        this.beetMaxSeeds = configuration.yields.beetroot.maximumSeeds.value();
        this.carrotMinYield = configuration.yields.carrot.minimumYield.value();
        this.carrotMaxYield = configuration.yields.carrot.maximumYield.value();
        this.potatoMinYield = configuration.yields.potato.minimumYield.value();
        this.potatoMaxYield = configuration.yields.potato.maximumYield.value();
        this.potatoPoisonChance = configuration.yields.potato.secondaryDropChance.value();
        this.netherWartMinYield = configuration.yields.netherWart.minimumYield.value();
        this.netherWartMaxYield = configuration.yields.netherWart.maximumYield.value();
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        // Event 1 fires for main hand item
        // Event 2 fires for offhand item

        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            // Offhand usage
        } else {
            // onhand usage
        }
    }


    private boolean isItemHoe(final @NotNull ItemStack item) {
        return this.hoeItems.stream().anyMatch(hoeItem -> item.getType() == hoeItem);
    }
}
