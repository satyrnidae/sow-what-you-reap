package dev.satyrn.sowwhatyoureap.utils;

/**
 * Represents the valid values of the Hand Yield configuration entry.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public enum HandYields {
    /**
     * No yield from hands
     */
    NONE,
    /**
     * One drop from hands
     */
    ONE,
    /**
     * Minimum drop from hands
     */
    MINIMUM,
    /**
     * Minimum drop with minimum seeds from hands
     */
    MINIMUM_WITH_SEEDS,
    /**
     * Random between min and max, but no seeds
     */
    NO_SEEDS,
    /**
     * Uses same logic as the hoe
     */
    SAME_AS_HOE
}
