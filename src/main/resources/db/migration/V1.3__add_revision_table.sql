CREATE TABLE revision (
    id uuid NOT NULL PRIMARY KEY,
    created_at timestamp,
    person uuid REFERENCES person(id),
    document uuid REFERENCES document(id)
);