-- Borrado limpio para empezar de nuevo.
-- Vaciar las tablas enteras.
DELETE FROM REALIZA;
DELETE FROM HABITACION;
DELETE FROM ACTIVIDAD;
DELETE FROM RESERVA;
DELETE FROM TRABAJADOR;
DELETE FROM ALOJAMIENTO;

-- Borrado de tablas para reescribirlas.
DROP TABLE TRABAJADOR;
DROP TABLE REALIZA;
DROP TABLE ACTIVIDAD;
DROP TABLE HABITACION;
DROP TABLE ALOJAMIENTO;
DROP TABLE RESERVA;

-- Eliminacion de las sequencias empleadas.
DROP SEQUENCE seq_al_pk;
DROP SEQUENCE seq_hab_pk;