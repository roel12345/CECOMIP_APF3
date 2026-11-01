> **ARCHIVO HISTÓRICO:** describe el proyecto previo basado en archivos TSV. La versión actual utiliza SQL Server; consultar `GUIA_SQL_SERVER.md` y `docs/APF3_INFORME_SQLSERVER.md`.

# Arquitectura APF3 — CECOMIP LTDA

## Diagnóstico del código recibido
- Aplicación Swing de escritorio funcional a nivel de código; persistencia en TSV, no base SQL.
- El modelo (`proyecto.modelo`) y las pantallas (`proyecto.ui`) ya existían.
- La clase `AppContext` concentraba servicios y persistencia: se extrajo **un contrato DAO** y un adaptador para reducir el acoplamiento.
- Se añadió `AutenticacionControlador`, que utiliza `AppContext` y está conectado al formulario `LoginFrame`.
- Se añadieron reglas de cálculo aisladas y pruebas reproducibles.
- Las cuatro librerías figuran en un módulo Maven auxiliar (`src-apf3`); **no sustituyen todavía el exportador original** y su compilación Maven requiere descargar dependencias. No afirmar que todas están integradas en los módulos de pantalla.

## Diagrama de dependencias
```
[LoginFrame (Vista)] --> [AutenticacionControlador (Controlador)]
                             |
                             v
                         [AppContext (Servicios)]
                             |
                             v
                         [DatosDAO (Interfaz SOLID/DIP)]
                             ^
                             |
                      [ArchivoDatosDAO (Adaptador)]
                             |
                             v
                       [RepositorioArchivos / TSV]

[Modelo: Cliente, ProduccionOro, VentaOro, Usuario]
[PruebasAPF3] --> [CalculadoraOro]
[RecursosJavaAPF3 (Maven)] --> [Guava, Commons, POI, Logback]
```

## Principios SOLID
- SRP: controlador de login, cálculo de oro y acceso a datos tienen responsabilidades separadas.
- DIP: `AppContext` depende de `DatosDAO` como contrato; la implementación se instancia todavía en el contexto (se puede mejorar con inyección completa).
- OCP: un futuro `SqlDatosDAO` puede implementar la interfaz sin cambiar sus consumidores.

## TDD
Pruebas automatizadas de cálculos y validaciones incorporadas; **no se puede demostrar que se escribieron antes del código**. Se aporta la base para continuar mediante ciclos rojo-verde-refactor.

## Seguridad y advertencias
- La versión recibida utiliza SHA-256 simple para claves y un usuario demostrativo `admin/admin123`. **No cumple estándares de producción.** Sustituir por Argon2id, scrypt o PBKDF2 con sal única; exigir cambio de clave de demostración.
- No publicar `datos/`, `facturas/`, contraseñas ni archivos personales en GitHub.
- El TSV codificado en Base64 no está cifrado: Base64 no ofrece confidencialidad.
- Validar entradas en todos los formularios; se añadieron validaciones en `CalculadoraOro`.
