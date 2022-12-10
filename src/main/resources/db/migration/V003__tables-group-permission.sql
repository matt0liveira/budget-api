CREATE TABLE permission (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
)engine=InnoDB;

CREATE TABLE profile (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL
)engine=InnoDB;

CREATE TABLE profile_permission (
	profile_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (profile_id, permission_id)
)engine=InnoDB;

CREATE TABLE user_profile (
	user_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    PRIMARY KEY(user_id, profile_id)
)engine=InnoDB;

ALTER TABLE profile_permission ADD CONSTRAINT fk_profile_permission_permission FOREIGN KEY (permission_id) REFERENCES permission (id);
ALTER TABLE profile_permission ADD CONSTRAINT fk_profile_permission_profile FOREIGN KEY (profile_id) REFERENCES profile (id);
ALTER TABLE user_profile ADD CONSTRAINT fk_user_profile_user FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE user_profile ADD CONSTRAINT fk_user_profile_profile FOREIGN KEY (profile_id) REFERENCES profile (id);