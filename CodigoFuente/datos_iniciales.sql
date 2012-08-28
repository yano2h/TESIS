INSERT INTO AREA (`id_area`, `nombre_area`, `descripcion_area`) VALUES 
(1, 'Desarrollo', 'Área de Desarrollo de Sistemas de Información es la encargada del desarrollo de sistemas informáticos, que permitan mejorar la calidad de los servicios que se brindan a alumnos, profesores y funcionarios de la Universidad.'),
(2, 'Fincon', 'Área de Sistemas Financiero-Contables es la encargada de automatizar los diferentes procesos que levan a cabo la Dirección de Administración y Finanzas, Dirección de Recursos Humanos y la Unidad de Aranceles de la Universidad, además de garantizar el funcionamiento de manera ininterrumpida y eficiente de los sistemas que apoyan estos procesos.'),
(3, 'Redes y Soporte','Área de Redes, Telecomunicaciones y Soporte es la encargada del correcto funcionamiento de los servicios de redes y comunicaciones, además del soporte de hardware y software de los sistemas computacionales y usuarios de la universidad.');

INSERT INTO ESTADO_SOLICITUD_REQ (`id_estado_solicitud_req`, `nombre_estado_solicitud`, `descripcion_estado`) VALUES 
(0, 'Enviada', 'La solicitud ha sido enviada al area correspondiente y en espera de asignación.'),
(1, 'Rechazada', 'La solicitud a sido rechazada.'),
(2, 'Transferida a otra Àrea', 'La solicitud ha sido transferida a otra area.'),
(3, 'Asignada', 'Se a asignado un funcionario quien debe atenderla y resolverla.'),
(4, 'Pendiente', 'El funcionario a rrevisado la solicitud pero no ha comenzado a trabajar en ella.'),
(5, 'Iniciada', 'El funcionario se encuentra trabajando en la solicitud.'),
(6, 'Vencida', 'La solicitud a superado el plazo maximo establecido para resolverla.'),
(7, 'Finalizada en espera de aprobación', 'La solicitud a sido completada y se a devuelto al jefe de area para que sea el quien envie una repsuesta oficial'),
(8, 'Cerrada', 'La solicitud ya se ha resuelto por lo que ya no puede ser modificada');

INSERT INTO TIPO_PRIORIDAD (`id_tipo_prioridad`, `nombre_prioridad`) VALUES
(0, 'No Asignada'),
(1, 'Baja'),
(2, 'Media'),
(3, 'Alta'),
(4, 'Critica');

INSERT INTO TIPO_SOLICITUD_REQ (`id_tipo_solicitud_req`, `nombre_tipo_solicitud`, `descripcion_tipo`) VALUES 
(1, 'Correo UV', ''),
(2, 'Clave', ''),
(3, 'Cambio de Clave', ''),
(4, 'Acceso a aplicación', ''),
(5, 'Información', ''),
(6, 'Actualización de Información', ''),
(7, 'Reporte', ''),
(8, 'Reporte SharePoint', ''),
(9, 'Reparación de Conexión', ''),
(10, 'Reparación de Equipo', '');

INSERT INTO FUNCIONARIO (`rut`, `nombre`, `apellido_paterno`, `apellido_materno`, `correo_uv`, `fecha_ultimo_acceso`, `fecha_primer_acceso`) VALUES
(16775578, 'Alejandro Andres', 'Alvarez', 'Ahumada', 'yano2h@gmail.com', NULL, NULL),
(17010101, 'Alberto Felipe', 'Alvarez', 'Gonzales', 'alberto.felipe@uv.cl', NULL, NULL),
(15409873, 'Carlos Gillermo', 'Villalon', 'Gonzales', 'felipe.villalon@uv.cl', NULL, NULL),
(15409873, 'Andres Mauricio', 'Plaza', 'Rivera', 'andres.plaza@uv.cl', NULL, NULL);
(18000000, 'Alejandro Andres', 'Alvarez', 'Ahumada', 'yano2h@gmail.com', '2012-07-18 16:53:56', '2012-07-12 16:42:20'),
(19000000, 'Rodrigo', 'Cabrera', 'Muñoz', 'rodrigo.cabrera@uv.cl', NULL, NULL),
(20000000, 'Sergio', 'Valdivia', 'Castro', 'sergio.valdivia@uv.cl', NULL, NULL);

INSERT INTO FUNCIONARIO_DISICO (`rut`, `area`, `cargo`, `anexo`)VALUES 
(16775578, 1, 'Ayudante', '0000'),
(18000000, 1, 'Ayudante', '2145'),
(19000000, 1, 'Analista/Desarrollo de Sistemas', '2546'),
(20000000, 1, 'Analista/Desarrollo de Sistemas', '3569');


INSERT INTO NOTIFICACION values
(null,CURRENT_TIMESTAMP,'Notificacion 11', false,16775578),
(null,CURRENT_TIMESTAMP,'Notificacion 12', false,16775578),
(null,CURRENT_TIMESTAMP,'Notificacion 13', false,16775578),
(null,CURRENT_TIMESTAMP,'Notificacion 14', false,16775578),
(null,CURRENT_TIMESTAMP,'Notificacion 15', false,16775578),
(null,CURRENT_TIMESTAMP,'Notificacion 16', false,16775578),
(null,CURRENT_TIMESTAMP,'Notificacion 17', false,16775578),
(null,CURRENT_TIMESTAMP,'Notificacion 18', false,16775578),
(null,CURRENT_TIMESTAMP,'Notificacion 19', false,16775578),
(null,CURRENT_TIMESTAMP,'Notificacion 20', false,16775578);

INSERT INTO ESTADO_PROYECTO values
(1, 'Activo'),
(2, 'Espera HH disponible'),
(3, 'Consulta'),
(4, 'Detenido por Prioridad'),
(5, 'Detenido por Cambio de contraparte'),
(6, 'Detenido por Requerimiento'),
(7, 'Espera Por revisión Unidad'),
(8, 'Espera Reunión coordinación'),
(9, 'Finalizado');

INSERT INTO TIPO_PROYECTO values
(1, 'Desarrollo'),
(2, 'Investigacion'),
(3, 'Migracion de Datos');

INSERT INTO TAREAS_SCM values
('1', 'Identificar y Nombrar Items de Configuracion', ''),
('2', 'Solicitar un Cambio', ''),
('3', 'Analizar Solicitudes de Cambio', ''),
('4', 'Aprobar o Desaprobar las Solicitudes de Cambio', ''),
('5', 'Implementar el Cambio', ''),
('6', 'Actualizar Documentación en la Biblioteca de Software', ''),
('7', 'Actualizar Codigo en la Biblioteca de Software', ''),
('8', 'Completar Check-List', '');

INSERT INTO ENTREGABLE values
('1', 'Documento de Identificación de la Configuración', '1'),
('2', 'Solicitud de Cambio', '2'),
('3', 'Analisis de Solicitud de Cambio', '3'),
('4', 'Documento del Usuario', '4'),
('5', 'Documento de Implementacion de Cambios', '5'),
('6', 'Actualización Biblioteca de Software', '6'),
('7', 'Actualización Biblioteca de Software', '7'),
('8', 'Check-List', '8');


--Funcionarios para Test Junit
INSERT INTO FUNCIONARIO (`rut`, `nombre`, `apellido_paterno`, `apellido_materno`, `correo_uv`, `fecha_ultimo_acceso`, `fecha_primer_acceso`) VALUES
(11111111, 'Usuario Uno', 'Paterno', 'Materno', 'user.uno@uv.cl', NULL, NULL), 
(22222222, 'Usuario Dos', 'Paterno', 'Materno', 'user.dos@uv.cl', NULL, NULL);

--Funcionarios para Test Junit
INSERT INTO FUNCIONARIO_DISICO (`rut`, `area`, `cargo`, `anexo`)VALUES 
(11111111, 1, 'Developer', '0000'),
(22222222, 1, 'Tester', '2145');

--Solicitud que debe cambiarse a vencida para test
INSERT INTO `test_sistema_solicitudes`.`solicitud_requerimiento` (`id_solicitud_req`, `asunto`, `codigo_consulta`, `fecha_envio`, `fecha_ultima_actualizacion`, `fecha_vencimiento`, `mensaje`, `respuesta`, `area_responsable`, `estado_solicitud`, `prioridad_solicitud`, `responsable`, `solicitante`, `tipo_solicitud`) VALUES (1, 'Asunto', 'ABCDEF', '2012-07-09 09:51:23', '2012-07-09 09:51:23', '2012-07-19 09:51:23', 'Mensaje', 'Respuesta', 1, 0, 0, NULL, 11111111, 1);

