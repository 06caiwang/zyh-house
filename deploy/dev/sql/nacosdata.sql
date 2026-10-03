/*
 Navicat Premium Dump SQL

 Source Server         : frame
 Source Server Type    : MySQL
 Source Server Version : 80402 (8.4.2)
 Source Host           : 175.178.110.25:3306
 Source Schema         : zyhhouse_nacos_dev

 Target Server Type    : MySQL
 Target Server Version : 80402 (8.4.2)
 File Encoding         : 65001

 Date: 30/09/2026 22:59:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
                                `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'group_id',
                                `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
                                `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
                                `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
                                `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
                                `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
                                `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
                                `c_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'configuration description',
                                `c_use` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'configuration usage',
                                `effect` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '配置生效的描述',
                                `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '配置的类型',
                                `c_schema` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT '配置的模式',
                                `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密钥',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (2, 'zyh-gateway', 'DEFAULT_GROUP', 'server:\n  port: 18080\n\nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1', 'e70d5ac1b2f3825f2592afed787490bd', '2026-08-08 21:40:45', '2026-08-08 21:57:31', 'nacos', '183.208.227.214', '', '972d99ce-2f94-49ea-aa37-6c5cb9f33e65', '网关服务配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (5, 'zyh-mstemplate', 'DEFAULT_GROUP', 'server:\n  port: 18085\n\nspring:\n  data:\n    redis:\n      host: 175.178.110.25\n      port: 6379\n      password: bite@123', '90e51109a1a8b58f8ed8fb509d2430f5', '2026-08-08 21:46:35', '2026-08-10 19:08:56', 'nacos', '183.208.227.214', '', '972d99ce-2f94-49ea-aa37-6c5cb9f33e65', '微服务模板配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (10, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', 'server:\n  port: 18080\nzyh:\n  redis:\n    enabled: true\n\nsecurity:\n  ignore:\n    whites:\n      - /admin/logout\n      - /admin/register\n      - admin/codeLogin\n      - /**/login/**\n      - /**/test/**\n      - /**/send_code/**\n      - /**/nologin/**\n      \nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-portal\n          uri: lb://zyh-portal\n          predicates: \n            - Path=/portal/**\n          filters:\n            - StripPrefix=1', '3850e49cb39ff78a9f366cfd1dbedf5a', '2026-08-10 19:27:10', '2026-09-16 16:14:16', 'nacos', '36.149.38.75', '', 'zyhhouse-dev', '网关环境配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (12, 'zyh-mstemplate-dev.yaml', 'DEFAULT_GROUP', 'server:\n  port: 18085\n\nzyh:\n  redis:\n    enabled: false', '03542d1bfd06d9b3706cd7c47662a25f', '2026-08-10 19:28:37', '2026-09-16 21:41:26', 'nacos', '36.149.38.75', '', 'zyhhouse-dev', '微服务模板配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (14, 'share-redis-dev.yaml', 'DEFAULT_GROUP', 'spring:\r\n  data:\r\n    redis:\r\n      host: 175.178.110.25\r\n      port: 6379\r\n      password: bite@123', 'a156007337a5abb4b986bb3481417b41', '2026-08-10 19:33:20', '2026-08-10 19:33:20', 'nacos', '183.208.227.214', '', 'zyhhouse-dev', 'redis服务配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (16, 'share-caffeine-dev.yaml', 'DEFAULT_GROUP', 'caffeine:\r\n  build:\r\n    initial-capacity: 128\r\n    maximum-size: 1024\r\n    expire: 60', '92e0d0f563d11d8e5e34e8932444ee1e', '2026-08-10 19:47:37', '2026-08-10 19:47:37', 'nacos', '183.208.227.214', '', 'zyhhouse-dev', '本地缓存配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (17, 'share-rabbitmq-dev.yaml', 'DEFAULT_GROUP', 'spring:\r\n  rabbitmq:\r\n    port: 5672\r\n    host: 175.178.110.25\r\n    username: admin\r\n    password: bite@123', 'bf677a17a5f5d07e9b5ba691862647a1', '2026-08-10 19:52:09', '2026-08-10 19:52:09', 'nacos', '183.208.227.214', '', 'zyhhouse-dev', 'rabbitmq的配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (18, 'zyh-file-dev.yaml', 'DEFAULT_GROUP', 'server:\n  port: 18082\n\nstorage:\n  type: oss\n\noss:\n  internal: false\n  endpoint: oss-cn-beijing.aliyuncs.com\n  intEndpoint: oss-cn-beijing-internal.aliyuncs.com\n  region: cn-beijing\n  accessKeyId: LTAI5t8ft4tLtbKtUeWatLhS\n  accessKeySecret: 1eQ3oULTvJ15hpKLJaW16h8GvAIv4D\n  bucketName: zyh-framework\n  pathPrefix: folder/\n  expre: 600\n  minLen: 0\n  maxLen: 1073741824\n\nspring:\n  servlet:\n    multipart:\n      max-file-size: 50MB\n      max-request-size: 50MB', '0d9c70ea96be5c130557260854feb94d', '2026-08-11 20:56:38', '2026-09-14 22:23:17', 'nacos', '36.149.38.75', '', 'zyhhouse-dev', '文件模块的配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (24, 'share-mysql-dev.yaml', 'DEFAULT_GROUP', 'spring:\r\n  datasource:\r\n    url: jdbc:mysql://175.178.110.25:3306/zyhhouse_dev?characterEncoding=utf8&useSSL=false\r\n    username: zyhdev\r\n    password: zyh@123\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\nmybatis:  # mybatis-plus  \r\n  configuration:\r\n    map-underscore-to-camel-case: true\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n  mapper-locations: classpath:mapper/*.xml', '154b26b1b5ccc81581d8dc00c66cbed4', '2026-08-24 01:37:23', '2026-08-24 01:37:23', 'nacos', '223.104.146.191', '', 'zyhhouse-dev', '数据库配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (25, 'zyh-admin', 'DEFAULT_GROUP', 'server:\n  port: 18081\n\nzyh:\n  redis:\n    enabled: true\n\nappuser:\n  info:\n    defaultAvatar: \"https://zyh-framework.oss-cn-beijing.aliyuncs.com/folder/332fe5ae-ad18-416e-9f1f-025ca2c324f1.jpg\"', 'ce86d6863df38241199bb9ad84a04444', '2026-08-24 01:39:33', '2026-09-14 22:28:47', 'nacos', '36.149.166.75', '', 'zyhhouse-dev', '地图服务的配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (27, 'share-map-dev.yaml', 'DEFAULT_GROUP', 'map:\r\n  type: qqmap\r\n\r\nqqmap:\r\n  key: QLTBZ-6LHKU-RAUVK-GRV7W-HZJEQ-AYFRQ\r\n  apiServer: https://apis.map.qq.com\r\n', 'f8cf712b425ec88d6e7d1774a53d7388', '2026-09-03 00:55:04', '2026-09-03 00:55:04', 'nacos', '36.149.167.96', '', 'zyhhouse-dev', '腾讯地图的配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (39, 'share-mail-dev.yaml', 'DEFAULT_GROUP', 'spring:\n  mail:\n    # QQ 邮箱 SMTP 服务器地址\n    host: smtp.qq.com\n    # SSL 加密端口，推荐 465\n    port: 465\n    # 你的 QQ 邮箱完整地址\n    username: 571711997@qq.com\n    # 注意：这里填的是 QQ 邮箱的「授权码」，不是 QQ 登录密码\n    password: cwhwzpuhlfxubcbf\n    # 协议通常设为 smtp 即可，SSL 通过 properties 开启\n    protocol: smtp\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          # 必须开启 SSL，否则连接会被拒绝\n          ssl:\n            enable: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n          # 可选：设置超时，避免线程被无响应的邮件服务器挂住\n          connectiontimeout: 5000\n          timeout: 5000\n          writetimeout: 5000\n\nzyh:\n  send-limit: 5\n  code-expiration: 2\n  send-message: false ', 'b79a920ea47221dbaea9d41158d301fa', '2026-09-15 23:07:18', '2026-09-16 15:54:22', 'nacos', '36.149.38.75', '', 'zyhhouse-dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (41, 'zyh-portal-dev.yaml', 'DEFAULT_GROUP', 'server:\n  port: 18083\n\nzyh:\n  redis:\n    enabled: true', '69ffce213675a295e2c3b189ffea0222', '2026-09-16 00:20:12', '2026-09-16 00:36:39', 'nacos', '36.149.38.75', '', 'zyhhouse-dev', '', '', '', 'yaml', '', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
                                     `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
                                     `datum_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
                                     `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
                                     `gmt_modified` datetime NOT NULL COMMENT '修改时间',
                                     `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
                                     `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `datum_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
                                     `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
                                     `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
                                     `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
                                     `beta_ips` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'betaIps',
                                     `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
                                     `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                     `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
                                     `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
                                     `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
                                     `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密钥',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                    `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
                                    `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
                                    `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                    `tag_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
                                    `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
                                    `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
                                    `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                    `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
                                    `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
                                         `id` bigint NOT NULL COMMENT 'id',
                                         `tag_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
                                         `tag_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tag_type',
                                         `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
                                         `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
                                         `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                         `nid` bigint NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增长标识',
                                         PRIMARY KEY (`nid`) USING BTREE,
                                         UNIQUE INDEX `uk_configtagrelation_configidtag`(`id` ASC, `tag_name` ASC, `tag_type` ASC) USING BTREE,
                                         INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
                                   `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
                                   `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
                                   `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
                                   `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                   `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
                                   `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                   `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
                                   `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `uk_group_id`(`group_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
                                    `id` bigint UNSIGNED NOT NULL COMMENT 'id',
                                    `nid` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增标识',
                                    `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
                                    `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
                                    `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
                                    `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
                                    `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                    `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
                                    `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
                                    `op_type` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'operation type',
                                    `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
                                    `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密钥',
                                    PRIMARY KEY (`nid`) USING BTREE,
                                    INDEX `idx_gmt_create`(`gmt_create` ASC) USING BTREE,
                                    INDEX `idx_gmt_modified`(`gmt_modified` ASC) USING BTREE,
                                    INDEX `idx_did`(`data_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 29, 'share-map-dev.yaml', 'DEFAULT_GROUP', '', 'map:\r\n  type: qqmap\r\n\r\nqqmap:\r\n  key: QLTBZ-6LHKU-RAUVK-GRV7W-HZJEQ-AYFRQ\r\n  apiServer: https://apis.map.qq.com\r\n', 'f8cf712b425ec88d6e7d1774a53d7388', '2026-09-03 00:55:03', '2026-09-03 00:55:04', 'nacos', '36.149.167.96', 'I', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (25, 30, 'zyh-admin', 'DEFAULT_GROUP', '', 'server:\r\n  port: 18081', '1315933bbdcac5a2a27f073d61742e9d', '2026-09-12 23:24:32', '2026-09-12 23:24:33', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (25, 31, 'zyh-admin', 'DEFAULT_GROUP', '', 'server:\n  port: 18081\n\nzyh:\n  redis:\n    enabled: true', '53a7079febf9d07e6fdc87e521d90d58', '2026-09-12 23:25:12', '2026-09-12 23:25:13', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (12, 32, 'zyh-mstemplate-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18085', 'b8f7009752749015890a5839d8522a48', '2026-09-12 23:25:53', '2026-09-12 23:25:54', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (10, 33, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18080\n\nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1', '80bf269f1b289cb82c9473f019d261b5', '2026-09-12 23:38:28', '2026-09-12 23:38:29', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (10, 34, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18080\nzyh:\n  redis:\n    enabled: true\nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1', 'f48775367b7728e09d658690068681ed', '2026-09-12 23:38:56', '2026-09-12 23:38:56', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (10, 35, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18080\nzyh:\n  redis:\n    enabled: true\n    \nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1', '447304b241eb03e11cd53ced2a18a58d', '2026-09-13 00:50:52', '2026-09-13 00:50:52', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (10, 36, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18080\nzyh:\n  redis:\n    enabled: true\n\nsecurity:\n  ignore:\n    whites:\n      - /sys_user/login/password\n      \nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1', 'a27baca14f86b0475322310c60a76a6b', '2026-09-13 00:51:18', '2026-09-13 00:51:19', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (18, 37, 'zyh-file-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18082\n\nstorage:\n  type: oss\n\noss:\n  internal: false\n  endpoint: oss-cn-beijing.aliyuncs.com\n  intEndpoint: oss-cn-beijing-internal.aliyuncs.com\n  region: cn-beijing\n  accessKeyId: LTAI5t8ft4tLtbKtUeWatLhS\n  accessKeySecret: 1eQ3oULTvJ15hpKLJaW16h8GvAIv4D\n  bucketName: zyh-zyhhouse\n  pathPrefix: folder/\n  expre: 600\n  minLen: 0\n  maxLen: 1073741824\n\nspring:\n  servlet:\n    multipart:\n      max-file-size: 50MB\n      max-request-size: 50MB', 'e5ac6eed6a6698ec24a2d966c4489c64', '2026-09-14 22:23:16', '2026-09-14 22:23:17', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (25, 38, 'zyh-admin', 'DEFAULT_GROUP', '', 'server:\n  port: 18081\n\nzyh:\n  redis:\n    enabled: true', '53a7079febf9d07e6fdc87e521d90d58', '2026-09-14 22:28:46', '2026-09-14 22:28:47', 'nacos', '36.149.166.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (10, 39, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18080\nzyh:\n  redis:\n    enabled: true\n\nsecurity:\n  ignore:\n    whites:\n      - /admin/sys_user/login/password\n      \nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1', '7f53a4d09f9ea5f9ab67111fe0d94b1c', '2026-09-14 22:40:05', '2026-09-14 22:40:05', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (0, 40, 'zyh-portal.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 18083', '0f8c2aa4642372253dfb078c650ec8db', '2026-09-15 19:42:17', '2026-09-15 19:42:17', 'nacos', '36.149.166.75', 'I', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (0, 41, 'share-mail-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\r\n  mail:\r\n    # QQ 邮箱 SMTP 服务器地址\r\n    host: smtp.qq.com\r\n    # SSL 加密端口，推荐 465\r\n    port: 465\r\n    # 你的 QQ 邮箱完整地址\r\n    username: 571711997@qq.com\r\n    # 注意：这里填的是 QQ 邮箱的「授权码」，不是 QQ 登录密码\r\n    password: cwhwzpuhlfxubcbf\r\n    # 协议通常设为 smtp 即可，SSL 通过 properties 开启\r\n    protocol: smtp\r\n    default-encoding: utf-8\r\n    properties:\r\n      mail:\r\n        smtp:\r\n          auth: true\r\n          # 必须开启 SSL，否则连接会被拒绝\r\n          ssl:\r\n            enable: true\r\n          # 可选：设置超时，避免线程被无响应的邮件服务器挂住\r\n          connectiontimeout: 5000\r\n          timeout: 5000\r\n          writetimeout: 5000', 'e8da7d350d6c3e34ab108c0cdb76faa5', '2026-09-15 23:07:18', '2026-09-15 23:07:18', 'nacos', '36.149.38.75', 'I', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (38, 42, 'zyh-portal.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 18083', '0f8c2aa4642372253dfb078c650ec8db', '2026-09-16 00:19:31', '2026-09-16 00:19:31', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (38, 43, 'zyh-portal.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18083\n\n', 'd16538360fe0705e1d3d57ce997e150a', '2026-09-16 00:19:46', '2026-09-16 00:19:47', 'nacos', '36.149.38.75', 'D', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (0, 44, 'zyh-portal-dev.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 18083', '0f8c2aa4642372253dfb078c650ec8db', '2026-09-16 00:20:11', '2026-09-16 00:20:12', 'nacos', '36.149.38.75', 'I', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (39, 45, 'share-mail-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\r\n  mail:\r\n    # QQ 邮箱 SMTP 服务器地址\r\n    host: smtp.qq.com\r\n    # SSL 加密端口，推荐 465\r\n    port: 465\r\n    # 你的 QQ 邮箱完整地址\r\n    username: 571711997@qq.com\r\n    # 注意：这里填的是 QQ 邮箱的「授权码」，不是 QQ 登录密码\r\n    password: cwhwzpuhlfxubcbf\r\n    # 协议通常设为 smtp 即可，SSL 通过 properties 开启\r\n    protocol: smtp\r\n    default-encoding: utf-8\r\n    properties:\r\n      mail:\r\n        smtp:\r\n          auth: true\r\n          # 必须开启 SSL，否则连接会被拒绝\r\n          ssl:\r\n            enable: true\r\n          # 可选：设置超时，避免线程被无响应的邮件服务器挂住\r\n          connectiontimeout: 5000\r\n          timeout: 5000\r\n          writetimeout: 5000', 'e8da7d350d6c3e34ab108c0cdb76faa5', '2026-09-16 00:24:12', '2026-09-16 00:24:12', 'nacos', '36.149.166.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (10, 46, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18080\nzyh:\n  redis:\n    enabled: true\n\nsecurity:\n  ignore:\n    whites:\n      - /admin/logout\n      - /admin/register\n      - admin/codeLogin\n      - /**/login/**\n      - /**/test/**\n      - /**/send_code/**\n      - /**/nologin/**\n      \nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1', 'c5f7e5669e65bfaad9a6de514d7630c8', '2026-09-16 00:26:37', '2026-09-16 00:26:38', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (10, 47, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18080\nzyh:\n  redis:\n    enabled: true\n\nsecurity:\n  ignore:\n    whites:\n      - /admin/logout\n      - /admin/register\n      - admin/codeLogin\n      - /**/login/**\n      - /**/test/**\n      - /**/send_code/**\n      - /**/nologin/**\n      \nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-portal\n          uri: lb://zyh-portal\n          predicates: \n            - Path=/user/**\n          filters:\n            - StripPrefix=1', '12ea21f05b9cbb322bbd847a5b74571c', '2026-09-16 00:31:00', '2026-09-16 00:31:00', 'nacos', '36.149.166.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (10, 48, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18080\nzyh:\n  redis:\n    enabled: true\n\nsecurity:\n  ignore:\n    whites:\n      - /admin/logout\n      - /admin/register\n      - admin/codeLogin\n      - /**/login/**\n      - /**/test/**\n      - /**/send_code/**\n      - /**/nologin/**\n      \nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-portal\n          uri: lb://zyh-portal\n          predicates: \n            - Path=/user/**\n          filters:\n            - StripPrefix=1', '12ea21f05b9cbb322bbd847a5b74571c', '2026-09-16 00:35:53', '2026-09-16 00:35:54', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (41, 49, 'zyh-portal-dev.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 18083', '0f8c2aa4642372253dfb078c650ec8db', '2026-09-16 00:36:39', '2026-09-16 00:36:39', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (39, 50, 'share-mail-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  mail:\n    # QQ 邮箱 SMTP 服务器地址\n    host: smtp.qq.com\n    # SSL 加密端口，推荐 465\n    port: 465\n    # 你的 QQ 邮箱完整地址\n    username: 571711997@qq.com\n    # 注意：这里填的是 QQ 邮箱的「授权码」，不是 QQ 登录密码\n    password: cwhwzpuhlfxubcbf\n    # 协议通常设为 smtp 即可，SSL 通过 properties 开启\n    protocol: smtp\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          # 必须开启 SSL，否则连接会被拒绝\n          ssl:\n            enable: true\n          # 可选：设置超时，避免线程被无响应的邮件服务器挂住\n          connectiontimeout: 5000\n          timeout: 5000\n          writetimeout: 5000\n\nzyh:\n  mail:\n   from: \"【登录服务】\"\n  send-limit: 3\n  code-expiration: 2\n  zyh.send-message: true', '09534469c89d4a7249a6d3cf05e14abb', '2026-09-16 00:37:02', '2026-09-16 00:37:03', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (39, 51, 'share-mail-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  mail:\n    # QQ 邮箱 SMTP 服务器地址\n    host: smtp.qq.com\n    # SSL 加密端口，推荐 465\n    port: 465\n    # 你的 QQ 邮箱完整地址\n    username: 571711997@qq.com\n    # 注意：这里填的是 QQ 邮箱的「授权码」，不是 QQ 登录密码\n    password: cwhwzpuhlfxubcbf\n    # 协议通常设为 smtp 即可，SSL 通过 properties 开启\n    protocol: smtp\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          # 必须开启 SSL，否则连接会被拒绝\n          ssl:\n            enable: true\n          # 可选：设置超时，避免线程被无响应的邮件服务器挂住\n          connectiontimeout: 5000\n          timeout: 5000\n          writetimeout: 5000\n\nzyh:\n  mail:\n   from: \"【登录服务】\"\n  send-limit: 3\n  code-expiration: 2\n  send-message: true ', 'f844028a2e854d14cda38e6030868923', '2026-09-16 08:05:55', '2026-09-16 08:05:56', 'nacos', '223.104.151.82', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (39, 52, 'share-mail-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  mail:\n    # QQ 邮箱 SMTP 服务器地址\n    host: smtp.qq.com\n    # SSL 加密端口，推荐 465\n    port: 465\n    # 你的 QQ 邮箱完整地址\n    username: 571711997@qq.com\n    # 注意：这里填的是 QQ 邮箱的「授权码」，不是 QQ 登录密码\n    password: cwhwzpuhlfxubcbf\n    # 协议通常设为 smtp 即可，SSL 通过 properties 开启\n    protocol: smtp\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          # 必须开启 SSL，否则连接会被拒绝\n          ssl:\n            enable: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n          # 可选：设置超时，避免线程被无响应的邮件服务器挂住\n          connectiontimeout: 5000\n          timeout: 5000\n          writetimeout: 5000\n\nzyh:\n  mail:\n   from: \"【登录服务】\"\n  send-limit: 100\n  code-expiration: 2\n  send-message: true ', '672d3d3dd246a299cee64a70582268a6', '2026-09-16 15:15:33', '2026-09-16 15:15:34', 'nacos', '36.149.166.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (39, 53, 'share-mail-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  mail:\n    # QQ 邮箱 SMTP 服务器地址\n    host: smtp.qq.com\n    # SSL 加密端口，推荐 465\n    port: 465\n    # 你的 QQ 邮箱完整地址\n    username: 571711997@qq.com\n    # 注意：这里填的是 QQ 邮箱的「授权码」，不是 QQ 登录密码\n    password: cwhwzpuhlfxubcbf\n    # 协议通常设为 smtp 即可，SSL 通过 properties 开启\n    protocol: smtp\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          # 必须开启 SSL，否则连接会被拒绝\n          ssl:\n            enable: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n          # 可选：设置超时，避免线程被无响应的邮件服务器挂住\n          connectiontimeout: 5000\n          timeout: 5000\n          writetimeout: 5000\n\nzyh:\n  send-limit: 100\n  code-expiration: 2\n  send-message: true ', '0c0e0fd4a868c66d03d8a2ed010b5217', '2026-09-16 15:27:47', '2026-09-16 15:27:47', 'nacos', '36.149.166.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (39, 54, 'share-mail-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  mail:\n    # QQ 邮箱 SMTP 服务器地址\n    host: smtp.qq.com\n    # SSL 加密端口，推荐 465\n    port: 465\n    # 你的 QQ 邮箱完整地址\n    username: 571711997@qq.com\n    # 注意：这里填的是 QQ 邮箱的「授权码」，不是 QQ 登录密码\n    password: cwhwzpuhlfxubcbf\n    # 协议通常设为 smtp 即可，SSL 通过 properties 开启\n    protocol: smtp\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          # 必须开启 SSL，否则连接会被拒绝\n          ssl:\n            enable: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n          # 可选：设置超时，避免线程被无响应的邮件服务器挂住\n          connectiontimeout: 5000\n          timeout: 5000\n          writetimeout: 5000\n\nzyh:\n  send-limit: 5\n  code-expiration: 2\n  send-message: true ', '50f6bd6cfeb73b55b307b13e79759c5f', '2026-09-16 15:54:22', '2026-09-16 15:54:22', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (10, 55, 'zyh-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18080\nzyh:\n  redis:\n    enabled: true\n\nsecurity:\n  ignore:\n    whites:\n      - /admin/logout\n      - /admin/register\n      - admin/codeLogin\n      - /**/login/**\n      - /**/test/**\n      - /**/send_code/**\n      - /**/nologin/**\n      \nspring:\n  cloud:\n    gateway:\n      routes:\n        # 管理模块\n        - id: zyh-mstemplate\n          uri: lb://zyh-mstemplate\n          predicates:\n            - Path=/mstemplate/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-file\n          uri: lb://zyh-file\n          predicates: \n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-admin\n          uri: lb://zyh-admin\n          predicates: \n            - Path=/admin/**\n          filters:\n            - StripPrefix=1\n        - id: zyh-portal\n          uri: lb://zyh-portal\n          predicates: \n            - Path=/user/**', 'c0eabc069d0d00f284a84eb256b5592d', '2026-09-16 16:14:16', '2026-09-16 16:14:16', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');
INSERT INTO `his_config_info` VALUES (12, 56, 'zyh-mstemplate-dev.yaml', 'DEFAULT_GROUP', '', 'server:\n  port: 18085\n\nzyh:\n  redis:\n    enabled: true', '03fdc05fe79c9ee6be262d6c65282e44', '2026-09-16 21:41:26', '2026-09-16 21:41:26', 'nacos', '36.149.38.75', 'U', 'zyhhouse-dev', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
                                `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'role',
                                `resource` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'resource',
                                `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'action',
                                UNIQUE INDEX `uk_role_permission`(`role` ASC, `resource` ASC, `action` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
                          `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'username',
                          `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'role',
                          UNIQUE INDEX `idx_user_role`(`username` ASC, `role` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
                                    `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
                                    `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
                                    `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
                                    `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                    `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
                                    `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                    `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `uk_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `kp` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
                                `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                `tenant_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_name',
                                `tenant_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
                                `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'create_source',
                                `gmt_create` bigint NOT NULL COMMENT '创建时间',
                                `gmt_modified` bigint NOT NULL COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp` ASC, `tenant_id` ASC) USING BTREE,
                                INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (2, '1', 'zyhhouse-dev', 'zyhhouse-dev', 'Java脚手架开发环境配置', 'nacos', 1786361141498, 1786361141498);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
                          `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'username',
                          `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'password',
                          `enabled` tinyint(1) NOT NULL COMMENT 'enabled',
                          PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$9IcbyRmE9Tulsa1baGrdNeQ4YiHiU9mRgyhuB.LwnntSb9JVPKLSi', 1);

SET FOREIGN_KEY_CHECKS = 1;
