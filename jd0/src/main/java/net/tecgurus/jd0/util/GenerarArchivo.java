package net.tecgurus.jd0.util;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.tecgurus.jd0.dto.ClienteDTO;

public class GenerarArchivo {
	
	static{
		Path path = Paths.get("C:/ExcelGenerado");
		if(!Files.exists(path)){
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				System.out.println("ERROR AL GENERAR LA CARPETA DE 'EXCEL GENERADO'");
			}
		}
	}

	public static void excel(List<ClienteDTO> clientes) throws Exception{
		String rutaArchivo = crearRutaArchivo();
		try( XSSFWorkbook workbook = new XSSFWorkbook();FileOutputStream outputStream = new FileOutputStream(rutaArchivo)){
	        XSSFSheet hoja = workbook.createSheet("LISTA DE CLIENTES");
	        int numFila = 0;
	        int columnaInicio = 0;
	        int numColumna = columnaInicio;
	        String[] nombresColumnas = {"ID", "NOMBRE", "A. PATERNO", "A. MATERNO", "EDAD", "ESTADO CIVIL"};
	        
	        XSSFRow filaTitulo = hoja.createRow(numFila);
        	XSSFCell celdaTitulo = filaTitulo.createCell(++numColumna);
        	celdaTitulo.setCellValue("LISTA DE CLIENTES");
        	celdaTitulo.setCellStyle(crearEstilo(workbook, HSSFColor.BLACK.index, HSSFColor.WHITE.index));
        	hoja.addMergedRegion(new CellRangeAddress(0, 0, numColumna, (numColumna+nombresColumnas.length) - 1) );// f_ini, f_fin, c_ini, c_fin
        	
	        XSSFRow filaNombreCol = hoja.createRow(++numFila);
	        numColumna = columnaInicio;
	        for(String nombreCol : nombresColumnas){
	        	XSSFCell celda = filaNombreCol.createCell(++numColumna);
	        	celda.setCellValue(nombreCol);
	        	celda.setCellStyle(crearEstilo(workbook, HSSFColor.DARK_GREEN.index, HSSFColor.WHITE.index));
	        }
	        
	        for(ClienteDTO cliente : clientes){
	        	XSSFRow fila = hoja.createRow(++numFila);
	        	numColumna = 0;
	        	XSSFCell celdaId = fila.createCell(++numColumna);
	        	celdaId.setCellValue(cliente.getId());
	        	
	        	XSSFCell celdaNombre = fila.createCell(++numColumna);
	        	celdaNombre.setCellValue(cliente.getNombre().toUpperCase());
	        	
	        	XSSFCell celdaApaterno = fila.createCell(++numColumna);
	        	celdaApaterno.setCellValue(cliente.getApaterno().toUpperCase());
	        	
	        	XSSFCell celdaAmaterno = fila.createCell(++numColumna);
	        	celdaAmaterno.setCellValue(cliente.getAmaterno().toUpperCase());
	        	
	        	XSSFCell celdaEdad = fila.createCell(++numColumna);
	        	celdaEdad.setCellValue(cliente.getEdad()+" AÑOS");
	        	
	        	XSSFCell celdaEcivil = fila.createCell(++numColumna);
	        	celdaEcivil.setCellValue(cliente.geteCivil().getTipo().toUpperCase());
	        }
	        
	        for(int i = (columnaInicio+1); i <= (columnaInicio+nombresColumnas.length) ; i++){
	        	hoja.autoSizeColumn(i);
	        }
	        
	        workbook.write(outputStream);
	        abrirArchivo();
		}
	}
	
	private static XSSFCellStyle crearEstilo(XSSFWorkbook wb, short colorFondo, short colorTexto){
		
        XSSFCellStyle style = wb.createCellStyle();
   //     style.setFillBackgroundColor(HSSFColor.BLACK.index);
        style.setFillForegroundColor(colorFondo);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        Font font = wb.createFont();
        font.setColor(colorTexto);
        style.setFont(font); 
        
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
	    
	}
	
	private static String crearRutaArchivo(){
		Calendar cal = Calendar.getInstance();
		String nombre = "Clientes";
		nombre += "_"+cal.get(Calendar.DAY_OF_MONTH);
		nombre += "-"+cal.get(Calendar.MONTH)+1;
		nombre += "-"+cal.get(Calendar.YEAR);
		nombre += "_"+cal.get(Calendar.HOUR_OF_DAY);
		nombre += "-"+cal.get(Calendar.MINUTE);
		nombre += "-"+cal.get(Calendar.SECOND);
		return "C:/ExcelGenerado/Clientes_"+nombre+".xlsx";
	}
	
	private static void abrirArchivo(){
		try {
			Desktop.getDesktop().open(new File(crearRutaArchivo()));
		} catch (IOException e) {
			System.out.println("*** ERROR AL ABRIR ARCHIVO EXCEL ***");
		}
	}
	
}
