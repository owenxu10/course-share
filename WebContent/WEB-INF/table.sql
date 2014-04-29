/*
SQLyog v10.2 
MySQL - 5.5.28 : Database - courseshare
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `knowledge` */

DROP TABLE IF EXISTS `knowledge`;

CREATE TABLE `knowledge` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `explanation` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `knowledge_map` */

DROP TABLE IF EXISTS `knowledge_map`;

CREATE TABLE `knowledge_map` (
  `knowledge_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `problem_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `problem` */

DROP TABLE IF EXISTS `problem`;

CREATE TABLE `problem` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `problem_type` varchar(20) COLLATE utf8_bin NOT NULL,
  `problem_media_type` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'swf',
  `problem_file_format` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'doc',
  `problem_url` varchar(1000) COLLATE utf8_bin NOT NULL DEFAULT 'html',
  `problem_content` varchar(2000) COLLATE utf8_bin NOT NULL,
  `difficulty` int(11) NOT NULL,
  `key_media_type` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'swf',
  `key_file_format` varchar(20) COLLATE utf8_bin NOT NULL  DEFAULT 'doc',
  `key_url` varchar(1000) COLLATE utf8_bin NOT NULL DEFAULT 'html', 
  `key_content` varchar(2000) COLLATE utf8_bin NOT NULL,
  `knowledge` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `description` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `media_type` varchar(20) COLLATE utf8_bin NOT NULL,
  `file_format` varchar(20) COLLATE utf8_bin NOT NULL,
  `file_size` double NOT NULL,
  `link_type` varchar(20) COLLATE utf8_bin NOT NULL,
  `url` varchar(1000) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `email` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `problem_resource`;

CREATE TABLE `problem_resource` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uri` varchar(50) COLLATE utf8_bin NOT NULL,
  `type` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `knowledge` varchar(45) NOT NULL,
  `url` varchar(45) NOT NULL,
  `user_id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
SELECT * FROM `course-share`.image;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
