/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor;

import com.tracktopell.cafevisitor.controller.ApplicationLogic;
import com.tracktopell.cafevisitor.controller.FramePrincipalController;
import com.tracktopell.cafevisitor.controller.UpadateApplicationJFrameControl;
import com.tracktopell.cafevisitor.dao.jdbc.DataSourceAdaptor;
import com.tracktopell.cafevisitor.view.UpadateApplicationJFrame;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Softtek
 */
public class Main {
	
	public static void main(String[] args) {
		isSingleInstanceRunning();
		
		if( ApplicationLogic.needsUpdateApplciation()) {
			UpadateApplicationJFrame uaf = new UpadateApplicationJFrame();
			UpadateApplicationJFrameControl uafc = new UpadateApplicationJFrameControl(uaf);
			uafc.estadoInicial();
		}
		
		FramePrincipalController fpc = FramePrincipalController.getInstance();
		fpc.inicializar();
		fpc.estadoInicial();		
	}
	private static void isSingleInstanceRunning(){
		Connection conn = null;
		try{
			conn = DataSourceAdaptor.getConnection();			
		} catch(Exception ex){
			ex.printStackTrace(System.err);
		} finally {
			if(conn == null) {
				System.err.println("-->> Another instance is running !");
				
				JOptionPane.showMessageDialog(null, "Ya esta corriendo la Aplicación en otra ventana", "Iniciar", JOptionPane.ERROR_MESSAGE);
				
				System.exit(1);
			}
		}
	}
	
}
