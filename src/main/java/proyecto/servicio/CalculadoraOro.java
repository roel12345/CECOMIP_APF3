package proyecto.servicio;
/** Reglas de negocio puras y comprobables con pruebas unitarias. */
public final class CalculadoraOro {
 private CalculadoraOro(){}
 public static double gramosFinos(double gramos,double pureza){
  if(!Double.isFinite(gramos)||gramos<=0||!Double.isFinite(pureza)||pureza<=0||pureza>100)
   throw new IllegalArgumentException("Peso y pureza fuera de rango");
  return gramos*pureza/100.0;
 }
 public static double totalVenta(double gramos,double pureza,double precio){
  if(!Double.isFinite(precio)||precio<=0)throw new IllegalArgumentException("Precio inválido");
  return gramosFinos(gramos,pureza)*precio;
 }
}
