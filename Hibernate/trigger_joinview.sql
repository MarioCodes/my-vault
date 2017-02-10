-- Creacion del Trigger.
create or replace TRIGGER TRIGGER_VISTA_HIBERNATE
INSTEAD OF INSERT OR DELETE OR UPDATE ON VISTA_ACTIVIDADES_ALOJAMIENTO
BEGIN
  IF inserting THEN
    INSERT INTO ALOJAMIENTO (id_alojamiento, nombre, descripcion, direccion_social, razon_social, telefono_contacto, valoracion, fecha_apertura, num_habitaciones, provincia) --Estos son los campos de la TABLA BASE.
      VALUES(SEQ_AL_PK.nextval, :new.nombre_alojamiento, :new.descripcion_alojamiento, :new.direccion_social, :new.razon_social, :new.telefono_contacto, :new.valoracion_alojamiento, :new.fecha_apertura,
                :new.numero_habitaciones, :new.provincia); --Y estos los campos de la VISTA. Son las variables que pondra el User.
      
    INSERT INTO ACTIVIDAD(id_actividad, nombre, descripcion, dia_semana, localizacion, dificultad, capacidad, hora_inicio, hora_fin, nombre_guia, dia_realizacion) --Campos TABLA.
      VALUES(:new.id_actividad, :new.nombre_actividad, :new.descripcion_actividad, :new.dia_semana, :new.localizacion, :new.dificultad, :new.capacidad, :new.hora_inicio, :new.hora_fin, :new.nombre_guia, :new.dia_realizacion); --Campos VISTA.
    
    INSERT INTO REALIZA(alojamiento_id_alojamiento, actividad_id_actividad)
      VALUES(SEQ_AL_PK.currval, :new.id_actividad);
    
  ELSIF UPDATING THEN -- Solo dejo Updatear algunos Campos, no todos.
    UPDATE ALOJAMIENTO
      SET NOMBRE = :new.nombre_alojamiento,
          TELEFONO_CONTACTO = :new.telefono_contacto,
          NUM_HABITACIONES = :new.numero_habitaciones
      WHERE ID_ALOJAMIENTO = :new.id_alojamiento;
      
    UPDATE ACTIVIDAD
      SET DIA_SEMANA = :new.dia_semana,
          CAPACIDAD = :new.capacidad,
          HORA_INICIO = :new.hora_inicio,
          HORA_FIN = :new.hora_fin,
          NOMBRE_GUIA = :new.nombre_guia,
          DIA_REALIZACION = :new.dia_realizacion
      WHERE ID_ACTIVIDAD = :new.id_actividad;
  ELSIF deleting THEN
    DELETE FROM REALIZA
      WHERE alojamiento_id_alojamiento = :old.id_alojamiento
      AND actividad_id_actividad = :old.id_actividad;
      
    DELETE FROM ALOJAMIENTO
      WHERE id_alojamiento = :old.id_alojamiento;
      
    DELETE FROM ACTIVIDAD
      WHERE id_actividad = :old.id_actividad;
  END IF;  
END;

-- Testeo del Trigger.
SELECT *
  FROM vista_actividades_alojamiento;
  
INSERT INTO vista_actividades_alojamiento (id_actividad, nombre_alojamiento, nombre_actividad, dificultad, capacidad, direccion_social, razon_social, telefono_contacto, descripcion_alojamiento, descripcion_actividad, dia_realizacion, dia_semana, valoracion_alojamiento, fecha_apertura, numero_habitaciones, provincia, localizacion, hora_inicio, hora_fin, nombre_guia)
  VALUES(11, 'nombreAloj.', 'nombreActiv.', 5, 20, 'dirSoc', 'razSoc', 'telefono', 'descripcion aloha', 'descripcion actividad', '23/11/1993', 'lunes', 5, 'fecha_apertura', 10, 'provincia', 'localizacion', '8:30', '15:00', 'Antonio');
  
UPDATE vista_actividades_alojamiento
  SET numero_habitaciones = 50,
    hora_inicio = '8:10'
  WHERE id_alojamiento = 64; -- Cambiar al id de la secuencia que haya introducido.
  
DELETE FROM vista_actividades_alojamiento
  WHERE id_alojamiento = 64
  AND id_actividad = 11;