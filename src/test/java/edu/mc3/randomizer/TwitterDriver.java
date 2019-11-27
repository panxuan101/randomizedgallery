package edu.mc3.randomizer;

import java.awt.image.BufferedImage;
import java.io.IOException;

import edu.mc3.randomizer.ImageMods;
import edu.mc3.randomizer.TwitterPhotoDownloader;
import edu.mc3.randomizer.UploadPicture;
import twitter4j.TwitterException;

/**
 * 
 *
 */
public class TwitterDriver 
{
    public static void main( String[] args ) throws TwitterException, IOException
    {
    	BufferedImage image = TwitterPhotoDownloader.getTwitterPhotoFromTwitterID("1193983796372213762");
    	BufferedImage abstractImage = ImageMods.redFilter(image);
    	abstractImage = ImageMods.colorizedStatic(abstractImage);
    	String message = "test";
    	UploadPicture.uploadPic(abstractImage, message);
    }
}