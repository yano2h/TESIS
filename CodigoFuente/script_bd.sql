SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `mydb` ;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`TIPO_SOLICITUD_REQ`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TIPO_SOLICITUD_REQ` (
  `id_tipo_solicitud_req` TINYINT NOT NULL ,
  `tipo_solicitud` VARCHAR(45) NOT NULL ,
  `descripcion_tipo` VARCHAR(255) NULL ,
  PRIMARY KEY (`id_tipo_solicitud_req`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ESTADO_SOLICITUD_REQ`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`ESTADO_SOLICITUD_REQ` (
  `id_estado_solicitud_req` TINYINT NOT NULL ,
  `estado_solicitud_req` VARCHAR(45) NOT NULL ,
  `descripcion_estado` VARCHAR(255) NULL ,
  PRIMARY KEY (`id_estado_solicitud_req`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`AREA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`AREA` (
  `id_area` TINYINT NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `descripcion_area` TEXT NULL ,
  PRIMARY KEY (`id_area`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`FUNCIONARIO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`FUNCIONARIO` (
  `rut` INT NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `apellido_paterno` VARCHAR(25) NOT NULL ,
  `apellido_m` VARCHAR(25) NOT NULL ,
  `correo_uv` VARCHAR(45) NULL ,
  `fecha_ultimo_acceso` DATETIME NULL COMMENT 'alvarezgonzalesperezahumada' ,
  `fecha_primer_acceso` DATETIME NULL ,
  PRIMARY KEY (`rut`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`FUNCIONARIO_DISICO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`FUNCIONARIO_DISICO` (
  `rut` INT NOT NULL ,
  `id_area` SMALLINT NOT NULL ,
  `cargo` VARCHAR(45) NOT NULL ,
  `anexo` VARCHAR(5) NULL ,
  PRIMARY KEY (`rut`) ,
  INDEX `fk_FUNCIONARIO_DISICO_AREA1` (`id_area` ASC) ,
  CONSTRAINT `fk_FUNCIONARIO_DISICO_FUNCIONARIOS1`
    FOREIGN KEY (`rut` )
    REFERENCES `mydb`.`FUNCIONARIO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FUNCIONARIO_DISICO_AREA1`
    FOREIGN KEY (`id_area` )
    REFERENCES `mydb`.`AREA` (`id_area` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TIPO_PRIORIDAD`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TIPO_PRIORIDAD` (
  `id_tipo_prioridad` TINYINT NOT NULL ,
  `prioridad` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_tipo_prioridad`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`SOLICITUD_REQUERIMIENTO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`SOLICITUD_REQUERIMIENTO` (
  `id_solicitud_req` BIGINT NOT NULL AUTO_INCREMENT ,
  `codigo_consulta` VARCHAR(9) NOT NULL ,
  `asunto` VARCHAR(45) NOT NULL DEFAULT '01A-3JFL-X' ,
  `mensaje` TEXT NOT NULL ,
  `id_tipo_solicitud_req` TINYINT NOT NULL ,
  `id_estado_solicitud_req` TINYINT NOT NULL ,
  `id_area` TINYINT NOT NULL ,
  `rut_responsable` INT NOT NULL ,
  `rut_solicitante` INT NOT NULL ,
  `id_tipo_prioridad` TINYINT NOT NULL ,
  `fecha_envio` DATETIME NOT NULL ,
  `fecha_cierre` DATETIME NULL ,
  `fecha_vencimiento` DATETIME NULL ,
  `fecha_ultima_actualizacion` DATETIME NOT NULL ,
  `justificacion_trasnferencia` VARCHAR(255) NULL ,
  `respuesta` TEXT NULL ,
  PRIMARY KEY (`id_solicitud_req`) ,
  UNIQUE INDEX `SOLICITUD_REQUERIMIENTOcol_UNIQUE` (`codigo_consulta` ASC) ,
  INDEX `fk_SOLICITUD_REQUERIMIENTO_TIPO_SOLICITUD_REQ` (`id_tipo_solicitud_req` ASC) ,
  INDEX `fk_SOLICITUD_REQUERIMIENTO_ESTADO_SOLICITUD1` (`id_estado_solicitud_req` ASC) ,
  INDEX `fk_SOLICITUD_REQUERIMIENTO_AREA1` (`id_area` ASC) ,
  INDEX `fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIO_DISICO1` (`rut_responsable` ASC) ,
  INDEX `fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIOS1` (`rut_solicitante` ASC) ,
  INDEX `fk_SOLICITUD_REQUERIMIENTO_TIPO_PRIORIDAD1` (`id_tipo_prioridad` ASC) ,
  CONSTRAINT `fk_SOLICITUD_REQUERIMIENTO_TIPO_SOLICITUD_REQ`
    FOREIGN KEY (`id_tipo_solicitud_req` )
    REFERENCES `mydb`.`TIPO_SOLICITUD_REQ` (`id_tipo_solicitud_req` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_REQUERIMIENTO_ESTADO_SOLICITUD1`
    FOREIGN KEY (`id_estado_solicitud_req` )
    REFERENCES `mydb`.`ESTADO_SOLICITUD_REQ` (`id_estado_solicitud_req` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_REQUERIMIENTO_AREA1`
    FOREIGN KEY (`id_area` )
    REFERENCES `mydb`.`AREA` (`id_area` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIO_DISICO1`
    FOREIGN KEY (`rut_responsable` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIOS1`
    FOREIGN KEY (`rut_solicitante` )
    REFERENCES `mydb`.`FUNCIONARIO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_REQUERIMIENTO_TIPO_PRIORIDAD1`
    FOREIGN KEY (`id_tipo_prioridad` )
    REFERENCES `mydb`.`TIPO_PRIORIDAD` (`id_tipo_prioridad` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TIPO_PROYECTO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TIPO_PROYECTO` (
  `id_tipo_proyecto` TINYINT NOT NULL ,
  `tipo_proyecto` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_tipo_proyecto`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ESTADO_PROYECTO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`ESTADO_PROYECTO` (
  `id_estado_proyecto` TINYINT NOT NULL ,
  `estado_proyecto` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_estado_proyecto`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PROYECTO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`PROYECTO` (
  `id_proyecto` INT NOT NULL AUTO_INCREMENT ,
  `codigo_interno` VARCHAR(6) NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `descripcion` VARCHAR(255) NOT NULL ,
  `fecha_inicio` DATETIME NOT NULL ,
  `fecha_termino` DATETIME NULL ,
  `id_tipo_proyecto` TINYINT NOT NULL ,
  `id_estado_proyecto` TINYINT NOT NULL ,
  PRIMARY KEY (`id_proyecto`) ,
  INDEX `fk_PROYECTO_TIPO_PROYECTO_copy11` (`id_tipo_proyecto` ASC) ,
  INDEX `fk_PROYECTO_ESTADO_PROYECTO1` (`id_estado_proyecto` ASC) ,
  CONSTRAINT `fk_PROYECTO_TIPO_PROYECTO_copy11`
    FOREIGN KEY (`id_tipo_proyecto` )
    REFERENCES `mydb`.`TIPO_PROYECTO` (`id_tipo_proyecto` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PROYECTO_ESTADO_PROYECTO1`
    FOREIGN KEY (`id_estado_proyecto` )
    REFERENCES `mydb`.`ESTADO_PROYECTO` (`id_estado_proyecto` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ESTADO_SOLICITUD_CAMBIO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`ESTADO_SOLICITUD_CAMBIO` (
  `id_estado_solicitud_cambio` TINYINT NOT NULL ,
  `estado_solicitud_cambio` VARCHAR(45) NOT NULL ,
  `descripcion` VARCHAR(255) NULL ,
  PRIMARY KEY (`id_estado_solicitud_cambio`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ITEM_CONFIGURACION`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`ITEM_CONFIGURACION` (
  `id_item_configuracion` INT NOT NULL ,
  `codigo_identificador_ic` VARCHAR(5) NOT NULL ,
  `nombre_ic` VARCHAR(45) NOT NULL ,
  `version` VARCHAR(10) NOT NULL ,
  `ubicacion_en_biblioteca` VARCHAR(100) NOT NULL ,
  `fecha_ultima_modificacion` DATETIME NOT NULL ,
  `id_proyecto` INT NOT NULL ,
  `rut_responsable_ic` INT NOT NULL ,
  PRIMARY KEY (`id_item_configuracion`) ,
  INDEX `fk_ITEM_CONFIGURACION_PROYECTO1` (`id_proyecto` ASC) ,
  INDEX `fk_ITEM_CONFIGURACION_FUNCIONARIO_DISICO1` (`rut_responsable_ic` ASC) ,
  CONSTRAINT `fk_ITEM_CONFIGURACION_PROYECTO1`
    FOREIGN KEY (`id_proyecto` )
    REFERENCES `mydb`.`PROYECTO` (`id_proyecto` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ITEM_CONFIGURACION_FUNCIONARIO_DISICO1`
    FOREIGN KEY (`rut_responsable_ic` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`SOLICITUD_CAMBIO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`SOLICITUD_CAMBIO` (
  `id_solicitud_cambio` INT NOT NULL ,
  `id_proyecto` INT NOT NULL ,
  `rut_solicitante` INT NOT NULL ,
  `titulo` VARCHAR(50) NOT NULL COMMENT '1234567890123456789012345678901234567890' ,
  `fecha_solicitud` DATETIME NOT NULL ,
  `descripcion_necesidad_cambio` TEXT NOT NULL ,
  `id_tipo_prioridad` TINYINT NOT NULL ,
  `id_estado_solicitud_cambio` TINYINT NOT NULL ,
  `id_item_configuracion` INT NOT NULL ,
  `descripcion_cambio` TEXT NULL ,
  `rut_evaluador_impacto` INT NULL ,
  `fecha_analisis` DATETIME NULL ,
  `descripcion_impacto_cambio` TEXT NULL ,
  `fecha_cierre` DATETIME NULL ,
  `modulo_afectado` VARCHAR(60) NULL ,
  `rut_evaluador_final` INT NULL ,
  `descripcion_resolucuion` VARCHAR(255) NULL ,
  PRIMARY KEY (`id_solicitud_cambio`) ,
  INDEX `fk_SOLICITUD_CAMBIO_PROYECTO1` (`id_proyecto` ASC) ,
  INDEX `fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO1` (`rut_solicitante` ASC) ,
  INDEX `fk_SOLICITUD_CAMBIO_TIPO_PRIORIDAD1` (`id_tipo_prioridad` ASC) ,
  INDEX `fk_SOLICITUD_CAMBIO_ESTADO_SOLICITUD_CAMBIO1` (`id_estado_solicitud_cambio` ASC) ,
  INDEX `fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO2` (`rut_evaluador_impacto` ASC) ,
  INDEX `fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO3` (`rut_evaluador_final` ASC) ,
  INDEX `fk_SOLICITUD_CAMBIO_ITEM_CONFIGURACION1` (`id_item_configuracion` ASC) ,
  CONSTRAINT `fk_SOLICITUD_CAMBIO_PROYECTO1`
    FOREIGN KEY (`id_proyecto` )
    REFERENCES `mydb`.`PROYECTO` (`id_proyecto` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO1`
    FOREIGN KEY (`rut_solicitante` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_CAMBIO_TIPO_PRIORIDAD1`
    FOREIGN KEY (`id_tipo_prioridad` )
    REFERENCES `mydb`.`TIPO_PRIORIDAD` (`id_tipo_prioridad` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_CAMBIO_ESTADO_SOLICITUD_CAMBIO1`
    FOREIGN KEY (`id_estado_solicitud_cambio` )
    REFERENCES `mydb`.`ESTADO_SOLICITUD_CAMBIO` (`id_estado_solicitud_cambio` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO2`
    FOREIGN KEY (`rut_evaluador_impacto` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO3`
    FOREIGN KEY (`rut_evaluador_final` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SOLICITUD_CAMBIO_ITEM_CONFIGURACION1`
    FOREIGN KEY (`id_item_configuracion` )
    REFERENCES `mydb`.`ITEM_CONFIGURACION` (`id_item_configuracion` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TAREA_SCM`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TAREA_SCM` (
  `id_tarea_scm` INT NOT NULL ,
  `nombre_tarea` VARCHAR(45) NOT NULL ,
  `descripcion_tarea` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id_tarea_scm`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`NOTIFICACION`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`NOTIFICACION` (
  `id_notificacion` BIGINT NOT NULL ,
  `fecha` DATETIME NOT NULL ,
  `mensaje_notificacion` VARCHAR(90) NOT NULL COMMENT 'andldksjsmando bdj mdkfna cmkd dnajs cndkc d\n\naaaaaaaaaaaaaaaaaaaaaaaaaaa\n\n' ,
  `revisada` TINYINT(1)  NOT NULL ,
  `rut_destinatario` INT NOT NULL ,
  PRIMARY KEY (`id_notificacion`) ,
  INDEX `fk_NOTIFICACION_FUNCIONARIO1` (`rut_destinatario` ASC) ,
  CONSTRAINT `fk_NOTIFICACION_FUNCIONARIO1`
    FOREIGN KEY (`rut_destinatario` )
    REFERENCES `mydb`.`FUNCIONARIO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TAREA_PROYECTO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TAREA_PROYECTO` (
  `id_tarea_proyecto` INT NOT NULL AUTO_INCREMENT ,
  `id_proyecto` INT NOT NULL ,
  `rut_responsable` INT NOT NULL ,
  `tarea` VARCHAR(45) NOT NULL ,
  `fecha_creacion` DATE NOT NULL ,
  `fecha_inicio_propuesta` DATETIME NOT NULL ,
  `fecha_inicio_real` DATETIME NULL ,
  `fecha_termino_propuesta` DATETIME NOT NULL ,
  `fecha_termino_real` DATETIME NULL ,
  `nivel_avance` SMALLINT NOT NULL ,
  `visible` TINYINT(1)  NOT NULL ,
  PRIMARY KEY (`id_tarea_proyecto`) ,
  INDEX `fk_TAREA_PROYECTO_PROYECTO1` (`id_proyecto` ASC) ,
  INDEX `fk_TAREA_PROYECTO_FUNCIONARIO_DISICO1` (`rut_responsable` ASC) ,
  CONSTRAINT `fk_TAREA_PROYECTO_PROYECTO1`
    FOREIGN KEY (`id_proyecto` )
    REFERENCES `mydb`.`PROYECTO` (`id_proyecto` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TAREA_PROYECTO_FUNCIONARIO_DISICO1`
    FOREIGN KEY (`rut_responsable` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`COMENTARIO_SOLICITUD`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`COMENTARIO_SOLICITUD` (
  `id_comentario` BIGINT NOT NULL ,
  `id_solicitud_req` BIGINT NOT NULL ,
  `rut_autor` INT NOT NULL ,
  `comentario` TEXT NOT NULL ,
  `fecha` DATETIME NOT NULL ,
  `visible` TINYINT(1)  NOT NULL DEFAULT true ,
  PRIMARY KEY (`id_comentario`) ,
  INDEX `fk_COMENTARIO_SOLICITUD_SOLICITUD_REQUERIMIENTO1` (`id_solicitud_req` ASC) ,
  INDEX `fk_COMENTARIO_SOLICITUD_FUNCIONARIOS1` (`rut_autor` ASC) ,
  CONSTRAINT `fk_COMENTARIO_SOLICITUD_SOLICITUD_REQUERIMIENTO1`
    FOREIGN KEY (`id_solicitud_req` )
    REFERENCES `mydb`.`SOLICITUD_REQUERIMIENTO` (`id_solicitud_req` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_COMENTARIO_SOLICITUD_FUNCIONARIOS1`
    FOREIGN KEY (`rut_autor` )
    REFERENCES `mydb`.`FUNCIONARIO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TAREA_SCM_PROYECTO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`TAREA_SCM_PROYECTO` (
  `id_tarea_scm_proyecto` INT NOT NULL ,
  `id_tarea_scm` INT NOT NULL ,
  `id_proyecto` INT NOT NULL ,
  `rut_responsable` INT NOT NULL ,
  `fecha_inicio` DATE NOT NULL ,
  `fecha_termino` DATE NOT NULL ,
  INDEX `fk_TAREA_SCM_has_PROYECTO_PROYECTO1` (`id_proyecto` ASC) ,
  INDEX `fk_TAREA_SCM_has_PROYECTO_TAREA_SCM1` (`id_tarea_scm` ASC) ,
  INDEX `fk_TAREA_SCM_has_PROYECTO_FUNCIONARIO_DISICO1` (`rut_responsable` ASC) ,
  PRIMARY KEY (`id_tarea_scm_proyecto`) ,
  CONSTRAINT `fk_TAREA_SCM_has_PROYECTO_TAREA_SCM1`
    FOREIGN KEY (`id_tarea_scm` )
    REFERENCES `mydb`.`TAREA_SCM` (`id_tarea_scm` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TAREA_SCM_has_PROYECTO_PROYECTO1`
    FOREIGN KEY (`id_proyecto` )
    REFERENCES `mydb`.`PROYECTO` (`id_proyecto` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TAREA_SCM_has_PROYECTO_FUNCIONARIO_DISICO1`
    FOREIGN KEY (`rut_responsable` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ENTREGABLE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`ENTREGABLE` (
  `id_entregable` INT NOT NULL AUTO_INCREMENT ,
  `nombre_entregable` VARCHAR(45) NOT NULL ,
  `id_tarea_scm` INT NOT NULL ,
  PRIMARY KEY (`id_entregable`) ,
  INDEX `fk_ENTREGABLE_TAREA_SCM1` (`id_tarea_scm` ASC) ,
  CONSTRAINT `fk_ENTREGABLE_TAREA_SCM1`
    FOREIGN KEY (`id_tarea_scm` )
    REFERENCES `mydb`.`TAREA_SCM` (`id_tarea_scm` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ROL_PROYECTO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`ROL_PROYECTO` (
  `id_rol` TINYINT NOT NULL AUTO_INCREMENT ,
  `nombre_rol` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_rol`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PARTICIPANTE_PROYECTO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`PARTICIPANTE_PROYECTO` (
  `rut_participante` INT NOT NULL ,
  `id_proyecto` INT NOT NULL ,
  `id_rol` TINYINT NOT NULL ,
  PRIMARY KEY (`rut_participante`, `id_proyecto`) ,
  INDEX `fk_FUNCIONARIO_DISICO_has_PROYECTO_PROYECTO1` (`id_proyecto` ASC) ,
  INDEX `fk_FUNCIONARIO_DISICO_has_PROYECTO_FUNCIONARIO_DISICO1` (`rut_participante` ASC) ,
  INDEX `fk_PARTICIPANTE_PROYECTO_ROL_PROYECTO1` (`id_rol` ASC) ,
  CONSTRAINT `fk_FUNCIONARIO_DISICO_has_PROYECTO_FUNCIONARIO_DISICO1`
    FOREIGN KEY (`rut_participante` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FUNCIONARIO_DISICO_has_PROYECTO_PROYECTO1`
    FOREIGN KEY (`id_proyecto` )
    REFERENCES `mydb`.`PROYECTO` (`id_proyecto` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PARTICIPANTE_PROYECTO_ROL_PROYECTO1`
    FOREIGN KEY (`id_rol` )
    REFERENCES `mydb`.`ROL_PROYECTO` (`id_rol` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`FORMULARIO_IMPLEMENTACION`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`FORMULARIO_IMPLEMENTACION` (
  `id_formulario_implementacion` INT NOT NULL AUTO_INCREMENT ,
  `observaciones` TEXT NOT NULL ,
  `fecha_verificacion` DATE NOT NULL ,
  `rut_verificador` INT NOT NULL ,
  `rut_implementador` INT NOT NULL ,
  `id_solicitud_cambio` INT NOT NULL ,
  PRIMARY KEY (`id_formulario_implementacion`) ,
  INDEX `fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO1` (`rut_verificador` ASC) ,
  INDEX `fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO2` (`rut_implementador` ASC) ,
  INDEX `fk_FORMULARIO_IMPLEMENTACION_SOLICITUD_CAMBIO1` (`id_solicitud_cambio` ASC) ,
  CONSTRAINT `fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO1`
    FOREIGN KEY (`rut_verificador` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO2`
    FOREIGN KEY (`rut_implementador` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FORMULARIO_IMPLEMENTACION_SOLICITUD_CAMBIO1`
    FOREIGN KEY (`id_solicitud_cambio` )
    REFERENCES `mydb`.`SOLICITUD_CAMBIO` (`id_solicitud_cambio` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ESTADISTICA_PERSONAL`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`ESTADISTICA_PERSONAL` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `rut_funcionario` INT NOT NULL ,
  `fecha` DATE NOT NULL ,
  `cantidad_total_solicitudes_asignadas` BIGINT NOT NULL ,
  `cantidad_solicitudes_pendientes` INT NOT NULL ,
  `cantidad_solicitudes_vencidas` INT NOT NULL ,
  `cantidad_solicitudes_iniciadas` INT NOT NULL ,
  `cantidad_solicitudes_cerradas` INT NOT NULL ,
  `cantidad_proyectos_acargo` INT NOT NULL ,
  `cantidad_proyectos_en_que_participa` INT NOT NULL ,
  `cantidad_tareas_proyecto_asociadas` INT NOT NULL ,
  `cantidad_tareas_scm_asociadas` INT NOT NULL ,
  INDEX `fk_ESTADISTICA_PERSONAL_FUNCIONARIO_DISICO1` (`rut_funcionario` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_ESTADISTICA_PERSONAL_FUNCIONARIO_DISICO1`
    FOREIGN KEY (`rut_funcionario` )
    REFERENCES `mydb`.`FUNCIONARIO_DISICO` (`rut` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;