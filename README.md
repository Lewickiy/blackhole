# Blackhole Server
**Blackhole Server** is a backend component for **block-level, deduplicated image storage**.
It stores unique Y/U/V blocks uploaded by [clients](https://github.com/Lewickiy/blackhole-eh) and serves as the authoritative repository for reconstructing images from `.blho` manifests.

> ⚠️ **Project status:** Research prototype. 
> Server functionality is focused on storage and retrieval; 
> compression and advanced deduplication research are handled on the client side.

## Purpose
The server handles:
* Storage of **unique 8×8 image blocks** (Y, U, V components).
* Indexing blocks by **SHA-256 hashes** for deduplication.
* Receiving block uploads from [clients](https://github.com/Lewickiy/blackhole-eh).
* Tracking metadata for `.blho` manifests.

It **does not process images**, perform transforms, or generate `.blho` files — these are responsibilities of the client.

## Architecture Overview
```
Client (.blho + missing blocks) ──► Blackhole Server ──► Block storage + metadata DB
```
1. **[Client](https://github.com/Lewickiy/blackhole-eh) uploads** missing blocks.
2. Server **stores blocks** in a deduplicated format.
3. Server **tracks metadata** to ensure reconstruction is possible.

### Storage Components
* **Luma Storage:** Stores unique Y blocks.
* **Chroma Storage:** Stores unique U and V blocks.
* **Metadata DB:** Tracks `.blho` files and block references.

### Block Deduplication
* Each block is hashed independently using **SHA-256**.
* The server **rejects duplicate uploads**.
* Guarantees **lossless reconstruction** when all referenced blocks are available.

## Getting Started
### Requirements
* Java 21+
* Maven
* PostgreSQL (or compatible RDBMS)

### Launch Server with Docker (PostgreSQL)
```bash
docker run -d --name blackhole-db \
  -e POSTGRES_USER=<user> \
  -e POSTGRES_PASSWORD=<password> \
  -e POSTGRES_DB=blackhole \
  -p 54321:5432 \
  postgres:17-alpine3.22
```

### Run Server
```bash
mvn spring-boot:run
```

The server will:
* Accept block uploads from [clients](https://github.com/Lewickiy/blackhole-eh).
* Maintain deduplicated block storage.
* Track `.blho` metadata.

## Limitations
* Server **does not perform image processing** — all transformations and `.blho` generation happen on the [client](https://github.com/Lewickiy/blackhole-eh).
* Deduplication is based solely on SHA-256 hashes — no delta compression yet.
* Designed for **centralized storage**; distributed deployment requires additional coordination.

## License
This project is proprietary and provided for research and review purposes only.

© 2025 Anatoliy Levitsky. All rights reserved.

Any use, reproduction, modification, or distribution of this software
without explicit written permission from the author is prohibited.