CREATE TABLE cards.public.app_user (
    surname varchar(255) PRIMARY KEY,
    name varchar(255) NOT NULL,
    avatarUrl varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE cards.public.card (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE cards.public.user_identifier (
    surname VARCHAR(255) PRIMARY KEY
);

CREATE TABLE cards.public.user_card (
    id SERIAL PRIMARY KEY,
    card_id INT NOT NULL,
    owner_surname VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (card_id) REFERENCES card(id),
    FOREIGN KEY (owner_surname) REFERENCES user_identifier(surname)
);

CREATE TABLE cards.public.transaction (
    id SERIAL PRIMARY KEY,
    user_card_id INT NOT NULL,
    seller_surname VARCHAR(255) NOT NULL,
    buyer_surname VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    sold_on DATE NOT NULL,
    FOREIGN KEY (user_card_id) REFERENCES user_card(id),
    FOREIGN KEY (seller_surname) REFERENCES user_identifier(surname),
    FOREIGN KEY (buyer_surname) REFERENCES user_identifier(surname)
);