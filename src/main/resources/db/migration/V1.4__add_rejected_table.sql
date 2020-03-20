CREATE TABLE rejection (
      id uuid NOT NULL PRIMARY KEY,
      created_at timestamp,
      message text,
      person uuid REFERENCES person(id),
      document uuid REFERENCES document(id)
);