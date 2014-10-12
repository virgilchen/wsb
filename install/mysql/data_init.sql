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


delete from cfg_dict where id<>9898986565104;
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-1, 0, 'Cache_Sql', 'Dict', 'subTable=domain_&isSCM=Y', 'select code_ as id, name_ , desc_ , domain_, ext_s1, ext_s2, ext_s3,ext_s4,ext_s5 from cfg_dict where domain_!=''Cache_Sql'' and status_ = ''A'' order by domain_,order_ ', null, 'A', 1, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-4, 0, 'Cache_Sql', 'Message', '', 'select code_ as id, value_ from cfg_message ', null, 'A', 4, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-5, 0, 'Cache_Sql', 'BusinessUpper', 'isSCM=Y', 'select business_id as id, business_name, business_id_upper, business_name_upper from business_rt  where business_id_upper is null', null, 'A', 5, null, null, null, null, null);
insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-6, 0, 'Cache_Sql', 'Role', 'isSCM=Y', 'select staff_role_id as id, staff_role_name from staff_role_demo_rt', null, 'A', 6, null, null, null, null, null);

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


insert into cfg_dict (id, pro_dict_id, domain_, code_, name_, desc_, flag_, status_, order_, ext_s1, ext_s2, ext_s3, ext_s4, ext_s5) values (-901, 0, 'DEV.js.config', 'min_mode', 'min.', '', null, 'A', 1, null, null, null, null, null);



