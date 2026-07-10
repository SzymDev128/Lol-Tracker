CREATE TABLE players
(
    id              BIGSERIAL PRIMARY KEY,
    puuid           VARCHAR(100) NOT NULL UNIQUE,
    game_name       VARCHAR(50)  NOT NULL,
    tag_line        VARCHAR(10)  NOT NULL,
    platform        VARCHAR(10)  NOT NULL,
    summoner_level  BIGINT       NOT NULL,
    profile_icon_id INTEGER      NOT NULL,
    last_updated    TIMESTAMP    NOT NULL
);

CREATE TABLE rank_entries
(
    id            BIGSERIAL PRIMARY KEY,
    player_id     BIGINT      NOT NULL REFERENCES players (id),
    queue_type    VARCHAR(30) NOT NULL,
    tier          VARCHAR(20) NOT NULL,
    rank          VARCHAR(5)  NOT NULL,
    league_points INTEGER     NOT NULL,
    wins          INTEGER     NOT NULL,
    losses        INTEGER     NOT NULL
);
