> **ARCHIVO HISTÓRICO:** describe el proyecto previo basado en archivos TSV. La versión actual utiliza SQL Server; consultar `GUIA_SQL_SERVER.md` y `docs/APF3_INFORME_SQLSERVER.md`.

# 03_EDPROYECTO_GRUPO_12

Sistema de escritorio en Java Swing para el control de producción y venta de oro de **CECOMIP LTDA**.

## Credenciales iniciales
- Usuario: `admin`
- Contraseña: `admin123`

## Cómo abrir en NetBeans
1. Descomprimir la carpeta.
2. En NetBeans seleccionar **Archivo > Abrir proyecto**.
3. Elegir la carpeta `03_EDPROYECTO_GRUPO_12`.
4. Ejecutar el proyecto.

## Módulos
- Login sin opción de registro público. Los usuarios se crean únicamente desde el módulo **Usuarios** dentro del sistema.
- Dashboard.
- Clientes (lista enlazada).
- Producción de oro y cola de validación.
- Ventas de oro (arreglo de objetos).
- Carga de factura PDF/JPG/PNG.
- Reportes diarios, mensuales y anuales (matriz bidimensional).
- Exportación del reporte anual en **PDF** y **Excel (.xlsx)**, sin librerías externas.
- Pila de operaciones.
- Árbol binario de búsqueda de ventas.

## Fórmula de venta
`Total = Peso en gramos × (Pureza / 100) × Precio por gramo de oro fino`

Los archivos de datos se guardan en la carpeta `datos` y las facturas en `facturas`.


## Estilo visual actualizado
- Botones **Registrar** en morado con texto blanco.
- Botones **Modificar** en naranja con texto blanco.
- Botones **Eliminar** en rojo con texto blanco.
- Encabezados de las tablas en celeste con texto blanco.
- Botones del panel lateral de administración en celeste con texto blanco.

## Corrección visual del menú lateral

Se corrigió el pintado de los botones del panel de administración para que el color celeste se muestre correctamente incluso cuando Java utiliza el Look & Feel de Windows o Nimbus. Los botones ahora usan fondo celeste sólido y letras blancas.

## Corrección de encabezados de tablas

Todos los encabezados de las tablas del sistema utilizan un renderer propio para evitar que el estilo visual de Windows o Nimbus oculte los títulos. Los nombres de las columnas ahora aparecen centrados, en letra negrita y con contraste visible en los módulos de Clientes, Producción de oro, Ventas y facturas, Reportes y Usuarios.


## Última actualización
- Se actualizó el nombre visible del software a **CECOMIP LTDA** en el login y el panel principal.
- El módulo Reportes recalcula automáticamente la información según el año seleccionado.
- Se agregaron los botones **Exportar PDF** y **Exportar Excel**.
- Los archivos exportados incluyen el día y mes con mayor producción, totales anuales y el detalle de los 12 meses.
