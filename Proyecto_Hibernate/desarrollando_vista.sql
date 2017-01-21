SELECT *
  FROM VISTA_ACTIVIDADES_ALOJAMIENTO;

SELECT *
  FROM ALOJAMIENTO;
  
DELETE FROM alojamiento
  WHERE id_alojamiento > 5;
  
SELECT *
  FROM ACTIVIDAD;

DELETE FROM actividad
  WHERE id_actividad > 5;

UPDATE VISTA_ACTIVIDADES_ALOJAMIENTO
  SET NOMBRE_ALOJAMIENTO = 'TEST'
  WHERE ID_ALOJAMIENTO = 1;
  
-- Falta terminar de meter todos los campos de las 2 (?) tablas aqui.  
INSERT INTO vista_actividades_alojamiento (id_actividad, nombre_alojamiento, nombre_actividad, direccion_social, razon_social, telefono_contacto)
  VALUES(6, 'nombreAloj.', 'nombreActiv.', 'dirSoc', 'razSoc', 'telefono');