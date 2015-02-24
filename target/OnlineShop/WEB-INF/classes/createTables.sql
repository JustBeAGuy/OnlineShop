CREATE DATABASE IF NOT EXISTS social;

USE social;

CREATE TABLE users (

  id BIGINT UNIQUE NOT NULL AUTO_INCREMENT,

  name VARCHAR(255) NOT NULL,
  sur_name VARCHAR(255) NOT NULL,
  type VARCHAR (255) NOT NULL DEFAULT 'user',

  PRIMARY KEY (id)
);


CREATE TABLE access (

  id int AUTO_INCREMENT NOT NULL,
  user_id BIGINT UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  pass VARCHAR (255) NOT NULL,
  user_type VARCHAR (255) NOT NULL,
  date_registration DATETIME NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE additional_info (

  id int AUTO_INCREMENT NOT NULL,
  user_id BIGINT UNIQUE NOT NULL,
  company VARCHAR (255),
  date_birthday DATE,
  sex VARCHAR (255),
  hobbies VARCHAR (255),

  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE locations (

  id int AUTO_INCREMENT NOT NULL,
  user_id BIGINT UNIQUE NOT NULL,
  country VARCHAR (255),
  city VARCHAR (255),

  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE skills (

  id int AUTO_INCREMENT NOT NULL,
  user_id BIGINT NOT NULL,
  name VARCHAR (255),
  level VARCHAR (255),

  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  UNIQUE KEY name (name, user_id)
);

CREATE TABLE messages (

  id int AUTO_INCREMENT NOT NULL,
  author_id BIGINT NOT NULL,
  receiver_id BIGINT NOT NULL,
  date DATETIME NOT NULL,
  text VARCHAR (255),
  status VARCHAR (255) NOT NULL,
  is_received BOOLEAN NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (author_id) REFERENCES users(id),
  FOREIGN KEY (receiver_id) REFERENCES users(id)
);


CREATE TABLE friends (

  id int AUTO_INCREMENT NOT NULL,
  initiate_user_id BIGINT NOT NULL,
  confirm_user_id BIGINT NOT NULL,
  date_initiated DATETIME NOT NULL,
  date_confirmed DATETIME,
  status VARCHAR (255),

  PRIMARY KEY (id),
  FOREIGN KEY (initiate_user_id ) REFERENCES users(id),
  FOREIGN KEY (confirm_user_id ) REFERENCES users(id),
  UNIQUE KEY initiate_user_id (initiate_user_id, confirm_user_id)
);

CREATE TABLE contacts (

  id int AUTO_INCREMENT NOT NULL,
  user_id BIGINT UNIQUE NOT NULL,
  phone VARCHAR (255),
  email VARCHAR (255),
  skype VARCHAR (255),
  github VARCHAR (255),

  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE users AUTO_INCREMENT = 100000;

# INSERT INTO users(id, name, sur_name) values (0000001, 'test1', 'test1');


# CREATE TABLE user_types (
#
#   id int AUTO_INCREMENT NOT NULL,
#   type VARCHAR (255) NOT NULL,
#   description VARCHAR (255),
#
#   PRIMARY KEY (id)
# );

# CREATE TABLE registration_dates (
#
#   id int AUTO_INCREMENT NOT NULL,
#   user_id BIGINT UNIQUE NOT NULL,
#   date_registration TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
#
#   PRIMARY KEY (id),
#   FOREIGN KEY (user_id) REFERENCES users(id)
# );

