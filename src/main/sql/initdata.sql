DELIMITER ;
delete from t_sys_user;
delete from t_sys_role;
delete from t_sys_resource;
delete from t_sys_organization;
-- 初始密码123456
insert into t_sys_user values(1,1,'admin','d3c59d25033dbf980d29554025c23a75','8d78869f470951332959580424d4bf4f','管理员', '1', false);
insert into t_sys_organization values(1, '总公司', 0, '0/', true);
insert into t_sys_organization values(2, '分公司1', 1, '0/1/', true);
insert into t_sys_organization values(3, '分公司2', 1, '0/1/', true);
insert into t_sys_organization values(4, '分公司11', 2, '0/1/2/', true);

insert into t_sys_resource values(1, '资源', 'menu', '', 0, '0/', '', true);

insert into t_sys_resource values(11, '系统管理', 'menu', '', 1, '0/1/', 'system:*', true);

insert into t_sys_resource values(111, '组织机构管理', 'menu', '/organization', 11, '0/1/11/', 'system:organization:*', true);
insert into t_sys_resource values(112, '组织机构新增', 'button', '', 111, '0/1/11/111/', 'system:organization:create', true);
insert into t_sys_resource values(113, '组织机构修改', 'button', '', 111, '0/1/11/111/', 'system:organization:update', true);
insert into t_sys_resource values(114, '组织机构删除', 'button', '', 111, '0/1/11/111/', 'system:organization:delete', true);
insert into t_sys_resource values(115, '组织机构查看', 'button', '', 111, '0/1/11/111/', 'system:organization:view', true);

insert into t_sys_resource values(121, '用户管理', 'menu', '/user', 11, '0/1/11/', 'system:user:*', true);
insert into t_sys_resource values(122, '用户新增', 'button', '', 121, '0/1/11/121/', 'system:user:create', true);
insert into t_sys_resource values(123, '用户修改', 'button', '', 121, '0/1/11/121/', 'system:user:update', true);
insert into t_sys_resource values(124, '用户删除', 'button', '', 121, '0/1/11/121/', 'system:user:delete', true);
insert into t_sys_resource values(125, '用户查看', 'button', '', 121, '0/1/11/121/', 'system:user:view', true);
insert into t_sys_resource values(126, '用户层级设定', 'button', '', 121, '0/1/11/121/', 'system:user:level', true);
insert into t_sys_resource values(127, '用户成本设定', 'button', '', 121, '0/1/11/121/', 'system:user:cost', true);

insert into t_sys_resource values(131, '资源管理', 'menu', '/resource', 11, '0/1/11/', 'system:resource:*', true);
insert into t_sys_resource values(132, '资源新增', 'button', '', 131, '0/1/11/131/', 'system:resource:create', true);
insert into t_sys_resource values(133, '资源修改', 'button', '', 131, '0/1/11/131/', 'system:resource:update', true);
insert into t_sys_resource values(134, '资源删除', 'button', '', 131, '0/1/11/131/', 'system:resource:delete', true);
insert into t_sys_resource values(135, '资源查看', 'button', '', 131, '0/1/11/131/', 'system:resource:view', true);

insert into t_sys_resource values(141, '角色管理', 'menu', '/role', 11, '0/1/11/', 'system:role:*', true);
insert into t_sys_resource values(142, '角色新增', 'button', '', 141, '0/1/11/141/', 'system:role:create', true);
insert into t_sys_resource values(143, '角色修改', 'button', '', 141, '0/1/11/141/', 'system:role:update', true);
insert into t_sys_resource values(144, '角色删除', 'button', '', 141, '0/1/11/141/', 'system:role:delete', true);
insert into t_sys_resource values(145, '角色查看', 'button', '', 141, '0/1/11/141/', 'system:role:view', true);

insert into t_sys_resource values(151, '项目设定', 'menu', '/project', 11, '0/1/11/', 'system:project:*', true);
insert into t_sys_resource values(152, '项目新增', 'button', '', 151, '0/1/11/151/', 'system:project:create', true);
insert into t_sys_resource values(153, '项目修改', 'button', '', 151, '0/1/11/151/', 'system:project:update', true);
insert into t_sys_resource values(154, '项目删除', 'button', '', 151, '0/1/11/151/', 'system:project:delete', true);
insert into t_sys_resource values(155, '项目查看', 'button', '', 151, '0/1/11/151/', 'system:project:view', true);
insert into t_sys_resource values(156, '项目授权', 'button', '', 151, '0/1/11/151/', 'system:project:auth', true);

insert into t_sys_resource values(161, '员工层级设定', 'menu', '/project', 11, '0/1/11/', 'system:level:*', true);
insert into t_sys_resource values(162, '员工层级新增', 'button', '', 161, '0/1/11/161/', 'system:level:create', true);
insert into t_sys_resource values(163, '员工层级修改', 'button', '', 161, '0/1/11/161/', 'system:level:update', true);
insert into t_sys_resource values(164, '员工层级删除', 'button', '', 161, '0/1/11/161/', 'system:level:delete', true);
insert into t_sys_resource values(165, '员工层级查看', 'button', '', 161, '0/1/11/161/', 'system:level:view', true);
insert into t_sys_resource values(166, '员工层级成本设定', 'button', '', 161, '0/1/11/161/', 'system:level:cost', true);
 
insert into t_sys_resource values(171, '费用类型设定', 'menu', '/expensestype', 11, '0/1/11/', 'system:expensestype:*', true);
insert into t_sys_resource values(172, '费用类型新增', 'button', '', 171, '0/1/11/171/', 'system:expensestype:create', true);
insert into t_sys_resource values(173, '费用类型修改', 'button', '', 171, '0/1/11/171/', 'system:expensestype:update', true);
insert into t_sys_resource values(174, '费用类型删除', 'button', '', 171, '0/1/11/171/', 'system:expensestype:delete', true);
insert into t_sys_resource values(175, '费用类型查看', 'button', '', 171, '0/1/11/171/', 'system:expensestype:view', true); 
 
insert into t_sys_resource values(21, '成本填报', 'menu', '', 1, '0/1/', 'cost:*', true);

insert into t_sys_resource values(211, '费用填报管理', 'menu', '/expensescreate', 21, '0/1/21/', 'cost:expenses:*', true);
insert into t_sys_resource values(212, '费用填报新增', 'button', '', 211, '0/1/21/211/', 'cost:expenses:create', true);
insert into t_sys_resource values(213, '费用填报修改', 'button', '', 211, '0/1/21/211/', 'cost:expenses:update', true);
insert into t_sys_resource values(214, '费用填报删除', 'button', '', 211, '0/1/21/211/', 'cost:expenses:delete', true);
insert into t_sys_resource values(215, '费用填报查看', 'button', '', 211, '0/1/21/211/', 'cost:expenses:view', true);
insert into t_sys_resource values(216, '费用填报提交', 'button', '', 211, '0/1/21/211/', 'cost:expenses:pending', true); 

insert into t_sys_resource values(221, '工时填报管理', 'menu', '/timesheetcreate', 21, '0/1/21/', 'cost:timesheet:*', true);
insert into t_sys_resource values(222, '工时填报新增', 'button', '', 221, '0/1/21/221/', 'cost:timesheet:create', true);
insert into t_sys_resource values(223, '工时填报修改', 'button', '', 221, '0/1/21/221/', 'cost:timesheet:update', true);
insert into t_sys_resource values(224, '工时填报删除', 'button', '', 221, '0/1/21/221/', 'cost:timesheet:delete', true);
insert into t_sys_resource values(225, '工时填报查看', 'button', '', 221, '0/1/21/221/', 'cost:timesheet:view', true);
insert into t_sys_resource values(226, '工时填报提交', 'button', '', 221, '0/1/21/221/', 'cost:timesheet:pending', true);


insert into t_sys_resource values(31, '成本审批', 'menu', '', 1, '0/1/', 'approve:*', true); 

insert into t_sys_resource values(311, '费用填报审批', 'menu', '/expensesapprove', 31, '0/1/31/', 'approve:expenses:*', true);
insert into t_sys_resource values(315, '费用填报查看', 'button', '', 311, '0/1/31/311/', 'approve:expenses:view', true);
insert into t_sys_resource values(316, '费用填报审批', 'button', '', 311, '0/1/31/311/', 'approve:expenses:approve', true); 

insert into t_sys_resource values(321, '工时填报审批', 'menu', '/timesheetapprove', 31, '0/1/31/', 'approve:timesheet:*', true);
insert into t_sys_resource values(325, '工时填报查看', 'button', '', 321, '0/1/31/321/', 'approve:timesheet:view', true);
insert into t_sys_resource values(326, '工时填报提交', 'button', '', 321, '0/1/31/321/', 'approve:timesheet:approve', true);

insert into t_sys_resource values(91, '统计分析', 'menu', '', 1, '0/1/', 'report:*', true);

insert into t_sys_resource values(911, '项目成本', 'menu', '/reportproject', 91, '0/1/91/', 'report:project:*', true);
insert into t_sys_resource values(915, '项目成本查看', 'button', '', 911, '0/1/91/911/', 'report:project:view', true);

insert into t_sys_role values(1, 'admin', '超级管理员', '11,111,121,131,141,151,161,171,21,211,221,31,311,321,91,911', true);