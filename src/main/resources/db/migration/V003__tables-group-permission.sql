CREATE TABLE permission (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
)engine=InnoDB;

CREATE TABLE `group` (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL
)engine=InnoDB;

CREATE TABLE group_permission (
	group_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (group_id, permission_id)
)engine=InnoDB;

CREATE TABLE user_group (
	user_id BIGINT NOT NULL,
    group_id BIGINT NOT NULL,
    PRIMARY KEY(user_id, group_id)
)engine=InnoDB;

ALTER TABLE group_permission ADD CONSTRAINT fk_group_permission_permission FOREIGN KEY (permission_id) REFERENCES permission (id);
ALTER TABLE group_permission ADD CONSTRAINT fk_group_permission_group FOREIGN KEY (group_id) REFERENCES `group` (id);
ALTER TABLE user_group ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE user_group ADD CONSTRAINT fk_user_group_group FOREIGN KEY (group_id) REFERENCES `group` (id);