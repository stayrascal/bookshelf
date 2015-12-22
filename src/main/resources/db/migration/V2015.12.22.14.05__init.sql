DROP TABLE IF EXISTS book;

CREATE TABLE book(
  isbn VARCHAR(30) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  author VARCHAR(50) NOT NULL,
  price DOUBLE NOT NULL
);

INSERT INTO book(isbn, name, author, price) VALUES ('9780201485677', 'Refactoring', 'Martin Fowler', 64.99);
INSERT INTO book(isbn, name, author, price) VALUES ('9780132350884', 'Clean Code', 'Robert C. Martin', 35.44);
INSERT INTO book(isbn, name, author, price) VALUES ('9780131429017', 'The Art of UNIX Programming', 'Eric S. Raymond', 39.99);
