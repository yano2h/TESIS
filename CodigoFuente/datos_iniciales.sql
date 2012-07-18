INSERT INTO AREA (`id_area`, `nombre_area`, `descripcion_area`) VALUES 
(1, 'Desarrollo', '�rea de Desarrollo de Sistemas de Informaci�n es la encargada del desarrollo de sistemas inform�ticos, que permitan mejorar la calidad de los servicios que se brindan a alumnos, profesores y funcionarios de la Universidad.'),
(2, 'Fincon', '�rea de Sistemas Financiero-Contables es la encargada de automatizar los diferentes procesos que levan a cabo la Direcci�n de Administraci�n y Finanzas, Direcci�n de Recursos Humanos y la Unidad de Aranceles de la Universidad, adem�s de garantizar el funcionamiento de manera ininterrumpida y eficiente de los sistemas que apoyan estos procesos.'),
(3, 'Redes y Soporte','�rea de Redes, Telecomunicaciones y Soporte es la encargada del correcto funcionamiento de los servicios de redes y comunicaciones, adem�s del soporte de hardware y software de los sistemas computacionales y usuarios de la universidad.');

INSERT INTO ESTADO_SOLICITUD_REQ (`id_estado_solicitud_req`, `nombre_estado_solicitud`, `descripcion_estado`) VALUES 
(0, 'Enviada', 'La solicitud ha sido enviada al area correspondiente y en espera de asignaci�n.'),
(1, 'Rechazada', 'La solicitud a sido rechazada.'),
(2, 'Transferida a otra �rea', 'La solicitud ha sido transferida a otra area.'),
(3, 'Asignada', 'Se a asignado un funcionario quien debe atenderla y resolverla.'),
(4, 'Pendiente', 'El funcionario a rrevisado la solicitud pero no ha comenzado a trabajar en ella.'),
(5, 'Iniciada', 'El funcionario se encuentra trabajando en la solicitud.'),
(6, 'Vencida', 'La solicitud a superado el plazo maximo establecido para resolverla.'),
(7, 'Finalizada en espera de aprobaci�n', 'La solicitud a sido completada y se a devuelto al jefe de area para que sea el quien envie una repsuesta oficial'),
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
(4, 'Acceso a aplicaci�n', ''),
(5, 'Informaci�n', ''),
(6, 'Actualizaci�n de Informaci�n', ''),
(7, 'Reporte', ''),
(8, 'Reporte SharePoint', ''),
(9, 'Reparaci�n de Conexi�n', ''),
(10, 'Reparaci�n de Equipo', '');

INSERT INTO FUNCIONARIO (`rut`, `nombre`, `apellido_paterno`, `apellido_materno`, `correo_uv`, `fecha_ultimo_acceso`, `fecha_primer_acceso`) VALUES
(16775578, 'Alejandro Andres', 'Alvarez', 'Ahumada', 'yano2h@gmail.com', NULL, NULL),
(17010101, 'Alberto Felipe', 'Alvarez', 'Gonzales', 'alberto.felipe@uv.cl', NULL, NULL),
(15409873, 'Carlos Gillermo', 'Villalon', 'Gonzales', 'felipe.villalon@uv.cl', NULL, NULL),
(15409873, 'Andres Mauricio', 'Plaza', 'Rivera', 'andres.plaza@uv.cl', NULL, NULL);
(18000000, 'Alejandro Andres', 'Alvarez', 'Ahumada', 'yano2h@gmail.com', '2012-07-18 16:53:56', '2012-07-12 16:42:20'),
(19000000, 'Rodrigo', 'Cabrera', 'Mu�oz', 'rodrigo.cabrera@uv.cl', NULL, NULL),
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
(7, 'Espera Por revisi�n Unidad'),
(8, 'Espera Reuni�n coordinaci�n'),
(9, 'Finalizado');

INSERT INTO TIPO_PROYECTO values
(1, 'Desarrollo'),
(2, 'Investigacion'),
(3, 'Migracion de Datos');

