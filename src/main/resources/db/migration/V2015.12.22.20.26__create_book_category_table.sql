DROP TABLE IF EXISTS category;
CREATE TABLE category(
  code VARCHAR(10) PRIMARY KEY,
  name VARCHAR(30) NOT NULL UNIQUE,
  description VARCHAR(255) NULL
);

INSERT INTO category(code, name) VALUES ('G623', 'IT'), ('G624', 'Mathematics'), ('G625', 'Philosophy');
