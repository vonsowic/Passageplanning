CREATE TABLE  (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) UNIQUE,
    characteristic VARCHAR(500),
    ukc FLOAT,
    latitude DECIMAL(2, 9),
    longitude DECIMAL(3, 9)
);
