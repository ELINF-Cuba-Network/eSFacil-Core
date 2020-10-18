CREATE TABLE submit_task
(
    id            uuid NOT NULL PRIMARY KEY,
    status        smallint,
    error_message text,
    submit_config uuid REFERENCES submit_config (id),
    document      uuid REFERENCES document (id),
    created_at    timestamp,
    updated_at    timestamp
);