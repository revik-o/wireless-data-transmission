CREATE TABLE TrustedDevice (
    id INTEGER PRIMARY KEY,
    deviceId INTEGER NOT NULL,
    title TEXT NOT NULL,
    additionDate TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (deviceId) REFERENCES DeviceModel(id)
);