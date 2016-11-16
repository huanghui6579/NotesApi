/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : notes_api

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-11-16 19:08:39
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
  `hash` varchar(255) DEFAULT NULL COMMENT '文件的hash值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sid` (`sid`) USING BTREE,
  KEY `noteSid` (`noteSid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='附件表';

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='清单表';

-- ----------------------------
-- Table structure for t_device_info
-- ----------------------------
DROP TABLE IF EXISTS `t_device_info`;
CREATE TABLE `t_device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `imei` varchar(255) DEFAULT NULL COMMENT '手机的IMEI编号',
  `os` varchar(255) DEFAULT NULL COMMENT '手机的系统平台，比如Android、IOS、Windows',
  `osVersion` varchar(255) DEFAULT NULL COMMENT '系统的版本，如Android 6.0',
  `phoneModel` varchar(255) DEFAULT NULL COMMENT '手机型号，如1505-A02',
  `brand` varchar(255) DEFAULT NULL COMMENT '手机的制作厂商，如360、小米、华为',
  `appVersionCode` int(11) DEFAULT NULL COMMENT 'App的版本号',
  `appVersionName` varchar(255) DEFAULT NULL COMMENT '软件的版本名称',
  `createTime` datetime DEFAULT NULL COMMENT '激活时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `imei` (`imei`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='设备信息表';

-- ----------------------------
-- Table structure for t_feedback_attach
-- ----------------------------
DROP TABLE IF EXISTS `t_feedback_attach`;
CREATE TABLE `t_feedback_attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `feedbackId` int(11) DEFAULT NULL COMMENT '所属的反馈的id',
  `filename` varchar(255) DEFAULT NULL COMMENT '文件名',
  `localPath` varchar(255) DEFAULT NULL COMMENT '本地存放路径',
  `mime` varchar(255) DEFAULT NULL COMMENT '文件的mime类型',
  `size` double DEFAULT NULL COMMENT '文件的大小',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='反馈的附件';

-- ----------------------------
-- Table structure for t_feedbak_info
-- ----------------------------
DROP TABLE IF EXISTS `t_feedbak_info`;
CREATE TABLE `t_feedbak_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userSid` varchar(255) DEFAULT NULL COMMENT '关联用户的sid',
  `content` varchar(1000) NOT NULL COMMENT '反馈的内容',
  `imei` varchar(255) DEFAULT NULL COMMENT '手机的imei号',
  `os` varchar(255) DEFAULT NULL COMMENT '系统的类型，Android、IOS等',
  `osVersion` varchar(255) DEFAULT NULL COMMENT '系统的版本号',
  `phoneModel` varchar(255) DEFAULT NULL COMMENT '手机的型号',
  `brand` varchar(255) DEFAULT NULL COMMENT '手机的品牌商',
  `appVersionCode` int(11) DEFAULT NULL COMMENT '客户端的版本号',
  `appVersionName` varchar(255) DEFAULT NULL COMMENT '客户端的版本名称',
  `contactWay` varchar(255) DEFAULT NULL COMMENT '用户的联系方式，可能为QQ、邮箱、手机号',
  `createTime` datetime DEFAULT NULL COMMENT '反馈时间',
  `state` tinyint(255) DEFAULT NULL COMMENT '解决状态，0：未解决；1、已解决',
  PRIMARY KEY (`id`),
  KEY `userSid` (`userSid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户反馈的问题或者建议';

-- ----------------------------
-- Table structure for t_folder
-- ----------------------------
DROP TABLE IF EXISTS `t_folder`;
CREATE TABLE `t_folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sid` varchar(255) DEFAULT NULL COMMENT '唯一值',
  `userId` int(11) DEFAULT NULL COMMENT '关联用户表的id',
  `name` varchar(255) NOT NULL COMMENT '文件夹的名称',
  `isLock` tinyint(4) DEFAULT NULL COMMENT '是否被锁定',
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `deleteState` tinyint(4) DEFAULT NULL COMMENT '删除状态，0：没有删除,1:删除到垃圾桶，2：隐藏，3：完全删除',
  `createTime` datetime DEFAULT NULL COMMENT '创建时时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `count` int(11) DEFAULT '0' COMMENT '改文件夹下可用笔记的数量',
  `hash` varchar(255) DEFAULT NULL COMMENT '笔记本的hash值，由name;isLock;sort;deleteState的格式组成，顺序不能错',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sid` (`sid`) USING BTREE,
  KEY `userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='笔记本的表';

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
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='笔记表';

-- ----------------------------
-- Table structure for t_open_api
-- ----------------------------
DROP TABLE IF EXISTS `t_open_api`;
CREATE TABLE `t_open_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '关联用户表的id',
  `openUserId` varchar(255) DEFAULT NULL COMMENT '关联用户表的id',
  `token` varchar(255) NOT NULL COMMENT '关联用户表的id',
  `expiresTime` bigint(20) DEFAULT NULL COMMENT 'token过期的时间',
  `type` tinyint(4) DEFAULT NULL COMMENT '第三方账号的类型，1：微信，2：QQ，3：微博',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openUserId` (`openUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方账号的表';

-- ----------------------------
-- Table structure for t_reset_pwd
-- ----------------------------
DROP TABLE IF EXISTS `t_reset_pwd`;
CREATE TABLE `t_reset_pwd` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userSid` varchar(255) DEFAULT NULL COMMENT '关联用户的sid',
  `account` varchar(255) DEFAULT NULL COMMENT '账号，一般是邮箱，以后可能是手机号',
  `validataCode` varchar(255) DEFAULT NULL COMMENT '加密后的校验码',
  `outDate` timestamp NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userSid` (`userSid`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='重置密码的记录表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(255) DEFAULT NULL COMMENT '用户的唯一Id,手动生成',
  `username` varchar(255) DEFAULT NULL COMMENT '账号，唯一ID',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号，可用此登录',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `nickname` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '本地的头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '本地的头像',
  `createTime` datetime DEFAULT NULL COMMENT '用户的创建时间',
  `state` tinyint(4) DEFAULT NULL COMMENT '状态，0：正常，1：停用',
  `avatarHash` varchar(255) DEFAULT NULL COMMENT '用户头像的hash值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `sid` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for t_version_info
-- ----------------------------
DROP TABLE IF EXISTS `t_version_info`;
CREATE TABLE `t_version_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(255) NOT NULL COMMENT '更新的日志',
  `versionCode` int(11) DEFAULT NULL COMMENT '软件的版本号',
  `versionName` varchar(255) DEFAULT NULL COMMENT '版本名称',
  `platform` tinyint(4) DEFAULT NULL COMMENT '系统平台，0:Android, 1:IOS',
  `createTime` datetime DEFAULT NULL COMMENT '更新时间',
  `isMilestone` tinyint(255) DEFAULT NULL COMMENT '是否是里程碑',
  `size` double DEFAULT NULL COMMENT '软件包的大小',
  `localPath` varchar(255) DEFAULT NULL COMMENT '软件的本地路径',
  `hash` varchar(255) DEFAULT NULL COMMENT '文件的md5 hash值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='软件的更新记录表';
DROP TRIGGER IF EXISTS `tri_delete_feedback`;
DELIMITER ;;
CREATE TRIGGER `tri_delete_feedback` AFTER DELETE ON `t_feedbak_info` FOR EACH ROW BEGIN
	DELETE FROM t_feedback_attach WHERE feedbackId = OLD.id;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `tri_trash_folder`;
DELIMITER ;;
CREATE TRIGGER `tri_trash_folder` AFTER UPDATE ON `t_folder` FOR EACH ROW BEGIN
	IF (OLD.deleteState IS NULL OR OLD.deleteState != 1) AND NEW.deleteState = 1 THEN
		UPDATE t_note_info SET deleteState = 1, modify_time = NEW.modifyTime WHERE folderSid = NEW.sid;
	ELSEIF (OLD.deleteState IS NULL AND OLD.deleteState != 0) AND NEW.deleteState = 0 THEN
		UPDATE t_note_info SET deleteState = 0, modify_time = NEW.modifyTime WHERE folderSid = NEW.sid;
	END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `tri_insert_note`;
DELIMITER ;;
CREATE TRIGGER `tri_insert_note` AFTER INSERT ON `t_note_info` FOR EACH ROW BEGIN  
         IF NEW.folderSid IS NOT NULL THEN 
                   UPDATE t_folder SET modifyTime = NEW.createTime, count = if(count is null, 1, count + 1) WHERE sid = NEW.folderSid;
         END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `tri_update_folder`;
DELIMITER ;;
CREATE TRIGGER `tri_update_folder` AFTER UPDATE ON `t_note_info` FOR EACH ROW BEGIN 
	IF NEW.folderSid IS NOT NULL AND OLD.deleteState != 0 AND NEW.deleteState = 0 THEN
		UPDATE t_folder SET modifyTime = NEW.modifyTime, count = if(count is null, 1, count + 1)  WHERE sid = NEW.folderSid;
	ELSEIF NEW.folderSid IS NOT NULL AND OLD.deleteState = 0 AND NEW.deleteState != 0 THEN
		UPDATE t_folder SET modifyTime = NEW.modifyTime, count = if(count > 0, count - 1, 0)  WHERE sid = NEW.folderSid;
	ELSE 
		UPDATE t_folder SET modifyTime = NEW.modifyTime WHERE sid = NEW.folderSid;
	END IF;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `tri_delete_note`;
DELIMITER ;;
CREATE TRIGGER `tri_delete_note` AFTER DELETE ON `t_note_info` FOR EACH ROW BEGIN
	DELETE FROM t_attach WHERE noteSid = OLD.sid;
	DELETE FROM t_detail_list WHERE noteSid = OLD.sid;
END
;;
DELIMITER ;
