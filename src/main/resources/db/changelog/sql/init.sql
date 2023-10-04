DROP TABLE IF EXISTS task_group, task CASCADE;
CREATE TABLE IF NOT EXISTS task_group(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS task(
    id SERIAL PRIMARY KEY,
    text VARCHAR(64) NOT NULL,
    date_created DATE NOT NULL,
    date_dead_line DATE,
    task_group_id INTEGER NOT NULL REFERENCES task_group(id),
    is_completed boolean NOT NULL DEFAULT FALSE
);