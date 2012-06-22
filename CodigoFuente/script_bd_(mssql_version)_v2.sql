-- -----------------------------------------------------
-- Table TIPO_SOLICITUD_REQ
-- -----------------------------------------------------
CREATE  TABLE TIPO_SOLICITUD_REQ (
  id_tipo_solicitud_req TINYINT NOT NULL ,
  nombre_tipo_solicitud VARCHAR(45) NOT NULL ,
  descripcion_tipo VARCHAR(255) NULL ,
  PRIMARY KEY (id_tipo_solicitud_req) )
;


-- -----------------------------------------------------
-- Table ESTADO_SOLICITUD_REQ
-- -----------------------------------------------------
CREATE  TABLE ESTADO_SOLICITUD_REQ (
  id_estado_solicitud_req TINYINT NOT NULL ,
  nombre_estado_solicitud VARCHAR(45) NOT NULL ,
  descripcion_estado VARCHAR(255) NULL ,
  PRIMARY KEY (id_estado_solicitud_req) )
;


-- -----------------------------------------------------
-- Table AREA
-- -----------------------------------------------------
CREATE  TABLE AREA (
  id_area TINYINT NOT NULL ,
  nombre VARCHAR(45) NOT NULL ,
  descripcion_area TEXT NULL ,
  PRIMARY KEY (id_area) )
;


-- -----------------------------------------------------
-- Table FUNCIONARIO
-- -----------------------------------------------------
CREATE  TABLE FUNCIONARIO (
  rut INT NOT NULL ,
  nombre VARCHAR(45) NOT NULL ,
  apellido_paterno VARCHAR(25) NOT NULL ,
  apellido_m VARCHAR(25) NOT NULL ,
  correo_uv VARCHAR(45) NULL ,
  fecha_ultimo_acceso DATETIME NULL ,
  fecha_primer_acceso DATETIME NULL ,
  PRIMARY KEY (rut) )
;


-- -----------------------------------------------------
-- Table FUNCIONARIO_DISICO
-- -----------------------------------------------------
CREATE  TABLE FUNCIONARIO_DISICO (
  rut INT NOT NULL ,
  id_area TINYINT NOT NULL ,
  cargo VARCHAR(45) NOT NULL ,
  anexo VARCHAR(5) NULL ,
  PRIMARY KEY (rut) ,
  CONSTRAINT fk_FUNCIONARIO_DISICO_FUNCIONARIOS1
    FOREIGN KEY (rut )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FUNCIONARIO_DISICO_AREA1
    FOREIGN KEY (id_area )
    REFERENCES AREA (id_area )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table TIPO_PRIORIDAD
-- -----------------------------------------------------
CREATE  TABLE TIPO_PRIORIDAD (
  id_tipo_prioridad TINYINT NOT NULL ,
  nombre_prioridad VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_tipo_prioridad) )
;


-- -----------------------------------------------------
-- Table SOLICITUD_REQUERIMIENTO
-- -----------------------------------------------------
CREATE  TABLE SOLICITUD_REQUERIMIENTO (
  id_solicitud_req BIGINT NOT NULL IDENTITY ,
  codigo_consulta VARCHAR(9) NOT NULL UNIQUE,
  asunto VARCHAR(45) NOT NULL ,
  mensaje TEXT NOT NULL ,
  id_tipo_solicitud_req TINYINT NOT NULL ,
  id_estado_solicitud_req TINYINT NOT NULL ,
  id_area TINYINT NOT NULL ,
  rut_responsable INT NOT NULL ,
  rut_solicitante INT NOT NULL ,
  id_tipo_prioridad TINYINT NOT NULL ,
  fecha_envio DATETIME NOT NULL ,
  fecha_cierre DATETIME NULL ,
  fecha_vencimiento DATETIME NULL ,
  fecha_ultima_actualizacion DATETIME NOT NULL ,
  justificacion_trasnferencia VARCHAR(255) NULL ,
  respuesta TEXT NULL ,
  PRIMARY KEY (id_solicitud_req) ,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_TIPO_SOLICITUD_REQ
    FOREIGN KEY (id_tipo_solicitud_req )
    REFERENCES TIPO_SOLICITUD_REQ (id_tipo_solicitud_req )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_ESTADO_SOLICITUD1
    FOREIGN KEY (id_estado_solicitud_req )
    REFERENCES ESTADO_SOLICITUD_REQ (id_estado_solicitud_req )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_AREA1
    FOREIGN KEY (id_area )
    REFERENCES AREA (id_area )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIO_DISICO1
    FOREIGN KEY (rut_responsable )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_FUNCIONARIOS1
    FOREIGN KEY (rut_solicitante )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_REQUERIMIENTO_TIPO_PRIORIDAD1
    FOREIGN KEY (id_tipo_prioridad )
    REFERENCES TIPO_PRIORIDAD (id_tipo_prioridad )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table TIPO_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE TIPO_PROYECTO (
  id_tipo_proyecto TINYINT NOT NULL ,
  nombre_tipo_proyecto VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_tipo_proyecto) )
;


-- -----------------------------------------------------
-- Table ESTADO_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE ESTADO_PROYECTO (
  id_estado_proyecto TINYINT NOT NULL ,
  nombre_estado_proyecto VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_estado_proyecto) )
;


-- -----------------------------------------------------
-- Table PROYECTO
-- -----------------------------------------------------
CREATE  TABLE PROYECTO (
  id_proyecto INT NOT NULL IDENTITY ,
  codigo_interno VARCHAR(6) NOT NULL ,
  nombre VARCHAR(45) NOT NULL ,
  descripcion VARCHAR(255) NOT NULL ,
  fecha_inicio DATETIME NOT NULL ,
  fecha_termino DATETIME NULL ,
  id_tipo_proyecto TINYINT NOT NULL ,
  id_estado_proyecto TINYINT NOT NULL ,
  PRIMARY KEY (id_proyecto) ,
  CONSTRAINT fk_PROYECTO_TIPO_PROYECTO1
    FOREIGN KEY (id_tipo_proyecto )
    REFERENCES TIPO_PROYECTO (id_tipo_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_PROYECTO_ESTADO_PROYECTO1
    FOREIGN KEY (id_estado_proyecto )
    REFERENCES ESTADO_PROYECTO (id_estado_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table ESTADO_SOLICITUD_CAMBIO
-- -----------------------------------------------------
CREATE  TABLE ESTADO_SOLICITUD_CAMBIO (
  id_estado_solicitud_cambio TINYINT NOT NULL ,
  nombre_estado_solicitud VARCHAR(45) NOT NULL ,
  descripcion VARCHAR(255) NULL ,
  PRIMARY KEY (id_estado_solicitud_cambio) )
;


-- -----------------------------------------------------
-- Table ITEM_CONFIGURACION
-- -----------------------------------------------------
CREATE  TABLE ITEM_CONFIGURACION (
  id_item_configuracion INT NOT NULL IDENTITY ,
  codigo_identificador_ic VARCHAR(5) NOT NULL ,
  nombre_ic VARCHAR(45) NOT NULL ,
  version VARCHAR(10) NOT NULL ,
  ubicacion_en_biblioteca VARCHAR(100) NOT NULL ,
  fecha_ultima_modificacion DATETIME NOT NULL ,
  id_proyecto INT NOT NULL ,
  rut_responsable_ic INT NOT NULL ,
  PRIMARY KEY (id_item_configuracion) ,
  CONSTRAINT fk_ITEM_CONFIGURACION_PROYECTO1
    FOREIGN KEY (id_proyecto )
    REFERENCES PROYECTO (id_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ITEM_CONFIGURACION_FUNCIONARIO_DISICO1
    FOREIGN KEY (rut_responsable_ic )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table SOLICITUD_CAMBIO
-- -----------------------------------------------------
CREATE  TABLE SOLICITUD_CAMBIO (
  id_solicitud_cambio INT NOT NULL IDENTITY ,
  id_proyecto INT NOT NULL ,
  rut_solicitante INT NOT NULL ,
  titulo VARCHAR(50) NOT NULL ,
  fecha_solicitud DATETIME NOT NULL ,
  descripcion_necesidad_cambio TEXT NOT NULL ,
  id_tipo_prioridad TINYINT NOT NULL ,
  id_estado_solicitud_cambio TINYINT NOT NULL ,
  id_item_configuracion INT NOT NULL ,
  descripcion_cambio TEXT NULL ,
  rut_evaluador_impacto INT NULL ,
  fecha_analisis DATETIME NULL ,
  descripcion_impacto_cambio TEXT NULL ,
  fecha_cierre DATETIME NULL ,
  modulo_afectado VARCHAR(60) NULL ,
  rut_evaluador_final INT NULL ,
  descripcion_resolucuion VARCHAR(255) NULL ,
  PRIMARY KEY (id_solicitud_cambio) ,
  CONSTRAINT fk_SOLICITUD_CAMBIO_PROYECTO1
    FOREIGN KEY (id_proyecto )
    REFERENCES PROYECTO (id_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO1
    FOREIGN KEY (rut_solicitante )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_TIPO_PRIORIDAD1
    FOREIGN KEY (id_tipo_prioridad )
    REFERENCES TIPO_PRIORIDAD (id_tipo_prioridad )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_ESTADO_SOLICITUD_CAMBIO1
    FOREIGN KEY (id_estado_solicitud_cambio )
    REFERENCES ESTADO_SOLICITUD_CAMBIO (id_estado_solicitud_cambio )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO2
    FOREIGN KEY (rut_evaluador_impacto )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_FUNCIONARIO_DISICO3
    FOREIGN KEY (rut_evaluador_final )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SOLICITUD_CAMBIO_ITEM_CONFIGURACION1
    FOREIGN KEY (id_item_configuracion )
    REFERENCES ITEM_CONFIGURACION (id_item_configuracion )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table TAREA_SCM
-- -----------------------------------------------------
CREATE  TABLE TAREA_SCM (
  id_tarea_scm INT NOT NULL IDENTITY ,
  nombre_tarea VARCHAR(45) NOT NULL ,
  descripcion_tarea VARCHAR(255) NOT NULL ,
  PRIMARY KEY (id_tarea_scm) )
;


-- -----------------------------------------------------
-- Table NOTIFICACION
-- -----------------------------------------------------
CREATE  TABLE NOTIFICACION (
  id_notificacion BIGINT NOT NULL IDENTITY ,
  fecha DATETIME NOT NULL ,
  mensaje_notificacion VARCHAR(90) NOT NULL,
  revisada BIT  NOT NULL DEFAULT 0 ,
  rut_destinatario INT NOT NULL ,
  PRIMARY KEY (id_notificacion) ,
  CONSTRAINT fk_NOTIFICACION_FUNCIONARIO1
    FOREIGN KEY (rut_destinatario )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table TAREA_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE TAREA_PROYECTO (
  id_tarea_proyecto INT NOT NULL IDENTITY ,
  id_proyecto INT NOT NULL ,
  rut_responsable INT NOT NULL ,
  tarea VARCHAR(60) NOT NULL ,
  fecha_creacion DATE NOT NULL ,
  fecha_inicio_propuesta DATETIME NOT NULL ,
  fecha_inicio_real DATETIME NULL ,
  fecha_termino_propuesta DATETIME NOT NULL ,
  fecha_termino_real DATETIME NULL ,
  nivel_avance SMALLINT NOT NULL ,
  visible BIT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (id_tarea_proyecto) ,
  CONSTRAINT fk_TAREA_PROYECTO_PROYECTO1
    FOREIGN KEY (id_proyecto )
    REFERENCES PROYECTO (id_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_TAREA_PROYECTO_FUNCIONARIO_DISICO1
    FOREIGN KEY (rut_responsable )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table COMENTARIO_SOLICITUD
-- -----------------------------------------------------
CREATE  TABLE COMENTARIO_SOLICITUD (
  id_comentario BIGINT NOT NULL IDENTITY ,
  id_solicitud_req BIGINT NOT NULL ,
  rut_autor INT NOT NULL ,
  comentario TEXT NOT NULL ,
  fecha DATETIME NOT NULL ,
  visible BIT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (id_comentario) ,
  CONSTRAINT fk_COMENTARIO_SOLICITUD_SOLICITUD_REQUERIMIENTO1
    FOREIGN KEY (id_solicitud_req )
    REFERENCES SOLICITUD_REQUERIMIENTO (id_solicitud_req )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_COMENTARIO_SOLICITUD_FUNCIONARIOS1
    FOREIGN KEY (rut_autor )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table TAREA_SCM_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE TAREA_SCM_PROYECTO (
  id_tarea_scm_proyecto INT NOT NULL IDENTITY ,
  id_tarea_scm INT NOT NULL ,
  id_proyecto INT NOT NULL ,
  rut_responsable INT NOT NULL ,
  fecha_inicio DATE NOT NULL ,
  fecha_termino DATE NOT NULL ,
  PRIMARY KEY (id_tarea_scm_proyecto) ,
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
    FOREIGN KEY (rut_responsable )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table ENTREGABLE
-- -----------------------------------------------------
CREATE  TABLE ENTREGABLE (
  id_entregable INT NOT NULL IDENTITY ,
  nombre_entregable VARCHAR(45) NOT NULL ,
  id_tarea_scm INT NOT NULL ,
  PRIMARY KEY (id_entregable) ,
  CONSTRAINT fk_ENTREGABLE_TAREA_SCM1
    FOREIGN KEY (id_tarea_scm )
    REFERENCES TAREA_SCM (id_tarea_scm )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table ROL_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE ROL_PROYECTO (
  id_rol TINYINT NOT NULL IDENTITY ,
  nombre_rol VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_rol) )
;


-- -----------------------------------------------------
-- Table PARTICIPANTE_PROYECTO
-- -----------------------------------------------------
CREATE  TABLE PARTICIPANTE_PROYECTO (
  rut_participante INT NOT NULL ,
  id_proyecto INT NOT NULL ,
  id_rol TINYINT NOT NULL ,
  PRIMARY KEY (rut_participante, id_proyecto) ,
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
    FOREIGN KEY (id_rol )
    REFERENCES ROL_PROYECTO (id_rol )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table FORMULARIO_IMPLEMENTACION
-- -----------------------------------------------------
CREATE  TABLE FORMULARIO_IMPLEMENTACION (
  id_formulario_implementacion INT NOT NULL IDENTITY ,
  observaciones TEXT NOT NULL ,
  fecha_verificacion DATE NOT NULL ,
  rut_verificador INT NOT NULL ,
  rut_implementador INT NOT NULL ,
  id_solicitud_cambio INT NOT NULL ,
  PRIMARY KEY (id_formulario_implementacion) ,
  CONSTRAINT fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO1
    FOREIGN KEY (rut_verificador )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FORMULARIO_IMPLEMENTACION_FUNCIONARIO_DISICO2
    FOREIGN KEY (rut_implementador )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FORMULARIO_IMPLEMENTACION_SOLICITUD_CAMBIO1
    FOREIGN KEY (id_solicitud_cambio )
    REFERENCES SOLICITUD_CAMBIO (id_solicitud_cambio )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table ESTADISTICA_PERSONAL
-- -----------------------------------------------------
CREATE  TABLE ESTADISTICA_PERSONAL (
  id BIGINT NOT NULL IDENTITY ,
  rut_funcionario INT NOT NULL ,
  fecha DATE NOT NULL ,
  cantidad_total_solicitudes_asignadas BIGINT NOT NULL ,
  cantidad_solicitudes_pendientes INT NOT NULL ,
  cantidad_solicitudes_vencidas INT NOT NULL ,
  cantidad_solicitudes_iniciadas INT NOT NULL ,
  cantidad_solicitudes_cerradas INT NOT NULL ,
  cantidad_proyectos_acargo INT NOT NULL ,
  cantidad_proyectos_en_que_participa INT NOT NULL ,
  cantidad_tareas_proyecto_asociadas INT NOT NULL ,
  cantidad_tareas_scm_asociadas INT NOT NULL ,
  PRIMARY KEY (id) ,
  CONSTRAINT fk_ESTADISTICA_PERSONAL_FUNCIONARIO_DISICO1
    FOREIGN KEY (rut_funcionario )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;
