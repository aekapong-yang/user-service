CREATE DATABASE IF NOT EXISTS my_app_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (username, email, password, created_by, updated_by) VALUES
('alice', 'alice@example.com', 'hashed_password_1', 'alice', 'alice'),
('bob', 'bob@example.com', 'hashed_password_2', 'bob', 'bob'),
('charlie', 'charlie@example.com', 'hashed_password_3', 'charlie', 'charlie');
