drop table if exists t_pm_expenses;
drop table if exists t_pm_expenses_type;
drop table if exists t_pm_level;
drop table if exists t_pm_level_cost;
drop table if exists t_pm_user_cost;
drop table if exists t_pm_user_level;
drop table if exists t_pm_project; 
drop table if exists t_pm_project_user;
drop table if exists t_pm_timesheet;


CREATE TABLE `t_pm_expenses` (
`expenses_id`  bigint NOT NULL AUTO_INCREMENT ,
`user_id`  bigint NOT NULL , 
`project_id`  bigint NOT NULL , 
`apply_date`  date NULL , 
`apply_date2`  date NULL , 
`invoice_date`  date NULL , 
`apply_content`  varchar(200) NULL ,
`invoice_amount`  decimal(8,2) NULL ,
`apply_amount`  decimal(8,2) NULL ,
`expenses_type`  bigint NULL ,
`expenses_status`  varchar(10) NULL ,
`operate_user`  varchar(100) NULL ,
`operate_time`  datetime NULL ,
`memo`  varchar(300) NULL ,
PRIMARY KEY (`expenses_id`)
) charset=utf8 ENGINE=InnoDB;

CREATE TABLE `t_pm_expenses_type` (
`type_id`  bigint NOT NULL AUTO_INCREMENT ,
`type_name`  varchar(200) NULL ,
PRIMARY KEY (`type_id`)
) charset=utf8 ENGINE=InnoDB;

CREATE TABLE `t_pm_level` (
`level_id`  bigint NOT NULL AUTO_INCREMENT ,
`level_name`  varchar(200) NULL ,
`level_code`  varchar(5) NULL ,
`operate_user`  varchar(100) NULL ,
`operate_time`  datetime NULL ,
`memo`  varchar(300) NULL ,
PRIMARY KEY (`level_id`)
) charset=utf8 ENGINE=InnoDB;

CREATE TABLE `t_pm_level_cost` (
`cost_id`  bigint NOT NULL AUTO_INCREMENT ,
`level_id`  bigint NOT NULL ,  
`begin_date`  date NULL , 
`cost`  decimal(8,2) NULL ,
`operate_user`  varchar(100) NULL ,
`operate_time`  datetime NULL , 
PRIMARY KEY (`cost_id`)
) charset=utf8 ENGINE=InnoDB;

CREATE TABLE `t_pm_user_cost` (
`cost_id`  bigint NOT NULL AUTO_INCREMENT ,
`user_id`  bigint NOT NULL ,  
`begin_date`  date NULL , 
`cost`  decimal(8,2) NULL ,
`operate_user`  varchar(100) NULL ,
`operate_time`  datetime NULL , 
PRIMARY KEY (`cost_id`)
) charset=utf8 ENGINE=InnoDB;

CREATE TABLE `t_pm_user_level` (
`user_level_id`  bigint NOT NULL AUTO_INCREMENT ,
`user_id`  bigint NOT NULL ,  
`level_id`  bigint NOT NULL ,  
`begin_date`  date NULL ,  
`operate_user`  varchar(100) NULL ,
`operate_time`  datetime NULL , 
PRIMARY KEY (`user_level_id`)
) charset=utf8 ENGINE=InnoDB;

CREATE TABLE `t_pm_project` (
`project_id`  bigint NOT NULL AUTO_INCREMENT ,
`pm_user_id`  bigint NOT NULL , 
`project_name`  varchar(200) NULL ,
`project_code`  varchar(20) NULL ,
`begin_date`  date NULL ,
`end_date`  date NULL ,
`budget`  decimal(10,2) NULL ,
`operate_user`  varchar(100) NULL ,
`operate_time`  datetime NULL ,
`memo`  varchar(300) NULL ,
PRIMARY KEY (`project_id`)
) charset=utf8 ENGINE=InnoDB;

CREATE TABLE `t_pm_project_user` (
`project_id`  bigint NOT NULL ,
`user_id`  bigint NOT NULL , 
PRIMARY KEY (`project_id`,`user_id`)
) charset=utf8 ENGINE=InnoDB; 

CREATE TABLE `t_pm_timesheet` (
`sheet_id`  bigint NOT NULL AUTO_INCREMENT ,
`user_id`  bigint NOT NULL , 
`project_id`  bigint NOT NULL , 
`ts_date`  date NULL , 
`ts_hour`  decimal(3,1) NULL ,
`ts_status`  varchar(10) NULL ,
`ts_cost`  decimal(8,2) NULL ,
`operate_user`  varchar(100) NULL ,
`operate_time`  datetime NULL , 
PRIMARY KEY (`sheet_id`)
) charset=utf8 ENGINE=InnoDB;
