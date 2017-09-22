/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.controller;

import com.tracktopell.cafevisitor.view.DialogoConfiguracionSistema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Softtek
 */
public class DialogoConfiguracionSistemaController implements ActionListener{	
	public DialogoConfiguracionSistema dialogoConfiguracionSistema;
	private static DialogoConfiguracionSistemaController instance;

	public static DialogoConfiguracionSistemaController getInstance() {
		if(instance==null){
			instance =  new DialogoConfiguracionSistemaController();
		}
		return instance;
	}
	
	void inicializar(DialogoConfiguracionSistema d){
		this.dialogoConfiguracionSistema = d;
		this.dialogoConfiguracionSistema.getAceptar().addActionListener(this);
		this.dialogoConfiguracionSistema.getCancelar().addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.dialogoConfiguracionSistema.getAceptar()){
			guardarPref();
		} else if(e.getSource() == this.dialogoConfiguracionSistema.getCancelar()){
			cancelar();
		}
	}
	
	void estadoInicial(){
		System.out.println("->estadoInicial()");
		
		this.dialogoConfiguracionSistema.getNombreNegocio().setText(ApplicationLogic.getNombreNegocioPref());
		this.dialogoConfiguracionSistema.getDireccion().setText(ApplicationLogic.getDireccionNegocioPref());
		this.dialogoConfiguracionSistema.getTelefonos().setText(ApplicationLogic.getTelefonosNegocioPref());
		this.dialogoConfiguracionSistema.getEmail().setText(ApplicationLogic.getEmailNegocioPref());
		this.dialogoConfiguracionSistema.getPasswordAdmin().setText(ApplicationLogic.getNombreNegocioPref());		
		
		this.dialogoConfiguracionSistema.setVisible(true);
	}

	private void guardarPref() {
		System.out.println("->guardarPref, guardar en DB");
		
		ApplicationLogic.setNombreNegocioPref(this.dialogoConfiguracionSistema.getNombreNegocio().getText());
		ApplicationLogic.setDireccionNegocioPref(this.dialogoConfiguracionSistema.getDireccion().getText());
		ApplicationLogic.setTelefonosNegocioPref(this.dialogoConfiguracionSistema.getTelefonos().getText());
		ApplicationLogic.setEmailNegocioPref(this.dialogoConfiguracionSistema.getEmail().getText());
		ApplicationLogic.setPasswordAdminPref(new String(this.dialogoConfiguracionSistema.getPasswordAdmin().getPassword()));		
		
		System.out.println("->guardarPref: ok dispose !");
		
		ApplicationLogic.recargarPreferencias();
		
		this.dialogoConfiguracionSistema.setVisible(false);
		FramePrincipalController.getInstance().actualizarTituloPrincipal();
	}

	private void cancelar() {
		this.dialogoConfiguracionSistema.setVisible(false);
	}
		
}
