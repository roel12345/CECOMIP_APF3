package proyecto.dao;
import proyecto.estructuras.*;
import proyecto.servicio.RepositorioArchivos;
/** Adaptador DAO sobre el repositorio de archivos legado. */
public final class ArchivoDatosDAO implements DatosDAO {
 private final RepositorioArchivos repositorio=new RepositorioArchivos();
 public boolean existenDatos(){return repositorio.existenDatos();}
 public void cargarUsuarios(ArregloUsuarios a){repositorio.cargarUsuarios(a);}
 public void cargarClientes(ListaClientes a){repositorio.cargarClientes(a);}
 public void cargarProducciones(ArregloProducciones a){repositorio.cargarProducciones(a);}
 public void cargarVentas(ArregloVentas a){repositorio.cargarVentas(a);}
 public void guardarUsuarios(ArregloUsuarios a){repositorio.guardarUsuarios(a);}
 public void guardarClientes(ListaClientes a){repositorio.guardarClientes(a);}
 public void guardarProducciones(ArregloProducciones a){repositorio.guardarProducciones(a);}
 public void guardarVentas(ArregloVentas a){repositorio.guardarVentas(a);}
}
