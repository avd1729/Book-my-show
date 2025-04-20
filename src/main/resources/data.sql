-- Admin user
INSERT INTO users (username, is_admin, email, password, first_name, last_name, phone_number)
VALUES
    ('adminjoe', true, 'admin@admin.com', '$2a$10$tQkoCgXEry/lMg1olz2N1.K9hj4xnxs5odli0BsMNu0Fe0Yqv0HcG', 'admin', 'admin', '90871237465')
    ON CONFLICT (email) DO NOTHING;
