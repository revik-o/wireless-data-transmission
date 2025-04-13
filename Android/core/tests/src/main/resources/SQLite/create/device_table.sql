CREATE TABLE Device (
    id INTEGER PRIMARY KEY,
    deviceName TEXT NOT NULL,
    operatingSystem TEXT NOT NULL,
    ipV4Address TEXT NOT NULL,
    additionDate TEXT DEFAULT CURRENT_TIMESTAMP
);