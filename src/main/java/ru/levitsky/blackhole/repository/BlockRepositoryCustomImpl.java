package ru.levitsky.blackhole.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of a custom repository for blocks.
 *
 * <p>Provides a concrete implementation of {@link BlockRepositoryCustom#incrementUsageForHashes(byte[][])},
 * which increments the usage count of blocks in the database table.</p>
 *
 * <p>Features:
 * - The class is parameterized with the table name so the same code
 * can be used for different types of blocks (LumaBlock, ChromaCbBlock, ChromaCrBlock).
 * - {@link EntityManager} is used to execute a native SQL query.
 * - The implementation performs a bulk update to efficiently increment
 * the usage count for multiple blocks in a single query.</p>
 *
 * <p>Note:
 * - {@link RequiredArgsConstructor} generates a constructor with parameters
 * for tableName and EntityManager.</p>
 */
@RequiredArgsConstructor
public class BlockRepositoryCustomImpl implements BlockRepositoryCustom {

    /**
     * The table name where blocks of this type are stored
     */
    private final String tableName;

    /**
     * EntityManager used to execute native SQL queries
     */
    private final EntityManager em;

    /**
     * Increments the usage count for all hashes in the array.
     *
     * @param hashArray a 2D byte array containing block hashes
     *                  for which the usage count will be incremented.
     */
    @Override
    public void incrementUsageForHashes(byte[][] hashArray) {
        Query query = em.createNativeQuery(
                "UPDATE " + tableName + " SET usage_count = usage_count + 1 WHERE hash = ANY(:hashArray)"
        );
        query.setParameter("hashArray", hashArray);
        query.executeUpdate();
    }
}
