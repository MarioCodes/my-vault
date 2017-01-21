create or replace TRIGGER TRIGGER_VISTA_HIBERNATE
--INSTEAD OF DELETE OR INSERT OR UPDATE ON VISTA_ACTIVIDADES_ALOJAMIENTO
INSTEAD OF INSERT ON VISTA_ACTIVIDADES_ALOJAMIENTO --Cambiarlo mas adelante, de momento intento implementar solo INSERTS.
BEGIN
  IF inserting THEN
    INSERT INTO ALOJAMIENTO (id_alojamiento, nombre, descripcion, direccion_social, razon_social, telefono_contacto, valoracion, fecha_apertura, num_habitaciones, provincia) --Estos son los campos de la TABLA BASE.
      VALUES(SEQ_AL_PK.nextval, :new.nombre_alojamiento, :new.descripcion_alojamiento, :new.direccion_social, :new.razon_social, :new.telefono_contacto, :new.valoracion_alojamiento, :new.fecha_apertura,
                :new.numero_habitaciones, :new.provincia); --Y estos los campos de la VISTA. Son las variables que pondra el User.
      
    INSERT INTO ACTIVIDAD(id_actividad, nombre, descripcion, dia_semana, localizacion, dificultad, capacidad, hora_inicio, hora_fin, nombre_guia, dia_realizacion) --Campos TABLA.
      VALUES(:new.id_actividad, :new.nombre_actividad, :new.descripcion_actividad, :new.dia_semana, :new.localizacion, :new.dificultad, :new.capacidad, :new.hora_inicio, :new.hora_fin, :new.nombre_guia, :new.dia_realizacion); --Campos VISTA.
    
    INSERT INTO REALIZA(alojamiento_id_alojamiento, actividad_id_actividad)
      VALUES(SEQ_AL_PK.currval, :new.id_actividad);
    
  --ELSIF UPDATING THEN
    --SYS.DBMS_OUTPUT.PUT('UPDATING');
  --ELSE --Deleting
    --DBMS_OUTPUT.PUT_LINE('DELETING');
  END IF;  
END;