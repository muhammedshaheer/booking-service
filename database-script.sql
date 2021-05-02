-- booking.users definition

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_number` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `residential_city` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_6efs5vmce86ymf5q7lmvn2uuf` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO booking.users
(id, contact_number, created_date, deleted, email, first_name, gender, last_modified_date, last_name, residential_city, user_id)
VALUES(1, '+91-9847012346', '2021-05-02 01:38:29.822000000', 0, 'riajohn@gmail.com', 'Ria', 'FEMALE', '2021-05-02 01:38:29.822000000', 'John', 'Calicut', '0f8dc7ef-9b2f-490a-bd92-3c742d143d2f');
INSERT INTO booking.users
(id, contact_number, created_date, deleted, email, first_name, gender, last_modified_date, last_name, residential_city, user_id)
VALUES(2, '+91-9847012345', '2021-05-02 01:40:35.830000000', 0, 'ckshaheer2012@gmail.com', 'Muhammed', 'MALE', '2021-05-02 01:40:35.830000000', 'Shaheer', 'Kannur', '47a80dfe-9ff5-4a27-b5d7-ce95a5651de7');


-- booking.hotels definition

CREATE TABLE `hotels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `default_check_in_time` varchar(255) DEFAULT NULL,
  `default_check_out_time` varchar(255) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `facilities` varchar(255) DEFAULT NULL,
  `hotel_id` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

INSERT INTO booking.hotels
(id, created_date, default_check_in_time, default_check_out_time, deleted, description, facilities, hotel_id, last_modified_date, location, name)
VALUES(1, '2021-05-02 01:39:25.326000000', '12:00 PM', '11:00 AM', 0, 'Hotel Description 1', 'Parking,Wifi,Complementary Breakfast', 'cd115723-70cb-4e72-8c7f-a30672cb5774', '2021-05-02 01:39:25.326000000', 'Hotel Location 1', 'Hotel Name 1');


-- booking.rooms definition

CREATE TABLE `rooms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `basic_fare` bigint(20) DEFAULT NULL,
  `bed_type` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `facilities` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `number_of_adults` int(11) DEFAULT NULL,
  `number_of_children` int(11) DEFAULT NULL,
  `number_of_rooms` int(11) DEFAULT NULL,
  `room_id` varchar(255) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `tax_percentage` bigint(20) DEFAULT NULL,
  `hotel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_522j1g1kig0go3c1o8r8gvu7g` (`room_id`),
  KEY `FKp3inkpl37lxejlqd7lfx7cpc6` (`hotel`),
  CONSTRAINT `FKp3inkpl37lxejlqd7lfx7cpc6` FOREIGN KEY (`hotel`) REFERENCES `hotels` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

INSERT INTO booking.rooms
(id, basic_fare, bed_type, created_date, deleted, facilities, last_modified_date, number_of_adults, number_of_children, number_of_rooms, room_id, room_type, tax_percentage, hotel)
VALUES(1, 1000, 'STANDARD_DOUBLE', '2021-05-02 01:39:25.326000000', 0, 'Air Conditioner', '2021-05-02 01:39:25.326000000', 2, 1, NULL, '1e37685b-1a1b-4583-860d-9ad27be6f1e0', 'DOUBLE', 10, 1);
INSERT INTO booking.rooms
(id, basic_fare, bed_type, created_date, deleted, facilities, last_modified_date, number_of_adults, number_of_children, number_of_rooms, room_id, room_type, tax_percentage, hotel)
VALUES(2, 1300, 'QUEEN', '2021-05-02 01:39:25.326000000', 0, 'Air Conditioner', '2021-05-02 01:39:25.326000000', 2, 1, NULL, '25374591-2067-4751-85e1-029e0efa8cf4', 'DOUBLE', 10, 1);
INSERT INTO booking.rooms
(id, basic_fare, bed_type, created_date, deleted, facilities, last_modified_date, number_of_adults, number_of_children, number_of_rooms, room_id, room_type, tax_percentage, hotel)
VALUES(3, 1500, 'KING', '2021-05-02 01:39:25.326000000', 0, 'Air Conditioner', '2021-05-02 01:39:25.326000000', 2, 1, NULL, '29e865e1-bb0c-40fc-a786-dae7addb9d4d', 'DOUBLE', 10, 1);


-- booking.reviews definition

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `rating_value` double DEFAULT NULL,
  `review_id` varchar(255) DEFAULT NULL,
  `reviewed_date` datetime(6) DEFAULT NULL,
  `hotel` int(11) DEFAULT NULL,
  `reviewed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ma914jppvtiw66wl1r26jtjv` (`hotel`),
  KEY `FK7k2jqjfeof9hvds3j5b7rk53b` (`reviewed_by`),
  CONSTRAINT `FK6ma914jppvtiw66wl1r26jtjv` FOREIGN KEY (`hotel`) REFERENCES `hotels` (`id`),
  CONSTRAINT `FK7k2jqjfeof9hvds3j5b7rk53b` FOREIGN KEY (`reviewed_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

INSERT INTO booking.reviews
(id, comment, deleted, rating_value, review_id, reviewed_date, hotel, reviewed_by)
VALUES(1, 'Comment1', 0, 9.0, '0988dc48-3219-460e-94fa-6ab1438f3c25', '2021-05-02 01:40:01.607000000', 1, 1);
INSERT INTO booking.reviews
(id, comment, deleted, rating_value, review_id, reviewed_date, hotel, reviewed_by)
VALUES(2, 'Comment2', 0, 9.1, 'ea25266a-66fe-4f7a-b384-85191d23311c', '2021-05-02 01:40:10.014000000', 1, 1);
INSERT INTO booking.reviews
(id, comment, deleted, rating_value, review_id, reviewed_date, hotel, reviewed_by)
VALUES(3, 'Comment3', 0, 8.5, '53dac895-6109-4dd2-8ccd-e6cbed790afa', '2021-05-02 01:41:03.426000000', 1, 2);
INSERT INTO booking.reviews
(id, comment, deleted, rating_value, review_id, reviewed_date, hotel, reviewed_by)
VALUES(4, 'Comment4', 0, 8.1, 'a714eb57-4a2b-47d6-b722-5f36b069e6d1', '2021-05-02 01:41:19.662000000', 1, 2);
