DELETE FROM alojamiento
  WHERE id_alojamiento > 5;
DELETE FROM actividad
  WHERE id_actividad > 5;
DELETE FROM REALIZA
  WHERE alojamiento_id_alojamiento > 5 AND actividad_id_actividad > 5;
  
SELECT *
  FROM ALOJAMIENTO;
  
SELECT *
  FROM REALIZA;
  
SELECT *
  FROM ACTIVIDAD;

SELECT *
  FROM vista_actividades_alojamiento;

UPDATE VISTA_ACTIVIDADES_ALOJAMIENTO
  SET NOMBRE_ALOJAMIENTO = 'TEST'
  WHERE ID_ALOJAMIENTO = 1;
  
-- Falta terminar de meter todos los campos de las 2 (?) tablas aqui.  
INSERT INTO vista_actividades_alojamiento (id_actividad, nombre_alojamiento, nombre_actividad, dificultad, capacidad, direccion_social, razon_social, telefono_contacto, descripcion_alojamiento, descripcion_actividad, dia_realizacion, dia_semana, valoracion_alojamiento, fecha_apertura, numero_habitaciones, provincia, localizacion, hora_inicio, hora_fin, nombre_guia)
  VALUES(11, 'nombreAloj.', 'nombreActiv.', 5, 20, 'dirSoc', 'razSoc', 'telefono', 'descripcion aloha', 'descripcion actividad', '23/11/1993', 'lunes', 5, 'fecha_apertura', 10, 'provincia', 'localizacion', '8:30', '15:00', 'Antonio');