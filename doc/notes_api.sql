/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : notes_api

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-10-08 19:14:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_attach
-- ----------------------------
DROP TABLE IF EXISTS `t_attach`;
CREATE TABLE `t_attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sid` varchar(255) NOT NULL COMMENT '附件的sid，由客户端生成',
  `noteSid` varchar(255) DEFAULT NULL COMMENT '关联笔记的sid',
  `filename` varchar(255) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '文件类型,1:图片，2：语音，3：涂鸦，4：压缩文件，5：视频，6：其他文件',
  `localPath` varchar(255) DEFAULT NULL COMMENT '文件的本地存储路径',
  `description` varchar(255) DEFAULT NULL COMMENT '文件的描述',
  `deleteState` tinyint(4) DEFAULT NULL COMMENT '删除状态，0：没有删除,1:删除到垃圾桶，2：隐藏，3：完全删除',
  `createTime` datetime DEFAULT NULL COMMENT '文件的创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '文件的修改时间',
  `size` double DEFAULT NULL COMMENT '文件的大小',
  `mimeType` varchar(255) DEFAULT NULL COMMENT '文件的mime类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sid` (`sid`) USING BTREE,
  KEY `noteSid` (`noteSid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Table structure for t_detail_list
-- ----------------------------
DROP TABLE IF EXISTS `t_detail_list`;
CREATE TABLE `t_detail_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sid` varchar(255) NOT NULL COMMENT '手动生成的唯一值，有客户端生成',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `noteSid` varchar(255) DEFAULT NULL COMMENT '关联笔记表的sid',
  `sort` int(255) DEFAULT NULL COMMENT '排序',
  `checked` tinyint(4) DEFAULT NULL COMMENT '该事项是否已完成',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `deleteState` tinyint(4) DEFAULT NULL COMMENT '删除状态，0：没有删除,1:删除到垃圾桶，2：隐藏，3：完全删除',
  `hash` varchar(255) DEFAULT NULL COMMENT '该清单的hash，主要用来检测更新',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sid` (`sid`) USING BTREE,
  KEY `noteSid` (`noteSid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='清单表';

-- ----------------------------
-- Table structure for t_device_info
-- ----------------------------
DROP TABLE IF EXISTS `t_device_info`;
CREATE TABLE `t_device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` varchar(255) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `osVersion` varchar(255) DEFAULT NULL,
  `phoneModel` varchar(255) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `imei` (`imei`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_folder
-- ----------------------------
DROP TABLE IF EXISTS `t_folder`;
CREATE TABLE `t_folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sid` varchar(255) DEFAULT NULL COMMENT '唯一值',
  `userId` int(11) DEFAULT NULL COMMENT '关联用户表的id',
  `name` varchar(255) DEFAULT NULL COMMENT '文件夹的名称',
  `isLock` tinyint(4) DEFAULT NULL COMMENT '是否被锁定',
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `deleteState` tinyint(4) DEFAULT NULL COMMENT '删除状态，0：没有删除,1:删除到垃圾桶，2：隐藏，3：完全删除',
  `createTime` datetime DEFAULT NULL COMMENT '创建时时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `count` int(11) DEFAULT NULL COMMENT '改文件夹下可用笔记的数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sid` (`sid`) USING BTREE,
  KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='笔记本的表';

-- ----------------------------
-- Table structure for t_note_info
-- ----------------------------
DROP TABLE IF EXISTS `t_note_info`;
CREATE TABLE `t_note_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sid` varchar(255) NOT NULL COMMENT '实际唯一标识',
  `userId` int(11) DEFAULT NULL COMMENT '关联用户的id',
  `title` varchar(255) DEFAULT NULL COMMENT '笔记标题',
  `content` varchar(255) DEFAULT NULL COMMENT '笔记内容',
  `remindId` int(11) DEFAULT NULL COMMENT '提醒的id',
  `remindTime` datetime DEFAULT NULL COMMENT '提醒时间',
  `folderSid` varchar(255) DEFAULT NULL COMMENT '关联文件夹的sid',
  `kind` tinyint(4) DEFAULT NULL COMMENT '笔记的类型，0：文本笔记， 1：清单笔记',
  `deleteState` tinyint(4) DEFAULT NULL COMMENT '删除状态，0：没有删除,1:删除到垃圾桶，2：隐藏，3：完全删除',
  `createTime` datetime DEFAULT NULL COMMENT '笔记的创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `hash` varchar(255) DEFAULT NULL COMMENT '笔记的hash',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sid` (`sid`) USING BTREE,
  KEY `userId` (`userId`),
  KEY `folderSid` (`folderSid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='笔记表';

-- ----------------------------
-- Table structure for t_open_api
-- ----------------------------
DROP TABLE IF EXISTS `t_open_api`;
CREATE TABLE `t_open_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `openUserId` varchar(255) DEFAULT NULL,
  `token` varchar(255) NOT NULL,
  `expiresTime` bigint(20) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `openUserId` (`openUserId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `sid` varchar(255) DEFAULT NULL,
  `avatarHash` varchar(255) DEFAULT NULL COMMENT '用户头像的hash值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `sid` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
DROP TRIGGER IF EXISTS `tri_trash_folder`;
DELIMITER ;;
CREATE TRIGGER `tri_trash_folder` AFTER UPDATE ON `t_folder` FOR EACH ROW BEGIN
	IF (OLD.deleteState IS NULL OR OLD.deleteState != 1) AND NEW.deleteState = 1 THEN
		UPDATE t_note_info SET deleteState = 1, modify_time = NEW.modifyTime WHERE folderSid = NEW.id;
	ELSEIF (OLD.deleteState IS NULL AND OLD.deleteState != 0) AND NEW.deleteState = 0 THEN
		UPDATE t_note_info SET deleteState = 0, modify_time = NEW.modifyTime WHERE folderSid = NEW.id;
	END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `tri_insert_note`;
DELIMITER ;;
CREATE TRIGGER `tri_insert_note` AFTER INSERT ON `t_note_info` FOR EACH ROW BEGIN  
         IF NEW.folderSid IS NOT NULL or NEW.folderSid != 0 THEN 
                   UPDATE t_folder SET modifyTime = NEW.createTime, count = count + 1 WHERE id = NEW.folderSid;
         END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `tri_update_folder`;
DELIMITER ;;
CREATE TRIGGER `tri_update_folder` AFTER UPDATE ON `t_note_info` FOR EACH ROW BEGIN 
	IF NEW.folderSid IS NOT NULL AND OLD.deleteState != 0 AND NEW.deleteState = 0 THEN
		UPDATE t_folder SET modifyTime = NEW.modifyTime, count = count + 1 WHERE id = NEW.folderSid;
	ELSEIF NEW.folderSid IS NOT NULL AND OLD.deleteState = 0 AND NEW.deleteState != 0 THEN
		UPDATE t_folder SET modifyTime = NEW.modifyTime, count = count - 1 WHERE id = NEW.folderSid;
	ELSE 
		UPDATE t_folder SET modifyTime = NEW.modifyTime WHERE id = NEW.folderSid;
	END IF;
END
;;
DELIMITER ;
