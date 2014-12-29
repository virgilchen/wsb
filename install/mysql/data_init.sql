/* system */
delete from cfg_message where id <> 0;

insert into cfg_message(ID, code_, value_) 
values('1', '1', '[{0}]输入不能为空！' );

insert into cfg_message(ID, code_, value_) 
values('95', '95', '已经成功发送信息！' );
insert into cfg_message(ID, code_, value_) 
values('96', '96', '操作成功！' );
insert into cfg_message(ID, code_, value_) 
values('97', '97', '已经成功创建记录！' );
insert into cfg_message(ID, code_, value_) 
values('98', '98', '已经成功更新记录！' );
insert into cfg_message(ID, code_, value_) 
values('99', '99', '已经成功删除记录！' );

/* 100~120 for DB*/
insert into cfg_message(ID, code_, value_) 
values('100', '100', '数据库操作异常！' );

insert into cfg_message(ID, code_, value_) 
values('101', '101', '需要操作的记录已经被其它程序更新，请重新查询数据后再做操作！[表名：{0}, {1}]' );


insert into cfg_message(ID, code_, value_) 
values('102', '102', '编码已经被使用，保存失败，请使用其它编码后再进行保存！' );

insert into cfg_message(ID, code_, value_) 
values('103', '103', '数据为系统保留数据，不能修改与删除！' );

insert into cfg_message(ID, code_, value_) 
values('104', '104', '数据为系统保留数据，不能删除！' );


insert into cfg_message(ID, code_, value_) 
values('1000', '1000', '未登陆或已经超时，请重新登陆！' );

insert into cfg_message(ID, code_, value_) 
values('1001', '1001', '当前用户没有操作权限！' );



insert into cfg_message(ID, code_, value_) 
values('1106', '1106', '用户已经成功登录！' );

insert into cfg_message(ID, code_, value_) 
values('1107', '1107', '用户已经成功更改密码！' );

insert into cfg_message(ID, code_, value_) 
values('1108', '1108', '密码为空，更改密码失败！' );

insert into cfg_message(ID, code_, value_) 
values('1109', '1109', '原密码不正确，更改密码失败！' );

insert into cfg_message(ID, code_, value_) 
values('1110', '1110', '用户登录账号已经被使用，请使用其它登录账号！' );

insert into cfg_message(ID, code_, value_) 
values('1111', '1111', '用户被冻结，不能进行任何操作！' );

insert into cfg_message(ID, code_, value_) 
values('1112', '1112', '用户非激活状态，不能登录！' );

insert into cfg_message(ID, code_, value_) 
values('1113', '1113', '用户已经成功登出！' );

--20141115 zhanjm
insert into cfg_message(ID, code_, value_) 
values('1114', '1114', '客户号已经被使用，请使用其它客户号！' );

insert into cfg_message(ID, code_, value_) 
values('1115', '1115', '会员号已经被使用，请使用其它会员号！' );


INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('1005', '1005', '用户不存在！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('1006', '1006', '密码错误！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('1007', '1007', '用户已有业务操作，不能被删除！');

INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('1401', '1401', '用户角色已有用户，不能被删除！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('1402', '1402', '用户角色已经被流程环节使用，不能被删除！');


INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11001', '11001', '本环节为业务最终环节，未能找到下一环节！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11002', '11002', '任务已被其它业务员领取！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11003', '11003', '当前环节由其业务员处理！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11004', '11004', '业务员[{0}]没有业务环节所需要的角色权限[{1}]！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11005', '11005', '本环节为业务第一环节，未能找到可回退环节');


INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('11201', '11201', '帐户上没有足够的已购买的产品！');
 

INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('12001', '12001', '父业务不存在，本操作无效！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('12002', '12002', '子业务存在，本操作无效！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('12003', '12003', '业务已被使用，不能被删除！');


INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('13001', '13001', '父机构不存在，本操作无效！');
INSERT INTO `cfg_message` (`ID`, `CODE_`, `VALUE_`) VALUES ('13002', '13002', '子机构存在，本操作无效！');



delete from cfg_dict where id<>9898986565104;
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1, 0, 'Cache_Sql', 'Dict', 'subTable=domain_&isSCM=Y', 'select code_ as id, name_ , desc_ , domain_, ext_s1, ext_s2, ext_s3,ext_s4,ext_s5 from cfg_dict where domain_!=''Cache_Sql'' and status_ = ''A'' order by domain_,order_ ', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-4, 0, 'Cache_Sql', 'Message', '', 'select code_ as id, value_ from cfg_message ', null, 'A', 4, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-5, 0, 'Cache_Sql', 'BusinessUpper', 'isSCM=Y', 'select business_id as id, business_name, business_id_upper, business_name_upper from business_rt  where business_id_upper is null', null, 'A', 5, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-6, 0, 'Cache_Sql', 'Role', 'isSCM=Y', 'select staff_role_id as id, staff_role_name from staff_role_demo_rt', null, 'A', 6, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-7, 0, 'Cache_Sql', 'Org', 'isSCM=Y', 'select org_id as id, o.* from company_org_rt o', null, 'A', 7, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-8, 0, 'Cache_Sql', 'Business', 'isSCM=Y', 'select business_id as id, business_name, business_id_upper, business_name_upper from business_rt', null, 'A', 8, null, null, null, null, null);



insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-501, 0, 'CM.gender', 'F', '女', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-502, 0, 'CM.gender', 'M', '男', '', null, 'A', 2, null, null, null, null, null);

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



insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3000009, 0, 'SYS.ActionName', '/system/user_logout', '用户登出', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3000008, 0, 'SYS.ActionName', '/system/user_login', '用户登录', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3000007, 0, 'SYS.ActionName', 'add', '添加', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3000006, 0, 'SYS.ActionName', 'get', '读取', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3000005, 0, 'SYS.ActionName', 'query', '查询', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3000004, 0, 'SYS.ActionName', 'list', '查询列表', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3000003, 0, 'SYS.ActionName', 'delete', '删除', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3000002, 0, 'SYS.ActionName', 'update', '更新', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3000001, 0, 'SYS.ActionName', 'create', '创建', '', null, 'A', 1, null, null, null, null, null);




insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3002001, 0, 'staffRole.Auth.Level', 'H', '高级', '', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3002002, 0, 'staffRole.Auth.Level', 'M', '中级', '', null, 'A', 2, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3002003, 0, 'staffRole.Auth.Level', 'L', '初级', '', null, 'A', 3, null, null, null, null, null);



insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3007001, 0, 'CUS.PROS.R', 'L', '客户资料初级限制字段', 'cust_phone_no,other_contact_way,cust_home_address', null, 'A', 1, null, null, null, null, null);

insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-3007101, 0, 'CUS.CAR.R', 'L', '车类资料初级限制字段', 'car_no,car_engine_no', null, 'A', 1, null, null, null, null, null);




delete from `staff_demo_rt` where `staff_id`<>0;

/*  Staff init  */
INSERT INTO `staff_demo_rt` (`staff_id`, `staff_status`, `staff_login_profile`, `staff_login_pwd`, `staff_name`, `staff_gender`, `staff_id_card`, staff_role_id) VALUES ('1', 'A', 'admin', 'C4CA4238A0B923820DCC509A6F75849B', '系统管理员', '男', '440181198001010001', 1);

INSERT INTO `staff_role_demo_rt` (`staff_role_id`, `staff_role_status`, `staff_role_name`, `staff_role_remark`, `staff_role_org_id`) VALUES ('1', 'A', '系统管理员', '系统管理员', '1');


delete from `page_id_dim` where `page_id` != '0';

INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('10001', '业务单处理');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('10004', '业务单流查看');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('11001', '客户列表');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('12001', '业务管理');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('12003', '基础商品管理');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('12004', '商品包管理');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('14001', '决策信息管理');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('19002', '组织结构管理');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('19003', '角色配置');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('19004', '员工管理');
INSERT INTO `page_id_dim` (`page_id`, `page_name`) VALUES ('20001', '公告管理');

delete from `role_page_rt` where `staff_role_id`<>0 and `page_id`<>0;
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '10001');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '10004');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '11001');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '12001');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '12003');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '12004');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '14001');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '19002');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '19003');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '19004');
INSERT INTO `role_page_rt` (`staff_role_id`, `page_id`) VALUES ('1', '20001');



