-- auto Generated on 2020-10-29
-- DROP TABLE IF EXISTS dog;
CREATE TABLE dog(
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
	`name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'name',
	birth DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'birth',
	salary DECIMAL (13,4) NOT NULL DEFAULT -1 COMMENT '工资',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'dog';