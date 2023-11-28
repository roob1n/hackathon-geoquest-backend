-- jdbc:postgresql://postgresql-hackathon-geoquest.alwaysdata.net:5432/hackathon-geoquest_0
-- user: hackathon-geoquest

DROP TABLE IF EXISTS response;
DROP TABLE IF EXISTS quest;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS place;
DROP TYPE IF EXISTS transaction_type;

CREATE TYPE transaction_type AS ENUM ('TRANSFER', 'PLATFORM');

CREATE TABLE place
(
    ID           SERIAL PRIMARY KEY,
    location     GEOMETRY(Point, 4326) NOT NULL,
    sloid        VARCHAR(255),
    display_name VARCHAR(255)
);

CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    is_admin  BOOLEAN,
    score     INT
);

CREATE TABLE quest
(
    ID            SERIAL PRIMARY KEY,
    Type          transaction_type NOT NULL,
    title         VARCHAR(255),
    instructions  TEXT,
    place_id      INTEGER,
    min_responses INT,
    base_reward   INT,
    start_date    DATE,
    end_date      DATE,
    transfer_from VARCHAR(255),
    transfer_to   VARCHAR(255),
    platform      VARCHAR(255),
    FOREIGN KEY (place_id) REFERENCES Place (ID) -- Foreign key constraint
);

CREATE TABLE response (
                          ID SERIAL PRIMARY KEY,
                          quest_id INT,
                          user_id INT,
                          transfer_time INT, -- Assuming this is in seconds
                          platform_location POINT,
                          FOREIGN KEY (quest_id) REFERENCES quest(ID),
                          FOREIGN KEY (user_id) REFERENCES users(ID)
);

INSERT INTO place (location, sloid, display_name)
VALUES
    (ST_GeomFromText('POINT(8.31018320694279 47.0501778280856)', 4326), 'ch:1:sloid:5000', 'Luzern'),
    (ST_GeomFromText('POINT(7.439130889923935 46.948832290498416)', 4326), 'ch:1:sloid:7000', 'Bern'),
    (ST_GeomFromText('POINT(8.54169438364029 47.3768866)', 4326), 'ch:1:sloid:3000', 'Zürich'),
    (ST_GeomFromText('POINT(7.5885761 47.5595986)', 4326), 'ch:1:sloid:10', 'Basel'),
    (ST_GeomFromText('POINT(6.1431577 46.2044)', 4326), 'ch:1:sloid:1008', 'Genève'),
    (ST_GeomFromText('POINT(8.5167244 47.1724)', 4326), 'ch:1:sloid:2204', 'Zug'),
    (ST_GeomFromText('POINT(9.532007 46.8523)', 4326), 'ch:1:sloid:9000', 'Chur'),
    (ST_GeomFromText('POINT(7.9045969 47.3526)', 4326), 'ch:1:sloid:218', 'Olten');

INSERT INTO users (user_name, is_admin, score)
VALUES
    ('GandalfTheJoker', true, 0),
    ('WookieLaughs', false, 0),
    ('HobbitHumorist', false, 0),
    ('TrekkieTickler', false, 0),
    ('DumbledoreGiggles', false, 0);
