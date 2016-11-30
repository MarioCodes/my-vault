-- Generado por Oracle SQL Developer Data Modeler 4.0.3.853
--   en:        2016-05-21 12:17:20 CEST
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



CREATE
  TABLE ACTIVIDAD
  (
    ID_ACTIVIDAD    NUMBER (7) NOT NULL ,
    NOMBRE          VARCHAR2 (100) ,
    DESCRIPCION     VARCHAR2 (300) ,
    DIFICULTAD      NUMBER (7) ,
    CAPACIDAD       VARCHAR2 (200) ,
    DIA_REALIZACION VARCHAR2 (200) ,
    HORA_INICIO     VARCHAR2 (100) ,
    HORA_FIN        VARCHAR2 (100) ,
    NOMBRE_GUIA     VARCHAR2 (200) ,
    LOCALIZACION    VARCHAR2 (200) ,
    DIA_SEMANA      VARCHAR2 (100)
  ) ;
ALTER TABLE ACTIVIDAD ADD CONSTRAINT ACTIVIDAD_PK PRIMARY KEY ( ID_ACTIVIDAD )
;

CREATE
  TABLE ALOJAMIENTO
  (
    ID_ALOJAMIENTO    NUMBER (7) NOT NULL ,
    NOMBRE            VARCHAR2 (80) NOT NULL ,
    DIRECCION_SOCIAL  VARCHAR2 (200) NOT NULL ,
    RAZON_SOCIAL      VARCHAR2 (200) ,
    TELEFONO_CONTACTO VARCHAR2 (15) ,
    DESCRIPCION       VARCHAR2 (500) ,
    VALORACION        NUMBER (1) ,
    FECHA_APERTURA    VARCHAR2 (50) ,
    NUM_HABITACIONES  NUMBER (7) ,
    PROVINCIA         VARCHAR2 (100)
  ) ;
ALTER TABLE ALOJAMIENTO ADD CONSTRAINT ALOJAMIENTO_PK PRIMARY KEY (
ID_ALOJAMIENTO ) ;

CREATE
  TABLE HABITACION
  (
    ID_HABITACION              NUMBER (7) NOT NULL ,
    EXTRAS_HABITACION          VARCHAR2 (200) ,
    PRECIO                     NUMBER (7,2) ,
    CUARTO_BA�O                CHAR (1) NOT NULL ,
    TIPO_HABITACION            VARCHAR2 (100) ,
    RESE�AS                    VARCHAR2 (200) ,
    ALOJAMIENTO_ID_ALOJAMIENTO NUMBER (7) NOT NULL ,
    RESERVA_ID_RESERVA         NUMBER (7)
  ) ;
ALTER TABLE HABITACION ADD CONSTRAINT HABITACION_PK PRIMARY KEY ( ID_HABITACION
);

CREATE
  TABLE REALIZA
  (
    ALOJAMIENTO_ID_ALOJAMIENTO NUMBER (7) NOT NULL ,
    ACTIVIDAD_ID_ACTIVIDAD     NUMBER (7) NOT NULL
  ) ;
ALTER TABLE REALIZA ADD CONSTRAINT REALIZA_PK PRIMARY KEY (
ALOJAMIENTO_ID_ALOJAMIENTO, ACTIVIDAD_ID_ACTIVIDAD ) ;

CREATE
  TABLE RESERVA
  (
    ID_RESERVA         NUMBER (7) NOT NULL ,
    NOMBRE             VARCHAR2 (100) NOT NULL ,
    APELLIDOS          VARCHAR2 (200) ,
    CORREO_ELECTRONICO VARCHAR2 (100) ,
    FORMA_PAGO         VARCHAR2 (200) NOT NULL ,
    COMENTARIOS        VARCHAR2 (400) ,
    CARGOS_ADICIONALES VARCHAR2 (500) ,
    TOTAL_RESERVA      NUMBER (7,2)
  ) ;
ALTER TABLE RESERVA ADD CONSTRAINT RESERVA_PK PRIMARY KEY ( ID_RESERVA ) ;

CREATE
  TABLE TRABAJADOR
  (
    ID_PERSONAL                  NUMBER (7) NOT NULL ,
    NOMBRE                       VARCHAR2 (100) NOT NULL ,
    APELLIDOS                    VARCHAR2 (200) ,
    DNI                          VARCHAR2 (20) NOT NULL ,
    DOMICILIO                    VARCHAR2 (150) ,
    FECHA_CONTRATACION           DATE NOT NULL ,
    CARGO                        VARCHAR2 (100) ,
    SALARIO                      NUMBER (7,2) ,
    TELEFONO                     VARCHAR2 (15) ,
    CORREO_ELECTRONICO           VARCHAR2 (100) ,
    SEXO                         VARCHAR2 (10) ,
    NUMERO_SEC_SOCIAL            NUMBER (15) ,
    FECHA_NACIMIENTO             DATE ,
    ALOJAMIENTO_ID_TRABAJA       NUMBER (7) NOT NULL ,
    ALOJAMIENTO_ID_PERS_CONTACTO NUMBER (7)
  ) ;
ALTER TABLE TRABAJADOR ADD CONSTRAINT TRABAJADOR_PK PRIMARY KEY ( ID_PERSONAL )
;

ALTER TABLE REALIZA ADD CONSTRAINT FK_ASS_3 FOREIGN KEY (
ALOJAMIENTO_ID_ALOJAMIENTO ) REFERENCES ALOJAMIENTO ( ID_ALOJAMIENTO ) ;

ALTER TABLE REALIZA ADD CONSTRAINT FK_ASS_4 FOREIGN KEY (
ACTIVIDAD_ID_ACTIVIDAD ) REFERENCES ACTIVIDAD ( ID_ACTIVIDAD ) ;

ALTER TABLE HABITACION ADD CONSTRAINT HABITACION_ALOJAMIENTO_FK FOREIGN KEY (
ALOJAMIENTO_ID_ALOJAMIENTO ) REFERENCES ALOJAMIENTO ( ID_ALOJAMIENTO ) ON DELETE CASCADE;

ALTER TABLE HABITACION ADD CONSTRAINT HABITACION_RESERVA_FK FOREIGN KEY (
RESERVA_ID_RESERVA ) REFERENCES RESERVA ( ID_RESERVA ) ;

ALTER TABLE TRABAJADOR ADD CONSTRAINT TRAB_ALOJ_FK_PERS_CONTACTO
FOREIGN KEY ( ALOJAMIENTO_ID_PERS_CONTACTO ) REFERENCES ALOJAMIENTO (
ID_ALOJAMIENTO ) ;

ALTER TABLE TRABAJADOR ADD CONSTRAINT TRAB_ALOJ_FK_TRABAJA FOREIGN
KEY ( ALOJAMIENTO_ID_TRABAJA ) REFERENCES ALOJAMIENTO ( ID_ALOJAMIENTO ) ;


-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             6
-- CREATE INDEX                             0
-- ALTER TABLE                             12
-- CREATE VIEW                              0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ERRORS                                   2
-- WARNINGS                                 0