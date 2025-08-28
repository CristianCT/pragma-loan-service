CREATE TABLE IF NOT EXISTS loan_type (
    id INTEGER PRIMARY KEY,
    description VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS status (
    id INTEGER PRIMARY KEY,
    description VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS loans (
    id VARCHAR(100) PRIMARY KEY DEFAULT (uuid()),
    user_id VARCHAR(100) NOT NULL,
    loan_type_id INTEGER NOT NULL,
    status_id INTEGER NOT NULL,
    term_in_months INTEGER,
    amount DOUBLE
);
/*
ALTER TABLE loans
ADD CONSTRAINT fk_loan_user
    FOREIGN KEY (user_id)
    REFERENCES users(id);

ALTER TABLE loans
    ADD CONSTRAINT fk_loan_type
        FOREIGN KEY (loan_type_id)
            REFERENCES loan_type(id)
            ON DELETE CASCADE;

ALTER TABLE loans
ADD CONSTRAINT fk_status
    FOREIGN KEY (status_id)
    REFERENCES status(id)
    ON DELETE CASCADE;

INSERT INTO loan_type (id, description)
VALUES (1, 'Personal'),
    (2, 'Mortgage'),
    (3, 'Auto');

INSERT INTO status (id, description)
VALUES (1, 'Pending Review'),
    (2, 'Reviewed'),
    (3, 'Approved'),
    (4, 'Rejected');*/