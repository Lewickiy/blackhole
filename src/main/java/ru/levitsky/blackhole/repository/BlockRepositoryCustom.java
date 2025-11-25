package ru.levitsky.blackhole.repository;

import org.springframework.data.repository.NoRepositoryBean;

/**
 * Custom repository interface for blocks, providing additional operations
 * that are not part of standard JpaRepository methods.
 *
 * <p>Specifically, this interface defines a method to increment the usage count
 * for a batch of block hashes. Implementations of this interface should
 * perform the update efficiently, ideally using a single bulk query.</p>
 *
 * <p>Note:
 * - This interface is annotated with {@link NoRepositoryBean} so that Spring Data JPA
 * does not try to create an instance of it directly.
 * - Each block type repository (Luma, ChromaCb, ChromaCr) should have
 * its own implementation that points to the correct database table.</p>
 */
@NoRepositoryBean
public interface BlockRepositoryCustom {

    /**
     * Increment the usage count for the given array of block hashes.
     *
     * @param hashArray a two-dimensional byte array containing the hashes
     *                  of the blocks whose usage count should be incremented.
     */
    void incrementUsageForHashes(byte[][] hashArray);
}
