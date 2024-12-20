CREATE TABLE bank_user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(20),
    status INT,
    type INT
);

CREATE TABLE bank_account (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    account_number VARCHAR(255),
    account_type INT,
    account_balance DECIMAL(15, 2),
    account_status INT,
    currency VARCHAR(10),
    FOREIGN KEY (user_id) REFERENCES bank_user(user_id)
);
