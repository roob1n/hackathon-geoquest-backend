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
    sloid        UUID,
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
