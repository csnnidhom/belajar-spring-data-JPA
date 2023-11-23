/*
 Navicat Premium Data Transfer

 Source Server         : LOCAL
 Source Server Type    : MySQL
 Source Server Version : 100420 (10.4.20-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : belajar_spring_data_jpa

 Target Server Type    : MySQL
 Target Server Version : 100420 (10.4.20-MariaDB)
 File Encoding         : 65001

 Date: 23/11/2023 19:34:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (2, 'GADGET MURAH SEKALI', '2023-11-19 05:02:28', '0000-00-00 00:00:00');
INSERT INTO `categories` VALUES (8, 'Category : 0', '2023-11-18 09:50:25', '0000-00-00 00:00:00');
INSERT INTO `categories` VALUES (9, 'Category : 1', '2023-11-18 09:50:25', '0000-00-00 00:00:00');
INSERT INTO `categories` VALUES (10, 'Category : 2', '2023-11-18 09:50:25', '0000-00-00 00:00:00');
INSERT INTO `categories` VALUES (11, 'Category : 3', '2023-11-18 09:50:25', '0000-00-00 00:00:00');
INSERT INTO `categories` VALUES (12, 'Category : 4', '2023-11-18 09:50:25', '0000-00-00 00:00:00');
INSERT INTO `categories` VALUES (13, 'GADGET CCUY', '2023-11-18 09:50:25', '0000-00-00 00:00:00');
INSERT INTO `categories` VALUES (24, 'Sample Audit', '2023-11-18 02:56:42', '2023-11-18 02:56:42');

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `price` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_products_categories`(`category_id` ASC) USING BTREE,
  CONSTRAINT `fk_products_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, 'Apple', 10000000, 2);
INSERT INTO `products` VALUES (2, 'Xiaomi', 0, 2);

SET FOREIGN_KEY_CHECKS = 1;
