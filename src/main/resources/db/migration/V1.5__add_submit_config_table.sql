CREATE TABLE submit_config
(
    id         uuid NOT NULL PRIMARY KEY,
    created_at timestamp,
    type       character varying(255),
    active     boolean,
    data       jsonb,
    person     uuid REFERENCES person (id)
);