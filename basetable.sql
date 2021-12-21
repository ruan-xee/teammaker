CREATE TABLE `tb_permission` (
  `id` bigint(20) NOT NULL,
  `permission` varchar(20) NOT NULL COMMENT '权限编号',
  `description` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `create_time` timestamp NULL DEFAULT current_timestamp COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT current_timestamp on update current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(20) NOT NULL COMMENT '用户名，默认同手机号（暂）',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `phone` varchar(11) NOT NULL unique COMMENT '手机号',
  `salt` varchar(100) NOT NULL COMMENT '盐值',
  `role_id` bigint(20) DEFAULT 2 COMMENT '角色id，默认“用户”',
  `avater` varchar(50) DEFAULT NULL comment '头像',
  `create_time` timestamp NULL DEFAULT current_timestamp COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT current_timestamp on update current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_role` (
  `id` bigint(20) NOT NULL,
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `parent_id` bigint(20) NOT NULL COMMENT '父角色id，默认同自己的id',
  `create_time` timestamp NULL DEFAULT current_timestamp COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT current_timestamp on update current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色编号',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限编号',
  `create_time` timestamp NULL DEFAULT current_timestamp COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT current_timestamp on update current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户编号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色编号',
  `create_time` timestamp NULL DEFAULT current_timestamp COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT current_timestamp on update current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;