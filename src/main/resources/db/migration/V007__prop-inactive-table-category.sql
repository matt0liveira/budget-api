ALTER TABLE category ADD COLUMN inactive TINYINT(1) NOT NULL;

UPDATE category SET inactive = false;