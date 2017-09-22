/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.model;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Softtek
 */
public class ComparingTwoImages {
	private static final int PIX_SIZE = 10;
	private static final boolean isDebbuging = false;

	public static boolean areDiferentImages(BufferedImage img1,BufferedImage img2){
		// How big should the pixelations be?
		boolean areDifferent = false;
		int countDifferent = 0;
		
		// Get the raster data (array of pixels)
		Raster src1 = img1.getData();
		// Create an identically-sized output raster
		WritableRaster dest1 = src1.createCompatibleWritableRaster();

		// Get the raster data (array of pixels)
		Raster src2 = img2.getData();
		// Create an identically-sized output raster
		WritableRaster dest2 = src2.createCompatibleWritableRaster();

		boolean theAlmostSamePixel = false;
		double[] redPixel = {255,0,0};

		double[] pixelAvg1 = {0.0,0.0,0.0};
		double[] srcPixel1 = null;
		double[] pixelAvg2 = {0.0,0.0,0.0};
		double[] srcPixel2 = null;
		
		double[] pixelImg1 = null;		
		double[] pixelImg2 = null;
		int pixelColorTollerance = 2;		
		int numPixels = 0;
		// Loop through every PIX_SIZE pixels, in both x and y directions
		for (int y = 0; y < src1.getHeight(); y += PIX_SIZE) {
			for (int x = 0; x < src1.getWidth(); x += PIX_SIZE) {
				theAlmostSamePixel = true;

				numPixels = 0;
				for (int yd = y; (yd < y + PIX_SIZE) && (yd < dest1.getHeight()); yd++) {
					for (int xd = x; (xd < x + PIX_SIZE) && (xd < dest1.getWidth()); xd++) {
						numPixels++;
						srcPixel1 = src1.getPixel(xd, yd, srcPixel1);
						pixelAvg1[0] += srcPixel1[0];
						pixelAvg1[1] += srcPixel1[1];
						pixelAvg1[2] += srcPixel1[2];
					}
				}
				pixelAvg1[0] = pixelAvg1[0] / numPixels;
				pixelAvg1[1] = pixelAvg1[1] / numPixels;
				pixelAvg1[2] = pixelAvg1[2] / numPixels;
				
				numPixels = 0;
				for (int yd = y; (yd < y + PIX_SIZE) && (yd < dest2.getHeight()); yd++) {
					for (int xd = x; (xd < x + PIX_SIZE) && (xd < dest2.getWidth()); xd++) {
						numPixels++;
						srcPixel2 = src2.getPixel(xd, yd, srcPixel2);
						pixelAvg2[0] += srcPixel2[0];
						pixelAvg2[1] += srcPixel2[1];
						pixelAvg2[2] += srcPixel2[2];
					}
				}
				pixelAvg2[0] = pixelAvg2[0] / numPixels;
				pixelAvg2[1] = pixelAvg2[1] / numPixels;
				pixelAvg2[2] = pixelAvg2[2] / numPixels;
				
				// Copy the pixel
				pixelImg1 = pixelAvg1;//src1.getPixel(x, y, pixelImg1);				
				pixelImg2 = pixelAvg2;//src2.getPixel(x, y, pixelImg2);
				
				int diffPixels = 0;
				for(int rgb,ipi=0; ipi <pixelImg1.length; ipi++){
					if(! isInRange(pixelImg1[ipi], pixelImg2[ipi], pixelColorTollerance)){
						diffPixels++;
					}		
				}
				
				if(diffPixels == 3){
					countDifferent++;
					if(isDebbuging){
						System.out.print("PIXEL Diff "+diffPixels+": At "+x+","+y+" :");
						for(int rgb,ipi=0; ipi <pixelImg1.length; ipi++){
							System.out.print("["+ipi+"]{"+pixelImg1[ipi]+" != "+pixelImg2[ipi]+", inRange ? "+
									isInRange(pixelImg1[ipi], pixelImg2[ipi], pixelColorTollerance)+"}");
						}
						System.out.println(" <- ");
					}
					theAlmostSamePixel = false;
				}
				for (int yd = y; (yd < y + PIX_SIZE) && (yd < dest1.getHeight()); yd++) {
					for (int xd = x; (xd < x + PIX_SIZE) && (xd < dest1.getWidth()); xd++) {
						if(theAlmostSamePixel){
							//dest1.setPixel(xd, yd, pixelImg1);
							dest1.setPixel(xd, yd, pixelAvg1);							
						} else{
							dest1.setPixel(xd, yd, redPixel);
						}
					}
				}
			}
		}
		if(isDebbuging){
			System.out.println("->countDifferent="+countDifferent);
		}
		if(countDifferent > 4){
			areDifferent = true;
		}
		return areDifferent;
	}
	
	public static BufferedImage makeVisibleIfDiferentImages(BufferedImage img1,BufferedImage img2){
		// How big should the pixelations be?
		boolean areDifferent = false;
		int countDifferent = 0;
		
		// Get the raster data (array of pixels)
		Raster src1 = img1.getData();
		// Create an identically-sized output raster
		WritableRaster dest1 = src1.createCompatibleWritableRaster();

		// Get the raster data (array of pixels)
		Raster src2 = img2.getData();
		// Create an identically-sized output raster
		WritableRaster dest2 = src2.createCompatibleWritableRaster();

		boolean theAlmostSamePixel = false;
		double[] redPixel =  {255,0,0};

		double[] pixelAvg1 = {0.0,0.0,0.0};
		double[] srcPixel1 = null;
		double[] pixelAvg2 = {0.0,0.0,0.0};
		double[] srcPixel2 = null;
		
		double[] pixelImg1 = null;		
		double[] pixelImg2 = null;
		int pixelColorTollerance = 3;		
		int numPixels = 0;
		// Loop through every PIX_SIZE pixels, in both x and y directions
		for (int y = 0; y < src1.getHeight(); y += PIX_SIZE) {
			for (int x = 0; x < src1.getWidth(); x += PIX_SIZE) {
				theAlmostSamePixel = true;

				numPixels = 0;
				for (int yd = y; (yd < y + PIX_SIZE) && (yd < dest1.getHeight()); yd++) {
					for (int xd = x; (xd < x + PIX_SIZE) && (xd < dest1.getWidth()); xd++) {
						numPixels++;
						srcPixel1 = src1.getPixel(xd, yd, srcPixel1);
						pixelAvg1[0] += srcPixel1[0];
						pixelAvg1[1] += srcPixel1[1];
						pixelAvg1[2] += srcPixel1[2];
					}
				}
				pixelAvg1[0] = pixelAvg1[0] / numPixels;
				pixelAvg1[1] = pixelAvg1[1] / numPixels;
				pixelAvg1[2] = pixelAvg1[2] / numPixels;
				
				numPixels = 0;
				for (int yd = y; (yd < y + PIX_SIZE) && (yd < dest2.getHeight()); yd++) {
					for (int xd = x; (xd < x + PIX_SIZE) && (xd < dest2.getWidth()); xd++) {
						numPixels++;
						srcPixel2 = src2.getPixel(xd, yd, srcPixel2);
						pixelAvg2[0] += srcPixel2[0];
						pixelAvg2[1] += srcPixel2[1];
						pixelAvg2[2] += srcPixel2[2];
					}
				}
				pixelAvg2[0] = pixelAvg2[0] / numPixels;
				pixelAvg2[1] = pixelAvg2[1] / numPixels;
				pixelAvg2[2] = pixelAvg2[2] / numPixels;
				
				// Copy the pixel
				pixelImg1 = pixelAvg1;//src1.getPixel(x, y, pixelImg1);				
				pixelImg2 = pixelAvg2;//src2.getPixel(x, y, pixelImg2);
				
				int diffPixels = 0;
				for(int rgb,ipi=0; ipi <pixelImg1.length; ipi++){
					if(! isInRange(pixelImg1[ipi], pixelImg2[ipi], pixelColorTollerance)){
						diffPixels++;
					}		
				}
				
				if(diffPixels == 3){
					countDifferent++;
					if(isDebbuging){
						System.out.print("PIXEL Diff "+diffPixels+": At "+x+","+y+" :");
						for(int rgb,ipi=0; ipi <pixelImg1.length; ipi++){
							System.out.print("["+ipi+"]{"+pixelImg1[ipi]+" != "+pixelImg2[ipi]+", inRange ? "+
									isInRange(pixelImg1[ipi], pixelImg2[ipi], pixelColorTollerance)+"}");
						}
						System.out.println(" <- ");
					}
					theAlmostSamePixel = false;
				}
				for (int yd = y; (yd < y + PIX_SIZE) && (yd < dest1.getHeight()); yd++) {
					for (int xd = x; (xd < x + PIX_SIZE) && (xd < dest1.getWidth()); xd++) {
						if(theAlmostSamePixel){
							dest1.setPixel(xd, yd, pixelAvg1);							
						} else{
							dest1.setPixel(xd, yd, redPixel);
						}
					}
				}
			}
		}
		if(isDebbuging){
			System.out.println("->countDifferent="+countDifferent);
		}
		if(countDifferent > 4){
			areDifferent = true;
		}
		if(areDifferent){
			img1.setData(dest1);
		}
		return img1;
	}
	
	private static boolean isInRange(double pixelRGB1,double pixelRGB2,int pixelTolerance){
		return(pixelRGB1 >= pixelRGB2 - pixelTolerance && pixelRGB1 <= pixelRGB2 + pixelTolerance );
	}
}
