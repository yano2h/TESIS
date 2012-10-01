----------------------------------
-- DATOS PARA USUARIO SOLICITANTE
----------------------------------
-- CUENTA DE USUARIOS EN FUNCIONARIOS AM CON EL ROL CORRESPONDIENTE


----------------------------------------
-- DATOS PARA USUARIO FUNCIONARIO DISICO
-- Remplazar: rut, area
----------------------------------------

--Solicitudes

INSERT INTO FUNCIONARIO (rut, nombre, apellido_paterno, apellido_materno, correo_uv, fecha_ultimo_acceso, fecha_primer_acceso) VALUES
(_rut , 'Nombre', 'Paterno', 'Materno', 'user.uno@uv.cl', NULL, NULL);

INSERT INTO FUNCIONARIO_DISICO (rut, area, cargo, anexo)VALUES 
(_rut, _area, 'Desarrollador', '3211');

INSERT INTO solicitud_requerimiento (asunto, codigo_consulta, fecha_envio, fecha_ultima_actualizacion, fecha_vencimiento, mensaje, respuesta, area_responsable, estado_solicitud, prioridad_solicitud, responsable, solicitante, tipo_solicitud) 
VALUES 
('Prueba Iniciar Solicitud', 'ABC' ,'2012-07-09 09:51:23', '2012-07-09 09:51:23', '2012-10-01 10:00:00', 'Para este caso de prueba, por favor para esta solicitud utilizar la opción Iniciar', NULL, _area, 3, 0, _respon, _solicitante, 1),
('Prueba Respuesta Directa', 'BCD' ,'2012-08-10 08:30:00', '2012-08-10 08:30:00', '2012-10-01 10:00:00', 'Para este caso de prueba, por favor para esta solicitud utilizar la opción Respuesta Directa', NULL, _area, 3, 0, _respon, _solicitante, 1),
('Prueba Respuesta al Jefe de Area', 'CDE' ,'2012-07-09 09:51:23', '2012-07-09 09:51:23', '2012-10-01 10:00:00', 'Para este caso de prueba, por favor para esta solicitud utilizar la opción Respuesta al Jefe de Area', NULL, _area, 3, 0, _respon, _solicitante, 1);

--Proyectos

