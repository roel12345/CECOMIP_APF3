> **ARCHIVO HISTÓRICO:** describe el proyecto previo basado en archivos TSV. La versión actual utiliza SQL Server; consultar `GUIA_SQL_SERVER.md` y `docs/APF3_INFORME_SQLSERVER.md`.

# INFORME APF3 — CECOMIP LTDA / Grupo 12

## Objetivo
Avance de solución informática Java para registrar producción, clientes, ventas y reportes de oro; versión académica de escritorio.

## Funcionalidades existentes en el ZIP original
Inicio de sesión, gestión de usuarios, clientes, producción, cola de validación, ventas con comprobantes, dashboard, estructuras de datos, reportes y exportaciones PDF/XLSX. Su presencia en el código no equivale a una prueba manual exhaustiva.

## Cambios de esta reestructuración
1. DAO: contrato `DatosDAO` + implementación `ArchivoDatosDAO`, empleado por `AppContext`.
2. MVC: `AutenticacionControlador` usado por `LoginFrame`.
3. Reglas: `CalculadoraOro` con excepciones ante números inválidos.
4. Pruebas: `PruebasAPF3` con casos nominales y de error.
5. Librerías: dependencia Maven y demostración de integración de Guava, Apache Commons, Apache POI y Logback en módulo adicional.
6. Protección de entrega: `.gitignore`, exclusión de datos TSV y facturas.

## Matriz de cumplimiento
| Criterio | Evidencia | Estado |
|---|---|---|
| Diseño MVC, DAO, SOLID | controlador, contrato DAO, diagrama | Parcial: otros paneles usan directamente AppContext |
| TDD | cinco comprobaciones automáticas | Parcial: pruebas añadidas, no historial rojo/verde |
| Librerías Java | pom.xml y servicio RecursosJavaAPF3 | Parcial: no usadas en el flujo original de UI; validar compilación Maven |
| Git/GitHub | guía y .gitignore | Pendiente: crear commits reales y subir a GitHub |
| UX/UI | paneles Swing existentes | Requiere demostración presencial y pruebas funcionales |
| Producto final | proyecto Ant compilable; registro y reportes | Validar por módulo, con casos de prueba; no se afirma 100% |
| Sustentación | guía de exposición | Pendiente: ensayo y evidencia de participación |

## Alcance y porcentajes
La consigna menciona 30% de funcionalidades y 40% de entregas de avance, sin lista total cuantificada. Preparar matriz de requerimientos (total vs implementados) con el docente antes de declarar porcentaje exacto.

## Limitaciones que deben declararse
- Datos simulados, no adecuados para operación real.
- Claves DEMO y algoritmo de hash inadecuados para producción.
- Persistencia TSV, no implementación de base de datos relacional.
- Integración Maven adicional pendiente de validación en un entorno conectado.
- Sin Git remoto ni commits generados automáticamente.

## Referencias
- Consigna APF3 proporcionada por el estudiante, semana 12 (págs. 1-7).
- Documentación técnica propia del proyecto entregado y Javadoc.
- Documentación de Git, Java SE, Apache Maven y bibliotecas Java (consultar sus sitios oficiales para la bibliografía final).
