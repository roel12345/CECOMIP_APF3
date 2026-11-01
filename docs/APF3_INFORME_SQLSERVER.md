# Informe técnico APF3 — Integración de SQL Server

## Objetivo
Implementar persistencia SQL Server en el sistema Java CECOMIP para producción y venta de oro, alineada con los requisitos MVC, DAO, SOLID, TDD, seguridad, librerías Java y Git/GitHub del APF3.

## Comparación del sistema antes y después
| Aspecto | Antes | Versión SQL Server |
| --- | --- | --- |
| Persistencia | Cuatro archivos `*.tsv` | Cuatro tablas relacionales SQL Server |
| Datos de usuarios | Archivo local | `dbo.usuarios` (PBKDF2 para claves nuevas) |
| Clientes | Lista enlazada/TSV | `dbo.clientes` (documento único) |
| Producciones | Arreglo/TSV | `dbo.producciones` |
| Ventas | Arreglo/TSV | `dbo.ventas` con FK a cliente |
| DAO | `ArchivoDatosDAO` | `SqlServerDatosDAO` (intercambiable mediante `DatosDAO`) |
| Seguridad | SHA-256 legado | PBKDF2 para nuevas claves y SHA-256 solo para migración |
| Transacciones | Escritura individual de archivos | Guardados SQL con `commit` y `rollback` |
| Inyección SQL | No aplicaba | Consultas JDBC con parámetros `?` |
| Dependencias | Ant + Maven opcional | Maven con driver oficial `mssql-jdbc` |

## Esquema relacional
```text
usuarios (id PK, usuario UNIQUE, clave_hash, rol, activo, ...)
clientes (id PK, documento UNIQUE, nombre_razon_social, ...)
producciones (id PK, fecha, peso_gramos, pureza, turno, estado, ...)
ventas (id PK, cliente_id FK -> clientes.id, fecha, peso_gramos, pureza, precio_gramo, total, ...)
```

## Arquitectura y patrones
```text
[Vista Java Swing] -> [Controlador / AppContext] -> [DatosDAO (interfaz)]
                                                      |
                 +------------------------------------+----------------+
                 |                                                     |
        [SqlServerDatosDAO]                                   [ArchivoDatosDAO]
                 |                                            (legado opcional)
      [SqlServerConexion JDBC]
                 |
         [Microsoft SQL Server]
```
La inyección del `DatosDAO` en `AppContext` evita depender directamente de una clase concreta (principio de inversión de dependencias). No todos los paneles están refactorizados a controladores específicos: MVC es **parcial**, como en el avance anterior.

## Criterios de rúbrica
- **Diseño 3 pts**: evidencia de DAO y separación del servicio; refactor MVC/SOLID aún parcial.
- **Recursos Java 2 pts**: cuatro dependencias declaradas en Maven y clase `RecursosJavaAPF3` con ejemplos reales; integrar su llamada en flujos de interfaz y validar dependencias en máquina conectada.
- **Git/GitHub 3 pts**: archivos preparados para commits; **no existe evidencia real de GitHub hasta que el estudiante haga push**.
- **Interfaces 6 pts**: se conservaron pantallas Swing; evaluar navegación y formularios con SQL Server operativo.
- **Construcción 4 pts**: nuevas tablas, DAO y controles de acceso; pruebas y revisión funcional aún requieren ejecución en Windows con SQL Server.
- **Sustentación 2 pts**: demostrar conexión, CRUD, consulta SQL, arquitectura y commits dentro de los 10 minutos.

## Evidencias recomendadas
1. Captura de las cuatro tablas en SSMS.
2. Java `SqlServerDatosDAO.java` mostrando `PreparedStatement`, transacción y relación de clientes/ventas.
3. Captura de NetBeans Maven con las dependencias y sus formularios.
4. Registrar un cliente; consultar `SELECT * FROM dbo.clientes` y mostrarlo en SSMS.
5. Registrar una producción y venta; verificar sus tablas y reportes de la interfaz.
6. Resultado de `mvn test` y prueba opcional de conectividad SQL Server.
7. Historial de commits y repositorio GitHub.

## Alcance y límites
El documento del curso indica 30% de desarrollo y 40% de entregas de avance (página 1) y la rúbrica mide el alcance comprometido. No es posible asegurar 20/20 con solo agregar SQL Server. Esta versión tampoco pretende garantizar integridad de inventarios bajo concurrencia: usa arreglos y IDs locales del proyecto original. Para producción real, migrar a identificadores generados en SQL Server, operaciones CRUD granulares y transacciones de inventario coherentes entre producción y ventas.

## Referencias técnicas
- Consigna/rúbrica APF3 proporcionada por el usuario, semanas 12, pp. 1–7.
- Microsoft, JDBC Driver para SQL Server: https://learn.microsoft.com/en-us/sql/connect/jdbc/
- Microsoft, Express: https://www.microsoft.com/en-us/download/details.aspx?id=108836
- Apache Maven: https://maven.apache.org/
