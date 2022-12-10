CREATE TABLE photo_user (
    user_id BIGINT NOT NULL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(255) NOT NULL,
    size BIGINT NOT NULL,

    CONSTRAINT fk_photo_user_user FOREIGN KEY (user_id) REFERENCES user (id)
)ENGINE=InnoDB default CHARSET=utf8;