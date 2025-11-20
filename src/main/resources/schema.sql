CREATE TABLE IF NOT EXISTS account (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    remember_me TINYINT(1) DEFAULT 0,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS movie_type (
    type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS movie (
    movie_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    publication_year INT,
    director VARCHAR(100),
    actor VARCHAR(200),
    genre VARCHAR(100),
    cover_photo VARCHAR(500),
    ticket_price DECIMAL(10, 2),
    type_id BIGINT NOT NULL,
    account_id BIGINT,
    CONSTRAINT fk_movie_type FOREIGN KEY (type_id) REFERENCES movie_type(type_id),
    CONSTRAINT fk_movie_account FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE IF NOT EXISTS transaction_history (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_amount DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30),
    movie_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT fk_transaction_movie FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE IF NOT EXISTS cart_item (
    cart_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    purchased TINYINT(1) DEFAULT 0,
    purchase_date TIMESTAMP NULL,
    movie_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT fk_cart_movie FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    CONSTRAINT fk_cart_account FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE IF NOT EXISTS rating (
    rating_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rating_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    rating INT NOT NULL,
    comment VARCHAR(500),
    movie_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT fk_rating_movie FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    CONSTRAINT fk_rating_account FOREIGN KEY (account_id) REFERENCES account(account_id)
);