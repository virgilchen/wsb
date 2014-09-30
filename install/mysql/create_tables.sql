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
