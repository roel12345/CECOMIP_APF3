package proyecto.integraciones;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
/** Uso real de cuatro recursos requeridos en la rúbrica, dentro del perfil Maven APF3. */
public final class RecursosJavaAPF3 {
 private static final Logger LOG=LoggerFactory.getLogger(RecursosJavaAPF3.class);
 private RecursosJavaAPF3(){}
 public static void exportarResumen(OutputStream destino,String titulo,double oroFino) throws IOException {
  Preconditions.checkNotNull(destino,"Salida requerida"); // Guava
  String encabezado=StringUtils.defaultIfBlank(titulo,"Resumen CECOMIP"); // Commons
  Preconditions.checkArgument(Double.isFinite(oroFino)&&oroFino>=0,"Valor inválido");
  try(Workbook libro=new XSSFWorkbook()) { // Apache POI
   Sheet hoja=libro.createSheet("Producción");
   Row a=hoja.createRow(0);a.createCell(0).setCellValue(encabezado);
   Row b=hoja.createRow(1);b.createCell(0).setCellValue("Oro fino (g)");b.createCell(1).setCellValue(oroFino);
   hoja.autoSizeColumn(0);libro.write(destino);
  }
  LOG.info("Reporte exportado correctamente"); // SLF4J + Logback
 }
}
