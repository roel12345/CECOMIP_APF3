/* Ejecutar como administrador DESPUÉS de crear en SSMS el login SQL
   y usuario de base llamado cecomip_app. No colocar ninguna clave aquí. */
USE CECOMIP_APF3;
GO
IF DATABASE_PRINCIPAL_ID(N'cecomip_rol_app') IS NULL
    CREATE ROLE cecomip_rol_app;
GO
GRANT SELECT, INSERT, UPDATE, DELETE ON dbo.usuarios TO cecomip_rol_app;
GRANT SELECT, INSERT, UPDATE, DELETE ON dbo.clientes TO cecomip_rol_app;
GRANT SELECT, INSERT, UPDATE, DELETE ON dbo.producciones TO cecomip_rol_app;
GRANT SELECT, INSERT, UPDATE, DELETE ON dbo.ventas TO cecomip_rol_app;
GO
IF DATABASE_PRINCIPAL_ID(N'cecomip_app') IS NOT NULL
    ALTER ROLE cecomip_rol_app ADD MEMBER cecomip_app;
ELSE
    PRINT 'Falta crear el usuario cecomip_app en CECOMIP_APF3 desde User Mapping en SSMS';
GO
