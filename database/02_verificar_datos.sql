USE CECOMIP_APF3;
GO
SELECT 'usuarios' tabla, COUNT(*) registros FROM dbo.usuarios
UNION ALL SELECT 'clientes', COUNT(*) FROM dbo.clientes
UNION ALL SELECT 'producciones', COUNT(*) FROM dbo.producciones
UNION ALL SELECT 'ventas', COUNT(*) FROM dbo.ventas;
GO
SELECT TOP (20) * FROM dbo.clientes ORDER BY id DESC;
SELECT TOP (20) * FROM dbo.producciones ORDER BY id DESC;
SELECT TOP (20) * FROM dbo.ventas ORDER BY id DESC;
GO
