/* CECOMIP LTDA – APF3. Ejecutar en SQL Server Management Studio.
   Los IDs los genera la aplicación Java, por compatibilidad con el proyecto original.
   Este script no borra datos ni crea usuarios con contraseñas predeterminadas. */
IF DB_ID(N'CECOMIP_APF3') IS NULL
    CREATE DATABASE CECOMIP_APF3;
GO
USE CECOMIP_APF3;
GO
IF OBJECT_ID(N'dbo.usuarios', N'U') IS NULL
BEGIN
 CREATE TABLE dbo.usuarios (
  id INT NOT NULL CONSTRAINT PK_usuarios PRIMARY KEY,
  nombre_completo NVARCHAR(200) NOT NULL,
  usuario NVARCHAR(80) NOT NULL CONSTRAINT UQ_usuarios_usuario UNIQUE,
  clave_hash NVARCHAR(300) NOT NULL,
  rol NVARCHAR(40) NOT NULL,
  activo BIT NOT NULL CONSTRAINT DF_usuarios_activo DEFAULT (1)
 );
END;
GO
IF OBJECT_ID(N'dbo.clientes', N'U') IS NULL
BEGIN
 CREATE TABLE dbo.clientes (
  id INT NOT NULL CONSTRAINT PK_clientes PRIMARY KEY,
  documento NVARCHAR(30) NOT NULL CONSTRAINT UQ_clientes_documento UNIQUE,
  nombre_razon_social NVARCHAR(250) NOT NULL,
  telefono NVARCHAR(40) NULL,
  correo NVARCHAR(200) NULL,
  direccion NVARCHAR(300) NULL
 );
END;
GO
IF OBJECT_ID(N'dbo.producciones', N'U') IS NULL
BEGIN
 CREATE TABLE dbo.producciones (
  id INT NOT NULL CONSTRAINT PK_producciones PRIMARY KEY,
  fecha DATE NOT NULL,
  peso_gramos DECIMAL(19,4) NOT NULL CONSTRAINT CK_producciones_peso CHECK (peso_gramos > 0),
  pureza DECIMAL(8,4) NOT NULL CONSTRAINT CK_producciones_pureza CHECK (pureza BETWEEN 0 AND 100),
  turno NVARCHAR(40) NOT NULL,
  observacion NVARCHAR(2000) NULL,
  estado NVARCHAR(30) NOT NULL
 );
END;
GO
IF OBJECT_ID(N'dbo.ventas', N'U') IS NULL
BEGIN
 CREATE TABLE dbo.ventas (
  id INT NOT NULL CONSTRAINT PK_ventas PRIMARY KEY,
  cliente_id INT NOT NULL,
  cliente_nombre NVARCHAR(250) NOT NULL,
  fecha DATE NOT NULL,
  peso_gramos DECIMAL(19,4) NOT NULL CONSTRAINT CK_ventas_peso CHECK (peso_gramos > 0),
  pureza DECIMAL(8,4) NOT NULL CONSTRAINT CK_ventas_pureza CHECK (pureza BETWEEN 0 AND 100),
  precio_gramo DECIMAL(19,4) NOT NULL CONSTRAINT CK_ventas_precio CHECK (precio_gramo >= 0),
  total DECIMAL(20,4) NOT NULL CONSTRAINT CK_ventas_total CHECK (total >= 0),
  ruta_factura NVARCHAR(1000) NULL,
  CONSTRAINT FK_ventas_clientes FOREIGN KEY (cliente_id) REFERENCES dbo.clientes(id)
 );
END;
GO
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name=N'IX_producciones_fecha' AND object_id=OBJECT_ID(N'dbo.producciones'))
 CREATE INDEX IX_producciones_fecha ON dbo.producciones(fecha);
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name=N'IX_ventas_fecha' AND object_id=OBJECT_ID(N'dbo.ventas'))
 CREATE INDEX IX_ventas_fecha ON dbo.ventas(fecha);
GO
SELECT N'Esquema CECOMIP_APF3 listo' AS resultado;
