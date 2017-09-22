-- ============================= CREACION DEL ESQUEMA DE LA BASE DE DATOS ====================

CREATE TABLE REGISTRO (
	ID				INTEGER			 NOT NULL,
	NOMBRE			VARCHAR (255)	 NOT NULL ,
	GENERO			VARCHAR (1)		 ,
	EDAD_APROXIMADA VARCHAR (5)		 ,
	DESTINO			VARCHAR (255)	 NOT NULL ,
	DEPARTAMENTO	VARCHAR (128)	 NOT NULL ,	
	ENTRADA			TIMESTAMP	 NOT NULL ,	
	SALIDA			TIMESTAMP,	
	FOTO			VARCHAR (255)	 NOT NULL ,
	RESTRINGIDO		INTEGER,
	USUARIO_ID		VARCHAR (32)	 NOT NULL,
	PRIMARY KEY (ID)
);

-- ===============================================================================
CREATE TABLE USUARIO (
	ID				VARCHAR (32)	 NOT NULL,	
	PASSWORD		VARCHAR (32)	 NOT NULL,	
	NOMBRE			VARCHAR (255)	 NOT NULL,
	ES_ADMIN		INTEGER NOT NULL,	
	PRIMARY KEY (ID)
);
-- ===============================================================================
CREATE TABLE PREFERENCIA (
	ID				VARCHAR (32)	 NOT NULL ,
	VALOR			VARCHAR (128)	 NOT NULL,
	PRIMARY KEY (ID)
);

-- =================================== CONSTRAINTS ==============================
ALTER TABLE REGISTRO ADD FOREIGN KEY (USUARIO_ID)	REFERENCES USUARIO(ID);
