CREATE SCHEMA IF NOT EXISTS `aircheck` DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `aircheck`.`record` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `longitude` DOUBLE NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `density` FLOAT NOT NULL,
  `device_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `unix_time` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `aircheck`.`device` (
  `id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `type` VARCHAR(45) NULL)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `aircheck`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `passwd` VARCHAR(45) NOT NULL,
  `e-mail` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;