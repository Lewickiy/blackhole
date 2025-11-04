CREATE TABLE IF NOT EXISTS block
(
    id         UUID                        NOT NULL,
    hash       VARCHAR(64)                 NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data       OID                         NOT NULL,
    size       INTEGER                     NOT NULL,
    CONSTRAINT pk_block PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS block
    ADD CONSTRAINT uc_block_hash UNIQUE (hash);

CREATE INDEX IF NOT EXISTS idx_block_hash ON block (hash);