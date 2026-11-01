# CECOMIP LTDA — APF3 — Proyecto NetBeans + SQL Server

**Abrir `COMENZAR_AQUI_NETBEANS.txt` para importar el código en NetBeans.**

Proyecto Maven con estructura estándar `src/main/java`, formularios Swing editables
cuando NetBeans reconozca los ficheros `.form` y pruebas JUnit en `src/test/java`.

- Punto de entrada: `proyecto.Main`.
- Persistencia por defecto: SQL Server mediante JDBC, `SqlServerDatosDAO`.
- Configuración local no incluida: copiar `config-local.properties.example`.
- Scripts: `database/01_crear_base_datos.sql` y `database/03_permisos_minimos.sql`.
- Bibliotecas declaradas en `pom.xml`: Microsoft JDBC, Guava, Apache POI, Apache Commons,
  Logback y JUnit (Maven las descarga en primer uso).
- Base de datos real, credenciales y commits GitHub deben configurarse aparte.

Para instalación y migración desde TSV: `GUIA_SQL_SERVER.md`.
Para el APF3: `docs/APF3_INFORME_SQLSERVER.md`.

Nota de validación: se comprobará compilación de las clases Java sin dependencias externas;
no se ha podido realizar una compilación Maven integral ni una conexión a SQL Server
porque no hay un servidor SQL disponible en este entorno.
