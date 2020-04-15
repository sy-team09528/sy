

DROP TABLE IF EXISTS `t_a_member`;

CREATE TABLE `t_a_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `pwd` varchar(200) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `true_name` varchar(50) DEFAULT NULL,
  `balance` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_a_member` */

insert  into `t_a_member`(`id`,`create_date_time`,`name`,`phone`,`pwd`,`sex`,`true_name`,`balance`) values (2,'2019-12-30 10:12:16','5566','18337537525','123456',2,'小明11144411',NULL);

/*Table structure for table `t_a_role` */

DROP TABLE IF EXISTS `t_a_role`;

CREATE TABLE `t_a_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `order_no` int(11) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_a_role` */

insert  into `t_a_role`(`id`,`create_date_time`,`name`,`order_no`,`remark`,`update_date_time`) values (3,'2019-02-20 11:35:54','管理员',1,NULL,'2019-12-31 12:34:52'),(4,'2019-07-04 23:52:50','普通角色',2,'','2019-07-04 23:52:56');

/*Table structure for table `t_a_type` */

DROP TABLE IF EXISTS `t_a_type`;

CREATE TABLE `t_a_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `sort` int(11) NOT NULL,
  `use_it` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_a_type` */

insert  into `t_a_type`(`id`,`name`,`sort`,`use_it`) values (1,'恐怖类型',1,1),(2,'古典类型',2,1),(3,'动漫类型',3,1);

/*Table structure for table `t_a_user` */

DROP TABLE IF EXISTS `t_a_user`;

CREATE TABLE `t_a_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `order_no` int(11) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `true_name` varchar(200) NOT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK76cs7cu4h4y8vc1vd4qyw36rt` (`role_id`),
  CONSTRAINT `FK76cs7cu4h4y8vc1vd4qyw36rt` FOREIGN KEY (`role_id`) REFERENCES `t_a_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_a_user` */

insert  into `t_a_user`(`id`,`create_date_time`,`name`,`order_no`,`pwd`,`remark`,`true_name`,`update_date_time`,`role_id`) values (1,'2019-02-18 08:40:27','admin',1,'ba61ce8fa1e3725876e6363c76043c8d',NULL,'管理-小明','2019-07-04 23:53:30',3);

/*Table structure for table `t_blog` */

DROP TABLE IF EXISTS `t_blog`;

CREATE TABLE `t_blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `click_hit` int(11) DEFAULT NULL,
  `content` longtext,
  `create_date_time` datetime DEFAULT NULL,
  `summary` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_blog` */

insert  into `t_blog`(`id`,`click_hit`,`content`,`create_date_time`,`summary`,`title`) values (1,7,'<p style=\"margin-top: 26px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\"><span class=\"bjh-p\">作者|钱洛滢</span></p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\"><span class=\"bjh-p\"><span class=\"bjh-strong\" style=\"font-size: 18px; font-weight: 700;\">华为</span>游戏在这个周末正式和网易游戏开“撕”了。</span></p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\"><span class=\"bjh-p\">12月28日，有玩家称网易游戏《阴阳师：百闻牌》、《明日之后》等多款游戏在OPPO渠道服内开启了“高比例”充值返利活动。华为游戏中心收到用户反馈后，认为此行为给华为玩家造成了严重不公平的对待，华为方面称在多次与网易沟通无果后，决定自掏腰包给玩家做返利活动。</span></p><p><img class=\"normal\" width=\"323px\" src=\"/static/ueditor_upload/catchimage/2019-12-31/181130030.jpg\"/></p><p><br/></p>','2019-12-31 12:34:57','作者|钱洛滢华为游戏在这个周末正式和网易游戏开“撕”了。12月28日，有玩家称网易游戏《阴阳师：百闻牌》、《明日之后》等多款游戏在OPPO渠道服内开启了“高比例”充值返利活动。华为游戏中心收到用户反馈后，认为此行为给华为玩家造成了严重不公平的对待，华为方面称在多次与网易沟通无果后，决定自掏腰包给玩家做返利活动','关于我们');

/*Table structure for table `t_config` */

DROP TABLE IF EXISTS `t_config`;

CREATE TABLE `t_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `web_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_config` */

insert  into `t_config`(`id`,`web_name`) values (1,'cosplay服装租赁系统');

/*Table structure for table `t_goods` */

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `click_hit` int(11) DEFAULT NULL,
  `content` longtext,
  `create_date_time` datetime DEFAULT NULL,
  `image_url` varchar(200) DEFAULT NULL,
  `summary` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `use_it` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8mm05uwi79ypjkclvc7ulxopl` (`type_id`),
  CONSTRAINT `FK8mm05uwi79ypjkclvc7ulxopl` FOREIGN KEY (`type_id`) REFERENCES `t_a_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_goods` */

insert  into `t_goods`(`id`,`click_hit`,`content`,`create_date_time`,`image_url`,`summary`,`title`,`use_it`,`type_id`,`price`) values (1,21,'<p><img src=\"/static/ueditor_upload/catchimage/2019-12-31/174023164.jpg\"/></p>','2019-12-27 20:56:14','/static/upload_image/goods_cover/20200312/20200312130711.jpg','','JackJones杰克琼斯连帽可拆两穿修身西服装外套',1,2,'56.00'),(2,2,'<p><br/><img src=\"/static/ueditor_upload/catchimage/2019-12-31/173945400.jpg\"/></p>','2019-12-28 11:11:52','/static/upload_image/goods_cover/20200312/20200312130734.jpg','','卫衣套装春季男士2019',1,2,'56.00'),(3,9,'<p><img src=\"/static/ueditor_upload/catchimage/2019-12-31/173802280.jpg\"/></p>','2019-12-28 11:12:22','/static/upload_image/goods_cover/20200312/20200312130756.jpg','','迪卡侬旗舰冲锋上衣男女防风外套',1,2,'3.00'),(4,2,'<p><img src=\"/static/ueditor_upload/catchimage/2019-12-31/173634673.jpg\"/></p>','2019-12-28 11:12:45','/static/upload_image/goods_cover/20200312/20200312130807.jpg','','骆驼户外冲锋衣男士潮牌 ',1,2,'598.00');

/*Table structure for table `t_lunbo` */

DROP TABLE IF EXISTS `t_lunbo`;

CREATE TABLE `t_lunbo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `image_url` varchar(200) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `pos` int(11) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `use_it` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `t_lunbo` */

insert  into `t_lunbo`(`id`,`create_date_time`,`image_url`,`name`,`pos`,`sort`,`update_date_time`,`url`,`use_it`) values (1,'2019-12-22 10:03:53','/static/upload_image/lunbo_cover/20191223/20191223223805.jpg','1',1,1,'2019-12-31 17:52:57','/goods/1',1),(2,'2019-12-22 10:07:01','/static/upload_image/lunbo_cover/20191223/20191223223808.jpg','2',1,2,'2019-12-31 17:52:59','/goods/1',1),(4,'2019-12-22 10:19:04','/static/upload_image/lunbo_cover/20200312/20200312131158.jpg','2',2,2,'2020-03-12 13:11:59','/goods/1',1),(5,'2019-12-22 10:19:30','/static/upload_image/lunbo_cover/20200312/20200312131204.jpg','3',2,3,'2020-03-12 13:12:05','/goods/1',1),(7,'2019-12-22 21:46:03','/static/upload_image/lunbo_cover/20200312/20200312131214.jpg','4',2,4,'2020-03-12 13:12:16','/goods/1',1);

/*Table structure for table `t_menu` */

DROP TABLE IF EXISTS `t_menu`;

CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  `permissions` varchar(100) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `t_menu` */

insert  into `t_menu`(`id`,`icon`,`name`,`p_id`,`permissions`,`state`,`type`,`url`,`order_no`) values (1,'','基本维护',-1,'',0,0,'',1),(2,'&#xe67a;','用户管理',1,'用户管理',1,0,'/houtai/user/manage',1),(3,'','功能',-1,'',0,0,'',2),(5,'&#xe67a;','角色管理',1,'角色管理',1,0,'/houtai/role/manage',2),(6,'&#xe67a;','菜单管理',1,'菜单管理',1,0,'/houtai/menu/manage?pId=-1',3),(7,'&#xe67a;','一级导航',3,'一级导航',1,0,'/houtai/nav/manage',7),(10,'&#xe67a;','网站配置',1,'网站配置',1,0,'/houtai/config/manage',4),(12,'&#xe67a;','会员管理',3,'会员管理',1,0,'/houtai/member/manage',15),(15,'&#xe67a;','商品管理',3,'商品管理',1,0,'/houtai/goods/manage',2),(16,'&#xe67a;','二级导航',3,'二级导航',1,0,'/houtai/nav2/manage',9),(18,'&#xe67a;','首页轮播',3,'首页轮播',1,0,'/houtai/lunbo/manage',3),(19,'&#xe67a;','首页图片',3,'首页图片',1,0,'/houtai/lunbo/manage2',4),(20,'&#xe67a;','类型',3,'类型',0,0,'/houtai/type/manage',1),(21,'&#xe67a;','内容管理',3,'内容管理',1,0,'/houtai/blog/manage',13);

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1x2939ojad7h7xnksxxb1bddq` (`member_id`),
  CONSTRAINT `FK1x2939ojad7h7xnksxxb1bddq` FOREIGN KEY (`member_id`) REFERENCES `t_a_member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_message` */

insert  into `t_message`(`id`,`content`,`create_date_time`,`member_id`) values (1,'123','2019-12-31 19:50:41',2),(2,'5435345','2019-12-31 19:53:41',2);

/*Table structure for table `t_nav` */

DROP TABLE IF EXISTS `t_nav`;

CREATE TABLE `t_nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `sort` int(11) NOT NULL,
  `state` varchar(10) DEFAULT NULL,
  `nav_id` int(11) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `image_url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnsoq6qqimy9t3jhn6eymh790p` (`nav_id`),
  CONSTRAINT `FKnsoq6qqimy9t3jhn6eymh790p` FOREIGN KEY (`nav_id`) REFERENCES `t_nav` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `t_nav` */

insert  into `t_nav`(`id`,`create_date_time`,`name`,`sort`,`state`,`nav_id`,`url`,`image_url`) values (2,'2019-12-18 19:36:33','首页',1,'1',NULL,'/index','/static/upload_image/nav_cover/20191219/20191219160931.jpg'),(3,'2019-12-18 19:36:58','xx衣服',2,'1',NULL,'/type/2','/static/upload_image/nav_cover/20191219/20191219160950.jpg'),(5,'2019-12-18 19:53:44','003衣服',3,'1',NULL,'/type/1','/static/upload_image/nav_cover/20191219/20191219160957.jpg'),(6,'2019-12-18 21:40:04','我的购物车',9,'1',NULL,'/shopping/cart/index','/static/upload_image/nav_cover/20191219/20191219161015.jpg'),(15,'2019-12-31 18:00:52','关于我们',4,'1',NULL,'/blog/1',NULL),(16,'2019-12-31 18:28:39','留言板',5,'1',NULL,'/message/index',NULL);

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(200) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `num` varchar(100) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `true_name` varchar(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm5vx5w91jqr9imluee5p6l83n` (`member_id`),
  CONSTRAINT `FKm5vx5w91jqr9imluee5p6l83n` FOREIGN KEY (`member_id`) REFERENCES `t_a_member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_order` */

insert  into `t_order`(`id`,`address`,`amount`,`create_date_time`,`num`,`phone`,`state`,`title`,`true_name`,`member_id`,`total`) values (1,'3213123','601.00','2020-01-09 20:27:14','20200109202713877','12312312311',1,'大功率墙壁线管水电安装工电线切割机133开槽机一次成型无尘工具,数量:1        sata世达工具万用表数显式万用表带频率水电工专用表笔03005-07,数量:1        ','234',2,2);

/*Table structure for table `t_order_info` */

DROP TABLE IF EXISTS `t_order_info`;

CREATE TABLE `t_order_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(200) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `type_name` varchar(50) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKli0isfi6l4uixky5pp7fotekv` (`order_id`),
  CONSTRAINT `FKli0isfi6l4uixky5pp7fotekv` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_order_info` */

insert  into `t_order_info`(`id`,`image_url`,`num`,`price`,`title`,`type_name`,`order_id`) values (1,'/static/upload_image/goods_cover/20191231/20191231173733.jpg',1,'598.00','大功率墙壁线管水电安装工电线切割机133开槽机一次成型无尘工具','水电材料',1),(2,'/static/upload_image/goods_cover/20191231/20191231173827.jpg',1,'3.00','sata世达工具万用表数显式万用表带频率水电工专用表笔03005-07','水电材料',1);

/*Table structure for table `t_role_menu` */

DROP TABLE IF EXISTS `t_role_menu`;

CREATE TABLE `t_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhayg4ib6v7h1wyeyxhq6xlddq` (`menu_id`),
  KEY `FKsonb0rbt2u99hbrqqvv3r0wse` (`role_id`),
  CONSTRAINT `FKhayg4ib6v7h1wyeyxhq6xlddq` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`id`),
  CONSTRAINT `FKsonb0rbt2u99hbrqqvv3r0wse` FOREIGN KEY (`role_id`) REFERENCES `t_a_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=utf8;

/*Data for the table `t_role_menu` */

insert  into `t_role_menu`(`id`,`menu_id`,`role_id`) values (74,1,4),(75,2,4),(76,5,4),(77,6,4),(280,1,3),(281,2,3),(282,5,3),(283,6,3),(284,10,3),(285,3,3),(286,20,3),(287,15,3),(288,18,3),(289,19,3),(290,7,3),(291,16,3),(292,21,3),(293,12,3);

/*Table structure for table `t_shopping_cart` */

DROP TABLE IF EXISTS `t_shopping_cart`;

CREATE TABLE `t_shopping_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date_time` datetime DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnqim1pluaj5gte7lqpcfxu6qe` (`member_id`),
  KEY `FKnvdy6q540pao9dxiotedkol5e` (`goods_id`),
  CONSTRAINT `FKnqim1pluaj5gte7lqpcfxu6qe` FOREIGN KEY (`member_id`) REFERENCES `t_a_member` (`id`),
  CONSTRAINT `FKnvdy6q540pao9dxiotedkol5e` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `t_shopping_cart` */

insert  into `t_shopping_cart`(`id`,`create_date_time`,`member_id`,`goods_id`) values (6,'2019-12-30 12:42:46',2,4),(7,'2019-12-30 12:42:47',2,3),(8,'2019-12-30 12:42:49',2,2),(9,'2019-12-30 12:42:51',2,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
