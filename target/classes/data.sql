INSERT INTO account (first_name, last_name, remember_me, username, password) VALUES
('Haroon', 'Butt', 1, 'haroon', 'password123'),
('Sara', 'Ali', 0, 'sara', 'password123'),
('Ali', 'Khan', 0, 'ali', 'password123'),
('Maya', 'Iqbal', 1, 'maya', 'password123'),
('Zain', 'Rashid', 0, 'zain', 'password123');

INSERT INTO movie_type (type_name) VALUES
('Action'),
('Drama'),
('Science Fiction'),
('Comedy'),
('Mystery');

INSERT INTO movie (name, description, publication_year, director, actor, genre, cover_photo, ticket_price, type_id, account_id) VALUES
('Storm Front', 'A retired detective faces a supernatural storm to save a city.', 2022, 'Bilal Ahmed', 'Fahad Mustafa', 'Action', 'https://example.com/stormfront.jpg', 18.00, 1, 1),
('Broken Dreams', 'The journey of a filmmaker chasing authenticity in a fragile world.', 2023, 'Muna Hussain', 'Sajal Aly', 'Drama', 'https://example.com/brokendreams.jpg', 12.50, 2, 2),
('Orbit Seven', 'An astronaut crew reconnects with humanity after a near-miss with a rogue planet.', 2024, 'Imran Abbas', 'Sheheryar Munawar', 'Science Fiction', 'https://example.com/orbitseven.jpg', 21.00, 3, 3),
('Campus Crazy', 'College friends turn the campus upside down while chasing one last laugh.', 2021, 'Amna Ilyas', 'Ahmed Ali Butt', 'Comedy', 'https://example.com/campuscrazy.jpg', 10.00, 4, 4),
('Silent Echoes', 'A linguist uncovers messages hidden in abandoned radio frequencies.', 2025, 'Tania Khan', 'Mahira Khan', 'Mystery', 'https://example.com/silentechoes.jpg', 14.75, 5, 5);

INSERT INTO cart_item (quantity, purchased, movie_id, account_id) VALUES
(2, 0, 1, 1),
(1, 0, 2, 2),
(3, 0, 3, 1),
(1, 0, 4, 3),
(2, 0, 5, 4);

INSERT INTO transaction_history (total_amount, status, movie_id, account_id) VALUES
(36.00, 'COMPLETED', 1, 1),
(12.50, 'COMPLETED', 2, 2),
(63.00, 'COMPLETED', 3, 1),
(10.00, 'COMPLETED', 4, 3),
(29.50, 'COMPLETED', 5, 4);

INSERT INTO rating (rating, comment, movie_id, account_id) VALUES
(5, 'Absolutely electrifying storytelling.', 1, 1),
(4, 'The pacing felt refreshing and grounded.', 2, 2),
(5, 'Space adventure with a warm heart.', 3, 3),
(3, 'Some jokes landed harder than others.', 4, 4),
(5, 'Mystery unravelled at a great pace.', 5, 5);