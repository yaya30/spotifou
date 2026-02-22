-- Spotifou Initial Schema

CREATE TABLE users
(
    id  BIGSERIAL  PRIMARY KEY,
    email VARCHAR(200) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    picture_url VARCHAR(200),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX  idx_users_email ON users(email);
