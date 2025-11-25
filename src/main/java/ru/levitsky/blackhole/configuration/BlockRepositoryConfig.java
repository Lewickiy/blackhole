package ru.levitsky.blackhole.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.levitsky.blackhole.repository.BlockRepositoryCustom;
import ru.levitsky.blackhole.repository.BlockRepositoryCustomImpl;

/**
 * Spring configuration class that defines custom beans for block repositories.
 *
 * <p>This class creates separate {@link BlockRepositoryCustom} beans for each block type
 * (Luma, ChromaCb, ChromaCr). All beans use the same implementation
 * {@link BlockRepositoryCustomImpl} but operate on different table names.</p>
 *
 * <p>Key points:
 * - {@link EntityManager} is injected via {@link PersistenceContext} and shared across all beans.
 * - Beans have explicit names ("lumaRepoCustom", "cbRepoCustom", "crRepoCustom") so that
 * they can be injected in services via {@link org.springframework.beans.factory.annotation.Qualifier}.</p>
 */
@Configuration
public class BlockRepositoryConfig {

    /**
     * EntityManager used by custom repositories
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Bean for the Luma custom repository.
     *
     * @return {@link BlockRepositoryCustom} operating on table "block_luma"
     */
    @Bean("lumaRepoCustom")
    public BlockRepositoryCustom lumaBlockRepositoryImpl() {
        return new BlockRepositoryCustomImpl("block_luma", entityManager);
    }

    /**
     * Bean for the Chroma Cb custom repository.
     *
     * @return {@link BlockRepositoryCustom} operating on table "block_chroma_cb"
     */
    @Bean("cbRepoCustom")
    public BlockRepositoryCustom cbBlockRepositoryImpl() {
        return new BlockRepositoryCustomImpl("block_chroma_cb", entityManager);
    }

    /**
     * Bean for the Chroma Cr custom repository.
     *
     * @return {@link BlockRepositoryCustom} operating on table "block_chroma_cr"
     */
    @Bean("crRepoCustom")
    public BlockRepositoryCustom crBlockRepositoryImpl() {
        return new BlockRepositoryCustomImpl("block_chroma_cr", entityManager);
    }
}
