DROP TABLE IF EXISTS task_group, task CASCADE;
CREATE TABLE IF NOT EXISTS task_group(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

ALTER TABLE task_group ADD UNIQUE (name);

CREATE TABLE IF NOT EXISTS task(
    id SERIAL PRIMARY KEY,
    text VARCHAR(64) NOT NULL,
    date_created DATE NOT NULL,
    date_dead_line DATE NOT NULL,
    task_group_id INTEGER NOT NULL REFERENCES task_group(id),
    is_completed boolean NOT NULL DEFAULT FALSE
);