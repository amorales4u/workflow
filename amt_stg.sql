-- MySQL dump 10.13  Distrib 5.7.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: amt_stg
-- ------------------------------------------------------
-- Server version	5.7.30-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `c20_global_word`
--

DROP TABLE IF EXISTS `c20_global_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_global_word` (
  `c20_word` varchar(255) NOT NULL,
  PRIMARY KEY (`c20_word`),
  KEY `c20_global_word_c20_word_index` (`c20_word`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_global_word`
--

LOCK TABLES `c20_global_word` WRITE;
/*!40000 ALTER TABLE `c20_global_word` DISABLE KEYS */;
INSERT INTO `c20_global_word` VALUES ('a'),('aceptada'),('Aceptar'),('atender'),('business'),('c20'),('cancela'),('cancelar'),('carpeta'),('correo'),('de'),('definido'),('demo'),('dev'),('email'),('en'),('engine'),('entities'),('enviar'),('error'),('es'),('esta'),('fact'),('facts'),('gotoaceptar'),('gotocancelar'),('gotoporatender'),('groovyfactservice'),('groovystringfactservice'),('group'),('groups'),('inicio'),('la'),('Manda'),('mover'),('Mueve'),('no'),('nohayfactporresolver'),('para'),('por'),('pues'),('que'),('reglas'),('Regresa'),('resolver'),('reststorage'),('resuelve'),('rule'),('rules'),('se'),('sendemail'),('system'),('tarea'),('tiene'),('un'),('una'),('y');
/*!40000 ALTER TABLE `c20_global_word` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg`
--

DROP TABLE IF EXISTS `c20_stg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg` (
  `c20_stg` bigint(20) NOT NULL AUTO_INCREMENT,
  `c20_assigned` varchar(255) DEFAULT NULL,
  `c20_restricted_children_by_role` tinyint(4) DEFAULT NULL,
  `c20_clazz_name` varchar(255) DEFAULT NULL,
  `c20_created` datetime DEFAULT NULL,
  `c20_creator` varchar(255) DEFAULT NULL,
  `c20_date_assigned` datetime DEFAULT NULL,
  `c20_deleted` tinyint(4) DEFAULT NULL,
  `c20_delete_date` datetime DEFAULT NULL,
  `c20_description` varchar(200) DEFAULT NULL,
  `c20_extension` varchar(255) DEFAULT NULL,
  `c20_file_id` bigint(20) DEFAULT NULL,
  `c20_image` varchar(255) DEFAULT NULL,
  `c20_is_folder` tinyint(4) DEFAULT NULL,
  `c20_level` int(11) DEFAULT NULL,
  `c20_locked` tinyint(4) DEFAULT NULL,
  `c20_modified` tinyint(4) DEFAULT NULL,
  `c20_modifier` varchar(255) DEFAULT NULL,
  `c20_modify_date` datetime DEFAULT NULL,
  `c20_name` varchar(255) DEFAULT NULL,
  `c20_path` varchar(1000) DEFAULT NULL,
  `c20_readonly` tinyint(4) DEFAULT NULL,
  `c20_restricted_by_role` tinyint(4) DEFAULT NULL,
  `c20_status` int(11) DEFAULT NULL,
  `c20_deleter` varchar(255) DEFAULT NULL,
  `c20_visible` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`c20_stg`)
) ENGINE=MyISAM AUTO_INCREMENT=148 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg`
--

LOCK TABLES `c20_stg` WRITE;
/*!40000 ALTER TABLE `c20_stg` DISABLE KEYS */;
INSERT INTO `c20_stg` VALUES (42,NULL,0,'dev.c20.rules.engine.entities.Group','2021-05-11 16:19:07',NULL,NULL,0,NULL,NULL,NULL,NULL,'group',1,3,0,NULL,NULL,NULL,'Por cancelar','/system/business/groups/Por cancelar/',0,0,NULL,NULL,1),(43,NULL,0,'dev.c20.rules.engine.entities.Rule','2021-05-11 16:19:08',NULL,NULL,0,NULL,NULL,NULL,NULL,'rule',0,3,0,NULL,NULL,NULL,'Enviar tarea a Aceptar','/system/business/rules/Enviar tarea a Aceptar',0,0,NULL,NULL,0),(44,NULL,0,'dev.c20.rules.engine.entities.Rule','2021-05-11 16:19:08',NULL,NULL,0,NULL,NULL,NULL,NULL,'rule',0,3,0,NULL,NULL,NULL,'Es aceptada y tiene definido un email','/system/business/rules/Es aceptada y tiene definido un email',0,0,NULL,NULL,0),(45,NULL,0,'dev.c20.rules.engine.entities.Rule','2021-05-11 16:19:08',NULL,NULL,0,NULL,NULL,NULL,NULL,'rule',0,3,0,NULL,NULL,NULL,'Es aceptada y NO tiene email','/system/business/rules/Es aceptada y NO tiene email',0,0,NULL,NULL,0),(46,NULL,0,'dev.c20.rules.engine.entities.Rule','2021-05-11 16:19:08',NULL,NULL,0,NULL,NULL,NULL,NULL,'rule',0,3,0,NULL,NULL,NULL,'Mover tarea a Cancelar','/system/business/rules/Mover tarea a Cancelar',0,0,NULL,NULL,0),(47,NULL,0,'dev.c20.rules.engine.entities.Rule','2021-05-11 16:19:08',NULL,NULL,0,NULL,NULL,NULL,NULL,'rule',0,3,0,NULL,NULL,NULL,'Cancela una tarea','/system/business/rules/Cancela una tarea',0,0,NULL,NULL,0),(48,NULL,0,'dev.c20.rules.engine.entities.Group','2021-05-11 16:23:10',NULL,NULL,0,NULL,'Reglas para Mover una tarea que esta en \'Por Resolver\'',NULL,NULL,'group',1,3,0,NULL,NULL,NULL,'Por resolver','/system/business/groups/Por resolver/',0,0,NULL,NULL,1),(145,NULL,NULL,NULL,'2021-09-20 19:26:52','admin',NULL,0,NULL,'Carpeta del sistema',NULL,NULL,NULL,1,0,0,0,NULL,NULL,'Sistema','/Sistema/',0,0,NULL,NULL,1),(142,NULL,0,NULL,'2021-06-24 02:41:31',NULL,NULL,0,NULL,NULL,NULL,NULL,'group-rule',1,4,0,NULL,NULL,NULL,'Mover tarea a Cancelar','/system/business/groups/Por resolver/Mover tarea a Cancelar/',0,0,NULL,NULL,1),(143,NULL,0,NULL,'2021-06-24 02:41:31',NULL,NULL,0,NULL,NULL,NULL,NULL,'group-rule',1,5,0,NULL,NULL,NULL,'Cancela una tarea','/system/business/groups/Por resolver/Mover tarea a Cancelar/Cancela una tarea/',0,0,NULL,NULL,1),(141,NULL,0,NULL,'2021-06-24 02:41:31',NULL,NULL,0,NULL,NULL,NULL,NULL,'group-rule',1,5,0,NULL,NULL,NULL,'Es aceptada y NO tiene email','/system/business/groups/Por resolver/Enviar tarea a Aceptar/Es aceptada y NO tiene email/',0,0,NULL,NULL,1),(144,NULL,NULL,NULL,'2021-09-20 19:26:01','admin',NULL,0,NULL,'Carpeta del sistema',NULL,NULL,NULL,1,-1,0,0,NULL,NULL,'/','/',0,0,NULL,NULL,1),(140,NULL,0,NULL,'2021-06-24 02:41:31',NULL,NULL,0,NULL,NULL,NULL,NULL,'group-rule',1,5,0,NULL,NULL,NULL,'Es aceptada y tiene definido un email','/system/business/groups/Por resolver/Enviar tarea a Aceptar/Es aceptada y tiene definido un email/',0,0,NULL,NULL,1),(41,NULL,0,'dev.c20.rules.engine.entities.Group','2021-05-11 16:19:07',NULL,NULL,0,NULL,NULL,NULL,NULL,'group',1,3,0,NULL,NULL,NULL,'Inicio','/system/business/groups/Inicio/',0,0,NULL,NULL,1),(32,NULL,0,NULL,'2021-05-11 16:19:07',NULL,NULL,0,NULL,'Facts',NULL,NULL,NULL,1,2,0,NULL,NULL,NULL,'facts','/system/business/facts/',0,1,NULL,NULL,1),(33,NULL,0,NULL,'2021-05-11 16:19:07',NULL,NULL,0,NULL,'Rules',NULL,NULL,NULL,1,2,0,NULL,NULL,NULL,'rules','/system/business/rules/',0,0,NULL,NULL,1),(34,NULL,0,'dev.c20.rules.engine.entities.Group','2021-05-11 16:19:07',NULL,NULL,0,NULL,'Reglas para Mover una tarea que esta en \'Por Resolver\'',NULL,NULL,'group',1,2,0,NULL,NULL,NULL,'groups','/system/business/groups/',0,0,NULL,NULL,1),(35,NULL,0,'dev.c20.rules.engine.demo.facts.GroovyFactService','2021-05-11 16:19:07',NULL,NULL,0,NULL,'Mueve la tarea a la carpeta de Aceptar',NULL,NULL,'fact',0,3,0,NULL,NULL,NULL,'GoToAceptar','/system/business/facts/GoToAceptar',0,0,NULL,NULL,1),(36,NULL,0,'dev.c20.rules.engine.demo.facts.GroovyFactService','2021-05-11 16:19:07',NULL,NULL,0,NULL,'Mueve la tarea a la carpeta por atender',NULL,NULL,'fact',0,3,0,NULL,NULL,NULL,'GotoPorAtender','/system/business/facts/GotoPorAtender',0,0,NULL,NULL,1),(37,NULL,0,'dev.c20.rules.engine.demo.facts.GroovyFactService','2021-05-11 16:19:07',NULL,NULL,0,NULL,'Manda un correo',NULL,NULL,'fact',0,3,0,NULL,NULL,NULL,'SendEmail','/system/business/facts/SendEmail',0,0,NULL,NULL,1),(38,NULL,0,'dev.c20.rules.engine.demo.facts.GroovyFactService','2021-05-11 16:19:07',NULL,NULL,0,NULL,'Manda un correo',NULL,NULL,'fact',0,3,0,NULL,NULL,NULL,'RestStorage','/system/business/facts/RestStorage',0,0,NULL,NULL,1),(39,NULL,0,'dev.c20.rules.engine.demo.facts.GroovyFactService','2021-05-11 16:19:07',NULL,NULL,0,NULL,'Manda la tarea a la carpeta de cancelar',NULL,NULL,'fact',0,3,0,NULL,NULL,NULL,'GotoCancelar','/system/business/facts/GotoCancelar',0,0,NULL,NULL,1),(40,NULL,0,'dev.c20.rules.engine.demo.facts.GroovyStringFactService','2021-05-11 16:19:07',NULL,NULL,0,NULL,'Regresa un error pues no se resuelve la carpeta',NULL,NULL,'fact',0,3,0,NULL,NULL,NULL,'NoHayFactPorResolver','/system/business/facts/NoHayFactPorResolver',0,0,NULL,NULL,1),(30,NULL,0,NULL,'2021-05-11 16:19:07',NULL,NULL,0,NULL,'Facts',NULL,NULL,NULL,1,0,0,NULL,NULL,NULL,'system','/system/',0,0,NULL,NULL,1),(31,NULL,0,NULL,'2021-05-11 16:19:07',NULL,NULL,0,NULL,'Facts',NULL,NULL,NULL,1,1,0,NULL,NULL,NULL,'business','/system/business/',0,0,NULL,NULL,1),(139,NULL,0,NULL,'2021-06-24 02:41:31',NULL,NULL,0,NULL,NULL,NULL,NULL,'group-rule',1,4,0,NULL,NULL,NULL,'Enviar tarea a Aceptar','/system/business/groups/Por resolver/Enviar tarea a Aceptar/',0,0,NULL,NULL,1),(146,NULL,NULL,NULL,'2021-09-20 19:46:06','admin',NULL,0,NULL,'Usuarios del Sistema',NULL,NULL,NULL,1,1,0,0,NULL,NULL,'Usuarios','/Sistema/Usuarios/',0,0,NULL,NULL,1),(147,NULL,NULL,NULL,'2021-09-20 19:50:41','admin',NULL,0,NULL,'Antonio Morales',NULL,NULL,NULL,0,2,0,0,NULL,NULL,'bfg9000','/Sistema/Usuarios/bfg9000',0,0,NULL,NULL,1);
/*!40000 ALTER TABLE `c20_stg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg_attach`
--

DROP TABLE IF EXISTS `c20_stg_attach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg_attach` (
  `C20_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `C20_FILE` bigint(20) DEFAULT NULL,
  `C20_MODIFIED` datetime DEFAULT NULL,
  `C20_MODIFIER` varchar(20) DEFAULT NULL,
  `C20_COMMENT` varchar(2000) DEFAULT NULL,
  `C20_STORAGE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`C20_ID`),
  KEY `FKmni3xlfn99eghoxout1tmm8n7` (`C20_STORAGE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg_attach`
--

LOCK TABLES `c20_stg_attach` WRITE;
/*!40000 ALTER TABLE `c20_stg_attach` DISABLE KEYS */;
/*!40000 ALTER TABLE `c20_stg_attach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg_data`
--

DROP TABLE IF EXISTS `c20_stg_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg_data` (
  `c20_storage` bigint(20) NOT NULL,
  `c20_value` varchar(32000) DEFAULT NULL,
  `C20_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`c20_storage`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg_data`
--

LOCK TABLES `c20_stg_data` WRITE;
/*!40000 ALTER TABLE `c20_stg_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `c20_stg_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg_file`
--

DROP TABLE IF EXISTS `c20_stg_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg_file` (
  `C20_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `C20_FILE` blob,
  PRIMARY KEY (`C20_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg_file`
--

LOCK TABLES `c20_stg_file` WRITE;
/*!40000 ALTER TABLE `c20_stg_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `c20_stg_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg_log`
--

DROP TABLE IF EXISTS `c20_stg_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg_log` (
  `C20_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `C20_COMMENT` varchar(2000) DEFAULT NULL,
  `C20_COMMENT_ID` bigint(20) DEFAULT NULL,
  `C20_MODIFIED` datetime DEFAULT NULL,
  `C20_MODIFIER` varchar(2000) DEFAULT NULL,
  `C20_TYPE` bigint(20) DEFAULT NULL,
  `C20_STORAGE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`C20_ID`),
  KEY `FKbm9gv1aqjwxu8jfk8g5460he8` (`C20_STORAGE`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg_log`
--

LOCK TABLES `c20_stg_log` WRITE;
/*!40000 ALTER TABLE `c20_stg_log` DISABLE KEYS */;
INSERT INTO `c20_stg_log` VALUES (1,'Registro creado',0,'2021-09-20 19:26:01',NULL,1000,144),(2,'Registro creado',0,'2021-09-20 19:26:52',NULL,1000,145),(3,'Registro creado',0,'2021-09-20 19:46:06',NULL,1000,146),(4,'Registro creado',0,'2021-09-20 19:50:41',NULL,1000,147);
/*!40000 ALTER TABLE `c20_stg_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg_note`
--

DROP TABLE IF EXISTS `c20_stg_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg_note` (
  `C20_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `C20_COMMENT` varchar(2000) DEFAULT NULL,
  `C20_CREATED` datetime DEFAULT NULL,
  `C20_CREATOR` varchar(255) DEFAULT NULL,
  `C20_IMAGE` varchar(255) DEFAULT NULL,
  `C20_STORAGE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`C20_ID`),
  KEY `FKcei5yj228lipk42km86usgk3m` (`C20_STORAGE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg_note`
--

LOCK TABLES `c20_stg_note` WRITE;
/*!40000 ALTER TABLE `c20_stg_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `c20_stg_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg_perm`
--

DROP TABLE IF EXISTS `c20_stg_perm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg_perm` (
  `C20_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `C20_ADMIN` bit(1) DEFAULT NULL,
  `C20_CREATE` bit(1) DEFAULT NULL,
  `C20_DELETE` bit(1) DEFAULT NULL,
  `C20_READ` bit(1) DEFAULT NULL,
  `C20_SEND` bit(1) DEFAULT NULL,
  `C20_UPDATE` bit(1) DEFAULT NULL,
  `C20_USER` varchar(10) DEFAULT NULL,
  `C20_STORAGE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`C20_ID`),
  KEY `FKs9f4ux8hqjkfrqluvjx3f0ryw` (`C20_STORAGE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg_perm`
--

LOCK TABLES `c20_stg_perm` WRITE;
/*!40000 ALTER TABLE `c20_stg_perm` DISABLE KEYS */;
/*!40000 ALTER TABLE `c20_stg_perm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg_value`
--

DROP TABLE IF EXISTS `c20_stg_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg_value` (
  `c20_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c20_date_value` datetime DEFAULT NULL,
  `c20_num_value` double DEFAULT NULL,
  `c20_long_value` bigint(20) DEFAULT NULL,
  `c20_name` varchar(255) DEFAULT NULL,
  `c20_str_value` varchar(255) DEFAULT NULL,
  `c20_storage` bigint(20) DEFAULT NULL,
  `C20_INT_VALUE` int(11) DEFAULT NULL,
  `C20_ORDER_VALUE` int(11) DEFAULT NULL,
  `C20_STR_TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`c20_id`),
  KEY `FKblopdwf90unhybqt0dgw5w9nf` (`c20_storage`)
) ENGINE=MyISAM AUTO_INCREMENT=1111 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg_value`
--

LOCK TABLES `c20_stg_value` WRITE;
/*!40000 ALTER TABLE `c20_stg_value` DISABLE KEYS */;
INSERT INTO `c20_stg_value` VALUES (42,NULL,NULL,NULL,'param.03','subject',8,NULL,NULL,NULL),(41,NULL,NULL,NULL,'param.02','to',8,NULL,NULL,NULL),(40,NULL,NULL,NULL,'param.01','email',8,NULL,NULL,NULL),(49,NULL,NULL,NULL,'param.06','path:null,',9,NULL,NULL,NULL),(48,NULL,NULL,NULL,'param.05','request: [',9,NULL,NULL,NULL),(47,NULL,NULL,NULL,'param.04','path: \'\',',9,NULL,NULL,NULL),(46,NULL,NULL,NULL,'param.03','service: null,',9,NULL,NULL,NULL),(45,NULL,NULL,NULL,'param.02','context: null,',9,NULL,NULL,NULL),(44,NULL,NULL,NULL,'param.01','[',9,NULL,NULL,NULL),(55,NULL,NULL,NULL,'factNotFound','GotoPorAtender',12,NULL,NULL,NULL),(16,NULL,NULL,NULL,'factNotFound','NoHayFactPorResolver',13,NULL,NULL,NULL),(17,NULL,NULL,NULL,'factNotFoundMessage','Para mover la tarea es necesario que aceptada sea 1 o 2,  y si desea mandar un email lo tiene que indicar.\n Los valores enviados son: aceptada=[$context.accept] email=[$context.email]',13,NULL,NULL,NULL),(59,NULL,NULL,NULL,'factNotFound','GotoCancelar',14,NULL,NULL,NULL),(61,NULL,NULL,NULL,'line','context.accept == 1',15,NULL,NULL,NULL),(66,NULL,NULL,NULL,'MapRuleToFact.param.context.param.path','null',16,NULL,NULL,NULL),(65,NULL,NULL,NULL,'MapRuleToFact.param.context.param.context','\'/workflow/rest/storage/list\'',16,NULL,NULL,NULL),(64,NULL,NULL,NULL,'MapRuleToFact.param.context.param.request.path','\'/system/business/facts/\'',16,NULL,NULL,NULL),(63,NULL,NULL,NULL,'MapRuleToFact.name','RestStorage',16,NULL,NULL,NULL),(62,NULL,NULL,NULL,'line','context.email != null',16,NULL,NULL,NULL),(71,NULL,NULL,NULL,'MapRuleToFact.param.pathToMove','context.pathToMove',17,NULL,NULL,NULL),(70,NULL,NULL,NULL,'MapRuleToFact.name','GoToAceptar',17,NULL,NULL,NULL),(69,NULL,NULL,NULL,'line','context.email == null',17,NULL,NULL,NULL),(75,NULL,NULL,NULL,'MapRuleToFact.param.pathToMove','context.pathToMove',18,NULL,NULL,NULL),(74,NULL,NULL,NULL,'MapRuleToFact.name','GoToCancelar',18,NULL,NULL,NULL),(73,NULL,NULL,NULL,'line','context.accept == 2',18,NULL,NULL,NULL),(79,NULL,NULL,NULL,'MapRuleToFact.param.pathToMove','context.pathToMove',19,NULL,NULL,NULL),(78,NULL,NULL,NULL,'MapRuleToFact.name','GoToAceptar',19,NULL,NULL,NULL),(77,NULL,NULL,NULL,'line','context.accept == 1',19,NULL,NULL,NULL),(43,NULL,NULL,NULL,'param.04','body',8,NULL,NULL,NULL),(50,NULL,NULL,NULL,'param.07','body:null',9,NULL,NULL,NULL),(51,NULL,NULL,NULL,'param.08',']',9,NULL,NULL,NULL),(52,NULL,NULL,NULL,'param.9',']',9,NULL,NULL,NULL),(53,NULL,NULL,NULL,'source','data',9,NULL,NULL,NULL),(54,NULL,NULL,NULL,'resource','/Workflow/storage/data/system/business/facts/RestStorage',9,NULL,NULL,NULL),(56,NULL,NULL,NULL,'factNotFoundMessage',NULL,12,NULL,NULL,NULL),(57,NULL,NULL,NULL,'factNotFound','NoHayFactPorResolver',5,NULL,NULL,NULL),(58,NULL,NULL,NULL,'factNotFoundMessage','Para mover la tarea es necesario que aceptada sea 1 o 2,  y si desea mandar un email lo tiene que indicar.\n Los valores enviados son: aceptada=[$context.accept] email=[$context.email]',5,NULL,NULL,NULL),(60,NULL,NULL,NULL,'factNotFoundMessage',NULL,14,NULL,NULL,NULL),(67,NULL,NULL,NULL,'MapRuleToFact.param.context.param.service','\'storage/list\'',16,NULL,NULL,NULL),(68,NULL,NULL,NULL,'MapRuleToFact.param.context.param.request.body','context.email',16,NULL,NULL,NULL),(72,NULL,NULL,NULL,'MapRuleToFact.param.taskName','context.taskName',17,NULL,NULL,NULL),(76,NULL,NULL,NULL,'MapRuleToFact.param.taskName','context.taskName',18,NULL,NULL,NULL),(80,NULL,NULL,NULL,'MapRuleToFact.param.taskName','context.taskName',19,NULL,NULL,NULL),(1070,NULL,NULL,NULL,'param.04','body',37,NULL,NULL,NULL),(1069,NULL,NULL,NULL,'param.03','subject',37,NULL,NULL,NULL),(1086,NULL,NULL,NULL,'factNotFound','GotoCancelar',42,NULL,NULL,NULL),(1081,NULL,NULL,NULL,'resource','/Workflow/storage/data/system/business/facts/RestStorage',38,NULL,NULL,NULL),(1083,NULL,NULL,NULL,'factNotFoundMessage',NULL,41,NULL,NULL,NULL),(1080,NULL,NULL,NULL,'source','data',38,NULL,NULL,NULL),(98,NULL,NULL,NULL,'factNotFound','NoHayFactPorResolver',34,NULL,NULL,NULL),(99,NULL,NULL,NULL,'factNotFoundMessage','Para mover la tarea es necesario que aceptada sea 1 o 2,  y si desea mandar un email lo tiene que indicar.\n Los valores enviados son: aceptada=[$context.accept] email=[$context.email]',34,NULL,NULL,NULL),(1088,NULL,NULL,NULL,'line','context.accept == 1',43,NULL,NULL,NULL),(1094,NULL,NULL,NULL,'MapRuleToFact.param.context.param.service','\'storage/list\'',44,NULL,NULL,NULL),(1095,NULL,NULL,NULL,'MapRuleToFact.param.context.param.request.body','context.email',44,NULL,NULL,NULL),(1103,NULL,NULL,NULL,'MapRuleToFact.param.taskName','context.taskName',46,NULL,NULL,NULL),(1107,NULL,NULL,NULL,'MapRuleToFact.param.taskName','context.taskName',47,NULL,NULL,NULL),(1085,NULL,NULL,NULL,'factNotFoundMessage','Para mover la tarea es necesario que aceptada sea 1 o 2,  y si desea mandar un email lo tiene que indicar.\n Los valores enviados son: aceptada=[$context.accept] email=[$context.email]',48,NULL,NULL,NULL),(1092,NULL,NULL,NULL,'MapRuleToFact.param.context.param.context','\'/workflow/rest/storage/list\'',44,NULL,NULL,NULL),(1099,NULL,NULL,NULL,'MapRuleToFact.param.taskName','context.taskName',45,NULL,NULL,NULL),(1093,NULL,NULL,NULL,'MapRuleToFact.param.context.param.path','null',44,NULL,NULL,NULL),(1098,NULL,NULL,NULL,'MapRuleToFact.param.pathToMove','context.pathToMove',45,NULL,NULL,NULL),(1102,NULL,NULL,NULL,'MapRuleToFact.param.pathToMove','context.pathToMove',46,NULL,NULL,NULL),(1106,NULL,NULL,NULL,'MapRuleToFact.param.pathToMove','context.pathToMove',47,NULL,NULL,NULL),(1079,NULL,NULL,NULL,'param.9',']',38,NULL,NULL,NULL),(1087,NULL,NULL,NULL,'factNotFoundMessage',NULL,42,NULL,NULL,NULL),(1090,NULL,NULL,NULL,'MapRuleToFact.name','RestStorage',44,NULL,NULL,NULL),(1068,NULL,NULL,NULL,'param.02','to',37,NULL,NULL,NULL),(1078,NULL,NULL,NULL,'param.08',']',38,NULL,NULL,NULL),(1077,NULL,NULL,NULL,'param.07','body:null',38,NULL,NULL,NULL),(1097,NULL,NULL,NULL,'MapRuleToFact.name','GoToAceptar',45,NULL,NULL,NULL),(1101,NULL,NULL,NULL,'MapRuleToFact.name','GoToCancelar',46,NULL,NULL,NULL),(1105,NULL,NULL,NULL,'MapRuleToFact.name','GoToAceptar',47,NULL,NULL,NULL),(1076,NULL,NULL,NULL,'param.06','path:null,',38,NULL,NULL,NULL),(1074,NULL,NULL,NULL,'param.04','path: \'\',',38,NULL,NULL,NULL),(1075,NULL,NULL,NULL,'param.05','request: [',38,NULL,NULL,NULL),(1082,NULL,NULL,NULL,'factNotFound','GotoPorAtender',41,NULL,NULL,NULL),(1084,NULL,NULL,NULL,'factNotFound','NoHayFactPorResolver',48,NULL,NULL,NULL),(1067,NULL,NULL,NULL,'param.01','email',37,NULL,NULL,NULL),(1073,NULL,NULL,NULL,'param.03','service: null,',38,NULL,NULL,NULL),(1072,NULL,NULL,NULL,'param.02','context: null,',38,NULL,NULL,NULL),(1089,NULL,NULL,NULL,'line','context.email != null',44,NULL,NULL,NULL),(1096,NULL,NULL,NULL,'line','context.email == null',45,NULL,NULL,NULL),(1100,NULL,NULL,NULL,'line','context.accept == 2',46,NULL,NULL,NULL),(1104,NULL,NULL,NULL,'line','context.accept == 1',47,NULL,NULL,NULL),(1091,NULL,NULL,NULL,'MapRuleToFact.param.context.param.request.path','\'/system/business/facts/\'',44,NULL,NULL,NULL),(1071,NULL,NULL,NULL,'param.01','[',38,NULL,NULL,NULL),(1108,NULL,NULL,NULL,'email','amorales@c20.dev',147,1000,10,'1000'),(1109,NULL,NULL,NULL,'random','MSWBO2PJ57X6NQEV',147,1000,20,'1000'),(1110,NULL,NULL,NULL,'password','FhG45wo4Gl3ki2AMK/DUHA==',147,1000,30,NULL);
/*!40000 ALTER TABLE `c20_stg_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg_value_protected`
--

DROP TABLE IF EXISTS `c20_stg_value_protected`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg_value_protected` (
  `C20_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `C20_DATE_VALUE` datetime DEFAULT NULL,
  `C20_NUM_VALUE` double DEFAULT NULL,
  `C20_INT_VALUE` int(11) DEFAULT NULL,
  `C20_LONG_VALUE` bigint(20) DEFAULT NULL,
  `C20_NAME` varchar(255) DEFAULT NULL,
  `C20_ORDER_VALUE` int(11) DEFAULT NULL,
  `C20_STR_TYPE` varchar(255) DEFAULT NULL,
  `C20_STR_VALUE` varchar(255) DEFAULT NULL,
  `C20_STORAGE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`C20_ID`),
  KEY `FKlsfvw36e95981g0i09y47yphs` (`C20_STORAGE`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg_value_protected`
--

LOCK TABLES `c20_stg_value_protected` WRITE;
/*!40000 ALTER TABLE `c20_stg_value_protected` DISABLE KEYS */;
INSERT INTO `c20_stg_value_protected` VALUES (1,NULL,NULL,1000,NULL,'random',NULL,NULL,'MSWBO2PJ57X6NQEV',147),(2,NULL,NULL,1000,NULL,'password',NULL,NULL,'FhG45wo4Gl3ki2AMK/DUHA==',147),(3,NULL,NULL,1000,NULL,'email',NULL,NULL,'amorales@c20.dev',147);
/*!40000 ALTER TABLE `c20_stg_value_protected` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c20_stg_word`
--

DROP TABLE IF EXISTS `c20_stg_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c20_stg_word` (
  `c20_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c20_word` varchar(60) DEFAULT NULL,
  `c20_storage` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`c20_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4669 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c20_stg_word`
--

LOCK TABLES `c20_stg_word` WRITE;
/*!40000 ALTER TABLE `c20_stg_word` DISABLE KEYS */;
INSERT INTO `c20_stg_word` VALUES (4439,'system',35),(4440,'business',35),(4441,'facts',35),(4442,'gotoaceptar',35),(4443,'mueve',35),(4444,'la',35),(4445,'tarea',35),(4446,'a',35),(4447,'la',35),(4448,'carpeta',35),(4449,'de',35),(4450,'aceptar',35),(4451,'dev',35),(4452,'c20',35),(4453,'rules',35),(4454,'engine',35),(4455,'demo',35),(4456,'facts',35),(4457,'groovyfactservice',35),(4458,'fact',35),(4459,'system',36),(4460,'business',36),(4461,'facts',36),(4462,'gotoporatender',36),(4463,'mueve',36),(4464,'la',36),(4465,'tarea',36),(4466,'a',36),(4467,'la',36),(4468,'carpeta',36),(4469,'por',36),(4470,'atender',36),(4471,'dev',36),(4472,'c20',36),(4473,'rules',36),(4474,'engine',36),(4475,'demo',36),(4476,'facts',36),(4477,'groovyfactservice',36),(4478,'fact',36),(4479,'system',37),(4480,'business',37),(4481,'facts',37),(4482,'sendemail',37),(4483,'manda',37),(4484,'un',37),(4485,'correo',37),(4486,'dev',37),(4487,'c20',37),(4488,'rules',37),(4489,'engine',37),(4490,'demo',37),(4491,'facts',37),(4492,'groovyfactservice',37),(4493,'fact',37),(4494,'system',38),(4495,'business',38),(4496,'facts',38),(4497,'reststorage',38),(4498,'manda',38),(4499,'un',38),(4500,'correo',38),(4501,'dev',38),(4502,'c20',38),(4503,'rules',38),(4504,'engine',38),(4505,'demo',38),(4506,'facts',38),(4507,'groovyfactservice',38),(4508,'fact',38),(4509,'system',39),(4510,'business',39),(4511,'facts',39),(4512,'gotocancelar',39),(4513,'manda',39),(4514,'la',39),(4515,'tarea',39),(4516,'a',39),(4517,'la',39),(4518,'carpeta',39),(4519,'de',39),(4520,'cancelar',39),(4521,'dev',39),(4522,'c20',39),(4523,'rules',39),(4524,'engine',39),(4525,'demo',39),(4526,'facts',39),(4527,'groovyfactservice',39),(4528,'fact',39),(4529,'system',40),(4530,'business',40),(4531,'facts',40),(4532,'nohayfactporresolver',40),(4533,'regresa',40),(4534,'un',40),(4535,'error',40),(4536,'pues',40),(4537,'no',40),(4538,'se',40),(4539,'resuelve',40),(4540,'la',40),(4541,'carpeta',40),(4542,'dev',40),(4543,'c20',40),(4544,'rules',40),(4545,'engine',40),(4546,'demo',40),(4547,'facts',40),(4548,'groovystringfactservice',40),(4549,'fact',40),(4550,'system',41),(4551,'business',41),(4552,'groups',41),(4553,'inicio',41),(4554,'dev',41),(4555,'c20',41),(4556,'rules',41),(4557,'engine',41),(4558,'entities',41),(4559,'group',41),(4560,'group',41),(4561,'system',48),(4562,'business',48),(4563,'groups',48),(4564,'por',48),(4565,'resolver',48),(4566,'reglas',48),(4567,'para',48),(4568,'mover',48),(4569,'una',48),(4570,'tarea',48),(4571,'que',48),(4572,'esta',48),(4573,'en',48),(4574,'por',48),(4575,'resolver',48),(4576,'dev',48),(4577,'c20',48),(4578,'rules',48),(4579,'engine',48),(4580,'entities',48),(4581,'group',48),(4582,'group',48),(4583,'system',42),(4584,'business',42),(4585,'groups',42),(4586,'por',42),(4587,'cancelar',42),(4588,'dev',42),(4589,'c20',42),(4590,'rules',42),(4591,'engine',42),(4592,'entities',42),(4593,'group',42),(4594,'group',42),(4595,'system',43),(4596,'business',43),(4597,'rules',43),(4598,'enviar',43),(4599,'tarea',43),(4600,'a',43),(4601,'aceptar',43),(4602,'dev',43),(4603,'c20',43),(4604,'rules',43),(4605,'engine',43),(4606,'entities',43),(4607,'rule',43),(4608,'rule',43),(4609,'system',44),(4610,'business',44),(4611,'rules',44),(4612,'es',44),(4613,'aceptada',44),(4614,'y',44),(4615,'tiene',44),(4616,'definido',44),(4617,'un',44),(4618,'email',44),(4619,'dev',44),(4620,'c20',44),(4621,'rules',44),(4622,'engine',44),(4623,'entities',44),(4624,'rule',44),(4625,'rule',44),(4626,'system',45),(4627,'business',45),(4628,'rules',45),(4629,'es',45),(4630,'aceptada',45),(4631,'y',45),(4632,'no',45),(4633,'tiene',45),(4634,'email',45),(4635,'dev',45),(4636,'c20',45),(4637,'rules',45),(4638,'engine',45),(4639,'entities',45),(4640,'rule',45),(4641,'rule',45),(4642,'system',46),(4643,'business',46),(4644,'rules',46),(4645,'mover',46),(4646,'tarea',46),(4647,'a',46),(4648,'cancelar',46),(4649,'dev',46),(4650,'c20',46),(4651,'rules',46),(4652,'engine',46),(4653,'entities',46),(4654,'rule',46),(4655,'rule',46),(4656,'system',47),(4657,'business',47),(4658,'rules',47),(4659,'cancela',47),(4660,'una',47),(4661,'tarea',47),(4662,'dev',47),(4663,'c20',47),(4664,'rules',47),(4665,'engine',47),(4666,'entities',47),(4667,'rule',47),(4668,'rule',47);
/*!40000 ALTER TABLE `c20_stg_word` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `palabras`
--

DROP TABLE IF EXISTS `palabras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `palabras` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `palabra` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `palabras`
--

LOCK TABLES `palabras` WRITE;
/*!40000 ALTER TABLE `palabras` DISABLE KEYS */;
/*!40000 ALTER TABLE `palabras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tmp_upload_file`
--

DROP TABLE IF EXISTS `tmp_upload_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_upload_file` (
  `col_01` varchar(20) DEFAULT NULL,
  `col_02` varchar(20) DEFAULT NULL,
  `col_03` varchar(20) DEFAULT NULL,
  `col_04` varchar(20) DEFAULT NULL,
  `col_05` varchar(20) DEFAULT NULL,
  `col_06` varchar(20) DEFAULT NULL,
  `col_07` varchar(20) DEFAULT NULL,
  `col_08` varchar(20) DEFAULT NULL,
  `col_09` varchar(20) DEFAULT NULL,
  `col_10` varchar(20) DEFAULT NULL,
  `col_11` varchar(20) DEFAULT NULL,
  `col_12` varchar(20) DEFAULT NULL,
  `col_13` varchar(20) DEFAULT NULL,
  `col_14` varchar(20) DEFAULT NULL,
  `col_15` varchar(20) DEFAULT NULL,
  `col_16` varchar(20) DEFAULT NULL,
  `col_17` varchar(20) DEFAULT NULL,
  `col_18` varchar(20) DEFAULT NULL,
  `col_19` varchar(20) DEFAULT NULL,
  `col_20` varchar(20) DEFAULT NULL,
  `col_21` varchar(20) DEFAULT NULL,
  `col_22` varchar(20) DEFAULT NULL,
  `col_23` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tmp_upload_file`
--

LOCK TABLES `tmp_upload_file` WRITE;
/*!40000 ALTER TABLE `tmp_upload_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `tmp_upload_file` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-14 12:00:16
