DROP TABLE IF EXISTS dnd_characters;
DROP TABLE IF EXISTS races;
DROP TABLE IF EXISTS classes;

CREATE TABLE IF NOT EXISTS dnd_characters
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId          BIGINT,
    name            VARCHAR(255),
    character_class VARCHAR(255),
    race            VARCHAR(255),
    level           INTEGER
);

CREATE TABLE IF NOT EXISTS races
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS classes
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS dnd_sessions
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId      BIGINT,
    sessionId   VARCHAR(255) UNIQUE NOT NULL,
    expiration  TIMESTAMP
);