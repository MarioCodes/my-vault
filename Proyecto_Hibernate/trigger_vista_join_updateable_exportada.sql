--------------------------------------------------------
-- Archivo creado  - viernes-enero-20-2017   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Trigger TRIGGER_VISTA_HIBERNATE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "DAI4"."TRIGGER_VISTA_HIBERNATE" 
--INSTEAD OF DELETE OR INSERT OR UPDATE ON VISTA_ACTIVIDADES_ALOJAMIENTO
INSTEAD OF INSERT ON VISTA_ACTIVIDADES_ALOJAMIENTO --Cambiarlo mas adelante, de momento intento implementar solo INSERTS.
BEGIN
  IF inserting THEN
    INSERT INTO ALOJAMIENTO (id_alojamiento, nombre, direccion_social, razon_social, telefono_contacto) --Estos son los campos de la TABLA BASE.
      VALUES(SEQ_AL_PK.nextval, :new.nombre_alojamiento, :new.direccion_social, :new.razon_social, :new.telefono_contacto); --Y estos los campos de la VISTA. Son las variables que pondra el User.
      
    INSERT INTO ACTIVIDAD(id_actividad, nombre, descripcion, dia_semana, localizacion, dificultad, capacidad) --Campos TABLA.
      VALUES(:new.id_actividad, :new.nombre_actividad, :new.descripcion_actividad, :new.dia_semana, :new.localizacion, :new.dificultad, :new.capacidad); --Campos VISTA.
    
  --ELSIF UPDATING THEN
    --SYS.DBMS_OUTPUT.PUT('UPDATING');
  --ELSE --Deleting
    --DBMS_OUTPUT.PUT_LINE('DELETING');
  END IF;  
END;
/
ALTER TRIGGER "DAI4"."TRIGGER_VISTA_HIBERNATE" ENABLE;
