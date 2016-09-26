/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : notes_api

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-09-26 17:13:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_device_info
-- ----------------------------
DROP TABLE IF EXISTS `t_device_info`;
CREATE TABLE `t_device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(255) DEFAULT NULL COMMENT '手机的IMEI编号',
  `os` varchar(255) DEFAULT NULL COMMENT '手机的系统平台，比如Android、IOS、Windows',
  `osVersion` varchar(255) DEFAULT NULL COMMENT '系统的版本，如Android 6.0',
  `phoneModel` varchar(255) DEFAULT NULL COMMENT '手机型号，如1505-A02',
  `brand` varchar(255) DEFAULT NULL COMMENT '手机的制作厂商，如360、小米、华为',
  `createTime` datetime DEFAULT NULL COMMENT '激活时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `imei` (`imei`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备信息表';

-- ----------------------------
-- Table structure for t_open_api
-- ----------------------------
DROP TABLE IF EXISTS `t_open_api`;
CREATE TABLE `t_open_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(255) DEFAULT NULL COMMENT '关联用户表的id',
  `openUserId` varchar(11) DEFAULT NULL COMMENT '第三方账号的用户id',
  `token` varchar(255) DEFAULT NULL COMMENT '第三方账号的token',
  `expiresTime` bigint(255) DEFAULT NULL COMMENT 'token过期的时间',
  `type` int(255) DEFAULT NULL COMMENT '第三方账号的类型，1：微信，2：QQ，3：微博',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方账号的表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '账号，唯一ID',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号，可用此登录',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `nickname` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `sid` varchar(255) DEFAULT NULL COMMENT '用户的唯一Id,手动生成',
  `avatar` varchar(255) DEFAULT NULL COMMENT '本地的头像',
  `gender` tinyint(255) DEFAULT NULL COMMENT '性别，0：未知，1：男，2：女',
  `createTime` datetime DEFAULT NULL COMMENT '用户的创建时间',
  `state` tinyint(255) DEFAULT NULL COMMENT '状态，0：正常，1：停用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `userId` (`sid`),
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
