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
    userId          VARCHAR2(255),
    name            VARCHAR2(255),
    character_class VARCHAR2(255),
    race            VARCHAR2(255),
    icon            VARCHAR2(255),
    level           INTEGER
);

CREATE TABLE IF NOT EXISTS races
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR2(255),
    icon VARCHAR2(255)
);

CREATE TABLE IF NOT EXISTS classes
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR2(255),
    icon VARCHAR2(255)
);

CREATE TABLE IF NOT EXISTS users
(
    id       VARCHAR2(255) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR2(255),
    password VARCHAR2(255)
);

CREATE TABLE IF NOT EXISTS dnd_sessions
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId          BIGINT,
    sessionId       VARCHAR2(255) UNIQUE NOT NULL,
    dungeonMasterId VARCHAR2(255),
    created         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_session_characters
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId           VARCHAR2(255),
    sessionId        VARCHAR2(255),
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