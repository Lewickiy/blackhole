CREATE TABLE IF NOT EXISTS block_luma
(
    hash        BYTEA PRIMARY KEY CHECK (octet_length(hash) = 32),
    created_at  TIMESTAMP       DEFAULT now() NOT NULL,
    data        BYTEA  NOT NULL,
    usage_count BIGINT NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS block_chroma_cb
(
    hash        BYTEA PRIMARY KEY CHECK (octet_length(hash) = 32),
    created_at  TIMESTAMP       DEFAULT now() NOT NULL,
    data        BYTEA  NOT NULL,
    usage_count BIGINT NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS block_chroma_cr
(
    hash        BYTEA PRIMARY KEY CHECK (octet_length(hash) = 32),
    created_at  TIMESTAMP       DEFAULT now() NOT NULL,
    data        BYTEA  NOT NULL,
    usage_count BIGINT NOT NULL DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_block_luma_usage ON block_luma (usage_count DESC);
CREATE INDEX IF NOT EXISTS idx_block_chroma_cb_usage ON block_chroma_cb (usage_count DESC);
CREATE INDEX IF NOT EXISTS idx_block_chroma_cr_usage ON block_chroma_cr (usage_count DESC);
