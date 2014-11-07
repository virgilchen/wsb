--create DB wsbd001 at server 192.168.0.160
CREATE SCHEMA `wsbd001` DEFAULT CHARACTER SET utf8 ;

SHOW CREATE DATABASE;

use `wsbd001`;


-- DROP TABLE `cust_demo_rt`;

--`wsbd001`.`cust_demo_rt`
CREATE TABLE `cust_demo_rt` (
  `psdo_cust_id` INT(11) NOT NULL AUTO_INCREMENT,
  `cust_code` VARCHAR(100) NULL,
  `cust_phone_no` BIGINT NOT NULL,
  `cust_name` VARCHAR(20) NULL,
  `cust_birthday` DATE NULL,
  `cust_home_address` VARCHAR(100) NULL,
  `cust_gender` CHAR(1) NULL,
  `other_contact_way` VARCHAR(100) NULL,
  `background` VARCHAR(1000) NULL,
  `company_info` VARCHAR(1000) NULL,
  `insurance_resource` VARCHAR(1000) NULL,
  `contact_person` VARCHAR(20) NULL,
  `cust_src` VARCHAR(20) NULL,
  `cust_industry_type` VARCHAR(20) NULL,
  `company_name` VARCHAR(100) NULL,
  `cust_job_title` VARCHAR(20) NULL,
  `relationship_network` VARCHAR(1000) NULL,
  `company_address` VARCHAR(100) NULL,
  `cust_age` VARCHAR(45) NULL,
  `member_idc` CHAR(1) NULL,
  `member_id` INT NULL,
  PRIMARY KEY (`psdo_cust_id`),
    -- INDEX `member_id_fk_idx` (`member_id` ASC),
    -- CONSTRAINT `member_id_fk`
    -- FOREIGN KEY (`member_id`)
    -- REFERENCES `business_rt` (`business_id`)
    -- ON DELETE NO ACTION
    -- ON UPDATE NO ACTION,
  UNIQUE INDEX `cust_key` (`psdo_cust_id` ASC))
ENGINE = InnoDB;



-- SELECT * FROM wsbd001.cust_demo_rt LIMIT 0, 1000



-- DROP TABLE `car_info_rt`;
-- `wsbd001`.`car_info_rt`

CREATE TABLE `car_info_rt` (
  `car_id` INT(11) NOT NULL AUTO_INCREMENT,
  `psdo_cust_id` INT NOT NULL,
  `car_no` VARCHAR(10) NULL,
  `car_district` VARCHAR(20) NULL,
  `car_band` VARCHAR(20) NULL,
  `car_type` VARCHAR(20) NULL,
  `car_color` VARCHAR(5) NULL,
  `car_pai_liang` VARCHAR(10) NULL,
  `car_framework_no` VARCHAR(50) NULL,
  `car_engine_no` VARCHAR(50) NULL,
  `car_init_register_date` DATE NULL,
  `car_miles` DECIMAL(8,2) NULL DEFAULT 0,
  PRIMARY KEY (`car_id`),
    -- INDEX `psdo_cust_id_fk_idx` (`member_id` ASC),
    -- CONSTRAINT `psdo_cust_id_fk`
    -- FOREIGN KEY (`psdo_cust_id`)
    -- REFERENCES `cust_demo_rt` (`psdo_cust_id`)
    -- ON DELETE NO ACTION
    -- ON UPDATE NO ACTION,
  INDEX `cust_key` (`psdo_cust_id` ASC),
  INDEX `car_key` (`car_no` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- drop table `order_rt`;
-- `wsbd001`.`order_rt`
CREATE TABLE `order_rt` (
  `order_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_no` VARCHAR(80),
  `order_init_time_stamp` TIMESTAMP NOT NULL,
  `order_init_staff_id` INT NULL,
  `psdo_cust_id` INT NULL,
  `order_cur_status` VARCHAR(10) NULL COMMENT 'C:Cancel/S:Started/I:Init',
  `order_remark` VARCHAR(1000) NULL,
  PRIMARY KEY (`order_id`),
  INDEX `order_key` (`order_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `order_prod_pack_rt`;
-- `wsbd001`.`order_prod_pack_rt`
CREATE TABLE `order_prod_pack_rt` (
  `order_prod_pack_id` BIGINT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL ,
  `prod_pack_id` BIGINT NULL,
  `no_of_order_prod_pack` SMALLINT NULL,
  `order_prod_pack_purchase_date` DATE NULL,
  `order_prod_pack_effect_date` DATE NULL,
  `order_prod_pack_expire_date` DATE NULL,
  `order_prod_pack_remark` VARCHAR(1000) NULL,
  PRIMARY KEY (`order_prod_pack_id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `order_prod_pack_event_rt`;
-- `wsbd001`.`order_prod_pack_event_rt`
CREATE TABLE `order_prod_pack_event_rt` (
  `event_id` BIGINT NOT NULL AUTO_INCREMENT,
  `event_time_stamp` TIMESTAMP NOT NULL,
  `order_id` BIGINT NULL,
  `prod_pack_id` BIGINT NULL,
  `prod_id` BIGINT NULL,
  `business_id` INT NULL,
  `procs_step_no` SMALLINT NULL,
  `event_type` SMALLINT NULL,
  `event_staff_id` INT NULL,
  `event_duration` DATETIME NULL,
  `event_status` VARCHAR(10) NULL,
  `event_remark` VARCHAR(1000) NULL,
  PRIMARY KEY (`event_id`),
  INDEX `event_key` (`event_id` ASC),
  INDEX `order_key` (`order_id` ASC),
  INDEX `prod_pack_key` (`prod_pack_id` ASC),
  INDEX `prod_key` (`prod_id` ASC),
  INDEX `business_key` (`business_id` ASC, `procs_step_no` ASC),
  INDEX `staff_key` (`event_staff_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `staff_demo_rt`;
-- `wsbd001`.`staff_demo_rt`
CREATE TABLE `staff_demo_rt` (
  `staff_id` INT NOT NULL AUTO_INCREMENT,
  `staff_status` VARCHAR(5) NULL,
  `staff_login_profile` VARCHAR(20) NOT NULL,
  `staff_login_pwd` VARCHAR(100) NOT NULL,
  `staff_name` VARCHAR(20) NULL,
  `staff_gender` VARCHAR(1) NULL,
  `staff_phone_no` BIGINT NULL,
  `staff_id_card` VARCHAR(30) NOT NULL,
  `staff_role_id` INT NULL,
  `staff_last_login_time` DATETIME NULL,
  PRIMARY KEY (`staff_id`),
  INDEX `staff_ik_key` (`staff_id` ASC),
  INDEX `staff_login_key` (`staff_login_profile` ASC),
  INDEX `staff_id_card_key` (`staff_id_card` ASC),
  INDEX `staff_role_id_key` (`staff_role_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- drop table `staff_role_demo_rt`;
-- `wsbd001`.`staff_role_demo_rt`
CREATE TABLE `staff_role_demo_rt` (
  `staff_role_id` INT NOT NULL AUTO_INCREMENT,
  --`staff_role_page_id` VARCHAR(100) NOT NULL,
  `staff_role_status` VARCHAR(5) NULL,
  `staff_role_name` VARCHAR(20) NULL,
  `staff_role_remark` VARCHAR(500) NULL,
  `staff_role_org_id` INT NOT NULL,
  PRIMARY KEY (`staff_role_id`),
  INDEX `role_id_key` (`staff_role_id` ASC),
  INDEX `staff_role_org_key` (`staff_role_org_id` ASC),
  INDEX `role_page_org_key` (`staff_role_id` ASC, `staff_role_org_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `role_page_rt`;
CREATE TABLE `role_page_rt` (
  `staff_role_id` INT NOT NULL ,
  `page_id` INT NOT NULL ,
  PRIMARY KEY (`staff_role_id`, `page_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- drop table `company_org_rt`;
-- `wsbd001`.`company_org_rt` 
CREATE TABLE `company_org_rt` (
  `org_id` INT NOT NULL AUTO_INCREMENT,
  `org_id_upper` INT NOT NULL,
  `org_code` VARCHAR(20) NULL,
  `org_level` SMALLINT NULL,
  `org_name` VARCHAR(20) NULL,
  `org_owner_staff` VARCHAR(10) NULL,
  `org_province` VARCHAR(10) NULL,
  `org_city` VARCHAR(10) NULL,
  `org_owner_phone_no` BIGINT NULL,
  `org_remark` VARCHAR(1000) NULL,
  PRIMARY KEY (`org_id`),
  INDEX `org_id_key` (`org_id` ASC))
ENGINE = InnoDB
-- CHANGE COLUMN `org_id` `org_id` INT(11) NOT NULL AUTO_INCREMENT ;
DEFAULT CHARACTER SET = utf8;

-- drop table `product_rt`;
-- `wsbd001`.`product_rt`
CREATE TABLE `product_rt` (
  `prod_id` INT NOT NULL AUTO_INCREMENT,
  `prod_name` VARCHAR(50) NULL,
  `prod_desc` VARCHAR(1000) NULL,
  `business_id` INT NULL,
  `prod_quantity` INT NULL,
  `prod_unit` VARCHAR(1) NULL,
  `prod_picture` VARCHAR(100) NULL,
  `product_original_price` DECIMAL(12,2) NULL,
  `prodcut_selling_price` DECIMAL(12,2) NULL,
  `no_of_product_effective_day` SMALLINT NULL,
  `no_of_product_effective_mth` SMALLINT NULL,
  `no_of_day_remind_advance` SMALLINT NULL,
  `no_of_mth_remind_advance` SMALLINT NULL,
  PRIMARY KEY (`prod_id`),
  INDEX `business_id_key` (`business_id` ASC),
  INDEX `prod_id_key` (`prod_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `product_pack_rt`;
-- `wsbd001`.`product_pack_rt`
CREATE TABLE `product_pack_rt` (
  `prod_pack_id` INT NOT NULL AUTO_INCREMENT,
  `prod_pack_name` VARCHAR(50) NULL,
  `prod_pack_owner` INT NULL,
  `prod_pack_picture` VARCHAR(100) NULL,
  `prod_pack_orignal_price` DECIMAL(12,2) NULL,
  `prod_pack_selling_price` DECIMAL(12,2) NULL,
  PRIMARY KEY (`prod_pack_id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- drop table `product_pack_detail_rt`;
-- `wsbd001`.`product_pack_detail_rt`
CREATE TABLE `product_pack_detail_rt` (
  `prod_pack_id` INT NOT NULL ,
  `prod_id` INT NOT NULL ,
  `quantity` INT NULL,
  PRIMARY KEY (`prod_pack_id`, `prod_id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `recommendation_inventory_dt`;
-- `wsbd001`.`recommendation_inventory_dt`
CREATE TABLE `recommendation_inventory_dt` (
  `recmdt_id` BIGINT NOT NULL,
  `recmdt_code` VARCHAR(30) NULL,
  `recmdt_name` VARCHAR(30) NULL,
  `recmdt_status` VARCHAR(10) NULL,
  `recmdt_remark` VARCHAR(1000) NULL,
  `recmdt_detail` VARCHAR(2000) NULL,
  `recmdt_condition_operator` VARCHAR(5) NULL,
  `recmdt_condition_no` SMALLINT NULL,
  `recmdt_type` VARCHAR(10) NULL,
  `recmdt_key` VARCHAR(30) NULL,
  `recmdt_operator` VARCHAR(10) NULL,
  `recmdt_value` VARCHAR(100) NULL,
  PRIMARY KEY (`recmdt_id`),
  INDEX `recmdt_id_key` (`recmdt_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `recommendation_engine_dt`;
-- `wsbd001`.`recommendation_engine_dt`
CREATE TABLE `recommendation_engine_dt` (
  `recmdt_engine_id` BIGINT NOT NULL,
  `recmdt_psdo_cust_id` INT NOT NULL,
  `recmdt_engine_priority` SMALLINT NOT NULL,
  `recmdt_egnine_detail` VARCHAR(2000) NULL,
  PRIMARY KEY (`recmdt_engine_id`, `recmdt_psdo_cust_id`, `recmdt_engine_priority`),
  INDEX `recmdt_engine_id_key` (`recmdt_engine_id` ASC),
  INDEX `recmdt_psdo_cust_id` (`recmdt_psdo_cust_id` ASC),
  INDEX `recmdt_cust_priority_key` (`recmdt_engine_id` ASC, `recmdt_engine_priority` ASC, `recmdt_psdo_cust_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `order_procs_rt` ;
-- `wsbd001`.`order_procs_rt` 
CREATE TABLE `order_procs_rt` (
  `business_id` INT NOT NULL,
  `procs_step_no` SMALLINT NOT NULL,
  `procs_step_name` VARCHAR(10) NULL,
  `procs_staff_role_id` INT NULL,
  PRIMARY KEY (`business_id`, `procs_step_no`),
  INDEX `business_id_key` (`business_id` ASC),
  INDEX `procs_key` (`business_id` ASC, `procs_step_no` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `member_info_rt`;
-- `wsbd001`.`member_info_rt`
CREATE TABLE `member_info_rt` (
  `member_id` INT NOT NULL,
  `psdo_cust_id` INT NULL,
  `member_login_id` VARCHAR(50) NOT NULL,
  `member_login_pwd` VARCHAR(100) NOT NULL,
  `member_status` VARCHAR(5) NULL,
  `member_expiry_date` DATE NULL,
  `member_type` VARCHAR(5) NULL,
  `member_internal_level` VARCHAR(5) NULL,
  `member_duration` SMALLINT NULL,
  `member_expectation` VARCHAR(1000) NULL,
  `member_create_time` TIMESTAMP NULL,
  PRIMARY KEY (`member_id`),
  INDEX `member_key` (`member_id` ASC),
  INDEX `cust_key` (`psdo_cust_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `member_appl_form_rt`;
CREATE TABLE `member_appl_form_rt` (
  `member_id` INT NOT NULL,
  `member_appl_form_type` VARCHAR(20) NOT NULL,
  `member_appl_form_question_id` SMALLINT NOT NULL,
  `member_appl_form_answer_id` INT NULL,
  PRIMARY KEY (`member_id`, `member_appl_form_type`, `member_appl_form_question_id`),
  INDEX `member_id_key` (`member_id` ASC));
  
-- drop table `appl_form_dim`;
CREATE TABLE `appl_form_dim` (
  `appl_form_type` VARCHAR(20) NOT NULL,
  `appl_form_question_id` SMALLINT NULL,
  `appl_form_question_name` VARCHAR(500) NULL,
  `appl_form_answer_id` INT NULL,
  `appl_form_answer_name` VARCHAR(50) NULL,
  PRIMARY KEY (`appl_form_type`),
  INDEX `appl_form_ques_key` (`appl_form_question_id` ASC, `appl_form_type` ASC));



-- drop table `page_id_dim`;
-- `wsbd001`.`page_id_dim`
CREATE TABLE `page_id_dim` (
  `page_id` int(5) NOT NULL,
  `page_name` VARCHAR(100) NULL,
  PRIMARY KEY (`page_id`),
  INDEX `page_key` (`page_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `business_rt`;
-- `wsbd001`.`business_rt` 
CREATE TABLE `business_rt` (
  `business_id` INT NOT NULL AUTO_INCREMENT,
  `business_name` VARCHAR(20) NULL,
  `business_id_upper` INT NULL,
  `business_name_upper` VARCHAR(20) NULL,
  PRIMARY KEY (`business_id`),
  INDEX `business_key` (`business_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- drop table `member_need_rt` ;
-- `wsbd001`.`member_need_rt` 
CREATE TABLE `member_need_rt` (
  `member_need_id` INT NOT NULL,
  `member_id` INT NULL,
  `member_need_name` VARCHAR(20) NULL,
  `member_need_desc` VARCHAR(1000) NULL,
  `prodcut_picture` VARCHAR(100) NULL,
  PRIMARY KEY (`member_need_id`),
  INDEX `member_need_id_key` (`member_need_id` ASC),
  INDEX `member_id_key` (`member_id` ASC));



CREATE TABLE `notice_rt`(
  `notice_id` INT NOT NULL AUTO_INCREMENT,
  `notice_subject` VARCHAR(100) NULL,
  `notice_timestamp` TIMESTAMP NULL,
  `notice_content` VARCHAR(2000) NULL,
  PRIMARY KEY (`notice_id`),
  INDEX `notice_key` (`notice_id` ASC));
  
  
  