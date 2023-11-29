-- jdbc:postgresql://postgresql-hackathon-geoquest.alwaysdata.net:5432/hackathon-geoquest_0
-- user: hackathon-geoquest

DROP TABLE IF EXISTS response;
DROP TABLE IF EXISTS quest;
DROP TABLE IF EXISTS users;
DROP TYPE IF EXISTS transaction_type;

CREATE TYPE transaction_type AS ENUM ('TRANSFER', 'PLATFORM');

CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    is_admin  BOOLEAN,
    score     INT
);

CREATE TABLE quest
(
    id            SERIAL PRIMARY KEY,
    type          VARCHAR(20) NOT NULL,
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
                          id SERIAL PRIMARY KEY,
                          quest_id INT,
                          user_id INT,
                          transfer_time INT,
                          platform_location GEOMETRY(Point, 4326),
                          FOREIGN KEY (quest_id) REFERENCES quest(ID),
                          FOREIGN KEY (user_id) REFERENCES users(ID)
);

INSERT INTO users (user_name, is_admin, score)
VALUES
    ('GandalfTheJoker', true, 0),
    ('WookieLaughs', false, 0),
    ('HobbitHumorist', false, 0),
    ('TrekkieTickler', false, 0),
    ('DumbledoreGiggles', false, 0);

INSERT INTO quest (type, title, instructions, place_id, min_responses, base_reward, start_date, end_date, transfer_from, transfer_to, platform)
VALUES
    ('TRANSFER', 'Luzern Adventure', 'Find the hidden gem in Luzern',18970 , 3, 100, '2023-06-01', '2023-06-10', 'ch:1:sloid:5000:1:3', 'ch:1:sloid:5000:3:7', null),
    ('TRANSFER', 'Bern Treasure Hunt', 'Solve the mystery in Bern', 31436, 5, 150, '2023-07-01', '2024-07-15', 'ch:1:sloid:7000:1:21'	, 'ch:1:sloid:7000:55:50'	, null),
    ('TRANSFER', 'Zürich Discovery', 'Explore the streets of Zürich', 22821, 2, 80, '2023-08-01', '2024-08-10', 'ch:1:sloid:3000:500:31'	, 'ch:1:sloid:3000:10:18'	, null),
    ('TRANSFER', 'Basel Exploration', 'Uncover the history in Basel', 44471, 4, 120, '2023-09-01', '2024-09-20', 'ch:1:sloid:10:0:20'	, 'ch:1:sloid:10:0:1'	, null),
    ('TRANSFER', 'Zug Adventure', 'Find the Zug’s hidden spots', 44295, 3, 90, '2023-11-01', '2023-12-15', 'ch:1:sloid:2204:1:1', 'ch:1:sloid:2204:1:7', null),
    ('TRANSFER', 'Chur Challenge', 'Take on the Chur challenge', 38734, 2, 100, '2023-12-01', '2023-12-10', 'ch:1:sloid:9000:1:1'	, 'ch:1:sloid:9000:1:4'	, null),
    ('TRANSFER', 'Olten Odyssey', 'Embark on the Olten journey',33325, 4, 130, '2024-01-01', '2024-01-20', 'ch:1:sloid:218:1:1'	, 'ch:1:sloid:218:1:11'	, null),
    ('PLATFORM', 'Zytglogge F', 'check the locatoin', 42067, 4, 130, '2024-01-01', '2024-01-20', NULL	, null	, 'ch:1:sloid:7110:0:2'	),
(TRANSFER','Bern Wankdorf,36493,4,130,'2023-11-01','2023-12-03','ch:1:sloid:16161:2:3','ch:1:sloid:90129:0:2',null),
(TRANSFER','Bern,31436,4,130,'2023-11-01','2023-12-03','ch:1:sloid:7000:500:21','ch:1:sloid:76646:0:5',null),
(TRANSFER','Bern,31436,4,130,'2023-11-01','2023-12-03','ch:1:sloid:7000:500:21','ch:1:sloid:81351:0:64472',null),
(TRANSFER','Bern,31436,4,130,'2023-11-01','2023-12-03','ch:1:sloid:7000:500:21','ch:1:sloid:7785:0:367530',null),
(TRANSFER','Bern,31436,4,130,'2023-11-01','2023-12-03','ch:1:sloid:7000:500:21','ch:1:sloid:30257:0:497737',null),
(PLATFORM','Bern, Güterbahnhof,14222,4,130,'2023-11-01','2023-12-03',null,null,'ch:1:sloid:90025:0:19199'),
(PLATFORM','Bern, Güterbahnhof,14222,4,130,'2023-11-01','2023-12-03',null,null,'ch:1:sloid:90025:0:9060');

