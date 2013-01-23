
-- -----------------------------------------------------
-- Table TIPO_SOLICITUD_REQ
-- -----------------------------------------------------
CREATE TABLE TIPO_SOLICITUD_REQ (
  id_tipo_solicitud_req SMALLINT NOT NULL ,
  nombre_tipo_solicitud VARCHAR(45) NOT NULL ,
  descripcion_tipo VARCHAR(255) NULL ,
  PRIMARY KEY (id_tipo_solicitud_req) );


-- -----------------------------------------------------
-- Table ESTADO_SOLICITUD_REQ
-- -----------------------------------------------------
CREATE TABLE ESTADO_SOLICITUD_REQ (
  id_estado_solicitud_req SMALLINT NOT NULL ,
  nombre_estado_solicitud VARCHAR(45) NOT NULL ,
  descripcion_estado VARCHAR(255) NULL ,
  PRIMARY KEY (id_estado_solicitud_req) );


-- -----------------------------------------------------
-- Table AREA
-- -----------------------------------------------------
CREATE TABLE AREA (
  id_area SMALLINT NOT NULL ,
  nombre_area VARCHAR(45) NOT NULL ,
  descripcion_area TEXT NULL ,
  PRIMARY KEY (id_area) );


-- -----------------------------------------------------
-- Table FUNCIONARIO
-- -----------------------------------------------------
CREATE TABLE FUNCIONARIO (
  rut INT NOT NULL ,
  nombre VARCHAR(45) NOT NULL ,
  apellido_paterno VARCHAR(25) NOT NULL ,
  apellido_materno VARCHAR(25) NOT NULL ,
  correo_uv VARCHAR(45) NULL ,
  fecha_ultimo_acceso TIMESTAMP NULL ,
  fecha_primer_acceso TIMESTAMP NULL ,
  PRIMARY KEY (rut) );


-- -----------------------------------------------------
-- Table FUNCIONARIO_DISICO
-- -----------------------------------------------------
CREATE TABLE FUNCIONARIO_DISICO (
  rut INT NOT NULL ,
  area SMALLINT NOT NULL ,
  cargo VARCHAR(45) NOT NULL ,
  anexo VARCHAR(5) NULL ,
  PRIMARY KEY (rut) ,
  CONSTRAINT fk_FUNCIONARIO_DISICO_FUNCIONARIOS1
    FOREIGN KEY (rut )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FUNCIONARIO_DISICO_AREA1
    FOREIGN KEY (area )
    REFERENCES AREA (id_area )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table TIPO_PRIORIDAD
-- -----------------------------------------------------
CREATE TABLE TIPO_PRIORIDAD (
  id_tipo_prioridad SMALLINT NOT NULL ,
  nombre_prioridad VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_tipo_prioridad) );


-- -----------------------------------------------------
-- Table SOLICITUD_REQUERIMIENTO
-- -----------------------------------------------------
CREATE TABLE SOLICITUD_REQUERIMIENTO (
  id_solicitud_req BIGSERIAL NOT NULL ,
  codigo_consulta VARCHAR(9) NOT NULL UNIQUE ,
  asunto VARCHAR(45) NOT NULL ,
  mensaje TEXT NOT NULL ,
  tipo_solicitud SMALLINT NOT NULL ,
  estado_solicitud SMALLINT NOT NULL ,
  prioridad_solicitud SMALLINT NOT NULL ,
  area_responsable SMALLINT NOT NULL ,
  responsable INT NULL ,
  solicitante INT NOT NULL ,
  fecha_envio TIMESTAMP NOT NULL ,
  fecha_cierre TIMESTAMP NULL ,
  fecha_vencimiento TIMESTAMP NULL ,
  fecha_ultima_actualizacion TIMESTAMP NOT NULL ,
  justificacion_trasnferencia VARCHAR(255) NULL ,
  respuesta TEXT NULL ,
  PRIMARY KEY (id_solicitud_req) ,
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
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table TIPO_PROYECTO
-- -----------------------------------------------------
CREATE TABLE TIPO_PROYECTO (
  id_tipo_proyecto SMALLINT NOT NULL ,
  nombre_tipo_proyecto VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_tipo_proyecto) );


-- -----------------------------------------------------
-- Table ESTADO_PROYECTO
-- -----------------------------------------------------
CREATE TABLE ESTADO_PROYECTO (
  id_estado_proyecto SMALLINT NOT NULL ,
  nombre_estado_proyecto VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_estado_proyecto) );


-- -----------------------------------------------------
-- Table PROYECTO
-- -----------------------------------------------------
CREATE TABLE PROYECTO (
  id_proyecto SERIAL NOT NULL ,
  codigo_interno VARCHAR(6) NOT NULL ,
  nombre VARCHAR(45) NOT NULL ,
  descripcion VARCHAR(255) NOT NULL ,
  fecha_inicio TIMESTAMP NOT NULL ,
  fecha_termino TIMESTAMP NULL ,
  tipo_proyecto SMALLINT NOT NULL ,
  estado_proyecto SMALLINT NOT NULL ,
  PRIMARY KEY (id_proyecto) ,
  CONSTRAINT fk_PROYECTO_TIPO_PROYECTO1
    FOREIGN KEY (tipo_proyecto )
    REFERENCES TIPO_PROYECTO (id_tipo_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_PROYECTO_ESTADO_PROYECTO1
    FOREIGN KEY (estado_proyecto )
    REFERENCES ESTADO_PROYECTO (id_estado_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table ESTADO_SOLICITUD_CAMBIO
-- -----------------------------------------------------
CREATE TABLE ESTADO_SOLICITUD_CAMBIO (
  id_estado_solicitud_cambio SMALLINT NOT NULL ,
  nombre_estado_solicitud VARCHAR(45) NOT NULL ,
  descripcion_estado VARCHAR(255) NULL ,
  PRIMARY KEY (id_estado_solicitud_cambio) )
;


-- -----------------------------------------------------
-- Table ITEM_CONFIGURACION
-- -----------------------------------------------------
CREATE TABLE ITEM_CONFIGURACION (
  id_item_configuracion SERIAL NOT NULL ,
  codigo_identificador_ic VARCHAR(5) NOT NULL ,
  nombre_item_configuracion VARCHAR(45) NOT NULL ,
  version VARCHAR(10) NOT NULL ,
  ubicacion_en_biblioteca VARCHAR(200) NOT NULL ,
  fecha_ultima_modificacion TIMESTAMP NOT NULL ,
  proyecto INT NOT NULL ,
  responsable_item INT NOT NULL ,
  PRIMARY KEY (id_item_configuracion) ,
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
;


-- -----------------------------------------------------
-- Table SOLICITUD_CAMBIO
-- -----------------------------------------------------
CREATE TABLE SOLICITUD_CAMBIO (
  id_solicitud_cambio SERIAL NOT NULL ,
  proyecto INT NOT NULL ,
  solicitante INT NOT NULL ,
  titulo VARCHAR(50) NOT NULL ,
  fecha_envio TIMESTAMP NOT NULL ,
  descripcion_necesidad_cambio TEXT NOT NULL ,
  prioridad_solicitud SMALLINT NOT NULL ,
  estado_solicitud SMALLINT NOT NULL ,
  item_configuracion INT NOT NULL ,
  descripcion_cambio TEXT NULL ,
  evaluador_impacto INT NULL ,
  fecha_analisis TIMESTAMP NULL ,
  descripcion_impacto_cambio TEXT NULL ,
  fecha_cierre TIMESTAMP NULL ,
  modulo_afectado VARCHAR(60) NULL ,
  evaluador_final INT NULL ,
  descripcion_resolucion VARCHAR(255) NULL ,
  aprobada BOOLEAN NULL ,
  PRIMARY KEY (id_solicitud_cambio) ,
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
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table TAREA_SCM
-- -----------------------------------------------------
CREATE TABLE TAREA_SCM (
  id_tarea_scm SERIAL NOT NULL ,
  nombre_tarea VARCHAR(60) NOT NULL ,
  descripcion_tarea VARCHAR(255) NOT NULL ,
  PRIMARY KEY (id_tarea_scm) );


-- -----------------------------------------------------
-- Table NOTIFICACION
-- -----------------------------------------------------
CREATE TABLE NOTIFICACION (
  id_notificacion BIGSERIAL NOT NULL ,
  fecha TIMESTAMP NOT NULL ,
  mensaje_notificacion VARCHAR(180) NOT NULL ,
  revisada BOOLEAN NOT NULL DEFAULT false ,
  destinatario INT NOT NULL ,
  PRIMARY KEY (id_notificacion) ,
  CONSTRAINT fk_NOTIFICACION_FUNCIONARIO1
    FOREIGN KEY (destinatario )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table TAREA_PROYECTO
-- -----------------------------------------------------
CREATE TABLE TAREA_PROYECTO (
  id_tarea_proyecto SERIAL NOT NULL,
  proyecto INT NOT NULL ,
  responsable_tarea INT NOT NULL ,
  tarea VARCHAR(60) NOT NULL ,
  descripcion_tarea VARCHAR(255) NOT NULL ,
  fecha_creacion DATE NOT NULL ,
  fecha_inicio_propuesta TIMESTAMP NOT NULL ,
  fecha_inicio_real TIMESTAMP NULL ,
  fecha_termino_propuesta TIMESTAMP NOT NULL ,
  fecha_termino_real TIMESTAMP NULL ,
  nivel_avance SMALLINT NOT NULL ,
  visible BOOLEAN NOT NULL ,
  PRIMARY KEY (id_tarea_proyecto) ,
  CONSTRAINT fk_TAREA_PROYECTO_PROYECTO1
    FOREIGN KEY (proyecto )
    REFERENCES PROYECTO (id_proyecto )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_TAREA_PROYECTO_FUNCIONARIO_DISICO1
    FOREIGN KEY (responsable_tarea )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table COMENTARIO_SOLICITUD
-- -----------------------------------------------------
CREATE TABLE COMENTARIO_SOLICITUD (
  id_comentario BIGSERIAL NOT NULL,
  solicitud_requerimiento BIGINT NOT NULL ,
  autor INT NOT NULL ,
  comentario TEXT NOT NULL ,
  fecha TIMESTAMP NOT NULL ,
  visible BOOLEAN NOT NULL DEFAULT true ,
  PRIMARY KEY (id_comentario) ,
  CONSTRAINT fk_COMENTARIO_SOLICITUD_SOLICITUD_REQUERIMIENTO1
    FOREIGN KEY (solicitud_requerimiento )
    REFERENCES SOLICITUD_REQUERIMIENTO (id_solicitud_req )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_COMENTARIO_SOLICITUD_FUNCIONARIOS1
    FOREIGN KEY (autor )
    REFERENCES FUNCIONARIO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table TAREA_SCM_PROYECTO
-- -----------------------------------------------------
CREATE TABLE TAREA_SCM_PROYECTO (
  id_tarea_scm INT NOT NULL ,
  id_proyecto INT NOT NULL ,
  responsable INT NOT NULL ,
  fecha_inicio DATE NOT NULL ,
  fecha_termino DATE NOT NULL ,
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
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table ENTREGABLE
-- -----------------------------------------------------
CREATE TABLE ENTREGABLE (
  id_entregable SERIAL NOT NULL ,
  nombre_entregable VARCHAR(50) NOT NULL ,
  tarea_scm INT NOT NULL ,
  PRIMARY KEY (id_entregable) ,
  CONSTRAINT fk_ENTREGABLE_TAREA_SCM1
    FOREIGN KEY (tarea_scm )
    REFERENCES TAREA_SCM (id_tarea_scm )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table ROL_PROYECTO
-- -----------------------------------------------------
CREATE TABLE ROL_PROYECTO (
  id_rol SERIAL NOT NULL ,
  nombre_rol VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_rol) )
;


-- -----------------------------------------------------
-- Table PARTICIPANTE_PROYECTO
-- -----------------------------------------------------
CREATE TABLE PARTICIPANTE_PROYECTO (
  rut_participante INT NOT NULL ,
  id_proyecto INT NOT NULL ,
  rol INT NOT NULL ,
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
    FOREIGN KEY (rol )
    REFERENCES ROL_PROYECTO (id_rol )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table FORMULARIO_IMPLEMENTACION
-- -----------------------------------------------------
CREATE TABLE FORMULARIO_IMPLEMENTACION (
  id_formulario_implementacion INT NOT NULL ,
  observaciones TEXT NOT NULL ,
  fecha_verificacion DATE NOT NULL ,
  verificador INT NOT NULL ,
  implementador INT NOT NULL ,
  nueva_version VARCHAR(10) NOT NULL ,
  nuevo_identificador VARCHAR(5) NOT NULL ,
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
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table ESTADISTICA_PERSONAL
-- -----------------------------------------------------
CREATE TABLE ESTADISTICA_PERSONAL (
  id BIGSERIAL NOT NULL ,
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
  PRIMARY KEY (id) ,
  CONSTRAINT fk_ESTADISTICA_PERSONAL_FUNCIONARIO_DISICO1
    FOREIGN KEY (funcionario )
    REFERENCES FUNCIONARIO_DISICO (rut )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table ARCHIVO_ADJUNTO
-- -----------------------------------------------------
CREATE TABLE ARCHIVO_ADJUNTO (
    id_archivo BIGSERIAL NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    path_file VARCHAR(255) NOT NULL,
    size_file BIGINT NOT NULL,
    size_format VARCHAR(10) NOT NULL,
    mimetype VARCHAR(100) ,
    fecha_upload TIMESTAMP NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_archivo)
);

-- -----------------------------------------------------
-- Table ARCHIVO_SOLICITUD_REQUERIMIENTO
-- -----------------------------------------------------
CREATE TABLE ARCHIVO_SOLICITUD_REQUERIMIENTO(
    id_solicitud BIGINT NOT NULL,
    id_archivo BIGINT NOT NULL UNIQUE,
    PRIMARY KEY (id_solicitud,id_archivo),
    FOREIGN KEY (id_solicitud) REFERENCES solicitud_requerimiento (id_solicitud_req),
    FOREIGN KEY (id_archivo) REFERENCES ARCHIVO_ADJUNTO (id_archivo)
);

-- -----------------------------------------------------
-- Table ARCHIVO_PROYECTO
-- -----------------------------------------------------
CREATE TABLE ARCHIVO_PROYECTO(
    id_proyecto INT NOT NULL,
    id_archivo BIGINT NOT NULL UNIQUE,
    PRIMARY KEY (id_proyecto,id_archivo),
    FOREIGN KEY (id_proyecto) REFERENCES PROYECTO (id_proyecto),
    FOREIGN KEY (id_archivo) REFERENCES ARCHIVO_ADJUNTO (id_archivo)
);

-- -----------------------------------------------------
-- View RESUMEN_SOLICITUD_REQUERIMIENTO
-- -----------------------------------------------------
CREATE VIEW RESUMEN_SOLICITUD_REQUERIMIENTO AS
    SELECT 
      SR.id_solicitud_req, 
      SR.codigo_consulta, 
      SR.asunto, 
      SR.responsable,
      (substring(F.nombre from 1 for 1)||'.'||F.apellido_paterno) AS nombreCorto,
      SR.estado_solicitud, 
      SR.tipo_solicitud, 
      SR.fecha_envio, 
      SR.fecha_vencimiento ,
      SR.area_responsable
    FROM SOLICITUD_REQUERIMIENTO SR
    LEFT JOIN FUNCIONARIO F 
      ON F.rut = SR.responsable 
    ORDER BY fecha_envio DESC 



-- -----------------------------------------------------
-- ADD COLUMNA AREA AL PROYECTO
-- -----------------------------------------------------
ALTER TABLE PROYECTO ADD area_responsable SMALLINT;
ALTER TABLE PROYECTO ADD FOREIGN KEY(area_responsable) REFERENCES AREA(id_area);


-- UPDATE DEL area_responsable

UPDATE PROYECTO P
SET area_responsable = (SELECT F.area
      FROM PARTICIPANTE_PROYECTO PP, FUNCIONARIO_DISICO F 
      WHERE PP.rol = 1 AND F.rut = PP.rut_participante AND PP.id_proyecto = P.id_proyecto)

-- AGREGA NOT NULL AL area_responsable
ALTER TABLE PROYECTO ALTER COLUMN area_responsable SET NOT NULL;


-- -----------------------------------------------------
-- TABLE ETAPA_PROYECTO
-- -----------------------------------------------------
CREATE TABLE ETAPA_PROYECTO(
  id_etapa_proyecto SMALLINT NOT NULL ,
  nombre_etapa_proyecto VARCHAR(45) NOT NULL ,
  PRIMARY KEY (id_etapa_proyecto) 
);

INSERT INTO ETAPA_PROYECTO VALUES 
(0, 'Solicitud de Proyecto'),
(1, 'Toma de Requerimientos'),
(2, 'Definición de Requerimientos'),
(3, 'Diseño'),
(4, 'Codificación'),
(5, 'Pruebas de Software'),
(6, 'Mantenciones correctivas'),
(7, 'Puesta en Producción'),
(8, 'Marcha Blanca'),
(9, 'Liberación del producto y cierre');

ALTER TABLE PROYECTO ADD etapa_proyecto SMALLINT NOT NULL DEFAULT 0;
ALTER TABLE PROYECTO ADD FOREIGN KEY(etapa_proyecto) REFERENCES ETAPA_PROYECTO(id_etapa_proyecto);

-- ---------------------------------------------------------------------------------
-- Se Agrega la columna tipo de informacion a los archivos adjuntos a los proyectos
-- ---------------------------------------------------------------------------------

ALTER TABLE archivo_proyecto ADD COLUMN tipo_informacion VARCHAR(200) NOT NULL;

-- ---------------------------------------------------------------------------------
-- Se agrega la bitacora de proyectos
-- ---------------------------------------------------------------------------------
CREATE TABLE REGISTRO_BITACORA(
    id_registro_bitacora BIGSERIAL NOT NULL,
    fecha_registro DATE NOT NULL,
    estado_proyecto SMALLINT NOT NULL ,
    descripcion VARCHAR(255) NOT NULL ,
    funcionario_responsable INT NULL,
    contraparte_responsable VARCHAR(60) NULL,
    proyecto INT NOT NULL,
    FOREIGN KEY (estado_proyecto) REFERENCES ESTADO_PROYECTO (id_estado_proyecto),
    FOREIGN KEY (funcionario_responsable) REFERENCES FUNCIONARIO_DISICO (rut),
    FOREIGN KEY (proyecto) REFERENCES PROYECTO (id_proyecto),
    PRIMARY KEY (id_registro_bitacora)
);

-- ---------------------------------------------------------------------------------
-- Se agrega la unidad solicitante del proyecto
-- ---------------------------------------------------------------------------------
CREATE TABLE UNIDAD_SOLICITANTE(
    id_unidad_solicitante SERIAL NOT NULL ,
    nombre_unidad_solicitante VARCHAR(60) NOT NULL ,
    PRIMARY KEY(id_unidad_solicitante)
);

INSERT INTO UNIDAD_SOLICITANTE VALUES
(0,'No Especificado');

ALTER TABLE PROYECTO ADD unidad_solicitante INT NOT NULL DEFAULT 0;
ALTER TABLE PROYECTO ADD FOREIGN KEY(unidad_solicitante) REFERENCES UNIDAD_SOLICITANTE(id_unidad_solicitante);
