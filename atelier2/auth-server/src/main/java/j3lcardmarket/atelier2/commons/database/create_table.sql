CREATE TABLE cards.public.users (
    surname varchar(255) PRIMARY KEY,
    name varchar(255) NOT NULL,
    avatarUrl varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);