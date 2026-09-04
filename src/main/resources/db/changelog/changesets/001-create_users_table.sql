CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255),
    telegram_id BIGINT UNIQUE,
    created_at TIMESTAMPTZ NOT NULL
);