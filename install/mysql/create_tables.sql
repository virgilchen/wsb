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


