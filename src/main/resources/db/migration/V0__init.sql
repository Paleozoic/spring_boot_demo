-- 用户表设计：登录账号，密码，昵称，以及每次登录都需要查询的字段，在基本表，其它扩展属性在详细表。
-- flyway不支持创建数据库
-- CREATE DATABASE IF NOT EXISTS maxplus DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
-- use maxplus;

CREATE TABLE `maxplus`.`sys_user` (
`user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id，自增',
`user_name` varchar(500) COLLATE utf8_general_ci NOT NULL COMMENT '用户名,用于登陆，唯一',
`status` smallint(4) unsigned NOT NULL DEFAULT '1' COMMENT '用户状态 0-删除，1-正常 2-冻结',
`real_name` varchar(100) COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
`password` varchar(100) COLLATE utf8_general_ci NOT NULL COMMENT '登陆密码，MD5加盐密文',
PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


-- INSERT INTO sys_user(user_name,status,real_name)
