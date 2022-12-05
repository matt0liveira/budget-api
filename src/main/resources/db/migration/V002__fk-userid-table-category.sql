ALTER TABLE category ADD column user_id BIGINT NOT NULL;
UPDATE category SET user_id = 1;
ALTER TABLE category ADD CONSTRAINT fk_category_user FOREIGN KEY(user_id) REFERENCES user (id);