package com.tracktopell.cafevisitor.ticket;

import com.tracktopell.cafevisitor.controller.ApplicationLogic;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {
		System.out.println("Test JasperReports");

		String reportPath;
		String compiledReportPath;
		
		reportPath			= "/reports/TicketDeRegistro.jrxml";
		compiledReportPath	= "/reports/TicketDeRegistro.jasper";
			
		InputStream inputStream = null;
		
		JRDataSource dataSource = null;
		System.out.println("Ok, JRDataSource created");
            
		try{
			
			inputStream = App.class.getResourceAsStream(reportPath);
			//inputStream = new FileInputStream(reportPath);
			
			/*                  Caso de Datasource Vacio               */
			dataSource = new JREmptyDataSource();
			
			/*
			           Caso de leer un result set y llenar un mapa, 
					   para crear un JRMapCollectionDataSource. 
					   
			Collection<Map<String,?>> colData = new ArrayList<Map<String,?>>();
            for(int numReg=0;numReg<10;numReg++){
				Map<String,Object> regMap = new HashMap<String, Object>();
				
				regMap.put("campo1", "campo_1_reg"+numReg);
				regMap.put("campo2", "campo_2_reg"+numReg);
				regMap.put("campo3", "campo_1_reg"+numReg);
				
				colData.add(regMap);
			}
			dataSource = new JRMapCollectionDataSource(colData);
			*/
			
			Map parameters = new HashMap();
            Image img = null;
			
			img = ImageIO.read(App.class.getResourceAsStream("/images/person.jpg"));
			
			parameters.put("nombreNegocio","NONEX, S.A. de C.V.");
			parameters.put("direccionNegocio","Av. Tacuba, Tacuba, Mexico D.F. 45678".toUpperCase());
			parameters.put("telefonosNegocio","55-6789-3456, 55-6678-9089");
			//parameters.put("nombre" ,"Fernada Valentina Linares de los Monteros");
			parameters.put("nombre" ,"Beatriz Fernandez Valderrama".toUpperCase());
			parameters.put("destino","Luis Valle Gomez".toUpperCase());
			parameters.put("oficina","Piso #6".toUpperCase());
			parameters.put("folio","1212321232");
			parameters.put("entrada", ApplicationLogic.formatHora(new Date()));
			parameters.put("imageVisitante",img );
            
			
			JasperReport jasperReport = null;
			/* Caso 1) cargamos el jrxml y lo compilamnos : lento */
			
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);			
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			
			/* Caso 2) cargamos el reporte ya compilado (.jasper) mas rapido
			
			InputStream compiledReportStream = App.class.getResourceAsStream(compiledReportPath);
			jasperReport = (JasperReport) JRLoader.loadObject(compiledReportStream);
			*/
			
            
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			/*   Salida a un flujo en memoria de bytes , este, se peude 
			     regresar o enviar por la salida de un servlet
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			
			System.out.println("Ok, JasperExportManager executed");
			
			byte[] pdfBytes = null;

			pdfBytes = baos.toByteArray();
			
			*/
			
			
			
			/*   Salida a directa a un archivo en el filesystem  */
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			
			String nombreArchivoPDFSalida = "./TicketDeRegistro_"+sdf.format(new Date())+".pdf";
			
			FileOutputStream fosSalida = new FileOutputStream(nombreArchivoPDFSalida);
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, fosSalida);
			
		}catch(Exception ex){
			ex.printStackTrace(System.err);
		}
	}
}
