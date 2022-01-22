package dev.satyrn.sowwhatyoureap.commands;

import dev.satyrn.papermc.api.commands.v1.CommandHandler;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Handles commands that require access to the Vault permissions manager.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public abstract class VaultCommandHandler extends CommandHandler {
    // The permission manager instance.
    private final transient @NotNull Permission permission;

    /**
     * Initializes the command handler with the permission manager.
     * @param permission The perrmission manager.
     */
    protected VaultCommandHandler(final @NotNull Plugin plugin, final @NotNull Permission permission) {
        super(plugin);
        this.permission = permission;
    }

    /**
     * Gets the permission manager instance.
     * @return The permission manager.
     */
    protected final @NotNull Permission getPermission() {
        return this.permission;
    }
}
