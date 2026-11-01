/*
 * Proyecto: Sistema de Control de Producción y Venta de Oro - CECOMIP LTDA
 * Curso: Estructura de Datos | Grupo 12
 *
 * Código fuente documentado con fines académicos.
 * Los comentarios describen responsabilidades, parámetros, resultados y
 * algoritmos sin modificar la lógica funcional del proyecto original.
 */
package proyecto.servicio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import proyecto.modelo.ReporteAnual;

/**
 * Exporta el reporte anual sin bibliotecas externas.
 * El archivo Excel se genera en formato XLSX (Office Open XML) y el PDF se
 * crea como un documento PDF estándar de una página en orientación horizontal.
 */
public final class ExportadorReportes {
    private static final String[] MESES = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    private static final Charset PDF_CHARSET = Charset.forName("windows-1252");

    /**
     * Inicializa una nueva instancia de exportador reportes.
     */
    private ExportadorReportes() {}

    /**
     * Permite seleccionar una ubicación y exporta el reporte actual a Excel.
     *
     * @param reporte reporte que se desea exportar
     * @param destino archivo o estructura de destino
     * @throws IOException si ocurre un error al leer o escribir archivos
     */
    public static void exportarExcel(ReporteAnual reporte, File destino) throws IOException {
        /**
         * Ejecuta la operación validar extension.
         */
        validarExtension(destino, ".xlsx");
        File padre = destino.getAbsoluteFile().getParentFile();
        if (padre != null && !padre.exists() && !padre.mkdirs()) {
            /**
             * Ejecuta la operación ioexception.
             *
             * @param destino." valor de destino."
             * @return resultado de la operación
             */
            throw new IOException("No se pudo crear la carpeta de destino.");
        }

        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(destino))) {
            agregarEntrada(zip, "[Content_Types].xml", contenidoTipos());
            agregarEntrada(zip, "_rels/.rels", relacionesRaiz());
            agregarEntrada(zip, "docProps/app.xml", propiedadesAplicacion());
            agregarEntrada(zip, "docProps/core.xml", propiedadesDocumento(reporte));
            agregarEntrada(zip, "xl/workbook.xml", libro());
            agregarEntrada(zip, "xl/_rels/workbook.xml.rels", relacionesLibro());
            agregarEntrada(zip, "xl/styles.xml", estilos());
            agregarEntrada(zip, "xl/worksheets/sheet1.xml", hoja(reporte));
        }
    }

    /**
     * Permite seleccionar una ubicación y exporta el reporte actual a PDF.
     *
     * @param reporte reporte que se desea exportar
     * @param destino archivo o estructura de destino
     * @throws IOException si ocurre un error al leer o escribir archivos
     */
    public static void exportarPdf(ReporteAnual reporte, File destino) throws IOException {
        /**
         * Ejecuta la operación validar extension.
         */
        validarExtension(destino, ".pdf");
        File padre = destino.getAbsoluteFile().getParentFile();
        if (padre != null && !padre.exists() && !padre.mkdirs()) {
            /**
             * Ejecuta la operación ioexception.
             *
             * @param destino." valor de destino."
             * @return resultado de la operación
             */
            throw new IOException("No se pudo crear la carpeta de destino.");
        }

        String contenido = construirContenidoPdf(reporte);
        byte[] stream = contenido.getBytes(PDF_CHARSET);
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        /**
         * Ejecuta la operación escribir.
         */
        escribir(salida, "%PDF-1.4\n%âãÏÓ\n");

        List<Integer> offsets = new ArrayList<Integer>();
        offsets.add(0); // Objeto cero reservado por la especificación PDF.
        /**
         * Ejecuta la operación escribir objeto.
         *
         * @param >>" valor de >>"
         */
        escribirObjeto(salida, offsets, 1, "<< /Type /Catalog /Pages 2 0 R >>");
        /**
         * Ejecuta la operación escribir objeto.
         *
         * @param >>" valor de >>"
         */
        escribirObjeto(salida, offsets, 2, "<< /Type /Pages /Kids [3 0 R] /Count 1 >>");
        escribirObjeto(salida, offsets, 3,
                "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 842 595] "
                + "/Resources << /Font << /F1 5 0 R /F2 6 0 R >> >> /Contents 4 0 R >>");

        offsets.add(salida.size());
        /**
         * Ejecuta la operación escribir.
         *
         * @param >>\nstream\n" valor de >>\nstream\n"
         */
        escribir(salida, "4 0 obj\n<< /Length " + stream.length + " >>\nstream\n");
        salida.write(stream);
        /**
         * Ejecuta la operación escribir.
         */
        escribir(salida, "\nendstream\nendobj\n");

        escribirObjeto(salida, offsets, 5,
                "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica /Encoding /WinAnsiEncoding >>");
        escribirObjeto(salida, offsets, 6,
                "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold /Encoding /WinAnsiEncoding >>");

        int inicioXref = salida.size();
        /**
         * Ejecuta la operación escribir.
         *
         * @param 7\n" valor de 7\n"
         */
        escribir(salida, "xref\n0 7\n");
        /**
         * Ejecuta la operación escribir.
         *
         * @param \n" valor de \n"
         */
        escribir(salida, "0000000000 65535 f \n");
        for (int i = 1; i <= 6; i++) {
            escribir(salida, String.format(Locale.US, "%010d 00000 n \n", offsets.get(i)));
        }
        escribir(salida, "trailer\n<< /Size 7 /Root 1 0 R >>\nstartxref\n"
                + inicioXref + "\n%%EOF\n");

        try (OutputStream archivo = new FileOutputStream(destino)) {
            salida.writeTo(archivo);
        }
    }

    /**
     * Verifica que el archivo de destino tenga la extensión esperada.
     *
     * @param archivo archivo que se desea validar o procesar
     * @param extension extensión esperada sin punto inicial
     */
    private static void validarExtension(File archivo, String extension) {
        if (archivo == null || !archivo.getName().toLowerCase(Locale.ROOT).endsWith(extension)) {
            /**
             * Ejecuta la operación illegal argument exception.
             *
             * @param extension valor de extension
             * @return resultado de la operación
             */
            throw new IllegalArgumentException("El archivo debe tener la extensión " + extension);
        }
    }

    /**
     * Escribe un objeto PDF y registra su desplazamiento para la tabla xref.
     *
     * @param salida flujo de salida del archivo
     * @param offsets lista de desplazamientos de objetos PDF
     * @param numero número o identificador que se desea procesar
     * @param cuerpo contenido del objeto PDF
     * @throws IOException si ocurre un error al leer o escribir archivos
     */
    private static void escribirObjeto(ByteArrayOutputStream salida, List<Integer> offsets,
            int numero, String cuerpo) throws IOException {
        offsets.add(salida.size());
        /**
         * Ejecuta la operación escribir.
         *
         * @param "\nendobj\n" valor de "\nendobj\n"
         */
        escribir(salida, numero + " 0 obj\n" + cuerpo + "\nendobj\n");
    }

    /**
     * Escribe texto usando codificación ISO-8859-1 en el flujo PDF.
     *
     * @param salida flujo de salida del archivo
     * @param texto texto que se desea procesar
     * @throws IOException si ocurre un error al leer o escribir archivos
     */
    private static void escribir(ByteArrayOutputStream salida, String texto) throws IOException {
        salida.write(texto.getBytes(PDF_CHARSET));
    }

    /**
     * Construye las instrucciones gráficas y de texto de la página PDF.
     *
     * @param r reporte anual utilizado para construir el contenido
     * @return texto resultante
     */
    private static String construirContenidoPdf(ReporteAnual r) {
        StringBuilder pdf = new StringBuilder();
        // Título y subtítulo.
        /**
         * Ejecuta la operación texto pdf.
         *
         * @param LTDA" valor de ltda"
         */
        textoPdf(pdf, 40, 555, 18, true, "CECOMIP LTDA");
        textoPdf(pdf, 40, 533, 13, true, "REPORTE ANUAL DE PRODUCCIÓN Y VENTAS DE ORO - " + r.getAnio());
        textoPdf(pdf, 655, 555, 9, false, "Generado: "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        // Resumen anual.
        /**
         * Ejecuta la operación texto pdf.
         *
         * @param producción:" valor de producción:"
         */
        textoPdf(pdf, 40, 498, 10, true, "Día con mayor producción:");
        textoPdf(pdf, 190, 498, 10, false, r.getFechaMayorProduccion() == null
                ? "Sin datos" : r.getFechaMayorProduccion() + " - " + numero(r.getMayorProduccionDiaria()) + " g");
        /**
         * Ejecuta la operación texto pdf.
         *
         * @param producción:" valor de producción:"
         */
        textoPdf(pdf, 40, 480, 10, true, "Mes con mayor producción:");
        textoPdf(pdf, 190, 480, 10, false, r.getIndiceMesMayorProduccion() < 0
                ? "Sin datos" : MESES[r.getIndiceMesMayorProduccion()] + " - "
                + numero(r.getProduccionMes(r.getIndiceMesMayorProduccion())) + " g");
        /**
         * Ejecuta la operación texto pdf.
         *
         * @param anual:" valor de anual:"
         */
        textoPdf(pdf, 430, 498, 10, true, "Producción anual:");
        textoPdf(pdf, 540, 498, 10, false, numero(r.getTotalProduccion()) + " g");
        /**
         * Ejecuta la operación texto pdf.
         *
         * @param vendido:" valor de vendido:"
         */
        textoPdf(pdf, 430, 480, 10, true, "Oro vendido:");
        textoPdf(pdf, 540, 480, 10, false, numero(r.getTotalVendido()) + " g");
        /**
         * Ejecuta la operación texto pdf.
         */
        textoPdf(pdf, 650, 498, 10, true, "Saldo:");
        textoPdf(pdf, 700, 498, 10, false, numero(r.getSaldoAnual()) + " g");
        /**
         * Ejecuta la operación texto pdf.
         */
        textoPdf(pdf, 650, 480, 10, true, "Ingresos:");
        textoPdf(pdf, 700, 480, 10, false, "S/ " + numero(r.getTotalIngresos()));

        // Tabla mensual.
        float x = 36;
        float ySuperior = 445;
        float alto = 23;
        float[] anchos = {112, 150, 140, 130, 170};
        String[] cabeceras = {"Mes", "Producción fina (g)", "Oro vendido (g)", "Saldo (g)", "Ingresos (S/)"};

        pdf.append("0.161 0.663 0.882 rg\n");
        pdf.append(formato(x)).append(' ').append(formato(ySuperior - alto)).append(' ')
                .append(formato(suma(anchos))).append(' ').append(formato(alto)).append(" re f\n");
        pdf.append("0 0 0 RG 0.6 w\n");

        float actualX = x;
        for (int c = 0; c < anchos.length; c++) {
            pdf.append(formato(actualX)).append(' ').append(formato(ySuperior - alto)).append(' ')
                    .append(formato(anchos[c])).append(' ').append(formato(alto)).append(" re S\n");
            /**
             * Ejecuta la operación texto pdf.
             *
             * @param 5 valor de 5
             * @param 15 valor de 15
             */
            textoPdf(pdf, actualX + 5, ySuperior - 15, 9, true, cabeceras[c], true);
            actualX += anchos[c];
        }

        for (int mes = 0; mes < 12; mes++) {
            float y = ySuperior - alto * (mes + 2);
            actualX = x;
            String[] valores = {
                MESES[mes],
                numero(r.getProduccionMes(mes)),
                numero(r.getVendidoMes(mes)),
                numero(r.getSaldoMes(mes)),
                numero(r.getIngresosMes(mes))
            };
            for (int c = 0; c < anchos.length; c++) {
                pdf.append(formato(actualX)).append(' ').append(formato(y)).append(' ')
                        .append(formato(anchos[c])).append(' ').append(formato(alto)).append(" re S\n");
                /**
                 * Ejecuta la operación texto pdf.
                 *
                 * @param 5 valor de 5
                 * @param 8 valor de 8
                 */
                textoPdf(pdf, actualX + 5, y + 8, 9, false, valores[c]);
                actualX += anchos[c];
            }
        }

        /**
         * Ejecuta la operación texto pdf.
         */
        textoPdf(pdf, 36, 62, 9, true, "Totales:");
        textoPdf(pdf, 148, 62, 9, true, numero(r.getTotalProduccion()));
        textoPdf(pdf, 298, 62, 9, true, numero(r.getTotalVendido()));
        textoPdf(pdf, 438, 62, 9, true, numero(r.getSaldoAnual()));
        textoPdf(pdf, 568, 62, 9, true, "S/ " + numero(r.getTotalIngresos()));
        textoPdf(pdf, 36, 30, 8, false,
                "03_EDPROYECTO_GRUPO_12 - Sistema de control de producción y venta de oro");
        return pdf.toString();
    }

    /**
     * Agrega una instrucción de texto al contenido PDF.
     *
     * @param pdf valor de pdf
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @param tamano tamaño de fuente
     * @param negrita indica si se utiliza una fuente en negrita
     * @param texto texto que se desea procesar
     */
    private static void textoPdf(StringBuilder pdf, float x, float y, int tamano,
            boolean negrita, String texto) {
        /**
         * Ejecuta la operación texto pdf.
         */
        textoPdf(pdf, x, y, tamano, negrita, texto, false);
    }

    /**
     * Agrega una instrucción de texto al contenido PDF.
     *
     * @param pdf valor de pdf
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @param tamano tamaño de fuente
     * @param negrita indica si se utiliza una fuente en negrita
     * @param texto texto que se desea procesar
     * @param blanco indica si el texto se dibuja en color blanco
     */
    private static void textoPdf(StringBuilder pdf, float x, float y, int tamano,
            boolean negrita, String texto, boolean blanco) {
        pdf.append(blanco ? "1 1 1 rg\n" : "0.12 0.15 0.23 rg\n");
        pdf.append("BT /").append(negrita ? "F2" : "F1").append(' ')
                .append(tamano).append(" Tf ").append(formato(x)).append(' ')
                .append(formato(y)).append(" Td (").append(escaparPdf(texto)).append(") Tj ET\n");
    }

    /**
     * Escapa caracteres especiales utilizados por la sintaxis PDF.
     *
     * @param texto texto que se desea procesar
     * @return texto resultante
     */
    private static String escaparPdf(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }

    /**
     * Suma todos los valores del arreglo recibido.
     *
     * @param valores valores que se mostrarán
     * @return resultado numérico
     */
    private static float suma(float[] valores) {
        float total = 0;
        for (float valor : valores) total += valor;
        return total;
    }

    /**
     * Formatea un número decimal para el contenido gráfico.
     *
     * @param valor valor que se desea procesar
     * @return texto resultante
     */
    private static String formato(float valor) {
        return String.format(Locale.US, "%.2f", valor);
    }

    /**
     * Formatea valores numéricos con dos decimales.
     *
     * @param valor valor que se desea procesar
     * @return texto resultante
     */
    private static String numero(double valor) {
        return String.format(Locale.US, "%,.2f", valor);
    }

    /**
     * Agrega un archivo de texto dentro del contenedor ZIP de Excel.
     *
     * @param zip flujo ZIP del archivo Excel
     * @param nombre nombre o identificador lógico
     * @param contenido contenido textual de la entrada
     * @throws IOException si ocurre un error al leer o escribir archivos
     */
    private static void agregarEntrada(ZipOutputStream zip, String nombre, String contenido) throws IOException {
        zip.putNextEntry(new ZipEntry(nombre));
        Writer escritor = new OutputStreamWriter(zip, "UTF-8");
        escritor.write(contenido);
        escritor.flush();
        zip.closeEntry();
    }

    /**
     * Construye la declaración de tipos de contenido del archivo XLSX.
     *
     * @return texto resultante
     */
    private static String contenidoTipos() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">"
                + "<Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/>"
                + "<Default Extension=\"xml\" ContentType=\"application/xml\"/>"
                + "<Override PartName=\"/xl/workbook.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml\"/>"
                + "<Override PartName=\"/xl/worksheets/sheet1.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\"/>"
                + "<Override PartName=\"/xl/styles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml\"/>"
                + "<Override PartName=\"/docProps/core.xml\" ContentType=\"application/vnd.openxmlformats-package.core-properties+xml\"/>"
                + "<Override PartName=\"/docProps/app.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.extended-properties+xml\"/>"
                + "</Types>";
    }

    /**
     * Construye las relaciones principales del paquete XLSX.
     *
     * @return texto resultante
     */
    private static String relacionesRaiz() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"xl/workbook.xml\"/>"
                + "<Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties\" Target=\"docProps/core.xml\"/>"
                + "<Relationship Id=\"rId3\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties\" Target=\"docProps/app.xml\"/>"
                + "</Relationships>";
    }

    /**
     * Construye las propiedades de aplicación del libro Excel.
     *
     * @return texto resultante
     */
    private static String propiedadesAplicacion() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Properties xmlns=\"http://schemas.openxmlformats.org/officeDocument/2006/extended-properties\" "
                + "xmlns:vt=\"http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes\">"
                + "<Application>03_EDPROYECTO_GRUPO_12</Application><Company>CECOMIP LTDA</Company>"
                + "</Properties>";
    }

    /**
     * Construye los metadatos principales del documento Excel.
     *
     * @param reporte reporte que se desea exportar
     * @return texto resultante
     */
    private static String propiedadesDocumento(ReporteAnual reporte) {
        String instante = java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<cp:coreProperties xmlns:cp=\"http://schemas.openxmlformats.org/package/2006/metadata/core-properties\" "
                + "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" "
                + "xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<dc:title>Reporte anual de producción y ventas " + reporte.getAnio() + "</dc:title>"
                + "<dc:creator>CECOMIP LTDA</dc:creator>"
                + "<dcterms:created xsi:type=\"dcterms:W3CDTF\">" + instante + "</dcterms:created>"
                + "</cp:coreProperties>";
    }

    /**
     * Construye la definición XML del libro de trabajo.
     *
     * @return texto resultante
     */
    private static String libro() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<workbook xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\" "
                + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">"
                + "<sheets><sheet name=\"Reporte anual\" sheetId=\"1\" r:id=\"rId1\"/></sheets>"
                + "</workbook>";
    }

    /**
     * Construye las relaciones internas del libro de trabajo.
     *
     * @return texto resultante
     */
    private static String relacionesLibro() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\" Target=\"worksheets/sheet1.xml\"/>"
                + "<Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles\" Target=\"styles.xml\"/>"
                + "</Relationships>";
    }

    /**
     * Construye los estilos, fuentes, bordes y formatos utilizados en Excel.
     *
     * @return texto resultante
     */
    private static String estilos() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<styleSheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">"
                + "<numFmts count=\"1\"><numFmt numFmtId=\"164\" formatCode=\"&quot;S/ &quot;#,##0.00\"/></numFmts>"
                + "<fonts count=\"3\">"
                + "<font><sz val=\"11\"/><name val=\"Calibri\"/></font>"
                + "<font><b/><color rgb=\"FFFFFFFF\"/><sz val=\"11\"/><name val=\"Calibri\"/></font>"
                + "<font><b/><color rgb=\"FF182249\"/><sz val=\"16\"/><name val=\"Calibri\"/></font>"
                + "</fonts>"
                + "<fills count=\"3\"><fill><patternFill patternType=\"none\"/></fill>"
                + "<fill><patternFill patternType=\"gray125\"/></fill>"
                + "<fill><patternFill patternType=\"solid\"><fgColor rgb=\"FF29A9E1\"/><bgColor indexed=\"64\"/></patternFill></fill></fills>"
                + "<borders count=\"2\"><border><left/><right/><top/><bottom/><diagonal/></border>"
                + "<border><left style=\"thin\"><color rgb=\"FFDCE1EA\"/></left>"
                + "<right style=\"thin\"><color rgb=\"FFDCE1EA\"/></right>"
                + "<top style=\"thin\"><color rgb=\"FFDCE1EA\"/></top>"
                + "<bottom style=\"thin\"><color rgb=\"FFDCE1EA\"/></bottom><diagonal/></border></borders>"
                + "<cellStyleXfs count=\"1\"><xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"0\"/></cellStyleXfs>"
                + "<cellXfs count=\"6\">"
                + "<xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"0\" xfId=\"0\"/>"
                + "<xf numFmtId=\"0\" fontId=\"2\" fillId=\"0\" borderId=\"0\" xfId=\"0\" applyAlignment=\"1\"><alignment horizontal=\"center\"/></xf>"
                + "<xf numFmtId=\"0\" fontId=\"1\" fillId=\"2\" borderId=\"1\" xfId=\"0\" applyAlignment=\"1\"><alignment horizontal=\"center\" vertical=\"center\"/></xf>"
                + "<xf numFmtId=\"4\" fontId=\"0\" fillId=\"0\" borderId=\"1\" xfId=\"0\" applyNumberFormat=\"1\"/>"
                + "<xf numFmtId=\"164\" fontId=\"0\" fillId=\"0\" borderId=\"1\" xfId=\"0\" applyNumberFormat=\"1\"/>"
                + "<xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"1\" xfId=\"0\"/>"
                + "</cellXfs><cellStyles count=\"1\"><cellStyle name=\"Normal\" xfId=\"0\" builtinId=\"0\"/></cellStyles>"
                + "</styleSheet>";
    }

    /**
     * Construye el XML de la hoja con el contenido del reporte.
     *
     * @param r reporte anual utilizado para construir el contenido
     * @return texto resultante
     */
    private static String hoja(ReporteAnual r) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .append("<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">")
                .append("<dimension ref=\"A1:E21\"/><sheetViews><sheetView workbookViewId=\"0\">")
                .append("<pane ySplit=\"8\" topLeftCell=\"A9\" activePane=\"bottomLeft\" state=\"frozen\"/>")
                .append("</sheetView></sheetViews>")
                .append("<cols><col min=\"1\" max=\"1\" width=\"18\" customWidth=\"1\"/>")
                .append("<col min=\"2\" max=\"5\" width=\"22\" customWidth=\"1\"/></cols><sheetData>");

        fila(xml, 1, celdaTexto("A1", "CECOMIP LTDA - REPORTE ANUAL DE PRODUCCIÓN Y VENTAS DE ORO " + r.getAnio(), 1));
        fila(xml, 3, celdaTexto("A3", "Día con mayor producción", 2)
                + celdaTexto("B3", r.getFechaMayorProduccion() == null ? "Sin datos"
                        : r.getFechaMayorProduccion() + " - " + numero(r.getMayorProduccionDiaria()) + " g", 5));
        fila(xml, 4, celdaTexto("A4", "Mes con mayor producción", 2)
                + celdaTexto("B4", r.getIndiceMesMayorProduccion() < 0 ? "Sin datos"
                        : MESES[r.getIndiceMesMayorProduccion()] + " - "
                        + numero(r.getProduccionMes(r.getIndiceMesMayorProduccion())) + " g", 5));
        fila(xml, 5, celdaTexto("A5", "Producción anual (g)", 2) + celdaNumero("B5", r.getTotalProduccion(), 3)
                + celdaTexto("D5", "Oro vendido (g)", 2) + celdaNumero("E5", r.getTotalVendido(), 3));
        fila(xml, 6, celdaTexto("A6", "Saldo anual (g)", 2) + celdaNumero("B6", r.getSaldoAnual(), 3)
                + celdaTexto("D6", "Ingresos anuales", 2) + celdaNumero("E6", r.getTotalIngresos(), 4));

        String[] cabeceras = {"Mes", "Producción fina (g)", "Oro vendido (g)", "Saldo mensual (g)", "Ingresos (S/)"};
        StringBuilder filaCabecera = new StringBuilder();
        for (int i = 0; i < cabeceras.length; i++) filaCabecera.append(celdaTexto(letra(i) + "8", cabeceras[i], 2));
        fila(xml, 8, filaCabecera.toString(), 24);

        for (int mes = 0; mes < 12; mes++) {
            int numeroFila = 9 + mes;
            fila(xml, numeroFila,
                    celdaTexto("A" + numeroFila, MESES[mes], 5)
                    + celdaNumero("B" + numeroFila, r.getProduccionMes(mes), 3)
                    + celdaNumero("C" + numeroFila, r.getVendidoMes(mes), 3)
                    + celdaNumero("D" + numeroFila, r.getSaldoMes(mes), 3)
                    + celdaNumero("E" + numeroFila, r.getIngresosMes(mes), 4));
        }

        fila(xml, 21,
                celdaTexto("A21", "TOTALES", 2)
                + celdaNumero("B21", r.getTotalProduccion(), 3)
                + celdaNumero("C21", r.getTotalVendido(), 3)
                + celdaNumero("D21", r.getSaldoAnual(), 3)
                + celdaNumero("E21", r.getTotalIngresos(), 4));

        xml.append("</sheetData><autoFilter ref=\"A8:E20\"/><mergeCells count=\"1\"><mergeCell ref=\"A1:E1\"/></mergeCells>")
                .append("<pageMargins left=\"0.3\" right=\"0.3\" top=\"0.5\" bottom=\"0.5\" header=\"0.2\" footer=\"0.2\"/>")
                .append("<pageSetup orientation=\"landscape\" paperSize=\"9\" fitToWidth=\"1\" fitToHeight=\"1\"/>")
                .append("</worksheet>");
        return xml.toString();
    }

    /**
     * Agrega una fila XML a la hoja de cálculo.
     *
     * @param xml valor de xml
     * @param numero número o identificador que se desea procesar
     * @param celdas valor de celdas
     */
    private static void fila(StringBuilder xml, int numero, String celdas) {
        /**
         * Ejecuta la operación fila.
         */
        fila(xml, numero, celdas, -1);
    }

    /**
     * Agrega una fila XML a la hoja de cálculo.
     *
     * @param xml valor de xml
     * @param numero número o identificador que se desea procesar
     * @param celdas valor de celdas
     * @param alto valor de alto
     */
    private static void fila(StringBuilder xml, int numero, String celdas, int alto) {
        xml.append("<row r=\"").append(numero).append("\"");
        if (alto > 0) xml.append(" ht=\"").append(alto).append("\" customHeight=\"1\"");
        xml.append('>').append(celdas).append("</row>");
    }

    /**
     * Construye una celda de texto para la hoja de cálculo.
     *
     * @param referencia valor de referencia
     * @param valor valor que se desea procesar
     * @param estilo valor de estilo
     * @return texto resultante
     */
    private static String celdaTexto(String referencia, String valor, int estilo) {
        return "<c r=\"" + referencia + "\" s=\"" + estilo
                + "\" t=\"inlineStr\"><is><t>" + escaparXml(valor) + "</t></is></c>";
    }

    /**
     * Construye una celda numérica para la hoja de cálculo.
     *
     * @param referencia valor de referencia
     * @param valor valor que se desea procesar
     * @param estilo valor de estilo
     * @return texto resultante
     */
    private static String celdaNumero(String referencia, double valor, int estilo) {
        return "<c r=\"" + referencia + "\" s=\"" + estilo + "\"><v>"
                + String.format(Locale.US, "%.6f", valor) + "</v></c>";
    }

    /**
     * Convierte un índice de columna en su referencia alfabética de Excel.
     *
     * @param indice posición mutable utilizada durante el recorrido recursivo
     * @return texto resultante
     */
    private static String letra(int indice) {
        return String.valueOf((char) ('A' + indice));
    }

    /**
     * Escapa caracteres especiales para producir XML válido.
     *
     * @param texto texto que se desea procesar
     * @return texto resultante
     */
    private static String escaparXml(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&apos;");
    }
}
