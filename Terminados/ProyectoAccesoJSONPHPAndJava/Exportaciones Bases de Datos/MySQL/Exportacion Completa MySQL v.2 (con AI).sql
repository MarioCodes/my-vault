-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-11-2016 a las 11:07:59
-- Versión del servidor: 5.6.20
-- Versión de PHP: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `proyecto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actividad`
--

CREATE TABLE IF NOT EXISTS `actividad` (
  `ID_ACTIVIDAD` int(16) NOT NULL,
  `NOMBRE` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `DESCRIPCION` varchar(300) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `DIFICULTAD` decimal(7,0) DEFAULT NULL,
  `CAPACIDAD` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `DIA_REALIZACION` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `HORA_INICIO` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `HORA_FIN` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `NOMBRE_GUIA` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `LOCALIZACION` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `DIA_SEMANA` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `actividad`
--

INSERT INTO `actividad` (`ID_ACTIVIDAD`, `NOMBRE`, `DESCRIPCION`, `DIFICULTAD`, `CAPACIDAD`, `DIA_REALIZACION`, `HORA_INICIO`, `HORA_FIN`, `NOMBRE_GUIA`, `LOCALIZACION`, `DIA_SEMANA`) VALUES
(1, 'Rafting', 'Descenso por el rio Ebro', '3', '6', '23/01/2016', '8:10', '14:20', 'Antonio Perez', 'Ainsa', 'Lunes'),
(2, 'Kayak', 'Recorrido por el rio Barasona', '4', '10', '01/05/2016', '9:10', '11:30', 'Toñi Santa', 'Graus', 'Martes'),
(3, 'Vias Ferrata', 'Realizacion de una via Ferrata', '1', '8', '11/12/2016', '7:00', '16:10', 'Ignacio Matamoros', 'Huesca', 'Jueves'),
(4, 'Escalada en Boulder', 'Realizacion de una salida para escalar en montaña', '4', '12', '05/06/2016', '6:30', '22:30', 'Ramon y Cajal', 'Albarracin', 'Sabado'),
(5, 'Iniciacion a Buceo', 'Iniciacion para obtener la primera estrella de buceo', '3', '4', '01/02/2017', '12:00', '13:30', 'Victoria Revertes', 'Zaragoza', 'Domingo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alojamiento`
--

CREATE TABLE IF NOT EXISTS `alojamiento` (
`ID_ALOJAMIENTO` int(16) NOT NULL,
  `NOMBRE` varchar(80) COLLATE utf8_spanish2_ci NOT NULL,
  `DIRECCION_SOCIAL` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `RAZON_SOCIAL` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `TELEFONO_CONTACTO` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `DESCRIPCION` varchar(500) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `VALORACION` decimal(1,0) DEFAULT NULL,
  `FECHA_APERTURA` varchar(20) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `NUM_HABITACIONES` decimal(7,0) DEFAULT NULL,
  `Provincia` varchar(100) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `alojamiento`
--

INSERT INTO `alojamiento` (`ID_ALOJAMIENTO`, `NOMBRE`, `DIRECCION_SOCIAL`, `RAZON_SOCIAL`, `TELEFONO_CONTACTO`, `DESCRIPCION`, `VALORACION`, `FECHA_APERTURA`, `NUM_HABITACIONES`, `Provincia`) VALUES
(1, 'Las Rosas', 'C:/Maria Zambrano Nº 15', 'Ferragosa S.A.', '974586523', 'El emplazamiento es inigualable debido a la panorámica que se aprecia desde el recinto.', '5', '1998-07-13', '6', 'Huesca'),
(2, 'Puerta de Ordesa', 'C:/Ramon y Cajal Nº 10', 'Hermanos Antonio S.A.', '974315841', 'Podemos ofrecerte apartamentos de tipo superior con hidromasaje, apartamentos para parejas con un dormitorio, como apartamentos para 3 ó 4 personas, con dos dormitorios.', '4', '1976-05-08', '4', 'Zaragoza'),
(3, 'Casa Rivera', 'C:/Santa Cruz Nº 14', 'Ruiz S.L.', '976475825', 'Contamos con un garaje en el bajo para guardar las bicis o los carritos de bebé, con herramientas de trabajo y video-vigilancia.', '3', '1999-05-03', '8', 'Teruel'),
(4, 'La Cuca', 'C:/Doctor Lopez Nº 66', 'Alfambra S.L.', '973461222', 'Disfruta de la tranquilidad sin renunciar a los servicios.', '2', '2007-01-20', '15', 'Huesca'),
(5, 'La Estibialla', 'Valle de Benasque', 'Cerbin S.A.', '976782511', 'Todas las casitas decoradas de forma acogedora están totalmente equipadas con edredones, menaje de cocina, T.V., frigorífico, horno-microondas, calefacción, lavadora, tabla de planchar y plancha.', '1', '1958-05-02', '9', 'Teruel');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitacion`
--

CREATE TABLE IF NOT EXISTS `habitacion` (
`ID_HABITACION` int(16) NOT NULL,
  `EXTRAS_HABITACION` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `PRECIO` decimal(7,2) DEFAULT NULL,
  `CUARTO_BAÑO` char(1) COLLATE utf8_spanish2_ci NOT NULL,
  `TIPO_HABITACION` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `RESEÑAS` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `ALOJAMIENTO_ID_ALOJAMIENTO` int(16) NOT NULL,
  `RESERVA_ID_RESERVA` int(16) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=11 ;

--
-- Volcado de datos para la tabla `habitacion`
--

INSERT INTO `habitacion` (`ID_HABITACION`, `EXTRAS_HABITACION`, `PRECIO`, `CUARTO_BAÑO`, `TIPO_HABITACION`, `RESEÑAS`, `ALOJAMIENTO_ID_ALOJAMIENTO`, `RESERVA_ID_RESERVA`) VALUES
(1, 'MiniBar y Cafetera Italiana', '80.00', '0', 'simple', 'Bien Iluminada', 1, 1),
(2, 'Horno y Cabina de Hidromasaje', '180.00', '1', 'triple', 'Habitacion Amplia', 1, NULL),
(3, 'Microondas', '100.00', '0', 'simple', 'Habitacion Pequeña', 2, NULL),
(4, 'Horno y Piscina', '200.00', '1', 'triple', 'Habitacion considerada de lujo', 2, 2),
(5, 'Cuarto de baño', '90.00', '1', 'simple', 'Habitacion de transito', 3, NULL),
(6, 'Horno, Piscina y Aire acondicionado', '300.00', '1', 'cuadruple', 'Habitacion para familias', 3, 3),
(7, 'Trastero', '110.00', '0', 'simple', 'Habitacion para dormir', 4, NULL),
(8, 'Jardin', '210.00', '1', 'doble', 'Habitacion comoda', 4, 4),
(9, 'Horno', '120.00', '0', 'simple', 'Habitacion luminosa', 5, NULL),
(10, 'Jardin y piscina', '180.00', '1', 'doble', 'Habitacion tranquila', 5, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `realiza`
--

CREATE TABLE IF NOT EXISTS `realiza` (
  `ALOJAMIENTO_ID_ALOJAMIENTO` int(16) NOT NULL,
  `ACTIVIDAD_ID_ACTIVIDAD` int(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `realiza`
--

INSERT INTO `realiza` (`ALOJAMIENTO_ID_ALOJAMIENTO`, `ACTIVIDAD_ID_ACTIVIDAD`) VALUES
(5, 1),
(4, 2),
(3, 3),
(2, 4),
(1, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE IF NOT EXISTS `reserva` (
  `ID_RESERVA` int(16) NOT NULL,
  `NOMBRE` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `APELLIDOS` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `CORREO_ELECTRONICO` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `FORMA_PAGO` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `COMENTARIOS` varchar(400) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `CARGOS_ADICIONALES` varchar(500) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `TOTAL_RESERVA` decimal(7,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`ID_RESERVA`, `NOMBRE`, `APELLIDOS`, `CORREO_ELECTRONICO`, `FORMA_PAGO`, `COMENTARIOS`, `CARGOS_ADICIONALES`, `TOTAL_RESERVA`) VALUES
(1, 'Juan', 'Perez', 'Juanperez@gmail.com', 'efectivo', 'Pidio MiniBar.', '50', '130.00'),
(2, 'Lola', 'Subias', 'Lolasubias@gmail.com', 'tarjeta', 'Pidio salir media hora mas tarde de los habitual.', '20', '450.00'),
(3, 'Antonio', 'Reverte', 'Antonioreverte@gmail.com', 'cheque', 'Nos pago con un cheque.', '150', '800.00'),
(4, 'Ixeia', 'Albajar', 'Ixeiaalbajar@gmail.com', 'tarjeta', 'Entro al dia siguiente de lo acordado.', '15', '230.00'),
(5, 'Victoria', 'LaFuente', 'Victorialafuente@gmail.com', 'efectivo', 'Trae animales.', '30', '300.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajador`
--

CREATE TABLE IF NOT EXISTS `trabajador` (
  `ID_PERSONAL` decimal(7,0) NOT NULL,
  `NOMBRE` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `APELLIDOS` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `DNI` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `DOMICILIO` varchar(150) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `FECHA_CONTRATACION` date NOT NULL,
  `CARGO` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `SALARIO` decimal(7,2) DEFAULT NULL,
  `TELEFONO` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `CORREO_ELECTRONICO` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `SEXO` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `NUMERO_SEC_SOCIAL` decimal(15,0) DEFAULT NULL,
  `FECHA_NACIMIENTO` date DEFAULT NULL,
  `ALOJAMIENTO_ID_TRABAJA` int(16) NOT NULL,
  `ALOJAMIENTO_ID_PERS_CONTACTO` int(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `trabajador`
--

INSERT INTO `trabajador` (`ID_PERSONAL`, `NOMBRE`, `APELLIDOS`, `DNI`, `DOMICILIO`, `FECHA_CONTRATACION`, `CARGO`, `SALARIO`, `TELEFONO`, `CORREO_ELECTRONICO`, `SEXO`, `NUMERO_SEC_SOCIAL`, `FECHA_NACIMIENTO`, `ALOJAMIENTO_ID_TRABAJA`, `ALOJAMIENTO_ID_PERS_CONTACTO`) VALUES
('1', 'Antonio', 'LaBordeta', '73204156A', 'C:/ Divino Pastor Nº 5, 5º', '0000-00-00', 'Jefe', '2112.20', '647589253', 'AntonioLabordeta@gmail.com', 'Varon', '256325412548', '0000-00-00', 1, 1),
('2', 'Maria Nieves', 'Andrade', '73475415G', 'C:/ De Galileo Nº 8, 1º', '0000-00-00', 'Secretaria', '1412.20', '647145853', 'NievesAndrade@gmail.com', 'Mujer', '251458523256', '0000-00-00', 1, NULL),
('3', 'Gabriel', 'Ortiz', '71234534F', 'C:/ Melendez Valdes Nº 9, 1º', '0000-00-00', 'Limpiador', '1222.10', '447182233', 'GabrielOrtiz@gmail.com', 'Mujer', '784525412538', '0000-00-00', 2, NULL),
('4', 'Carla', 'Medina', '71524856E', 'C:/ Divino Pastor Nº 5, 5º', '0000-00-00', 'Jefa', '3002.20', '747539253', 'CarlaMedina@gmail.com', 'Varon', '152366542548', '0000-00-00', 2, 2),
('5', 'Ignacio', 'Santos', '72104556Z', 'C:/ Andres Mellado Nº 66, 8º', '0000-00-00', 'Jefe', '1118.81', '321585483', 'IgnacioSantos@gmail.com', 'Varon', '213216354131', '0000-00-00', 3, 3),
('6', 'Ines', 'Avendaño', '78454113F', 'C:/ Martires de Alcala Nº 15, 5º', '0000-00-00', 'Relaciones Publicas', '1102.60', '321596453', 'InesAvedaño@gmail.com', 'Mujer', '256549684654', '0000-00-00', 3, NULL),
('7', 'Patricia', 'Cruz', '78465455A', 'C:/ Fernando el Catolico Nº 10, 1º', '0000-00-00', 'Jefa', '1412.20', '654321553', 'PatriciaCruz@gmail.com', 'Mujer', '256574655454', '0000-00-00', 4, 4),
('8', 'Alvaro', 'Guajardo', '75745411C', 'C:/ Benito Gutierrez Nº 8, 4º', '0000-00-00', 'Aparcacoches', '1012.20', '687984563', 'AlvaroGuajardo@gmail.com', 'Varon', '213215154488', '0000-00-00', 4, NULL),
('9', 'Isai', 'Morfin', '61203216P', 'C:/ Donoso Cortes Nº 7, 5º', '0000-00-00', 'Becaria', '312.20', '634516542', 'IsaiMorfin@gmail.com', 'Mujer', '654865165448', '0000-00-00', 5, NULL),
('10', 'Rafel', 'Duran', '68754216M', 'C:/ Divino Pastor Nº 85, 9º', '0000-00-00', 'Jefe', '5892.20', '679498413', 'RafaelDuran@gmail.com', 'Varon', '798496816548', '0000-00-00', 5, 5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `actividad`
--
ALTER TABLE `actividad`
 ADD PRIMARY KEY (`ID_ACTIVIDAD`);

--
-- Indices de la tabla `alojamiento`
--
ALTER TABLE `alojamiento`
 ADD PRIMARY KEY (`ID_ALOJAMIENTO`);

--
-- Indices de la tabla `habitacion`
--
ALTER TABLE `habitacion`
 ADD PRIMARY KEY (`ID_HABITACION`), ADD KEY `HABITACION_ALOJAMIENTO_FK` (`ALOJAMIENTO_ID_ALOJAMIENTO`), ADD KEY `HABITACION_RESERVA_FK` (`RESERVA_ID_RESERVA`);

--
-- Indices de la tabla `realiza`
--
ALTER TABLE `realiza`
 ADD PRIMARY KEY (`ALOJAMIENTO_ID_ALOJAMIENTO`,`ACTIVIDAD_ID_ACTIVIDAD`), ADD KEY `FK_ASS_4` (`ACTIVIDAD_ID_ACTIVIDAD`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
 ADD PRIMARY KEY (`ID_RESERVA`);

--
-- Indices de la tabla `trabajador`
--
ALTER TABLE `trabajador`
 ADD PRIMARY KEY (`ID_PERSONAL`), ADD KEY `TRABAJADOR_ALOJAMIENTO_FK_PERS_CONTACTO` (`ALOJAMIENTO_ID_PERS_CONTACTO`), ADD KEY `TRABAJADOR_ALOJAMIENTO_FK_TRABAJA` (`ALOJAMIENTO_ID_TRABAJA`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alojamiento`
--
ALTER TABLE `alojamiento`
MODIFY `ID_ALOJAMIENTO` int(16) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `habitacion`
--
ALTER TABLE `habitacion`
MODIFY `ID_HABITACION` int(16) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `habitacion`
--
ALTER TABLE `habitacion`
ADD CONSTRAINT `HABITACION_ALOJAMIENTO_FK` FOREIGN KEY (`ALOJAMIENTO_ID_ALOJAMIENTO`) REFERENCES `alojamiento` (`ID_ALOJAMIENTO`) ON DELETE CASCADE,
ADD CONSTRAINT `HABITACION_RESERVA_FK` FOREIGN KEY (`RESERVA_ID_RESERVA`) REFERENCES `reserva` (`ID_RESERVA`);

--
-- Filtros para la tabla `realiza`
--
ALTER TABLE `realiza`
ADD CONSTRAINT `FK_ASS_3` FOREIGN KEY (`ALOJAMIENTO_ID_ALOJAMIENTO`) REFERENCES `alojamiento` (`ID_ALOJAMIENTO`) ON DELETE CASCADE,
ADD CONSTRAINT `FK_ASS_4` FOREIGN KEY (`ACTIVIDAD_ID_ACTIVIDAD`) REFERENCES `actividad` (`ID_ACTIVIDAD`) ON DELETE CASCADE;

--
-- Filtros para la tabla `trabajador`
--
ALTER TABLE `trabajador`
ADD CONSTRAINT `TRABAJADOR_ALOJAMIENTO_FK_PERS_CONTACTO` FOREIGN KEY (`ALOJAMIENTO_ID_PERS_CONTACTO`) REFERENCES `alojamiento` (`ID_ALOJAMIENTO`) ON DELETE CASCADE,
ADD CONSTRAINT `TRABAJADOR_ALOJAMIENTO_FK_TRABAJA` FOREIGN KEY (`ALOJAMIENTO_ID_TRABAJA`) REFERENCES `alojamiento` (`ID_ALOJAMIENTO`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
