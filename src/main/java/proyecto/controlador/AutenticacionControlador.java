package proyecto.controlador;
import proyecto.modelo.Usuario;
import proyecto.servicio.AppContext;
/** Controlador MVC que desacopla la vista de las reglas de autenticación. */
public final class AutenticacionControlador {
 private final AppContext servicio;
 public AutenticacionControlador(AppContext servicio){
  if(servicio==null)throw new IllegalArgumentException("Servicio requerido");
  this.servicio=servicio;
 }
 /** Retorna null para credenciales inválidas, sin propagar contraseñas a logs. */
 public Usuario ingresar(String nombre,char[] clave){
  if(nombre==null||clave==null||nombre.trim().isEmpty()||clave.length==0)return null;
  return servicio.autenticar(nombre,new String(clave));
 }
}
