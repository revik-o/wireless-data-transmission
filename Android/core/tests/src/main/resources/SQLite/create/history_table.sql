CREATE TABLE History (
    id INTEGER PRIMARY KEY,
    typeOfResource TEXT NOT NULL,
    shortFormData TEXT NOT NULL,
    commentFromSender TEXT,
    fromDeviceId INTEGER,
    action TEXT NOT NULL,
    date TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fromDeviceId) REFERENCES DeviceModel(id)
);