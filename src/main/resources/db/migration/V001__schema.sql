CREATE DATABASE IF NOT EXISTS budget;
USE budget;

CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `balance` double NOT NULL,
    `email` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `registration_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `category` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `color` varchar(255) NOT NULL,
    `description` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `transaction` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code` varchar(255) NOT NULL,
    `value` double NOT NULL, 
    `date` date NOT NULL,
    `creation_date` datetime(6) NOT NULL,
    `description` varchar(255) NOT NULL,
    `type` varchar(255) NOT NULL,
    `category_id` bigint DEFAULT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKgik7ruym8r1n4xngrclc6kiih` (`category_id`),
    KEY `FKsg7jp0aj6qipr50856wf6vbw1` (`user_id`),
    CONSTRAINT `FKgik7ruym8r1n4xngrclc6kiih` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
    CONSTRAINT `FKsg7jp0aj6qipr50856wf6vbw1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;