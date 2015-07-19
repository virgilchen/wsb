/*==============================================================*/
/* Table: CFG_DICT                                              */
/*==============================================================*/
create table cfg_dict  (
   id                 bigint(12)                      not null AUTO_INCREMENT,
   pro_dict_id        bigint(12)                     default 0,
   domain_            VARCHAR(64),
   code_              VARCHAR(64),
   name_              text(1000),
   desc_              VARCHAR(256),
   flag_              CHAR(1),
   status_            CHAR(1),
   order_             int(8),
   ext_s1               varchar(250),
   ext_s2               varchar(250),
   ext_s3               varchar(250),
   ext_s4               varchar(250),
   ext_s5               varchar(250),
   constraint PK_CFG_DICT primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*==============================================================*/
/* Table: CFG_Message                                         */
/*==============================================================*/
create table cfg_message  (
   ID                   bigint(12)                      not null AUTO_INCREMENT,
   CODE_                bigint(12)                     default 0,
   VALUE_               VARCHAR(1024),
   VALUE_en               VARCHAR(1024),
   constraint PK_CFG_MESSAGE primary key (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*==============================================================*/
/* Table: cfg_sequence                                          */
/*==============================================================*/
create table cfg_sequence  (
   name         varchar(32)             not null,
   value        bigint(12)              default 1,
   constraint PK_cfg_sequence primary key (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*==============================================================*/
/* Table: sys_event_log                                       */
/*==============================================================*/
create table sys_event_log  (
   id                 bigint                          not null  auto_increment,
   event_type_code    VARCHAR(8)                     default '',
   desc_              VARCHAR(256),
   user_id            bigint(12)                     default 0,
   user_name          VARCHAR(250),
   thread_id          bigint(12)                     default 0,
   biz_id             bigint(12)                     default 0,
   biz_data           VARCHAR(2500),
   version_id         bigint,
   record_status      char(1),
   created_by         varchar(50),
   created_on         datetime,
   update_by          varchar(50),
   update_on          datetime,
   ext_c1             varchar(150),
   ext_c2             varchar(150),
   ext_c3             varchar(150),
   ext_c4             varchar(150),
   ext_c5             VARCHAR(2500),
   ext_n1             decimal(12,2),
   ext_n2             decimal(12,2),
   constraint PK_SYS_EVENT_LOG primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: biz_assets_holding ，客户资产持有表                                   */
/*==============================================================*/
create table biz_assets_holding  (
   id                 bigint(12) not null AUTO_INCREMENT,
   customer_id        bigint(12)  comment '客户ID',
   assets_type        int(5) comment '资产类型，对应字典表Biz.Assets.Type，1:产品',
   pro_assets_id      bigint(12) comment '资产ID，当assets_type=1，本字段为商品ID',
   assets_id          bigint(12) comment '资产ID，当assets_type=1，本字段为产品ID',
   assets_unit        int(5) comment '资产单位，对应字典表Biz.Assets.Unit，1：件',
   order_id           bigint(12) comment '资产产生订单源ID',
   
   total_amount       decimal(12,2) default '0' NOT NULL comment '总量',
   used_amount        decimal(12,2) default '0' NOT NULL comment '已使用',
   pending_amount     decimal(12,2) default '0' NOT NULL comment '预扣',
   remain_amount      decimal(12,2) default '0' NOT NULL comment '剩余',
   
   effect_date        datetime comment '资产生效时间',
   expire_date        datetime comment '资产失效时间',
   
   version_id         bigint,
   record_status      char(1),
   created_by         varchar(50),
   created_on         datetime,
   updated_by          varchar(50),
   updated_on          datetime,
   
   constraint PK_biz_assets_holding primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: biz_assets_transaction ，客户资产交易表                                         */
/*==============================================================*/
create table biz_assets_transaction (
   id                 bigint(12) not null AUTO_INCREMENT,
   assets_holding_id  bigint(12)  comment '资产持有记录ID',
   order_id              bigint(12)  comment '订单记录ID',
   transaction_type   int(5)  comment '交易类型，对应字典表Biz.Assets.TransactionType，1：订购，-1：使用',
   amount             decimal(12,2)  comment '资产交易量',
   
   version_id         bigint,
   record_status      char(1),
   created_by         varchar(50),
   created_on         datetime,
   updated_by          varchar(50),
   updated_on          datetime,
   
   constraint PK_biz_assets_transaction primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table biz_document
(
   id                   bigint not null auto_increment comment '标识',
   order_id             bigint comment '订单ID',
   company_id             bigint comment '公司ID',
   folder_name          varchar(6) comment 'yyyymm作为文档的文件夹，不可改',
   file_uuid            varchar(36) comment '文件的uuid，作为系统文件名称，不可改',
   document_name        varchar(150) comment '文档名称',
   document_type        varchar(1) comment '文档类型',
   desc_                varchar(500) comment '文档描述',
   record_status        char(1) comment '记录状态，用于逻辑删除
            A-Active
            C-Cancel',
   order_               int comment '记录顺序',
   version_id           bigint comment '版本号，保留，数据更新时，用于“乐观锁”，放置时间的long(java)形式',
   created_by           varchar(50) comment '创建的操作员Login_ID',
   created_on           datetime comment '创建的时间',
   updated_by           varchar(50) comment '更新的操作员Login_ID',
   updated_on           datetime comment '更新的时间，同时用作version_id',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `wsbd001`.`wf_key_info` (
  `wf_key_info_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wf_key_info_type` VARCHAR(2) NULL DEFAULT NULL COMMENT '关键信息类型：0单选，1多选，2填空',
  `wf_key_info_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '关键信息名称',
  `isRequired` VARCHAR(2) NULL DEFAULT NULL COMMENT '是否必填：0是，1不是',
  `isActive` VARCHAR(2) NULL DEFAULT NULL COMMENT '是否有效：0是，1不是',
  `version_id` BIGINT(20) NULL DEFAULT NULL COMMENT '版本号，保留，数据更新时，用于“乐观锁”，放置时间的long(java)形式',
  `created_by` VARCHAR(50) NULL DEFAULT NULL COMMENT '创建的操作员Login_ID',
  `created_on` DATETIME NULL DEFAULT NULL COMMENT '创建的时间',
  `updated_by` VARCHAR(50) NULL DEFAULT NULL COMMENT '更新的操作员Login_ID',
  `updated_on` DATETIME NULL DEFAULT NULL COMMENT '更新的时间，同时用作version_id',
  PRIMARY KEY (`wf_key_info_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '环节管理:关键信息';

CREATE TABLE `wsbd001`.`wf_key_info_details` (
  `wf_key_info_details_id` INT(11) NOT NULL COMMENT '主键',
  `wf_key_info_id` INT(11) NOT NULL COMMENT '外键关联关键信息主表',
  `detail_name` VARCHAR(100) NULL COMMENT '选项名称',
  `can_input` VARCHAR(2) NULL DEFAULT NULL COMMENT '可否填空：0可，1否',
  `detail_val` VARCHAR(1000) NULL DEFAULT NULL COMMENT '选项值',
  `detail_sn` INT(11) NULL DEFAULT 0 COMMENT '选项顺序',
  `max_length` INT(11) NULL DEFAULT 0 COMMENT '最大字数',
  `min_length` INT(11) NULL DEFAULT 0 COMMENT '最小字数',
  `isActive` VARCHAR(2) NULL DEFAULT NULL COMMENT '是否有效：0是，1否',
  `version_id` BIGINT(20) NULL DEFAULT NULL COMMENT '版本号，保留，数据更新时，用于“乐观锁”，放置时间的long(java)形式',
  `created_by` VARCHAR(50) NULL DEFAULT NULL COMMENT '创建的操作员Login_ID',
  `created_on` DATETIME NULL DEFAULT NULL COMMENT '创建的时间',
  `updated_by` VARCHAR(50) NULL DEFAULT NULL COMMENT '更新的操作员Login_ID',
  `updated_on` DATETIME NULL DEFAULT NULL COMMENT '更新的时间，同时用作version_id',
  PRIMARY KEY (`wf_key_info_details_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '关键信息明细表';

