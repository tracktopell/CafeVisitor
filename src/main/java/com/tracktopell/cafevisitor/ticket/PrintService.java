/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.ticket;
import com.tracktopell.cafevisitor.controller.ApplicationLogic;
import com.tracktopell.cafevisitor.model.Registro;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Softtek
 */
public class PrintService {
	private static JasperReport jasperReport = null;
			
	public static void print(final Registro r){
		
		new Thread(){

			@Override
			public void run() {
				makePrint(r);
			}			
		}.start();	
	}

	private static void makePrint(Registro r) {		
		String reportPath;
		String compiledReportPath;
		
		reportPath			= "/reports/TicketDeRegistro.jrxml";
		compiledReportPath	= "/reports/TicketDeRegistro.jasper";
			
		InputStream inputStream = null;
		
		JRDataSource dataSource = null;
		System.out.println("--->>>makePrint:");
            
		try{
			
			inputStream = App.class.getResourceAsStream(reportPath);
			
			dataSource = new JREmptyDataSource();
			
			Map parameters = new HashMap();
            Image img = null;
			
			if(r.getFoto() != null) {
				img = ImageIO.read(new FileInputStream(r.getFoto()));
				System.out.println("-> Foto cargada de :"+r.getFoto()+", como BufferedImage:"+img);
			}
			
			parameters.put("nombreNegocio", ApplicationLogic.getNombreNegocioPref());
			parameters.put("direccionNegocio",ApplicationLogic.getDireccionNegocioPref());
			parameters.put("telefonosNegocio",ApplicationLogic.getTelefonosNegocioPref());
			
			parameters.put("nombre" ,r.getNombre().toUpperCase());
			parameters.put("destino",r.getDestino().toUpperCase());
			if(r.getRestringido()==1){
				parameters.put("oficina","[ "+r.getDepartamento().toUpperCase()+" ]");
			} else {
				parameters.put("oficina",r.getDepartamento().toUpperCase());
			}
			
			parameters.put("folio",""+r.getId());
			parameters.put("entrada",ApplicationLogic.formatHora(r.getEntrada()));
			parameters.put("imageVisitante",img );            
			
			if(jasperReport == null) {
				/* Caso 1) cargamos el jrxml y lo compilamnos : lento */
				JasperDesign jasperDesign = JRXmlLoader.load(inputStream);			
				jasperReport = JasperCompileManager.compileReport(jasperDesign);
				System.out.println("-> JasperReport compilado por 1ra vez :"+jasperReport);
				/* Caso 2) cargamos el reporte ya compilado (.jasper) mas rapido
				InputStream compiledReportStream = App.class.getResourceAsStream(compiledReportPath);
				jasperReport = (JasperReport) JRLoader.loadObject(compiledReportStream);
				*/
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);            
			System.out.println("-> JasperPrint generado:"+jasperPrint);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			/*   Salida a directa a un archivo en el filesystem  */
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			
			String nombreArchivoPDFSalida = "./TicketDeRegistro_"+sdf.format(new Date())+".pdf";
			
			FileOutputStream fosSalida = new FileOutputStream(nombreArchivoPDFSalida);
			
			respaldarPDF(jasperPrint, fosSalida, nombreArchivoPDFSalida);
			
			JasperPrintManager.printReport(jasperPrint, false);
			System.out.println("-> enviado a impresora por default!");
			
		} catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Hubo un error al imprimir:"+ex.getLocalizedMessage(), "Imprimir", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace(System.err);
		}
	
	}

	private static void respaldarPDF(final JasperPrint jasperPrint, final FileOutputStream fosSalida, final String nombreArchivoPDFSalida) throws JRException {
		new Thread(){

			@Override
			public void run() {
				try{
					System.out.println("-> Generaremos el PDF");
					JasperExportManager.exportReportToPdfStream(jasperPrint, fosSalida);
					System.out.println("-> guardando en PDF:"+nombreArchivoPDFSalida);
				}catch(Exception e){
					System.out.println("-> error al guardaren PDF:");
					e.printStackTrace(System.err);
				}
			}
		
		}.start();		
	}
}
