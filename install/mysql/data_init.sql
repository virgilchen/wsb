/* system */
insert into cfg_message(code_, value_) 
values('1', '[{0}]输入不能为空！' );

insert into cfg_message(code_, value_) 
values('95', '已经成功发送信息！' );
insert into cfg_message(code_, value_) 
values('96', '操作成功！' );
insert into cfg_message(code_, value_) 
values('97', '已经成功创建记录！' );
insert into cfg_message(code_, value_) 
values('98', '已经成功更新记录！' );
insert into cfg_message(code_, value_) 
values('99', '已经成功删除记录！' );

/* 100~120 for DB*/
insert into cfg_message(code_, value_) 
values('100', '数据库操作异常！' );

insert into cfg_message(code_, value_) 
values('101', '需要操作的记录已经被其它程序更新，请重新查询数据后再做操作！[表名：{0}, {1}]' );


insert into cfg_message(code_, value_) 
values('102', '编码已经被使用，保存失败，请使用其它编码后再进行保存！' );

insert into cfg_message(code_, value_) 
values('103', '数据为系统保留数据，不能修改与删除！' );

insert into cfg_message(code_, value_) 
values('104', '数据为系统保留数据，不能删除！' );


insert into cfg_message(code_, value_) 
values('1000', '未登陆或已经超时，请重新登陆！' );

insert into cfg_message(code_, value_) 
values('1001', '当前用户没有操作权限！' );



insert into cfg_message(code_, value_) 
values('1106', '用户已经成功登录！' );

insert into cfg_message(code_, value_) 
values('1107', '用户已经成功更改密码！' );

insert into cfg_message(code_, value_) 
values('1108', '密码为空，更改密码失败！' );

insert into cfg_message(code_, value_) 
values('1109', '原密码不正确，更改密码失败！' );

insert into cfg_message(code_, value_) 
values('1110', '用户登录账号已经被使用，请使用其它登录账号！' );

insert into cfg_message(code_, value_) 
values('1111', '用户被冻结，不能进行任何操作！' );

insert into cfg_message(code_, value_) 
values('1112', '用户非激活状态，不能登录！' );

insert into cfg_message(code_, value_) 
values('1113', '用户已经成功登出！' );


INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('1005', '1005', '用户不存在！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('1006', '1006', '密码错误！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('1007', '1007', '用户已有业务操作，不能被删除！');


INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11001', '11001', '本环节为业务最终环节，未能找到下一环节！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11002', '11002', '任务已被其它业务员领取！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11003', '11003', '当前环节由其业务员处理！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11004', '11004', '业务员[{0}]没有业务环节所需要的角色权限[{1}]！');
 

INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('12001', '12001', '父业务不存在，本操作无效！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('12002', '12002', '子业务存在，本操作无效！');


INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('13001', '13001', '父机构不存在，本操作无效！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('13002', '13002', '子机构存在，本操作无效！');



/*  Staff init  */
INSERT INTO `staff_demo_rt` (`staff_id`, `staff_status`, `staff_login_profile`, `staff_login_pwd`, `staff_name`, `staff_gender`, `staff_id_card`) VALUES ('1', '有效', 'admin', 'C4CA4238A0B923820DCC509A6F75849B', '系统管理员', '男', '440181198001010001');

delete from cfg_dict where id<>9898986565104;
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1, 0, 'Cache_Sql', 'Dict', 'subTable=domain_&isSCM=Y', 'select code_ as id, name_ , desc_ , domain_, ext_s1, ext_s2, ext_s3,ext_s4,ext_s5 from cfg_dict where domain_!=''Cache_Sql'' and status_ = ''A'' order by domain_,order_ ', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-4, 0, 'Cache_Sql', 'Message', '', 'select code_ as id, value_ from cfg_message ', null, 'A', 4, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-5, 0, 'Cache_Sql', 'BusinessUpper', 'isSCM=Y', 'select business_id as id, business_name, business_id_upper, business_name_upper from business_rt  where business_id_upper is null', null, 'A', 5, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-6, 0, 'Cache_Sql', 'Role', 'isSCM=Y', 'select staff_role_id as id, staff_role_name from staff_role_demo_rt', null, 'A', 6, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-7, 0, 'Cache_Sql', 'Org', 'isSCM=Y', 'select org_id as id, o.* from company_org_rt o', null, 'A', 7, null, null, null, null, null);



insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-501, 0, 'CM.gender', 'F', '男', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-502, 0, 'CM.gender', 'M', '女', '', null, 'A', 2, null, null, null, null, null);

insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1001, 0, 'product.unit', 'A', '个', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1002, 0, 'product.unit', 'B', '打', '', null, 'A', 2, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1003, 0, 'product.unit', 'C', '箱', '', null, 'A', 3, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1004, 0, 'product.unit', 'D', '份', '', null, 'A', 4, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1005, 0, 'product.unit', 'E', '片', '', null, 'A', 5, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1006, 0, 'product.unit', 'F', '条', '', null, 'A', 6, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1007, 0, 'product.unit', 'G', '包', '', null, 'A', 7, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1008, 0, 'product.unit', 'H', '只', '', null, 'A', 8, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1009, 0, 'product.unit', 'I', '件', '', null, 'A', 9, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1010, 0, 'product.unit', 'O', '自定义', '', null, 'A', 99, null, null, null, null, null);

insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1200, 0, 'staffRole.status', 'A', '有效', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1201, 0, 'staffRole.status', 'I', '无效', '', null, 'A', 2, null, null, null, null, null);

insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1210, 0, 'staff.status', 'A', '有效', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1211, 0, 'staff.status', 'I', '无效', '', null, 'A', 2, null, null, null, null, null);


insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-901, 0, 'DEV.js.config', 'min_mode', 'min.', '', null, 'A', 1, null, null, null, null, null);


insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1300, 0, 'orderEvent.status', 'C', '继续跟进', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1301, 0, 'orderEvent.status', 'B', '回退跟单', '', null, 'A', 2, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1302, 0, 'orderEvent.status', 'S', '成功', '', null, 'A', 3, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1303, 0, 'orderEvent.status', 'F', '失败', '', null, 'A', 4, null, null, null, null, null);
