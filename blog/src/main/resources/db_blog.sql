/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : db_blog

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2017-12-19 20:43:55
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_blog`
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '博客类型',
  `title` varchar(200) NOT NULL COMMENT '博客题目',
  `summary` varchar(400) DEFAULT NULL COMMENT '博客摘要',
  `releaseDate` datetime DEFAULT NULL COMMENT '发布日期',
  `clickHit` int(11) DEFAULT NULL COMMENT '评论次数',
  `replyHit` int(11) DEFAULT NULL COMMENT '回复次数',
  `content` longtext COMMENT '博客内容',
  `keyWord` varchar(200) DEFAULT NULL COMMENT '关键字',
  `type_id` int(11) DEFAULT NULL COMMENT '外键关联博客类别',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `t_blog_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `t_blogtype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_blog
-- ----------------------------
INSERT INTO `t_blog` VALUES ('50', '????', '', '2017-12-19 12:29:04', '21', '0', '<p><img src=\"/uploads/20171210211225185.png\" alt=\"20171210211225185.png\"></p><p><img src=\"/uploads/20171210211230974.png\" alt=\"20171210211230974.png\"><br></p><p><img src=\"/uploads/2017121021123596.png\" alt=\"2017121021123596.png\"><br></p>', '????', '3');
INSERT INTO `t_blog` VALUES ('51', '?????', '????� ???� ???� ???��', '2017-12-17 19:19:08', '4', '0', '<p><b>????&nbsp; <i>???&nbsp; <u>???&nbsp; <strike>???&nbsp;<img src=\"http://localhost:8443/layui/images/face/0.gif\" alt=\"[??]\">&nbsp;</strike></u></i></b></p><p><b><i><u><strike><img src=\"/uploads/20171211080040247.png\" alt=\"20171211080040247.png\"><br></strike></u></i></b></p><b><i><u><strike></strike></u></i></b>', '?????', '7');
INSERT INTO `t_blog` VALUES ('62', '??????????', '1', '2017-12-19 20:27:32', '9', '0', '<img src=\"../uploads/20171213095632764.png\" alt=\"20171213095632764.png\">1', '11', '1');
INSERT INTO `t_blog` VALUES ('71', '????', '??', '2017-12-19 19:47:32', '7', '0', '??<img src=\"../../uploads/201712151616d6-577611f647ae.png\" alt=\"201712151616d6-577611f647ae.png\">', '??', '6');
INSERT INTO `t_blog` VALUES ('73', '??????PC', '??????????????', '2017-12-19 20:27:51', '8', '0', '<p><img src=\"../../uploads/2017121718105d-9b70ca489be7.png\" alt=\"2017121718105d-9b70ca489be7.png\"></p><p>?????<img src=\"../../uploads/20171217181060-eecdc5a00c49.png\" alt=\"20171217181060-eecdc5a00c49.png\"></p><p><br></p><p><b>????</b></p><p><b><br></b></p><p><b><u><strike>?????</strike></u></b></p>', '?????', '2');
INSERT INTO `t_blog` VALUES ('74', '??????', '??', '2017-12-19 12:45:58', '6', '0', '<img src=\"https://localhost/layui/images/face/1.gif\" alt=\"[??]\">??', '??', '1');
INSERT INTO `t_blog` VALUES ('75', 'dddddddd??ddd??', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', '2017-12-19 20:27:40', '2', '0', '<p>ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??</p>', 'dd', '1');
INSERT INTO `t_blog` VALUES ('76', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', '2017-12-19 11:53:33', '1', '0', '<p>ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??</p>', 'vv', '1');
INSERT INTO `t_blog` VALUES ('77', 'ddd??ddd??', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', '2017-12-19 11:44:36', '0', '0', '<p>ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??</p>', '767', '5');
INSERT INTO `t_blog` VALUES ('78', 'ddd??ddd??ddd??ddd??', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', '2017-12-19 11:44:45', '0', '0', '<p>ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??</p>', '666', '9');
INSERT INTO `t_blog` VALUES ('79', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', '2017-12-19 11:45:35', '0', '0', '<p>ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??</p>', 'dd', '4');
INSERT INTO `t_blog` VALUES ('80', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', '2017-12-19 11:45:40', '0', '0', '<p>ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??</p>', 'ddd??ddd??ddd??ddd??ddd??ddd??ddd??ddd??', '4');
INSERT INTO `t_blog` VALUES ('81', '?????????', '???', '2017-12-19 20:27:34', '3', '1', '???', '??', '1');

-- ----------------------------
-- Table structure for `t_blogger`
-- ----------------------------
DROP TABLE IF EXISTS `t_blogger`;
CREATE TABLE `t_blogger` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '??id',
  `username` varchar(50) NOT NULL COMMENT '????',
  `password` varchar(100) NOT NULL COMMENT '????',
  `profile` longtext COMMENT '????',
  `nickname` varchar(50) DEFAULT NULL COMMENT '????',
  `sign` varchar(255) DEFAULT NULL COMMENT '????',
  `imagename` varchar(255) DEFAULT NULL COMMENT '??????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_blogger
-- ----------------------------
INSERT INTO `t_blogger` VALUES ('1', 'admin', 'b3a927205c4aabc80130d0e7269abc8b', '<img src=\"../uploads/20171213093105442.png\" alt=\"20171213093105442.png\">??', '????', '???????????11212', '20171213093102514.png');

-- ----------------------------
-- Table structure for `t_blogtype`
-- ----------------------------
DROP TABLE IF EXISTS `t_blogtype`;
CREATE TABLE `t_blogtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '??id',
  `type_name` varchar(30) DEFAULT NULL COMMENT '????',
  `order_num` int(11) DEFAULT NULL COMMENT '????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_blogtype
-- ----------------------------
INSERT INTO `t_blogtype` VALUES ('1', 'java', '2');
INSERT INTO `t_blogtype` VALUES ('2', 'javaee', '2');
INSERT INTO `t_blogtype` VALUES ('3', 'javame', '55');
INSERT INTO `t_blogtype` VALUES ('4', 'javase', '4');
INSERT INTO `t_blogtype` VALUES ('5', 'Spring', '7');
INSERT INTO `t_blogtype` VALUES ('6', 'SpringBoot', '8');
INSERT INTO `t_blogtype` VALUES ('7', 'Mybatis', '9');
INSERT INTO `t_blogtype` VALUES ('8', 'SpringMVC', '10');
INSERT INTO `t_blogtype` VALUES ('9', 'Thymeleaf', '10');
INSERT INTO `t_blogtype` VALUES ('14', '????', '58');

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_ip` varchar(50) DEFAULT NULL,
  `blog_id` int(11) DEFAULT NULL,
  `content` text,
  `comment_date` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES ('51', '0:0:0:0:0:0:0:1', '81', '????', '2017-12-19 12:47:07', '1', '??');
INSERT INTO `t_comment` VALUES ('52', '0:0:0:0:0:0:0:1', '81', 'ddddd', '2017-12-19 12:47:23', '2', '??');
INSERT INTO `t_comment` VALUES ('53', '0:0:0:0:0:0:0:1', '71', '2222', '2017-12-19 19:50:29', '0', '??');

-- ----------------------------
-- Table structure for `t_great`
-- ----------------------------
DROP TABLE IF EXISTS `t_great`;
CREATE TABLE `t_great` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_ip` varchar(50) NOT NULL,
  `image_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=402 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_great
-- ----------------------------
INSERT INTO `t_great` VALUES ('395', '', '31');
INSERT INTO `t_great` VALUES ('400', '', '33');
INSERT INTO `t_great` VALUES ('401', '0:0:0:0:0:0:0:1', '31');

-- ----------------------------
-- Table structure for `t_link`
-- ----------------------------
DROP TABLE IF EXISTS `t_link`;
CREATE TABLE `t_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `link_name` varchar(100) DEFAULT NULL,
  `link_url` varchar(200) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_link
-- ----------------------------
INSERT INTO `t_link` VALUES ('1', '??', 'http://www.baidu.com', '1');
INSERT INTO `t_link` VALUES ('2', '??', 'http://www.sougou.com', '1');

-- ----------------------------
-- Table structure for `t_message`
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_ip` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `content` text,
  `message_date` datetime DEFAULT NULL,
  `state` int(4) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_message
-- ----------------------------
INSERT INTO `t_message` VALUES ('25', '127.0.0.1', '568845948@qq.com', 'ds', '2017-12-11 12:57:54', '1', '??IP');
INSERT INTO `t_message` VALUES ('27', '0:0:0:0:0:0:0:1', '568845948@qq.com', '??????', '2017-12-13 10:52:34', '1', '??');
INSERT INTO `t_message` VALUES ('35', '0:0:0:0:0:0:0:1', '568845948@qq.com', 'ddd<img src=\"https://localhost/layui/images/face/0.gif\" alt=\"[??]\">', '2017-12-15 13:53:46', '1', '??');
INSERT INTO `t_message` VALUES ('40', '0:0:0:0:0:0:0:1', '', 'dsasddd', '2017-12-18 08:57:08', '1', '??');
INSERT INTO `t_message` VALUES ('41', '0:0:0:0:0:0:0:1', '', '23333333', '2017-12-19 10:34:50', '2', '??');
INSERT INTO `t_message` VALUES ('42', '0:0:0:0:0:0:0:1', '', 'sdad', '2017-12-19 10:50:21', '1', '??');
INSERT INTO `t_message` VALUES ('43', '0:0:0:0:0:0:0:1', '', 'dddd', '2017-12-19 12:08:09', '2', '??');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(250) NOT NULL,
  `notice_publisher` varchar(100) NOT NULL,
  `notice_date` datetime NOT NULL,
  `level` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('13', '????Ip???????????????IP???????0-0', 'admin', '2017-12-11 12:03:47', '2');
INSERT INTO `t_notice` VALUES ('15', '???????????', 'admin', '2017-12-11 12:28:31', '2');
INSERT INTO `t_notice` VALUES ('23', '????????', 'admin', '2017-12-17 18:59:34', '2');
INSERT INTO `t_notice` VALUES ('24', '???', 'admin', '2017-12-19 19:06:29', '1');

-- ----------------------------
-- Table structure for `t_picture`
-- ----------------------------
DROP TABLE IF EXISTS `t_picture`;
CREATE TABLE `t_picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) NOT NULL,
  `publisher` varchar(100) NOT NULL,
  `date` datetime NOT NULL,
  `click` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_picture
-- ----------------------------
INSERT INTO `t_picture` VALUES ('31', '../uploads/201712151509b0-046f6ddac9b2.jpg', 'admin', '2017-12-15 15:42:19', '2', '??1');
INSERT INTO `t_picture` VALUES ('33', '../uploads/20171215150999-40e668e6a16e.png', 'admin', '2017-12-15 15:43:55', '1', '????????????');
INSERT INTO `t_picture` VALUES ('44', '../uploads/201712151708d4-54bda219200f.png', 'admin', '2017-12-17 19:00:36', '0', '????');
INSERT INTO `t_picture` VALUES ('46', '../uploads/20171215170835-fd7590814c33.png', 'admin', '2017-12-17 19:00:38', '0', '????');
INSERT INTO `t_picture` VALUES ('48', '../uploads/20171215171406-b5abff02c3d9.jpg', 'admin', '2017-12-17 19:00:40', '0', '????');
INSERT INTO `t_picture` VALUES ('50', '../uploads/201712151714ab-65d3979eb1fa.jpg', 'admin', '2017-12-17 19:00:42', '0', '????');
INSERT INTO `t_picture` VALUES ('52', '../uploads/201712151853cf-03adbc0acafe.png', 'admin', '2017-12-17 19:00:45', '0', '????');
INSERT INTO `t_picture` VALUES ('54', '../uploads/20171215185375-9abc5f2a2a0c.png', 'admin', '2017-12-17 19:00:47', '0', '????');
INSERT INTO `t_picture` VALUES ('56', '../uploads/20171215185385-03f88babbe2f.png', 'admin', '2017-12-17 19:00:49', '0', '????');
INSERT INTO `t_picture` VALUES ('58', '../uploads/20171215185601-e55d4448182a.png', 'admin', '2017-12-17 19:00:52', '0', '????');
INSERT INTO `t_picture` VALUES ('59', '../uploads/20171215185616-251327aa5813.png', 'admin', '2017-12-17 19:01:26', '0', '??2');
