package com.tracktopell.cafevisitor.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.tracktopell.cafevisitor.dao.EntidadExistenteException;
import com.tracktopell.cafevisitor.dao.RegistroDAOFactory;
import com.tracktopell.cafevisitor.model.Registro;
import com.tracktopell.cafevisitor.ticket.PrintService;
import com.tracktopell.cafevisitor.view.PanelRegistro;
import com.tracktopell.cafevisitor.view.WebcamPhotoPreviewPanel;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 *
 * @author Alfredo Estrada
 */
public class PanelRegistroController implements ActionListener, DocumentListener, WebcamDiscoveryListener, Runnable {

	PanelRegistro panelRegistro;
	WebcamPhotoPreviewPanel wcPPPanel;
	boolean cameraConnected;
	private Date horaEntrada;
	private boolean clockRunning;
	private Document nombreVisitanteDocument;
	private Document visitaADocument;
	private Document departamentoDocument;
	private static PanelRegistroController instance;
	
	private boolean emulationWebCamBeautyFace = false;
	BufferedImage beautyFacePicture = null;
	
	private PanelRegistroController() {
	}

	public static PanelRegistroController getInstance() {
		if (instance == null) {
			instance = new PanelRegistroController();
		}
		return instance;
	}

	void inicializar(PanelRegistro panelRegistro) {
		this.panelRegistro = panelRegistro;

		this.wcPPPanel = this.panelRegistro.getWcPPPanel();

		this.panelRegistro.getFotoBtn().addActionListener(this);
		this.panelRegistro.getCameraBtn().addActionListener(this);
		this.panelRegistro.getAceptar().addActionListener(this);
		this.panelRegistro.getCancelar().addActionListener(this);

		this.nombreVisitanteDocument	= this.panelRegistro.getNombreVisitante().getDocument();
		this.visitaADocument			= this.panelRegistro.getVisitaA().getDocument();
		this.departamentoDocument		= this.panelRegistro.getDepartamento().getDocument();


		this.panelRegistro.getNombreVisitante().getDocument().addDocumentListener(this);
		this.visitaADocument.addDocumentListener(this);
		this.departamentoDocument.addDocumentListener(this);
				
		this.panelRegistro.getNombreVisitante().setText("");
		this.panelRegistro.getVisitaA().setText("");
		this.panelRegistro.getDepartamento().setText("");
		
		if(emulationWebCamBeautyFace){
			try {
				beautyFacePicture = ImageIO.read(PanelRegistroController.class.getResourceAsStream("/images/person.jpg"));
			}catch(IOException ioe){
				ioe.printStackTrace(System.err);
			}
		} else {
			Webcam.addDiscoveryListener(this);
		}
		
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
						horaEntrada = new Date();
						updateHoraEntrada();
					}
				}
			}
		}.start();
	}

	public void run() {
		System.out.println("---------------------> RUN");
		if(emulationWebCamBeautyFace) {
			cameraConnected = true;
			try{
				while(cameraConnected){
					wcPPPanel.setWebcamImage(beautyFacePicture);
					Thread.sleep(100);						
				}
			}catch(InterruptedException ie){
			
			}
		} else {
			Webcam webcam = Webcam.getDefault();
			try {
				if (webcam != null) {
					System.out.println("-> Webcam name:" + webcam.getName() + ", cameraConnected?" + cameraConnected);
					System.out.println("-> Webcam class:" + webcam.getClass());
					webcam.open();
					System.out.println("-> Webcam open");
					cameraConnected = true;

					while (cameraConnected) {

						BufferedImage image = webcam.getImage();
						//System.out.println("-> Image captured");
						if (image != null) {
							wcPPPanel.setWebcamImage(image);
						} else {
						}

						Thread.sleep(100);
						//ImageIO.write(image, "JPG", new File("test.jpg"));
						//System.out.println("-> Image saved");
					}
					System.out.println("-> exit while, camera not connected! ");
					wcPPPanel.setWebcamImage(null);
				} else {
					System.out.println("-> No webcam :( ");
					if (firstTimeListener) {
						firstTimeListener = false;
					}
					wcPPPanel.setWebcamImage(null);
				}

				wcPPPanel.repaint();

			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.panelRegistro.getFotoBtn()) {
			estadoFoto();
		} else if (e.getSource() == this.panelRegistro.getCameraBtn()) {
			estadoCamara();
		} else if (e.getSource() == this.panelRegistro.getAceptar()) {
			guardar();
		} else if (e.getSource() == this.panelRegistro.getCancelar()) {
			cancelar();
		}
	}

	private void estadoFoto() {
		wcPPPanel.takeSnapshot();
	}

	private void estadoCamara() {
		wcPPPanel.cameraMode();
	}

	private void guardar() {
		String pictureFileName = null;
		int folioActual = getFolioActual();
		if(cameraConnected) {
			wcPPPanel.takeSnapshot();
			BufferedImage webcamLastPicture = wcPPPanel.getWebcamLastPicture();
			
			pictureFileName = "./CafeVisitor_Picture_"+folioActual+".jpg";
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(pictureFileName);
				ImageIO.write(webcamLastPicture, "jpeg", fileOutputStream);
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace(System.err);
			} catch (IOException ioe) {
				ioe.printStackTrace(System.err);
			}
		}
		Registro registro = new Registro();
		
		registro.setId(folioActual);
		registro.setNombre(panelRegistro.getNombreVisitante().getText());		
		registro.setGenero(panelRegistro.getGeneroHombre().isSelected()?"H":"M");
		int edadAproxValue = panelRegistro.getEdadAproximada().getValue();
		String edadAproxDisplay =	edadAproxValue == 1?"15-20":
									edadAproxValue == 2?"21-35":
									edadAproxValue == 3?"36-50":
														"+50";
		registro.setEdadAproximada(edadAproxDisplay);
		registro.setDestino(panelRegistro.getVisitaA().getText());
		registro.setDepartamento(panelRegistro.getDepartamento().getText());
		registro.setEntrada(new Timestamp(horaEntrada.getTime()));
		registro.setFoto(pictureFileName);
		registro.setUsuario_id("administrador");
		registro.setRestringido(panelRegistro.getRestringidoSI().isSelected()?1:0);
				
		try {
			RegistroDAOFactory.getRegistroDAO().persist(registro);
			System.out.println("-> OK, guardado");
			
			JOptionPane.showMessageDialog(panelRegistro, "Ok, guardado", "Guardar", JOptionPane.INFORMATION_MESSAGE);
			
			PrintService.print(registro);
			System.out.println("-> PrintService.makePrint(registro); folio="+registro.getId());

			estadoInicial();
		
		} catch (EntidadExistenteException ex) {
			JOptionPane.showMessageDialog(panelRegistro, "Ocurrio un error al guardar:"+ex, "Guardar", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void cancelar() {
		wcPPPanel.cameraMode();
	}
	private boolean firstTimeListener = true;

	public void webcamFound(WebcamDiscoveryEvent wde) {
		System.out.println("-> Webcam found: cameraConnected?" + cameraConnected);

		if (firstTimeListener) {
			firstTimeListener = false;
			System.out.println("-> Webcam found: firstTime, ok bye !");
			return;
		}

		Webcam webcam = Webcam.getDefault();
		if (!webcam.isOpen()) {
			if (!cameraConnected) {
				System.out.println("-> Webcam found:\t launch thread startCapturing()!");
				startCapturing();
			}
		}
	}

	public void webcamGone(WebcamDiscoveryEvent wde) {
		System.out.println("-> Webcam gone :( cameraConnected?" + cameraConnected);
		cameraConnected = false;
	}

	void startCapturing() {
		new Thread(this).start();
	}

	private int getFolioActual() {
		long folio = -1;
		String strFolio = null;

		Date hoy = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyDDD");
		DecimalFormat df = new DecimalFormat("000");
		System.out.println("hoy=" + sdf.format(hoy));

		List<Registro> rl = RegistroDAOFactory.getRegistroDAO().getAllToday();
		System.out.println("Registro size=" + rl.size());
		
		for(Registro r: rl){
			System.out.println("\t->" + r);
		}
		
		folio = rl.size() + 1;

		strFolio = sdf.format(hoy) + df.format(folio);
		int folioReal = Integer.parseInt(strFolio);
		System.out.println("realFolio int=" + folioReal);

		return folioReal;
	}

	public void estadoInicial() {
		Cursor cursorOriginal = this.panelRegistro.getCursor();
		
		this.panelRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		this.wcPPPanel.cameraMode();
		this.panelRegistro.getFolio().setText("" + getFolioActual());
		this.panelRegistro.getGeneroHombre().setSelected(true);
		this.panelRegistro.getRestringidoNO().setSelected(true);
		this.panelRegistro.getEdadAproximada().setValue(2);

		this.panelRegistro.getNombreVisitante().setText("");
		this.panelRegistro.getVisitaA().setText("");
		this.panelRegistro.getDepartamento().setText("");

		this.panelRegistro.getAceptar().setEnabled(false);
		this.panelRegistro.getCancelar().setEnabled(true);
		
		this.panelRegistro.getNombreVisitante().requestFocus();
		
		this.panelRegistro.setCursor(cursorOriginal);
	}

	private void updateHoraEntrada() {
		if (this.panelRegistro.isVisible()) {
			this.panelRegistro.getEntrada().setText(ApplicationLogic.formatHora(horaEntrada));
		}
	}

	public void changedUpdate(DocumentEvent e) {
		validateTextUpdates(e);
	}

	public void removeUpdate(DocumentEvent e) {
		validateTextUpdates(e);
	}

	public void insertUpdate(DocumentEvent e) {
		validateTextUpdates(e);
	}

	void validateTextUpdates(DocumentEvent e) {
		int visitanteLength		= nombreVisitanteDocument.getLength();
		int visitaALength		= visitaADocument.getLength();
		int departamentoLength	= departamentoDocument.getLength();
		
		//System.out.println("->validateTextUpdates: visitanteLength="+visitanteLength+", visitaALength="+visitaALength+", departamentoLength="+departamentoLength);
		if(visitanteLength >= 3 && visitaALength >= 3 && departamentoLength >=1){
			this.panelRegistro.getAceptar().setEnabled(true);
		} else {
			this.panelRegistro.getAceptar().setEnabled(false);
		}
	}
}
