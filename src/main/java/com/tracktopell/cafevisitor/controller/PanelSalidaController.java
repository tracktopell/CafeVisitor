/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.controller;

import com.tracktopell.cafevisitor.dao.EntidadInexistenteException;
import com.tracktopell.cafevisitor.dao.RegistroDAOFactory;
import com.tracktopell.cafevisitor.model.Registro;
import com.tracktopell.cafevisitor.view.PanelSalida;
import com.tracktopell.cafevisitor.view.PhotoPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author Softtek
 */
public class PanelSalidaController implements ActionListener {

	PanelSalida panelSalida;
	PhotoPanel photoPanel;
	Registro registroActual;
	private Date horaSalida;
	private boolean clockRunning;

	private static PanelSalidaController instance;

	public static PanelSalidaController getInstance() {
		if (instance == null) {
			instance = new PanelSalidaController();
		}
		return instance;
	}

	void inicializar(PanelSalida panelSalida) {
		this.panelSalida = panelSalida;
		this.photoPanel = panelSalida.getPhotoPanel();

		this.panelSalida.getFolio().addActionListener(this);
		this.panelSalida.getAceptar().addActionListener(this);
		this.panelSalida.getCancelar().addActionListener(this);
		new Thread() {
			@Override
			public void run() {
				long t1 = System.currentTimeMillis();
				long t2 = t1;
				long dt = 0;
				clockRunning = true;
				System.out.println("---------------------> CLOCK RUN");

				while (clockRunning) {
					t1 = System.currentTimeMillis();
					dt = t1 - t2;
					if (dt >= 1000) {
						t2 = t1;
						horaSalida = new Date();
						updateHoraSalida();
					}
				}
			}
		}.start();

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.panelSalida.getFolio()) {
			buscarFolio();
		} else if (e.getSource() == this.panelSalida.getAceptar()) {
			guardarSalida();
		} else if (e.getSource() == this.panelSalida.getCancelar()) {
			cancelar();
		}
	}

	void estadoInicial() {
		registroActual = null;
		
		this.panelSalida.getFolio().setText("");
		this.panelSalida.getEntrada().setText("");
		this.panelSalida.getSalida().setText("");
		this.panelSalida.getSalida().setBackground(this.panelSalida.getEntrada().getBackground());
		this.panelSalida.getSalida().setEnabled(false);
		this.panelSalida.getNombreVisitante().setText("");
		this.panelSalida.getGenero().setText("");
		this.panelSalida.getEdadAproximada().setText("");
		this.panelSalida.getVisitaA().setText("");
		this.panelSalida.getDepartamento().setText("");
		this.panelSalida.getRestringido().setText("");

		this.panelSalida.getFolio().requestFocus();

		this.photoPanel.setWebcamImage(null);
		this.photoPanel.setMostrarIamgen(false);
		
		this.panelSalida.getAceptar().setEnabled(false);
	}

	private void buscarFolio() {
		String textFolio = this.panelSalida.getFolio().getText();
		if (textFolio.length() == 0) {
			return;
		}

		int numFolio = 0;
		try {
			numFolio = Integer.parseInt(textFolio);
		} catch (NumberFormatException nfe) {
			showErrorMessage("FOLIO INVALIDO");
			estadoInicial();
			return;
		}
		System.out.println("->buscarFolio:"+numFolio);
		registroActual = RegistroDAOFactory.getRegistroDAO().getRegistro(numFolio);
		System.out.println("->registroActual:"+registroActual);
		
		if(registroActual != null) {
			mostrarRegistroActual();
		} else{
			showErrorMessage("FOLIO NO EXISTE");			
			estadoInicial();
		}
	}

	private void guardarSalida() {
		registroActual.setSalida(new Timestamp(horaSalida.getTime()));
		try {
			RegistroDAOFactory.getRegistroDAO().edit(registroActual);
			mostrarRegistroActual();
			showErrorMessage("SALIDA REGISTRADA");
		} catch (EntidadInexistenteException ex) {
			ex.printStackTrace(System.err);
		}
	}

	private void cancelar() {
		estadoInicial();
	}

	private void showErrorMessage(final String m) {
		new Thread() {
			@Override
			public void run() {
				try {
					for(int j=0;j<3;j++){
						panelSalida.getMsgBusqueda().setText(m);
						Thread.sleep(750);
						panelSalida.getMsgBusqueda().setText("");
						Thread.sleep(100);
					}
				} catch (InterruptedException ie) {
					panelSalida.getMsgBusqueda().setText("");
				}
			}
		}.start();
	}

	public void setRegistroActual(Registro registroActual) {
		this.registroActual = registroActual;
		mostrarRegistroActual();
	}
	
	

	private void mostrarRegistroActual() {
		this.panelSalida.getFolio().setText(""+registroActual.getId());
		this.panelSalida.getEntrada().setText(ApplicationLogic.formatHora(registroActual.getEntrada()));
		if(registroActual.getSalida() != null){
			this.panelSalida.getSalida().setText(ApplicationLogic.formatHora(registroActual.getSalida()));
			this.panelSalida.getSalida().setBackground(this.panelSalida.getEntrada().getBackground());
			this.panelSalida.getSalida().setEnabled(false);
			this.panelSalida.getAceptar().setEnabled(false);			
		} else {
			this.panelSalida.getSalida().setBackground(Color.RED);
			this.panelSalida.getAceptar().setEnabled(true);
			this.panelSalida.getSalida().setEnabled(true);
		}
		this.panelSalida.getNombreVisitante().setText(registroActual.getNombre());
		this.panelSalida.getGenero().setText(registroActual.getGenero());
		this.panelSalida.getEdadAproximada().setText(registroActual.getEdadAproximada());
		this.panelSalida.getVisitaA().setText(registroActual.getDestino());
		this.panelSalida.getDepartamento().setText(registroActual.getDepartamento());
		this.panelSalida.getRestringido().setText(registroActual.getRestringido()==1?"SI":"NO");

		if(registroActual.getFoto() != null ){
			BufferedImage bi = null;
			try{
				bi = ImageIO.read(new FileInputStream(registroActual.getFoto()));
				System.out.println("->mostrar foto="+registroActual.getFoto()+", bi="+bi);
				this.photoPanel.setMostrarIamgen(true);
				photoPanel.setWebcamImage(bi);				
			} catch(IOException ioe){
				ioe.printStackTrace(System.err);
			}
		} else {
			System.out.println("->mostrar sin foto :( ");
			this.photoPanel.setMostrarIamgen(true);	
			this.photoPanel.setWebcamImage(null);
		}
		
		this.panelSalida.getFolio().requestFocus();
		
		this.panelSalida.getFolio().setSelectionStart(0);
		this.panelSalida.getFolio().setSelectionEnd(this.panelSalida.getFolio().getText().length());		
	}

	private void updateHoraSalida() {
		if (this.panelSalida.isVisible() && (registroActual!=null && registroActual.getSalida()==null)) {
			this.panelSalida.getSalida().setText(ApplicationLogic.formatHora(horaSalida));
		}
	}

}
