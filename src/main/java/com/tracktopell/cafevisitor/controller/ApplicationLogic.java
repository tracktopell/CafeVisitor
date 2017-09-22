/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.controller;

import com.tracktopell.cafevisitor.dao.EntidadInexistenteException;
import com.tracktopell.cafevisitor.dao.PreferenciaDAO;
import com.tracktopell.cafevisitor.dao.PreferenciaDAOFactory;
import com.tracktopell.cafevisitor.model.Preferencia;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;

/**
 *
 * @author Softtek
 */
public class ApplicationLogic {
	private static final String ULR_VERSION_FILE = "http://nonex.ddns.net/tracktopell/cafevisitor/version.properties";
	private static final String ULR_APP_PACKAGE  = "http://nonex.ddns.net/tracktopell/cafevisitor/UPDATE_BUILD.zip";
	private static final String FILE_APP_PACKAGE = "./UPDATE_BUILD.zip";
	
	private static String _version = null;
		
	private static final String VERSION_PROPERTY = "cafevisitor.version";

	private static SimpleDateFormat sdfHoraEntrada = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static List<Preferencia> prefList;

	private static List<Preferencia> getPrefList() {
		if (prefList == null) {
			PreferenciaDAO preferenciaDAO = PreferenciaDAOFactory.getPreferenciaDAO();
			prefList = preferenciaDAO.getAll();
		}
		return prefList;
	}

	public static String getNombreNegocioPref() {
		for (Preferencia p : getPrefList()) {
			if (p.getId().equalsIgnoreCase("NOMBRE_NEGOCIO")) {
				return p.getValor();
			}
		}
		return null;
	}
	
	static void recargarPreferencias(){
		prefList = null;
	}

	public static String getDireccionNegocioPref() {
		for (Preferencia p : getPrefList()) {
			if (p.getId().equalsIgnoreCase("DIRECCION_NEGOCIO")) {
				return p.getValor();
			}
		}
		return null;
	}

	public static String getTelefonosNegocioPref() {
		for (Preferencia p : getPrefList()) {
			if (p.getId().equalsIgnoreCase("TELEFONOS_NEGOCIO")) {
				return p.getValor();
			}
		}
		return null;
	}

	public static String getPasswordAdminPref() {
		for (Preferencia p : getPrefList()) {
			if (p.getId().equalsIgnoreCase("PASSWORD_ADMIN")) {
				return p.getValor();
			}
		}
		return null;
	}

	public static String getEmailNegocioPref() {
		for (Preferencia p : getPrefList()) {
			if (p.getId().equalsIgnoreCase("EMAIL_NEGOCIO")) {
				return p.getValor();
			}
		}
		return null;
	}

	static void setNombreNegocioPref(String v) {
		Preferencia p = new Preferencia("NOMBRE_NEGOCIO", v);
		try {
			PreferenciaDAOFactory.getPreferenciaDAO().edit(p);
		} catch (EntidadInexistenteException e) {
			e.printStackTrace(System.err);
		}
	}

	static void setDireccionNegocioPref(String v) {
		Preferencia p = new Preferencia("DIRECCION_NEGOCIO", v);
		try {
			PreferenciaDAOFactory.getPreferenciaDAO().edit(p);
		} catch (EntidadInexistenteException e) {
			e.printStackTrace(System.err);
		}
	}

	static void setTelefonosNegocioPref(String v) {
		Preferencia p = new Preferencia("TELEFONOS_NEGOCIO", v);
		try {
			PreferenciaDAOFactory.getPreferenciaDAO().edit(p);
		} catch (EntidadInexistenteException e) {
			e.printStackTrace(System.err);
		}
	}

	static void setEmailNegocioPref(String v) {
		Preferencia p = new Preferencia("EMAIL_NEGOCIO", v);
		try {
			PreferenciaDAOFactory.getPreferenciaDAO().edit(p);
		} catch (EntidadInexistenteException e) {
			e.printStackTrace(System.err);
		}
	}
	
	static void setPasswordAdminPref(String v) {
		Preferencia p = new Preferencia("PASSWORD_ADMIN", getMD5Encrypted(v));
		try {
			PreferenciaDAOFactory.getPreferenciaDAO().edit(p);
		} catch (EntidadInexistenteException e) {
			e.printStackTrace(System.err);
		}
	}

	public static String formatHora(Object o) {
		return sdfHoraEntrada.format(o);
	}
		
	private static String getMD5Encrypted(String e) {

		MessageDigest mdEnc = null; // Encryption algorithm
		try {
			mdEnc = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			return null;
		}
		mdEnc.update(e.getBytes(), 0, e.length());
		return (new BigInteger(1, mdEnc.digest())).toString(16);
	}
	public static boolean needsUpdateApplciation(){
		boolean updateApp =  false;
		
		URL url=null;
		InputStream is = null;
		BufferedReader br = null;
		try{
			url = new URL(ULR_VERSION_FILE);
			is = url.openStream();
			br = new BufferedReader(new InputStreamReader(is));
		}catch(IOException ioe){
			ioe.printStackTrace(System.err);
			return false;
		}
		
		String lineRead = null;
		try{
			while((lineRead = br.readLine()) != null) {
				if(lineRead.contains(VERSION_PROPERTY)){
					
					String[] propValue = lineRead.split("=");
					String versionReadOfLine = propValue[1]; 
					
					System.err.println("->needsUpdateApplciation:lineRead="+lineRead+", versionReadOfLine="+versionReadOfLine);
					System.err.println("->needsUpdateApplciation:version ="+getVersion());
					System.err.println("->result ? ="+versionReadOfLine.compareTo(getVersion()));
					
					if(versionReadOfLine.compareTo(getVersion())>0){
						System.err.println("->needsUpdateApplciation: Ok, update!");
						return true;
					}
				}
			}
		} catch(IOException ioe){
		
		}
		
		return updateApp;
	}
	
	static void updateApplication(final UpdateApplicationListener ual) {
		new Thread(){

			@Override
			public void run() {
				downloadApplication(ual);
			}
		}.start();
	}
	
	static void cacellUpdateApplication() {
		keepDownlaod = false;
	}
	
	private static boolean keepDownlaod;
	
	static private void downloadApplication(final UpdateApplicationListener ual) {
		URL url=null;
		BufferedReader br = null;
		InputStream is = null;
		HttpURLConnection conn = null;
		
		try{
			url = new URL(ULR_APP_PACKAGE);
			conn = (HttpURLConnection)url.openConnection();
			int length = conn.getContentLength();
			is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(FILE_APP_PACKAGE);
			byte[] buffer = new byte[1024 * 16];
			int r = -1;
			int t= 0;
			keepDownlaod = true;
			while ((r = is.read(buffer, 0, buffer.length)) != -1) {
				if(!keepDownlaod){
					int resp = JOptionPane.showConfirmDialog(null, "¿Desea cancelar la descarga ?", "Cancelar", 
							JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(resp == JOptionPane.YES_OPTION){
						break;
					} else {
						keepDownlaod = true;
					}
				}
				t += r;
				fos.write(buffer, 0, r);
				fos.flush();
				int advance = (100 * t) / length;
				System.err.print("Downloaded:\t"+advance+" % \r");
				ual.updateProgress(advance);				
			}
			System.err.println("");
			System.err.println("finished");
			is.close();
			fos.close();
			if(!keepDownlaod){
				throw new IllegalStateException("Update Canceled");
			} else {
				extractFolder(FILE_APP_PACKAGE);
				JOptionPane.showMessageDialog(null, "Se ha actualizado la Aplicación, \nReinicie por favor.", 
						"Actualización", JOptionPane.INFORMATION_MESSAGE);
				//System.exit(2);			
			}
		} catch (IOException ex) {
			throw new IllegalStateException("Can't download UPDATE data package:"+ex.getMessage());
		}
		/*
		try {
			extractFolder(FILE_APP_PACKAGE);
			JOptionPane.showMessageDialog(null, "Se ha actualizado la Aplicación, \nPor favor reinicie nuevamente", 
					"Actualización", JOptionPane.INFORMATION_MESSAGE);
			System.exit(2);
		} catch (IOException ex) {
			throw new IllegalStateException("Can't extract & deflate UPDATE data paclkage:"+ex.getMessage());
		}
		*/
	}

	static private void extractFolder(String zipFile) throws ZipException, IOException {
		System.out.println(zipFile);
		int BUFFER = 2048;
		File file = new File(zipFile);

		ZipFile zip = new ZipFile(file);
		String destPathToInflate = ".";

		Enumeration zipFileEntries = zip.entries();

		// Process each entry
		System.err.println("-> extracting :");
		while (zipFileEntries.hasMoreElements()) {
			// grab a zip file entry
			ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
			String currentEntry = entry.getName();
			File destFile = new File(destPathToInflate, currentEntry);
			System.err.println("-> inflating :"+destFile.getPath());
			//destFile = new File(newPath, destFile.getName());
			File destinationParent = destFile.getParentFile();

			// create the parent directory structure if needed
			destinationParent.mkdirs();

			if (!entry.isDirectory()) {
				BufferedInputStream is = new BufferedInputStream(zip
						.getInputStream(entry));
				int currentByte;
				// establish buffer for writing file
				byte data[] = new byte[BUFFER];

				// write the current file to disk
				FileOutputStream fos = new FileOutputStream(destFile);
				BufferedOutputStream dest = new BufferedOutputStream(fos,
						BUFFER);

				// read and write until last byte is encountered
				while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, currentByte);
				}
				dest.flush();
				dest.close();
				is.close();
			}
		}
		System.err.println("-> OK, finish extracting.");
	}

	static boolean canDownlaodUpdateApplication() {
		try {
			URL  url = new URL(ULR_APP_PACKAGE);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			int length = conn.getContentLength();
			if(length > 1024*1024){
				return true;
			} else{
				return false;
			}
		} catch(Exception ex){
			ex.printStackTrace(System.err);
			return false;
		}
	}
	static String getVersion() {
		if(_version == null){
			Properties porpVersion = new Properties();
			try {
				porpVersion.load(ApplicationLogic.class.getResourceAsStream("/version.properties"));
				_version = porpVersion.getProperty("cafevisitor.version");
			} catch (IOException ex) {
				ex.printStackTrace(System.err);
			}
		}
		return _version;
	}

}
