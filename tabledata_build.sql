insert into tb_role (id, role_name, description, parent_id) values(0, 'super', '超级管理员', 0),(1, 'admin', '管理员', 1), (2, 'user', '用户', 2), (3, 'visitor', '游客', 3);

insert into tb_permission(id, permission, description) values(1000, 'super', '超级管理员权限'),
															 (1001, 'admin', '管理员权限'),
															 (1002, 'user', '用户权限'),
															 (1003, 'visitor', '游客权限');
                          
insert into tb_role_permission(role_id, permission_id) values(0, 1000), (1, 1001), (2, 1002), (3, 1003);

insert into tb_user(id, username, password, phone, salt, role_id) values(9001, '17750623931', '789789', '17750623931', 'salt', 0),
																		(9002, '17750623932', '123456', '17750623932', 'salt', 2),
                                                                        (9003, '17750623933', '123456', '17750623933', 'salt', 3);

insert into tb_user_role(user_id, role_id) values(9001, 0), (9002, 2), (9003, 3);
                          
select * from tb_role;
select * from tb_permission;
select * from tb_role_permission;
select * from tb_user;
select * from tb_user_role;