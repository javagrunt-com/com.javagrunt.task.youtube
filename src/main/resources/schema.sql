CREATE TABLE IF NOT EXISTS you_tube_video
(
    id          varchar(25) primary key,
    link        varchar(255),
    description text,
    title       varchar(255),
    thumbnail   varchar(255),
    date        varchar(255)
);