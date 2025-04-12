-- Insert Users
INSERT INTO users (username, is_admin, email, password, first_name, last_name, phone_number)
VALUES
    ('adminjoe', true,'admin@admin.com', '$2a$10$tQkoCgXEry/lMg1olz2N1.K9hj4xnxs5odli0BsMNu0Fe0Yqv0HcG', 'admin', 'admin', '90871237465'),
    ('john123', false,'john.doe@example.com', '$2a$10$bK4sngBXPZ99egFe6srq7ei/YFDKORnImGsMxqXeFA/BVOR15LERa', 'John', 'Doe', '1234567890'),
    ('jane456', false,'jane.smith@example.com', '$2a$10$bK4sngBXPZ99egFe6srq7ei/YFDKORnImGsMxqXeFA/BVOR15LERa', 'Jane', 'Smith', '9876543210');

-- Insert Theaters (Added `is_active`)
INSERT INTO theaters (theater_name, theater_address, theater_city, theater_state, zip_code, total_screens, is_active)
VALUES
    ('Grand Cinema', '123 Main St', 'New York', 'NY', '10001', 5, true),
    ('Movie Palace', '456 Elm St', 'Los Angeles', 'CA', '90001', 7, true);

-- Insert Movies
INSERT INTO movies (title, description, duration, rating, genre, release_date, end_date, poster_url, backdrop_url, trailer_url, language)
VALUES
    ('Inception', 'A mind-bending thriller', 148, 'PG-13', 'Sci-Fi', '2010-07-16', '2010-12-31', 'poster_url1', 'backdrop_url1', 'trailer_url1', 'English'),
    ('Interstellar', 'A journey beyond the stars', 169, 'PG-13', 'Sci-Fi', '2014-11-07', '2015-05-01', 'poster_url2', 'backdrop_url2', 'trailer_url2', 'English');

-- Insert Screens (Added `is_active`)
INSERT INTO screens (theater_id, screen_number, seating_capacity, screen_type, is_active)
VALUES
    (1, 1, 150, 'IMAX', true),
    (1, 2, 100, 'Regular', true),
    (2, 1, 200, 'THREE_D', true);

-- Insert Seats
INSERT INTO seats (screen_id, seat_row, seat_number, seat_type, is_active)
VALUES
    (1, 'A', 1, 'Regular', true),
    (1, 'A', 2, 'Premium', true),
    (2, 'B', 1, 'Recliner', true);

-- Insert Showtimes
INSERT INTO show_times (movie_id, screen_id, start_time, end_time, price, is_active)
VALUES
    (1, 1, '2025-02-03 14:00:00', '2025-02-03 16:30:00', 12.99, true),
    (2, 2, '2025-02-03 18:00:00', '2025-02-03 20:30:00', 10.99, true);

-- Insert Reservations
INSERT INTO reservations (user_id, showtime_id, reservation_status, total_amount, payment_status)
VALUES
    (1, 1, 'Confirmed', 25.98, 'Paid'),
    (2, 2, 'Pending', 10.99, 'Pending');

-- Insert Reserved Seats
INSERT INTO reserved_seats (reservation_id, seat_id, price)
VALUES
    (1, 1, 12.99),
    (1, 2, 12.99);

-- Insert Payments
INSERT INTO payments (reservation_id, amount, payment_type, transaction_id, payment_status)
VALUES
    (1, 25.98, 'Credit Card', '12345', 'Paid'),
    (2, 10.99, 'PayPal', '67890', 'Pending');

-- Insert Reviews
INSERT INTO reviews (user_id, movie_id, rating, review_text)
VALUES
    (1, 1, 5, 'Amazing movie!'),
    (2, 2, 4, 'Great visuals and story.');
