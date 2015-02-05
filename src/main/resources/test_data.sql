# Sample Users 
INSERT INTO `permission` (`id`, `description`, `name`) VALUES (7,'Permission to upload a CSV file.','UPLOAD_CSV'),(8,'Permission to view CSV data in the form of graphs.','VIEW_CSV_CHARTS'),(9,'Permission to manage users.','MANAGE_USER'),(10,'Permission to view CSV data in the form of graphs.','VIEW_CSV_CHARTS'),(11,'Permission to manage users.','MANAGE_USER'),(12,'Permission to upload a CSV file.','UPLOAD_CSV');
INSERT INTO `role` (`id`, `name`) VALUES (3,'ADMIN'),(4,'ADMIN');
INSERT INTO `user` (`id`, `email`, `name`, `password`) VALUES (3,'adfs','admin','admin'),(4,'adfs','TEST','FDADDA');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES (3,7),(3,8),(3,9),(4,10),(4,11),(4,12);
INSERT INTO `user_role` (`role_id`, `user_id`) VALUES (3,3),(4,4);


# Sample Network unit, Unit Config and unit image(data)

INSERT INTO `product_data` (`id`, `lim_capacitance`, `lim_capacitance_cm`, `lim_imbalance`, `lim_resistance`, `lim_resistance_cm`, `line_current`, `line_frequency`, `line_phase`, `line_voltage`, `status`, `time`, `type`, `vNetAddress`, `unit_project_id`) VALUES (1,'123','123',12,12,12,12,12,12,12,'2','2014-10-24T10:26:31',2,'1','123456');
INSERT INTO `unit_config` (`id`, `headers`, `method`, `remote_url`) VALUES (1,'Accept:application/json','POST','http://10.106.30.55/dataLog');
INSERT INTO `network_unit` (`project_id`, `channel`, `company_name`, `control_system`, `ip_address`, `is_alive`, `platform`, `unit_serial_no`, `unit_config_id`) VALUES ('123456','Test','Unilever','Test','10.106.30.59','','Test','121297',1);

