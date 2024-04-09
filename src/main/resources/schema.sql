DROP TABLE IF EXISTS dnd_characters;
DROP TABLE IF EXISTS races;
DROP TABLE IF EXISTS classes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dnd_sessions;
DROP TABLE IF EXISTS user_session_characters;
DROP TABLE IF EXISTS user_session_favorites;

CREATE TABLE IF NOT EXISTS dnd_characters
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId          VARCHAR(255),
    name            VARCHAR(255),
    character_class VARCHAR(255),
    race            VARCHAR(255),
    icon            VARCHAR(255),
    level           INTEGER
);

CREATE TABLE IF NOT EXISTS races
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    icon VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS classes
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    icon VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS dnd_sessions
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId          BIGINT,
    sessionId       VARCHAR UNIQUE NOT NULL,
    dungeonMasterId VARCHAR(255),
    created         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_session_characters
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId           VARCHAR(255),
    sessionId        VARCHAR(255),
    characterId      BIGINT,
    experiencePoints INTEGER,
    level            INTEGER,

    UNIQUE (userId, sessionId, characterId)
);

CREATE TABLE IF NOT EXISTS user_session_favorites
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId    BIGINT,
    sessionId VARCHAR(255),

    UNIQUE (userId, sessionId)
);