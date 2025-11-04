# Black hole

## Docker container PostgreSQL for local launch
```bash
docker run -d --name black-hole-db -e POSTGRES_USER=blackhole -e POSTGRES_PASSWORD=gJ3lqSs5 -e POSTGRES_DB=blackhole -p 54321:5432 postgres:18.0
```