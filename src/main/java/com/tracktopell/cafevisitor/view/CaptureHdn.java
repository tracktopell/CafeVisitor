/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.view;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author Softtek
 */
public class CaptureHdn extends javax.swing.JFrame implements WebcamMotionListener {

	/**
	 * Creates new form CaptureHdn
	 */
	private static WebcamTargetPanel wcPanel;
	private SimpleDateFormat sdfVideo = new SimpleDateFormat("yyyyMMdd_HHmmss");
	private SimpleDateFormat sdfRecordingVideo = new SimpleDateFormat("mm:ss.S");
	private DecimalFormat dfFrames=new DecimalFormat("00000");
	private BufferedImage lastImage;
	private boolean hideMode=true;
	private long videoStartTime=0;
	private boolean recording = false;
	public CaptureHdn() {
		initComponents();
		wcPanel = (WebcamTargetPanel) wcp;
		wcPanel.setPaintPersonIcon(false);

		hideModeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideMode = !hideMode;
			}
		});

		superfotoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teakePicture();
			}
		});
		
		videoStartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recording){
					videoStart();
				} else{
					videoStop();
				}
			}
		});
		

		Webcam.addDiscoveryListener(new WebcamDiscoveryListener() {
			public void webcamFound(WebcamDiscoveryEvent wde) {
				Webcam webcamF = wde.getWebcam();
				System.out.println("-> Webcam found:" + webcamF);
			}

			public void webcamGone(WebcamDiscoveryEvent wde) {
				Webcam webcamG = wde.getWebcam();
				System.out.println("-> Webcam gone:" + webcamG);
			}
		});

		WebcamMotionDetector detector = new WebcamMotionDetector(Webcam.getDefault());
		detector.setInterval(500); // one check per 500 ms
		detector.addMotionListener(this);
		detector.start();
	}

	@Override
	public void motionDetected(WebcamMotionEvent wme) {
		//System.out.println("Detected motion at:" + new Date());
	}

	private void lauchCaptureImages() {
		new Thread() {
			@Override
			public void run() {
				captureImages();
			}
		}.start();

	}
	
	private void videoStart(){
		new Thread() {
			@Override
			public void run() {
				videoRecording();
			}
		}.start();
	}
	
	private void teakePicture(){
		if(lastImage != null){
			try{
				ImageIO.write(lastImage, "JPG", new File("CameraFastSnapshot_"+sdfVideo.format(new Date())+".jpg"));
			}catch(Exception e){
				e.printStackTrace(System.err);
			}
		}
	}
	
	private void videoRecording(){
		
//		File file = new File("Recording_"+sdfVideo.format(new Date())+".ts");
//		IMediaWriter writer = ToolFactory.makeWriter(file.getName());
//		Dimension size = WebcamResolution.QVGA.getSize();
//		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
		try {

			Webcam webcam = Webcam.getDefault();

			if (webcam != null) {
				System.out.println("-> Webcam open: Start video Recording");
				long currentTimeVideo=0;
				long diffTime=0;
				int frame=0;
				recording = true;
				videoStartStop.setText("[_]");
				videoStartTime=System.currentTimeMillis();
				Date dateStart = new Date(videoStartTime);
				while (true) {

					if (lastImage != null) {
						File fileRecording = new File("recordings/Recording_"+sdfVideo.format(dateStart)+"_F"+dfFrames.format(frame)+".jpg");					
						ImageIO.write(lastImage, "JPG", fileRecording);
					} else {
						
					}
					currentTimeVideo=System.currentTimeMillis();
					diffTime=currentTimeVideo-videoStartTime;
					String recordingText = sdfRecordingVideo.format(new Date(diffTime));
					//System.out.println("-> Recording:"+recordingText);
					videoTimeRecording.setText(recordingText);
					Thread.sleep(200);
					
					if(!recording){
						break;
					}
					
					frame++;					
				}
				System.out.println("-> Webcam open: Finished video Recording");
				
			} else {
				System.out.println("-> No webcam :( ");
				wcPanel.repaint();
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}

	}
	
	private void videoStop(){
		System.out.println("-> STOP Recording ");
		videoStartStop.setText("O");		
		recording = false;
		videoTimeRecording.setText("");		
	}
	

	private void captureImages() {
		try {

			Webcam webcam = Webcam.getDefault();

			if (webcam != null) {
				System.out.println("-> Webcam name:" + webcam.getName());
				System.out.println("-> Webcam class:" + webcam.getClass());
				
				webcam.open();
				System.out.println("-> Webcam open");
				while (true) {

					lastImage = webcam.getImage();
					
					if (lastImage != null) {
						if(! hideMode){
							wcPanel.setWebcamImage(lastImage);
						} else {
							wcPanel.setWebcamImage(null);
						}
					} else {
					}

					Thread.sleep(100);					
				}
			} else {
				System.out.println("-> No webcam :( ");
				wcPanel.repaint();
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}

	private void captureVideo() {
		try {

			Webcam webcam = Webcam.getDefault();

			if (webcam != null) {
				System.out.println("-> Webcam name:" + webcam.getName());
				System.out.println("-> Webcam class:" + webcam.getClass());
				webcam.open();
				System.out.println("-> Webcam open");

			} else {
				System.out.println("-> No webcam :( ");
				wcPanel.repaint();
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wcp = new WebcamTargetPanel();
        toolBarControls = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        hideModeBtn = new javax.swing.JButton();
        superfotoBtn = new javax.swing.JButton();
        videoStartStop = new javax.swing.JButton();
        videoTimeRecording = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout wcpLayout = new javax.swing.GroupLayout(wcp);
        wcp.setLayout(wcpLayout);
        wcpLayout.setHorizontalGroup(
            wcpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );
        wcpLayout.setVerticalGroup(
            wcpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        getContentPane().add(wcp, java.awt.BorderLayout.CENTER);

        toolBarControls.setRollover(true);

        hideModeBtn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        hideModeBtn.setText("H");
        jPanel2.add(hideModeBtn);

        superfotoBtn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        superfotoBtn.setText("P");
        jPanel2.add(superfotoBtn);

        videoStartStop.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        videoStartStop.setForeground(new java.awt.Color(255, 0, 0));
        videoStartStop.setText("O");
        jPanel2.add(videoStartStop);

        videoTimeRecording.setBackground(new java.awt.Color(0, 0, 0));
        videoTimeRecording.setColumns(7);
        videoTimeRecording.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        videoTimeRecording.setForeground(new java.awt.Color(0, 255, 0));
        videoTimeRecording.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jPanel2.add(videoTimeRecording);

        toolBarControls.add(jPanel2);

        getContentPane().add(toolBarControls, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents
	private static CaptureHdn captureHdn = null;

	public static void main(String[] args) {
		captureHdn = new CaptureHdn();
		captureHdn.setVisible(true);
		captureHdn.lauchCaptureImages();
	}
	
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton hideModeBtn;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton superfotoBtn;
    private javax.swing.JToolBar toolBarControls;
    private javax.swing.JButton videoStartStop;
    private javax.swing.JTextField videoTimeRecording;
    private javax.swing.JPanel wcp;
    // End of variables declaration//GEN-END:variables
}
