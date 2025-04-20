-- Admin user
INSERT INTO users (created_at, email, first_name, is_admin, last_name, password, phone_number, username)
VALUES (NOW(), 'admin@example.com', 'Admin', true, 'User', '$2a$10$7uDksLZfyx5HDm7VEHkO1epEo3aTba8N5YrYIjz4yOQ9b4oB2TtIi', '1234567890', 'admin')
    ON CONFLICT (email) DO NOTHING;
