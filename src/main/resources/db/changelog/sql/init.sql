DROP TABLE IF EXISTS users, task_group, task CASCADE;

CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT username_unique UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS task_group(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    users_id INTEGER NOT NULL REFERENCES users(id),
    is_default boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS task(
    id SERIAL PRIMARY KEY,
    text VARCHAR(64) NOT NULL,
    date_created DATE NOT NULL,
    date_dead_line DATE,
    task_group_id INTEGER NOT NULL REFERENCES task_group(id),
    is_completed boolean NOT NULL DEFAULT FALSE
);