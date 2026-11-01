> **ARCHIVO HISTÓRICO:** describe el proyecto previo basado en archivos TSV. La versión actual utiliza SQL Server; consultar `GUIA_SQL_SERVER.md` y `docs/APF3_INFORME_SQLSERVER.md`.

# CECOMIP LTDA — APF3 / Grupo 12

Proyecto reconstruido a partir del ZIP de semanas 10-12: conserva interfaz Swing y algoritmos del proyecto original.

## Ejecutar en NetBeans (versión Ant, sin dependencias)
Abrir esta carpeta como proyecto NetBeans (contiene `nbproject/project.xml`) y ejecutar `proyecto.Main`.
La primera ejecución crea la carpeta `datos` con datos de demostración. **Credencial DEMO solo para pruebas**: admin / admin123; cambiarla antes de usar datos reales.

## Construir y verificar
`ant clean jar` (proyecto Ant). Para pruebas sin librerías externas:
`mkdir -p build/test-apf3 && javac -cp build/classes -d build/test-apf3 tests/proyecto/PruebasAPF3.java && java -cp build/classes:build/test-apf3 proyecto.PruebasAPF3` (en Windows usar `;` en classpath).

## Librerías APF3
`pom.xml` declara Guava, Apache Commons, Apache POI y Logback. `src-apf3` ofrece un servicio de exportación usando realmente las cuatro; ejecutar `mvn compile` cuando Maven tenga acceso a las dependencias. **Esta ruta Maven no ha sido comprobada en el entorno de entrega; la app Ant sigue siendo la ruta validada.**

## Git/GitHub
1. Inicializar Git en la raíz del proyecto (`git init`).
2. Revisar `.gitignore` para evitar datos, facturas, credenciales y compilados.
3. `git add . && git commit -m "APF3: arquitectura MVC y DAO inicial"`.
4. Crear un repo GitHub privado sin README inicial y vincular (`git remote add origin URL`).
5. `git push -u origin main` tras elegir/renombrar rama (`git branch -M main`).
6. En siguientes cambios: editar, probar, commit y push. **No se incluyó un historial Git ficticio en el ZIP**.

## Entregables
- `docs/APF3_ARQUITECTURA.md`: diseño, SOLID, DAO, seguridad.
- `docs/APF3_INFORME.md`: evaluación contra rúbrica, alcance y pendientes.
- `docs/APF3_GUIA_SUSTENTACION.md`: guion de 10 minutos.
- `docs/javadoc/`: documentación recibida (regenerarla después de cambios).
