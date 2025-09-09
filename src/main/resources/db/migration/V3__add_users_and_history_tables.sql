CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(100) UNIQUE,
    email VARCHAR(100) UNIQUE,
    name VARCHAR(100),
    image_url VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS history (
    id SERIAL PRIMARY KEY,
    user_id INT,
    content VARCHAR(200),
    day DATE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);