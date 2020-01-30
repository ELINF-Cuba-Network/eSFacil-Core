CREATE TABLE person (
    id uuid NOT NULL PRIMARY KEY,
    active boolean,
    email character varying(255) unique,
    firstname character varying(255),
    lastname character varying(255),
    password character varying(255),
    username character varying(255) unique
);

CREATE TABLE document (
    id uuid NOT NULL PRIMARY KEY,
    condition character varying(255),
    data jsonb,
    person uuid REFERENCES person(id)
);

CREATE TABLE bitstream (
    id uuid NOT NULL PRIMARY KEY,
    code character varying(255),
    extension character varying(255),
    name character varying(255),
    document_id uuid REFERENCES document(id)
);

CREATE TABLE public.user_roles (
    user_id uuid NOT NULL REFERENCES person(id),
    roles character varying(255)
);