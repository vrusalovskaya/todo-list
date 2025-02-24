DROP DATABASE IF EXISTS todo_list_db;

CREATE DATABASE todo_list_db;

USE todo_list_db;

CREATE TABLE Tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    completed BOOLEAN DEFAULT FALSE
);