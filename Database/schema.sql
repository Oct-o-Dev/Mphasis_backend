

-- =========================
-- TABLE 1: USERS
-- =========================

CREATE TABLE user_entity (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    phone_no VARCHAR(15),
    email_id VARCHAR(100) UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role VARCHAR(20) NOT NULL  -- e.g., CUSTOMER, CRO, ADMIN
);

-- =========================
-- TABLE 2.1: REQUESTS
-- =========================

CREATE TABLE requests (
    ticket_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    subcategory VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
    date_of_submission TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_of_update TIMESTAMP,

    CONSTRAINT fk_requests_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON UPDATE CASCADE
);

-- =========================
-- TABLE 2.2: SERVICES
-- =========================

CREATE TABLE services (
    service_id SERIAL PRIMARY KEY,
    ticket_id INT NOT NULL,
    service_type VARCHAR(30) NOT NULL,  -- COMMENT, STATUS_CHANGE, etc.
    comment TEXT,
    old_status VARCHAR(30),
    new_status VARCHAR(30),
    date_of_service TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cro_id INT NOT NULL,

    CONSTRAINT fk_services_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES requests(ticket_id)
        ON UPDATE CASCADE,

    CONSTRAINT fk_services_cro
        FOREIGN KEY (cro_id)
        REFERENCES users(user_id)
        ON UPDATE CASCADE
);

-- =========================
-- TABLE 3.1: DEBIT CARDS
-- =========================

CREATE TABLE debit_cards (
    debit_card_number VARCHAR(20) PRIMARY KEY,
    user_id INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    CONSTRAINT fk_cards_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON UPDATE CASCADE
);

-- =========================
-- INDEXES (HIGHLY RECOMMENDED)
-- =========================

CREATE INDEX idx_requests_user_id ON requests(user_id);
CREATE INDEX idx_services_ticket_id ON services(ticket_id);
CREATE INDEX idx_services_cro_id ON services(cro_id);

-- =========================
-- TABLE 2.1: REQUESTS
-- =========================

CREATE TABLE requests (
    ticket_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    subcategory VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
    date_of_submission TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_of_update TIMESTAMP,

    CONSTRAINT fk_requests_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON UPDATE CASCADE
);

-- =========================
-- TABLE 2.2: SERVICES
-- =========================

CREATE TABLE services (
    service_id SERIAL PRIMARY KEY,
    ticket_id INT NOT NULL,
    service_type VARCHAR(30) NOT NULL,  -- COMMENT, STATUS_CHANGE, etc.
    comment TEXT,
    old_status VARCHAR(30),
    new_status VARCHAR(30),
    date_of_service TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cro_id INT NOT NULL,

    CONSTRAINT fk_services_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES requests(ticket_id)
        ON UPDATE CASCADE,

    CONSTRAINT fk_services_cro
        FOREIGN KEY (cro_id)
        REFERENCES users(user_id)
        ON UPDATE CASCADE
);

-- =========================
-- TABLE 3.1: DEBIT CARDS
-- =========================

CREATE TABLE debit_cards (
    debit_card_number VARCHAR(20) PRIMARY KEY,
    user_id INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    CONSTRAINT fk_cards_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON UPDATE CASCADE
);

-- =========================
-- INDEXES (HIGHLY RECOMMENDED)
-- =========================

CREATE INDEX idx_requests_user_id ON requests(user_id);
CREATE INDEX idx_services_ticket_id ON services(ticket_id);
CREATE INDEX idx_services_cro_id ON services(cro_id);