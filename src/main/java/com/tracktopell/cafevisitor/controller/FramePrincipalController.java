/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.controller;

import com.tracktopell.cafevisitor.dao.RegistroDAOFactory;
import com.tracktopell.cafevisitor.model.Registro;
import com.tracktopell.cafevisitor.model.RegistroTableModel;
import com.tracktopell.cafevisitor.view.DialogoConfiguracionSistema;
import com.tracktopell.cafevisitor.view.FramePrincipal;
import com.tracktopell.cafevisitor.view.PanelBitacora;
import com.tracktopell.cafevisitor.view.PanelRegistro;
import com.tracktopell.cafevisitor.view.PanelSalida;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Softtek
 */
public class FramePrincipalController implements ActionListener{
	FramePrincipal framePrincipal;
	PanelRegistro  panelRegistro;
	PanelBitacora  panelBitacora;
	
	PanelSalida    panelSalida;
	
	PanelRegistroController panelRegistroController;
	PanelSalidaController   panelSalidaController;
	DialogoConfiguracionSistemaController dialogoConfiguracionSistemaController;
	
	private static FramePrincipalController instance;
	private DialogoConfiguracionSistema dialogoConfiguracionSistema;
	
	private FramePrincipalController(){
	
	}

	public static FramePrincipalController getInstance() {
		if(instance == null){
			instance = new FramePrincipalController();
		}
		return instance;
	}
	
	public void inicializar() {
		this.framePrincipal = new FramePrincipal();
		
		this.panelRegistro  = (PanelRegistro)this.framePrincipal.getPanelRegistro();
		this.panelRegistroController = PanelRegistroController.getInstance();
		this.panelRegistroController.inicializar(panelRegistro);

		this.panelSalida    = (PanelSalida)this.framePrincipal.getPanelSalida();
		this.panelSalidaController = PanelSalidaController.getInstance();
		this.panelSalidaController.inicializar(panelSalida);
		
		this.panelBitacora  = (PanelBitacora)this.framePrincipal.getPanelBitacora();		
		this.panelBitacora.getTablaBitacora().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.panelBitacora.getTablaBitacora().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					mostrarRegistro();
				}
			}
		});

		this.framePrincipal.getRegistrarMenu().addActionListener(this);
		this.framePrincipal.getBitacoraMenu().addActionListener(this);
		this.framePrincipal.getSalidaMenu().addActionListener(this);
		this.framePrincipal.getSalirMenu().addActionListener(this);		
		this.framePrincipal.getPreferenciasMenu().addActionListener(this);
	
		this.dialogoConfiguracionSistema = new DialogoConfiguracionSistema(this.framePrincipal);
		this.dialogoConfiguracionSistemaController = DialogoConfiguracionSistemaController.getInstance();
		this.dialogoConfiguracionSistemaController.inicializar(dialogoConfiguracionSistema);
		
		actualizarTituloPrincipal();
	}
	
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.framePrincipal.getRegistrarMenu()){
			registrarEntrada();
		} else if(e.getSource() == this.framePrincipal.getBitacoraMenu()){
			bitacora();
		} else if(e.getSource() == this.framePrincipal.getSalidaMenu()){
			registrarSalida();
		} else if(e.getSource() == this.framePrincipal.getSalirMenu()){
			salir();
		} else if(e.getSource() == this.framePrincipal.getPreferenciasMenu()){
			preferencias();
		}
	}
	
	void registrarEntrada(){
		((CardLayout)this.framePrincipal.getPanels().getLayout()).show(this.framePrincipal.getPanels(), "panelRegistro");
	}
	
	void bitacora(){
		((CardLayout)this.framePrincipal.getPanels().getLayout()).show(this.framePrincipal.getPanels(), "panelBitacora");
		refrescarBitacora();		
	}
	
	private void refrescarBitacora(){
		
		ArrayList<Registro> rl = (ArrayList<Registro>)RegistroDAOFactory.getRegistroDAO().getAllToday();
		System.out.println("Registro size=" + rl.size());
		
		for(Registro r: rl){
			System.out.println("\t->" + r);
		}
		
		JTable tablaBitacora = this.panelBitacora.getTablaBitacora();
		tablaBitacora.setModel(new RegistroTableModel(rl));
		tablaBitacora.updateUI();
	}
		
	
	
	public void estadoInicial(){
		this.framePrincipal.setVisible(true);
		this.actualizarTituloPrincipal();
		
		this.framePrincipal.setExtendedState(this.framePrincipal.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		this.panelRegistroController.estadoInicial();
		this.panelSalidaController.estadoInicial();
		this.panelRegistroController.startCapturing();		
	}

	private void registrarSalida() {
		((CardLayout)this.framePrincipal.getPanels().getLayout()).show(this.framePrincipal.getPanels(), "panelSalida");
		this.panelSalida.getFolio().requestFocus();
	}

	Registro getSelectedRegistro() {
		int cteSel = panelBitacora.getTablaBitacora().getSelectedRow();
		Registro cs = null;
		if (cteSel != -1) {
			cs = ((RegistroTableModel) panelBitacora.getTablaBitacora().getModel()).getAt(cteSel);
		}
		return cs;
	}

	private void mostrarRegistro(){
		Registro c = getSelectedRegistro();
		registrarSalida();
		panelSalidaController.setRegistroActual(c);
	}

	private void salir() {
		System.exit(0);
	}

	private void preferencias() {
		System.out.println("-> menu preferencias()");
		this.dialogoConfiguracionSistemaController.estadoInicial();
	}

	void actualizarTituloPrincipal() {
		new Thread(){

			@Override
			public void run() {
				framePrincipal.setTitle("CafeVisitor-"+ApplicationLogic.getVersion()+" ["+ApplicationLogic.getNombreNegocioPref()+"]");
			}
		
		}.start();
		
	}
}
