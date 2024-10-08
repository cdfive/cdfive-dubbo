CREATE TABLE `t_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_types` varchar(64) NOT NULL COMMENT '客户端类型集合',
  `scenes` varchar(64) NOT NULL COMMENT '场景集合',
  `type` varchar(32) NOT NULL COMMENT '类型',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `content` mediumtext NULL COMMENT '内容',
  `status` varchar(32) NOT NULL COMMENT '状态',
  `effective_start_time` datetime NOT NULL COMMENT '生效开始时间',
  `effective_end_time` datetime NOT NULL COMMENT '生效结束时间',
  `cover_image` varchar(512) NULL COMMENT '封面图片',
  `ext_data` varchar(2048) NULL COMMENT '数据,json格式',
  `popup_window_times` int NOT NULL COMMENT '弹窗次数',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `create_user_desc` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人账号',
  `update_user_desc` varchar(64) DEFAULT NULL COMMENT '更新人名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

alter table t_notice add unique index idx_status(status) using btree;

CREATE TABLE t_category (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `category_code` varchar(32) NOT NULL COMMENT '分类编码',
  `category_name` varchar(32) NOT NULL COMMENT '分类名称',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `create_user_desc` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人账号',
  `update_user_desc` varchar(64) DEFAULT NULL COMMENT '更新人名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

alter table t_category add unique index idx_category_code(category_code) using btree;

CREATE TABLE t_blog (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `category_code` varchar(32) NULL COMMENT '分类编码',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `publish` tinyint(1) NOT NULL COMMENT '是否发布',
  `publish_time` datetime NULL COMMENT '发布时间',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `create_user_desc` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人账号',
  `update_user_desc` varchar(64) DEFAULT NULL COMMENT '更新人名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='博客表';

alter table t_blog add unique index idx_category_code(category_code) using btree;