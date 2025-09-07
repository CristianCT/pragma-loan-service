CREATE TABLE IF NOT EXISTS loan_type (
    id INTEGER PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    minimum_amount DOUBLE NOT NULL,
    maximum_amount DOUBLE NOT NULL,
    tax_rate DOUBLE NOT NULL,
    automatic_approval BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS status (
    id INTEGER PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
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

INSERT INTO loan_type (id, name, description, minimum_amount, maximum_amount, tax_rate, automatic_approval)
VALUES (1, 'PERSONAL', 'Personal Loan', 1000, 50000, 5.5, TRUE),
    (2, 'MORTGAGE', 'Mortgage Loan', 50000, 500000, 3.8, FALSE),
    (3, 'AUTO', 'Auto Loan', 5000, 100000, 4.2, TRUE),
    (4, 'STUDENT', 'Student Loan', 2000, 20000, 2.5, TRUE);

INSERT INTO status (id, name, description)
VALUES (1, 'PENDING', 'Pending Approval'),
    (2, 'APPROVED', 'Approved Loan'),
    (3, 'REJECTED', 'Rejected Loan'),
    (4, 'MANUAL_REVIEW', 'Under Manual Review');
