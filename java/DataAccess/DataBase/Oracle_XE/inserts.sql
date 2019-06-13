-- Generacion de Sequencias
-- Sequencia ID_ALOJAMIENTO
CREATE SEQUENCE seq_al_pk
	INCREMENT BY 1
	START WITH 1;

-- Sequencia ID_HABITACION
CREATE SEQUENCE seq_hab_pk
	INCREMENT BY 1
	START WITH 1;
	
-- Relleno Tabla Alojamiento.
-- Alojamiento 1
INSERT INTO ALOJAMIENTO(ID_ALOJAMIENTO, NOMBRE, DIRECCION_SOCIAL, RAZON_SOCIAL, TELEFONO_CONTACTO, DESCRIPCION, VALORACION, FECHA_APERTURA, NUM_HABITACIONES, PROVINCIA)
    VALUES(seq_al_pk.nextval, 'Las Rosas', 'C:/Maria Zambrano Nº 15', 'Ferragosa S.A.', '974586523', 'El emplazamiento es inigualable debido a la panorámica que se aprecia desde el recinto.', 5, '01-02-1990', 6, 'Huesca');

-- Alojamiento 2
INSERT INTO ALOJAMIENTO(ID_ALOJAMIENTO, NOMBRE, DIRECCION_SOCIAL, RAZON_SOCIAL, TELEFONO_CONTACTO, DESCRIPCION, VALORACION, FECHA_APERTURA, NUM_HABITACIONES, PROVINCIA)
    VALUES(seq_al_pk.nextval, 'Puerta de Ordesa', 'C:/Ramon y Cajal Nº 10', 'Hermanos Antonio S.A.', '974315841', 'Podemos ofrecerte apartamentos de tipo superior con hidromasaje, apartamentos para parejas con un dormitorio, como apartamentos para 3 ó 4 personas, con dos dormitorios.', 4, '5-12-2000', 4, 'Zaragoza');
    
-- Alojamiento 3    
INSERT INTO ALOJAMIENTO(ID_ALOJAMIENTO, NOMBRE, DIRECCION_SOCIAL, RAZON_SOCIAL, TELEFONO_CONTACTO, DESCRIPCION, VALORACION, FECHA_APERTURA, NUM_HABITACIONES, PROVINCIA)
    VALUES(seq_al_pk.nextval, 'Casa Rivera', 'C:/Santa Cruz Nº 14', 'Ruiz S.L.', '976475825', 'Contamos con un garaje en el bajo para guardar las bicis o los carritos de bebé, con herramientas de trabajo y video-vigilancia.', 3, '21-06-1985', 8, 'Teruel');
    
-- Alojamiento 4
INSERT INTO ALOJAMIENTO(ID_ALOJAMIENTO, NOMBRE, DIRECCION_SOCIAL, RAZON_SOCIAL, TELEFONO_CONTACTO, DESCRIPCION, VALORACION, FECHA_APERTURA, NUM_HABITACIONES, PROVINCIA)
    VALUES(seq_al_pk.nextval, 'La Cuca', 'C:/Doctor Lopez Nº 66', 'Alfambra S.L.', '973461222', 'Disfruta de la tranquilidad sin renunciar a los servicios.', 2, '06-12-2003', 15, 'Huesca');
    
-- Alojamiento 5
INSERT INTO ALOJAMIENTO(ID_ALOJAMIENTO, NOMBRE, DIRECCION_SOCIAL, RAZON_SOCIAL, TELEFONO_CONTACTO, DESCRIPCION, VALORACION, FECHA_APERTURA, NUM_HABITACIONES, PROVINCIA)
    VALUES(seq_al_pk.nextval, 'La Estibialla', 'Valle de Benasque', 'Cerbin S.A.', '976782511', 'Todas las casitas decoradas de forma acogedora están totalmente equipadas con edredones, menaje de cocina, T.V., frigorífico, horno-microondas, calefacción, lavadora, tabla de planchar y plancha.', 1, '20-12-1986', 9, 'Teruel');      
COMMIT;


-- Relleno Tabla Trabajador. En este ejemplo, los Alojamientos tendran 2 trabajadores cada uno; uno de ellos trabajador normal y el otro jefe, que sera a su vez persona de contacto
-- Trabajadores Alojamiento 1.
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA, ALOJAMIENTO_ID_PERS_CONTACTO)
    VALUES(1, 'Antonio', 'LaBordeta', '73204156A', 'C:/ Divino Pastor Nº 5, 5º', '21-10-2005', 'Jefe', 2112.20, '647589253', 'AntonioLabordeta@gmail.com', 'Varon', 256325412548, '05-01-1986', 1, 1);
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA)
    VALUES(2, 'Maria Nieves', 'Andrade', '73475415G', 'C:/ De Galileo Nº 8, 1º', '12-02-2003', 'Secretaria', 1412.20, '647145853', 'NievesAndrade@gmail.com', 'Mujer', 251458523256, '03-03-1982', 1);
    
-- Trabajadores Alojamiento 2.  
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA)
    VALUES(3, 'Gabriel', 'Ortiz', '71234534F', 'C:/ Melendez Valdes Nº 9, 1º', '05-11-2010', 'Limpiador', 1222.10, '447182233', 'GabrielOrtiz@gmail.com', 'Mujer', 784525412538, '12-12-1988', 2);
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA, ALOJAMIENTO_ID_PERS_CONTACTO)
    VALUES(4, 'Carla', 'Medina', '71524856E', 'C:/ Divino Pastor Nº 5, 5º', '21-10-2005', 'Jefa', 3002.20, '747539253', 'CarlaMedina@gmail.com', 'Varon', 152366542548, '05-01-1993', 2, 2);
    
-- Trabajadores Alojamiento 3.
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA, ALOJAMIENTO_ID_PERS_CONTACTO)
    VALUES(5, 'Ignacio', 'Santos', '72104556Z', 'C:/ Andres Mellado Nº 66, 8º', '11-11-2000', 'Jefe', 1118.81, '321585483', 'IgnacioSantos@gmail.com', 'Varon', 213216354131, '15-03-1996', 3, 3);
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA)
    VALUES(6, 'Ines', 'Avendaño', '78454113F', 'C:/ Martires de Alcala Nº 15, 5º', '01-10-2015', 'Relaciones Publicas', 1102.60, '321596453', 'InesAvedaño@gmail.com', 'Mujer', 256549684654, '05-11-1976', 3);
    
-- Trabajadores Alojamiento 4.
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA, ALOJAMIENTO_ID_PERS_CONTACTO)
    VALUES(7, 'Patricia', 'Cruz', '78465455A', 'C:/ Fernando el Catolico Nº 10, 1º', '20-11-2015', 'Jefa', 1412.20, '654321553', 'PatriciaCruz@gmail.com', 'Mujer', 256574655454, '05-01-1976', 4, 4);
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA)
    VALUES(8, 'Alvaro', 'Guajardo', '75745411C', 'C:/ Benito Gutierrez Nº 8, 4º', '21-09-1995', 'Aparcacoches', 1012.20, '687984563', 'AlvaroGuajardo@gmail.com', 'Varon', 213215154488, '05-01-1996', 4);
  
-- Trabajadores Alojamiento 5.  
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA)
    VALUES(9, 'Isai', 'Morfin', '61203216P', 'C:/ Donoso Cortes Nº 7, 5º', '21-10-2015', 'Becaria', 312.20, '634516542', 'IsaiMorfin@gmail.com', 'Mujer', 654865165448, '05-11-1975', 5);
INSERT INTO TRABAJADOR(ID_PERSONAL, NOMBRE, APELLIDOS, DNI, DOMICILIO, FECHA_CONTRATACION, CARGO, SALARIO, TELEFONO, CORREO_ELECTRONICO, SEXO, NUMERO_SEC_SOCIAL, FECHA_NACIMIENTO, ALOJAMIENTO_ID_TRABAJA, ALOJAMIENTO_ID_PERS_CONTACTO)
    VALUES(10, 'Rafel', 'Duran', '68754216M', 'C:/ Divino Pastor Nº 85, 9º', '21-10-1995', 'Jefe', 5892.20, '679498413', 'RafaelDuran@gmail.com', 'Varon', 798496816548, '06-12-1981', 5, 5);
COMMIT;    


-- Relleno Tabla Reservas. Cada Alojamiento tendra reservada una de las 2 habitaciones de las que dispone.
-- Reserva 1
INSERT INTO RESERVA(ID_RESERVA, NOMBRE, APELLIDOS, CORREO_ELECTRONICO, FORMA_PAGO, COMENTARIOS, CARGOS_ADICIONALES, TOTAL_RESERVA)
  VALUES(1, 'Juan', 'Perez', 'Juanperez@gmail.com', 'efectivo', 'Pidio MiniBar.', 50, 130);
  
-- Reserva 2
INSERT INTO RESERVA(ID_RESERVA, NOMBRE, APELLIDOS, CORREO_ELECTRONICO, FORMA_PAGO, COMENTARIOS, CARGOS_ADICIONALES, TOTAL_RESERVA)
  VALUES(2, 'Lola', 'Subias', 'Lolasubias@gmail.com', 'tarjeta', 'Pidio salir media hora mas tarde de los habitual.', 20, 450);
  
-- Reserva 3
INSERT INTO RESERVA(ID_RESERVA, NOMBRE, APELLIDOS, CORREO_ELECTRONICO, FORMA_PAGO, COMENTARIOS, CARGOS_ADICIONALES, TOTAL_RESERVA)
  VALUES(3, 'Antonio', 'Reverte', 'Antonioreverte@gmail.com', 'cheque', 'Nos pago con un cheque.', 150, 800);
  
-- Reserva 4
INSERT INTO RESERVA(ID_RESERVA, NOMBRE, APELLIDOS, CORREO_ELECTRONICO, FORMA_PAGO, COMENTARIOS, CARGOS_ADICIONALES, TOTAL_RESERVA)
  VALUES(4, 'Ixeia', 'Albajar', 'Ixeiaalbajar@gmail.com', 'tarjeta', 'Entro al dia siguiente de lo acordado.', 15, 230);
  
-- Reserva 5
INSERT INTO RESERVA(ID_RESERVA, NOMBRE, APELLIDOS, CORREO_ELECTRONICO, FORMA_PAGO, COMENTARIOS, CARGOS_ADICIONALES, TOTAL_RESERVA)
  VALUES(5, 'Victoria', 'LaFuente', 'Victorialafuente@gmail.com', 'efectivo', 'Trae animales.', 30, 300);
COMMIT;
  
  
-- Relleno Tabla Habitaciones. Cada Alojamiento tendra 2 habitaciones, una de ellas reservada.
-- Aloj. 1, Habitacion 1
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO, RESERVA_ID_RESERVA)
  VALUES(seq_hab_pk.nextval, 'MiniBar y Cafetera Italiana', 80, 'simple', 'Bien Iluminada', 1, 1);
-- Aloj. 1, Habitacion 2
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO)
  VALUES(seq_hab_pk.nextval, 'Horno y Cabina de Hidromasaje', 180, 'triple', 'Habitacion Amplia', 1);
  
-- Aloj. 2, Habitacion 1
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO)
  VALUES(seq_hab_pk.nextval, 'Microondas', 100, 'simple', 'Habitacion Pequeña', 2);
-- Aloj. 2, Habitacion 2
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO, RESERVA_ID_RESERVA)
  VALUES(seq_hab_pk.nextval, 'Horno y Piscina', 200, 'triple', 'Habitacion considerada de lujo', 2, 2);
  
-- Aloj. 3, Habitacion 1
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO)
  VALUES(seq_hab_pk.nextval, 'Cuarto de baño', 90, 'simple', 'Habitacion de transito', 3);
-- Aloj. 3, Habitacion 2
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO, RESERVA_ID_RESERVA)
  VALUES(seq_hab_pk.nextval, 'Horno, Piscina y Aire acondicionado', 300, 'cuadruple', 'Habitacion para familias', 3, 3);
  
-- Aloj. 4, Habitacion 1
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO)
  VALUES(seq_hab_pk.nextval, 'Trastero', 110, 'simple', 'Habitacion para dormir', 4);
-- Aloj. 4, Habitacion 2
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO, RESERVA_ID_RESERVA)
  VALUES(seq_hab_pk.nextval, 'Jardin', 210, 'doble', 'Habitacion comoda', 4, 4);
  
-- Aloj. 5, Habitacion 1
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO)
  VALUES(seq_hab_pk.nextval, 'Horno', 120, 'simple', 'Habitacion luminosa', 5);
-- Aloj. 5, Habitacion 2
INSERT INTO HABITACION(ID_HABITACION, EXTRAS_HABITACION, PRECIO, TIPO_HABITACION, RESENIAS, ALOJAMIENTO_ID_ALOJAMIENTO, RESERVA_ID_RESERVA)
  VALUES(seq_hab_pk.nextval, 'Jardin y piscina', 180, 'doble', 'Habitacion tranquila', 5, 5);
  
  
-- Relleno para Actividades, cada Alojamiento realizara una.
-- Actividad Alojamiento 1
INSERT INTO ACTIVIDAD(ID_ACTIVIDAD, NOMBRE, DESCRIPCION, DIFICULTAD, CAPACIDAD, DIA_REALIZACION, HORA_INICIO, HORA_FIN, NOMBRE_GUIA, LOCALIZACION, DIA_SEMANA)
  VALUES(1, 'Rafting', 'Descenso por el rio Ebro', 3, 6, '23/01/2016', '8:10', '14:20', 'Antonio Perez', 'Ainsa', 'Lunes');
  
-- Actividad Alojamiento 2
INSERT INTO ACTIVIDAD(ID_ACTIVIDAD, NOMBRE, DESCRIPCION, DIFICULTAD, CAPACIDAD, DIA_REALIZACION, HORA_INICIO, HORA_FIN, NOMBRE_GUIA, LOCALIZACION, DIA_SEMANA)
  VALUES(2, 'Kayak', 'Recorrido por el rio Barasona', 4, 10, '01/05/2016', '9:10', '11:30', 'Toñi Santa', 'Graus', 'Martes');
  
-- Actividad Alojamiento 3
INSERT INTO ACTIVIDAD(ID_ACTIVIDAD, NOMBRE, DESCRIPCION, DIFICULTAD, CAPACIDAD, DIA_REALIZACION, HORA_INICIO, HORA_FIN, NOMBRE_GUIA, LOCALIZACION, DIA_SEMANA)
  VALUES(3, 'Vias Ferrata', 'Realizacion de una via Ferrata', 1, 8, '11/12/2016', '7:00', '16:10', 'Ignacio Matamoros', 'Huesca', 'Jueves');
  
-- Actividad Alojamiento 4
INSERT INTO ACTIVIDAD(ID_ACTIVIDAD, NOMBRE, DESCRIPCION, DIFICULTAD, CAPACIDAD, DIA_REALIZACION, HORA_INICIO, HORA_FIN, NOMBRE_GUIA, LOCALIZACION, DIA_SEMANA)
  VALUES(4, 'Escalada en Boulder', 'Realizacion de una salida para escalar en montaña', 4, 12, '05/06/2016', '6:30', '22:30', 'Ramon y Cajal', 'Albarracin', 'Sabado');
  
-- Actividad Alojamiento 3
INSERT INTO ACTIVIDAD(ID_ACTIVIDAD, NOMBRE, DESCRIPCION, DIFICULTAD, CAPACIDAD, DIA_REALIZACION, HORA_INICIO, HORA_FIN, NOMBRE_GUIA, LOCALIZACION, DIA_SEMANA)
  VALUES(5, 'Iniciacion a Buceo', 'Iniciacion para obtener la primera estrella de buceo', 3, 4, '01/02/2017', '12:00', '13:30', 'Victoria Revertes', 'Zaragoza', 'Domingo');
  
-- Relleno Realiza. Tabla de relacion n a n.
-- Realizacion Alojamiento 1
INSERT INTO REALIZA(ALOJAMIENTO_ID_ALOJAMIENTO, ACTIVIDAD_ID_ACTIVIDAD)
  VALUES(1, 5);

-- Realizacion Alojamiento 2
INSERT INTO REALIZA(ALOJAMIENTO_ID_ALOJAMIENTO, ACTIVIDAD_ID_ACTIVIDAD)
  VALUES(2, 4);

-- Realizacion Alojamiento 3
INSERT INTO REALIZA(ALOJAMIENTO_ID_ALOJAMIENTO, ACTIVIDAD_ID_ACTIVIDAD)
  VALUES(3, 3);

-- Realizacion Alojamiento 4
INSERT INTO REALIZA(ALOJAMIENTO_ID_ALOJAMIENTO, ACTIVIDAD_ID_ACTIVIDAD)
  VALUES(4, 2);

-- Realizacion Alojamiento 5
INSERT INTO REALIZA(ALOJAMIENTO_ID_ALOJAMIENTO, ACTIVIDAD_ID_ACTIVIDAD)
  VALUES(5, 1);
COMMIT;