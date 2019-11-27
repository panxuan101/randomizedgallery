package edu.mc3.randomizer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import boofcv.alg.color.ColorRgb;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;

public class ImageMods {  
	private String mod;
	//private BufferedImage img;
	public String getMod() {
		return mod;
	}
	
	public void setMod(String mod) {
		this.mod = mod;
	}
	
	public String[] getAvailableMods() {
		String[] mods = {"Brighten", "Invert", "GrayScale", "Static", "RedFilter"};
		return mods;
	}
	
	public BufferedImage applyFilter(BufferedImage img) throws IOException {
		BufferedImage image = deepCopy(img);
		if(mod == "Brighten") {
			return makeBrighter(image);
		}
		else if(mod == "Invert") {
			return invertColors(image);
		}
		else if(mod == "GrayScale") {
			return grayScale(image);
		}
		else if(mod == "Static") {
			return colorizedStatic(image);
		}
		else if(mod == "RedFilter") {
			return redFilter(image);
		}
		return image;
		
		
	}
	
	// CREDIT TO STACKOVERFLOW
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
//	/**
//	 * @return the img
//	 */
//	public BufferedImage getImg() {
//		return img;
//	}


//	/**
//	 * @param img the img to set
//	 */
//	public void setImg(BufferedImage img) {
//		this.img = img;
//	}


//	/**
//	 * @return the twitterStuff
//	 */
//	public clsTwitter getTwitterStuff() {
//		return twitterStuff;
//	}


//	/**
//	 * @param twitterStuff the twitterStuff to set
//	 */
//	public void setTwitterStuff(clsTwitter twitterStuff) {
//		this.twitterStuff = twitterStuff;
//	}


	/**
	 * Method to apply a red filter over the given image
	 * @return the filtered image
	 * @throws IOException
	 */
		    public static BufferedImage redFilter(BufferedImage img) throws IOException{
		
		    	//img = ImageIO.read(new File("strawberry.jpg"));
		    	for(int y = 0; y < img.getHeight(); y++) {
		    		for(int x = 0; x < img.getWidth(); x++) {
		    			Color originalColor = new Color(img.getRGB(x, y));
		    			int red = originalColor.getRed();
		    			int green = originalColor.getGreen();
		    			int blue = originalColor.getBlue();
		    			int rTint = (255 - red);
		    			int gTint = (255 - green);
		    			int bTint= (255 - blue);
		    			Color tinty = new Color(red, gTint, bTint);
		    			int tint = tinty.getRGB();
		    			img.setRGB(x, y, tint);
		    					    			
		    		}
		    	}
		    	return img;
//		    	return null;
		    	}
		    
		    
		    /**
		     * Method to invert the colors of the given image
		     * @return the inverted image
		     * @throws IOException
		     */
		    public static BufferedImage invertColors(BufferedImage img) throws IOException{
		    	
		    	//loop through the x and y coordinates to access each pixel
				for (int y = 0; y < img.getHeight(); y++) {
				    for (int x = 0; x < img.getWidth(); x++) {
				    	
				    	//create a color object to store the original color of the given pixel
				    	Color originalColor = new Color(img.getRGB(x, y));
				    	
				    	//get the red, green, and blue values of the pixel
				    	int red = originalColor.getRed();
				    	int green = originalColor.getGreen();
				    	int blue = originalColor.getBlue();
				    	
				    	//set a new color object to the inverse of the current color
				    	Color invertColor = new Color((255 - red), (255 - green), (255 - blue));
				    	
				    	//store the new RGB value within an int
				    	int invertRGB = invertColor.getRGB();
				    	
				    	//set the pixel to the inverted color
				    	img.setRGB(x, y, invertRGB);
				    	
				    }
				}
		    	return img;
		    }
		    
		    /**
		     * Method to convert the image to gray scale
		     * @return the gray scale image
		     * @throws IOException
		     */
		    public static BufferedImage grayScale(BufferedImage img) throws IOException {
		    	
		    	
		    	//convert the buffered image to a Planar 
		    	Planar<GrayU8> color = ConvertBufferedImage.convertFrom(img,true, ImageType.pl(3,GrayU8.class));
		    	
		    	//use the Planar to create a GrayU8
		    	GrayU8 weighted = new GrayU8(color.width,color.height);

		    	//convert the image to gray scale
		    	for (int i = 0; i < 100; i++) {
					ColorRgb.rgbToGray_Weighted(color, weighted); 
					
		    	}
		    	return img;
		
		    }
		    
		    /**
		     * Method to increase the brightness of an image
		     * @return the brightened image
		     * @throws IOException
		     */
		    public static BufferedImage makeBrighter(BufferedImage img) throws IOException{
		    	
		    	//img = ImageIO.read(new File("strawberry.jpg"));
				for(int y = 0; y < img.getHeight(); y++) {
					for(int x = 0; x < img.getWidth(); x++) {
						
						//get the current RGB value
						int RGBVal = img.getRGB(x, y);
						
						//create an int to multiply the RGB value by
						int num = 30000;
						
						//increase the current RGB value 
						int newRGB = RGBVal + (255 * num);
						
						//reset the pixel to the new color
						img.setRGB(x, y, newRGB);
					}
				}
				return img;
		    }
		    
		    /**
		     * Method to randomize the color of each pixel
		     * @return a randomized image
		     * @throws IOException
		     */
		    public static BufferedImage colorizedStatic(BufferedImage img) throws IOException {
		    	
		    	//create a new Random object 
		    	Random rand = new Random();
		    	
		    	//img = ImageIO.read(new File("strawberry.jpg"));
		    	for (int y = 0; y < img.getHeight(); y++) {
		    	    for (int x = 0; x < img.getWidth(); x++) {
		    	    	
		    	    	//generate three random ints between 0 - 255 to be used for each RGB value
		    	    	int randInt = rand.nextInt(256);
				    	int randInt2 = rand.nextInt(256);
				    	int randInt3 = rand.nextInt(256);
				    	
				    	//create a new color with the randomized values
				    	Color randomized = new Color(randInt, randInt2, randInt3);
				    	
				    	//store the new RGB value in an int
				    	int color = randomized.getRGB();
				    	
				    	  //assing the current pixel its new color
		    	          img.setRGB(x, y, color);
		    	          
		    	    }
		    	}
		    	return img;
		    }
		    
}