CREATE TABLE locomotive (
    id CHAR(36) PRIMARY KEY,
    number VARCHAR(20) NOT NULL,
    model VARCHAR(50) NOT NULL,
    UNIQUE (number)
);


CREATE TABLE train_composition (
    id CHAR(36) PRIMARY KEY,
    locomotive_id CHAR(36) NOT NULL,
    FOREIGN KEY (locomotive_id) REFERENCES locomotive (id)
    ON DELETE RESTRICT ON UPDATE CASCADE
);


CREATE TABLE train (
    id CHAR(36) PRIMARY KEY,
    train_composition_id CHAR(36) NOT NULL,
    number VARCHAR(20) NOT NULL,
    FOREIGN KEY (train_composition_id) REFERENCES train_composition (id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE (number)
);

CREATE INDEX idx_train_composition_id ON train (train_composition_id);


CREATE TABLE carriage (
    id CHAR(36) PRIMARY KEY,
    number VARCHAR(20) NOT NULL,
    train_composition_id CHAR(36),
    position TINYINT UNSIGNED,
    content_type ENUM('passenger', 'cargo'),
    capacity INT UNSIGNED,
    FOREIGN KEY (train_composition_id) REFERENCES train_composition (id)
    ON DELETE SET NULL ON UPDATE CASCADE,
    UNIQUE (number),
    UNIQUE (train_composition_id, position),
    CHECK (position IS NULL OR position > 0),
    CHECK (capacity IS NULL OR capacity > 0)
);


CREATE TABLE station (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL
);


CREATE TABLE line (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);


CREATE TABLE line_station (
    line_id CHAR(36) NOT NULL,
    station_id CHAR(36) NOT NULL,
    position TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (line_id, station_id),
    FOREIGN KEY (line_id) REFERENCES line (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (station_id) REFERENCES station (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (line_id, position),
    CHECK (position > 0)
);

CREATE INDEX idx_line_station_station ON line_station (station_id);


CREATE TABLE schedule_entry (
    id CHAR(36) PRIMARY KEY,
    train_id CHAR(36) NOT NULL,
    station_id CHAR(36) NOT NULL,
    arrival_time DATETIME,
    departure_time DATETIME,
    order_number TINYINT UNSIGNED NOT NULL,
    FOREIGN KEY (train_id) REFERENCES train (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (station_id) REFERENCES station (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (train_id, order_number),
    CHECK (
        arrival_time IS NULL
        OR departure_time IS NULL
        OR arrival_time <= departure_time
    )
);

CREATE INDEX idx_schedule_entry_station_id ON schedule_entry (station_id);
CREATE INDEX idx_schedule_entry_train_id ON schedule_entry (train_id);
CREATE INDEX idx_schedule_entry_departure_time ON schedule_entry (
    departure_time
);
CREATE INDEX idx_schedule_entry_arrival_time ON schedule_entry (arrival_time);
