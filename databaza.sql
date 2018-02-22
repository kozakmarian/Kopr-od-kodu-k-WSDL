CREATE DATABASE ajskod;
USE ajskod;
CREATE TABLE `ajskod`.`predmet` (
  `UUID` VARCHAR(36) NOT NULL,
  `NazovPredmetu` VARCHAR(150) NULL,
  PRIMARY KEY (`UUID`),
  UNIQUE INDEX `NazovPredmetu_UNIQUE` (`NazovPredmetu` ASC));
CREATE TABLE `ajskod`.`ucastnik` (
  `UUID` VARCHAR(36) NOT NULL,
  `Meno` VARCHAR(40) NULL,
  `Priezvisko` VARCHAR(40) NULL,
  PRIMARY KEY (`UUID`));
CREATE TABLE `ajskod`.`prezencna_listina` (
  `UUID` VARCHAR(36) NOT NULL,
  `UUIDPredmetu` VARCHAR(36) NOT NULL,
  `DatumACas` DATETIME NULL,
  PRIMARY KEY (`UUID`));
  CREATE TABLE `ajskod`.`ucast` (
  `idUcasti` INT NOT NULL,
  `UUIDUcastnik` VARCHAR(36) NOT NULL,
  `UUIDPrezencnaListina` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`idUcasti`));
  ALTER TABLE `ajskod`.`ucast` 
CHANGE COLUMN `idUcasti` `idUcasti` INT(11) NOT NULL AUTO_INCREMENT ;
