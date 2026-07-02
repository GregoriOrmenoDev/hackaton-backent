package com.techstore.backend.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.techstore.backend.model.Producto;          // ADAPTAR: importa tu entidad
import com.techstore.backend.repository.ProductoRepository; // ADAPTAR: importa tu repo
import com.techstore.backend.service.ProductoService;  // ADAPTAR: importa tu service interface
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// =====================================================================
// ADAPTAR: Los unicos metodos que cambias son:
//   - exportarExcel()  -> nombres de columnas + campos que se escriben
//   - exportarPDF()    -> titulo del sistema + nombres de columnas
//   - importarDesdeCSV()    -> campos que se leen del CSV
//   - importarDesdeExcel()  -> campos que se leen del Excel
//   - guardar() -> si tu campo unico no es "nombre", cambia la validacion
// =====================================================================
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository; // ADAPTAR: LibroRepository, etc.

    @Override
    public List<Producto> listar() {
        return productoRepository.findByEstadoTrue();
    }

    @Override
    public List<Producto> listarPorEstado(Boolean estado) {
        return estado == null ? productoRepository.findAll() : productoRepository.findByEstado(estado);
    }

    @Override
    public Optional<Producto> buscarPorId(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardar(Producto producto) {
        boolean esCreacion = (producto.getIdProducto() == null); // ADAPTAR: getIdLibro()
        if (esCreacion) {
            // ADAPTAR: Cambia getNombre() por el campo unico de tu entidad
            // Ejemplo: getTitulo(), getCodigo(), getPlaca()
            if (productoRepository.existsByNombreIgnoreCase(producto.getNombre())) {
                throw new RuntimeException("Ya existe un registro con ese nombre.");
            }
            producto.setCreatedAt(LocalDateTime.now());
        } else {
            producto.setUpdatedAt(LocalDateTime.now());
        }
        return productoRepository.save(producto);
    }

    @Override
    public boolean eliminar(Integer id) {
        Optional<Producto> opt = productoRepository.findById(id);
        if (opt.isPresent()) {
            Producto p = opt.get();
            p.setEstado(false);
            p.setDeletedAt(LocalDateTime.now());
            productoRepository.save(p);
            return true;
        }
        return false;
    }

    @Override
    public boolean restaurar(Integer id) {
        Optional<Producto> opt = productoRepository.findById(id);
        if (opt.isPresent()) {
            Producto p = opt.get();
            p.setEstado(true);
            p.setRestoredAt(LocalDateTime.now());
            productoRepository.save(p);
            return true;
        }
        return false;
    }

    // =====================================================================
    // EXPORTAR EXCEL
    // ADAPTAR: Cambia los nombres de columnas y los campos que se escriben
    // =====================================================================
    @Override
    public byte[] exportarExcel(Boolean estado) throws IOException {
        List<Producto> lista = listarPorEstado(estado);

        try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = wb.createSheet("Datos"); // ADAPTAR: "Libros", "Equipos", etc.

            // Estilo cabecera verde
            CellStyle headerStyle = wb.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            org.apache.poi.ss.usermodel.Font hFont = wb.createFont();
            hFont.setColor(IndexedColors.WHITE.getIndex());
            hFont.setBold(true);
            headerStyle.setFont(hFont);

            // ADAPTAR: Cambia los nombres de columnas segun tus campos
            // Ejemplo Libreria: {"ID","Titulo","Autor","Genero","Precio","Stock","Estado"}
            String[] cols = {"ID", "Nombre", "Descripcion", "Categoria", "Precio", "Stock", "Estado"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < cols.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(cols[i]);
                cell.setCellStyle(headerStyle);
            }

            // ADAPTAR: Cambia los getters segun tus campos
            // Ejemplo Libreria: p.getTitulo(), p.getAutor(), p.getGenero()
            int rowNum = 1;
            for (Producto p : lista) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getIdProducto() != null ? p.getIdProducto() : 0);
                row.createCell(1).setCellValue(nvl(p.getNombre()));       // ADAPTAR: getTitulo()
                row.createCell(2).setCellValue(nvl(p.getDescripcion())); // ADAPTAR: getAutor()
                row.createCell(3).setCellValue(nvl(p.getCategoria()));   // ADAPTAR: getGenero()
                row.createCell(4).setCellValue(p.getPrecio() != null ? p.getPrecio().doubleValue() : 0);
                row.createCell(5).setCellValue(p.getStock() != null ? p.getStock() : 0);
                row.createCell(6).setCellValue(Boolean.TRUE.equals(p.getEstado()) ? "Activo" : "Inactivo");
            }

            for (int i = 0; i < cols.length; i++) sheet.autoSizeColumn(i);
            wb.write(out);
            return out.toByteArray();
        }
    }

    // =====================================================================
    // EXPORTAR PDF
    // ADAPTAR: Cambia el titulo y los nombres de columnas
    // =====================================================================
    @Override
    public byte[] exportarPDF(Boolean estado) throws Exception {
        List<Producto> lista = listarPorEstado(estado);

        Document doc = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(doc, out);
        doc.open();

        com.itextpdf.text.Font titleFont =
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new BaseColor(33, 150, 83));

        // ADAPTAR: Cambia el titulo del sistema
        Paragraph title = new Paragraph("Sistema de Gestion - Listado de Productos", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(8f);
        doc.add(title);

        Paragraph fecha = new Paragraph("Generado: " + java.time.LocalDate.now(),
                FontFactory.getFont(FontFactory.HELVETICA, 9));
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setSpacingAfter(12f);
        doc.add(fecha);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1f, 3f, 3f, 2f, 2f, 1.5f, 2f});

        BaseColor verde = new BaseColor(33, 150, 83);
        com.itextpdf.text.Font fh = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.WHITE);

        // ADAPTAR: Cambia los nombres de columnas
        // Ejemplo Libreria: {"ID","Titulo","Autor","Genero","Precio","Stock","Estado"}
        for (String h : new String[]{"ID", "Nombre", "Descripcion", "Categoria", "Precio", "Stock", "Estado"}) {
            PdfPCell cell = new PdfPCell(new Phrase(h, fh));
            cell.setBackgroundColor(verde);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5f);
            table.addCell(cell);
        }

        com.itextpdf.text.Font fd = FontFactory.getFont(FontFactory.HELVETICA, 8);
        boolean alt = false;
        for (Producto p : lista) {
            BaseColor bg = alt ? new BaseColor(240, 248, 244) : BaseColor.WHITE;
            // ADAPTAR: Cambia los getters segun tus campos
            String[] vals = {
                    String.valueOf(p.getIdProducto() != null ? p.getIdProducto() : ""),
                    nvl(p.getNombre()),       // ADAPTAR: getTitulo()
                    nvl(p.getDescripcion()), // ADAPTAR: getAutor()
                    nvl(p.getCategoria()),   // ADAPTAR: getGenero()
                    p.getPrecio() != null ? "S/ " + p.getPrecio() : "-",
                    String.valueOf(p.getStock() != null ? p.getStock() : 0),
                    Boolean.TRUE.equals(p.getEstado()) ? "Activo" : "Inactivo"
            };
            for (String v : vals) {
                PdfPCell cell = new PdfPCell(new Phrase(v, fd));
                cell.setBackgroundColor(bg);
                cell.setPadding(4f);
                table.addCell(cell);
            }
            alt = !alt;
        }

        doc.add(table);
        doc.close();
        return out.toByteArray();
    }

    // =====================================================================
    // IMPORTAR CSV
    // Formato esperado: nombre,descripcion,categoria,precio,stock
    // ADAPTAR: Cambia los setters segun tus campos
    // =====================================================================
    @Override
    public int importarDesdeCSV(MultipartFile archivo) throws IOException {
        int count = 0;
        String[] lines = new String(archivo.getBytes()).split("\n");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isBlank()) continue;
            String[] cols = line.split(",");
            if (cols.length < 3) continue;

            // ADAPTAR: Ajusta los indices segun las columnas de tu CSV
            String campo1 = clean(cols[0]); // ADAPTAR: titulo, marca, etc.
            String campo2 = cols.length > 1 ? clean(cols[1]) : "";  // ADAPTAR: autor, modelo, etc.
            String campo3 = cols.length > 2 ? clean(cols[2]) : "General"; // ADAPTAR: genero, tipo, etc.
            String precio = cols.length > 3 ? clean(cols[3]) : "0";
            String stock  = cols.length > 4 ? clean(cols[4]) : "0";

            if (campo1.isBlank() || productoRepository.existsByNombreIgnoreCase(campo1)) continue; // ADAPTAR validacion

            Producto p = new Producto(); // ADAPTAR: new Libro()
            // ADAPTAR: Cambia los setters segun tus campos
            p.setNombre(campo1);          // ADAPTAR: setTitulo(campo1)
            p.setDescripcion(campo2.isBlank() ? null : campo2); // ADAPTAR: setAutor()
            p.setCategoria(campo3);       // ADAPTAR: setGenero()
            p.setPrecio(new BigDecimal(precio.isEmpty() ? "0" : precio));
            p.setStock(Integer.parseInt(stock.isEmpty() ? "0" : stock));
            p.setEstado(true);
            p.setCreatedAt(LocalDateTime.now());

            productoRepository.save(p);
            count++;
        }
        return count;
    }

    // =====================================================================
    // IMPORTAR EXCEL
    // ADAPTAR: Cambia los indices de columna y setters segun tus campos
    // =====================================================================
    @Override
    public int importarDesdeExcel(MultipartFile archivo) throws IOException {
        List<Producto> nuevos = new ArrayList<>(); // ADAPTAR: List<Libro>

        try (Workbook wb = WorkbookFactory.create(archivo.getInputStream())) {
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // ADAPTAR: Ajusta los indices segun el orden de columnas de tu Excel
                String campo1 = cell(row, 0); // ADAPTAR: col 0 = titulo, marca, etc.
                String campo2 = cell(row, 1); // ADAPTAR: col 1 = autor, modelo, etc.
                String campo3 = cell(row, 2); // ADAPTAR: col 2 = genero, tipo, etc.
                String precio = cell(row, 3);
                String stock  = cell(row, 4);

                if (campo1.isBlank() || productoRepository.existsByNombreIgnoreCase(campo1)) continue;

                Producto p = new Producto(); // ADAPTAR: new Libro()
                // ADAPTAR: Cambia los setters
                p.setNombre(campo1);         // ADAPTAR: setTitulo()
                p.setDescripcion(campo2.isBlank() ? null : campo2); // ADAPTAR: setAutor()
                p.setCategoria(campo3.isBlank() ? "General" : campo3); // ADAPTAR: setGenero()
                p.setPrecio(new BigDecimal(precio.isEmpty() ? "0" : precio));
                p.setStock(Integer.parseInt(stock.isEmpty() ? "0" : stock));
                p.setEstado(true);
                p.setCreatedAt(LocalDateTime.now());
                nuevos.add(p);
            }
        }

        productoRepository.saveAll(nuevos);
        return nuevos.size();
    }

    // ---- Helpers internos — no cambiar ----
    private String cell(Row row, int col) {
        Cell c = row.getCell(col);
        if (c == null) return "";
        return switch (c.getCellType()) {
            case STRING  -> c.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) c.getNumericCellValue());
            case BOOLEAN -> String.valueOf(c.getBooleanCellValue());
            default      -> "";
        };
    }

    private String clean(String s) { return s.replace("\"", "").trim(); }
    private String nvl(String s)   { return s != null ? s : "-"; }
}
