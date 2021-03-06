/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.view;

import com.tracktopell.cafevisitor.model.ComparingTwoImages;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;

/**
 *
 * @author Softtek
 */
public class WebcamPhotoPreviewPanel extends javax.swing.JPanel {

	BufferedImage webcamPreviousImage;
	BufferedImage webcamImage;
	BufferedImage webcamLastPicture;
	BufferedImage webcamLastFastPicture;
	BufferedImage personIconImage;
	BufferedImage noCameraIconImage;
	boolean paintPersonIcon = true;
	boolean moving = false;
	boolean detectMovemnt = false;
	boolean backUpSnapshot = false;
	
	public void setWebcamImage(BufferedImage webcamImage) {
		this.webcamPreviousImage = this.webcamImage;
		this.webcamImage = webcamImage;

		if (detectMovemnt) {
			if (this.webcamImage != null && this.webcamPreviousImage != null) {
				moving = false; // implement
				if (!moving) {
					System.out.println(">> MOVING:" + new Date());
				} else {
					moving = false;
				}
			}
		}
		repaint();
	}

	public void setPaintPersonIcon(boolean paintPersonIcon) {
		this.paintPersonIcon = paintPersonIcon;
	}

	/**
	 * Creates new form WebcamPhotoPreviewPanel
	 */
	public WebcamPhotoPreviewPanel() {
		initComponents();
		try {
			personIconImage = ImageIO.read(WebcamPhotoPreviewPanel.class.getResourceAsStream("/images/person-icon.png"));
			noCameraIconImage = ImageIO.read(WebcamPhotoPreviewPanel.class.getResourceAsStream("/images/noCamera-icon.png"));
		} catch (Exception e) {
		}
	}

	public void takeSnapshot() {
		webcamLastPicture = webcamImage;
		if (backUpSnapshot) {
			new Thread() {
				@Override
				public void run() {
					saveWebcamLastPicture();
				}
			}.start();
		}

	}

	public void takeFastSnapshot() {
		webcamLastFastPicture = webcamImage;
		new Thread() {
			@Override
			public void run() {
				saveWebcamFastLastPicture();
			}
		}.start();
	}
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMHHmmss");

	private void saveWebcamLastPicture() {
		String fileName = "./CameraSnapshot_" + sdf.format(new Date()) + ".png";
		try {
			ImageIO.write(webcamLastPicture, "png", new FileOutputStream(fileName));
			System.out.println("..ok saved to " + fileName);
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}

	private void saveWebcamFastLastPicture() {
		String fileName = "./CameraFastSnapshot_" + sdf.format(new Date()) + ".png";
		try {
			ImageIO.write(webcamLastFastPicture, "png", new FileOutputStream(fileName));
			System.out.println("..ok saved to " + fileName);
			webcamLastFastPicture = null;
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}

	public BufferedImage getWebcamLastPicture() {
		return webcamLastPicture;
	}

	public void cameraMode() {
		webcamLastPicture = null;
	}

	static double mv = Math.PI;
	static boolean rotated = true;

	public void setRotated(boolean r) {
		rotated = r;
		repaint();
	}

	private BufferedImage getRotatedImage(BufferedImage img) {
		BufferedImage rotatedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = (Graphics2D) rotatedImage.getGraphics();
		if (rotated) {
			g2d.rotate(mv);
			g2d.drawImage(img, -img.getWidth(), -img.getHeight(), null);
		} else {
			g2d.drawImage(img, 0, 0, null);
		}

		return rotatedImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.err.println("->WebcamPhotoPreviewPanel.paintComponent:");
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//System.err.println("->WebcamPhotoPreviewPanel.paint:");
		BufferedImage imageToPaint = null;

		if (webcamImage != null) {

			if (webcamLastPicture != null) {
				imageToPaint = webcamLastPicture;
			} else {
				if (moving) {
					imageToPaint = webcamPreviousImage;
				} else {
					imageToPaint = webcamImage;
				}
			}

			int imgW = imageToPaint.getWidth();
			int imgH = imageToPaint.getHeight();
			double rImg = (double) getWidth() / (double) imgW;

			int imgY = 0;

			Graphics2D g2d = (Graphics2D) g;
			AffineTransform atB = g2d.getTransform();

			AffineTransform at = new AffineTransform();

			at.scale(rImg, rImg);

			g2d.setTransform(at);

			imgY = (int) (((getHeight() / 2.0) / rImg) - (imgH / 2.0));

			g2d.drawImage(imageToPaint,
					0,
					imgY,
					null);
			g2d.setTransform(atB);

			if (webcamLastPicture == null) {

				if (paintPersonIcon && personIconImage != null) {
					AffineTransform atp = new AffineTransform();
					Composite acB = g2d.getComposite();

					int imgWP = personIconImage.getWidth();
					int imgHP = personIconImage.getHeight();
					double rImgP = (double) getWidth() / ((double) imgWP);
					atp.scale(rImgP, rImgP);
					AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

					g2d.setTransform(atp);
					g2d.setComposite(ac);

					int imgYP = (int) (((getHeight() / 2.0) / rImgP) - (imgHP / 2.0));

					g2d.drawImage(personIconImage,
							0,
							imgYP,
							null);

					g2d.setTransform(atB);
					g2d.setComposite(acB);
				}
				//==================================================================
				g.setColor(Color.RED);

				int imgHS = (int) (imgH * rImg);

				//VERTICAL
				g.drawLine(getWidth() / 2, getHeight() / 2 - imgHS / 2,
						getWidth() / 2, getHeight() / 2 + imgHS / 2);
				// HORIZONTAL
				g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);


			} else {
				g.setColor(Color.GREEN);

				int imgHS = (int) (imgH * rImg);
				int imgWS = (int) (imgW * rImg);

				g.drawRect(0, getHeight() / 2 - imgHS / 2,
						imgWS - 1, imgHS - 1);

			}
		} else if (noCameraIconImage != null) {
			//g.setColor(getBackground());
			//g.clearRect(0, 0, getWidth(), getHeight());

			imageToPaint = noCameraIconImage;

			int imgW = imageToPaint.getWidth();
			int imgH = imageToPaint.getHeight();
			double rImg = (double) getWidth() / (double) imgW;

			int imgY = 0;

			Graphics2D g2d = (Graphics2D) g;
			AffineTransform atB = g2d.getTransform();

			AffineTransform at = new AffineTransform();

			at.scale(rImg, rImg);

			g2d.setTransform(at);

			imgY = (int) (((getHeight() / 2.0) / rImg) - (imgH / 2.0));

			g2d.drawImage(imageToPaint,
					0,
					imgY,
					null);
			g2d.setTransform(atB);

		} else {
			g.setColor(getBackground());
			g.clearRect(0, 0, getWidth(), getHeight());

			g.setColor(Color.RED);
			g.drawString("WEBCAM NO ENCONTRADA", 10, getHeight() / 2);
		}
	}

	@Override
	public void paintAll(Graphics g) {
		super.paintAll(g);
		//System.err.println("->WebcamPhotoPreviewPanel.paintAll:");		
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(240, 320));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
