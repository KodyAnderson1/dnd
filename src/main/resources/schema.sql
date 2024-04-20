DROP TABLE IF EXISTS dnd_characters;
DROP TABLE IF EXISTS races;
DROP TABLE IF EXISTS classes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dnd_sessions;
DROP TABLE IF EXISTS user_session_characters;
DROP TABLE IF EXISTS user_session_favorites;
DROP TABLE IF EXISTS user_session_character_attributes;
DROP TABLE IF EXISTS dnd_character_stats;


-- General Tables
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


-- User specific tables
CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    guid     VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS dnd_characters
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_guid       VARCHAR(255),
    name            VARCHAR(255),
    character_class VARCHAR(255),
    race            VARCHAR(255),
    icon            VARCHAR(255),

    strength     DECIMAL,
    initiative   DECIMAL,
    dexterity    DECIMAL,
    constitution DECIMAL,
    intelligence DECIMAL,
    wisdom       DECIMAL,
    charisma     DECIMAL
);


-- Session related tables below
CREATE TABLE IF NOT EXISTS dnd_sessions
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(255),
    description         VARCHAR(255),
    guid                VARCHAR(255),
    dungeon_master_guid VARCHAR(255),
    invite_code         VARCHAR(255),
    created             TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_sessions
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_guid   VARCHAR(255),
    session_guid VARCHAR(255),
    character_guid BIGINT,
    attributes_guid BIGINT,
    is_dungeon_master BOOLEAN,
    created     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (user_guid, session_guid)
);

CREATE TABLE IF NOT EXISTS user_session_characters
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_guid       VARCHAR(255),
    session_guid    VARCHAR(255),
    character_id    BIGINT,

    name            VARCHAR(255),
    character_class VARCHAR(255),
    race            VARCHAR(255),
    icon            VARCHAR(255),

    UNIQUE (user_guid, session_guid, character_id)
);

CREATE TABLE IF NOT EXISTS user_session_character_attributes
(
    id                        BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_session_character_fk BIGINT,
    user_guid                 VARCHAR(255),

    experience_points         INTEGER,
    level                     DECIMAL,

    health                    DECIMAL,
    stamina                   DECIMAL,
    mana                      DECIMAL,

    strength                  DECIMAL,
    initiative                DECIMAL,
    dexterity                 DECIMAL,
    constitution              DECIMAL,
    intelligence              DECIMAL,
    wisdom                    DECIMAL,
    charisma                  DECIMAL
);

