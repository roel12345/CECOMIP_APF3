package proyecto.dao;
import proyecto.estructuras.*;
/** Contrato de acceso a datos; permite sustituir el almacenamiento TSV. */
public interface DatosDAO {
 boolean existenDatos();
 void cargarUsuarios(ArregloUsuarios a);
 void cargarClientes(ListaClientes a);
 void cargarProducciones(ArregloProducciones a);
 void cargarVentas(ArregloVentas a);
 void guardarUsuarios(ArregloUsuarios a);
 void guardarClientes(ListaClientes a);
 void guardarProducciones(ArregloProducciones a);
 void guardarVentas(ArregloVentas a);
 /** Sincroniza todos los módulos; en SQL Server se ejecuta en una transacción. */
 default void guardarTodo(ArregloUsuarios u, ListaClientes c, ArregloProducciones p, ArregloVentas v) {
  guardarUsuarios(u); guardarClientes(c); guardarProducciones(p); guardarVentas(v);
 }
}
