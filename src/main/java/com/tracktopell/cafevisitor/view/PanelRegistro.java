/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author Softtek
 */
public class PanelRegistro extends javax.swing.JPanel {

	/**
	 * Creates new form PanelRegistro
	 */
	public PanelRegistro() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        generoGrp = new javax.swing.ButtonGroup();
        restringidoGrp = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        folio = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        entrada = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nombreVisitante = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        generoHombre = new javax.swing.JRadioButton();
        generoMujer = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        edadAproximada = new javax.swing.JSlider();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        visitaA = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        departamento = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        restringidoNO = new javax.swing.JRadioButton();
        restringidoSI = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        wcPPPanel = new WebcamPhotoPreviewPanel();
        jPanel14 = new javax.swing.JPanel();
        fotoBtn = new javax.swing.JButton();
        cameraBtn = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        aceptar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(8, 1));

        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("FOLIO:");
        jLabel12.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel15.add(jLabel12);

        folio.setEditable(false);
        folio.setColumns(8);
        folio.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel15.add(folio);

        jPanel1.add(jPanel15);

        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("FECHA/HORA ENTRADA:");
        jLabel7.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel13.add(jLabel7);

        entrada.setEditable(false);
        entrada.setColumns(15);
        entrada.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel13.add(entrada);

        jPanel1.add(jPanel13);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("NOMBRE VISITANTE :");
        jLabel1.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel4.add(jLabel1);

        nombreVisitante.setColumns(20);
        nombreVisitante.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel4.add(nombreVisitante);

        jPanel1.add(jPanel4);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("GÉNERO :");
        jLabel5.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel6.add(jLabel5);

        generoGrp.add(generoHombre);
        generoHombre.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        generoHombre.setText("HOMBRE");
        jPanel6.add(generoHombre);

        generoGrp.add(generoMujer);
        generoMujer.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        generoMujer.setText("MUJER");
        jPanel6.add(generoMujer);

        jPanel1.add(jPanel6);

        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("EDAD APROXIMADA :");
        jLabel6.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel8.add(jLabel6);

        jPanel7.setLayout(new java.awt.BorderLayout());

        edadAproximada.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        edadAproximada.setMajorTickSpacing(1);
        edadAproximada.setMaximum(4);
        edadAproximada.setMinimum(1);
        edadAproximada.setPaintTicks(true);
        edadAproximada.setSnapToTicks(true);
        edadAproximada.setValue(1);
        edadAproximada.setPreferredSize(new java.awt.Dimension(400, 31));
        jPanel7.add(edadAproximada, java.awt.BorderLayout.CENTER);

        jPanel9.setLayout(new java.awt.GridLayout(1, 4));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setText("15-20");
        jPanel9.add(jLabel9);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("21-35");
        jPanel9.add(jLabel10);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("36-50");
        jPanel9.add(jLabel11);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("+50");
        jPanel9.add(jLabel8);

        jPanel7.add(jPanel9, java.awt.BorderLayout.SOUTH);

        jPanel8.add(jPanel7);

        jPanel1.add(jPanel8);

        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("VISITA A :");
        jLabel2.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel10.add(jLabel2);

        visitaA.setColumns(25);
        visitaA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel10.add(visitaA);

        jPanel1.add(jPanel10);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("OFICINA :");
        jLabel3.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel3.add(jLabel3);

        departamento.setColumns(5);
        departamento.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel3.add(departamento);

        jPanel1.add(jPanel3);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("RESTRINGIDO :");
        jLabel4.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel5.add(jLabel4);

        restringidoGrp.add(restringidoNO);
        restringidoNO.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        restringidoNO.setText("NO, TODO EL EDIFICIO");
        restringidoNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restringidoNOActionPerformed(evt);
            }
        });
        jPanel5.add(restringidoNO);

        restringidoGrp.add(restringidoSI);
        restringidoSI.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        restringidoSI.setText("SI, SOLO EL PISO");
        jPanel5.add(restringidoSI);

        jPanel1.add(jPanel5);

        add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FOTOGRAFÍA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel2.setMinimumSize(new java.awt.Dimension(200, 100));
        jPanel2.setLayout(new java.awt.BorderLayout());

        wcPPPanel.setMinimumSize(new java.awt.Dimension(100, 10));
        wcPPPanel.setPreferredSize(new java.awt.Dimension(100, 10));
        jPanel2.add(wcPPPanel, java.awt.BorderLayout.CENTER);

        fotoBtn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        fotoBtn.setText("Foto");
        jPanel14.add(fotoBtn);

        cameraBtn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cameraBtn.setText("Camara");
        jPanel14.add(cameraBtn);

        jPanel2.add(jPanel14, java.awt.BorderLayout.SOUTH);

        add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 5));

        aceptar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        aceptar.setText("REGISTRAR ENTRADA");
        jPanel11.add(aceptar);

        cancelar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cancelar.setText("CANCELAR");
        jPanel11.add(cancelar);

        add(jPanel11, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void restringidoNOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restringidoNOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_restringidoNOActionPerformed

	public WebcamPhotoPreviewPanel getWcPPPanel() {
		return (WebcamPhotoPreviewPanel)wcPPPanel;
	}

	public JButton getFotoBtn() {
		return fotoBtn;
	}

	public JButton getCameraBtn() {
		return cameraBtn;
	}

	public JButton getAceptar() {
		return aceptar;
	}

	public JButton getCancelar() {
		return cancelar;
	}

	public JTextField getFolio() {
		return folio;
	}

	public JTextField getNombreVisitante() {
		return nombreVisitante;
	}

	public JTextField getVisitaA() {
		return visitaA;
	}
		
	public JTextField getEntrada() {
		return entrada;
	}

	public ButtonGroup getGeneroGrp() {
		return generoGrp;
	}

	public JRadioButton getGeneroHombre() {
		return generoHombre;
	}

	public JRadioButton getGeneroMujer() {
		return generoMujer;
	}

	public ButtonGroup getRestringidoGrp() {
		return restringidoGrp;
	}

	public JRadioButton getRestringidoSI() {
		return restringidoSI;
	}

	public JRadioButton getRestringidoNO() {
		return restringidoNO;
	}

	public JSlider getEdadAproximada() {
		return edadAproximada;
	}

	public JTextField getDepartamento() {
		return departamento;
	}
	
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JButton cameraBtn;
    private javax.swing.JButton cancelar;
    private javax.swing.JTextField departamento;
    private javax.swing.JSlider edadAproximada;
    private javax.swing.JTextField entrada;
    private javax.swing.JTextField folio;
    private javax.swing.JButton fotoBtn;
    private javax.swing.ButtonGroup generoGrp;
    private javax.swing.JRadioButton generoHombre;
    private javax.swing.JRadioButton generoMujer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField nombreVisitante;
    private javax.swing.ButtonGroup restringidoGrp;
    private javax.swing.JRadioButton restringidoNO;
    private javax.swing.JRadioButton restringidoSI;
    private javax.swing.JTextField visitaA;
    private javax.swing.JPanel wcPPPanel;
    // End of variables declaration//GEN-END:variables
	
}
