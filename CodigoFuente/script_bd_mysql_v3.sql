SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';


-- -----------------------------------------------------
-- Table TIPO_SOLICITUD_REQ
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS TIPO_SOLICITUD_REQ (
  id_tipo_solicitud_req TINYINT NOT NULL ,
  nombre_tipo_solicitud VARCHAR(45) NOT NULL ,
  descripcion_tipo VARCHAR(255) NULL ,
  PRIMARY KEY (id_tipo_solicitud_req) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table ESTADO_SOLICITUD_REQ
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS ESTADO_SOLICITUD_REQ (
  id_estado_solicitud_req TINYINT NOT NULL ,
  nombre_estado_solicitud VARCHAR(45) NOT NULL ,
  descripcion_estado VARCHAR(255) NULL ,
  PRIMARY KEY (id_estado_solicitud_req) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table AREA
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS AREA (
  id_area TINYINT NOT NULL ,
  nombre_area VARCHAR(45) NOT NULL ,
  descripcion_area TEXT NULL ,
  PRIMARY KEY (id_area) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table FUNCIONARIO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS FUNCIONARIO (
  rut INT NOT NULL ,
  nombre VARCHAR(45) NOT NULL ,
  apellido_paterno VARCHAR(25) NOT NULL ,
  apellido_materno VARCHAR(25) NOT NULL ,
  correo_uv VARCHAR(45) NULL ,
  fecha_ultimo_acceso DATETIME NULL ,
  fecha_primer_acceso DATETIME NULL ,
  PRIMARY KEY (rut) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table FUNCIONARIO_DISICO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS FUNCIONARIO_DISICO (
  rut INT NOT NULL ,
  area TINYINT NOT NULL ,
  cargo VARCHAR(45) NOT NULL ,
  anexo VARCHAR(5) NULL ,
  PRIMARY KEY (rut) ,
  INDEX fk_FUNCIONARIO_DISICO_AREA1 (area ASC) ,
  CONSTRAINT fk_FUNCIONARIO_DISICO_FUNCIONARIOS1
    FOREIGN KEY (rut )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FUNCIONARIO_DISICO_AREA1
    FOREIGN KEY (area )
    REFERENCES AREA (id_area )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table TIPO_PRIORIDAD
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS TIPO_PRIORIDAD (
  id_tipo_prioridad TINYINT NOT NULL ,
  nombre_prioridad VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_tipo_prioridad) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table SOLICITUD_REQUERIMIENTO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS SOLICITUD_REQUERIMIENTO (
  id_solicitud_req BIGINT NOT NULL AUTO_INCREMENT ,
  codigo_consulta VARCHAR(9) NOT NULL ,
  asunto VARCHAR(45) NOT NULL ,
  mensaje TEXT NOT NULL ,
  tipo_solicitud TINYINT NOT NULL ,
  estado_solicitud TINYINT NOT NULL ,
  prioridad_solicitud TINYINT NOT NULL ,
  area_responsable TINYINT NOT NULL ,
  responsable INT NULL ,
  solicitante INT NOT NULL ,
  fecha_envio DATETIME NOT NULL ,
  fecha_cierre DATETIME NULL ,
  fecha_vencimiento DATETIME NULL ,
  fecha_ultima_actualizacion DATETIME NOT NULL ,
  justificacion_trasnferencia VARCHAR(255) NULL ,
  respuesta TEXT NULL ,
  PRIMARY KEY (id_solicitud_req) ,
  UNIQUE INDEX SOLICITUD_REQUERIMIENTOcol_UNIQUE (codigo_consulta ASC) ,
  INDEX fk_SOLICITUD_REQUERIMIENTO_TIPO_SOLICITUD_REQ (tipo_solicitud ASC) ,
  INDEX fk_SOLICITUD_REQUERIMIENTO_ESTADO_SOLICITUD1 (estado_solicitud ASC) ,
  INDEX fk_SOLICITUD_REQUERIMIENTO_AREA1 (area_responsable ASC) ,
  INDEX fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIO_DISICO1 (responsable ASC) ,
  INDEX fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIOS1 (solicitante ASC) ,
  INDEX fk_SOLICITUD_REQUERIMIENTO_TIPO_PRIORIDAD1 (prioridad_solicitud ASC) ,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_TIPO_SOLICITUD_REQ
    FOREIGN KEY (tipo_solicitud )
    REFERENCES TIPO_SOLICITUD_REQ (id_tipo_solicitud_req )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_ESTADO_SOLICITUD1
    FOREIGN KEY (estado_solicitud )
    REFERENCES ESTADO_SOLICITUD_REQ (id_estado_solicitud_req )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_AREA1
    FOREIGN KEY (area_responsable )
    REFERENCES AREA (id_area )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIO_DISICO1
    FOREIGN KEY (responsable )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIOS1
    FOREIGN KEY (solicitante )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_TIPO_PRIORIDAD1
    FOREIGN KEY (prioridad_solicitud )
    REFERENCES TIPO_PRIORIDAD (id_tipo_prioridad )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table TIPO_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS TIPO_PROYECTO (
  id_tipo_proyecto TINYINT NOT NULL ,
  nombre_tipo_proyecto VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_tipo_proyecto) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table ESTADO_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS ESTADO_PROYECTO (
  id_estado_proyecto TINYINT NOT NULL ,
  nombre_estado_proyecto VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_estado_proyecto) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table PROYECTO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS PROYECTO (
  id_proyecto INT NOT NULL AUTO_INCREMENT ,
  codigo_interno VARCHAR(6) NOT NULL ,
  nombre VARCHAR(45) NOT NULL ,
  descripcion VARCHAR(255) NOT NULL ,
  fecha_inicio DATETIME NOT NULL ,
  fecha_termino DATETIME NULL ,
  tipo_proyecto TINYINT NOT NULL ,
  estado_proyecto TINYINT NOT NULL ,
  PRIMARY KEY (id_proyecto) ,
  INDEX fk_PROYECTO_TIPO_PROYECTO1 (tipo_proyecto ASC) ,
  INDEX fk_PROYECTO_ESTADO_PROYECTO1 (estado_proyecto ASC) ,
  CONSTRAINT fk_PROYECTO_TIPO_PROYECTO1
    FOREIGN KEY (tipo_proyecto )
    REFERENCES TIPO_PROYECTO (id_tipo_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_PROYECTO_ESTADO_PROYECTO1
    FOREIGN KEY (estado_proyecto )
    REFERENCES ESTADO_PROYECTO (id_estado_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table ESTADO_SOLICITUD_CAMBIO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS ESTADO_SOLICITUD_CAMBIO (
  id_estado_solicitud_cambio TINYINT NOT NULL ,
  nombre_estado_solicitud VARCHAR(45) NOT NULL ,
  descripcion_estado VARCHAR(255) NULL ,
  PRIMARY KEY (id_estado_solicitud_cambio) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table ITEM_CONFIGURACION
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS ITEM_CONFIGURACION (
  id_item_configuracion INT NOT NULL AUTO_INCREMENT ,
  codigo_identificador_ic VARCHAR(5) NOT NULL ,
  nombre_item_configuracion VARCHAR(45) NOT NULL ,
  version VARCHAR(10) NOT NULL ,
  ubicacion_en_biblioteca VARCHAR(200) NOT NULL ,
  fecha_ultima_modificacion DATETIME NOT NULL ,
  proyecto INT NOT NULL ,
  responsable_item INT NOT NULL ,
  PRIMARY KEY (id_item_configuracion) ,
  INDEX fk_ITEM_CONFIGURACION_PROYECTO1 (proyecto ASC) ,
  INDEX fk_ITEM_CONFIGURACION_FUNCIONARIO_DISICO1 (responsable_item ASC) ,
  CONSTRAINT fk_ITEM_CONFIGURACION_PROYECTO1
    FOREIGN KEY (proyecto )
    REFERENCES PROYECTO (id_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ITEM_CONFIGURACION_FUNCIONARIO_DISICO1
    FOREIGN KEY (responsable_item )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table SOLICITUD_CAMBIO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS SOLICITUD_CAMBIO (
  id_solicitud_cambio INT NOT NULL AUTO_INCREMENT ,
  proyecto INT NOT NULL ,
  solicitante INT NOT NULL ,
  titulo VARCHAR(50) NOT NULL ,
  fecha_envio DATETIME NOT NULL ,
  descripcion_necesidad_cambio TEXT NOT NULL ,
  prioridad_solicitud TINYINT NOT NULL ,
  estado_solicitud TINYINT NOT NULL ,
  item_configuracion INT NOT NULL ,
  descripcion_cambio TEXT NULL ,
  evaluador_impacto INT NULL ,
  fecha_analisis DATETIME NULL ,
  descripcion_impacto_cambio TEXT NULL ,
  fecha_cierre DATETIME NULL ,
  modulo_afectado VARCHAR(60) NULL ,
  evaluador_final INT NULL ,
  descripcion_resolucion VARCHAR(255) NULL ,
  PRIMARY KEY (id_solicitud_cambio) ,
  INDEX fk_SOLICITUD_CAMBIO_PROYECTO1 (proyecto ASC) ,
  INDEX fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO1 (solicitante ASC) ,
  INDEX fk_SOLICITUD_CAMBIO_TIPO_PRIORIDAD1 (prioridad_solicitud ASC) ,
  INDEX fk_SOLICITUD_CAMBIO_ESTADO_SOLICITUD_CAMBIO1 (estado_solicitud ASC) ,
  INDEX fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO2 (evaluador_impacto ASC) ,
  INDEX fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO3 (evaluador_final ASC) ,
  INDEX fk_SOLICITUD_CAMBIO_ITEM_CONFIGURACION1 (item_configuracion ASC) ,
  CONSTRAINT fk_SOLICITUD_CAMBIO_PROYECTO1
    FOREIGN KEY (proyecto )
    REFERENCES PROYECTO (id_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO1
    FOREIGN KEY (solicitante )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_TIPO_PRIORIDAD1
    FOREIGN KEY (prioridad_solicitud )
    REFERENCES TIPO_PRIORIDAD (id_tipo_prioridad )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_ESTADO_SOLICITUD_CAMBIO1
    FOREIGN KEY (estado_solicitud )
    REFERENCES ESTADO_SOLICITUD_CAMBIO (id_estado_solicitud_cambio )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO2
    FOREIGN KEY (evaluador_impacto )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO3
    FOREIGN KEY (evaluador_final )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_ITEM_CONFIGURACION1
    FOREIGN KEY (item_configuracion )
    REFERENCES ITEM_CONFIGURACION (id_item_configuracion )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table TAREA_SCM
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS TAREA_SCM (
  id_tarea_scm INT NOT NULL AUTO_INCREMENT ,
  nombre_tarea VARCHAR(60) NOT NULL ,
  descripcion_tarea VARCHAR(255) NOT NULL ,
  PRIMARY KEY (id_tarea_scm) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table NOTIFICACION
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS NOTIFICACION (
  id_notificacion BIGINT NOT NULL AUTO_INCREMENT ,
  fecha DATETIME NOT NULL ,
  mensaje_notificacion VARCHAR(180) NOT NULL ,
  revisada TINYINT(1) NOT NULL DEFAULT false ,
  destinatario INT NOT NULL ,
  PRIMARY KEY (id_notificacion) ,
  INDEX fk_NOTIFICACION_FUNCIONARIO1 (destinatario ASC) ,
  CONSTRAINT fk_NOTIFICACION_FUNCIONARIO1
    FOREIGN KEY (destinatario )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table TAREA_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS TAREA_PROYECTO (
  id_tarea_proyecto INT NOT NULL AUTO_INCREMENT ,
  proyecto INT NOT NULL ,
  responsable_tarea INT NOT NULL ,
  tarea VARCHAR(60) NOT NULL ,
  descripcion_tarea VARCHAR(255) NOT NULL ,
  fecha_creacion DATE NOT NULL ,
  fecha_inicio_propuesta DATETIME NOT NULL ,
  fecha_inicio_real DATETIME NULL ,
  fecha_termino_propuesta DATETIME NOT NULL ,
  fecha_termino_real DATETIME NULL ,
  nivel_avance SMALLINT NOT NULL ,
  visible TINYINT(1) NOT NULL ,
  PRIMARY KEY (id_tarea_proyecto) ,
  INDEX fk_TAREA_PROYECTO_PROYECTO1 (proyecto ASC) ,
  INDEX fk_TAREA_PROYECTO_FUNCIONARIO_DISICO1 (responsable_tarea ASC) ,
  CONSTRAINT fk_TAREA_PROYECTO_PROYECTO1
    FOREIGN KEY (proyecto )
    REFERENCES PROYECTO (id_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_TAREA_PROYECTO_FUNCIONARIO_DISICO1
    FOREIGN KEY (responsable_tarea )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table COMENTARIO_SOLICITUD
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS COMENTARIO_SOLICITUD (
  id_comentario BIGINT NOT NULL AUTO_INCREMENT ,
  solicitud_requerimiento BIGINT NOT NULL ,
  autor INT NOT NULL ,
  comentario TEXT NOT NULL ,
  fecha DATETIME NOT NULL ,
  visible TINYINT(1) NOT NULL DEFAULT true ,
  PRIMARY KEY (id_comentario) ,
  INDEX fk_COMENTARIO_SOLICITUD_SOLICITUD_REQUERIMIENTO1 (solicitud_requerimiento ASC) ,
  INDEX fk_COMENTARIO_SOLICITUD_FUNCIONARIOS1 (autor ASC) ,
  CONSTRAINT fk_COMENTARIO_SOLICITUD_SOLICITUD_REQUERIMIENTO1
    FOREIGN KEY (solicitud_requerimiento )
    REFERENCES SOLICITUD_REQUERIMIENTO (id_solicitud_req )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_COMENTARIO_SOLICITUD_FUNCIONARIOS1
    FOREIGN KEY (autor )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table TAREA_SCM_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS TAREA_SCM_PROYECTO (
  id_tarea_scm INT NOT NULL ,
  id_proyecto INT NOT NULL ,
  responsable INT NOT NULL ,
  fecha_inicio DATE NOT NULL ,
  fecha_termino DATE NOT NULL ,
  INDEX fk_TAREA_SCM_has_PROYECTO_PROYECTO1 (id_proyecto ASC) ,
  INDEX fk_TAREA_SCM_has_PROYECTO_TAREA_SCM1 (id_tarea_scm ASC) ,
  INDEX fk_TAREA_SCM_has_PROYECTO_FUNCIONARIO_DISICO1 (responsable ASC) ,
  PRIMARY KEY (id_tarea_scm, id_proyecto) ,
  CONSTRAINT fk_TAREA_SCM_has_PROYECTO_TAREA_SCM1
    FOREIGN KEY (id_tarea_scm )
    REFERENCES TAREA_SCM (id_tarea_scm )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_TAREA_SCM_has_PROYECTO_PROYECTO1
    FOREIGN KEY (id_proyecto )
    REFERENCES PROYECTO (id_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_TAREA_SCM_has_PROYECTO_FUNCIONARIO_DISICO1
    FOREIGN KEY (responsable )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table ENTREGABLE
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS ENTREGABLE (
  id_entregable INT NOT NULL AUTO_INCREMENT ,
  nombre_entregable VARCHAR(50) NOT NULL ,
  tarea_scm INT NOT NULL ,
  PRIMARY KEY (id_entregable) ,
  INDEX fk_ENTREGABLE_TAREA_SCM1 (tarea_scm ASC) ,
  CONSTRAINT fk_ENTREGABLE_TAREA_SCM1
    FOREIGN KEY (tarea_scm )
    REFERENCES TAREA_SCM (id_tarea_scm )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table ROL_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS ROL_PROYECTO (
  id_rol TINYINT NOT NULL AUTO_INCREMENT ,
  nombre_rol VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_rol) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table PARTICIPANTE_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS PARTICIPANTE_PROYECTO (
  rut_participante INT NOT NULL ,
  id_proyecto INT NOT NULL ,
  rol TINYINT NOT NULL ,
  PRIMARY KEY (rut_participante, id_proyecto) ,
  INDEX fk_FUNCIONARIO_DISICO_has_PROYECTO_PROYECTO1 (id_proyecto ASC) ,
  INDEX fk_FUNCIONARIO_DISICO_has_PROYECTO_FUNCIONARIO_DISICO1 (rut_participante ASC) ,
  INDEX fk_PARTICIPANTE_PROYECTO_ROL_PROYECTO1 (rol ASC) ,
  CONSTRAINT fk_FUNCIONARIO_DISICO_has_PROYECTO_FUNCIONARIO_DISICO1
    FOREIGN KEY (rut_participante )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FUNCIONARIO_DISICO_has_PROYECTO_PROYECTO1
    FOREIGN KEY (id_proyecto )
    REFERENCES PROYECTO (id_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_PARTICIPANTE_PROYECTO_ROL_PROYECTO1
    FOREIGN KEY (rol )
    REFERENCES ROL_PROYECTO (id_rol )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table FORMULARIO_IMPLEMENTACION
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS FORMULARIO_IMPLEMENTACION (
  id_formulario_implementacion INT NOT NULL ,
  observaciones TEXT NOT NULL ,
  fecha_verificacion DATE NOT NULL ,
  verificador INT NOT NULL ,
  implementador INT NOT NULL ,
  INDEX fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO1 (verificador ASC) ,
  INDEX fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO2 (implementador ASC) ,
  INDEX fk_FORMULARIO_IMPLEMENTACION_SOLICITUD_CAMBIO1 (id_formulario_implementacion ASC) ,
  PRIMARY KEY (id_formulario_implementacion) ,
  CONSTRAINT fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO1
    FOREIGN KEY (verificador )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO2
    FOREIGN KEY (implementador )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FORMULARIO_IMPLEMENTACION_SOLICITUD_CAMBIO1
    FOREIGN KEY (id_formulario_implementacion )
    REFERENCES SOLICITUD_CAMBIO (id_solicitud_cambio )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table ESTADISTICA_PERSONAL
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS ESTADISTICA_PERSONAL (
  id BIGINT NOT NULL AUTO_INCREMENT ,
  funcionario INT NOT NULL ,
  fecha_medicion DATE NOT NULL ,
  cantidad_total_solicitudes_asignadas BIGINT NOT NULL ,
  cantidad_solicitudes_pendientes INT NOT NULL ,
  cantidad_solicitudes_vencidas INT NOT NULL ,
  cantidad_solicitudes_iniciadas INT NOT NULL ,
  cantidad_solicitudes_cerradas INT NOT NULL ,
  cantidad_proyectos_acargo INT NOT NULL ,
  cantidad_proyectos_en_que_participa INT NOT NULL ,
  cantidad_tareas_proyecto_asociadas INT NOT NULL ,
  cantidad_tareas_scm_asociadas INT NOT NULL ,
  INDEX fk_ESTADISTICA_PERSONAL_FUNCIONARIO_DISICO1 (funcionario ASC) ,
  PRIMARY KEY (id) ,
  CONSTRAINT fk_ESTADISTICA_PERSONAL_FUNCIONARIO_DISICO1
    FOREIGN KEY (funcionario )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
