
# Sample Users 
INSERT INTO `client_info` (`id`, `address`, `contact_no`, `contact_person`, `name`) VALUES (1,'Mumbai',813948773,'admin','Hiranandani');

insert into product_info(part_no, unit_serial_no, description) values ('2342', '1234', 'VLIFE MK1');

INSERT INTO `permission` (`id`, `description`, `name`) VALUES (7,'Permission to upload a CSV file.','UPLOAD_CSV'),(8,'Permission to view CSV data in the form of graphs.','VIEW_CSV_CHARTS'),(9,'Permission to manage users.','MANAGE_USER'),(10,'Permission to view CSV data in the form of graphs.','VIEW_CSV_CHARTS'),(11,'Permission to manage users.','MANAGE_USER'),(12,'Permission to upload a CSV file.','UPLOAD_CSV');

INSERT INTO `role` (`id`, `name`) VALUES (3,'ADMIN'),(4,'ADMIN');

INSERT INTO `user` ( `email`, `password`, `first_name`, `last_login`, `last_modified_by`, `last_modified_date`, `last_name`, `username`) VALUES ('admin@dms.com','admin','Admin',NULL,'admin',CURDATE(),'DMS','admin');

INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (3,7),(3,8),(3,9),(4,10),(4,11),(4,12);

INSERT INTO `user_role` (`role_id`, `user_id`) VALUES (3,3),(4,4);


