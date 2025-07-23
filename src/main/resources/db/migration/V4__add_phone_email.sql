ALTER TABLE passenger
    ADD email VARCHAR(255) NULL;

ALTER TABLE passenger
    ADD name VARCHAR(255) NULL;

ALTER TABLE passenger
    ADD password VARCHAR(255) NULL;

ALTER TABLE passenger
    ADD phone_number VARCHAR(255) NULL;

ALTER TABLE passenger
    MODIFY name VARCHAR (255) NOT NULL;

ALTER TABLE passenger
    MODIFY password VARCHAR (255) NOT NULL;

ALTER TABLE passenger
    MODIFY phone_number VARCHAR (255) NOT NULL;

ALTER TABLE passenger
    ADD CONSTRAINT uc_passenger_email UNIQUE (email);

ALTER TABLE passenger
DROP
COLUMN first_name;
