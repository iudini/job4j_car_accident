CREATE TABLE accident_type (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE rule (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE accident (
    id SERIAL PRIMARY KEY,
    name VARCHAR(2000),
    text TEXT,
    address TEXT,
    accident_type_id INTEGER REFERENCES accident_type(id)
);

CREATE TABLE accidents_rules (
    accident_id INTEGER REFERENCES accident(id),
    rule_id INTEGER REFERENCES rule(id),
    UNIQUE (accident_id, rule_id)
);